package com.enes.ilerlemetakipcisi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected final static boolean DEBUG = false;
    protected final static String TAG = "MAINACTVTY DEBUG ON -->";
    public static DBHandler db;
    public static CustomAdapter adaptor;
    protected List<Birim> tumBirimler;
    protected ListView listeKayitlar;
    public static int _listPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(DEBUG){
            Log.i(TAG,"MainActivity oluşturuldu.");
        }


        db = new DBHandler(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton butonEkle = (FloatingActionButton) findViewById(R.id.butonKayitEkle);
        butonEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kayitEkraninaGec();
            }
        });

        List<Kayit> tumKayitlar = db.getKayitlar();
        tumBirimler = db.getBirimler();
        if (DEBUG){
            Log.i(TAG,"kayit size = " + String.valueOf(tumKayitlar.size()));
            Log.i(TAG,"birim size = " + String.valueOf(db.getBirimler().size()));
            Log.i(TAG,"birim = "+ db.getBirimler().get(0).getBirim());
        }
        TextView textKayitYok = findViewById(R.id.textHicYok);
        listeKayitlar = findViewById(R.id.listeKayitlar);
        registerForContextMenu(listeKayitlar);
        adaptor = new CustomAdapter(this, tumKayitlar, tumBirimler, db);
        listeKayitlar.setAdapter(adaptor);
        listeKayitlar.setEmptyView(textKayitYok);
        //TODO Kayıt Ekle butonunun konumu pek sağlıklı değil.

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        _listPosition = info.position;      // uzun basılan itemin indexi
        if(DEBUG){
            Log.i(TAG,"Listedeki Indexi: "+String.valueOf(_listPosition)); // TODO ID al.
        }

        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Düzenle");
        menu.add(0, v.getId(), 1, "Sil");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        if(item.getTitle() == "Düzenle")
        {
            if(DEBUG){
                Log.i(TAG,"Düzenle seçildi");
            }
            kayitDuzenEkraninaGec();

        }
        else if(item.getTitle() == "Sil")
        {
            int silId = adaptor.getKayitId();
            if(DEBUG){
                Log.i(TAG,"Kayıt silindi: "+db.getKayit(silId).getIsim());
            }
            try {
                db.silKayit(silId);
                listeAdaptorYenile();
                Toast.makeText(this, "Kayıt silindi.", Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"Kayıt silinirken hata oluştu!",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            return false;
        }

        return true;
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_ekle_kayit){
            if(DEBUG){
                Log.i(TAG,"Kayıt ekle basıldı.");
            }
            kayitEkraninaGec();
        }
        else if (id == R.id.action_ekle_birim){
            if(DEBUG){
                Log.i(TAG,"Birim ekle basıldı");
            }
            birimEkleEkraninaGec();
        }

        return super.onOptionsItemSelected(item);
    }

    public static void listeAdaptorYenile (){
        adaptor.yenile();
    }

    public void kayitEkraninaGec(){
        Intent intent = new Intent(this, KayitOlustur.class);
        startActivity(intent);
    }

    public void kayitDuzenEkraninaGec(){
        Intent intent = new Intent(this, KayitDuzenle.class);
        startActivity(intent);
    }

    public void birimEkleEkraninaGec(){
        Intent intent = new Intent(this, BirimEkle.class);
        startActivity(intent);
    }
}
