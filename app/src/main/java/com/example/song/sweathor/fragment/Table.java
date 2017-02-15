package com.example.song.sweathor.fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.song.sweathor.R;
import com.example.song.sweathor.util.HttpCallbackListener;
import com.example.song.sweathor.util.HttpUtil;
import com.example.song.sweathor.util.Untility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 15-3-13.
 */
public class Table extends Fragment {
    private View rootview;
    private ImageView curser;
    private int bmpw;
    private ArrayList<View> listViews;
    private int offset = 0;
    private View page;
    private View page1;
    private String code;
    private SharedPreferences pref;
    private ProgressDialog progressDialog;
    private TextView temp;
    private TextView weather;
    private TextView date;
    private TextView city;
    private TextView wind;
    private TextView quality;
    private ImageView weatherpic;
    private String sdate;
    private String swind;
    private String scity;
    private String sweather;
    private String stemp;
    private String spm;
    private TextView chuanyi;
    private TextView shushi;
    private TextView ganmao;
    private TextView yundong;
    private TextView guomin;
    private TextView UV;
    private TextView yuehui;
    private TextView jiaotong;
    private TextView yusan;
    private TextView xiche;

    String tmpcode;
    int tmppage;


    //TestF testF;
    private int currIndex = 0;
    private ViewPager mPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.table, container, false);
        InitViewPager();
        InitImageView();
        InitPage();
        InitPage1();
        mPager.setCurrentItem(0);
        return rootview;
    }

    private void InitPage1() {
        chuanyi = (TextView) page1.findViewById(R.id.chuanyi);
        shushi = (TextView) page1.findViewById(R.id.shushi);
        ganmao = (TextView) page1.findViewById(R.id.ganmao);
        yundong = (TextView) page1.findViewById(R.id.yundong);
        guomin = (TextView) page1.findViewById(R.id.guomin);
        UV = (TextView) page1.findViewById(R.id.UV);
        yuehui = (TextView) page1.findViewById(R.id.yuehui);
        jiaotong = (TextView) page1.findViewById(R.id.jiaotong);
        yusan = (TextView) page1.findViewById(R.id.yusan);
        xiche = (TextView) page1.findViewById(R.id.xiche);

    }

    private void InitPage() {

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        temp = (TextView) page.findViewById(R.id.tempv);
        weather = (TextView) page.findViewById(R.id.weatherv);
        weatherpic = (ImageView) page.findViewById(R.id.weatherpicv);
        date = (TextView) page.findViewById(R.id.datav);
        city = (TextView) page.findViewById(R.id.cityv);
        wind = (TextView) page.findViewById(R.id.windv);
        quality = (TextView) page.findViewById(R.id.qualityv);
        String address = "http://m.weather.com.cn/data5/city" + tmpcode + ".xml";
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
                        weather.setText(sweather);
                        wind.setText(swind);
                        date.setText(sdate);
                        quality.setText(spm);
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


    }

    private void InitViewPager() {
        mPager = (ViewPager) rootview.findViewById(R.id.vPager);
        listViews = new ArrayList<View>();
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        page = mInflater.inflate(R.layout.tableview, null);
        page1 = mInflater.inflate(R.layout.teble_item2, null);
        tmpcode = getArguments().getString("county_code");
        tmppage = getArguments().getInt("currentpage");
        Log.e("song", "I got " + tmpcode);

        listViews.add(page);
        listViews.add(page1);

        mPager.setAdapter(new MyPagerAdapter(listViews));
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void InitImageView() {
        curser = (ImageView) rootview.findViewById(R.id.cursor);
        bmpw = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = screenW - bmpw;
        Matrix matrix = new Matrix();
        matrix.postTranslate(0, 0);
        curser.setImageMatrix(matrix);


    /*   curser = (ImageView) rootview.findViewById(R.id.cursor);
        bmpw = BitmapFactory.decodeResource(getResources(), R.drawable.a)
                .getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 2 - bmpw) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        curser.setImageMatrix(matrix);*/
    }
    /*public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }*/

   /* public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list;
        public MyFragmentPagerAdapter(FragmentManager fm,ArrayList<Fragment> list) {
            super(fm);
            this.list = list;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }
    }*/

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
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
        } else
            Temp = temp.substring(0, l);
        //Temp=Temp+"°C";
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

        sweather = pref.getString("weather1", "test");
        stemp = pref.getString("temp1", "");
        stemp = solvetemp(stemp);
        swind = pref.getString("wind1", "");
        spm = pref.getString("pm", "良");
        if (spm.equals("")) {
            spm = "正在更新数据,请稍等.";
        }
        String Schuanyi = pref.getString("chuanyi", "");
        String Sshushi = pref.getString("shushi", "");
        String Sganmao = pref.getString("ganmao", "");
        String Syundong = pref.getString("yundong", "");
        String Sguomin = pref.getString("guomin", "");
        String SUV = pref.getString("UV", "");
        String Syuehui = pref.getString("yuehui", "");
        String Sjiaotong = pref.getString("jiaotong", "");
        String Syusan = pref.getString("yusan", "");
        String Sxiche = pref.getString("xiche", "");
        chuanyi.setText(Schuanyi);
        shushi.setText(Sshushi);
        ganmao.setText(Sganmao);
        yundong.setText(Syundong);
        guomin.setText(Sguomin);
        UV.setText(SUV);
        yuehui.setText(Syuehui);
        jiaotong.setText(Sjiaotong);
        yusan.setText(Syusan);
        xiche.setText(Sxiche);

    }

    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        // int one = offset * 2 + bmpw;// 页卡1 -> 页卡2 偏移量
        // int two = one * 2;// 页卡1 -> 页卡3 偏移量

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(offset, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, offset, 0, 0);
                    }
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            curser.startAnimation(animation);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }


    }
}
