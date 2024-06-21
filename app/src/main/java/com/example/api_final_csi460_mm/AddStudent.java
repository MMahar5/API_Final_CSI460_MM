package com.example.api_final_csi460_mm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddStudent extends AppCompatActivity {

    //Variables for layout components
    private EditText fName, lName, address, rollNum, mobile;
    private TextView txtView;
    Button submitBtn, backBtn;

    // Base URL for the API
    private static final String BASE_URL = "https://8112-73-118-137-185.ngrok-free.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

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
        submitBtn = findViewById(R.id.addBtn);
        backBtn = findViewById(R.id.backBtn);
        txtView = findViewById(R.id.textView3);

        //OnClick listener for the submit button to attempt to save the new created student and update our API data
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get user input from the user
                String fN = fName.getText().toString();
                String lN = lName.getText().toString();
                String add = address.getText().toString();
                int rN = Integer.parseInt(rollNum.getText().toString());
                String mb = mobile.getText().toString();

                //Create a new API response object
                ApiResponse newApiResponse = new ApiResponse();
                newApiResponse.setFirstName(fN);
                newApiResponse.setLastName(lN);
                newApiResponse.setAddress(add);
                newApiResponse.setRollNumber(rN);
                newApiResponse.setMobile(mb);

                // Send a POST request to the API to create the new entry
                Call<ApiResponse> call = apiService.postApiResponse(newApiResponse);
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Display the response from the server
                            txtView.setText("Post Response was successful");
                        } else {
                            // Indicate that the POST request was not successful
                            txtView.setText("Post Response was not successful");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        // Display an error message
                        txtView.setText("Error: " + t.getMessage());
                    }
                });
            }
        });

        //Back Button to return back to the main activity
        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddStudent.this, MainActivity.class);
                startActivity(intent);
            }
        });




    }
}