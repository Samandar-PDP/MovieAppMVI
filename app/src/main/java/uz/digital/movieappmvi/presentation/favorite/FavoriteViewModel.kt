package uz.digital.movieappmvi.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import uz.digital.movieappmvi.repository.LocalRepository

class FavoriteViewModel(
    private val repository: LocalRepository
): ViewModel() {
    private val _state: MutableStateFlow<FavoriteState> = MutableStateFlow(FavoriteState.Loading)
    val state: StateFlow<FavoriteState> get() = _state

    val channel = Channel<FavoriteIntent>(Channel.UNLIMITED)

    init {
        getHandles()
    }

    private fun getHandles() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect {
                when(it) {
                    FavoriteIntent.OnFragmentLaunched -> {
                        _state.value = FavoriteState.Loading
                        delay(500L)
                        repository.getAllMovies().collect { list ->
                            _state.value = FavoriteState.Success(list)
                        }
                    }
                }
            }
        }
    }
}