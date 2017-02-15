package com.example.song.sweathor.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.song.sweathor.R;

/**
 * Created by song on 15-3-14.
 */
public class Page2 extends Fragment {
    View rootView;
    private SharedPreferences pref;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.teble_item2, container, false);
        initTextView();
        return rootView;
    }

    private void initTextView() {
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
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
        chuanyi = (TextView) rootView.findViewById(R.id.chuanyi);
        shushi = (TextView) rootView.findViewById(R.id.shushi);
        ganmao = (TextView) rootView.findViewById(R.id.ganmao);
        yundong = (TextView) rootView.findViewById(R.id.yundong);
        guomin = (TextView) rootView.findViewById(R.id.guomin);
        UV = (TextView) rootView.findViewById(R.id.UV);
        yuehui = (TextView) rootView.findViewById(R.id.yuehui);
        jiaotong = (TextView) rootView.findViewById(R.id.jiaotong);
        yusan = (TextView) rootView.findViewById(R.id.yusan);
        xiche = (TextView) rootView.findViewById(R.id.xiche);
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
}
