package pl.szkoleniaandroid.solarsystem

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import pl.szkoleniaandroid.solarsystem.di.solarSystemModule

class SolarSystemApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SolarSystemApp)
            modules(solarSystemModule)
        }
    }
}

