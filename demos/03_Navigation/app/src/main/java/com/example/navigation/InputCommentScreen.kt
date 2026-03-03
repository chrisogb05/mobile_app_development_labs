package com.example.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.navigation.model.MessageRow
import com.example.navigation.ui.theme.SimpleUITheme

//Add simple user interaction, handle the buttons' onclick events
@Composable
fun InputCommentScreen (commentList:MutableList<String>, navController:NavHostController, modifier:Modifier = Modifier) {
    //use rememberSaveable instead of remember to save and restore the state during the Activity lifecycle events
    //so that the value of the TextField will not disappear when the screen rotates
    //Note: It only works for simple variable.
    var comment by rememberSaveable { mutableStateOf ("") }// use comment to remember the comment to submit

    Column (modifier = modifier.padding(16.dp)){
        Text(
            text = "Please input your comment below:",
            modifier = modifier.padding(bottom = 16.dp)
        )
        TextField(
            value = comment,
            onValueChange = { comment = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Row {
            //wrap the Button in a Box and give the Box a weight of "1f"
            //if you would like to position the second button always to the right edge of the screen
            //Box (modifier = modifier.weight(1f)){}, in this case, the Spacer is no longer needed
            val context = LocalContext.current//get the Activity Context
            Box (modifier = Modifier.weight(1f)){
                Button(onClick = {
                    /*TODO: create a simple Toast message showing the comment*/
                    if (comment == "") {
                        Toast.makeText(
                            context,//activity context
                            "Please enter your comment.",//message to display
                            Toast.LENGTH_LONG//duration
                        )
                            .show()//show the message
                        Log.d("InputCommentScreen", "empty comment")
                    } else {
                        /*
                        Toast.makeText(
                            context,//activity context
                            "Thank you for your comment: $comment",//message to display
                            Toast.LENGTH_LONG//duration
                        )
                            .show()//show the message
                        */
                        commentList.add(comment)
                        comment = ""
                        Log.d("comment", commentList.size.toString())
                    }
                }) {
                    Text("Submit")
                }
            }
            Button(onClick = { /*TODO: clear the input comment*/ comment =""}) {
                Text("Cancel")
            }
        }
        CommentList(commentList, navController)
    }
}


//3. Create a composable function that takes in a list of Comments and create a lazyColumn of comments, each comment is an item in the list of comments.
@Composable
fun CommentList (commentList:MutableList<String>, navController:NavHostController, modifier:Modifier = Modifier){
    LazyColumn {
        //use the itemsIndexed() method if you need to access the index as well as the item in the LazyColumn
        itemsIndexed(commentList) { index, item ->
            CommentRow(navController = navController, message = MessageRow(R.drawable.profile,item), index = index, modifier = modifier.padding(6.dp))
        }
    }
}

//This function creates a message row containing a profile image and the string comment
@Composable
fun CommentRow(navController: NavHostController, index:Int, message: MessageRow, modifier: Modifier = Modifier){
    val context = LocalContext.current
    Card (modifier = modifier) {
        Row (modifier.clickable {
                //handle the onClick event to the list item
                Toast
                    .makeText(context, "Item clicked!", Toast.LENGTH_SHORT)
                    .show()//display a Toast when an item in the list is clicked
                //navigate to the CommentDetailScreen
                navController.navigate(route = AppScreens.Detail.name + "/$index")
        }) {
            Image(
                painter = painterResource(message.imageResourceId),
                contentDescription = "",
                modifier = modifier
                    .width(40.dp)
                    .height(40.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = message.msg,
                modifier = modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputCommentScreenPreview() {
    SimpleUITheme {
        // Create a mock NavController using rememberNavController for preview
        val navController = rememberNavController()
        InputCommentScreen(commentList = mutableListOf("dummy comment #"), navController = navController)
    }
}