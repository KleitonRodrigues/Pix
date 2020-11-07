package com.br.pix;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.snackbar.Snackbar;

public class CadastroActivity extends AppCompatActivity {
    Button btn_1, btn_2;
    EditText campo_1, campo_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        //
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ARMAZENAR MEU PIX");
        getSupportActionBar().setSubtitle("Informe os dados de seu pix...");
        // inicio banner
        AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId("ca-app-pub-2381321693293442/1383284188");
        mAdView = findViewById(R.id.adView_cadastro);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        // final banner
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(CadastroActivity.this);
        String name_do_banco = myPreferences.getString("NOME_BANCO", "unknown");
        String name_do_pix   = myPreferences.getString("NOME_PIX", "unknown");
        //
        btn_1 = (Button) findViewById(R.id.btnReset);
        btn_2 = (Button) findViewById(R.id.btnGravar);
        campo_1 = (EditText) findViewById(R.id.txtBanco);
        campo_2 = (EditText) findViewById(R.id.txtPix);
        //
        if(name_do_banco == "unknown"){
            campo_1.setText("");
        }else{
            campo_1.setText(name_do_banco.toString());
        }
        if(name_do_pix == "unknown"){
            campo_2.setText("");
        }else{
            campo_2.setText(name_do_pix.toString());
        }

        // Salvar PIX
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gravar(view);
            }
        });
        // Limpar campos PIX
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campo_1.setText("");
                campo_2.setText("");
            }
        });
    }

    public void gravar(View view){
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(CadastroActivity.this);
        SharedPreferences.Editor myEditor = myPreferences.edit();
        myEditor.putString("NOME_BANCO",campo_1.getText().toString() );
        myEditor.putString("NOME_PIX", campo_2.getText().toString());
        myEditor.commit();
        salvar(view);
    }

    public void salvar(View view){
        if(campo_2.getText().toString().length() > 0 ){
            AlertDialog alert = new AlertDialog.Builder(CadastroActivity.this).create();
            alert.setTitle("Armazenados com Sucesso!");
            alert.setMessage("Banco Destino: "+campo_1.getText()+" \nSeu PIX: "+campo_2.getText()+" \n\n");
            alert.show();
            startActivity(new Intent( CadastroActivity.this, MainActivity.class));
        }else{
            campo_1.setFocusable(true);
            Snackbar.make(view, "Favor informar um valor!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}