package ru.kirea.filmswithpagination.windows.filmlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent
import ru.kirea.filmswithpagination.AppConstants
import ru.kirea.filmswithpagination.api.entities.AppMovie
import ru.kirea.filmswithpagination.databinding.FilmListFragmentBinding
import ru.kirea.filmswithpagination.di.Scopes
import ru.kirea.filmswithpagination.states.BaseState
import ru.kirea.filmswithpagination.states.FilmListState
import ru.kirea.filmswithpagination.windows.pagewithfilms.PageWithFilmsFragment

class FilmListFragment: Fragment() {

    private val scope = KoinJavaComponent.getKoin().createScope<FilmListFragment>()
    private var _binding: FilmListFragmentBinding? = null
    private val binding
        get() = _binding

    private val viewModel: FilmListViewModel = scope.get(qualifier = named(Scopes.FILM_LIST_VIEW_MODEL))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FilmListFragmentBinding.inflate(inflater, container, false)
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }

        viewModel.getFilms()

        //навешиваем обработчики
        setClickListener()

        return binding?.root
    }

    /** Задать видимость view на экране */
    private fun setVisibleView(progressBarVisible: Boolean) {
        binding?.progressBar?.isVisible = progressBarVisible
        binding?.dataBlock?.isVisible = !progressBarVisible
    }

    /**
     * Обработать событие от viewModel.
     * @param state полученное состояние.
     */
    private fun renderData(state: BaseState?) {
        Log.d(AppConstants.TAG_LOG, "renderData state = $state")

        when (state) {
            //загрузка информации
            BaseState.Loading -> setVisibleView(true)

            //ошибка
            is BaseState.Error -> {
                setVisibleView(false)
                Toast.makeText(requireContext(), state.text, Toast.LENGTH_LONG).show()
            }

            //полученный список фильмов
            is FilmListState.Success -> {
                setVisibleView(false)
                showPages(state.films)
            }

            else -> { }
        }
    }

    /** Сформировать пагинацию с фильмами */
    private fun showPages(films: List<AppMovie>?) {
        films?.let { appMovies ->
            //на каждой странице делаем по 4 фильма
            val fragments = mutableListOf<PageWithFilmsFragment>()
            for (ind in appMovies.indices step PageWithFilmsFragment.COUNT_ON_PAGE) {
                val pageMovies: List<AppMovie> = appMovies.subList(ind, ind + PageWithFilmsFragment.COUNT_ON_PAGE)
                fragments.add(PageWithFilmsFragment.newInstance(pageMovies))
            }

            binding?.let {
                it.viewPager.adapter = PhotosMarsViewPagerAdapter(requireActivity(), fragments)
                //связываем вкладки (индикаторы) с viewpager, чтобы при смене фото менялся индикатор
                TabLayoutMediator(it.pageIndicatorId, it.viewPager) { _, _ -> }.attach()
            }

            setVisibleView(false)
        }
    }

    /** Навешать обработчки кликов */
    private fun setClickListener() {
        binding?.pagePrevId?.setOnClickListener {
            changePage(false)
        }
        binding?.pageNextId?.setOnClickListener {
            changePage(true)
        }
    }

    /**
     * Сменить страницу с фильмами.
     * @param next true - переход к следующей странице, false - к предыдущей
     */

    private fun changePage(next: Boolean) {
        binding?.viewPager?.let {
            val currentPosition = it.currentItem
            val count = it.adapter?.itemCount ?: 0
            if (count > 0) {
                var isChange = false
                val newPosition = if ((next && currentPosition < count-1) || (!next && currentPosition > 0)) {
                    isChange = true
                    currentPosition + if (next) 1 else -1
                }
                else {
                    0
                }

                //делаем смещение
                if (isChange) {
                    it.setCurrentItem(newPosition, true)
                }
            }
        }
    }
}