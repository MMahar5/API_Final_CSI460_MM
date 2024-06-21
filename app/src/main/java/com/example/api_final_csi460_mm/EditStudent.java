package com.example.api_final_csi460_mm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditStudent extends AppCompatActivity {

    //Variables for layout components
    private EditText fName, lName, address, rollNum, mobile;
    private TextView txtView;
    Button editBtn, backBtn;
    Integer id;

    // Base URL for the API
    private static final String BASE_URL = "https://8112-73-118-137-185.ngrok-free.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        // Set up Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of the API service
        ApiService apiService = retrofit.create(ApiService.class);

        //Instantiate our layout components
        fName = findViewById(R.id.firstNameInput);
        lName = findViewById(R.id.lastNameInput);
        address = findViewById(R.id.addressInput);
        rollNum = findViewById(R.id.rollNumberInput);
        mobile = findViewById(R.id.mobileInput);
        editBtn = findViewById(R.id.addBtn);
        backBtn = findViewById(R.id.backBtn);
        txtView = findViewById(R.id.textView3);

        //Receive the ID value passed from main activity
        Intent intent = getIntent();
        if(intent.hasExtra("ID_EXTRA")) {
            id = intent.getIntExtra("ID_EXTRA", -1);


            //This calls the async task method to get retrieve the json data of the student based on their Id
            new EditStudent.GetSingleApiDataTask().execute(BASE_URL + "api/basic/" + id + "/?format=json");

        }


        //This is the patch button for when the user is ready to make the changes
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the existing student data in the API
                String firstName = fName.getText().toString();
                String lastName = lName.getText().toString();
                String addrs = address.getText().toString();
                int rollNumber = Integer.parseInt(rollNum.getText().toString());
                String mobil = mobile.getText().toString();

                //Create a new API response object with the updated data
                ApiResponse updatedApiResponse = new ApiResponse();
                updatedApiResponse.setFirstName(firstName);
                updatedApiResponse.setLastName(lastName);
                updatedApiResponse.setAddress(addrs);
                updatedApiResponse.setRollNumber(rollNumber);
                updatedApiResponse.setMobile(mobil);

                //Send a PATCH request to update the entry
                new PatchApiDataTask().execute(BASE_URL + "api/basic/" + id + "/", new Gson().toJson(updatedApiResponse));
            }
        });

        //Back Button to return back to the main activity
        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditStudent.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }


    //AsyncTask to fetch a single data entry from the API by ID
    private class GetSingleApiDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            StringBuilder result = new StringBuilder();
            try {
                // Create a URL object from the URL string
                URL url = new URL(urlString);
                // Open a connection to the URL
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                // Set the request method to GET
                urlConnection.setRequestMethod("GET");
                // Create a BufferedReader to read the response from the server
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                // Read the response line by line and append it to the result
                while ((inputLine = in.readLine()) != null) {
                    result.append(inputLine);
                }
                in.close();
                // Disconnect the URL connection
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Return the result as a string
            return result.toString();
        }

        //Post execute extracts and inserts the student data into each Edit text field for the user to update.
        @Override
        protected void onPostExecute(String result) {
            // Display the result in the textView
            txtView.setText(result);

            try {
                JSONObject jsonObject = new JSONObject(result);

                //Extract student information from JSON
                String firstName = jsonObject.getString("firstName");
                String lastName = jsonObject.getString("lastName");
                String address1 = jsonObject.getString("address");
                int rollNumber = jsonObject.getInt("rollNumber");
                String mobile1 = jsonObject.getString("mobile");

                //Update EditText fields with student information
                fName.setText(firstName);
                lName.setText(lastName);
                address.setText(address1);
                rollNum.setText(String.valueOf(rollNumber));
                mobile.setText(mobile1);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // AsyncTask to patch (update) an existing data entry in the API
    private class PatchApiDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            String jsonInputString = strings[1];
            StringBuilder result = new StringBuilder();
            try {
                // Create a URL object from the URL string
                URL url = new URL(urlString);
                // Open a connection to the URL
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                // Set the request method to PATCH
                urlConnection.setRequestMethod("PATCH");
                // Set the request property to indicate the content type is JSON
                urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                // Allow output to the connection
                urlConnection.setDoOutput(true);
                // Write the JSON input string to the output stream
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"))) {
                    writer.write(jsonInputString);
                    writer.flush();
                }
                // Create a BufferedReader to read the response from the server
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                // Read the response line by line and append it to the result
                while ((inputLine = in.readLine()) != null) {
                    result.append(inputLine);
                }
                in.close();
                // Disconnect the URL connection
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Return the result as a string
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            // Display the result in the textView
            txtView.setText(result);
        }
    }


}