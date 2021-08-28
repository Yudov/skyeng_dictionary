package tk.skeptick.skyeng_dictionary.domain

import io.reactivex.Single
import tk.skeptick.skyeng_dictionary.domain.models.Meaning
import tk.skeptick.skyeng_dictionary.domain.models.Word

interface DictionaryRepository {

    fun hasCachedMeaning(meaningId: Int): Boolean

    fun search(text: String, page: Int, pageSize: Int): Single<List<Word>>

    fun getMeaning(meaningId: Int): Single<Meaning>

}