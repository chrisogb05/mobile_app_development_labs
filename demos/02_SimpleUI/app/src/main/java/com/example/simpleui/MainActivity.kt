package com.example.simpleui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpleui.ui.theme.SimpleUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleUITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InputCommentScreen()
                }
            }
        }
    }
}

/*
//Version 1: Composition: contains only the composable UI components, What would be the result?
@Composable
fun InputCommentScreen () {
    var comment by remember { mutableStateOf("") }
    Text(text = "Please input your comment below:")
    TextField(value = comment, onValueChange = { comment = it })
    Button(onClick = { /*TODO*/ }){
        Text("Submit")
    }
    Spacer(modifier = Modifier.width(150.dp))
    Button(onClick = { /*TODO*/ }) {
        Text("Cancel")
    }
}
*/
/*
//version 2: Layout: use composable layout column and row to organise the UI components
@Composable
fun InputCommentScreen () {
    var comment by remember { mutableStateOf("") }
    Column {
        Text(text = "Please input your comment below:")
        TextField(value = comment, onValueChange = { comment = it })
        Row {
            Button(onClick = { /*TODO*/ }) {
                Text("Submit")
            }
            Spacer(modifier = Modifier.width(150.dp))
            Button(onClick = { /*TODO*/ }) {
                Text("Cancel")
            }
        }
    }
}
*/
/*
//version 3: Drawing: use modifiers to specify the appearance of UI components
@Composable
fun InputCommentScreen () {
    var comment by remember { mutableStateOf ("") }
    Column (modifier = Modifier.padding(16.dp)){
        Text(
            text = "Please input your comment below:",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        TextField(
            value = comment,
            onValueChange = { comment = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Row {
            //TODO: wrap the Button in a Box and give the Box a weight of "1f"
            //if you would like to position the second button always to the right edge of the screen
            //Box (modifier = modifier.weight(1f)){}, in this case, the Spacer is no longer needed
            Button(onClick = {
            /*TODO:*/
            }) {
                Text("Submit")
            }
            Spacer(modifier = Modifier.width(150.dp))
            Button(onClick = { /*TODO:*/}) {
                Text("Cancel")
            }
        }
    }
}
*/


//version 4: Add simple user interaction, handle the buttons' onclick events
@Composable
fun InputCommentScreen () {
    var comment by remember { mutableStateOf ("") }
    Column (modifier = Modifier.padding(16.dp)){
        Text(
            text = "Please input your comment below:",
            modifier = Modifier.padding(bottom = 16.dp)
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
                    } else
                        Toast.makeText(
                            context,//activity context
                            "Thank you for your comment: $comment",//message to display
                            Toast.LENGTH_LONG//duration
                        )
                            .show()//show the message
                }) {
                    Text("Submit")
                }
            }
            Button(onClick = { /*TODO: clear the input comment*/ comment =""}) {
                Text("Cancel")
            }
        }
    }
}

//version 5: use rememberSaveable instead of remember to save and restore UI state during the Activity Lifecycle events
//Do it yourself and test what happens when the screen rotates, does the input comment disappear or not?

@Preview(showBackground = true)
@Composable
fun InputCommentScreenPreview() {
    SimpleUITheme {
        InputCommentScreen()
    }
}