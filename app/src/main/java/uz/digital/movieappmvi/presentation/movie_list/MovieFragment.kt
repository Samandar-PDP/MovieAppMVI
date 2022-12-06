package uz.digital.movieappmvi.presentation.movie_list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import uz.digital.movieappmvi.R
import uz.digital.movieappmvi.databinding.FragmentMovieBinding

class MovieFragment : Fragment(R.layout.fragment_movie) {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val movieAdapter by lazy { MovieAdapter() }
    private lateinit var viewModel: MovieViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieBinding.bind(view)
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        initViews()
    }

    private fun initViews() {
        setupRv()
        binding.btnGet.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.userIntent.send(MainIntent.FetchUsers(1))
                binding.btnGet.isVisible = false
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is MainState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is MainState.Init -> Unit
                    is MainState.Error -> {
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        binding.progressBar.isVisible = false
                    }
                    is MainState.Success -> {
                        binding.progressBar.isVisible = false
                        movieAdapter.submitList(it.movies)
                        binding.btnGet.isVisible = it.movies.isEmpty()
                    }
                }
            }
        }
    }

    private fun setupRv() {
        val pagerSnapHelper = PagerSnapHelper()
        binding.recyclerView.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            pagerSnapHelper.attachToRecyclerView(this)
        }
        movieAdapter.onClick = {
            val bundle = bundleOf("movie" to it)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}