package uz.digital.movieappmvi.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.digital.movieappmvi.presentation.favorite.FavoriteFragment
import uz.digital.movieappmvi.presentation.movie_list.MovieFragment
import uz.digital.movieappmvi.presentation.search.SearchFragment

class MainAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MovieFragment()
            1 -> SearchFragment()
            2 -> FavoriteFragment()
            else -> Fragment()
        }
    }
}