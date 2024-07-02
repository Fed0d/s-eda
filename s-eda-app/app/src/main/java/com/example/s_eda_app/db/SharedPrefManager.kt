package com.example.s_eda_app.db

import android.content.Context
import android.content.Intent
import com.example.s_eda_app.activities.MainActivity
import com.example.s_eda_app.entity.User

class SharedPrefManager private constructor(context: Context) {
    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences?.getString(KEY_USERNAME, null) != null
        }

    val user: User
        get() {
            val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
                sharedPreferences!!.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getInt(KEY_CALORIES, -1),

            )
        }
    init {
        ctx = context
    }

    fun userLogin(user: User) {
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putInt(KEY_ID, user.id)
        editor?.putString(KEY_USERNAME, user.nickName)
        editor?.apply()
    }
    fun setBreakfastId (id:Int){
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putInt(KEY_BREAKFAST, id)
    }
    fun setDinnerId (id:Int){
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putInt(KEY_DINNER, id)
    }
    fun setLunchId (id:Int){
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putInt(KEY_LUNCH, id)
    }
    fun deleteDayDishes(){
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.remove(KEY_BREAKFAST)?.apply()
        editor?.remove(KEY_DINNER)?.apply()
        editor?.remove(KEY_LUNCH)?.apply()
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
        private const val KEY_USERNAME = "keyusername"
        private const val KEY_ID = "keyid"
        private const val KEY_CALORIES = "calories"
        private const val KEY_BREAKFAST = "breakfast"
        private const val KEY_DINNER = "dinner"
        private const val KEY_LUNCH = "lunch"
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
