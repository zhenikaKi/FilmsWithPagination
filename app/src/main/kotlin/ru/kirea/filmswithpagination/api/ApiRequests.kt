package ru.kirea.filmswithpagination.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kirea.filmswithpagination.BuildConfig
import ru.kirea.filmswithpagination.api.entities.Configuration
import ru.kirea.filmswithpagination.api.entities.Movie
import ru.kirea.filmswithpagination.api.entities.MovieFined

interface ApiRequests {
    /** Получить список фильмов по определенному фильтру */
    @GET("discover/movie")
    fun getFilms(
            @Query("primary_release_date.gte") releaseDateFrom: String? = null,
            @Query("primary_release_date") releaseDateTo: String? = null,
            @Query("primary_release_year") releaseYear: Int? = null,
            @Query("sort_by") sorted: String? = null,
            @Query("include_adult") includeAdult: Boolean = false,
            @Query("language") language: String = "ru-RU",
            @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<MovieFined>

    /** Получить информацию по фильму */
    @GET("movie/{filmId}")
    //получить список фильмов по определенному фильтру
    fun getFilm(
            @Path("filmId") filmId: Long?,
            @Query("include_adult") includeAdult: Boolean = false,
            @Query("language") language: String = "ru-RU",
            @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<Movie>

    /** Получить настройки с сервера */
    @GET("configuration")
    fun getConfiguration(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<Configuration>
}