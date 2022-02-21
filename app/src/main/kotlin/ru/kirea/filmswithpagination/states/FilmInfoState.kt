package ru.kirea.filmswithpagination.states

import ru.kirea.filmswithpagination.api.entities.AppMovie

sealed class FilmInfoState: BaseState {
    data class Success(val film: AppMovie?): FilmInfoState()
}