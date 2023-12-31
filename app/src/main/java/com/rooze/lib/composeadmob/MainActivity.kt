package com.rooze.lib.composeadmob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rooze.lib.basicads.banner.BannerAdView
import com.rooze.lib.basicads.inter.InterAdManager
import com.rooze.lib.basicads.native_ads.LargeLoadingView
import com.rooze.lib.basicads.native_ads.NativeAdViewModel
import com.rooze.lib.basicads.native_ads.NativeAdView
import com.rooze.lib.composeadmob.ui.theme.ComposeAdmobTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nativeViewModel: NativeAdViewModel by viewModels {
            NativeAdViewModel.Factory(applicationContext)
        }
        val interAdManager = InterAdManager.getInstance(applicationContext)
        interAdManager.loadInter()

        setContent {
            ComposeAdmobTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "main") {
                        composable(route = "main") {
                            MainScreen(nativeViewModel = nativeViewModel) {
                                interAdManager.showInterAd(this@MainActivity) {
                                    navController.navigate("next")
                                }
                            }
                        }
                        composable(route = "next") {
                            ListScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListScreen() {

}

@Composable
fun MainScreen(
    nativeViewModel: NativeAdViewModel,
    onNextScreen: () -> Unit = {}
) {
    val nativeAd by nativeViewModel.nativeAd.collectAsState()
    val nativeState by nativeViewModel.adState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        nativeViewModel.loadNativeAd()
    }

    Column {
        BannerAdView(
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedCard(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            NativeAdView(
                modifier = Modifier.fillMaxWidth(),
                nativeAd = nativeAd,
                state = nativeState,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { onNextScreen() }) {
            Text(text = "Next screen with inter")
        }

        Spacer(modifier = Modifier.height(16.dp))
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
}
