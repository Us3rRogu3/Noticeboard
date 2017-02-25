package android.appz.com.noticeboard;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by AniMe on 14-Oct-16.
 */

public class ViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Notifications> notificationList;

    public AlarmManager am;
    Intent notifintent;
    PendingIntent broadcast;


    public class noDetail extends RecyclerView.ViewHolder {
        public TextView titletext, message, remainder, time;
        public LinearLayout remainderview;

        public noDetail(View view) {
            super(view);
            titletext = (TextView) view.findViewById(R.id.titlenodetail);
            message = (TextView) view.findViewById(R.id.messagenodetail);
            remainder = (TextView) view.findViewById(R.id.remaindernodetail);
            time = (TextView) view.findViewById(R.id.timenodetail);
            remainderview = (LinearLayout) view.findViewById(R.id.remaindernodetaillayout);
        }
    }

    public class detail extends RecyclerView.ViewHolder {
        public TextView titletext, message, remainder, time;
        public ImageView extra;
        public LinearLayout remainderview;

        public detail(View view) {
            super(view);
            titletext = (TextView) view.findViewById(R.id.titledetail);
            message = (TextView) view.findViewById(R.id.messagedetail);
            remainder = (TextView) view.findViewById(R.id.remainderdetail);
            extra = (ImageView) view.findViewById(R.id.imagedetail);
            time = (TextView) view.findViewById(R.id.timedetail);
            remainderview = (LinearLayout) view.findViewById(R.id.remainderdetaillayout);
        }
    }

    public ViewAdapter(Context context, List<Notifications> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        notifintent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notifintent.addCategory("android.intent.category.DEFAULT");

        broadcast = PendingIntent.getBroadcast(context, 100, notifintent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(notificationList.get(position).getType() == 0)
            return 0;
        else
            return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitemnodetail, parent, false);
                return new noDetail(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitemdetail, parent, false);
                return new detail(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Notifications notif = notificationList.get(position);
        int type = notif.getType();
        switch(type){
            case 1:
                detail holder1 = (detail) holder;
                holder1.titletext.setText(notif.getTitle());
                holder1.message.setText(notif.getMessage());
                holder1.extra.setImageDrawable(context.getResources().getDrawable(R.drawable.doc));
                holder1.time.setText(notif.getDatetime());
                holder1.remainderview.setTag(position);
                holder1.remainderview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = Integer.parseInt(v.getTag().toString());
                        Notifications nf = notificationList.get(pos);
                        try{
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yy");
                            Date date = sdf.parse(nf.getStarttime() + " " + nf.getDate());
                            long timeinms = date.getTime();
                            notifintent.putExtra("Title", nf.getTitle());
                            notifintent.putExtra("Message", nf.getMessage() + "| starting now");
                            broadcast = PendingIntent.getBroadcast(context, pos, notifintent, PendingIntent.FLAG_UPDATE_CURRENT);
                            am.setExact(AlarmManager.RTC_WAKEUP, timeinms ,broadcast);
                            TextView r = (TextView) v.findViewById(R.id.remainderdetail);
                            r.setText("Remainder at " + nf.getDatetime());
                            r.setClickable(false);
                            Log.e("123", "notification set");
                        }catch(ParseException e){
                            Log.e("123", e.toString());
                        }
                    }
                });
                break;
            case 2:
                detail holder2 = (detail) holder;
                holder2.titletext.setText(notif.getTitle());
                holder2.message.setText(notif.getMessage());
                holder2.extra.setImageDrawable(context.getResources().getDrawable(R.drawable.img));
                holder2.time.setText(notif.getDatetime());
                holder2.remainderview.setTag(position);
                holder2.remainderview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = Integer.parseInt(v.getTag().toString());
                        Notifications nf = notificationList.get(pos);
                        try{
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yy");
                            Date date = sdf.parse(nf.getStarttime() + " " + nf.getDate());
                            long timeinms = date.getTime();
                            notifintent.putExtra("Title", nf.getTitle());
                            notifintent.putExtra("Message", nf.getMessage() + "| starting now");
                            broadcast = PendingIntent.getBroadcast(context, pos, notifintent, PendingIntent.FLAG_UPDATE_CURRENT);
                            am.setExact(AlarmManager.RTC_WAKEUP, timeinms ,broadcast);
                            TextView r = (TextView) v.findViewById(R.id.remainderdetail);
                            r.setText("Remainder at " + nf.getDatetime());
                            r.setClickable(false);
                            Log.e("123", "notification set");
                        }catch(ParseException e){
                            Log.e("123", e.toString());
                        }
                    }
                });
                break;
            default:
                noDetail holder3 = (noDetail) holder;
                holder3.titletext.setText(notif.getTitle());
                holder3.message.setText(notif.getMessage());
                holder3.time.setText(notif.getDatetime());
                holder3.remainderview.setTag(position);
                holder3.remainderview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = Integer.parseInt(v.getTag().toString());
                        Notifications nf = notificationList.get(pos);
                        try{
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yy");
                            Date date = sdf.parse(nf.getStarttime() + " " + nf.getDate());
                            long timeinms = date.getTime();
                            notifintent.putExtra("Title", nf.getTitle());
                            notifintent.putExtra("Message", nf.getMessage() + "| starting now");
                            broadcast = PendingIntent.getBroadcast(context, pos, notifintent, PendingIntent.FLAG_UPDATE_CURRENT);
                            am.setExact(AlarmManager.RTC_WAKEUP, timeinms ,broadcast);
                            TextView r = (TextView) v.findViewById(R.id.remaindernodetail);
                            r.setText("Remainder at " + nf.getDatetime());
                            r.setClickable(false);
                            Log.e("123", "notification set");
                        }catch(ParseException e){
                            Log.e("123", e.toString());
                        }
                    }
                });
                break;
        }
    }
}
