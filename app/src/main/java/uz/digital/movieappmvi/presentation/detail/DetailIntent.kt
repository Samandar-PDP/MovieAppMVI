package uz.digital.movieappmvi.presentation.detail

import uz.digital.movieappmvi.model.MovieEntity

sealed class DetailIntent {
    data class OnSaveClicked(val movieEntity: MovieEntity): DetailIntent()
    data class OnDeleteClicked(val movieEntity: MovieEntity): DetailIntent()
}