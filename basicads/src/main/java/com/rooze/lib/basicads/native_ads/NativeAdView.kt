package com.rooze.lib.basicads.native_ads

import android.view.LayoutInflater
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.rooze.lib.basicads.R
import com.rooze.lib.basicads.data.AdState
import com.valentinilk.shimmer.shimmer

@Composable
fun NativeAdView(
    modifier: Modifier = Modifier,
    customLayout: Int = R.layout.native_ad,
    mainTextColor: Color? = null,
    subTextColor: Color? = null,
    actionColor: Color? = null,
    nativeAd: NativeAd?,
    state: AdState,
    loadingView: @Composable (Modifier) -> Unit = {
        LoadingView(it)
    }
) {
    if (state == AdState.FAILED) {
        return
    }

    if (state == AdState.LOADING) {
        loadingView(modifier)
        return
    }
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val nativeAdView = LayoutInflater.from(context)
                .inflate(customLayout, null, false) as NativeAdView
            nativeAdView.init()
            nativeAdView
        },
        update = { nativeAdView ->
            nativeAd?.let { nativeAd ->
                nativeAdView.bindNativeAd(nativeAd)
                nativeAdView.updateColor(mainTextColor, subTextColor, actionColor)
                nativeAdView.setNativeAd(nativeAd)
            }
        }
    )
}

@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    loadingBg: Color = Color.Gray.copy(alpha = 0.5f),
    loadingContentBg: Color = Color.Gray
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(loadingBg)
            .shimmer()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(loadingContentBg)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(loadingContentBg)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(loadingContentBg)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(loadingContentBg)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(loadingContentBg)
            )
        }
        Text(
            text = "Ad",
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 10.dp))
                .background(Color(0xffff8e00))
                .padding(start = 3.dp, end = 5.dp, top = 2.dp, bottom = 2.dp)
                .align(Alignment.TopEnd),
            color = Color.White,
        )
    }
}

@Composable
fun LargeLoadingView(
    modifier: Modifier,
    loadingBg: Color = Color.Gray.copy(alpha = 0.5f),
    loadingContentBg: Color = Color.Gray
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(loadingBg)
            .shimmer()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(loadingContentBg)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(loadingContentBg)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(loadingContentBg)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(loadingContentBg)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(loadingContentBg)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(loadingContentBg)
            )
        }
        Text(
            text = "Ad",
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 10.dp))
                .background(Color(0xffff8e00))
                .padding(start = 3.dp, end = 5.dp, top = 2.dp, bottom = 2.dp)
                .align(Alignment.TopEnd),
            color = Color.White,
        )
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    Column {
        LoadingView(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(12.dp))
        LargeLoadingView(modifier = Modifier.fillMaxWidth())
    }
}