package com.example.s_eda_app.volley

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.s_eda_app.URLs
import com.example.s_eda_app.db.SharedPrefManager
import org.json.JSONObject
import java.util.HashMap


class Requests(con: Context) {
    val context=con
    //TODO добавить обновление регистрации
    fun getImagesRequest(url: String,  listener: Response.Listener<Bitmap>): ImageRequest {
        return ImageRequest(url, listener
        , 0, 0, null, { error ->
                if(error.message==null){
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }else {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }
        })
    }
    fun getIngredientsOfDish(dishId:Int, listener: Response.Listener<String>): StringRequest{
        return object : StringRequest(
            Request.Method.GET, URLs.URL_INGREDIENTS_OF_DISH + dishId.toString(),
            listener,
            Response.ErrorListener { error ->
                if(error.message==null){
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders():Map<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = "Bearer "+ SharedPrefManager.getInstance(context).getJWT()
                return params
            }
        }
    }
    fun getRegisterRequest(username: String, password:String, listener: Response.Listener<JSONObject>): JsonObjectRequest {
        return JsonObjectRequest(Request.Method.POST,
            URLs.URL_REGISTER,
            getParams(username,password),
            listener,
            Response.ErrorListener { error ->
                if(error.message==null){
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }else{
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }
            })
    }
    fun geLoginRequest(username: String, password:String, listener: Response.Listener<JSONObject>): JsonObjectRequest{
        return JsonObjectRequest(Request.Method.POST,
            URLs.URL_LOGIN,
            getParams(username,password),
            listener,
            Response.ErrorListener { error ->
                if(error.message==null){
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }
            })

    }
    fun getTodayMenu(listener: Response.Listener<String>): StringRequest{
        return object : StringRequest(Request.Method.GET,
            URLs.URL_TODAY_MENU,
            listener,
            Response.ErrorListener {
                    error ->
                if(error.message==null){
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }else {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders():Map<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = "Bearer "+ SharedPrefManager.getInstance(context).getJWT()
                return params
            }
        }
    }

    private fun getParams(username: String, password: String): JSONObject {
        val params = JSONObject()
        params.put("nickname",username)
        params.put("password",password)
        return params
    }
}