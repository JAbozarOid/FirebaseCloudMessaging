package com.example.tabianconsulting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "MyFirebaseToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFCM();

        if (getIntent().getExtras()!=null){
            Toast.makeText(this, "notif is not empty", Toast.LENGTH_SHORT).show();
        }

        /*ArrayList<String> list = null;
        list.size();*/
    }

    private void initFCM() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "initFCM: token" + " " + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String refreshToken) {

        Log.d(TAG, "sendRegistrationToServer: sending token to server" + refreshToken);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child(getString(R.string.dbnode_user))
                .child(getString(R.string.field_message_token))
                .setValue(refreshToken);
    }
}
