package android.appz.com.noticeboard;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ViewAdapter adapter;
    ArrayList<Notifications> notifs;
    MySQLiteHelper sh;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
//        if(!intent.getBooleanExtra("Welcome_Launch", false)){
//            intent = new Intent(this, WelcomeActivity.class);
//            startActivity(intent);
//        }
//        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor edit = sf.edit();
//        edit.putInt("count", 0);
//        edit.commit();
        setContentView(R.layout.activity_home);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("Notice Board");
        myToolbar.setTitleTextColor(getColor(R.color.colorText));
        setSupportActionBar(myToolbar);
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
//        FirebaseMessaging.getInstance().subscribeToTopic("BITS");
//        FirebaseMessaging.getInstance().subscribeToTopic("BOSM");
//        FirebaseMessaging.getInstance().subscribeToTopic("OASIS");
//        FirebaseMessaging.getInstance().subscribeToTopic("APOGEE");
        notifs = new ArrayList<>();
        adapter = new ViewAdapter(getApplicationContext(), notifs);
        LinearLayout layout = (LinearLayout) getWindow().getDecorView().getRootView().findViewById(R.id.Linearlayout);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        sh = new MySQLiteHelper(this);
//        testNotifications();
        testDatabase();
    }

    public void testNotifications(){
        Notifications n = new Notifications("t#1", "m#1", "d#1", 0, "5:00", "10:00", "17/10/16", "24/10/16");
        notifs.add(n);
        n = new Notifications("t#2", "m#2", "d#2", 1, "5:00", "10:00", "17/10/16", "24/10/16");
        notifs.add(n);
        n = new Notifications("t#3", "m#3", "d#3", 2, "5:00", "10:00", "17/10/16", "24/10/16");
        notifs.add(n);
        n = new Notifications("t#4", "m#4", "d#4", 1, "5:00", "10:00", "17/10/16", "24/10/16");
        notifs.add(n);
        n = new Notifications("t#5", "m#5", "d#5", 0, "5:00", "10:00", "17/10/16", "24/10/16");
        notifs.add(n);
        adapter.notifyDataSetChanged();
    }

    public void testDatabase(){
        sh.onUpgrade(sh.getWritableDatabase(), 0, 1);
        Notifications n = new Notifications("t#1", "m#1", "d#1", 0, "5:00", "10:00", "17/10/16", "24/10/16");
        sh.addNotification(n);
        n = new Notifications("t#2", "m#2", "d#2", 1, "16:28", "10:00", "15/10/16", "24/10/16");
        sh.addNotification(n);
        n = new Notifications("t#3", "m#3", "d#3", 2, "16:27", "17:00", "15/10/16", "24/10/16");
        sh.addNotification(n);
        n = new Notifications("t#4", "m#4", "d#4", 1, "5:00", "10:00", "10/10/16", "13/10/16");
        sh.addNotification(n);
        n = new Notifications("t#5", "m#5", "d#5", 0, "5:00", "10:00", "17/10/16", "24/10/16");
        sh.syncNotifications(n);
        notifs = sh.getAllNotifications();
        adapter = new ViewAdapter(this, notifs);
        recyclerView.setAdapter(adapter);
    }
}
