package ru.kirea.filmswithpagination.states

import ru.kirea.filmswithpagination.api.entities.AppMovie

sealed class FilmListState: BaseState {
    data class Success(val films: List<AppMovie>?): FilmListState()
}