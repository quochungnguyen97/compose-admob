package com.rooze.lib.basicads.native_ads

import android.content.res.ColorStateList
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.rooze.lib.basicads.R

fun NativeAdView.init() {
    iconView = findViewById(R.id.ad_app_icon)
    headlineView = findViewById(R.id.ad_headline)
    bodyView = findViewById(R.id.ad_body)
    callToActionView = findViewById(R.id.ad_call_action)
    mediaView = findViewById(R.id.ad_media)
    starRatingView = findViewById(R.id.ad_rating)
    storeView = findViewById(R.id.ad_store)
    priceView = findViewById(R.id.ad_price)
}

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

    nativeAd.starRating?.let {
        (starRatingView as? RatingBar)?.rating = it.toFloat()
        starRatingView?.visibility = View.VISIBLE
    } ?: run {
        starRatingView?.visibility = View.GONE
    }

    nativeAd.store?.let {
        (storeView as? TextView)?.text = it
        storeView?.visibility = View.VISIBLE
    } ?: run {
        storeView?.visibility = View.GONE
    }

    nativeAd.price?.let {
        (priceView as? TextView)?.text = it
        priceView?.visibility = View.VISIBLE
    } ?: run {
        priceView?.visibility = View.GONE
    }
}

fun NativeAdView.updateColor(mainTextColor: Color?, subTextColor: Color?, actionColor: Color?, onActionColor: Color?) {
    subTextColor?.let { color ->
        (storeView as? TextView)?.setTextColor(color.toArgb())
    }
    mainTextColor?.let { color ->
        (headlineView as? TextView)?.setTextColor(color.toArgb())
        (bodyView as? TextView)?.setTextColor(color.toArgb())
    }
    actionColor?.let { color ->
        (priceView as? TextView)?.setTextColor(color.toArgb())
        (callToActionView as? Button)?.backgroundTintList = ColorStateList.valueOf(color.toArgb())
    }
    onActionColor?.let { color ->
        (callToActionView as? Button)?.setTextColor(color.toArgb())
    }
}

