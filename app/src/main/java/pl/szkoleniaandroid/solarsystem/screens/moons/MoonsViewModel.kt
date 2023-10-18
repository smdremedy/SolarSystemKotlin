package pl.szkoleniaandroid.solarsystem.screens.moons

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.szkoleniaandroid.solarsystem.api.SolarSystemApi
import pl.szkoleniaandroid.solarsystem.common.SolarObjectsViewModel
import pl.szkoleniaandroid.solarsystem.common.toSolarObject

class MoonsViewModel(api: SolarSystemApi, private val orbitId: String) : SolarObjectsViewModel(
    api
) {
    override fun fetchObjects() {
        viewModelScope.launch {
            val response = solarSystemApi.getMoon(orbitId)
            if (response.isSuccessful) {
                _uiState.emit(UiState.Success(response.body()!!.map { it.toSolarObject() }))
            }
        }
    }
}