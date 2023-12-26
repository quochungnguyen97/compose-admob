package com.rooze.lib.basicads.native_ads

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

fun NativeAdView.bindNativeAd(nativeAd: NativeAd) {
    (headlineView as? TextView)?.text = nativeAd.headline

    nativeAd.body?.let {
        (bodyView as? TextView)?.text = it
        bodyView?.visibility = View.VISIBLE
    } ?: run {
        bodyView?.visibility = View.GONE
    }

    nativeAd.callToAction?.let {
        (callToActionView as? Button)?.text = nativeAd.callToAction
        callToActionView?.visibility = View.VISIBLE
    } ?: run {
        callToActionView?.visibility = View.GONE
    }

    nativeAd.mediaContent?.let {
        mediaView?.mediaContent = it
        mediaView?.visibility = View.VISIBLE
    } ?: run {
        mediaView?.visibility = View.GONE
    }

    nativeAd.icon?.let {
        (iconView as? ImageView)?.setImageDrawable(it.drawable)
        iconView?.visibility = View.VISIBLE
    } ?: run {
        iconView?.visibility = View.INVISIBLE
    }
}

