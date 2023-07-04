package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences

class Preference(context: Context) {
    private val BEARER_HEADER = "Bearer0 "
    private val sharedPreferences: SharedPreferences
    private val PREFS_ACCOUNT = "PREFS_ACCOUNT"
    private val KEY_TYPE_ONE = "KEY_TYPE_ONE"
    private val KEY_TOTAL_COIN = "KEY_TOTAL_COIN" // coin
    private val KEY_FIRST_INSTALL = "KEY_FIRST_INSTALL" // coin
    private val INT_ZERO = 0 // coin
    private  val POS_ACTIVE = "POS_ACTIVE"
    private  val COIN = "COIN"


    init {
        sharedPreferences = context.getSharedPreferences(PREFS_ACCOUNT, Context.MODE_PRIVATE)
    }

    fun setValueTypeOne(value: String?) {
        sharedPreferences.edit().putString(KEY_TYPE_ONE, value).apply()
    }

    var firstInstall: Boolean
        get() = sharedPreferences.getBoolean(KEY_FIRST_INSTALL, false)
        set(value) {
            sharedPreferences.edit().putBoolean(KEY_FIRST_INSTALL, value).apply()
        }

    fun setValueCoin(value: Int) {
        sharedPreferences.edit().putInt(KEY_TOTAL_COIN, value).apply()
    }

    fun getValueCoin(): Int {
        return sharedPreferences.getInt(KEY_TOTAL_COIN, INT_ZERO)
    }

    fun getPosActive(): Int {
        return sharedPreferences.getInt(POS_ACTIVE, Constants.TOTAL_REQUEST)
    }

    fun setPosActive(value: Int) {
        sharedPreferences.edit().putInt(POS_ACTIVE, value).apply()
    }

    fun getCoin(): Int {
        return sharedPreferences.getInt(COIN, Constants.COIN_DEFAULT)
    }

    fun setCoin(value: Int) {
        sharedPreferences.edit().putInt(COIN, value).apply()
    }


    companion object {
        var instance: Preference? = null
        fun buildInstance(context: Context): Preference? {
            if (instance == null) {
                instance = Preference(context)
            }
            return instance
        }
    }
}