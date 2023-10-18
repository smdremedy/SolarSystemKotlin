package pl.szkoleniaandroid.solarsystem.screens.objectswithmoons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.szkoleniaandroid.solarsystem.api.SolarSystemApi
import pl.szkoleniaandroid.solarsystem.common.SolarObjectsViewModel
import pl.szkoleniaandroid.solarsystem.common.toSolarObject

class ObjectsWithMoonsViewModel(
    private val solarSystemApi: SolarSystemApi
) : ViewModel() {
    protected val _uiState =
        MutableStateFlow<SolarObjectsViewModel.UiState>(SolarObjectsViewModel.UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<SolarObjectsViewModel.Effect>()
    val effect = _effect.asSharedFlow()

    fun fetchObjects() {
        viewModelScope.launch {
            val objectsWithMoons = solarSystemApi.getObjectsWithMoons()
            _uiState.emit(
                SolarObjectsViewModel.UiState.Success(
                    objects = objectsWithMoons.body()!!.map { it.toSolarObject() })
            )
        }
    }
}