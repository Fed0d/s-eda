package com.example.s_eda_app.db

import android.content.Context
import android.content.Intent
import com.example.s_eda_app.activities.MainActivity
import com.example.s_eda_app.entity.User

class SharedPrefManager private constructor(context: Context) {
    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences?.getString(KEY_JWT, null) != null
        }

    val user: User
        get() {
            val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
                sharedPreferences?.getString(KEY_USERNAME, null),
                sharedPreferences?.getString(KEY_PASS, null),
                sharedPreferences?.getInt(KEY_CALORIES, -1),
                sharedPreferences?.getString(KEY_JWT, null)
            )
        }
    init {
        ctx = context
    }

    fun userLogin(user: User) {
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString(KEY_USERNAME, user.nickName)
        editor?.putString(KEY_PASS, user.password)
        if(user.calories==null){
            editor?.putInt(KEY_CALORIES, -1)
        }else{
        editor?.putInt(KEY_CALORIES, user.calories!!)
        }
        editor?.putString(KEY_JWT, user.jwt)
        editor?.apply()
    }
    fun setNewJWT(jwt: String){
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.remove(KEY_JWT)?.apply()
        editor?.putString(KEY_JWT, user.jwt)?.apply()
    }
    fun getJWT():String?{
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences?.getString(KEY_JWT, "")
    }
    fun logout() {
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
        ctx?.startActivity(Intent(ctx, MainActivity::class.java))
    }
    companion object {
        private const val SHARED_PREF_NAME = "volleyregisterlogin"
        private const val KEY_USERNAME = "username"
        private const val KEY_CALORIES = "calories"
        private const val KEY_PASS = "password"
        private const val KEY_JWT = "jwt"
        private var mInstance: SharedPrefManager? = null
        private var ctx: Context? = null
        @Synchronized
        fun getInstance(context: Context): SharedPrefManager {
            if(mInstance == null) {
                mInstance = SharedPrefManager(context)
            }
            return mInstance as SharedPrefManager
        }
    }
}
