package com.example.sumativa

import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

class VideoPlayerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Obtener el URL del video pasado como argumento
        val videoId = intent.getStringExtra("VIDEO_ID") ?: ""

        // Inicializar el WebView para reproducir el video
        val webView = WebView(this).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.cacheMode = WebSettings.LOAD_NO_CACHE

            webChromeClient = object : WebChromeClient() {
                override fun onPermissionRequest(request: PermissionRequest) {
                    request.grant(request.resources) // Conceder permisos necesarios
                }
            }

            webViewClient = WebViewClient() // Manejar el contenido dentro del WebView

            // Cargar la URL del video de YouTube
            val videoUrl = "https://www.youtube.com/embed/$videoId?cc_load_policy=1&cc_lang_pref=es&autoplay=1"
            loadUrl(videoUrl)
        }

        // Definir la vista en pantalla completa
        setContentView(webView)
    }
}
