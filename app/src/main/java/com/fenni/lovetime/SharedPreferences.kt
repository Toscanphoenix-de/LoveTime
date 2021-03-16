package com.fenni.lovetime

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context : Context, string: String) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(string,0)


    // Put in //

    fun save(KEY_NAME : String, value : Int){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(KEY_NAME,value)
        editor.apply()
    }
    fun save(KEY_NAME : String, value : String){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_NAME,value)
        editor.apply()
    }
    fun save(KEY_NAME : String, value : Boolean){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(KEY_NAME,value)
        editor.apply()
    }

    // Retrieve

    fun getValueString(KEY_NAME : String) : String?{
        return sharedPreferences.getString(KEY_NAME, "Nothing here. Change it in Settings" )
    }

    fun getValueInt(KEY_NAME : String) : Int {
        return sharedPreferences.getInt(KEY_NAME, -1)
    }

    fun getValueBoolean(KEY_NAME : String) : Boolean {
        return sharedPreferences.getBoolean(KEY_NAME, false)
    }

    // Remove Data
    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }

    fun clearSharedPref() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }



}