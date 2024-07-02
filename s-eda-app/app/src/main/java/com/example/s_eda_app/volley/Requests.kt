package com.example.s_eda_app.volley

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.example.s_eda_app.URLs
import java.util.HashMap


class Requests(con: Context) {
    val context=con
    fun getImagesRequest(url: String,  listener: Response.Listener<Bitmap>): ImageRequest {
        return ImageRequest(url, listener
        , 0, 0, null, { error ->
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        })
    }
    fun getIngredientsOfDish(dishId:Int, listener: Response.Listener<String>): StringRequest{
        return object : StringRequest(
            Request.Method.GET, URLs.URL_INGREDIENTS_OF_DISH + dishId.toString(),
            listener,
            Response.ErrorListener { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }) {
        }
    }
    fun getRegisterRequest(username: String, password:String, listener: Response.Listener<String>): StringRequest{
        return object : StringRequest(
            Request.Method.POST,
            URLs.URL_REGISTER,
            listener,
            Response.ErrorListener { error -> Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["nickname"] = username
                params["password"] = password
                return params
            }
        }
    }
    fun geLoginRequest(username: String, password:String, listener: Response.Listener<String>): StringRequest{
        return object : StringRequest(Request.Method.POST,
            URLs.URL_LOGIN,
            listener,
            Response.ErrorListener { error -> Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["nickname"] = username
                params["password"] = password
                return params
            }
        }
    }
    fun getTodayMenu(listener: Response.Listener<String>): StringRequest{
        return object : StringRequest(Request.Method.GET,
            URLs.URL_TODAY_MENU,
            listener,
            Response.ErrorListener {
                    error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show() }) {
        }
    }
}