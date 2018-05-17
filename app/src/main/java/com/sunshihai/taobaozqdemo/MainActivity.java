package com.sunshihai.taobaozqdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.sunshihai.taobaozqdemo.Utils.Utils;
import com.sunshihai.taobaozqdemo.event.BaseEvent;
import com.sunshihai.taobaozqdemo.event.QueryListStatusEvent;
import com.sunshihai.taobaozqdemo.service.QueryListStatusService;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {


    public final static String TYPELIST="TYPELIST";
    public final static String TYPEDETAIL="TYPEDETAIL";


    WebView webView;
    WindowsLoad loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);
        loadingView = findViewById(R.id.loadingView);
        initWebView();
        EventBus.getDefault().register(this);

    }

    ArrayList<String> orderList;
    int count=0;
    public void onEventMainThread(QueryListStatusEvent event) {


        switch (event.getStatus()){
            case BaseEvent.SUCESS:
                if(event.getType().equals(TYPELIST)){
                    orderList= Utils.getOrDerIdList(event.getContent());


                    Log.i("orderList 的长度 ",orderList.size()+"");

                }else if(event.getType().equals(TYPEDETAIL)){

                    saveFile(event.getContent(),"detial"+count);

                    Log.i("抓取的数据详情 ",event.getContent()+"");


                    count++;
                }
                if(count>=orderList.size()){

                    Intent intent =new Intent(MainActivity.this,SearchActivity.class);
                    intent.putExtra("cookie",event.getContent());
                    startActivity(intent);
                    MainActivity.this.finish();
                }else{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    webView.loadUrl(Config.BoughtDetial+orderList.get(count));
                }
                break;
            case BaseEvent.FIELD:
            case BaseEvent.EXCEPTION:
                break;
        }
    }


    public static void saveFile(String str, String fileName) {
        // 创建String对象保存文件名路径
        try {
            // 创建指定路径的文件
            File file = new File(Environment.getExternalStorageDirectory(), fileName+".txt");
            // 如果文件不存在
            if (file.exists()) {
                // 创建新的空文件
                file.delete();
            }
            file.createNewFile();
            // 获取文件的输出流对象
            FileOutputStream outStream = new FileOutputStream(file);
            // 获取字符串对象的byte数组并写入文件流
            outStream.write(str.getBytes());
            // 最后关闭文件输出流
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAppCacheEnabled(true);
        webView.loadUrl(Config.TaoBaoLoginURl);

        settings.setSupportZoom(true);
        settings.setSavePassword(false);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int progress) {
                super.onProgressChanged(view, progress);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;
                Log.i("shouldOverrid URL", url);

                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                        return true;
                    } else {
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);


            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                CookieManager cookieManager = CookieManager.getInstance();
                String cookie = cookieManager.getCookie(url);
                String finishUrl = "http://h5.m.taobao.com/mlapp/mytaobao.html#mlapp-mytaobao";
                Log.i("WEBVIEW URL", url);
                Log.i("cookie", cookie);

                if (url.equals(finishUrl)) {
                    webView.setVisibility(View.GONE);
                    loadingView.setVisibility(View.VISIBLE);
                    webView.loadUrl("http://h5.m.taobao.com/mlapp/olist.html");
                }


                if(url.startsWith(Config.TaoBaoOlist)){
                    webView.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    while(true){
                                        webView.dispatchTouchEvent(MotionEvent.obtain(354277422, 354277422, MotionEvent.ACTION_DOWN, (float)659.542, (float)1362.1241, 0) );
                                        webView.dispatchTouchEvent(MotionEvent.obtain(354277422, 354277439, MotionEvent.ACTION_MOVE, (float)659.542, (float)1362.1241, 0) );
                                        webView.dispatchTouchEvent(MotionEvent.obtain(354277422, 354277465, MotionEvent.ACTION_MOVE, (float)660.78876, (float)1347.1727, 0) );
                                        webView.dispatchTouchEvent(MotionEvent.obtain(354277422, 354277481, MotionEvent.ACTION_MOVE, (float)662.62646, (float)1318.0493, 0) );
                                        webView.dispatchTouchEvent(MotionEvent.obtain(354277422, 354277498, MotionEvent.ACTION_MOVE, (float)667.0155, (float)1269.0256, 0) );
                                        webView.dispatchTouchEvent(MotionEvent.obtain(354277422, 354277515, MotionEvent.ACTION_MOVE, (float)672.7567, (float)1194.25, 0) );
                                        webView.dispatchTouchEvent(MotionEvent.obtain(354277422, 354277532, MotionEvent.ACTION_MOVE, (float)678.1326, (float)1112.8976, 0) );
                                        webView.dispatchTouchEvent(MotionEvent.obtain(354277422, 354277546, MotionEvent.ACTION_MOVE, (float)682.4011, (float)1043.4987, 0) );
                                        webView.dispatchTouchEvent(MotionEvent.obtain(354277422, 354277565, MotionEvent.ACTION_MOVE, (float)692.90967, (float) 917.7894, 0) );
                                        webView.dispatchTouchEvent(MotionEvent.obtain(354277422, 354277582, MotionEvent.ACTION_MOVE, (float)708.76587, (float)822.702, 0) );
                                        webView.dispatchTouchEvent(MotionEvent.obtain(354277422, 354277586, MotionEvent.ACTION_MOVE, (float)714.25397, (float)792.3468, 0) );
                                        webView.dispatchTouchEvent(MotionEvent.obtain(354277422, 354277599, MotionEvent.ACTION_UP, (float)714.25397, (float)792.3468, 0) );

                                    }
                                }
                            }).start();
                        }
                    },200);



                }



                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }


            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }


            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                String targetUrl = request.getUrl().toString();
                Log.i("WebResourceResponse URL", targetUrl);
                if (targetUrl != null && targetUrl.startsWith(Config.BoughtListApi)) {

                    CookieManager cookieManager = CookieManager.getInstance();
                    String cookie = cookieManager.getCookie(targetUrl);
                    if(orderList==null){
                        startService(QueryListStatusService.getIntent(MainActivity.this,cookie,request.getMethod(),targetUrl,request.getRequestHeaders(),TYPELIST));
                    }
                }
                if(targetUrl != null && targetUrl.startsWith(Config.BoughtDetialApi)){
                    CookieManager cookieManager = CookieManager.getInstance();
                    String cookie = cookieManager.getCookie(targetUrl);
                    startService(QueryListStatusService.getIntent(MainActivity.this,cookie,request.getMethod(),targetUrl,request.getRequestHeaders(),TYPEDETAIL));
                }
                return super.shouldInterceptRequest(view, request);
            }
        });
    }
}
