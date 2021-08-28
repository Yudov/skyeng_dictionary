package tk.skeptick.skyeng_dictionary.database.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

open class MeaningDto(
    @PrimaryKey var id: String = "",
    @Required var wordId: Int = 0,
    @Required var difficultyLevel: Int = 0,
    @Required var partOfSpeechCode: String = "",
    var prefix: String? = null,
    @Required var text: String = "",
    var soundUrl: String? = null,
    var transcription: String? = null,
    @Required var updatedAt: String = "",
    var mnemonics: String? = null,
    @Required var translation: Translation = Translation(),
    var imageUrls: RealmList<String> = RealmList(),
    var definition: Definition? = null,
    var examples: RealmList<Definition> = RealmList()
) : RealmObject() {

    @RealmClass(embedded = true)
    open class Translation(
        @Required var text: String = "",
        var note: String? = null
    ) : RealmObject()

    @RealmClass(embedded = true)
    open class Definition(
        @Required var text: String = "",
        @Required var soundUrl: String = ""
    ) : RealmObject()

}