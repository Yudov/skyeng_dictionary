package tk.skeptick.skyeng_dictionary.repositories

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmList
import tk.skeptick.skyeng_dictionary.database.models.DefinitionDto
import tk.skeptick.skyeng_dictionary.database.models.MeaningDto
import tk.skeptick.skyeng_dictionary.database.models.TranslationDto
import tk.skeptick.skyeng_dictionary.domain.DictionaryRepository
import tk.skeptick.skyeng_dictionary.domain.models.*
import tk.skeptick.skyeng_dictionary.network.DictionaryApi
import tk.skeptick.skyeng_dictionary.network.models.MeaningResponse
import tk.skeptick.skyeng_dictionary.network.models.WordResponse
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryApi: DictionaryApi,
    private val realm: Realm
    ) : DictionaryRepository {

    override fun hasCachedMeaning(meaningId: Int): Boolean =
        realm.where(MeaningDto::class.java)
            .containsValue("id", meaningId)
            .count() == 1L

    override fun search(text: String, page: Int, pageSize: Int): Single<List<Word>> =
        dictionaryApi.search(text, page, pageSize)
            .map { words -> words.map(mapWordResponseToDomain) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getMeaning(meaningId: Int): Single<Meaning> =
        realm.where(MeaningDto::class.java)
            .equalTo("id", meaningId)
            .findFirstAsync()
            .asFlowable<MeaningDto>()
            .filter { it.isLoaded && it.isValid }
            .firstOrError()
            .map { mapMeaningDtoToDomain(it)!! }
            .onErrorResumeNext { getMeaningFromNetwork(meaningId) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun getMeaningFromNetwork(meaningId: Int): Single<Meaning> =
        dictionaryApi.getMeanings(listOf(meaningId))
            .map { it.firstOrNull()?.let(mapMeaningResponseToDomain) ?: throw NoSuchElementException() }
            .doOnSuccess { realm.insert(mapMeaningDomainToDto(it)) }


    private companion object {

        val partOfSpeechByCode = PartOfSpeech.values().associateBy(PartOfSpeech::code)

        val mapWordResponseToDomain: WordResponse.() -> Word = {
            Word(
                id = id,
                text = text,
                meanings = meanings.mapNotNull { meaning ->
                    Word.Meaning(
                        id = meaning.id,
                        partOfSpeech = partOfSpeechByCode[meaning.partOfSpeechCode] ?: return@mapNotNull null,
                        translation = Translation(meaning.translation.text, meaning.translation.note),
                        previewUrl = meaning.previewUrl,
                        imageUrl = meaning.imageUrl,
                        transcription = meaning.transcription,
                        soundUrl = meaning.soundUrl
                    )
                }
            )
        }

        val mapMeaningResponseToDomain: MeaningResponse.() -> Meaning? = mapper@ {
            Meaning(
                id = id,
                wordId = wordId,
                difficultyLevel = difficultyLevel,
                partOfSpeech = partOfSpeechByCode[partOfSpeechCode] ?: return@mapper null,
                prefix = prefix,
                text = text,
                soundUrl = soundUrl,
                transcription = transcription,
                updatedAt = updatedAt,
                mnemonics = mnemonics,
                translation = Translation(translation.text, translation.note),
                imageUrls = images.map(MeaningResponse.Image::url),
                definition = definition?.let { Definition(it.text, it.soundUrl) },
                examples = examples.map { Definition(it.text, it.soundUrl) }
            )
        }

        val mapMeaningDtoToDomain: MeaningDto.() -> Meaning? = mapper@ {
            Meaning(
                id = id,
                wordId = wordId,
                difficultyLevel = difficultyLevel,
                partOfSpeech = partOfSpeechByCode[partOfSpeechCode] ?: return@mapper null,
                prefix = prefix,
                text = text,
                soundUrl = soundUrl,
                transcription = transcription,
                updatedAt = updatedAt,
                mnemonics = mnemonics,
                translation = Translation(translation!!.text, translation!!.note),
                imageUrls = imageUrls.toList(),
                definition = definition?.let { Definition(it.text, it.soundUrl) },
                examples = examples.map { Definition(it.text, it.soundUrl) }
            )
        }

        val mapMeaningDomainToDto: Meaning.() -> MeaningDto = {
            MeaningDto(
                id = id,
                wordId = wordId,
                difficultyLevel = difficultyLevel,
                partOfSpeechCode = partOfSpeech.code,
                prefix = prefix,
                text = text,
                soundUrl = soundUrl,
                transcription = transcription,
                updatedAt = updatedAt,
                mnemonics = mnemonics,
                translation = TranslationDto(translation.text, translation.note),
                imageUrls = RealmList(*imageUrls.toTypedArray()),
                definition = definition?.let { DefinitionDto(it.text, it.soundUrl) },
                examples = RealmList(*examples.map { DefinitionDto(it.text, it.soundUrl) }.toTypedArray())
            )
        }

    }

}