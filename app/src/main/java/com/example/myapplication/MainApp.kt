package com.example.myapplication

import android.app.Application


class MainApp : Application() {
    var preference: Preference? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
       getDatabaseHandler()
        preference = Preference.buildInstance(this)
        if (preference?.firstInstall == false) {
            preference?.firstInstall = true
            preference?.setValueCoin(10)

        }

    }
    fun getDatabaseHandler() : DatabaseHandler = DatabaseHandler(this.applicationContext)

    companion object {
        private var instance: MainApp? = null

        @JvmStatic
        fun newInstance(): MainApp? {
            if (instance == null) {
                instance = MainApp()
            }
            return instance
        }
    }
}