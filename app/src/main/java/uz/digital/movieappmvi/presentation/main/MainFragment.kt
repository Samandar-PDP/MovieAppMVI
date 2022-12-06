package uz.digital.movieappmvi.presentation.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import uz.digital.movieappmvi.MainActivity
import uz.digital.movieappmvi.R
import uz.digital.movieappmvi.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main), NavigationBarView.OnItemSelectedListener {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        val mainAdapter = MainAdapter(this)
        binding.viewPager.adapter = mainAdapter
        binding.viewPager.isUserInputEnabled = false
        binding.bottomNav.setOnItemSelectedListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_movie -> changeIndex(0, R.string.movies)
            R.id.menu_search -> changeIndex(1, R.string.search)
            R.id.menu_fav -> changeIndex(2, R.string.fav)
        }
        return true
    }
    private fun changeIndex(index: Int, title: Int) {
        binding.viewPager.currentItem = index
        (activity as MainActivity).supportActionBar?.title = getString(title)
    }
}