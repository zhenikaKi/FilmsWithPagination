package ru.kirea.filmswithpagination.windows.pagewithfilms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.github.terrakok.cicerone.Router
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent
import ru.kirea.filmswithpagination.R
import ru.kirea.filmswithpagination.api.ApiSetting
import ru.kirea.filmswithpagination.api.entities.AppMovie
import ru.kirea.filmswithpagination.databinding.ItemFilmBinding
import ru.kirea.filmswithpagination.databinding.PageWithFilmsFragmentBinding
import ru.kirea.filmswithpagination.di.Scopes
import ru.kirea.filmswithpagination.navigation.FilmInfoScreen
import ru.kirea.filmswithpagination.windows.filmlist.FilmListFragment

class PageWithFilmsFragment: Fragment() {
    companion object {
        const val COUNT_ON_PAGE = 4 //количество фильмов на странице

        fun newInstance(pageMovies: List<AppMovie>): PageWithFilmsFragment {
            val fragment = PageWithFilmsFragment()
            fragment.pageMovies = pageMovies
            return fragment
        }
    }

    private val scope = KoinJavaComponent.getKoin().createScope<FilmListFragment>()
    private val router: Router = scope.get(qualifier = named(Scopes.ROUTER))
    private var _binding: PageWithFilmsFragmentBinding? = null
    private val binding
        get() = _binding

    private var pageMovies: List<AppMovie>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = PageWithFilmsFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //запонляем каждую карточку данными
        binding?.let {
            pageMovies?.forEachIndexed { index, appMovie ->
                val item = when (index) {
                    1 -> it.film2
                    2 -> it.film3
                    3 -> it.film4
                    else -> it.film1
                }
                setFilmData(item, appMovie)
            }
        }
    }

    private fun setFilmData(item: ItemFilmBinding, appMovie: AppMovie) {
        item.filmTitleId.text = appMovie.title
        item.filmDescriptionId.text = appMovie.description

        //загрузим изображение
        item.filmImageId.setBackgroundResource(R.drawable.no_image)
        appMovie.imageUrl ?. let {
            item.filmImageId.load(ApiSetting.getUrlToImage(it))
        } ?: item.filmImageId.load(R.drawable.no_image)

        //покажем или скроем популярность фильма
        appMovie.popularity ?. let{
            item.filmPopularityId.text = it.toString()
            item.filmPopularityId.visibility = View.VISIBLE
        } ?: kotlin.run { item.filmPopularityId.visibility = View.GONE }

        //покажем или скроем дату релиза
        appMovie.releaseDate ?. let{
            item.filmReleaseId.text = it
            item.filmReleaseId.visibility = View.VISIBLE
        } ?: kotlin.run { item.filmReleaseId.visibility = View.GONE }

        item.root.isVisible = true
        item.cardFilmId.setOnClickListener {
            router.navigateTo(FilmInfoScreen(appMovie.filmId))
        }
    }
}