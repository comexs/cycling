package com.alex.cycling.db;

import android.content.Context;
import android.text.TextUtils;

import com.alex.cycling.utils.FileUtil;
import com.alex.greendao.DaoMaster;
import com.alex.greendao.DaoSession;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by comexs on 16/3/30.
 */
public class DbCore {

    private static final String DEFAULT_DB_NAME = "default.db";  //数据库名
    private static DaoMaster daoMaster;
    private static DaoMaster trackDaoMaster;
    private static DaoSession daoSession;
    private static DaoSession trackDaoSession;

    private static DBContext mContext;
    private static String DB_NAME;
    private static String TRACK_NAME;

    public static void init(Context context) {
        init(context, DEFAULT_DB_NAME);
    }

    public static void init(Context context, String dbName) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        mContext = new DBContext(context.getApplicationContext());
        DB_NAME = dbName;
    }

    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            mContext.setName(null);
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(mContext, DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoMaster getDaoMaster(String tableName) {
        if (TextUtils.isEmpty(TRACK_NAME) || !TextUtils.equals(TRACK_NAME, tableName)) {
            mContext.setName(FileUtil.getTrackDir());
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(mContext, tableName, null);
            trackDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return trackDaoMaster;
    }

    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 创建轨迹数据库
     *
     * @param tableName
     * @return
     */
    public static DaoSession createTrackSession(String tableName) {
        if (TextUtils.isEmpty(TRACK_NAME) || !TextUtils.equals(TRACK_NAME, tableName)) {
            if (trackDaoMaster == null || !TextUtils.equals(TRACK_NAME, tableName)) {
                trackDaoMaster = getDaoMaster(tableName);
                TRACK_NAME = tableName;
            }
            trackDaoSession = trackDaoMaster.newSession();
        }
        return trackDaoSession;
    }

    public static void enableQueryBuilderLog() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }
}
