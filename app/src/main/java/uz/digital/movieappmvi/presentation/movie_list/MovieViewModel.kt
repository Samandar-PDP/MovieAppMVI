package uz.digital.movieappmvi.presentation.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import uz.digital.movieappmvi.network.RetrofitInstance
import uz.digital.movieappmvi.repository.MovieRepository

class MovieViewModel: ViewModel() {
    private val repository = MovieRepository(RetrofitInstance.getApiService())

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState.Init)
    val state: StateFlow<MainState> get() = _state

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when(it) {
                    is MainIntent.FetchUsers -> callMovies(it.page)
                }
            }
        }
    }

    private fun callMovies(page: Int) {
        viewModelScope.launch {
            _state.value = MainState.Loading
            delay(500L)
            try {
                 val response = repository.getMovies()
                if (response.isSuccessful) {
                    _state.value = MainState.Success(response.body()?.results!!)
                }
            } catch (e: Exception) {
                _state.value = MainState.Error(e.message!!)
            }
        }
    }
}