package com.br.pix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class FazerActivity extends AppCompatActivity {
    TextView txtDados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer);
        //
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("LER O QRCODE");
        getSupportActionBar().setSubtitle("Mostre para pessoa que vai te pagar!");
        // inicio banner
        AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId("ca-app-pub-2381321693293442/1383284188");
        mAdView = findViewById(R.id.adView_fazer);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        // final banner
        txtDados = (TextView) findViewById(R.id.txtDados);
        //
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(FazerActivity.this);
        SharedPreferences.Editor myEditor = myPreferences.edit();
        String nome_do_banco = myPreferences.getString("NOME_BANCO", "unknown");
        String nome_do_pix   = myPreferences.getString("NOME_PIX", "unknown");
        txtDados.setText("Banco de Destino: "+nome_do_banco+"\n"+"PIX de destino: "+nome_do_pix);
        //
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://chart.googleapis.com/chart?cht=qr&chl="+nome_do_pix+"&chs=180x180&choe=UTF-8&chld=L|2");

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}