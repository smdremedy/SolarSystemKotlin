package pl.szkoleniaandroid.solarsystem.screens.others

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.szkoleniaandroid.solarsystem.api.SolarSystemApi
import pl.szkoleniaandroid.solarsystem.common.SolarObjectsViewModel
import pl.szkoleniaandroid.solarsystem.common.toSolarObject

class OthersViewModel(api: SolarSystemApi) :
    SolarObjectsViewModel(api) {
    override fun fetchObjects() {
        viewModelScope.launch {

            val response = solarSystemApi.getOthers()
            if (response.isSuccessful) {
                _uiState.emit(UiState.Success(response.body()!!.map { it.toSolarObject() }))
            }
        }
    }
}