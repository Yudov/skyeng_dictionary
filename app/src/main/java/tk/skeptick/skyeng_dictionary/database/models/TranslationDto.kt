package tk.skeptick.skyeng_dictionary.database.models

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass(embedded = true)
open class TranslationDto(
    @Required var text: String = "",
    var note: String? = null
) : RealmObject()