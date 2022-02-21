package ru.kirea.filmswithpagination.api.repository

import ru.kirea.filmswithpagination.api.entities.AppMovie
import ru.kirea.filmswithpagination.api.entities.ResponseData

interface FilmsRepository {
    /** Получить список фильмов */
    suspend fun getFilms(responseData: ResponseData<List<AppMovie>>)

    /** Получить данные по фильму */
    suspend fun getFilm(filmId: Long, responseData: ResponseData<AppMovie>)

    /** Получить конфигурацию по фильмам */
    suspend fun getConfiguration()
}