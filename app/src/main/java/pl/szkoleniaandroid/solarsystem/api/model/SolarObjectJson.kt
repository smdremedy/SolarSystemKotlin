package pl.szkoleniaandroid.solarsystem.api.model

data class SolarObjectJson(
    val id: String,
    val name: String,
    val objectType: ObjectType = ObjectType.PLANET,
    val orbitsId: String? = null,
    val videoId: String? = null
) {
    val imageUrl: String
        get() = "https://localhost/img/${id}.jpg"
}