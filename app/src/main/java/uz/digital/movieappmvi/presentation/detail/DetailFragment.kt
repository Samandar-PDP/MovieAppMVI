package uz.digital.movieappmvi.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import uz.digital.movieappmvi.R
import uz.digital.movieappmvi.databinding.FragmentDetailBinding
import uz.digital.movieappmvi.model.ResultMovie
import uz.digital.movieappmvi.util.Constants

class DetailFragment: Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var movie: ResultMovie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable("movie")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        initViews()
    }

    private fun initViews() {
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
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}