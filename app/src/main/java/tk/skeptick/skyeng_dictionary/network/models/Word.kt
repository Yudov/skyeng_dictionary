package tk.skeptick.skyeng_dictionary.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Word(
    @SerialName("id") val id: Int,
    @SerialName("text") val text: String,
    @SerialName("meanings") val meanings: List<Meaning>
) {

    @Serializable
    data class Meaning(
        @SerialName("id") val id: Int,
        @SerialName("partOfSpeechCode") val partOfSpeech: PartOfSpeech,
        @SerialName("translation") val translation: Translation,
        @SerialName("previewUrl") val previewUrl: String? = null,
        @SerialName("imageUrl") val imageUrl: String? = null,
        @SerialName("transcription") val transcription: String? = null,
        @SerialName("soundUrl") val soundUrl: String? = null
    )

}