package android.appz.com.noticeboard;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by AniMe on 15-Oct-16.
 */

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("Title");
        String message = intent.getStringExtra("Message");

        Notification.Builder b = new Notification.Builder(context);
        Notification notif = b.setContentTitle(title)
                .setContentText(message)
                .setTicker(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        Log.e("123", title + message);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, notif);
    }
}
