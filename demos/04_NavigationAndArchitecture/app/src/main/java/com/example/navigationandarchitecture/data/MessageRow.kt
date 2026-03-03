package com.example.navigationandarchitecture.data

//defines the structure of each message in the data layer
//currently contains profile image's resource ID and the comment
//can be expanded later to include timestamp or user
data class MessageRow (
    val imageResourceId:Int,
    val comment:String
)
