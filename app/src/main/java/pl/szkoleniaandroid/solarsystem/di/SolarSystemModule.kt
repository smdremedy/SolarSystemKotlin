package pl.szkoleniaandroid.solarsystem.di

import coil.ImageLoader
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.szkoleniaandroid.solarsystem.api.SolarSystemApi
import pl.szkoleniaandroid.solarsystem.mock.MockInterceptor
import pl.szkoleniaandroid.solarsystem.sandbox.SandboxViewModel
import pl.szkoleniaandroid.solarsystem.screens.details.ObjectDetailsViewModel
import pl.szkoleniaandroid.solarsystem.screens.moons.MoonsViewModel
import pl.szkoleniaandroid.solarsystem.screens.objectswithmoons.ObjectsWithMoonsViewModel
import pl.szkoleniaandroid.solarsystem.screens.others.OthersViewModel
import pl.szkoleniaandroid.solarsystem.screens.planets.PlanetsViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val solarSystemModule = module {

    viewModel {
        PlanetsViewModel(api = get())
    }

    viewModel {
        OthersViewModel(api = get())
    }

    viewModel {
        ObjectsWithMoonsViewModel(solarSystemApi = get())
    }

    viewModel { params ->
        MoonsViewModel(api = get(), orbitId = params.get())
    }

    viewModel { params ->
        ObjectDetailsViewModel(solarSystemApi = get(), params.get())
    }

    viewModel {
        SandboxViewModel(solarSystemApi = get())
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(MockInterceptor(androidContext()))
            .build()
    }

    single<SolarSystemApi> {
        val retrofit = Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl("https://localhost")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(SolarSystemApi::class.java)
    }

    single {
        ImageLoader.Builder(androidContext())
            .okHttpClient(get<OkHttpClient>())
            .build()
    }
}