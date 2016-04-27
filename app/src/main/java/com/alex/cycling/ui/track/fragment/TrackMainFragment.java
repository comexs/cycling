package com.alex.cycling.ui.track.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alex.cycling.R;
import com.alex.cycling.base.BaseFragment;
import com.alex.cycling.db.DbUtil;
import com.alex.cycling.service.TrackManager;
import com.alex.cycling.utils.BaiduTool;
import com.alex.cycling.utils.SystemUtil;
import com.alex.greendao.TrackInfo;
import com.alex.greendao.TrackInfoDao;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by comexs on 16/4/24.
 */
public class TrackMainFragment extends BaseFragment {


    @Bind(R.id.frg_map)
    MapView mapView;

    private LatLngBounds bounds;

    private String trackUUID;
    private TrackInfo trackInfo;

    private LatLng firstLatlng;
    private LatLng endLatlng;
    private LatLng centerLatlng;

    public static Fragment newInstance(String trackUUID) {
        TrackMainFragment fragment = new TrackMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uuid", trackUUID);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_main, null, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        trackUUID = getArguments().getString("uuid");
        trackInfo = DbUtil.getTrackInfoService().queryBuilder().where(TrackInfoDao.Properties.TrackUUID.eq(trackUUID)).unique();
        initMapView();
    }

    private void initMapView() {
        mapView.showScaleControl(false);
        mapView.showZoomControls(false);
        //百度地图logo消失
        mapView.removeViewAt(1);
        UiSettings mapSetting = mapView.getMap().getUiSettings();
        mapSetting.setAllGesturesEnabled(true);
        mapSetting.setCompassEnabled(false);
        mapSetting.setOverlookingGesturesEnabled(false);
        mapSetting.setRotateGesturesEnabled(false);
        mapSetting.setZoomGesturesEnabled(true);
        mapView.getMap().setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                initData(trackInfo);
//                mapView.getMap().setMapStatusLimits(bounds);
                computeMapSie();
            }
        });
    }

    private void initData(TrackInfo info) {
        List<LatLng> bdpts = TrackManager.getCacheList(info.getTrackUUID());

        LatLngBounds.Builder boundBuilder = new LatLngBounds.Builder();
        boundBuilder.include(bdpts.get(0));
        boundBuilder.include(bdpts.get(bdpts.size() - 1));
        bounds = boundBuilder.build();

        PolylineOptions polyline = new PolylineOptions();
        if (bdpts.size() > 1) {
            polyline.points(bdpts);
            polyline.color(Color.RED);
            mapView.getMap().addOverlay(polyline);
        }
        firstLatlng = bdpts.get(0);
        if (firstLatlng != null) {
            MarkerOptions start = new MarkerOptions();
            start.position(bdpts.get(0));
            start.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_start));
            mapView.getMap().addOverlay(start);
        }
        endLatlng = bdpts.get(bdpts.size() - 1);
        if (endLatlng != null) {
            MarkerOptions end = new MarkerOptions();
            end.position(bdpts.get(bdpts.size() - 1));
            end.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_stop));
            mapView.getMap().addOverlay(end);
        }
        MapStatusUpdate update = MapStatusUpdateFactory.zoomTo(19);
        mapView.getMap().setMapStatus(update);
//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
//        mapView.getMap().animateMapStatus(u);
    }


    private void computeMapSie() {
        int height = (int) (139 * SystemUtil.getScreenInfo(getContext()).density + 0.5);//mapViewContainer 高为 139dp
        int width = SystemUtil.getScreenInfo(getContext()).widthPixels;
        int zoom1 = BaiduTool.LatLngBoundsToZoom(firstLatlng, endLatlng, height);
        int zoom2 = BaiduTool.LatLngBoundsToZoom(firstLatlng, endLatlng, width);

        float smallZoom = zoom1 < zoom2 ? zoom1 : zoom2;
        smallZoom = smallZoom - 1f;
        height = (int) (mapView.getHeight() * SystemUtil.getScreenInfo(getContext()).density - height + 0.5);
        height -= 80;
        double angle2 = BaiduTool.pixelsToAngle(smallZoom, height);
        centerLatlng = new LatLng((endLatlng.latitude + firstLatlng.latitude - angle2) / 2, (endLatlng.longitude + firstLatlng.longitude) / 2);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(firstLatlng, smallZoom);
        mapView.getMap().setMapStatus(u);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (null != mapView) mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
