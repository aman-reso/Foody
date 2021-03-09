package com.dev.foody;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

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
import java.util.List;

public class DataRepo {

    List<Models> list;
    MutableLiveData<List<Models>> data;


    public MutableLiveData<List<Models>> getTimeFromServer(Context context, String query) {
        list = new ArrayList<>();
        data = new MutableLiveData<>();
        fetchQuery(query, context);
        fetchDiet(query, context);

        return data;
    }

    private void fetchQuery(String query, Context context) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoints.URL + "&query=" + query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray resultArray = jsonObject.getJSONArray("results");
                    if (resultArray.length() > 0) {
                        for (int i = 0; i < resultArray.length(); i++) {
                            Models models = new Models();

                            String id = resultArray.getJSONObject(i).getString("id");
                            String title = resultArray.getJSONObject(i).getString("title");
                            String image_url = resultArray.getJSONObject(i).getString("image");

                            models.setId(id);
                            models.setImgUrl(image_url);
                            models.setTitle(title);

                            list.add(models);
                        }
                        data.postValue(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(context, "Some error got-->" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    private void fetchDiet(String query, Context context) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoints.URL + "&diet=" + query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray resultArray = jsonObject.getJSONArray("results");
                    if (resultArray.length() > 0) {
                        for (int i = 0; i < resultArray.length(); i++) {
                            Models models = new Models();

                            String id = resultArray.getJSONObject(i).getString("id");
                            String title = resultArray.getJSONObject(i).getString("title");
                            String image_url = resultArray.getJSONObject(i).getString("image");

                            models.setId(id);
                            models.setImgUrl(image_url);
                            models.setTitle(title);
                            list.add(models);
                        }
                        data.postValue(list);

                    } else {
                        Toast.makeText(context, "No data found try other keyword", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(context, "Some error got-->" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


}
