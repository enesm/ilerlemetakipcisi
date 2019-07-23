package com.enes.ilerlemetakipcisi;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import static com.enes.ilerlemetakipcisi.MainActivity.db;

public class KayitOlustur extends AppCompatActivity {

    protected final static boolean DEBUG = false;
    protected final static String TAG = "KayitOlustur";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_olustur);

        setTitle("Kayıt Ekle");

        Button butonKayitIptal = (Button) findViewById(R.id.butonKayitIptal);
        Button butonKayitEkle = (Button) findViewById(R.id.butonKayitEkle);
        final Spinner acilirListe = (Spinner) findViewById(R.id.acilirListeBirimler);
        final Spinner acilirListeRenkler = (Spinner) findViewById(R.id.acilirListeRenklendirme);

        List<Birim> birimler = db.getBirimler();
        final List<String> items = new ArrayList<>();
        for(int i=0; i<birimler.size();i++){
                items.add(birimler.get(i).getBirim());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acilirListe.setAdapter(adapter);

        final List<String> renklendirme = new ArrayList<>();
        renklendirme.add("Yok");
        renklendirme.add("Limite yaklaştıkça yeşil");
        renklendirme.add("Sıfıra yaklaştıkça yeşil");
        ArrayAdapter<String> renkadapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,renklendirme);
        renkadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acilirListeRenkler.setAdapter(renkadapter);

        butonKayitIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        butonKayitEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText inputIsim = (EditText)findViewById(R.id.inputKayitOlusturIsim);
                final EditText inputBaslangic = (EditText)findViewById(R.id.inputKayitOlusturBaslangic);
                final EditText inputArtis = (EditText)findViewById(R.id.inputKayitOlusturArtis);
                final EditText inputLimit = (EditText)findViewById(R.id.inputKayitOlusturLimit);
                if (DEBUG){
                    Log.i(TAG,inputIsim.getText().toString());
                    Log.i(TAG,inputBaslangic.getText().toString());
                    Log.i(TAG,inputArtis.getText().toString());
                    Log.i(TAG,inputLimit.getText().toString());
                }

                if (inputIsim.getText().toString().trim().length() != 0 && // İsim sadece boşluklardan oluşmuyorsa
                        inputBaslangic.getText().toString().trim().length() != 0 && // Başlangıç Değeri sadece bışluklardan oluşmuyorsa
                        inputArtis.getText().toString().trim().length() != 0 && // Değişim Miktarı sadece boşluklardan oluşmuyorsa
                        inputLimit.getText().toString().trim().length() != 0 && // Limit sadece boşluklardan oluşmuyorsa
                        Integer.parseInt(inputArtis.getText().toString()) > 0 // Değişim Miktarı sıfırdan büyükse
                        ){
                    DBHandler db = new DBHandler(getApplicationContext());
                    int birim_id = db.getBirimId(acilirListe.getSelectedItem().toString());
                    String renk_secenegi = acilirListeRenkler.getSelectedItem().toString();
                    if(DEBUG) {
                        Log.i(TAG, renk_secenegi);
                    }
                    int renk_id = 0;
                    if(renk_secenegi.equals("Yok")){
                        renk_id = 0;
                    }
                    else if (renk_secenegi.equals("Limite yaklaştıkça yeşil")){
                        renk_id = 1;
                    }
                    else if (renk_secenegi.equals("Sıfıra yaklaştıkça yeşil")){
                        renk_id = 2;
                    }
                    Kayit kayit = new Kayit(inputIsim.getText().toString(),Integer.parseInt(inputBaslangic.getText().toString()),
                            birim_id, Integer.parseInt(inputArtis.getText().toString()),Integer.parseInt(inputLimit.getText().toString()),renk_id);
                    db.ekleKayit(kayit);
                    MainActivity.listeAdaptorYenile();
                    Toast.makeText(getApplicationContext(), "Kayıt oluşturuldu.", Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Lütfen girdileri kontrol edin.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
