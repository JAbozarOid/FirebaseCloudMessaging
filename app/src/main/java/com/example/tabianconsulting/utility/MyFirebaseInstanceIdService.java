package com.example.tabianconsulting.utility;

import android.util.Log;

import com.example.tabianconsulting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public static final String TAG = "MyFirebaseInstanceIdSer";

    @Override
    public void onTokenRefresh() {

        String refreshToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "onTokenRefresh: Refresh Token: "+refreshToken);

        // send token to database
        sendRegistrationToServer(refreshToken);
    }

    private void sendRegistrationToServer(String refreshToken) {

        Log.d(TAG, "sendRegistrationToServer: sending token to server" + refreshToken);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child(getString(R.string.dbnode_user))
                 .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                 .child(getString(R.string.field_message_token))
                 .setValue(refreshToken);
    }
}
