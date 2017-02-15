package com.example.song.sweathor.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.song.sweathor.model.City;
import com.example.song.sweathor.model.County;
import com.example.song.sweathor.model.Province;
import com.example.song.sweathor.model.Sdb;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


/**
 * Created by song on 15-2-13.
 */
public class Untility {
    public synchronized static boolean handleProvince(Sdb sdb, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvince = response.split(",");
            if (allProvince != null && allProvince.length > 0) {
                for (String p : allProvince) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    sdb.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCity(Sdb sdb, String response, int provinceid) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCity = response.split(",");
            if (allCity != null && allCity.length > 0) {
                for (String p : allCity) {
                    String[] array = p.split("\\|");
                    City city = new City();
                    city.setCode(array[0]);
                    city.setName(array[1]);
                    city.setpId(provinceid);
                    sdb.savecity(city);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCounty(Sdb sdb, String response, int cityid) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounty = response.split(",");
            if (allCounty != null && allCounty.length > 0) {
                for (String p : allCounty) {
                    String[] array = p.split("\\|");
                    County county = new County();
                    county.setCode(array[0]);
                    county.setName(array[1]);
                    county.setpId(cityid);
                    sdb.savecounty(county);
                }
                return true;
            }
        }
        return false;
    }

    public static String getCode(String tmp) {
        String[] array = tmp.split("\\|");
        String Code = array[1];
        return Code;
    }

    public static void handleWeatherResponse(Context context, String response) {
        try {
            Log.e("song", "正在解析...");
            response = response.substring(17);
            Log.e("song", response);
            response = response.substring(0, response.length() - 1);
            Log.e("song", response);
            JSONObject jsonObject = new JSONObject(response);

            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String temp3 = weatherInfo.getString("temp3");
            String temp4 = weatherInfo.getString("temp4");
            String temp5 = weatherInfo.getString("temp5");
            String temp6 = weatherInfo.getString("temp6");

            String weather1 = weatherInfo.getString("weather1");
            String weather2 = weatherInfo.getString("weather2");
            String weather3 = weatherInfo.getString("weather3");
            String weather4 = weatherInfo.getString("weather4");
            String weather5 = weatherInfo.getString("weather5");
            String weather6 = weatherInfo.getString("weather6");

            String wind1 = weatherInfo.getString("wind1");
            String wind2 = weatherInfo.getString("wind2");
            String wind3 = weatherInfo.getString("wind3");
            String wind4 = weatherInfo.getString("wind4");
            String wind5 = weatherInfo.getString("wind5");
            String wind6 = weatherInfo.getString("wind6");


            String date = weatherInfo.getString("date_y");
            String chuanyi = weatherInfo.getString("index_d");
            String shushi = weatherInfo.getString("index_co_d");
            String ganmao = weatherInfo.getString("index_gm_d");
            String yundong = weatherInfo.getString("index_yd_d");
            String guomin = weatherInfo.getString("index_ag_d");
            String UV = weatherInfo.getString("index_uv_d");
            String yuehui = weatherInfo.getString("index_yh_d");
            String jiaotong = weatherInfo.getString("index_jt_d");
            String yusan = weatherInfo.getString("index_ys_d");
            String xiche = weatherInfo.getString("index_xc_d");
            String pm = weatherInfo.getString("pm-level");

            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putBoolean("city_selected", true);
            editor.putString("chuanyi", chuanyi);
            editor.putString("ganmao", ganmao);
            editor.putString("shushi", shushi);
            editor.putString("yundong", yundong);
            editor.putString("guomin", guomin);
            editor.putString("UV", UV);
            editor.putString("yuehui", yuehui);
            editor.putString("jiaotong", jiaotong);
            editor.putString("yusan", yusan);
            editor.putString("xiche", xiche);
            editor.putString("weather1", weather1);
            editor.putString("weather2", weather2);
            editor.putString("weather3", weather3);
            editor.putString("weather4", weather4);
            editor.putString("weather5", weather5);
            editor.putString("weather6", weather6);
            editor.putString("pm", pm);

            editor.putString("temp1", temp1);
            editor.putString("temp2", temp2);
            editor.putString("temp3", temp3);
            editor.putString("temp4", temp4);
            editor.putString("temp5", temp5);
            editor.putString("temp6", temp6);


            editor.putString("wind1", wind1);
            editor.putString("wind2", wind2);
            editor.putString("wind3", wind3);
            editor.putString("wind4", wind4);
            editor.putString("wind5", wind5);
            editor.putString("wind6", wind6);


            editor.putString("cityName", cityName);
            editor.putString("date", date);
            editor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}