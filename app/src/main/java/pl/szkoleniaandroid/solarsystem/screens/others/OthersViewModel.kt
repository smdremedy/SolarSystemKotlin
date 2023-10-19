package pl.szkoleniaandroid.solarsystem.screens.others

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.szkoleniaandroid.solarsystem.api.SolarSystemApi
import pl.szkoleniaandroid.solarsystem.common.SolarObjectsViewModel

class OthersViewModel(
    api: SolarSystemApi,
    private val objectsRepository: OtherObjectsRepository
) : SolarObjectsViewModel(api) {
    override fun fetchObjects() {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            objectsRepository.othersFlow.collect {
                _uiState.emit(UiState.Success(it))
            }
        }
    }
}
