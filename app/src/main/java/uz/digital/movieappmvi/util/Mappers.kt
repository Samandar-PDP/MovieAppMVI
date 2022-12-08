package uz.digital.movieappmvi.util

import uz.digital.movieappmvi.model.MovieEntity
import uz.digital.movieappmvi.model.ResultMovie

fun ResultMovie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        lan = original_language,
        title = original_title,
        backImage = backdrop_path,
        posterImage = poster_path,
        overView = overview,
        popularity = popularity,
        voteAverage = vote_average,
        voteCount = vote_count
    )
}
fun MovieEntity.toResultMovie(): ResultMovie {
    return ResultMovie(
        id = id,
        backdrop_path = backImage,
        original_language = lan,
        original_title = title,
        overview = overView,
        popularity = popularity,
        poster_path = posterImage,
        vote_average = voteAverage,
        vote_count = voteCount
    )
}