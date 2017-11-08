package com.yhao.commen

import android.app.Application
import com.yhao.commen.notNullSingleValue


class App : Application() {


    companion object {
        var instance: App by notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}