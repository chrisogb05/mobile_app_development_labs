package com.example.navigationandarchitecture.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Consuming the UI state in the UI
//Pass the ViewModel object to the composable function
@Composable
fun CommentDetailScreen(
    commentViewModel: CommentViewModel,
    itemIndex: Int?
){
    //Inside the composable, observe the commentList from the ViewModel and collects its state
    //then update how commentList is used to display the UI according to its new type
    val commentList by commentViewModel.commentList.observeAsState(emptyList())
    Text(
        text = commentList[itemIndex!!].comment,
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
        modifier = Modifier.padding(16.dp)
    )
}
/*
@Preview(showBackground = true)
@Composable
fun CommentDetailPreview(){
    CommentDetailScreen(commentData = listOf("Comment Dummy Data"),0)
}
*/