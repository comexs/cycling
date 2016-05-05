package com.alex.cycling.base;

import android.app.Application;

import com.alex.cycling.db.DbCore;
import com.alex.cycling.utils.FileUtil;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

/**
 * Created by comexs on 16/3/27.
 */
public class CSApplication extends Application {


    private static CSApplication application;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        DbCore.init(this);
        DbCore.enableQueryBuilderLog();
        SDKInitializer.initialize(this);
        imageConfig();
    }


    public static Application getInstance() {
        if (application == null) {
            application = new CSApplication();
        }
        return application;
    }

    public void imageConfig() {
        //默认图片的磁盘配置
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(new File(FileUtil.getMainDir()))//缓存图片基路径
                .setBaseDirectoryName(FileUtil.IMAGE_PATH + File.separator + FileUtil.CACHE_IMAGE_PATH + File.separator)//文件夹名
                .setMaxCacheSize(50 * ByteConstants.MB)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(40 * ByteConstants.MB)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(20 * ByteConstants.MB)//缓存的最大大小,当设备极低磁盘空间
                .setVersion(1)
                .build();

        //缓存图片配置
        ImagePipelineConfig configBuilder = ImagePipelineConfig.newBuilder(getApplicationContext())
                .setMainDiskCacheConfig(diskCacheConfig).build();//磁盘缓存配置（总，三级缓存）
        Fresco.initialize(this, configBuilder);
    }
}
