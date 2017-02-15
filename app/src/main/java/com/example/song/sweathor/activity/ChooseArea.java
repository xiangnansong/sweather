package com.example.song.sweathor.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.offers.OffersManager;

import com.example.song.sweathor.R;
import com.example.song.sweathor.model.City;
import com.example.song.sweathor.model.County;
import com.example.song.sweathor.model.Province;
import com.example.song.sweathor.model.Sdb;
import com.example.song.sweathor.util.HttpCallbackListener;
import com.example.song.sweathor.util.HttpUtil;
import com.example.song.sweathor.util.Untility;


import java.util.ArrayList;
import java.util.List;

/*
 * Created by song on 15-2-13.
 */
public class ChooseArea extends Activity {

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog progressDialog;
    private TextView title;
    private ListView listview;
    private ArrayAdapter<String> adapter;
    private Sdb sdb;
    private List<String> datalist = new ArrayList<String>();
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private Province selectProvince;
    private City selectCity;
    private County selectcounty;
    private int currentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdManager.getInstance(this).init("7e3c6bf0efab8011", "e4bb7641a65c69fb");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        listview = (ListView) findViewById(R.id.list_view);


        OffersManager.getInstance(this).onAppLaunch();



        title = (TextView) findViewById(R.id.title);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datalist);
        listview.setAdapter(adapter);
        sdb = Sdb.getInstance(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectProvince = provinceList.get(i);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectCity = cityList.get(i);

                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    String countyCode = countyList.get(i).getCode();
                    Intent intent = new Intent(ChooseArea.this, WeatherActivity.class);

                    intent.putExtra("county_code", countyCode);
                    startActivity(intent);

                    finish();
                }
            }
        });
        queryProvinces();


    }

    private void queryProvinces() {
        provinceList = sdb.loadProvince();
        if (provinceList.size() > 0) {
            datalist.clear();
            for (Province province : provinceList) {
                datalist.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listview.setSelection(0);
            title.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        } else {
            queryfromServer(null, "province");
        }
    }


    private void queryCities() {
        cityList = sdb.loadCity(selectProvince.getId());
        if (cityList.size() > 0) {
            datalist.clear();
            for (City city : cityList) {
                datalist.add(city.getName());
            }
            adapter.notifyDataSetChanged();
            listview.setSelection(0);
            title.setText(selectProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
        } else {
            queryfromServer(selectProvince.getProvinceCode(), "city");
        }
    }

    private void queryCounties() {
        countyList = sdb.loadCounty(selectCity.getId());
        if (countyList.size() > 0) {
            datalist.clear();
            for (County county : countyList) {
                datalist.add(county.getName());
            }
            adapter.notifyDataSetChanged();
            listview.setSelection(0);
            title.setText(selectCity.getName());
            currentLevel = LEVEL_COUNTY;
        } else {

            queryfromServer(selectCity.getCode(), "county");
        }
    }

    private void queryfromServer(String code, final String type) {

        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://m.weather.com.cn/data5/city" + code + ".xml";
        } else {
            address = "http://m.weather.com.cn/data5/city.xml";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)) {
                    result = Untility.handleProvince(sdb, response);
                } else if ("city".equals(type)) {
                    result = Untility.handleCity(sdb, response, selectProvince.getId());
                } else if ("county".equals(type)) {

                    result = Untility.handleCounty(sdb, response, selectCity.getId());
                }
                if (result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {

                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseArea.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
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

    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_COUNTY) {
            queryCities();
        } else if (currentLevel == LEVEL_CITY) {
            queryProvinces();
        } else if (currentLevel == LEVEL_PROVINCE) {
            finish();
        }
    }
}
