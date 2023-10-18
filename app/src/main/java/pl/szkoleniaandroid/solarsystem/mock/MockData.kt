package pl.szkoleniaandroid.solarsystem.mock

import pl.szkoleniaandroid.solarsystem.api.model.ObjectType
import pl.szkoleniaandroid.solarsystem.api.model.SolarObjectJson

val objects = listOf(
    SolarObjectJson(
        id = "mercury",
        name = "Mercury",
        videoId = "mCzchPx3yF8"
    ),
    SolarObjectJson(
        id = "venus",
        name = "Venus",
        videoId = "ZFUgy3crCYY"
    ),
    SolarObjectJson(
        id = "earth",
        name = "Earth",
        videoId = "w-9gDALvMF4",
    ),
    SolarObjectJson(
        id = "moon",
        name = "Moon",
        objectType = ObjectType.MOON,
        orbitsId = "earth",
        videoId = "mCzchPx3yF8"
    ),
    SolarObjectJson(
        id = "mars",
        name = "Mars",
        videoId = "I-88YWx71gE",
    ),
    SolarObjectJson(
        id = "deimos",
        name = "Deimos",
        objectType = ObjectType.MOON,
        orbitsId = "mars"
    ),
    SolarObjectJson(
        id = "phobos",
        name = "Phobos",
        objectType = ObjectType.MOON,
        orbitsId = "mars"
    ),
    SolarObjectJson(
        id = "jupiter",
        name = "Jupiter",
        videoId = "Xwn8fQSW7-8",
    ),
    SolarObjectJson(
        id = "ganymede",
        name = "Ganymede",
        objectType = ObjectType.MOON,
        orbitsId = "jupiter",
        videoId = "HaFaf7vbgpE"
    ),
    SolarObjectJson(
        id = "callisto",
        name = "Callisto",
        objectType = ObjectType.MOON,
        orbitsId = "jupiter",
        videoId = "HaFaf7vbgpE"
    ),
    SolarObjectJson(
        id = "io",
        name = "Io",
        objectType = ObjectType.MOON,
        orbitsId = "jupiter",
        videoId = "HaFaf7vbgpE"
    ),
    SolarObjectJson(
        id = "europa",
        name = "Europa",
        objectType = ObjectType.MOON,
        orbitsId = "jupiter",
        videoId = "HaFaf7vbgpE"
    ),
    SolarObjectJson(
        id = "saturn",
        name = "Saturn",
        videoId = "E8GNde5nCSg",
    ),
    SolarObjectJson(
        id = "titan",
        name = "Titan",
        objectType = ObjectType.MOON,
        orbitsId = "saturn",
        videoId = "D2QjUtmhEFo"
    ),
    SolarObjectJson(
        id = "rhea",
        name = "Rhea",
        objectType = ObjectType.MOON,
        orbitsId = "saturn",
    ),
    SolarObjectJson(
        id = "iapetus",
        name = "Iapetus",
        objectType = ObjectType.MOON,
        orbitsId = "saturn",
    ),
    SolarObjectJson(
        id = "dione",
        name = "Dione",
        objectType = ObjectType.MOON,
        orbitsId = "saturn",
    ),
    SolarObjectJson(
        id = "tethys",
        name = "Tethys",
        objectType = ObjectType.MOON,
        orbitsId = "saturn",
    ),
    SolarObjectJson(
        id = "enceladus",
        name = "Enceladus",
        objectType = ObjectType.MOON,
        orbitsId = "saturn",
    ),
    SolarObjectJson(
        id = "mimas",
        name = "Mimas",
        objectType = ObjectType.MOON,
        orbitsId = "saturn",
    ),
    SolarObjectJson(
        id = "hyperion",
        name = "Hyperion",
        objectType = ObjectType.MOON,
        orbitsId = "saturn",
    ),
    SolarObjectJson(
        id = "phoebe",
        name = "Phoebe",
        objectType = ObjectType.MOON,
        orbitsId = "saturn",
    ),
    SolarObjectJson(
        id = "janus",
        name = "Janus",
        objectType = ObjectType.MOON,
        orbitsId = "saturn",
    ),
    SolarObjectJson(
        id = "epimetheus",
        name = "Epimetheus",
        objectType = ObjectType.MOON,
        orbitsId = "saturn",
    ),
    SolarObjectJson(
        id = "prometheus",
        name = "Prometheus",
        objectType = ObjectType.MOON,
        orbitsId = "saturn",
    ),
    SolarObjectJson(
        id = "pandora",
        name = "Pandora",
        objectType = ObjectType.MOON,
        orbitsId = "saturn",
    ),
    SolarObjectJson(
        id = "helene",
        name = "Helene",
        objectType = ObjectType.MOON,
        orbitsId = "saturn",
    ),
    SolarObjectJson(
        id = "uranus",
        name = "Uranus",
        videoId = "1hIwD17Crko",
    ),
    SolarObjectJson(
        id = "titania",
        name = "Titania",
        objectType = ObjectType.MOON,
        orbitsId = "uranus"
    ),
    SolarObjectJson(
        id = "oberon",
        name = "Oberon",
        objectType = ObjectType.MOON,
        orbitsId = "uranus"
    ),
    SolarObjectJson(
        id = "umbriel",
        name = "Umbriel",
        objectType = ObjectType.MOON,
        orbitsId = "uranus"
    ),
    SolarObjectJson(
        id = "ariel",
        name = "Ariel",
        objectType = ObjectType.MOON,
        orbitsId = "uranus"
    ),
    SolarObjectJson(
        id = "miranda",
        name = "Miranda",
        objectType = ObjectType.MOON,
        orbitsId = "uranus"
    ),
    SolarObjectJson(
        id = "neptune",
        name = "Neptune",
        videoId = "1hIwD17Crko",
    ),
    SolarObjectJson(
        id = "triton",
        name = "Triton",
        objectType = ObjectType.MOON,
        orbitsId = "neptune"
    ),
    SolarObjectJson(
        id = "proteus",
        name = "Proteus",
        objectType = ObjectType.MOON,
        orbitsId = "neptune"
    ),
    SolarObjectJson(
        id = "sun",
        name = "Sun",
        objectType = ObjectType.OTHER,
        videoId = "b22HKFMIfWo"
    ),
    SolarObjectJson(
        id = "pluto",
        name = "Pluto",
        objectType = ObjectType.OTHER
    ),
    SolarObjectJson(
        id = "charon",
        name = "Charon",
        objectType = ObjectType.MOON,
        orbitsId = "pluto"
    ),
    SolarObjectJson(
        id = "ceres",
        name = "Ceres",
        objectType = ObjectType.OTHER
    ),
    SolarObjectJson(
        id = "vesta",
        name = "Vesta",
        objectType = ObjectType.OTHER
    ),
    SolarObjectJson(
        id = "ida",
        name = "Ida",
        objectType = ObjectType.OTHER
    )
)