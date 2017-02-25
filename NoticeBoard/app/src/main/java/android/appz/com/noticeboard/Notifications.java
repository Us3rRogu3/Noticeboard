package android.appz.com.noticeboard;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AniMe on 29-Aug-16.
 */
public class Notifications {
    private String title;
    private String message;
    private String data;
    private int type;
    private long _id;
    private String starttime;
    private String endtime;
    private String date;
    private String expiry;

    public Notifications(String title, String message, String data,
                         int type, String starttime, String endtime,
                         String date, String expiry) {
        this.title = title;
        this.message = message;
        this.data = data;
        this.type = type;
        this.starttime = starttime;
        this.date = date;
        this.endtime = endtime;
        this.expiry = expiry;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getDatetime(){
        String finaldate;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date d = null;
        try{
            d = sdf.parse(date);
        }catch (ParseException e){
            Log.e("123", "Parse exception" + e.toString());
        }
        sdf = new SimpleDateFormat("MMM dd");
        finaldate = sdf.format(d);
        return (starttime + " - " + endtime + ", " + finaldate);
    }


    public void setDateTime(String dateTime){
        String[] strings = dateTime.split("-");
        this.starttime = strings[0];
        this.endtime = strings[1];
        this.date = strings[2];
        this.expiry = strings[3];
    }

}
