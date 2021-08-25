package tk.skeptick.skyeng_dictionary.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PartOfSpeech {
    @SerialName("n") NOUN,
    @SerialName("v") VERB,
    @SerialName("j") ADJECTIVE,
    @SerialName("r") ADVERB,
    @SerialName("prp") PREPOSITION,
    @SerialName("prn") PRONOUN,
    @SerialName("crd") CARDINAL_NUMBER,
    @SerialName("cjc") CONJUNCTION,
    @SerialName("exc") INTERJECTION,
    @SerialName("det") ARTICLE,
    @SerialName("abb") ABBREVIATION,
    @SerialName("x") PARTICLE,
    @SerialName("ord") ORDINAL_NUMBER,
    @SerialName("md") MODAL_VERB,
    @SerialName("ph") PHRASE,
    @SerialName("phi") IDIOM
}