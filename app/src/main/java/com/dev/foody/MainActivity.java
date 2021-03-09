package com.dev.foody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerViewAdapter adapter;
    List<Models> list;
    RecyclerView recyclerView;
    TextView searchText;
    EditText queryText;
    View loaderView;
    ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeObjects();
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ViewModel.class);

    }

    private void initializeObjects() {
        queryText = findViewById(R.id.query_edittext);
        searchText = findViewById(R.id.search_button);
        recyclerView = findViewById(R.id.recycler_view);
        loaderView = findViewById(R.id.loading_icon);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchText.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View view) {
        String query = queryText.getText().toString().trim();
        if (!TextUtils.isEmpty(query)) {
            System.out.println("called--");
            list = new ArrayList<>();
            adapter = new RecyclerViewAdapter(list, MainActivity.this);
            recyclerView.setAdapter(adapter);
            hideSoftKeyboard(this);
            loaderView.setVisibility(View.VISIBLE);
            setData(query);
        } else {
            Toast.makeText(this, "Write some Key diet or cuisine", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(String query) {
        viewModel.init(MainActivity.this, query);
        viewModel.getCheckModel().observe(this, new Observer<List<Models>>() {
            @Override
            public void onChanged(List<Models> models) {
                if (models.size() > 0) {
                    list = models;
                    adapter.setData(list);
                    loaderView.setVisibility(View.GONE);
                } else {
                    loaderView.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "No data found try other keyword", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}