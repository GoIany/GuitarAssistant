package com.poly.GuitarAssistant.Instrument;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.GuitarAssistant.GuitarChord.UnderbarMenu;
import com.poly.GuitarAssistant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Instrument extends AppCompatActivity {
    private static String IP_ADDRESS = "58.236.242.194";
    private static String TAG = "phptest";

    private EditText mEditTextName;
    private EditText mEditTextCountry;
    private TextView mTextViewResult;
    private ArrayList<PersonalData> mArrayList;
    private UserAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;
    UnderbarMenu underbarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument);
      //  mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

     //  mTextViewResult.setMovementMethod(new ScrollingMovementMethod());



        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.instrument_bar_layout);

        underbarMenu = findViewById(R.id.UnderBarMenu3);
        underbarMenu.setActivity(this);

        mArrayList = new ArrayList<>();

        mAdapter = new UserAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getjson.php", "");
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("종료 하시겠습니까");

        builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                finish();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
            }
        });
        builder.show();
    }

        private class GetData extends AsyncTask<String, Void, String> {

            ProgressDialog progressDialog;
            String errorString = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Instrument.this,
                        "Please Wait", null, true, true);
            }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
         //   mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

               // mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
              //  errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){

        String TAG_JSON="webnautes";
        String TAG_FILE_PATH = "file_path";
        String TAG_NAME = "name";
        String TAG_COMPANY ="company";
        String TAG_PRICE = "price";
        String TAG_STORE = "store";
        String TAG_PRICE2 = "price2";
        String TAG_STORE2 = "store2";
        String TAG_PRICE3 = "price3";
        String TAG_STORE3 = "store3";
        String TAG_PRICE4 = "price4";
        String TAG_STORE4 = "store4";
        String TAG_LINK = "link";
        String TAG_LINK2 = "link2";
        String TAG_LINK3 = "link3";
        String TAG_LINK4 = "link4";
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String file_path = item.getString(TAG_FILE_PATH);
                String name = item.getString(TAG_NAME);
                String company = item.getString(TAG_COMPANY);
                String price = item.getString(TAG_PRICE);
                String store = item.getString(TAG_STORE);
                String price2 = item.getString(TAG_PRICE2);
                String store2 = item.getString(TAG_STORE2);
                String price3 = item.getString(TAG_PRICE3);
                String store3 = item.getString(TAG_STORE3);
                String price4 = item.getString(TAG_PRICE4);
                String store4 = item.getString(TAG_STORE4);
                String link = item.getString(TAG_LINK);
                String link2 = item.getString(TAG_LINK2);
                String link3 = item.getString(TAG_LINK3);
                String link4 = item.getString(TAG_LINK4);
                PersonalData personalData = new PersonalData();

                personalData.setMember_file_path(file_path);
                personalData.setMember_name(name);
                personalData.setMember_company(company);
                personalData.setMember_price(price);
                personalData.setMember_store(store);
                personalData.setMember_price2(price2);
                personalData.setMember_store2(store2);
                personalData.setMember_price3(price3);
                personalData.setMember_store3(store3);
                personalData.setMember_price4(price4);
                personalData.setMember_store4(store4);
                personalData.setMember_link(link);
                personalData.setMember_link2(link2);
                personalData.setMember_link3(link3);
                personalData.setMember_link4(link4);
                mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}