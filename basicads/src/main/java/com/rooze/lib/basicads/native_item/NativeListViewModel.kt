package com.rooze.lib.basicads.native_item

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.nativead.NativeAd
import com.rooze.lib.basicads.data.AdState
import com.rooze.lib.basicads.native_ads.NativeAdManager
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NativeListViewModel(private val nativeAdManager: NativeAdManager) : ViewModel() {

    private var _nativeItems: MutableStateFlow<List<Pair<NativeAd?, AdState>>> =
        MutableStateFlow(emptyList())
    val nativeItems: StateFlow<List<Pair<NativeAd?, AdState>>> get() = _nativeItems

    fun loadAd(count: Int) {
        viewModelScope.launch {
            val availableCount = withContext(Dispatchers.Default) {
                nativeItems.value.count { (_, state) ->
                    state == AdState.SUCCESS || state == AdState.LOADING
                }
            }

            if (availableCount < count) {
                var requestCount = count - availableCount
                val nativeAds = HashMap<Int, Deferred<NativeAd?>>()
                val list = if (nativeItems.value.size < count) {
                    withContext(Dispatchers.Default) {
                        ArrayList<Pair<NativeAd?, AdState>>().apply {
                            addAll(nativeItems.value)
                            addAll((1 .. (count - nativeItems.value.size)).map { Pair(null, AdState.INIT) })
                        }
                    }
                } else {
                    nativeItems.value
                }
                _nativeItems.value = list.mapIndexed { i, (nativeAd, state) ->
                    if (requestCount > 0 && (state == AdState.FAILED || state == AdState.INIT)) {
                        requestCount--
                        nativeAds[i] = async {
                            nativeAdManager.loadNativeAd()
                        }
                        Pair(null, AdState.LOADING)
                    } else {
                        Pair(nativeAd, state)
                    }
                }
                _nativeItems.value = list.mapIndexed { index, pair ->
                    if (index in nativeAds.keys) {
                        val result = nativeAds[index]?.await()
                        Pair(
                            result,
                            if (result != null) AdState.SUCCESS else AdState.FAILED
                        )
                    } else {
                        pair
                    }
                }
            }
        }
    }

    class Factory(appContext: Context) : ViewModelProvider.Factory {
        private val nativeAdManager = NativeAdManager(appContext)
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NativeListViewModel(nativeAdManager) as T
        }
    }
}