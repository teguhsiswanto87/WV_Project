package id.codelabs.wvproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewTreeObserver
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val link = "http://whmnk.adipatikresna.co.id/"
    private lateinit var mOnScrollChangedListener: ViewTreeObserver.OnScrollChangedListener

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview.loadUrl(link)
        webview.settings.javaScriptEnabled = true
        webview.settings.setSupportZoom(false)

        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                swipeRefresh.isRefreshing = false
                super.onPageFinished(view, url)
            }


        }

        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

            }
        }

        //swipe resfresh
        swipeRefresh.setOnRefreshListener {
            webview.loadUrl("javascript:window.location.reload(true)")
        }

    }

    override fun onStart() {
        super.onStart()
        mOnScrollChangedListener = object : ViewTreeObserver.OnScrollChangedListener {
            override fun onScrollChanged() {
                swipeRefresh.isEnabled = webview.scrollY == 0
            }
        }
        swipeRefresh.viewTreeObserver.addOnScrollChangedListener(mOnScrollChangedListener)
    }

    override fun onStop() {
        super.onStop()
        swipeRefresh.viewTreeObserver.removeOnScrollChangedListener(mOnScrollChangedListener)
    }

    override fun onBackPressed() {

        if (webview.canGoBack()) {
            webview.goBack()
        } else {
            finish()
        }
    }
}
