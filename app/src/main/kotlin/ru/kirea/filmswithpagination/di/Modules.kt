package ru.kirea.filmswithpagination.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.kirea.filmswithpagination.MainActivity
import ru.kirea.filmswithpagination.api.ApiRequests
import ru.kirea.filmswithpagination.api.RetrofitHelper
import ru.kirea.filmswithpagination.api.repository.FilmsRepository
import ru.kirea.filmswithpagination.api.repository.FilmsRepositoryApi
import ru.kirea.filmswithpagination.windows.filminfo.FilmInfoFragment
import ru.kirea.filmswithpagination.windows.filminfo.FilmInfoViewModel
import ru.kirea.filmswithpagination.windows.filmlist.FilmListFragment
import ru.kirea.filmswithpagination.windows.filmlist.FilmListViewModel

object Modules {
    //модуль, содержимое которого должно быть во всем приложении
    val application = module {
        //навигация
        single<Cicerone<Router>>(qualifier = named(Scopes.CICERONE)) {
            Cicerone.create(Router())
        }
        single<NavigatorHolder>(qualifier = named(Scopes.NAVIGATOR)) {
            get<Cicerone<Router>>(qualifier = named(Scopes.CICERONE)).getNavigatorHolder()
        }
        single<Router>(qualifier = named(Scopes.ROUTER)) {
            get<Cicerone<Router>>(qualifier = named(Scopes.CICERONE)).router
        }

        //работа с api
        single<Retrofit>(qualifier = named(Scopes.RETROFIT)) {
            RetrofitHelper.create()
        }
        single<ApiRequests>(qualifier = named(Scopes.API)) {
            get<Retrofit>(qualifier = named(Scopes.RETROFIT)).create(ApiRequests::class.java)
        }
        single<FilmsRepository>(qualifier = named(Scopes.FILMS_REPOSITORY)) {
            FilmsRepositoryApi(get(qualifier = named(Scopes.API)))
        }
    }

    //модуль основной активити
    val appActivity = module {
        scope<MainActivity> {
        }
    }

    //модуль списка фильмов
    val filmListWindow = module {
        scope<FilmListFragment> {
            viewModel(qualifier = named(Scopes.FILM_LIST_VIEW_MODEL)) {
                FilmListViewModel(get(qualifier = named(Scopes.FILMS_REPOSITORY)))
            }
        }
    }

    //модуль одной страницы с фильмами
    val pageWithFilmsWindow = module {
        scope<FilmListFragment> {
        }
    }

    //модуль окна с описанием фильма
    val filmInfoWindow = module {
        scope<FilmInfoFragment> {
            viewModel(qualifier = named(Scopes.FILM_INFO_VIEW_MODEL)) {
                FilmInfoViewModel(get(qualifier = named(Scopes.FILMS_REPOSITORY)))
            }
        }
    }
}
