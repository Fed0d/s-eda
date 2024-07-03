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
import com.example.s_eda_app.R
import com.example.s_eda_app.db.DBHelper
import com.example.s_eda_app.db.SharedPrefManager
import com.example.s_eda_app.entity.User
import com.example.s_eda_app.volley.Requests
import com.example.s_eda_app.volley.VolleySingleton
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var userNameField: EditText
    private lateinit var userPasswordField: EditText
    private lateinit var authButton: Button
    private lateinit var linkToRegistration: TextView
    private val requests= Requests(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db= DBHelper(applicationContext,null)
        setContentView(R.layout.activity_main)
        userNameField = findViewById(R.id.username_field)
        userPasswordField = findViewById(R.id.password_field)
        authButton= findViewById(R.id.button_auth)
        linkToRegistration= findViewById(R.id.link_to_registration)
        authButton.setOnClickListener{
            userLogin()
            if(SharedPrefManager.getInstance(this).isLoggedIn) {

                val intent = Intent(this, DishChoiceActivity::class.java)
                startActivity(intent)
            }
        }
        linkToRegistration.setOnClickListener {
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        if(SharedPrefManager.getInstance(this).isLoggedIn) {
                db.deleteDishes()
                val intent = Intent(this, DishChoiceActivity::class.java)
                startActivity(intent)
        }
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
        val onLoginResponse:(JSONObject)-> Unit={ response ->
            try {
                val jwt = response.getString("jwt")
                val user = User(
                    login,
                    pass,
                    null,
                    jwt
                )
                SharedPrefManager.getInstance(applicationContext).userLogin(user)
                finish()
                startActivity(Intent(applicationContext, MainActivity::class.java))

            } catch(e: JSONException) {
                e.printStackTrace()
            }
        }
        val jsonObjectRequest = requests.geLoginRequest(login, pass, onLoginResponse)
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}