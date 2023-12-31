# Compose Admob Guide
## Implementations
Add it in your root build.gradle at the end of repositories:
```groovy
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```
Add the dependency
```groovy
	dependencies {
	        implementation 'com.github.quochungnguyen97:compose-admob:1.2'
    		implementation("com.google.android.gms:play-services-ads:22.6.0")
	}
```
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
