package com.example.lab2

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WeatherDetailScreen(weatherdata:List<String>, itemIndex: Int, modifier:Modifier = Modifier){
    Text(
        //To do
        text = weatherdata[itemIndex],
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
        modifier = modifier.padding(16.dp)
    )
}