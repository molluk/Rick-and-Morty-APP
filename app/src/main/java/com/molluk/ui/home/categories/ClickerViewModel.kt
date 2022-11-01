package com.molluk.ui.home.categories

import androidx.lifecycle.MutableLiveData
import com.molluk.ui.base.BaseViewModel
import com.molluk.ui.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClickerViewModel @Inject constructor(

) : BaseViewModel() {

    val clickElement = MutableLiveData<Event<Any>>()

    fun clickElement(element: Any){
        clickElement.value = Event(element)
    }

}