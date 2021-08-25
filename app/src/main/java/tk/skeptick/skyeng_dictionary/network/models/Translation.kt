package tk.skeptick.skyeng_dictionary.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Translation(
    @SerialName("text") val text: String,
    @SerialName("note") val note: String? = null
)