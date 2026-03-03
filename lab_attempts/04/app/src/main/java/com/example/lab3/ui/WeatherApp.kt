package com.example.lab3.ui

import android.R.attr.text
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab3.WeatherDetailScreen
import com.example.lab3.WeatherSearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp (){
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            title = {
                Text("Weather App")//Add a title to the app
            },
            actions = {
                //To do: Move the two buttons’ functions here
                IconButton(onClick = {
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
                }) {
                    Icon(Icons.Filled.Place, contentDescription = "View location on Map")
                }
                IconButton(onClick = {
                    //Display a Toast
                    Toast.makeText(context, "Refreshing weather!", Toast.LENGTH_SHORT).show()
                    //val city = text
                    //Fetch weather data
                    viewModel.fetchForecastForCity(text)
                    //Log the raw JSON response
                    //Log.d("ui State", weather)
                }) {
                    Icon(Icons.Filled.Search, contentDescription = "Refresh weather")
                }

            }
        )
    }) { innerPadding ->
        Surface(//Let Surface take care of the innerPadding in Scaffold, place the WeatherSearchScreen in the correct place, and use theme colours, etc.
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController: NavHostController = rememberNavController()
            //dummy weather data
            //val weatherData = listOf("Today - Storm 8 / 12", "Tomorrow - Foggy 9 / 13", "Thurs - Rainy 8 / 13", "Fri - foggy 8 / 12", "Sat - Sunny 9 / 14", "Sun - Sunny 10 / 15", "Mon - Sunny 11 / 15")
            val viewModel: WeatherViewModel = viewModel()
            NavHost(
                navController = navController,
                startDestination = AppScreens.Search.name
            ) {
                //call the composable() function once for each of the routes
                composable(route = AppScreens.Search.name) {//Search
                    //To do: call the WeatherSearchScreen composable function
                    WeatherSearchScreen(viewModel, navController)
                }
                composable(
                    route = AppScreens.Detail.name + "/{index}",//Detail
                    arguments = listOf(
                        navArgument(name = "index") {
                            type = NavType.IntType //extract the argument
                        }
                    )
                ) { index ->//call the WeatherSearchScreen composable function
                    WeatherDetailScreen(
                        viewModel,
                        itemIndex = index.arguments?.getInt("index") ?: 0//passing the index
                    )
                }

            }
        }
    }
}
enum class AppScreens{
    Search, //reference it everywhere else using AppScreens.Search.name
    Detail //reference it everywhere else using AppScreens.Detail.name
}