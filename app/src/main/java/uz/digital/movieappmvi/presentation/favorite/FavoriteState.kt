package uz.digital.movieappmvi.presentation.favorite

import uz.digital.movieappmvi.model.MovieEntity

sealed class FavoriteState {
    object Loading: FavoriteState()
    data class Success(val movieEntities: List<MovieEntity>): FavoriteState()
}