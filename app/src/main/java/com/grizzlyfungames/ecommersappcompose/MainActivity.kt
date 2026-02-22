package com.grizzlyfungames.ecommersappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.grizzlyfungames.ecommersappcompose.ui.MainScreen
import com.grizzlyfungames.ecommersappcompose.ui.theme.ECommersAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ECommersAppComposeTheme {
                MainScreen()
            }
        }
    }
}

