package ru.kirea.filmswithpagination.windows.filminfo

import kotlinx.coroutines.launch
import ru.kirea.filmswithpagination.api.entities.AppMovie
import ru.kirea.filmswithpagination.api.entities.ResponseData
import ru.kirea.filmswithpagination.api.repository.FilmsRepository
import ru.kirea.filmswithpagination.states.BaseState
import ru.kirea.filmswithpagination.states.FilmInfoState
import ru.kirea.filmswithpagination.windows.BaseViewModel

class FilmInfoViewModel(private val filmsRepository: FilmsRepository): BaseViewModel<BaseState>() {

    /** Получить информацию по фильму */
    fun getFilm(filmId: Long) {
        liveData.postValue(BaseState.Loading)

        coroutineScope.launch {
            filmsRepository.getFilm(filmId, object: ResponseData<AppMovie> {
                override fun success(result: AppMovie?) {
                    liveData.postValue(FilmInfoState.Success(result))
                }

                override fun error(result: String?) {
                    liveData.postValue(BaseState.Error(result))
                }
            })
        }
    }
}