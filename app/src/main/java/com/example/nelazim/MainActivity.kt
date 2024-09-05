package com.example.nelazim

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nelazim.pages.CurrencyStart
import com.example.nelazim.pages.PharmacyStart
import com.example.nelazim.pages.PrayerStart
import com.example.nelazim.pages.SplashScreenStart
import com.example.nelazim.pages.UserInfoPageStart
import com.example.nelazim.pages.WeatherPageStart


class MainActivity : ComponentActivity() {
    val data: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Screens()
        }
    }
}

@Composable
fun Screens() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val firstPage: String
    if (context.getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("il", "") == "") {
        firstPage = "UserInfoPage"
    } else {
        firstPage = "SplashScreenStart"
    }
    NavHost(navController = navController, startDestination = firstPage) {
        composable(route = "CurrencyStart") {
            CurrencyStart(navController = navController)
        }
        composable(route = "PharmacyStart") {
            PharmacyStart(navController = navController)
        }
        composable(route = "PrayerStart") {
            PrayerStart(navController = navController)
        }
        composable(route = "SplashScreenStart") {
            SplashScreenStart(navController)
        }
        composable(route = "UserInfoPageStart") {
            UserInfoPageStart(navController, modifier = Modifier.fillMaxSize())
        }
        composable(route = "WeatherPageStart") {
            WeatherPageStart(navController)
        }
    }
}

