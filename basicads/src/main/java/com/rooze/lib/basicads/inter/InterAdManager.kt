package com.rooze.lib.basicads.inter

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.rooze.lib.basicads.config.AdsConfig

class InterAdManager(private val context: Context) {

    companion object {
        private const val TAG = "InterAdManager"
        private const val INTERVAL = 15000

        private var _instance: InterAdManager? = null
        fun getInstance(appContext: Context): InterAdManager {
            if (_instance == null) {
                _instance = InterAdManager(appContext)
            }
            return _instance!!
        }
    }

    private var _interAd: InterstitialAd? = null
    private val interAd: InterstitialAd? get() = _interAd

    private var loading: Boolean = false
    private var showTime: Long = 0L

    fun loadInter() {
        Log.d(TAG, "loadInter: $loading $interAd")
        if (loading || interAd != null) {
            return
        }
        val adRequest = AdRequest.Builder().build()

        loading = true
        InterstitialAd.load(
            context,
            AdsConfig.interId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, "onAdFailedToLoad: error ${adError.message}")
                    loading = false
                    _interAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    loading = false
                    _interAd = interstitialAd
                }
            }
        )
    }

    fun showInterAd(activity: Activity, onNext: () -> Unit) {
        Log.d(TAG, "showInterAd: $interAd $showTime")
        if (System.currentTimeMillis() - showTime < INTERVAL) {
            onNext()
            return
        }
        interAd?.let { interAd ->
            interAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    _interAd = null
                    onNext()
                    loadInter()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    showTime = System.currentTimeMillis()
                    _interAd = null
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    _interAd = null
                    onNext()
                }
            }
            interAd.show(activity)
        } ?: run {
            loadInter()
            onNext()
        }
    }
}

fun InterAdManager?.showInterAd(activity: Activity, onNext: () -> Unit) {
    this?.showInterAd(activity, onNext) ?: run {
        onNext()
    }
}