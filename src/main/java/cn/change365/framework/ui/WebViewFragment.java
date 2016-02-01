package cn.change365.framework.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.change365.framework.R;
import cn.change365.framework.ui.base.BaseFragment;

public class WebViewFragment extends BaseFragment {

	private static final Logger log = LoggerFactory.getLogger(WebViewFragment.class);

	public static final String ARGS_URL = "toUrl";

	protected WebView webView;

	protected String homeUrl;

	protected boolean isHome;

	@Override
	protected void onMyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onMyCreateView(inflater, container, savedInstanceState);
		homeUrl = (String) getArguments().get(ARGS_URL);
		webView = (WebView) view;
		if(homeUrl != null){
			webView.setWebChromeClient(new WebChromeClient() {
				public boolean onConsoleMessage(ConsoleMessage cm) {
					ConsoleMessage.MessageLevel level = cm.messageLevel();
					log.warn("webview console msg = {} lineNumer = {} sourceId = {} level = {}", cm.message(), cm.lineNumber(), cm.sourceId(), level);
//					if (level.ordinal() == 3) { // Error ordinal
//						webView.stopLoading();
//
//					}
					return super.onConsoleMessage(cm);
				}
			});
			webView.setWebViewClient(customWebViewClient());
//			webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

			webView.getSettings().setJavaScriptEnabled(true);
			webView.getSettings().setSupportZoom(false);
			webView.getSettings().setBuiltInZoomControls(false);
//			webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//			webView.getSettings().setDefaultFontSize(18);
			webView.loadUrl(homeUrl);
		}
	}

	protected void doAfterChange(){

	}

	protected void refresh(){
		webView.loadUrl(homeUrl);
	}

	protected WebViewClient customWebViewClient(){
		return new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url){
				log.debug("shouldOverrideUrlLoading {}", url);
				if(canChange(url)){
					view.loadUrl(url);
					return false;
				}else{
					return true;
				}
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				if(homeUrl.equals(url)){
					isHome = true;
				}else{
					isHome = false;
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				doAfterChange();
			}

			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				if(getActivity() != null) {
					Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
				}
			}
		};
	}

	protected boolean canChange(String url) {
		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check if the key event was the Back button and if there's history
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(isHome){
				return false;
			}else if(webView.canGoBack()){
				webView.goBack();
				return true;
			}
		}
		// If it wasn't the Back key or there's no web page history, bubble up to the default
		// system behavior (probably exit the activity)
		return false;
	}


	@Override
	protected int getLayoutId() {
		return R.layout.fragment_webview;
	}

	@Override
	protected String getName() {
		return "WebViewFragment";
	}

}
