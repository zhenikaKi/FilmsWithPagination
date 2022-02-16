package ru.kirea.filmswithpagination.windows.filminfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent
import ru.kirea.filmswithpagination.AppConstants
import ru.kirea.filmswithpagination.R
import ru.kirea.filmswithpagination.api.ApiSetting
import ru.kirea.filmswithpagination.api.entities.AppMovie
import ru.kirea.filmswithpagination.databinding.FilmInfoFragmentBinding
import ru.kirea.filmswithpagination.di.Scopes
import ru.kirea.filmswithpagination.states.BaseState
import ru.kirea.filmswithpagination.states.FilmInfoState

class FilmInfoFragment: Fragment() {
    companion object {
        fun newInstance(filmId: Long): FilmInfoFragment {
            val fragment = FilmInfoFragment()
            fragment.filmId = filmId
            return fragment
        }
    }

    private val scope = KoinJavaComponent.getKoin().createScope<FilmInfoFragment>()
    private var _binding: FilmInfoFragmentBinding? = null
    private val binding
        get() = _binding

    private val viewModel: FilmInfoViewModel = scope.get(qualifier = named(Scopes.FILM_INFO_VIEW_MODEL))

    private var filmId: Long? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FilmInfoFragmentBinding.inflate(inflater, container, false)
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }

        filmId?.let { viewModel.getFilm(it) }


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
            is FilmInfoState.Success -> {
                setVisibleView(false)
                showInfo(state.film)
            }

            else -> { }
        }
    }

    private fun showInfo(film: AppMovie?) {
        film?.let { appMovie ->
            binding?.let {
                it.filmTitleId.text = appMovie.title
                it.filmDescriptionId.text = appMovie.description
                it.filmPopularityId.text = appMovie.popularity ?. toString() ?: "-"
                it.filmReleaseId.text = appMovie.releaseDate ?: "-"

                //загрузим изображение
                appMovie.imageUrl?.let { url ->
                    it.filmImageId.load(ApiSetting.getUrlToImage(url))
                } ?: it.filmImageId.load(R.drawable.no_image)
            }
        }
    }
}