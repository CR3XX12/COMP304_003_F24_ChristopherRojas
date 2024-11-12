package com.example.group13_comp304sec003_lab03.navigation

sealed interface ContentType {
    object List : ContentType
    object ListAndDetail : ContentType
}