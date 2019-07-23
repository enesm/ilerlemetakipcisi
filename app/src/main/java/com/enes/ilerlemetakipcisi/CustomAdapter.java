package com.enes.ilerlemetakipcisi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import static android.view.View.GONE;

/**
 * Created by Enes on 16.12.2017.
 */

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Kayit> mKayitListesi;
    private List<Birim> mBirimListesi;
    private DBHandler mDb;
    private Kayit kayit;
    private TextView textId;
    public int longPressItemId; // Basılı tutulan nesne id sini kayıt et. Aksi halde en son eklenen nesne id si hafızada tutuluyor.
    View satirView;
    private static final boolean DEBUG = true;
    private static final String TAG = "CUSTOMADAPTER DEBUG -->";

    public CustomAdapter(Activity activity, List<Kayit> kayitlar, List<Birim> birimler, DBHandler db) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mKayitListesi = kayitlar;
        mBirimListesi = birimler;
        mDb = db;
    }

    @Override
    public int getCount() {
        return mKayitListesi.size();
    }

    @Override
    public Kayit getItem(int i) {
        return mKayitListesi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mKayitListesi.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        satirView = mInflater.inflate(R.layout.activity_ozel_list, null);
        textId = (TextView) satirView.findViewById(R.id.textId);
        TextView textIsim = (TextView) satirView.findViewById(R.id.textIsim);
        TextView textDeger =(TextView) satirView.findViewById(R.id.textDeger);
        TextView textLimit =(TextView) satirView.findViewById(R.id.textLimit);
        TextView textBirim1=(TextView) satirView.findViewById(R.id.textBirim1);
        TextView textBirim2=(TextView) satirView.findViewById(R.id.textBirim2);
        TextView textSlash =(TextView) satirView.findViewById(R.id.textSlash);
        Button butonArti = (Button) satirView.findViewById(R.id.butonArti);
        Button butonEksi = (Button) satirView.findViewById(R.id.butonEksi);
        ImageButton butonSifirla = (ImageButton) satirView.findViewById(R.id.butonSifirla);

        kayit = mKayitListesi.get(position);
        Birim birim = mDb.getBirim(kayit.getBirim_id());
        final int kayitId = kayit.getId();
        final int kayitDeger = kayit.getDeger();
        final int kayitDegisim = kayit.getDegisim_miktari();
        final int birimId = birim.getId();
        final String birimAdi = birim.getBirim();
        if(!DEBUG) {
            textId.setVisibility(View.INVISIBLE); // Debug açıksa nesne idlerini köşesinde yaz.
        }
        textIsim.setText(kayit.getIsim());
        textDeger.setText(String.valueOf(kayitDeger));
        if (birimId != 1) { // birim id = 1 --> Birim: - Yok -
            textBirim1.setText(birimAdi);
        }
        else{
            textBirim1.setText("");
        }
        if (kayit.getLimit() > 0) { // Limit var ise
            textLimit.setText(String.valueOf(kayit.getLimit()));
            if(birimId != 1) { // birim id = 1 --> Birim: - Yok -
                textBirim2.setText(birimAdi);
            }
            else{
                textBirim2.setText("");
            }
            double oran = (Double.parseDouble(textDeger.getText().toString()) / Double.parseDouble(textLimit.getText().toString())) * 100.0;
            if(DEBUG) {
                Log.i(TAG, String.valueOf(oran));
            }
            if (oran < 25){
                if (kayit.getRenklendirme() == 1) {
                    textDeger.setTextColor(Color.parseColor("#d50000")); //Kırmızı
                    textBirim1.setTextColor(Color.parseColor("#d50000")); //Kırmızı
                }
                else if (kayit.getRenklendirme() == 2){
                    textDeger.setTextColor(Color.parseColor("#43a047")); //Yeşil
                    textBirim1.setTextColor(Color.parseColor("#43a047")); //Yeşil
                }
            }
            else if (oran < 50){
                if (kayit.getRenklendirme() == 1) {
                    textDeger.setTextColor(Color.parseColor("#f57f17")); //Turuncu
                    textBirim1.setTextColor(Color.parseColor("#f57f17")); //Turuncu
                }
                else if (kayit.getRenklendirme() == 2){
                    textDeger.setTextColor(Color.parseColor("#afb42b")); //Sarı
                    textBirim1.setTextColor(Color.parseColor("#afb42b")); //Sarı
                }
            }
            else if (oran < 75){
                if (kayit.getRenklendirme() == 1){
                    textDeger.setTextColor(Color.parseColor("#afb42b")); //Sarı
                    textBirim1.setTextColor(Color.parseColor("#afb42b")); //Sarı
                }
                else if (kayit.getRenklendirme() == 2){
                    textDeger.setTextColor(Color.parseColor("#f57f17")); //Turuncu
                    textBirim1.setTextColor(Color.parseColor("#f57f17")); //Turuncu
                }
            }
            else {
                if(kayit.getRenklendirme() == 1) {
                    textDeger.setTextColor(Color.parseColor("#43a047")); //Yeşil
                    textBirim1.setTextColor(Color.parseColor("#43a047")); //Yeşil
                }
                else if (kayit.getRenklendirme() == 2){
                    textDeger.setTextColor(Color.parseColor("#d50000")); //Kırmızı
                    textBirim1.setTextColor(Color.parseColor("#d50000")); //Kırmızı
                }
            }
        }
        else { // Limit yoksa
            textLimit.setVisibility(View.INVISIBLE); // GONE yer kaplamaz. INVIS yer tutar ama gözükmez.
            textSlash.setVisibility(View.INVISIBLE);
            textBirim2.setVisibility(View.INVISIBLE);
        }

        butonArti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // TODO Limit olayını düşün.
                kayit.setDeger(kayitDeger + kayitDegisim);
                mDb.guncelleKayit(kayitId, kayit.getDeger(), DBHandler.DEGER_DEGER);
                yenile();
                if(DEBUG){
                    Log.i(TAG, "Artı butonu kullanılan nesne ID: "+String.valueOf(kayitId)+"\nÖnceki Değer: "+kayitDeger
                            +"\nDeğişim: "+String.valueOf(kayitDegisim));
                }
            }
        });

        butonEksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // TODO Limit olayını düşün.
                kayit.setDeger(kayitDeger - kayitDegisim);
                mDb.guncelleKayit(kayitId, kayit.getDeger(), DBHandler.DEGER_DEGER);
                yenile();
                if(DEBUG){
                    Log.i(TAG,"Eksi butonu kullanılan nesne ID: "+String.valueOf(kayitId)+"\nÖnceki Değer: "+kayitDeger
                            +"\nDeğişim: "+String.valueOf(kayitDegisim));
                }
            }
        });

        butonSifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kayitDeger != 0) {
                    kayit.setDeger(0);
                    mDb.guncelleKayit(kayitId, kayit.getDeger(), DBHandler.DEGER_DEGER);
                    yenile();
                    if(DEBUG){
                        Log.i(TAG,"Sıfırla butonu kullanılan nesne ID:" +String.valueOf(kayitId)+"\nÖnceki Değer: "+kayitDeger);
                    }
                }
            }
        });
        textId.setText(String.valueOf(kayit.getId()));
        satirView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longPressItemId = kayitId;
                if(DEBUG) {
                    Log.i(TAG, String.valueOf(longPressItemId));
                }
                return false;
            }
        });

        return satirView;
    }

    public int getKayitId(){ // Son basılı tutulan nesne id sini döndür.
        return longPressItemId;
    }

    public void yenile(){
        mKayitListesi=mDb.getKayitlar();
        notifyDataSetChanged();
    }
}
