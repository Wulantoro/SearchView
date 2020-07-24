package com.example.nugroho_takehome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.nugroho_takehome.Adapter.UserAdapter;
import com.example.nugroho_takehome.Model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvuser;
    private UserAdapter userAdapter;
    private Gson gson;
    private User user;
    private EditText etsearch;


    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvuser = findViewById(R.id.rvuser);
        etsearch = findViewById(R.id.etsearch);

        List<User> result = new ArrayList<>();
        result.add(user);
        gson = new Gson();
        userAdapter = new UserAdapter(getApplication());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rvuser.setLayoutManager(layoutManager);
        loadUser();
        rvuser.setAdapter(userAdapter);

        final List<User> list = userAdapter.getList_item();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        etsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final List<User> filteredList = new ArrayList<>();

                if (list != null & list.size() > 0) {

                    for (int i = 0; i < list.size(); i++) {
                        final String text = String.valueOf(list.get(i).getLogin()).toLowerCase();
                        if (text.contains(query.toString())) {

                            filteredList.add(list.get(i));
                        }
                    }
                    rvuser.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    userAdapter = new UserAdapter(filteredList, MainActivity.this);
                    rvuser.setAdapter(userAdapter);
                    userAdapter.notifyDataSetChanged();
                }
            }
        });
        rvuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    public void loadUser() {

        if (userAdapter != null) {
            userAdapter.clearAll();

            AndroidNetworking.get("https://api.github.com/users")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            List<User> result = new ArrayList<>();
                            try {
                                Log.e(TAG, "tampil user = " + response);

                                        for (int i = 0; i < response.length(); i++) {
                                            User user = gson.fromJson(response.getJSONObject(i).toString(), User.class);
                                            result.add(user);
                                        }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            userAdapter.addAll(result);
                        }
                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        }
    }
}