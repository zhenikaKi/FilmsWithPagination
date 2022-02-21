package ru.kirea.filmswithpagination.windows.filmlist

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.kirea.filmswithpagination.windows.pagewithfilms.PageWithFilmsFragment

class PhotosMarsViewPagerAdapter(fragmentActivity: FragmentActivity,
                                 private val fragments: List<PageWithFilmsFragment>): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}