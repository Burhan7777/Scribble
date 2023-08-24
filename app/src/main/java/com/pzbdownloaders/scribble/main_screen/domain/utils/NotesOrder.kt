package com.pzbdownloaders.scribble.main_screen.domain.utils

import com.pzbdownloaders.scribble.main_screen.domain.model.Note

sealed class NotesOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : NotesOrder(orderType)
    class Date(orderType: OrderType) : NotesOrder(orderType)
    class Color(orderType: OrderType) : NotesOrder(orderType)

}
