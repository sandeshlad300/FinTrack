    package com.sandesh.fintrack

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.sandesh.fintrack.core.data.AppPreferences
import com.sandesh.fintrack.navigation.AppNavGraph
import com.sandesh.fintrack.ui.theme.FinTrackTheme

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {
    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        appPreferences = AppPreferences.getInstance(applicationContext)
        //fintrack theme add Drone CI Pipeline(using ngrok server)
        setContent {
            FinTrackTheme {
                    val navController = rememberNavController()
                    AppNavGraph(navController)
            }
        }
    }
}
