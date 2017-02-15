package com.example.song.sweathor.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.song.sweathor.db.Shdb;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by song on 15-2-13.
 */
public class Sdb {
    public static final String DB_NAME = "SWeather";
    public static final int VERSION = 1;
    private static Sdb sdb;
    private SQLiteDatabase db;

    private Sdb(Context context) {
        Shdb shdb = new Shdb(context, DB_NAME, null, VERSION);
        db = shdb.getWritableDatabase();
    }

    public synchronized static Sdb getInstance(Context context) {
        if (sdb == null) {
            sdb = new Sdb(context);
        }
        return sdb;
    }

    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

    public List<Province> loadProvince() {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void savecity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getName());
            values.put("city_code", city.getCode());
            values.put("province_id", city.getpId());
            db.insert("City", null, values);
        }
    }

    public void savecounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", county.getName());
            values.put("county_code", county.getCode());
            values.put("city_id", county.getpId());
            db.insert("County", null, values);
        }
    }

    public List<City> loadCity(int provinceid) {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceid)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setpId(provinceid);
                list.add(city);
            }
            while (cursor.moveToNext());

        }
        return list;
    }

    public List<County> loadCounty(int cityid) {
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County", null, "city_id=?", new String[]{String.valueOf(cityid)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setpId(cityid);
                list.add(county);

            }
            while (cursor.moveToNext());

        }
        return list;
    }


}

