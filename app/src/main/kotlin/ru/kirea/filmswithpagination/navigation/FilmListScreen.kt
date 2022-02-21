package ru.kirea.filmswithpagination.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.kirea.filmswithpagination.windows.filmlist.FilmListFragment

class FilmListScreen(): FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment = FilmListFragment()
}