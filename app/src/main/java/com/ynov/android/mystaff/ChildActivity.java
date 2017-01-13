package com.ynov.android.mystaff;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ChildActivity extends AppCompatActivity {


    private TextView mDisplayText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        mDisplayText = (TextView) findViewById(R.id.tv_display);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {

            String textEntered = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

            mDisplayText.setText(textEntered);
        }

    }

    public void onClickOpenWebpageButton(View v) {
        // COMPLETED (5) Create a String that contains a URL ( make sure it starts with http:// or https:// )
        String urlAsString = "https://ynov-nantes.slack.com/";

        // COMPLETED (6) Replace the Toast with a call to openWebPage, passing in the URL String from the previous step
        openWebPage(urlAsString);
    }

    private void openWebPage(String url) {

        Uri webpage = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
