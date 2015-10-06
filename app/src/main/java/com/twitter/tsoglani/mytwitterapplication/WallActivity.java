package com.twitter.tsoglani.mytwitterapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import com.example.twittershare.R;

public class WallActivity extends Activity {
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        List<Status> statuses = null;
        try {
            try {
                db = new DB(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String extra = getIntent().getStringExtra("Extra");
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(MainActivity.consumerKey);
            builder.setOAuthConsumerSecret(MainActivity.consumerSecret);

            // Access Token
            String access_token = MainActivity.mSharedPreferences.getString(MainActivity.PREF_KEY_OAUTH_TOKEN, "");
            // Access Token Secret
            String access_token_secret = MainActivity.mSharedPreferences.getString(MainActivity.PREF_KEY_OAUTH_SECRET, "");

            AccessToken accessToken = new AccessToken(access_token, access_token_secret);
            Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
           // Toast.makeText(WallActivity.this, extra, Toast.LENGTH_SHORT).show();
            Paging paging = new Paging(1, 120);
            if (extra.equalsIgnoreCase("home")) {
                statuses = twitter.getHomeTimeline(paging);
            } else if (extra.equalsIgnoreCase("user")) {
                statuses = twitter.getUserTimeline(paging);
            }else{

                Query query = new Query(extra);
                query.setCount(200);
                QueryResult result = twitter.search(query);
                statuses= result.getTweets();
            }

            LinearLayout ll = (LinearLayout) findViewById(R.id.linear_wall);
            for (final Status status : statuses) {
                ViewGroup view = (ViewGroup) View.inflate(getApplicationContext(), R.layout.share_item, null);


                ll.addView(view);

                for (int i = 0; i < view.getChildCount(); i++) {
                    View v = view.getChildAt(i);
                    if (v instanceof TextView) {
                        TextView txt = (TextView) v;
                        if (txt.getText().toString().equalsIgnoreCase("User")) {
                            txt.setText(status.getUser().getName());
                        } else if (txt.getText().toString().equalsIgnoreCase("Message")) {
                            txt.setText(status.getText());
                        }else if (txt.getText().toString().equalsIgnoreCase("Save")) {
                            ArrayList<String> list=db.getMessagesAndNames();
                            for(int j=0;j<list.size();j++){
                                 String[] str = list.get(j).split("@ @ @ @");
                                if(str[0].equals(status.getUser().getName())&&str[1].equals(status.getText())){
                                    txt.setEnabled(false);
                                }
                            }
                            txt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    db.save(status.getUser().getName(),status.getText());
                                    v.setEnabled(false);
                                }
                            });
                        }
                    }
                }
                View v = new View(this);
                v.setBackgroundColor(getResources().getColor(R.color.white));
                v.setLayoutParams(new ViewGroup.LayoutParams(getWindowManager().getDefaultDisplay().getWidth(), 20));
                ll.addView(v);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

}
