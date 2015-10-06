package com.twitter.tsoglani.mytwitterapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.twittershare.R;

public class MenuActivity extends AppCompatActivity {
   // public static DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //db = new DB(this);
        setContentView(R.layout.activity_menu);

       final EditText editText= (EditText) findViewById(R.id.search_text);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(editText.getText().toString().equalsIgnoreCase("Enter Text for Twitter Search")){
                    editText.setTextColor(Color.BLACK);
                    editText.setText("");
                }
                return false;
            }
        });


    }

    public void shareFunction(View v) {
        Intent intent = new Intent(MenuActivity.this, ShareActivty.class);
        startActivity(intent);
    }


    public void wallFunction(View v) {
        Intent intent = new Intent(MenuActivity.this, WallActivity.class);
        intent.putExtra("Extra", "home");
        startActivity(intent);
    }

    public void myWallFunction(View v) {
        Intent intent = new Intent(MenuActivity.this, WallActivity.class);
        intent.putExtra("Extra", "user");
        startActivity(intent);
    }

    public void mySavedStatusFunction(View v) {
        Intent intent = new Intent(MenuActivity.this, SavedTweetActivity.class);
        intent.putExtra("Extra", "user");
        startActivity(intent);
    }

    public void searchStatusFunction(View v) {
        Intent intent = new Intent(MenuActivity.this, WallActivity.class);
        EditText editText= (EditText) findViewById(R.id.search_text);
        if(editText.getText().toString().equalsIgnoreCase("Enter Text for Twitter Search")||editText.getText().toString().replaceAll(" ","").equalsIgnoreCase("")){
            Toast.makeText(MenuActivity.this, "Enter a real Text", Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra("Extra", editText.getText().toString());
        Toast.makeText(MenuActivity.this, "Searching for : "+editText.getText().toString(), Toast.LENGTH_SHORT).show();
        editText.setText("");
        startActivity(intent);


    }

}
