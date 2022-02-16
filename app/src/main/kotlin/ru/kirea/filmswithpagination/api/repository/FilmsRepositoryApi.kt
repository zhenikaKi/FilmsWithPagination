package ru.kirea.filmswithpagination.api.repository

import retrofit2.Response
import ru.kirea.filmswithpagination.api.ApiRequests
import ru.kirea.filmswithpagination.api.ApiSetting
import ru.kirea.filmswithpagination.api.RetrofitResponse
import ru.kirea.filmswithpagination.api.entities.*

class FilmsRepositoryApi(private val apiRequests: ApiRequests): FilmsRepository {

    /** Получить список фильмов */
    override suspend fun getFilms(responseData: ResponseData<List<AppMovie>>) {
        try {
            val movieFined: Response<MovieFined> =
                apiRequests.getFilms(includeAdult = true, sorted = "popularity.desc").execute()

            //конвентируем сущность фильма из API к сущности приложения
            val result: MovieFined? = RetrofitResponse(movieFined).getResult()
            result?.let {
                val films = it.movies
                    .map { m -> AppMovie(m) }
                    .toCollection(mutableListOf())
                responseData.success(films)
            } ?: responseData.success(null)
        }
        catch (e: Exception) {
            responseData.error(e.message)
        }
    }

    /** Получить данные по фильму */
    override suspend fun getFilm(filmId: Long, responseData: ResponseData<AppMovie>) {
        try {
            val response: Response<Movie> = apiRequests.getFilm(filmId = filmId).execute()

            //конвентируем сущность фильма из API к сущности приложения
            val result: Movie? = RetrofitResponse(response).getResult()
            result ?. let {
                responseData.success(AppMovie(it))
            } ?: responseData.success(null)
        }
        catch (e: Exception) {
            responseData.error(e.message)
        }
    }

    /** Получить конфигурацию по фильмам */
    override suspend fun getConfiguration() {
        try {
            val response: Response<Configuration> = apiRequests.getConfiguration().execute()
            val result: Configuration? = RetrofitResponse(response).getResult()
            ApiSetting.secureBaseUrl = null
            ApiSetting.posterSize = null
            result ?. let {
                ApiSetting.secureBaseUrl = result.images.secureBaseUrl
                //найдем приемлемый размер постера
                ApiSetting.priorPosterSizes.find {
                    result.images.posterSizes.indexOf(it) >= 0
                } ?. let {
                    ApiSetting.posterSize = it
                }
            }
        }
        catch (e: Exception) {
            ApiSetting.secureBaseUrl = null
            ApiSetting.posterSize = null
        }
    }
}