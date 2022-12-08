package uz.digital.movieappmvi.presentation.search

import uz.digital.movieappmvi.model.ResultMovie

sealed class SearchState {
    object Loading: SearchState()
    data class Error(val error: String): SearchState()
    data class Success(val success: List<ResultMovie>): SearchState()
}