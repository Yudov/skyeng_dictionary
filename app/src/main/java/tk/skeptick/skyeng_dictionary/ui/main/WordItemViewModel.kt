package tk.skeptick.skyeng_dictionary.ui.main

import tk.skeptick.skyeng_dictionary.domain.models.PartOfSpeech

sealed class WordItemViewModel {

    data class Word(
        val text: String,
        val transcription: String?
    ) : WordItemViewModel()

    data class Meaning(
        val id: Int,
        val partOfSpeech: PartOfSpeech,
        val translation: String,
        val isLast: Boolean
    ) : WordItemViewModel()

    object Progress : WordItemViewModel()

    object Error : WordItemViewModel()

}