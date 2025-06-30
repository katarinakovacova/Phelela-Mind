package com.example.phelela_mind

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.phelela_mind.ui.MainScreen
import com.example.phelela_mind.ui.theme.NavigationDrawerJetpackComposeTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var darkThemeEnabled by rememberSaveable { mutableStateOf(false) }

            NavigationDrawerJetpackComposeTheme(darkTheme = darkThemeEnabled) {
                val onAppPaused: (() -> Unit)? by remember { mutableStateOf(null) }
                val onAppResumed: (() -> Unit)? by remember { mutableStateOf(null) }

                val lifecycleOwner = LocalLifecycleOwner.current

                DisposableEffect(lifecycleOwner) {
                    val observer = LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
                        when (event) {
                            Lifecycle.Event.ON_STOP -> onAppPaused?.invoke()
                            Lifecycle.Event.ON_START -> onAppResumed?.invoke()
                            else -> {}
                        }
                    }

                    lifecycleOwner.lifecycle.addObserver(observer)

                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                }

                MainScreen(
                    darkThemeEnabled = darkThemeEnabled,
                    onToggleDarkTheme = { enabled -> darkThemeEnabled = enabled }
                )
            }
        }
    }
}
