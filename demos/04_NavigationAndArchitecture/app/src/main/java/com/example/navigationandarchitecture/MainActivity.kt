package com.example.navigationandarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationandarchitecture.ui.CommentDetailScreen
import com.example.navigationandarchitecture.ui.CommentViewModel
import com.example.navigationandarchitecture.ui.theme.SimpleUITheme

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
                    //create a commentList to keep a list of submitted comments, initially the list is empty
                    //val commentList = remember { mutableStateListOf<String>() }
                    //moved this to the data class CommentUiState

                    //create a commentViewModel
                    val commentViewModel = CommentViewModel()
                    //val commentList by commentViewModel.commentList.observeAsState(emptyList())
                    //create a NavController
                    val navController: NavHostController = rememberNavController()
                    //add a NavHost: an empty container that swaps screens in and out
                    NavHost(
                        navController = navController,
                        startDestination = AppScreens.Comment.name
                        ){
                        //call the composable() function once for each of the routes
                        composable(route = AppScreens.Comment.name){
                            //route = "Comment", call the InputCommentScreen composable function
                            InputCommentScreen(commentViewModel,navController)
                        }
                        composable(
                            //route = "Detail/{index}", call the CommentDetailScreen composable function
                            route = AppScreens.Detail.name+"/{index}",//Detail
                            arguments = listOf(
                                navArgument(name = "index"){
                                    type = NavType.IntType //extract the argument
                                }
                            )
                        ) {
                                index->//call the CommentDetailScreen composable function
                            CommentDetailScreen(
                                commentViewModel,
                                itemIndex = index.arguments?.getInt("index")//passing the index
                            )
                        }

                    }
                }
            }
        }
    }
}

//1. Create a enum class to define the routes
enum class AppScreens {
    Comment, //represents InputCommentScreen
    Detail //represents CommentDetailScreen
}
