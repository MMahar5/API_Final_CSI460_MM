package com.example.api_final_csi460_mm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // UI elements
    private TextView textView;
    private Button fetchAllButton, fetchSingleButton, postButton, patchButton, deleteButton;
    private EditText idInput, firstNameInput, lastNameInput, addressInput, rollNumberInput, mobileInput;

    // Base URL for the API
    private static final String BASE_URL = "https://8112-73-118-137-185.ngrok-free.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        textView = findViewById(R.id.textView);
        fetchAllButton = findViewById(R.id.fetchAllButton);
        fetchSingleButton = findViewById(R.id.fetchSingleButton);
        postButton = findViewById(R.id.postButton);
        patchButton = findViewById(R.id.patchButton);
        deleteButton = findViewById(R.id.deleteButton);
        idInput = findViewById(R.id.idInput);

        // Set up Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of the API service
        ApiService apiService = retrofit.create(ApiService.class);


        //Button that gets all the data and displays the json data
        fetchAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch all data from the API
                new GetApiDataTask().execute(BASE_URL + "api/basic/?format=json");
            }
        });


        //Button gets one students data based on the Id that was entered by the user
        fetchSingleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Does a validation check to make sure the ID field is not empty and there is a valid number
                String strId = idInput.getText().toString().trim(); // Get input and trim whitespace
                if(!strId.isEmpty()){
                    try{
                        int id = Integer.parseInt(strId);
                        //Calls Asynctask to retrieve data based on ID
                        new GetSingleApiDataTask().execute(BASE_URL + "api/basic/" + id + "/?format=json");
                    }
                    catch (NumberFormatException e) {
                        // Handle the case where inputId is not a valid integer
                        Toast.makeText(MainActivity.this, "Invalid ID input", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Please enter an ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        postButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddStudent.class);
                startActivity(intent);
            }
        });



        //This button takes the user to the EditStudent activity and uses the ID to get the student information
        patchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Does a validation check to make sure the ID field is not empty and there is a valid number
                String strId = idInput.getText().toString().trim(); // Get input and trim whitespace
                if(!strId.isEmpty()){
                    try{
                        int id = Integer.parseInt(strId);

                        // Create intent to start EditStudent Activity
                        Intent intent = new Intent(MainActivity.this, EditStudent.class);

                        // Pass the selected ID to as an extra
                        intent.putExtra("ID_EXTRA", id);
                        startActivity(intent);

                    }
                    catch (NumberFormatException e) {
                        // Handle the case where inputId is not a valid integer
                        Toast.makeText(MainActivity.this, "Invalid ID input", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Please enter an ID", Toast.LENGTH_SHORT).show();
                }

            }
        });



        //Button to delete a student by using their ID
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Delete an entry by ID
                int id = Integer.parseInt(idInput.getText().toString());
                Call<Void> call = apiService.deleteApiResponse(id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Indicate that the entry was deleted successfully
                            textView.setText("Record deleted successfully");
                        } else {
                            // Indicate that the delete request was not successful
                            textView.setText("Delete Response was not successful");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Display an error message
                        textView.setText("Error: " + t.getMessage());
                    }
                });
            }
        });
    }



    // AsyncTask to fetch all data from the API
    private class GetApiDataTask extends AsyncTask<String, Void, String> {

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

        @Override
        protected void onPostExecute(String result) {
            // Display the result in the textView
            textView.setText(result);
        }
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

        @Override
        protected void onPostExecute(String result) {
            // Display the result in the textView
            textView.setText(result);
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
            textView.setText(result);
        }
    }


}