package com.example.song.sweathor.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.song.sweathor.R;
import com.example.song.sweathor.util.HttpCallbackListener;
import com.example.song.sweathor.util.HttpUtil;
import com.example.song.sweathor.util.Untility;

/**
 * Created by song on 15-3-14.
 */
public class Page extends Fragment {
    private View rootView;
    private SharedPreferences pref;
    private ProgressDialog progressDialog;
    private TextView temp;
    private TextView weather;
    private TextView date;
    private TextView city;
    private TextView quality;
    private TextView wind;
    private ImageView weatherpic;
    private String code;
    private int page;
    private String cityCode;
    private String sdate;
    private String swind;
    private String scity;
    private String sweather;
    private String stemp;
    private String spm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.table_item, container, false);
        cityCode = getArguments().getString("county_code");
        page = getArguments().getInt("currentpage");
        Log.e("song", "I got in 2end " + cityCode);
        Log.e("song", "I got index in 2end " + page);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        temp = (TextView) rootView.findViewById(R.id.temp);
        weather = (TextView) rootView.findViewById(R.id.weather);
        weatherpic = (ImageView) rootView.findViewById(R.id.weatherpic);
        date = (TextView) rootView.findViewById(R.id.data);
        city = (TextView) rootView.findViewById(R.id.city);
        quality = (TextView) rootView.findViewById(R.id.quality);
        wind = (TextView) rootView.findViewById(R.id.wind);
        String address = "http://m.weather.com.cn/data5/city" + cityCode + ".xml";
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                code = Untility.getCode(response);
                Log.e("song", code);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        while (code == null) {
        }
        final String Waddress = "http://weather.123.duba.net/static/weather_info/" + code + ".html";
        showProgressDialog();
        HttpUtil.sendHttpRequest(Waddress, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.e("song", response);
                Untility.handleWeatherResponse(getActivity(), response);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        Log.e("song", "I had set UI");
                        solvepic(sweather);
                        temp.setText(stemp);
                        city.setText(scity);
                        quality.setText(spm);
                        weather.setText(sweather);
                        wind.setText(swind);
                        date.setText(sdate);
                        closeProgressDialog();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return rootView;
    }

    public View getView() {
        return rootView;
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载......");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private String solvetemp(String temp) {
        int l = temp.indexOf("~");
        String Temp;
        if (l == -1) {
            Temp = temp;
        } else {
            Temp = temp.substring(0, l);
            //Temp = Temp + "°C";
        }
        return Temp;
    }

    private void solvepic(String weather) {
        if (weather.indexOf('云') != -1) {
            weatherpic.setImageResource(R.drawable.cloud);
        } else if (weather.indexOf('霾') != -1) {
            weatherpic.setImageResource(R.drawable.yin);
        } else if (weather.indexOf('雨') != -1) {
            weatherpic.setImageResource(R.drawable.rain);
        } else if (weather.indexOf('雪') != -1) {
            weatherpic.setImageResource(R.drawable.snow);
        } else if (weather.indexOf('晴') != -1) {
            weatherpic.setImageResource(R.drawable.sun);
        } else {
            weatherpic.setImageResource(R.drawable.yin);
        }
    }

    private void getData() {
        sdate = pref.getString("date", "");
        scity = pref.getString("cityName", "");
        spm = pref.getString("pm", "良");
        if (spm.equals("")) {
            spm = "正在更新数据,请稍等.";
        }
        switch (page) {
            case 0:
                sweather = pref.getString("weather1", "test");
                stemp = pref.getString("temp1", "");
                stemp = solvetemp(stemp);
                swind = pref.getString("wind1", "");
                break;
            case 1:
                sweather = pref.getString("weather2", "");
                stemp = pref.getString("temp2", "");
                stemp = solvetemp(stemp);
                swind = pref.getString("wind2", "");
                break;
            case 2:
                sweather = pref.getString("weather3", "");
                stemp = pref.getString("temp3", "");
                stemp = solvetemp(stemp);
                swind = pref.getString("wind3", "");
                break;
            case 3:
                sweather = pref.getString("weather4", "");
                stemp = pref.getString("temp4", "");
                stemp = solvetemp(stemp);
                swind = pref.getString("wind4", "");
                break;
            case 4:
                sweather = pref.getString("weather5", "");
                stemp = pref.getString("temp5", "");
                stemp = solvetemp(stemp);
                swind = pref.getString("wind6", "");
                break;
            case 5:
                sweather = pref.getString("weather6", "");
                stemp = pref.getString("temp6", "");
                stemp = solvetemp(stemp);
                swind = pref.getString("wind6", "");
                break;
        }
    }
}

