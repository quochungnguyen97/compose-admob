package com.rooze.lib.basicads.native_ads

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.nativead.NativeAd
import com.rooze.lib.basicads.data.AdState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NativeAdViewModel(private val nativeAdManager: NativeAdManager) : ViewModel() {

    private val _nativeAd: MutableStateFlow<NativeAd?> = MutableStateFlow(null)
    val nativeAd: StateFlow<NativeAd?> get() = _nativeAd

    private val _adState: MutableStateFlow<AdState> = MutableStateFlow(AdState.INIT)
    val adState: StateFlow<AdState> get() = _adState

    private var loadedTime: Long = 0L

    fun loadNativeAd() {
        if (!adState.value.shouldLoad && isInvalidTimeToLoad()) {
            return
        }

        _adState.value = AdState.LOADING

        viewModelScope.launch {
            val newNativeAd = nativeAdManager.loadNativeAd()

            if (newNativeAd != null) {
                loadedTime = System.currentTimeMillis()
                nativeAd.value?.destroy()
                _nativeAd.value = newNativeAd
                _adState.value = AdState.SUCCESS
            } else {
                _adState.value = AdState.FAILED
            }
        }
    }

    private fun isInvalidTimeToLoad(): Boolean {
        val diffTime = System.currentTimeMillis() - loadedTime
        return diffTime < 120000L
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NativeAdViewModel(NativeAdManager(context.applicationContext)) as T
        }
    }
}