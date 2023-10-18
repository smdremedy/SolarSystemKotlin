package pl.szkoleniaandroid.solarsystem.api

import pl.szkoleniaandroid.solarsystem.api.model.SolarObjectDetailsJson
import pl.szkoleniaandroid.solarsystem.api.model.SolarObjectJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface SolarSystemApi {
    @Headers("Delay: 1000")
    @GET("/planets")
    suspend fun getPlanets(): Response<List<SolarObjectJson>>

    @GET("/others")
    suspend fun getOthers(): Response<List<SolarObjectJson>>

    @GET("/objects_with_moons")
    suspend fun getObjectsWithMoons(): Response<List<SolarObjectJson>>

    @GET("/moons/{id}")
    suspend fun getMoon(@Path("id") orbitId: String): Response<List<SolarObjectJson>>

    @GET("/objects/{id}")
    suspend fun getObject(@Path("id") objectId: String): Response<SolarObjectDetailsJson>
}
