package com.enes.ilerlemetakipcisi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enes on 15.12.2017.
 */

public class DBHandler extends SQLiteOpenHelper{

    private static final String DB_NAME = "DB_ILERLEME_TAKIPCISI";
    private static final String TABLO_KAYITLAR = "kayitlar";
    private static final String TABLO_BIRIMLER = "birimler";
    public static final String ID = "id";
    public static final String DEGER_ISIM = "isim";
    public static final String DEGER_DEGER = "deger";
    public static final String DEGER_BIRIM_ID = "birim_id";
    public static final String DEGER_DEGISIM_MIKTARI = "degisim_miktari";
    public static final String DEGER_LIMIT = "deger_limit";
    public static final String DEGER_RENKLENDIRME = "renklendirme";
    public static final String BIRIM_BIRIM = "birim";
    private static final boolean DEBUG = true;
    private static final String TAG = "DBHANDLER DEBUG ON --> ";

    public DBHandler (Context context) { super(context, DB_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ TABLO_KAYITLAR + " (" + ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ DEGER_ISIM +" TEXT, " + DEGER_DEGER +
                " INTEGER, " + DEGER_BIRIM_ID +" INTEGER, " + DEGER_DEGISIM_MIKTARI + " INTEGER, " + DEGER_LIMIT + " INTEGER, "+
                DEGER_RENKLENDIRME + " INTEGER"+")";
        String sql2= "CREATE TABLE "+ TABLO_BIRIMLER + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BIRIM_BIRIM + " TEXT)";
        if (DEBUG) {
            Log.i(TAG, "onCreate çalıştı.");
            Log.i(TAG,"SQL: "+sql);
            Log.i(TAG,"SQL: "+sql2);
        }
        db.execSQL(sql);
        db.execSQL(sql2);
        ContentValues birimler = new ContentValues();
        birimler.put(BIRIM_BIRIM, "- Yok -");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Saniye");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Dakika");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Saat");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Gün");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Hafta");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Ay");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Yıl");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Ders Saati");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Adet");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Tane");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Kez");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Kilometre");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Metre");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Santimetre");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Milimetre");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Litre");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Mililitre");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Kilogram");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Gram");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Miligram");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "kB");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "MB");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "GB");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "TB");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "Bardak");
        db.insert(TABLO_BIRIMLER, null, birimler);
        birimler.put(BIRIM_BIRIM, "At Gücü");
        db.insert(TABLO_BIRIMLER, null, birimler);
        if(DEBUG){
            Log.i(TAG,"Birimler eklendi:");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int eskiV, int yeniV) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLO_BIRIMLER);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLO_KAYITLAR);
        if(DEBUG){
            Log.i(TAG,"onUpgrade çalıştı.");
        }
        onCreate(db);
    }

    public int ekleKayit(Kayit girdi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues kayit = new ContentValues();
        kayit.put(DEGER_ISIM,girdi.getIsim());
        kayit.put(DEGER_DEGER,girdi.getDeger());
        kayit.put(DEGER_BIRIM_ID,girdi.getBirim_id());
        kayit.put(DEGER_DEGISIM_MIKTARI,girdi.getDegisim_miktari());
        kayit.put(DEGER_LIMIT,girdi.getLimit());
        kayit.put(DEGER_RENKLENDIRME,girdi.getRenklendirme());
        db.insert(TABLO_KAYITLAR, null, kayit);
        Cursor cursor = db.query(TABLO_KAYITLAR,new String[]{ID},null,null,null,null,"id DESC");
        cursor.moveToFirst();
        int kayitId = cursor.getInt(0);
        db.close();
        if(DEBUG){
            //Log.i(TAG,String.valueOf(kayitId));
            Log.i(TAG,"ekleKayit: "+girdi.getIsim()+" "+girdi.getDeger()+" "+girdi.getBirim_id()+" "+girdi.getDegisim_miktari()+" "+
                    girdi.getLimit()+" "+kayitId+ " " + girdi.getRenklendirme());
        }
        return kayitId;
    }

    public void silKayit(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLO_KAYITLAR, "id =" + id, null);
        db.close();
        if(DEBUG){
            Log.i(TAG,"silKayit: id:"+id);
        }
    }

    public void silKayitlar(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLO_KAYITLAR,null,null);
        db.close();
        if(DEBUG){
            Log.e(TAG,"Tüm kayıtlar silindi!");
        }
    }

    public void guncelleKayit(int id, int yeniValue, String columnAdi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(columnAdi,yeniValue);
        db.update(TABLO_KAYITLAR, cv, "id ="+id,null);
        if(DEBUG){
            Log.i(TAG,"Kayıt güncellendi Kayıt ID: "+String.valueOf(id));
        }
        db.close();
    }

    public void guncelleKayit(int id, String yeniValue, String columnAdi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(columnAdi,yeniValue);
        db.update(TABLO_KAYITLAR, cv, "id ="+id,null);
        if(DEBUG){
            Log.i(TAG,"Kayıt güncellendi Kayıt ID: "+String.valueOf(id));
        }
        db.close();
    }

    public void ekleBirim(Birim birim){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues icerik = new ContentValues();
        icerik.put(BIRIM_BIRIM, birim.getBirim());
        db.insert(TABLO_BIRIMLER, null, icerik);
        db.close();
        if(DEBUG){
            Log.i(TAG,"ekleBirim: "+birim.getBirim());
        }
    }

    public void silBirim(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLO_BIRIMLER, "id =" + id, null);
        db.close();
        if(DEBUG){
            Log.i(TAG,"silBirim: id:"+id);
        }
    }

    public void guncelleBirim(int id, String yeniValue, String columnAdi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(columnAdi,yeniValue);
        db.update(TABLO_BIRIMLER, cv, "id="+id,null);
        db.close();
        if(DEBUG){
            Log.i(TAG,"Birim güncellendi.");
        }
    }

    public List<Kayit> getKayitlar(){
        List<Kayit> kayitlar = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLO_KAYITLAR, new String[]{ID,DEGER_ISIM,DEGER_DEGER,DEGER_BIRIM_ID,DEGER_DEGISIM_MIKTARI,DEGER_LIMIT,DEGER_RENKLENDIRME},
        null, null, null, null, null);
        while(cursor.moveToNext()){
            Kayit kayit = new Kayit(cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5),cursor.getInt(6));
            kayit.setId(cursor.getInt(0));
            kayitlar.add(kayit);
            if(DEBUG){
                Log.e(TAG,"getKayitlar Çalıştı\nID: "+String.valueOf(kayit.getId())+"\nİsim: "+kayit.getIsim()+
                        "\nDeğer: "+String.valueOf(kayit.getDeger())+"\nDeğişim: "+String.valueOf(kayit.getDegisim_miktari()));
            }
        }
        db.close();
        return kayitlar;
    }

    public List<Birim> getBirimler(){
        List<Birim> birimler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLO_BIRIMLER, new String[]{ID, BIRIM_BIRIM}, null, null, null, null,
                null);
        while (cursor.moveToNext()){
            Birim birim = new Birim(cursor.getString(1));
            birim.setId(cursor.getInt(0));
            birimler.add(birim);
        }
        db.close();
        if(DEBUG){
            Log.i(TAG,"getBirimler: ");
        }
        return birimler;
    }

    public Kayit getKayit(int kayit_id){
        if (DEBUG){
            Log.i(TAG,"getKayit çalıştı.");
        }
        SQLiteDatabase db = getReadableDatabase();
        Kayit kayit = null;
        Cursor cursor = db.query(TABLO_KAYITLAR, new String []{ID,DEGER_ISIM,DEGER_DEGER,DEGER_BIRIM_ID,DEGER_DEGISIM_MIKTARI,DEGER_LIMIT, DEGER_RENKLENDIRME},
                null,null,null,null,null);
        while(cursor.moveToNext()){
            if(cursor.getInt(0) == kayit_id){
                kayit = new Kayit(cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6));
                break;
            }
        }
        db.close();
        return kayit;
    }

    public Birim getBirim(int birim_id){
        if (DEBUG){
            Log.i(TAG,"getBirim çalıştı.");
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Birim birim = new Birim("null");
        birim.setId(1);
        Cursor cursor = db.query(TABLO_BIRIMLER, new String[]{ID, BIRIM_BIRIM}, null,null,null,null,null);
        while (cursor.moveToNext()){
            if (cursor.getInt(0) == birim_id){
                birim.setBirim(cursor.getString(1));
                birim.setId(cursor.getInt(0));
                if(DEBUG){
                    Log.i(TAG,"getBirim: "+birim.getBirim());
                }
                break;
            }
        }
        db.close();
        return birim;
    }

    public int getBirimId(String birim_adi){
        if (DEBUG){
            Log.i(TAG,"getBirimId çalıştı.");
        }
        SQLiteDatabase db = this.getReadableDatabase();
        int id = 1;
        Cursor cursor = db.query(TABLO_BIRIMLER, new String[]{ID, BIRIM_BIRIM},null,null,null,null,null);
        while (cursor.moveToNext()){
            if(DEBUG) {
                Log.i(TAG, "CursorBirim: " + (cursor.getString(1)).toLowerCase() + "\nAranan: " + birim_adi.toLowerCase());
            }
            if ((cursor.getString(1)).toLowerCase().equals(birim_adi.toLowerCase())){
                id = cursor.getInt(0);
                break;
            }
        }
        db.close();
        return id;
    }

    public int getKayitRenklendirme(int kayit_id){
        if (DEBUG){
            Log.i(TAG,"getKayitRenklendirme çalıştı.");
        }
        SQLiteDatabase db = this.getReadableDatabase();
        int renklendirme_id = 0;
        Cursor cursor = db.query(TABLO_BIRIMLER, new String[]{ID, DEGER_RENKLENDIRME}, null,null,null,null,null);
        while (cursor.moveToNext()){
            if (cursor.getInt(0) == kayit_id){
                renklendirme_id = cursor.getInt(1);
                if(DEBUG){
                    Log.i(TAG,"getKayitRenklendirme: "+String.valueOf(renklendirme_id));
                }
                break;
            }
        }
        db.close();
        return renklendirme_id;
    }
}
