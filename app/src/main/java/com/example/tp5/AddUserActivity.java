package com.example.tp5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etFirstName, etLastName, etAge, etMajor;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAge = findViewById(R.id.etAge);
        etMajor = findViewById(R.id.etMajor);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {
            addUser();
        }
    }

    private void addUser() {
        // Validation basique
        if (etFirstName.getText().toString().trim().isEmpty() ||
                etLastName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please fill at least first and last name", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.0.2.2/android_api/add_user.php"; // 10.0.2.2 pour l'émulateur

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String message = jsonResponse.getString("message");
                            Toast.makeText(AddUserActivity.this, message, Toast.LENGTH_SHORT).show();

                            // Vider les champs après succès
                            if (message.equals("User added successfully")) {
                                etFirstName.setText("");
                                etLastName.setText("");
                                etAge.setText("");
                                etMajor.setText("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddUserActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddUserActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", etFirstName.getText().toString().trim());
                params.put("last_name", etLastName.getText().toString().trim());
                params.put("age", etAge.getText().toString().trim());
                params.put("major", etMajor.getText().toString().trim());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}