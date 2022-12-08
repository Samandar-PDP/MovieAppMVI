package uz.digital.movieappmvi.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import uz.digital.movieappmvi.repository.LocalRepository

class DetailViewModel(
    private val repository: LocalRepository
): ViewModel() {

    val channel = Channel<DetailIntent>(Channel.UNLIMITED)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect {
                when(it) {
                    is DetailIntent.OnSaveClicked -> {
                        repository.saveMovie(it.movieEntity)
                    }
                    is DetailIntent.OnDeleteClicked -> {
                        repository.deleteMovie(it.movieEntity)
                    }
                }
            }
        }
    }
}