package ru.kirea.filmswithpagination.windows.filmlist

import kotlinx.coroutines.launch
import ru.kirea.filmswithpagination.api.entities.AppMovie
import ru.kirea.filmswithpagination.api.entities.ResponseData
import ru.kirea.filmswithpagination.api.repository.FilmsRepository
import ru.kirea.filmswithpagination.states.BaseState
import ru.kirea.filmswithpagination.states.FilmListState
import ru.kirea.filmswithpagination.windows.BaseViewModel

class FilmListViewModel(private val filmsRepository: FilmsRepository): BaseViewModel<BaseState>() {

    init {
        coroutineScope.launch {
            filmsRepository.getConfiguration()
        }
    }

    /** Получить список фильмов */
    fun getFilms() {
        liveData.postValue(BaseState.Loading)

        coroutineScope.launch {
            filmsRepository.getFilms(object: ResponseData<List<AppMovie>> {
                override fun success(result: List<AppMovie>?) {
                    liveData.postValue(FilmListState.Success(result))
                }

                override fun error(result: String?) {
                    liveData.postValue(BaseState.Error(result))
                }
            })
        }
    }
}