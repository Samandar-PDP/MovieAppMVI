package uz.digital.movieappmvi.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import uz.digital.movieappmvi.network.RetrofitInstance
import uz.digital.movieappmvi.repository.RemoteRepository

class SearchViewModel: ViewModel() {
    val repository = RemoteRepository(RetrofitInstance.getApiService())
    private val _state: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.Loading)
    val state get() = _state.asStateFlow()
    val channel = Channel<SearchIntent>(Channel.UNLIMITED)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect {
                when(it) {
                    is SearchIntent.OnSearched -> searchMovies(it.query)
                }
            }
        }
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            _state.value = SearchState.Loading
            delay(500L)
            try {
                val response = repository.searchMovie(query)
                if (response.isSuccessful) {
                    _state.value = SearchState.Success(response.body()?.results!!)
                }
            } catch (e: Exception) {
                _state.value = SearchState.Error(e.message!!)
            }
        }
    }
}