package pl.szkoleniaandroid.solarsystem.screens.planets

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.szkoleniaandroid.solarsystem.api.SolarSystemApi
import pl.szkoleniaandroid.solarsystem.common.SolarObjectsViewModel
import pl.szkoleniaandroid.solarsystem.common.toSolarObject

class PlanetsViewModel(api: SolarSystemApi) :
    SolarObjectsViewModel(api) {

    override fun fetchObjects() {
        viewModelScope.launch(exceptionHandler) {
            val response = solarSystemApi.getPlanets()
            if (response.isSuccessful) {
                _uiState.emit(UiState.Success(response.body()!!.map { it.toSolarObject() }))
            } else {
                _uiState.emit(UiState.Error("Can't fetch planets!"))
            }
        }
    }
}