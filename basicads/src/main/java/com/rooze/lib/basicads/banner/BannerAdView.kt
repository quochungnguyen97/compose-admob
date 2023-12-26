package com.rooze.lib.basicads.banner

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.rooze.lib.basicads.config.AdsConfig
import com.rooze.lib.basicads.data.AdState
import com.valentinilk.shimmer.shimmer

private const val TAG = "BannerAdView"

@Composable
fun BannerAdView(
    modifier: Modifier = Modifier,
    id: String = AdsConfig.bannerId,
    loadingBg: Color = Color.Gray.copy(alpha = 0.5f),
    loadingContentBg: Color = Color.Gray
) {
    var adState: AdState by remember {
        mutableStateOf(AdState.INIT)
    }

    if (adState == AdState.FAILED) {
        return
    }

    Box(
        modifier = modifier
            .defaultMinSize(minHeight = 60.dp),
        contentAlignment = Alignment.Center
    ) {
        val config = LocalConfiguration.current
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize(config.screenWidthDp, 60))
                    adUnitId = id
                    adState = AdState.LOADING
                    loadAd(AdRequest.Builder().build())
                    adListener = object : AdListener() {
                        override fun onAdLoaded() {
                            super.onAdLoaded()
                            adState = AdState.SUCCESS
                        }

                        override fun onAdFailedToLoad(p0: LoadAdError) {
                            super.onAdFailedToLoad(p0)
                            Log.e(TAG, "onAdFailedToLoad: ${p0.message}")
                            adState = AdState.FAILED
                        }
                    }
                }
            }
        )
        if (adState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(loadingBg)
                    .shimmer()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .height(20.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(loadingContentBg)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .height(20.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(loadingContentBg)
                    )
                }

                Text(
                    text = "Ad",
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomEnd = 10.dp))
                        .background(Color(0xffff8e00))
                        .padding(start = 3.dp, end = 5.dp, top = 2.dp, bottom = 2.dp),
                    color = Color.White
                )
            }
        }
    }
}