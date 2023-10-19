package pl.szkoleniaandroid.solarsystem.screens.others

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.szkoleniaandroid.solarsystem.api.SolarSystemApi
import pl.szkoleniaandroid.solarsystem.common.toSolarObject
import pl.szkoleniaandroid.solarsystem.db.SolarObjectDao
import pl.szkoleniaandroid.solarsystem.db.SolarObjectDto
import pl.szkoleniaandroid.solarsystem.domain.SolarObject

interface OtherObjectsRepository {
    val othersFlow: Flow<List<SolarObject>>
}

class OtherObjectsRepositoryImpl(
    private val solarObjectDao: SolarObjectDao,
    private val solarSystemApi: SolarSystemApi
) : OtherObjectsRepository {
    override val othersFlow: Flow<List<SolarObject>> = flow {
        val dtos = solarObjectDao.getOthers()
        emit(dtos.map {
            SolarObject(
                id = it.id,
                name = it.name,
                imageUrl = "https://localhost/img/${it.id}.jpg",
                video = it.videoId
            )
        })

        val jsons = solarSystemApi.getOthers().body()!!
        jsons.forEach {
            solarObjectDao.insert(
                SolarObjectDto(
                    id = it.id,
                    name = it.name,
                    objectType = it.objectType,
                    orbitsId = it.orbitsId,
                    videoId = it.videoId
                )
            )
        }
        emit(
            jsons.map {
                it.toSolarObject()
            }
        )
    }
}