package pl.szkoleniaandroid.solarsystem.domain

data class SolarObjectDetails(
    val description: String,
    val moons: List<SolarObject>
)