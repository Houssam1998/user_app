package com.example.tp5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAddUser, btnDeleteUser, btnDisplayUser, btnDisplayAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des boutons
        btnAddUser = findViewById(R.id.btnAddUser);
        btnDeleteUser = findViewById(R.id.btnDeleteUser);
        btnDisplayUser = findViewById(R.id.btnDisplayUser);
        btnDisplayAll = findViewById(R.id.btnDisplayAll);

        // Ajout des listeners
        btnAddUser.setOnClickListener(this);
        btnDeleteUser.setOnClickListener(this);
        btnDisplayUser.setOnClickListener(this);
        btnDisplayAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.btnAddUser) {
            intent = new Intent(MainActivity.this, AddUserActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnDeleteUser) {
            intent = new Intent(MainActivity.this, DeleteUserActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnDisplayUser) {
            intent = new Intent(MainActivity.this, DisplayUserActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnDisplayAll) {
            intent = new Intent(MainActivity.this, DisplayAllUsersActivity.class);
            startActivity(intent);
        }
    }
}