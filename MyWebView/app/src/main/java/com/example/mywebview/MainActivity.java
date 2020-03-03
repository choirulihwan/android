package com.example.mywebview;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.URLUtil;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    SwipeRefreshLayout swipe;
    private long downloadID;
    private String namaFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Runtime External storage permission for saving download files
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                Log.d("permission", "permission denied to WRITE_EXTERNAL_STORAGE - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);
            }
        }

        /* method 1*/
        /*webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                //System.out.println("test " + url);
                String search  = "export_pdf";
                //if(url.endsWith(".pdf")){
                if ( url.toLowerCase().indexOf(search.toLowerCase()) != -1 ) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
                return false;
            }
        });*/

        /* method 2*/
        /*webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                //System.out.println("test " + url);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });*/

        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //webView.reload();
                        LoadWeb();
                    }
                });

        LoadWeb();
    }

    private void LoadWeb() {
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        //webView.loadUrl("http://192.168.10.8/jquery-mobile/GoMobile%20-%20A%20next%20generation%20web%20app%20theme.html");
        //webView.loadUrl(Uri.parse("file:///android_asset/contoh.html").toString());
        if(haveNetworkConnection()){
            webView.loadUrl("http://www.drugvisions.com/tbs_marketing/");
        } else {
            webView.loadUrl("file:///android_asset/error.html");
        }
        swipe.setRefreshing(true);

        webView.setWebViewClient(new WebViewClient(){
            /*public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if(errorCode == ERROR_BAD_URL) {
                    webView.loadUrl("file:///android_asset/error.html");
                }
            }*/

            public  void  onPageFinished(WebView view, String url){
                //ketika loading selesai, ison loading akan hilang
                swipe.setRefreshing(false);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //loading akan jalan lagi ketika masuk link lain
                // dan akan berhenti saat loading selesai
                if(webView.getProgress()== 100){
                    swipe.setRefreshing(false);
                } else {
                    swipe.setRefreshing(true);
                }
            }
        });

        /* method 3 */
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setMimeType(mimetype);
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading File...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadID = dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Starting Download..", Toast.LENGTH_LONG).show();
                setNamaFile(url);
            }

        });
    }

    private void setNamaFile(String url){
                URI uri = null;
                namaFile = "";
                try {
                    uri = new URI(url);
                    String path = uri.getPath();
                    namaFile = path.substring(path.lastIndexOf('/') + 1);
                    namaFile = "pesanan_" + namaFile + ".pdf";

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id

            //get nama file
            if (downloadID == id) {
                Toast.makeText(MainActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Konfirmasi sharing");
                builder.setMessage("Apakah anda ingin melakukan sharing file" + namaFile);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shareFile(namaFile);
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                builder.show();
            }
        }
    };

    public void shareFile(String namaFile){

        File pdfFolder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            pdfFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        } else{
            pdfFolder = Environment.getExternalStorageDirectory();
        }

        File myFile = new File(pdfFolder.getAbsolutePath() + File.separator + namaFile);

        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        if(myFile.exists()) {

            intentShareFile.setType("application/pdf");
            //intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(myFile));
            Uri pdfUri = FileProvider.getUriForFile(MainActivity.this, "com.example.mywebview.file.provider", myFile);
            intentShareFile.putExtra(Intent.EXTRA_STREAM, pdfUri);
            //intentShareFile.putExtra(Intent.EXTRA_SUBJECT,"Sharing File from Webkul...");
            //intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File from Webkul to purchase items...");
            MainActivity.this.startActivity(Intent.createChooser(intentShareFile, "Send " + namaFile));
        }
    }


    @Override
    public void onBackPressed(){
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            finish();
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }
}
