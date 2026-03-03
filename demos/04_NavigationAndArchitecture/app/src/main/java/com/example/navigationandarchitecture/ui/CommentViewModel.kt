package com.example.navigationandarchitecture.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationandarchitecture.data.MessageRow

//Create a class extending ViewModel
class CommentViewModel:ViewModel() {
    //backing property
    //a private property holding the list of comments as the UI state, mutable from within the class
    private val _commentList = MutableLiveData<List<MessageRow>>(emptyList())
    //a public property that is immutable for accessing from outside the ViewModel class
    val commentList: LiveData<List<MessageRow>> get() = _commentList

    //Methods to manipulate the UI state
    //add a comment to the list of comments
    fun addComment(comment: MessageRow) {
        _commentList.value = _commentList.value.orEmpty() + comment
    }
}