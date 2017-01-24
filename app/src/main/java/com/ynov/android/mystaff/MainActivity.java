package com.ynov.android.mystaff;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.ynov.android.mystaff.data.FakeData;
import com.ynov.android.mystaff.data.MystaffPreferences;
import com.ynov.android.mystaff.data.StaffListContract;
import com.ynov.android.mystaff.data.StaffListDbHelper;
import com.ynov.android.mystaff.utilities.NetworkUtils;
import com.ynov.android.mystaff.utilities.SlackJsonUtils;

import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements GreenAdapter.ListItemClickListener{

    private TextView mStaff_list;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private GreenAdapter mAdapter;

    private RecyclerView recycleStaff_list;

    private Toast mToast;

    private String[] data = {
            "Alex Soudant Active",
            "Alexandre DesVallees Active",
            "Killian Barreau Active",
            "Maxime Rolland Inactive",
            "Alix Nouzillat Inactive",
            "Charles Huet Inactive",
            "Vincent Rouyer Inactive"
    };

    private SQLiteDatabase mDb;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        //mLoadingIndicator.setVisibility(View.VISIBLE);
        //mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mStaff_list = (TextView) findViewById(R.id.staff_list_item);

        //loadSlackData();

        StaffListDbHelper dbHelper = new StaffListDbHelper(this);

        mDb = dbHelper.getWritableDatabase();

        FakeData.insertFakeData(mDb);




        
        init();

    }

    private void init() {

        Cursor cursor = getAllStaff();

        recycleStaff_list = (RecyclerView) findViewById(R.id.Staff_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleStaff_list.setLayoutManager(layoutManager);

        recycleStaff_list.setHasFixedSize(true);

        mAdapter = new GreenAdapter(data, this, cursor.getCount());

        recycleStaff_list.setAdapter(mAdapter);

    }

    private void loadSlackData() {
        String channel = MystaffPreferences.getDefaultSlackChannel();
        new SlackQueryTask().execute(channel);
    }


    //private void showJsonDataView() {
        // First, make sure the error is invisible
        //mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        //mStaff_list.setVisibility(View.VISIBLE);
    //}

    //private void showErrorMessage() {
        // First, hide the currently visible data
        //mStaff_list.setVisibility(View.INVISIBLE);
        // Then, show the error
        //mErrorMessageDisplay.setVisibility(View.VISIBLE);
    //}

    public class SlackQueryTask extends AsyncTask<String,Void,Object[]>{

        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //mLoadingIndicator.setVisibility(View.VISIBLE);
            dialog.setMessage("Downloading Staff list...");
            dialog.show();
        }

        @Override
        protected Object[] doInBackground(String... params) {
            String channel = params[0];
            URL slackRequestUrl = NetworkUtils.buildUrl(channel);
            try {
                String jsonSlackResponse = NetworkUtils.getReponseFromHttpUrl(slackRequestUrl);

                String[] simpleJsonSlackData = SlackJsonUtils
                        .getSimpleSlackStringsFromJson(jsonSlackResponse, channel);

                URL presenceRequestUrl = NetworkUtils.buildUrlPresence();

                String jsonPresenceResponse = NetworkUtils.getReponseFromHttpUrl(presenceRequestUrl);

                Object[] simpleJsonPresenceData = SlackJsonUtils
                            .getPresenceFromStaffList(jsonPresenceResponse, simpleJsonSlackData);

                return simpleJsonPresenceData;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

        }

        @Override
        protected void onPostExecute(Object[] slackSearchResults) {
            //mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (slackSearchResults != null && !slackSearchResults.equals("")){
                //showJsonDataView();
                String[] membersRealNames = (String[])slackSearchResults[0];
                String[] membersPresence = (String[])slackSearchResults[1];
                for(int i = 0; i < membersRealNames.length; i++){
                    mStaff_list.append((membersRealNames[i])+ "\n" + (membersPresence[i]) + "\n\n\n");
                }


            }else{
                //showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stafflist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_refresh) {
            Context context = MainActivity.this;
            String textToShow = "refresh clicked";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
            loadSlackData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onListItemClick(int clickedItemIndex){

        Context context = MainActivity.this;

        Class destinationActivity = ChildActivity.class;

        Intent startChildActivityIntent = new Intent(context, destinationActivity);



        if (mToast != null) {
            mToast.cancel();
        }

        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this, toastMessage,Toast.LENGTH_LONG);

        mToast.show();

        String textEntered = toastMessage;

        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, textEntered);

        startActivity(startChildActivityIntent);

    }

    private Cursor getAllStaff() {
        // COMPLETED (6) Inside, call query on mDb passing in the table name and projection String [] order by COLUMN_TIMESTAMP
        return mDb.query(
                StaffListContract.StaffListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                StaffListContract.StaffListEntry.COLUMN_TIMESTAMP
        );
    }


}
