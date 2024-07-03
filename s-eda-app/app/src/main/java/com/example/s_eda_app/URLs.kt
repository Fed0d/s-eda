package com.example.s_eda_app

object URLs {
    private const val ROOT_URL = "http://10.0.2.2:8080/api/v1/"
    const val URL_REGISTER = ROOT_URL +"auth/register"
    const val URL_LOGIN = ROOT_URL +"auth/authenticate"
    const val URL_TODAY_MENU= ROOT_URL + "menu"
    const val URL_INGREDIENTS_OF_DISH=ROOT_URL + "recipes/ingredients/"
}