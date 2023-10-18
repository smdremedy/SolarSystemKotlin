package pl.szkoleniaandroid.solarsystem.sandbox

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.szkoleniaandroid.solarsystem.api.SolarSystemApi

class SandboxViewModel(private val solarSystemApi: SolarSystemApi) : ViewModel() {
    val uiState = MutableLiveData<UiState>(UiState.Success(message = ""))

    fun doAction() {
        viewModelScope.launch {
            // DO STUFF HERE
        }
    }

    sealed interface UiState {
        data object Loading : UiState
        data class Success(val message: String) : UiState
        data class Error(val errorMessage: String) : UiState
    }

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
    }
}