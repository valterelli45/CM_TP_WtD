package intro.android.cm_tp_wtd.models

import java.util.Date

data class Location(
    val name: String,
    val imageUrl: String,
    val description: String,
    val rating: Float,
    val category: String,
    val date: String
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "imageUrl" to imageUrl,
            "description" to description,
            "rating" to rating,
            "category" to category,
            "date" to date
        )
    }
}