package uz.digital.movieappmvi.repository

import uz.digital.movieappmvi.network.ApiService

class RemoteRepository(
    private val apiService: ApiService
) {
    suspend fun getMovies() = apiService.getMovies()
    suspend fun searchMovie(query: String) = apiService.searchMovie(query = query)
}