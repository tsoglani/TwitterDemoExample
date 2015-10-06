package com.twitter.tsoglani.mytwitterapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.twittershare.R;

public class ShareActivty extends Activity implements View.OnClickListener {
    private View shareLayout;
    private EditText mShareEditText;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_activty);


        shareLayout = (LinearLayout) findViewById(R.id.share_layout);
        mShareEditText = (EditText) findViewById(R.id.share_text);
        userName = (TextView) findViewById(R.id.user_name);
        findViewById(R.id.btn_share).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        SharedPreferences mSharedPreferences = getSharedPreferences(MainActivity.PREF_NAME, 0);
        String username = mSharedPreferences.getString(MainActivity.PREF_USER_NAME, "");
        userName.setText(getResources ().getString(R.string.hello)
                + username);

        final String status = mShareEditText.getText().toString();

        if (status.trim().length() > 0) {
            new com.twitter.tsoglani.mytwitterapplication.MainActivity.updateTwitterStatus(ShareActivty.this).execute(status);

    }
}


}
