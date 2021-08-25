package tk.skeptick.skyeng_dictionary.domain.models

data class Meaning(
    val id: String,
    val wordId: Int,
    val difficultyLevel: Int,
    val partOfSpeech: PartOfSpeech,
    val prefix: String?,
    val text: String,
    val soundUrl: String?,
    val transcription: String?,
    val updatedAt: String?,
    val mnemonics: String?,
    val translation: Translation,
    val imageUrls: List<String>,
    val definition: Definition?,
    val examples: List<Definition>
)