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
    internal lateinit var userNameField: EditText
    internal lateinit var userPasswordField: EditText
    internal lateinit var authButton: Button
    internal lateinit var linkToRegistration: TextView
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
            val intent = Intent(this, DishChoiceActivity::class.java)
            startActivity(intent)
            //userLogin()
        }
        linkToRegistration.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
        /*if(SharedPrefManager.getInstance(this).isLoggedIn) {
                db.deleteDishes()
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
        val onLoginResponse:(String)-> Unit={ response ->
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
        }
        val stringRequest = requests.geLoginRequest(login, pass, onLoginResponse)
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}