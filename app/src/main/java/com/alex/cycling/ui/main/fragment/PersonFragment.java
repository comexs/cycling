package com.alex.cycling.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.cycling.R;
import com.alex.cycling.base.BaseFragment;
import com.alex.cycling.db.DbUtil;
import com.alex.cycling.ui.main.util.DayAxisValueFormatter;
import com.alex.cycling.ui.setting.SettingActivity;
import com.alex.greendao.TrackInfo;
import com.alex.greendao.TrackInfoDao;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by comexs on 16/4/10.
 */
public class PersonFragment extends BaseFragment {

//    @BindView(R.id.avatar)
//    ImageView avatar;
//    @BindView(R.id.name)
//    TextView name;
//    @BindView(R.id.address)
//    TextView address;
    @BindView(R.id.chart)
    BarChart mChart;
//    @BindView(R.id.back_image)
//    ImageView backImage;

    static {   //vector兼容性问题
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.fragment_person, null);
        ButterKnife.bind(this, main);
        initView();
        initData();
        return main;
    }

    private void initData() {
//        BtPoint btPoint = new BtPoint();
//        btPoint.lat = 10;
//        btPoint.lon = 20;
//        Account.isVerfyPoint(btPoint);
    }


    private void initView() {
//        avatar.setImageURI(Uri.parse("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png"));

//        Glide.with(getContext())
//                .load(R.mipmap.default_head)
//                .bitmapTransform(new CropCircleTransformation(getContext()))
//                .dontAnimate()
//                .into(avatar);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setDescription("");
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setMaxVisibleValueCount(60);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        mChart.animateXY(2000, 2000);

        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);
        AxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart); //修改数字为英文
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(6);
        xAxis.setAxisLineColor(R.color.holo_black);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(getResources().getColor(R.color.white));
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setValueFormatter(xAxisFormatter);
        setData(6, 50);
    }

    private void queryWeekDate() {  //查询一周的数据
        List<TrackInfo> trackInfoList = DbUtil.getTrackInfoService().queryBuilder().where(TrackInfoDao.Properties.Status.eq("1")).orderDesc(TrackInfoDao.Properties.StartTime).list();
        for (TrackInfo trackInfo : trackInfoList) {
            long time = trackInfo.getStartTime();
        }
    }


    private void setData(int count, float range) {

        float start = 0f;

        mChart.getXAxis().setAxisMinValue(start);
        mChart.getXAxis().setAxisMaxValue(start + count + 2);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = (int) start; i < start + count + 1; i++) {
            float val = i;
            yVals1.add(new BarEntry(i + 1f, val));
        }

        BarDataSet set1;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "本周");
//            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            set1.setColor(getResources().getColor(R.color.green));
            set1.setBarBorderColor(getResources().getColor(R.color.white));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setDrawValues(false);
            data.setBarWidth(0.2f);
            mChart.setData(data);
        }
    }


//    @OnClick({R.id.person_info})
//    void click(View v) {
//        switch (v.getId()) {
//            case R.id.person_info:
//                openActivity(getActivity(), PersonActivity.class);
//                break;
//        }
////        TestFragment testFragment = new TestFragment();
////        testFragment.show(getFragmentManager(), "aaa");
//    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_comm, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem setting = menu.findItem(R.id.menu_common);
        setting.setTitle("设置");
        setting.setIcon(R.drawable.ic_main_nav_settings);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_common:
                openActivity(getActivity(), SettingActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
