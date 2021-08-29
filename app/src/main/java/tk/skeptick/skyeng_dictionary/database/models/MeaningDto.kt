package tk.skeptick.skyeng_dictionary.database.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class MeaningDto(
    @PrimaryKey var id: String = "",
    var wordId: Int = 0,
    var difficultyLevel: Int? = null,
    @Required var partOfSpeechCode: String = "",
    var prefix: String? = null,
    @Required var text: String = "",
    var soundUrl: String? = null,
    var transcription: String? = null,
    @Required var updatedAt: String = "",
    var mnemonics: String? = null,
    var translation: TranslationDto? = null,
    var imageUrls: RealmList<String> = RealmList(),
    var definition: DefinitionDto? = null,
    var examples: RealmList<DefinitionDto> = RealmList()
) : RealmObject()