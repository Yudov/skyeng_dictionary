package tk.skeptick.skyeng_dictionary.domain.models

data class Word(
    val id: Int,
    val text: String,
    val meanings: List<Meaning>
) {

    data class Meaning(
        val id: Int,
        val partOfSpeech: PartOfSpeech,
        val translation: Translation,
        val previewUrl: String?,
        val imageUrl: String?,
        val transcription: String?,
        val soundUrl: String?
    )

}