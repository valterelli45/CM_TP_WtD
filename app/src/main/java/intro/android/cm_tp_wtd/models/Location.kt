package intro.android.cm_tp_wtd.models

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

    companion object {
        fun fromMap(map: Map<String, Any>): Location {
            return Location(
                name = map["name"] as String,
                imageUrl = map["imageUrl"] as String,
                description = map["description"] as String,
                rating = (map["rating"] as Double).toFloat(),
                category = map["category"] as String,
                date = map["date"] as String
            )
        }
    }
}
