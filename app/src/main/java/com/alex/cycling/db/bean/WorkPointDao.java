package com.alex.cycling.db.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.alex.greendao.DaoSession;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "WORK_POINT".
 */
public class WorkPointDao extends AbstractDao<WorkPoint, Void> {

    public static final String TABLENAME = "WORK_POINT";

    /**
     * Properties of entity WorkPoint.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Lat = new Property(0, double.class, "lat", false, "LAT");
        public final static Property Lon = new Property(1, double.class, "lon", false, "LON");
        public final static Property Alt = new Property(2, double.class, "alt", false, "ALT");
        public final static Property Speed = new Property(3, float.class, "speed", false, "SPEED");
        public final static Property Power = new Property(4, float.class, "power", false, "POWER");
        public final static Property Temp = new Property(5, int.class, "temp", false, "TEMP");
        public final static Property Status = new Property(6, int.class, "status", false, "STATUS");
        public final static Property Time = new Property(7, long.class, "time", false, "TIME");
    }

    ;


    public WorkPointDao(DaoConfig config) {
        super(config);
    }

    public WorkPointDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"WORK_POINT\" (" + //
                "\"LAT\" int4," + // 0: lat
                "\"LON\" int4," + // 1: lon
                "\"ALT\" int4," + // 2: alt
                "\"SPEED\" int2," + // 3: speed
                "\"POWER\" int2," + // 4: power
                "\"TEMP\" int1," + // 5: temp
                "\"STATUS\" int1," + // 6: status
                "\"TIME\" int4);"); // 7: time
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WORK_POINT\"";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, WorkPoint entity) {
        stmt.clearBindings();

        Double lat = entity.getLat();
        if (lat != null) {
            stmt.bindDouble(1, lat);
        }

        Double lon = entity.getLon();
        if (lon != null) {
            stmt.bindDouble(2, lon);
        }

        Double alt = entity.getAlt();
        if (alt != null) {
            stmt.bindDouble(3, alt);
        }

        Float speed = entity.getSpeed();
        if (speed != null) {
            stmt.bindDouble(4, speed);
        }

        Float power = entity.getPower();
        if (power != null) {
            stmt.bindDouble(5, power);
        }

        Integer temp = entity.getTemp();
        if (temp != null) {
            stmt.bindLong(6, temp);
        }

        Integer status = entity.getStatus();
        if (status != null) {
            stmt.bindLong(7, status);
        }

        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(8, time);
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }

    /**
     * @inheritdoc
     */
    @Override
    public WorkPoint readEntity(Cursor cursor, int offset) {
        WorkPoint entity = new WorkPoint( //
                cursor.getDouble(offset + 0), // lat
                cursor.getDouble(offset + 1), // lon
                cursor.getDouble(offset + 2), // alt
                cursor.getFloat(offset + 3), // speed
                cursor.getFloat(offset + 4), // power
                cursor.getInt(offset + 5), // temp
                cursor.getInt(offset + 6), // status
                cursor.getLong(offset + 7) // time
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, WorkPoint entity, int offset) {
        entity.setLat(cursor.getDouble(offset + 0));
        entity.setLon(cursor.getDouble(offset + 1));
        entity.setAlt(cursor.getDouble(offset + 2));
        entity.setSpeed(cursor.getFloat(offset + 3));
        entity.setPower(cursor.getFloat(offset + 4));
        entity.setTemp(cursor.getInt(offset + 5));
        entity.setStatus(cursor.getInt(offset + 6));
        entity.setTime(cursor.getLong(offset + 7));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Void updateKeyAfterInsert(WorkPoint entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Void getKey(WorkPoint entity) {
        return null;
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

}