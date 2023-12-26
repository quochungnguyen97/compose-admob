package com.rooze.lib.composeadmob

import android.app.Application
import com.rooze.lib.basicads.config.AdsConfig

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AdsConfig.configIds(bannerId = BuildConfig.BANNER_ADS_ID)
    }
}