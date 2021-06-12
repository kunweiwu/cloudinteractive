package mason.cloudinteractive.ui.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mason.cloudinteractive.domain.photo.LoadPhotoUseCase
import mason.cloudinteractive.data.ApiProvider
import mason.cloudinteractive.data.DefaultPhotoRepository
import mason.cloudinteractive.data.model.Photo
import mason.cloudinteractive.result.data

class PhotosViewModel : ViewModel() {

    private val repository = DefaultPhotoRepository(ApiProvider.apiService)

    private val loadPhotoUseCase = LoadPhotoUseCase(repository, Dispatchers.IO)

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = _photos

    init {
        viewModelScope.launch {
            _photos.value = loadPhotoUseCase(Unit).data
        }
    }
}