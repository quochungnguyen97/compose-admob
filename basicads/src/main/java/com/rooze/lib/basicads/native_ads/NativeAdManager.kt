package com.rooze.lib.basicads.native_ads

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.rooze.lib.basicads.config.AdsConfig
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NativeAdManager(private val context: Context) {
    suspend fun loadNativeAd(onAdClicked: () -> Unit = {}, onAdShown: () -> Unit = {}): NativeAd? =
        suspendCoroutine { continuation ->
            AdLoader.Builder(context, AdsConfig.nativeId)
                .forNativeAd { ad: NativeAd ->
                    continuation.resume(ad)
                }
                .withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        continuation.resume(null)
                    }

                    override fun onAdClicked() {
                        super.onAdClicked()
                        onAdClicked()
                    }

                    override fun onAdImpression() {
                        super.onAdImpression()
                        onAdShown()
                    }
                })
                .withNativeAdOptions(
                    NativeAdOptions.Builder()
                        .setAdChoicesPlacement(AdsConfig.nativeAdChoicesPosition)
                        .build()
                )
                .build()
                .loadAd(AdRequest.Builder().build())
        }
}