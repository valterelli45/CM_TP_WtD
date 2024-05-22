package intro.android.cm_tp_wtd.models

data class Location(
    val name: String,
    val imageUrl: String
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "imageUrl" to imageUrl
        )
    }
}