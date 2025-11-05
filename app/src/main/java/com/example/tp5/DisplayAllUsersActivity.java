package com.example.tp5;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayAllUsersActivity extends AppCompatActivity {

    TextView tvAllUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_users);

        tvAllUsers = findViewById(R.id.tvAllUsers);

        loadAllUsers();
    }

    private void loadAllUsers() {
        String url = "http://10.0.2.2///android_api/get_all_users.php";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            StringBuilder stringBuilder = new StringBuilder();

                            if (jsonArray.length() == 0) {
                                tvAllUsers.setText("No users found");
                                return;
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);
                                stringBuilder.append("ID: ").append(user.getString("id"))
                                        .append("\nName: ").append(user.getString("first_name"))
                                        .append(" ").append(user.getString("last_name"))
                                        .append("\nAge: ").append(user.getString("age"))
                                        .append("\nMajor: ").append(user.getString("major"))
                                        .append("\nAdded: ").append(user.getString("added_time"))
                                        .append("\n\n-------------------\n\n");
                            }
                            tvAllUsers.setText(stringBuilder.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            tvAllUsers.setText("Error parsing user list");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvAllUsers.setText("Error: " + error.getMessage());
                    }
                });

        Volley.newRequestQueue(this).add(request);
    }
}