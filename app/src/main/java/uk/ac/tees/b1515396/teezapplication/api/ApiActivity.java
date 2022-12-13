package uk.ac.tees.b1515396.teezapplication.api;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import uk.ac.tees.b1515396.teezapplication.R;

public class ApiActivity extends AppCompatActivity {

    //declare the variables
    private RecyclerView courseRecyclerView;

    //declare variable for adapter and arrayList
    private CourseAdapter adapter;
    private ArrayList<CourseModel> courseModelArrayList;

    // API string
    String apiURL = "https://run.mocky.io/v3/6dc2223e-3dc8-44e6-8f99-98a15f754fde";

    // Progress bar
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        // Get the variables
        courseRecyclerView = findViewById(R.id.Rv_Courses);
        progressBar = findViewById(R.id.progressBar);

        // Create new array
        courseModelArrayList = new ArrayList<>();
        getData();

        // build recycler view.
        buildRecyclerView();

    }

    private void getData() {
        // create variable for request queue
        RequestQueue requestQueue = Volley.newRequestQueue(ApiActivity.this);

        // Get json array request.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                apiURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        courseRecyclerView.setVisibility(View.VISIBLE);
                        for (int i = 0; i < response.length(); i++) {
                            // create new json object & get from json array.
                            try {
                                // get each object
                                JSONObject responseObj = response.getJSONObject(i);

                                // extract the information
                                String courseName = responseObj.getString("courseName");
                                String courseTracks = responseObj.getString("courseTracks");
                                String courseMode = responseObj.getString("courseMode");
                                String courseImageURL = responseObj.getString("courseimg");
                                courseModelArrayList.add(new CourseModel(
                                        courseName,
                                        courseImageURL,
                                        courseMode,
                                        courseTracks));
                                buildRecyclerView();
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ApiActivity.this, "Failed to get the data...", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void buildRecyclerView(){
        // initiate adapter
        adapter = new CourseAdapter(courseModelArrayList, ApiActivity.this);

        //Add layout manager to recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRecyclerView.setHasFixedSize(true);

        // setting layout manager to view recycler view.
        courseRecyclerView.setLayoutManager(manager);

        // setting adapter manager to recycler view.
        courseRecyclerView.setAdapter(adapter);
    }


}