package uz.digital.movieappmvi.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.digital.movieappmvi.model.Movie
import uz.digital.movieappmvi.util.Constants

interface ApiService {
    @GET("3/movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("page") page: Int = 1
    ): Response<Movie>

    @GET("3/search/movie")
    suspend fun searchMovie(
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Response<Movie>
}