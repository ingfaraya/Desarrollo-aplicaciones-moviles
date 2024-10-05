package com.example.sumativa

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

class PDFWebViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pdfUrl = intent.getStringExtra("PDF_URL") ?: ""

        setContent {
            PDFWebViewScreen(pdfUrl)
        }
    }

    @Composable
    fun PDFWebViewScreen(pdfUrl: String) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.cacheMode = WebSettings.LOAD_NO_CACHE
                    settings.setSupportZoom(true)
                    settings.builtInZoomControls = true
                    settings.displayZoomControls = false
                    webViewClient = WebViewClient()

                    // Cargar PDF usando la vista local de PDF.js desde los assets
                    val pdfViewerUrl = "file:///android_asset/pdfjs/web/viewer.html?file=$pdfUrl"
                    loadUrl(pdfViewerUrl)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
