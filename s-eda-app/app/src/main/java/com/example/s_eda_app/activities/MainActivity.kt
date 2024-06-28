package com.example.s_eda_app.activities


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.example.s_eda_app.R
import com.example.s_eda_app.SharedPrefManager
import com.example.s_eda_app.URLs
import com.example.s_eda_app.entity.User
import com.example.s_eda_app.singleton.VolleySingleton
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap


class MainActivity : AppCompatActivity() {
    internal lateinit var userNameField: EditText
    internal lateinit var userPasswordField: EditText
    internal lateinit var authButton: Button
    internal lateinit var linkToRegistration: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        userNameField = findViewById(R.id.username_field)
        userPasswordField = findViewById(R.id.password_field)
        authButton= findViewById(R.id.button_auth)
        linkToRegistration= findViewById(R.id.link_to_registration)
        authButton.setOnClickListener{
            val intent = Intent(this, DishChoiceActivity::class.java)
            startActivity(intent)
            //userLogin()
        }
        linkToRegistration.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
        /*if(SharedPrefManager.getInstance(this).isLoggedIn) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } */
    }
    private fun userLogin() {
        val login = userNameField.text.toString().trim()
        val pass = userPasswordField.text.toString().trim()
        if (TextUtils.isEmpty(login)) {
            userNameField.error = "Please enter your username"
            userNameField.requestFocus()
            return
        }
        if (TextUtils.isEmpty(pass)) {
            userPasswordField.error = "Please enter your password"
            userPasswordField.requestFocus()
            return
        }
        val stringRequest = object : StringRequest(Request.Method.POST,
            URLs.URL_LOGIN,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    if(!obj.getBoolean("error")) {
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        val userJson = obj.getJSONObject("user")
                        val user = User(
                            userJson.getInt("id"),
                            userJson.getString("username"),
                            userJson.getInt("calories")
                        )
                        SharedPrefManager.getInstance(applicationContext).userLogin(user)
                        finish()
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch(e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["username"] = login
                params["password"] = pass
                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}