package tk.skeptick.skyeng_dictionary.database.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

open class MeaningDto(
    @PrimaryKey var id: String? = null,
    var wordId: Int? = null,
    var difficultyLevel: Int? = null,
    var partOfSpeechCode: String? = null,
    var prefix: String? = null,
    var text: String? = null,
    var soundUrl: String? = null,
    var transcription: String? = null,
    var updatedAt: String? = null,
    var mnemonics: String? = null,
    var translation: RealmList<Translation> = RealmList(),
    var images: RealmList<String> = RealmList(),
    var definition: Definition? = null,
    var examples: RealmList<Definition> = RealmList()
) : RealmObject() {

    @RealmClass(embedded = true)
    open class Translation(
        var text: String? = null,
        var note: String? = null
    ) : RealmObject()

    @RealmClass(embedded = true)
    open class Definition(
        var text: String? = null,
        var soundUrl: String? = null
    ) : RealmObject()

}