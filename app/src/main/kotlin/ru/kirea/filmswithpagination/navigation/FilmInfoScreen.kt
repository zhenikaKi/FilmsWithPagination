package ru.kirea.filmswithpagination.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.kirea.filmswithpagination.windows.filminfo.FilmInfoFragment

class FilmInfoScreen(private val filmId: Long): FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment = FilmInfoFragment.newInstance(filmId)
}