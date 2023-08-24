package com.pzbdownloaders.scribble.main_screen.domain.utils

sealed class OrderType{
    object Ascending: OrderType()
    object Decending: OrderType()
}
