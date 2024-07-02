package com.example.s_eda_app

object URLs {
    private val ROOT_URL = "http://10.0.2.2:8080/api/v1/"
    val URL_REGISTER = ROOT_URL + "signup"
    val URL_LOGIN = ROOT_URL + "login"
    val URL_TODAY_MENU= ROOT_URL + "menu"
    val URL_INGREDIENTS_OF_DISH=ROOT_URL + "recipes/ingredients/"
}