package com.example.lab3

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lab3.ui.WeatherViewModel

@Composable
fun WeatherSearchScreen(viewModel: WeatherViewModel, navController: NavHostController, modifier: Modifier = Modifier) {
    var text by rememberSaveable { mutableStateOf("") } //a variable to keep the value of the TextField
    val context = LocalContext.current //get the activity context within a composable function, use "this" instead in an activity

    //passing the viewModel instead of dummy weather data to the WeatherSearchScreen

    //For stage 1, observe the ui state - JSON string. Don't need it once the JSON string is parsed into weatherData
    //val weather by viewModel.uiState.collectAsState()

    //For stage 2, observe the weatherData - list of strings
    val weatherData by viewModel.weatherData.collectAsState()

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
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    //get user input location
                    val location = text
                    //construct the uri
                    val geoUri = Uri.parse("geo:0,0?q=$location")
                    //val geoUri = ("geo:0,0?q=$location").toUri()
                    Log.d("uri",location)
                    //create an Intent
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        //set the uri data this intent is operating on
                        data = geoUri
                    }
                    //if there is an app that can handle the implicit intent
                    if (intent.resolveActivity(context.packageManager)!= null) {
                        context.startActivity(intent)
                    }else{
                        Log.d("WeatherSearchScreen", "no app handling implicit intent")
                    }
                }
            ) { Text("View on Map") }
            Button(
                onClick = {
                    //Display a Toast
                    Toast.makeText(context, "Refreshing weather!", Toast.LENGTH_SHORT).show()
                    //val city = text
                    //Fetch weather data
                    viewModel.fetchForecastForCity(text)
                    //Log the raw JSON response
                    //Log.d("ui State", weather)
                }) {
                Text(stringResource(R.string.refresh))
            }
        }

        //For stage 1, display the JSON string
        /*
        Text(
            text = weather,
            modifier = modifier.padding(bottom = 16.dp)//add padding between child elements
        )
        */

        //Stage 2, display the list of weather forecast
        Text(
            text = "Weather forecast for $text:",
            modifier = modifier.padding(bottom = 16.dp)
        )
        WeatherList(weatherData, navController, modifier)

    }
}

@Composable
fun WeatherList(data:List<String>, navController: NavHostController, modifier: Modifier){//create a lazy list of texts from the dummy data
    val context = LocalContext.current //get the activity context within a composable function
    LazyColumn {
        itemsIndexed(data) {//iterate through each item in the List and create a Text for each item
            index, item ->
            Text(
                text = item,
                modifier = modifier.padding(16.dp).clickable(
                    onClick = { //handle the onClick event to the list item
                        Toast.makeText(context, "Item Clicked!", Toast.LENGTH_LONG).show()
                        navController.navigate(route = "Detail/$index")
                    }
                )
            )//add padding between items in the lazy list

        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun WeatherSearchScreenPreview() {
    lab3Theme {
        WeatherSearchScreen()
    }
}
*/