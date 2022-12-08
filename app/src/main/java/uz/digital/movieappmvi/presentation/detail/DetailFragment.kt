package uz.digital.movieappmvi.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import uz.digital.movieappmvi.R
import uz.digital.movieappmvi.database.MovieDatabase
import uz.digital.movieappmvi.databinding.FragmentDetailBinding
import uz.digital.movieappmvi.model.ResultMovie
import uz.digital.movieappmvi.repository.LocalRepository
import uz.digital.movieappmvi.util.Constants
import uz.digital.movieappmvi.util.toMovieEntity

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var viewModel: DetailViewModel
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var movie: ResultMovie? = null
    private var isSaved: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable("movie")
        isSaved = arguments?.getBoolean("isSaved") ?: false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        val dao = MovieDatabase.invoke(requireContext()).dao
        val factory = DetailViewModelFactory(LocalRepository(dao))
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        initViews()
    }

    private fun initViews() {
        if (isSaved) {
            binding.btnDelSv.text = "Delete"
        }
        movie?.let { mov ->
            binding.apply {
                btnBack.setOnClickListener {
                    findNavController().popBackStack()
                }
                val image = "${Constants.BASE_IMG}${mov.backdrop_path}"
                Picasso.get()
                    .load(image)
                    .into(imageView)
                textTitle.text = mov.original_title
                textDesc.text = "Language: ${mov.original_language}\n${mov.overview}"
            }
        }
        binding.btnDelSv.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (isSaved) {
                    viewModel.channel.send(DetailIntent.OnDeleteClicked(movie?.toMovieEntity()!!))
                    findNavController().popBackStack()
                } else {
                    viewModel.channel.send(DetailIntent.OnSaveClicked(movie?.toMovieEntity()!!))
                    binding.btnDelSv.text = "Saved"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}