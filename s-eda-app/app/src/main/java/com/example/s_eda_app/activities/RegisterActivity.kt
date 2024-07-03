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
import com.example.s_eda_app.db.SharedPrefManager
import com.example.s_eda_app.entity.User
import com.example.s_eda_app.volley.Requests
import com.example.s_eda_app.volley.VolleySingleton
import org.json.JSONException
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {
    private lateinit var userNameField: EditText
    private lateinit var userPasswordField: EditText
    private lateinit var userPassword2Field: EditText
    private lateinit var regButton: Button
    private lateinit var linkToAuth: TextView
    private lateinit var requests: Requests
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        requests= Requests(applicationContext)
        userNameField = findViewById(R.id.username_reg_field)
        userPasswordField = findViewById(R.id.password_reg_field)
        userPassword2Field= findViewById(R.id.password2_reg_field)
        regButton= findViewById(R.id.button_reg)
        linkToAuth= findViewById(R.id.link_to_authorization)
        regButton.setOnClickListener{
            registerUser()
        }
        linkToAuth.setOnClickListener{
            val registerIntent = Intent(this, MainActivity::class.java)
            startActivity(registerIntent)
        }
    }
    private fun registerUser() {
        val username =  userNameField.text.toString().trim()
        val password = userPasswordField.text.toString().trim()
        val password2 = userPassword2Field.text.toString().trim()
        if(TextUtils.isEmpty(username)) {
            userNameField.error = "Введите имя пользователя"
            userNameField.requestFocus()
            return
        }
        if(TextUtils.isEmpty(password)) {
            userPasswordField.error = "Введите пароль"
            userPasswordField.requestFocus()
            return
        }
        if(TextUtils.isEmpty(password2)) {
            userPassword2Field.error = "Повторите пароль"
            userPassword2Field.requestFocus()
            return
        }
        if(password != password2){
            userPassword2Field.error = "Пароли не совпадают"
            userPassword2Field.requestFocus()
            return
        }
        val onRegisterResponse:(JSONObject)-> Unit={ response ->
            try {
                val jwt = response.getString("jwt")
                val user = User(
                    username,
                    password,
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
        val jsonObjectRequest = requests.getRegisterRequest(username, password, onRegisterResponse)
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}

