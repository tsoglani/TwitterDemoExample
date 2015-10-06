package com.twitter.tsoglani.mytwitterapplication;

import android.app.Activity;
import android.os.Bundle;;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.twittershare.R;

import java.util.ArrayList;

public class SavedTweetActivity extends Activity {
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_tweet);
        db = new DB(this);
        ArrayList<String> list = db.getMessagesAndNames();
        if (list != null && !list.isEmpty()) {
            TextView txtView = (TextView) findViewById(R.id.empty_text_view);
            txtView.setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < list.size(); i++) {
            final ViewGroup view = (ViewGroup) View.inflate(getApplicationContext(), R.layout.saved_item, null);
            final LinearLayout ll = (LinearLayout) findViewById(R.id.saved_linear);
            final String[] str = list.get(i).split("@ @ @ @");

            for (int j = 0; j < view.getChildCount(); j++) {
                View v = view.getChildAt(j);
                if (v instanceof TextView) {
                    TextView txt = (TextView) v;

                    if (txt.getText().toString().equalsIgnoreCase("User")) {
                        txt.setText(str[0]);
                    } else if (txt.getText().toString().equalsIgnoreCase("Message")) {
                        txt.setText(str[1]);

                    } else if (txt.getText().toString().equalsIgnoreCase("Delete")) {

                        v.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                db.deleteContact(Integer.parseInt(str[2]));
                                v.setEnabled(false);
                                ll.removeView(view);
                                if (db.getMessagesAndNames().isEmpty()) {
                                    TextView txtView = (TextView) findViewById(R.id.empty_text_view);
                                    txtView.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                }
            }
            ll.addView(view);
            View v = new View(this);
            v.setBackgroundColor(getResources().getColor(R.color.white));
            v.setLayoutParams(new ViewGroup.LayoutParams(getWindowManager().getDefaultDisplay().getWidth(), 20));
            ll.addView(v);
        }

    }

}
