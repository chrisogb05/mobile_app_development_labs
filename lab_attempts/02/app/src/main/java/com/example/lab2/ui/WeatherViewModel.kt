package com.example.lab2.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class WeatherViewModel : ViewModel() {
    private val _uiState = MutableStateFlow("")
    val uiState = _uiState.asStateFlow()
}