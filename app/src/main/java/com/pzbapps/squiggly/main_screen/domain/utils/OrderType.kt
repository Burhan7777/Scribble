package com.pzbapps.squiggly.main_screen.domain.utils

sealed class OrderType{
    object Ascending: OrderType()
    object Decending: OrderType()
}
