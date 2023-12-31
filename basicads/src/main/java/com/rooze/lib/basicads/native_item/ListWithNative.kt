package com.rooze.lib.basicads.native_item

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.ads.nativead.NativeAd
import com.rooze.lib.basicads.R
import com.rooze.lib.basicads.data.AdState
import com.rooze.lib.basicads.native_ads.NativeAdView
import com.valentinilk.shimmer.shimmer
import java.lang.Integer.min

@Composable
fun <T> ListWithNative(
    modifier: Modifier = Modifier,
    useId: Boolean = false,
    nativeCount: Int = 0,
    nativeRepeat: Int = 0,
    nativeShift: Int = 3,
    nativeView: @Composable (nativeAd: NativeAd?, state: AdState) -> Unit = { nativeAd, state ->
        NativeAdView(
            modifier = Modifier.fillMaxWidth(),
            nativeAd = nativeAd,
            state = state,
            customLayout = R.layout.native_ad_item
        ) {
            LoadingViewItem(modifier = it)
        }
    },
    items: List<WithNativeItem<T>> = emptyList(),
    itemView: @Composable (T) -> Unit = {},
) {
    val context = LocalContext.current
    val nativeViewModel: NativeListViewModel = viewModel(
        factory = NativeListViewModel.Factory(context.applicationContext)
    )
    val nativeAds: List<Pair<NativeAd?, AdState>> by nativeViewModel.nativeItems.collectAsState()

    val totalCount = min(nativeCount, items.size / nativeShift)

    LaunchedEffect(key1 = totalCount) {
        nativeViewModel.loadAd(totalCount)
    }

    LazyColumn(modifier = modifier) {
        items.chunked(nativeShift).forEachIndexed { index, chunked ->
            if (useId) {
                items(chunked) { item ->
                    itemView(item.data)
                }
            } else {
                items(chunked, key = { it.id }) { item ->
                    itemView(item.data)
                }
            }
            if (chunked.size >= nativeShift) {
                if (index < nativeAds.size) {
                    item {
                        nativeView(nativeAds[index].first, nativeAds[index].second)
                    }
                } else if (nativeRepeat > 0 && nativeAds.size * (nativeRepeat + 1) > index && nativeAds.isNotEmpty()) {
                    item {
                        nativeView(
                            nativeAds[index % nativeAds.size].first,
                            nativeAds[index % nativeAds.size].second
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingViewItem(
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
