package mason.cloudinteractive.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mason.cloudinteractive.result.Event

class MainViewModel : ViewModel() {

    private val _navigateToPhotosAction = MutableLiveData<Event<Unit>>()
    val navigateToPhotosAction: LiveData<Event<Unit>>
        get() = _navigateToPhotosAction

    fun onRequestApiClicked() {
        _navigateToPhotosAction.value = Event(Unit)
    }
}