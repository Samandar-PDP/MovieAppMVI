package uz.digital.movieappmvi.presentation.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.digital.movieappmvi.R
import uz.digital.movieappmvi.databinding.FragmentSearchBinding
import uz.digital.movieappmvi.presentation.movie_list.MovieAdapter

class SearchFragment: Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    private val movieAdapter by lazy { MovieAdapter() }
    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        initViews()
    }
    private fun initViews() {
        setupRv()
        binding.editSearch.addTextChangedListener { edit ->
            viewLifecycleOwner.lifecycleScope.launch {
                job?.cancel()
                delay(700L)
                job = MainScope().launch {
                    viewModel.channel.send(SearchIntent.OnSearched(edit.toString()))


                    viewModel.state.collect {
                        when(it) {
                            is SearchState.Loading -> {
                                binding.progressBar.isVisible = true
                            }
                            is SearchState.Error -> {
                                binding.progressBar.isVisible = false
                                Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                            }

                            is SearchState.Success -> {
                                binding.progressBar.isVisible = false
                                movieAdapter.submitList(it.success)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupRv() {
        binding.recyclerView.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)
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