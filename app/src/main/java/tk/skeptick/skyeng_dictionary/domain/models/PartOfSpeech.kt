package tk.skeptick.skyeng_dictionary.domain.models

enum class PartOfSpeech(val code: String) {
    NOUN("n"),
    VERB("v"),
    ADJECTIVE("j"),
    ADVERB("r"),
    PREPOSITION("prp"),
    PRONOUN("prn"),
    CARDINAL_NUMBER("crd"),
    CONJUNCTION("cjc"),
    INTERJECTION("exc"),
    ARTICLE("det"),
    ABBREVIATION("abb"),
    PARTICLE("x"),
    ORDINAL_NUMBER("ord"),
    MODAL_VERB("md"),
    PHRASE("ph"),
    IDIOM("phi")
}