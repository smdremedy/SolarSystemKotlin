package pl.szkoleniaandroid.solarsystem.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.szkoleniaandroid.solarsystem.api.SolarSystemApi
import pl.szkoleniaandroid.solarsystem.domain.SolarObject
import java.io.IOException

abstract class SolarObjectsViewModel(
    protected val solarSystemApi: SolarSystemApi
) : ViewModel() {
    protected val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    val effect = _effect.asSharedFlow()

    protected val exceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            viewModelScope.launch {
                _uiState.emit(
                    when (throwable) {
                        is IOException -> UiState.Error("Connection failed!")
                        else -> UiState.Error("Something went wrong!")
                    }
                )
            }

        }

    abstract fun fetchObjects()

    fun showObject(clickedObject: SolarObject) {
        viewModelScope.launch {
            _effect.emit(Effect.ShowObject(clickedObject))
        }
    }

    sealed interface UiState {
        data object Loading : UiState
        data class Success(val objects: List<SolarObject>) : UiState
        data class Error(val message: String) : UiState
    }

    sealed interface Effect {
        data class ShowObject(val objectToShow: SolarObject) : Effect
    }
}