package uz.digital.movieappmvi.presentation.movie_list

sealed class MainIntent {
    data class FetchUsers(val page: Int): MainIntent()
}