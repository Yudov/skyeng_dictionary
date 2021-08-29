package tk.skeptick.skyeng_dictionary.extensions

import android.graphics.Color
import tk.skeptick.skyeng_dictionary.domain.models.PartOfSpeech

val PartOfSpeech.color: Int
    get() = when (this) {
        PartOfSpeech.NOUN -> Color.BLUE
        PartOfSpeech.VERB -> Color.GREEN
        PartOfSpeech.ADJECTIVE -> Color.RED
        PartOfSpeech.ADVERB -> Color.CYAN
        PartOfSpeech.PREPOSITION -> Color.GRAY
        PartOfSpeech.PRONOUN -> Color.MAGENTA
        else -> Color.BLACK
    }