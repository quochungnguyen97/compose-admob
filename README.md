# Compose Admob Guide
## Config Ids
```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AdsConfig.configIds(bannerId = BuildConfig.BANNER_ADS_ID)
    }
}
```
## Banner
```kotlin
  BannerAdView(
      modifier = Modifier
          .fillMaxWidth()
  )
```
## Native
```kotlin
    val context = LocalContext.current
    val nativeViewModel: NativeAdViewModel by viewModels {
        NativeAdViewModel.Factory(context)
    }
    val nativeAd by nativeViewModel.nativeAd.collectAsState()
    val nativeState by nativeViewModel.adState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        nativeViewModel.loadNativeAd()
    }

    Column {
        ElevatedCard(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            NativeAdView(
                modifier = Modifier.fillMaxWidth(),
                nativeAd = nativeAd,
                state = nativeState,
                customLayout = com.rooze.lib.basicads.R.layout.native_ad_large,
                loadingView = {
                    LargeLoadingView(it)
                }
            )
        }
    }
```
