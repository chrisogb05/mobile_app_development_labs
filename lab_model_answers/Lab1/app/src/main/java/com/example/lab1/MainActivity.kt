package com.example.lab1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab1.ui.theme.Lab1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(//Let Surface take care of the innerPadding in Scaffold, place the WeatherSearchScreen in the correct place, and use theme colours, etc.
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        WeatherSearchScreen()//uses an empty modifier of its own
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherSearchScreen(modifier: Modifier = Modifier) {
    var text by rememberSaveable {mutableStateOf("")} //a variable to keep the value of the TextField
    val context = LocalContext.current //get the activity context within a composable function, use "this" instead in an activity
    var data by rememberSaveable {mutableStateOf(emptyList<String>())} //a list of String that will contain the dummy weather data, initially an empty List, later replaced with a new List containing dummy data
    Column (modifier=
        modifier.padding(16.dp)//add padding all around
    ){
        Text(
            text = stringResource(R.string.welcome_msg),
            modifier = modifier.padding(bottom = 16.dp)//add padding between child elements
        )
        TextField(
            value = text,
            label = { Text(stringResource(R.string.enter_a_location)) },
            maxLines = 1,
            onValueChange = { text = it },
            modifier = modifier
                .padding(bottom = 16.dp)//add padding between child elements
                .fillMaxWidth(1f)//make the TextField fill the width of the screen
        )
        Button(
            modifier = modifier.align(Alignment.End),//align the Button to the right of the screen
            onClick = {
                //Display the LazyList of search result using the following dummy data
                data = listOf("Today - Storm 8 / 12", "Tomorrow - Foggy 9 / 13", "Thurs - Rainy 8 / 13", "Fri - foggy 8 / 12", "Sat - Sunny 9 / 14", "Sun - Sunny 10 / 15", "Mon - Sunny 11 / 15")
                //Display a Toast
                Toast.makeText(context,"Refreshing weather!",Toast.LENGTH_SHORT).show()
            }) {
            Text(stringResource(R.string.refresh))
        }
        WeatherList(data,modifier)
    }
}

@Composable
fun WeatherList(data:List<String>, modifier: Modifier){//create a lazy list of texts from the dummy data
    LazyColumn {
        items(data) {//iterate through each item in the List and create a Text for each item
                item ->
            Text(text = item, modifier.padding(16.dp))//add padding between items in the lazy list
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherSearchScreenPreview() {
    Lab1Theme {
        WeatherSearchScreen()
    }
}