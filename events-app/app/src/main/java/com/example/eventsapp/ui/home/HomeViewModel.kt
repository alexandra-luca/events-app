package com.example.eventsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private var _text = MutableLiveData<String>().apply {
        value = "This is events Fragment"
    }

    val text: LiveData<String> = _text
}