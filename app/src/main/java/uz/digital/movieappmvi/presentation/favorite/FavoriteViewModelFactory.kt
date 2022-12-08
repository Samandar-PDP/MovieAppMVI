package uz.digital.movieappmvi.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.digital.movieappmvi.repository.LocalRepository

class FavoriteViewModelFactory (
    private val repository: LocalRepository
    ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(repository) as T
    }
}