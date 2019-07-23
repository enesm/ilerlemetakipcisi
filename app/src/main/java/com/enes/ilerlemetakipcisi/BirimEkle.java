package com.enes.ilerlemetakipcisi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.enes.ilerlemetakipcisi.MainActivity.db;
import static com.enes.ilerlemetakipcisi.MainActivity.listeAdaptorYenile;

public class BirimEkle extends AppCompatActivity {

    protected final static boolean DEBUG = false;
    protected final static String TAG = "BirimEkle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birim_ekle);

        setTitle("Birimleri Yönet");

        final Button butonBirimEkle = (Button) findViewById(R.id.butonBirimEkle);
        Button butonBirimIptal = (Button) findViewById(R.id.butonBirimIptal);
        final Button butonBirimSil = (Button) findViewById(R.id.butonBirimSil);
        final Spinner listeBirimler = (Spinner) findViewById(R.id.acilirListeBirimSil);

        List<Birim> birimler = db.getBirimler();
        final List<String> items = new ArrayList<>();
        for(int i=0; i<birimler.size();i++){
            if (!(birimler.get(i).getBirim().equals("- Yok -")) && !(birimler.get(i).getBirim().equals("null"))) {
                items.add(birimler.get(i).getBirim());
            }
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeBirimler.setAdapter(adapter);
        if(items.size()==0){
            butonBirimSil.setEnabled(false);
        }

        butonBirimSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String secilenBirim = listeBirimler.getSelectedItem().toString();
                db.silBirim(db.getBirimId(secilenBirim));
                Toast.makeText(getApplicationContext(),"Birim silindi.",Toast.LENGTH_SHORT).show();
                adapter.remove(secilenBirim);
                listeAdaptorYenile();
                if(items.size()==0){
                    butonBirimSil.setEnabled(false);
                }
            }
        });

        butonBirimIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        butonBirimEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText inputBirimAdi = (EditText) findViewById(R.id.inputBirimAdi);
                String birimAdi = inputBirimAdi.getText().toString();
                if (birimAdi.trim().length() > 0){
                    if(db.getBirimId(birimAdi) == 1 && !birimAdi.equals("- yok -")) { // 1 Dönmüşse bu isimde bir birim yok.
                        Birim birim = new Birim(birimAdi);
                        db.ekleBirim(birim);
                        inputBirimAdi.setText("");
                        adapter.add(birim.getBirim());
                        Toast.makeText(getApplicationContext(),"Birim eklendi.",Toast.LENGTH_SHORT).show();
                        if(items.size()>0){
                            butonBirimSil.setEnabled(true);
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Bu isimde bir birim zaten mevcut.",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Lütfen girdilieri kontrol edin.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
