package android.appz.com.noticeboard;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by AniMe on 28-Aug-16.
 */
public class Registration extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refresh = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refresh);
        Log.e("123", "registration");
    }

    private void sendRegistrationToServer(String token) {

    }
}
