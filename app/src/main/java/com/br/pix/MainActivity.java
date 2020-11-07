package com.br.pix;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Button botao1, botao2, botao3;//, botao4, botao5, botao6;
    public String valor1, valor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // inicio banner
        AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId("ca-app-pub-2381321693293442/1383284188");
        mAdView = findViewById(R.id.adView_home);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        // final banner
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        final String valor1 = myPreferences.getString("NOME_BANCO", "unknown");
        final String valor2 = myPreferences.getString("NOME_PIX", "unknown");
        //
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               compartilhar(valor1, valor2);
            }
        });

        //
        botao1 = (Button) findViewById(R.id.btn1);
        botao2 = (Button) findViewById(R.id.btn2);
        botao3 = (Button) findViewById(R.id.btn3);
        //
        if(valor2 == "unknown"){
            botao3.setText("CADASTRAR MEU PIX");
            //botao3.setBackgroundTintList(ColorStateList.valueOf(0x00fFFFFF));
        }else{
            botao3.setText("ALTERAR MEU PIX");
            //botao3.setBackgroundColor(0x0077b8a4);
        }
        //
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        // botoes
        botao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ComoActivity.class));
            }
        });
        botao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("unknown" != valor2){
                    startActivity(new Intent(MainActivity.this, FazerActivity.class));
                }else{
                    cadastroAntes();
                }
            }
        });
        botao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));
            }
        });

        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                boolean handled = NavigationUI.onNavDestinationSelected(item, navController);

                if (!handled) {
                    switch (item.getItemId()) {//
                        case R.id.nav_menu_meupix: {
                            if(valor1 != "unknown"){
                                startActivity(new Intent(MainActivity.this, FazerActivity.class));
                            }else{
                                cadastroAntes();
                            }
                            break;
                        }
                        case R.id.nav_menu_cadastrar: {
                            startActivity(new Intent(MainActivity.this, CadastroActivity.class));
                            break;
                        }
                        case R.id.nav_menu_comofuncionapix: {
                            startActivity(new Intent(MainActivity.this, ComoActivity.class));
                            break;
                        }
                        case R.id.nav_menu_sobre: {
                            sobre();
                            break;
                        }
                        case R.id.nav_menu_rate: {
                            avaliar();
                            break;
                        }
                        case R.id.nav_menu_share: {
                            compartilharApp();
                            break;
                        }
                    }
                }

                drawer.closeDrawer(GravityCompat.START);
                return handled;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            compartilharApp();
        }
        if (id == R.id.action_rate) {
            avaliar();
        }
        if (id == R.id.action_about) {
            sobre();
        }
       // if (id == R.id.action_cad) {
        //    startActivity(new Intent(MainActivity.this, CadastroActivity.class));
       // }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void avaliar() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + "com.br.pix")));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.br.pix")));
        }
    }

    public void compartilhar(String texto1, String texto2){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        if(texto1 == "unknown"){
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"*Baixe Grátis Aplicativo PIX* \uD83D\uDCF2\uD83D\uDCB0 \n https://play.google.com/store/apps/details?id=com.br.pix");
        }else{
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Dados para pagamento:\n *Banco Destino:* "+texto1+" \n *PIX:* "+texto2+" \n\n*Baixe Grátis Aplicativo PIX* \uD83D\uDCF2\uD83D\uDCB0 \n https://play.google.com/store/apps/details?id=com.br.pix");
        }

        if(texto2 == "unknown"){
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"*Baixe Grátis Aplicativo PIX* \uD83D\uDCF2\uD83D\uDCB0 \n https://play.google.com/store/apps/details?id=com.br.pix");
        }else{
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Dados para pagamento:\n *Banco Destino:* "+texto1+" \n *PIX:* "+texto2+" \n\n*Baixe Grátis Aplicativo PIX* \uD83D\uDCF2\uD83D\uDCB0 \n https://play.google.com/store/apps/details?id=com.br.pix");
        }
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    public void compartilharApp(){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"*Baixe Grátis Aplicativo PIX* \uD83D\uDCF2\uD83D\uDCB0 \n https://play.google.com/store/apps/details?id=com.br.pix");
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    public void sobre(){
        AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
        alert.setTitle("Pix");
        alert.setMessage("Versão 1.0.3\nDesenvolvido por:\nkleiton@email.com");
        alert.show();
    }

    public void cadastroAntes() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Deseja cadastrar um PIX agora?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}