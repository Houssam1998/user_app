package com.example.tp5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DisplayUserActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUserId;
    Button btnGetUser;
    TextView tvUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);

        etUserId = findViewById(R.id.etUserId);
        btnGetUser = findViewById(R.id.btnGetUser);
        tvUserDetails = findViewById(R.id.tvUserDetails);

        btnGetUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnGetUser) {
            getUser();
        }
    }

    private void getUser() {
        String userId = etUserId.getText().toString().trim();
        if (userId.isEmpty()) {
            Toast.makeText(this, "Please enter a user ID", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.0.2.2/android_api/get_user.php?id=" + userId;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.has("error")) {
                                String error = jsonResponse.getString("error");
                                tvUserDetails.setText(error);
                            } else {
                                String userDetails = "ID: " + jsonResponse.getString("id") + "\n" +
                                        "First Name: " + jsonResponse.getString("first_name") + "\n" +
                                        "Last Name: " + jsonResponse.getString("last_name") + "\n" +
                                        "Age: " + jsonResponse.getString("age") + "\n" +
                                        "Major: " + jsonResponse.getString("major") + "\n" +
                                        "Added: " + jsonResponse.getString("added_time");
                                tvUserDetails.setText(userDetails);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            tvUserDetails.setText("Error parsing user data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvUserDetails.setText("Error: " + error.getMessage());
                    }
                });

        Volley.newRequestQueue(this).add(request);
    }
}