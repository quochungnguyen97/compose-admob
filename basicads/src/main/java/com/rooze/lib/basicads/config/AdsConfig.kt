package com.rooze.lib.basicads.config

import com.google.android.gms.ads.nativead.NativeAdOptions

object AdsConfig {
    private var _bannerId: String = ""
    val bannerId: String get() = _bannerId

    private var _interId: String = ""
    val interId: String get() = _interId

    private var _nativeId: String = ""
    val nativeId: String get() = _nativeId

    private var _nativeAdChoicesPosition: Int = NativeAdOptions.ADCHOICES_TOP_LEFT
    val nativeAdChoicesPosition: Int get() = _nativeAdChoicesPosition

    fun configIds(
        bannerId: String = "ca-app-pub-3940256099942544/6300978111",
        interId: String = "ca-app-pub-3940256099942544/1033173712",
        nativeId: String = "ca-app-pub-3940256099942544/2247696110",
        nativeAdChoicesPosition: Int = NativeAdOptions.ADCHOICES_TOP_LEFT,
    ) {
        _bannerId = bannerId
        _interId = interId
        _nativeId = nativeId
        _nativeAdChoicesPosition = nativeAdChoicesPosition
    }
}