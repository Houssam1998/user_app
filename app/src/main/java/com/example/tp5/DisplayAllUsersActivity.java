package com.example.tp5;

import android.os.Bundle;
// import android.widget.TextView; // On n'utilise plus TextView
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tp5.Adapters.UserAdapter;
import com.example.tp5.Bean.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// --- MODIFICATION : Implémenter l'interface ---
public class DisplayAllUsersActivity extends AppCompatActivity implements UserAdapter.OnUserClickListener {

    RecyclerView recyclerView;
    UserAdapter adapter;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_users);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();

        // --- MODIFICATION : Passer 'this' (l'activité) comme listener ---
        adapter = new UserAdapter(this, userList, this);
        recyclerView.setAdapter(adapter);

        loadAllUsers();
    }

    private void loadAllUsers() {
        String url = "http://10.0.2.2/android_api/get_all_users.php";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        userList.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            if (jsonArray.length() == 0) {
                                Toast.makeText(DisplayAllUsersActivity.this, "No users found", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject userObject = jsonArray.getJSONObject(i);
                                User user = new User();
                                user.setId(userObject.getInt("id"));
                                user.setFirstName(userObject.getString("first_name"));
                                user.setLastName(userObject.getString("last_name"));
                                try {
                                    user.setAge(userObject.getInt("age"));
                                } catch (JSONException e) {
                                    user.setAge(0);
                                }
                                user.setMajor(userObject.getString("major"));
                                userList.add(user);
                            }
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DisplayAllUsersActivity.this, "Error parsing user list", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DisplayAllUsersActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(request);
    }

    // --- NOUVELLE MÉTHODE (Implémentation de l'interface) ---
    /**
     * Cette méthode est appelée par l'adaptateur lorsque l'utilisateur
     * clique sur un item.
     * @param position La position de l'item cliqué
     */
    @Override
    public void onUserClick(int position) {
        // Récupérer l'utilisateur sur lequel on a cliqué
        User clickedUser = userList.get(position);

        // Pour l'instant, on affiche un Toast
        Toast.makeText(this, "Clic sur : " + clickedUser.getFirstName() + " " + clickedUser.getLastName(), Toast.LENGTH_SHORT).show();

        // Plus tard, vous pourrez ouvrir une nouvelle activité pour voir les détails
        // Intent intent = new Intent(this, UserDetailActivity.class);
        // intent.putExtra("USER_ID", clickedUser.getId());
        // startActivity(intent);
    }
}