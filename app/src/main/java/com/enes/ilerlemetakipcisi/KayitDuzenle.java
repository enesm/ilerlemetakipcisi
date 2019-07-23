package com.enes.ilerlemetakipcisi;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import static com.enes.ilerlemetakipcisi.MainActivity.adaptor;
import static com.enes.ilerlemetakipcisi.MainActivity.db;
import static com.enes.ilerlemetakipcisi.MainActivity.listeAdaptorYenile;

/**
 * Created by Enes on 17.12.2017.
 */

public class KayitDuzenle extends AppCompatActivity{

    public int kayitId;
    public Kayit kayit;
    private static final boolean DEBUG = false;
    private static final String TAG = "KayitDuzenle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_olustur);

        setTitle("Kayıt Düzenle");
        kayitId = adaptor.getKayitId();
        final EditText inputIsim = (EditText)findViewById(R.id.inputKayitOlusturIsim);
        final EditText inputBaslangic = (EditText)findViewById(R.id.inputKayitOlusturBaslangic);
        final EditText inputArtis = (EditText)findViewById(R.id.inputKayitOlusturArtis);
        final EditText inputLimit = (EditText)findViewById(R.id.inputKayitOlusturLimit);
        final Button kayitKaydet = (Button)findViewById(R.id.butonKayitEkle);
        final Button kayitIptal = (Button)findViewById(R.id.butonKayitIptal);
        final Spinner acilirListe = (Spinner) findViewById(R.id.acilirListeBirimler);
        final Spinner acilirListeRenk = (Spinner) findViewById(R.id.acilirListeRenklendirme);
        kayitKaydet.setText("Kaydet");

        kayit = db.getKayit(kayitId);

        inputIsim.setText(kayit.getIsim());
        inputBaslangic.setText(String.valueOf(kayit.getDeger()));
        inputArtis.setText(String.valueOf(kayit.getDegisim_miktari()));
        inputLimit.setText(String.valueOf(kayit.getLimit()));
        List<Birim> birimler = db.getBirimler();
        final List<String> items = new ArrayList<>();
        for(int i=0; i<birimler.size();i++){
            items.add(birimler.get(i).getBirim());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        acilirListe.setAdapter(adapter);
        if (kayit.getBirim_id() == 1) {
            acilirListe.setSelection(0);
        }
        else {
            acilirListe.setSelection(adapter.getPosition(db.getBirim(kayit.getBirim_id()).getBirim()));
        }

        final List<String> renklendirme = new ArrayList<>();
        renklendirme.add("Yok");
        renklendirme.add("Limite yaklaştıkça yeşil");
        renklendirme.add("Sıfıra yaklaştıkça yeşil");
        ArrayAdapter<String> renkadapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,renklendirme);
        renkadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acilirListeRenk.setAdapter(renkadapter);
        acilirListeRenk.setSelection(kayit.getRenklendirme());

        kayitIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        kayitKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputIsim.getText().toString().trim().length() != 0 && // İsim sadece boşluklardan oluşmuyorsa
                        inputBaslangic.getText().toString().trim().length() != 0 && // Başlangıç Değeri sadece bışluklardan oluşmuyorsa
                        inputArtis.getText().toString().trim().length() != 0 && // Değişim Miktarı sadece boşluklardan oluşmuyorsa
                        inputLimit.getText().toString().trim().length() != 0 && // Limit sadece boşluklardan oluşmuyorsa
                        Integer.parseInt(inputArtis.getText().toString()) > 0 // Değişim Miktarı sıfırdan büyükse
                        ){
                    int birim_id = db.getBirimId(acilirListe.getSelectedItem().toString());
                    String renk_secenegi = acilirListeRenk.getSelectedItem().toString();
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

                    db.guncelleKayit(kayitId, inputIsim.getText().toString(), DBHandler.DEGER_ISIM);
                    db.guncelleKayit(kayitId, Integer.parseInt(inputBaslangic.getText().toString()), DBHandler.DEGER_DEGER);
                    db.guncelleKayit(kayitId, birim_id, DBHandler.DEGER_BIRIM_ID);
                    db.guncelleKayit(kayitId, Integer.parseInt(inputArtis.getText().toString()), DBHandler.DEGER_DEGISIM_MIKTARI);
                    db.guncelleKayit(kayitId, Integer.parseInt(inputLimit.getText().toString()), DBHandler.DEGER_LIMIT);
                    db.guncelleKayit(kayitId, renk_id,DBHandler.DEGER_RENKLENDIRME);
                    listeAdaptorYenile();
                    Toast.makeText(getApplicationContext(), "Kayıt düzenlendi.",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Lütfen girdileri kontrol edin.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
