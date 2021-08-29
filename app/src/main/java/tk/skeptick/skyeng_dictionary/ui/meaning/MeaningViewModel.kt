package tk.skeptick.skyeng_dictionary.ui.meaning

data class MeaningViewModel(
    val word: String,
    val transcription: String?,
    val translation: String,
    val description: String?,
    val imageUrl: String?
)