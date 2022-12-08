package uz.digital.movieappmvi.repository

import uz.digital.movieappmvi.database.MovieDao
import uz.digital.movieappmvi.model.MovieEntity

class LocalRepository(
    private val dao: MovieDao
) {
    suspend fun saveMovie(movieEntity: MovieEntity) = dao.saveMovie(movieEntity)
    fun getAllMovies() = dao.getAllMovies()
    suspend fun deleteMovie(movieEntity: MovieEntity) = dao.deleteMovie(movieEntity)
}