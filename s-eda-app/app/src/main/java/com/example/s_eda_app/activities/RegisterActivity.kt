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
    internal lateinit var userNameField: EditText
    internal lateinit var userPasswordField: EditText
    internal lateinit var userPassword2Field: EditText
    internal lateinit var regButton: Button
    internal lateinit var linkToAuth: TextView
    val requests = Requests(applicationContext)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
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
        val username =  userNameField.text.toString().trim { it <= ' ' }
        val password = userPasswordField.text.toString().trim { it <= ' ' }
        val password2 = userPassword2Field.text.toString().trim { it <= ' ' }
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
        val onRegisterResponse:(String)-> Unit={ response ->
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
        val stringRequest = requests.getRegisterRequest(username, password, onRegisterResponse)
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}

