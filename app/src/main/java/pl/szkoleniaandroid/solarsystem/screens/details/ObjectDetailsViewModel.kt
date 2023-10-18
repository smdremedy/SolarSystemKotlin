package pl.szkoleniaandroid.solarsystem.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.szkoleniaandroid.solarsystem.api.SolarSystemApi
import pl.szkoleniaandroid.solarsystem.api.model.SolarObjectDetailsJson
import pl.szkoleniaandroid.solarsystem.api.model.SolarObjectJson
import pl.szkoleniaandroid.solarsystem.common.toSolarObject
import pl.szkoleniaandroid.solarsystem.domain.SolarObject
import pl.szkoleniaandroid.solarsystem.domain.SolarObjectDetails
import retrofit2.Response

class ObjectDetailsViewModel(
    private val solarSystemApi: SolarSystemApi,
    private val solarObject: SolarObject
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private suspend fun buildState(
        detailsResponse: Response<SolarObjectDetailsJson>,
        moonsResponse: Response<List<SolarObjectJson>>
    ) {
        if (detailsResponse.isSuccessful) {
            val detailsJson = detailsResponse.body()!!

            _uiState.emit(UiState.Success(
                SolarObjectDetails(
                    description = detailsJson.description,
                    moons = if (moonsResponse.isSuccessful) {
                        moonsResponse.body()!!.map {
                            it.toSolarObject()
                        }
                    } else {
                        emptyList()
                    }
                )
            ))
        } else {
            _uiState.emit(UiState.Error("Error: loading details failed"))
        }
    }

    fun loadData() {
        viewModelScope.launch {
            val detailsResponse = async {
                solarSystemApi.getObject(solarObject.id)
            }
            val moonsResponse = async {
                solarSystemApi.getMoon(solarObject.id)
            }

            buildState(detailsResponse.await(), moonsResponse.await())
        }

    }

    sealed interface UiState {
        data object Loading : UiState
        data class Success(val detailsJson: SolarObjectDetails) : UiState
        data class Error(val message: String) : UiState
    }
}