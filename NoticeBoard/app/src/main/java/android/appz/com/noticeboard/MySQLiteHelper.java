package android.appz.com.noticeboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by AniMe on 29-Aug-16.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NOTIFICATIONS = "notifications";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_STARTTIME = "starttime";
    public static final String COLUMN_ENDTIME = "endtime";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_EXPIRY = "expiry";

    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NOTIFICATIONS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TITLE
            + " text, " + COLUMN_MESSAGE + " text, " + COLUMN_DATA
            + " text, " + COLUMN_TYPE + " integer, " + COLUMN_STARTTIME
            + " text, " + COLUMN_ENDTIME + " text, " + COLUMN_DATE
            + " text, " + COLUMN_EXPIRY + " text);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        Log.e("123", "database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        onCreate(db);
    }

    public void addNotification(Notifications notifications){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Log.e("123", "added " + notifications.getTitle());
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, notifications.getTitle());
        contentValues.put(COLUMN_MESSAGE, notifications.getMessage());
        contentValues.put(COLUMN_DATA, notifications.getData());
        contentValues.put(COLUMN_TYPE, notifications.getType());
        contentValues.put(COLUMN_DATE, notifications.getDate());
        contentValues.put(COLUMN_EXPIRY, notifications.getExpiry());
        contentValues.put(COLUMN_STARTTIME, notifications.getStarttime());
        contentValues.put(COLUMN_ENDTIME, notifications.getEndtime());

        sqLiteDatabase.insert(TABLE_NOTIFICATIONS, null, contentValues);
        sqLiteDatabase.close();
    }

    public Notifications getNotification(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NOTIFICATIONS,
                new String[]{COLUMN_ID,
                        COLUMN_TITLE,
                        COLUMN_MESSAGE,
                        COLUMN_DATA,
                        COLUMN_TYPE,
                        COLUMN_STARTTIME,
                        COLUMN_ENDTIME,
                        COLUMN_DATE,
                        COLUMN_EXPIRY
                },
                COLUMN_ID + " ?= " ,
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if(c != null){
            c.moveToFirst();
        }

        Notifications notifications = new Notifications(c.getString(0), c.getString(1), c.getString(2),
                Integer.parseInt(c.getString(3)), c.getString(4), c.getString(5), c.getString(6), c.getString(7));
        return notifications;
    }

    public ArrayList<Notifications> getAllNotifications() {
        ArrayList<Notifications> notificationlist = new ArrayList<Notifications>();
        String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Notifications notifications = new Notifications(null, null, null, 0, null, null, null, null);
                notifications.set_id(Integer.parseInt(cursor.getString(0)));
                notifications.setTitle(cursor.getString(1));
                notifications.setMessage(cursor.getString(2));
                notifications.setData(cursor.getString(3));
                notifications.setType(Integer.parseInt(cursor.getString(4)));
                notifications.setStarttime(cursor.getString(5));
                notifications.setEndtime(cursor.getString(6));
                notifications.setDate(cursor.getString(7));
                notifications.setExpiry(cursor.getString(8));
                notificationlist.add(notifications);
            } while (cursor.moveToNext());
        }
        return notificationlist;
    }

    public void deleteNotifications(){
        SQLiteDatabase db = this.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yy");
        String date = sf.format(c.getTime());
        sf = new SimpleDateFormat("HH:mm");
        String time = sf.format(c.getTime());
        time ="\"" +  time  + "\"";
        db.execSQL("DELETE FROM " + TABLE_NOTIFICATIONS + " WHERE " + COLUMN_EXPIRY + " < " + date + ";");
        db.execSQL("DELETE FROM " + TABLE_NOTIFICATIONS + " WHERE " + COLUMN_EXPIRY + " = " + date + " AND " + COLUMN_ENDTIME + " < " + time + ";");
        Log.e("123", "deleted");
    }

    public void syncNotifications(Notifications notifications){
        deleteNotifications();
        addNotification(notifications);
    }
}

