package uz.digital.movieappmvi.presentation.movie_list

import uz.digital.movieappmvi.model.ResultMovie

sealed class MainState {
    object Init: MainState()
    object Loading: MainState()
    data class Error(val error: String): MainState()
    data class Success(val movies: List<ResultMovie>): MainState()
}