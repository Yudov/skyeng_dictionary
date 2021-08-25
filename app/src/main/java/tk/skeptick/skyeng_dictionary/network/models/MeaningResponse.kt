package tk.skeptick.skyeng_dictionary.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeaningResponse(
    @SerialName("id") val id: String,
    @SerialName("wordId") val wordId: Int,
    @SerialName("difficultyLevel") val difficultyLevel: Int,
    @SerialName("partOfSpeechCode") val partOfSpeechCode: String,
    @SerialName("prefix") val prefix: String? = null,
    @SerialName("text") val text: String,
    @SerialName("soundUrl") val soundUrl: String? = null,
    @SerialName("transcription") val transcription: String? = null,
    @SerialName("updatedAt") val updatedAt: String,
    @SerialName("mnemonics") val mnemonics: String? = null,
    @SerialName("translation") val translation: Translation,
    @SerialName("images") val images: List<Image> = emptyList(),
    @SerialName("definition") val definition: Definition? = null,
    @SerialName("examples") val examples: List<Definition> = emptyList()
) {

    @Serializable
    data class Translation(
        @SerialName("text") val text: String,
        @SerialName("note") val note: String? = null
    )

    @Serializable
    data class Image(
        @SerialName("url") val url: String
    )

    @Serializable
    data class Definition(
        @SerialName("text") val text: String,
        @SerialName("soundUrl") val soundUrl: String
    )

}