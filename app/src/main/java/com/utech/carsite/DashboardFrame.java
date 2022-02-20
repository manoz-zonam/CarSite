package com.utech.carsite;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.utech.carsite.API.ApiHelper;
import com.utech.carsite.API.ApiService;
import com.utech.carsite.adapters.DistrictByStateAdapter;
import com.utech.carsite.fragments.HomePage;
import com.utech.carsite.model.DistrictByStateModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFrame extends AppCompatActivity {

    @BindView(R.id.bottomNav)
    BottomNavigationView bottomNav;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.scrollBar)
    ScrollView scrollBar;

    @BindView(R.id.navigation_view)
    NavigationView navView;

    @BindView(R.id.search_by_district)
    TextView search_by_district;

    @BindView(R.id.favorite)
    ImageView favorite;

    @BindView(R.id.notification)
    ImageView notification;

    @BindView(R.id.recyclerviewforDistrict)
    RecyclerView recyclerView;

    ActionBarDrawerToggle actionBarDrawerToggle;
    Call<JsonElement> call;
    Dialog dialog;
    String state_id, state_name, district_id, district_name;
    ArrayList<DistrictByStateModel> arrayList;

    DistrictByStateAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_frame);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        RecyclerView.LayoutManager linearlayoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearlayoutmanager);

        search_by_district = (TextView) findViewById(R.id.search_by_district);
        search_by_district.clearFocus();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomePage()).commit();
        bottomNav.setSelectedItemId(R.id.home);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.red));



        search_by_district.setTextColor(Color.parseColor("#000000"));
        search_by_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context;
                dialog = new Dialog(DashboardFrame.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
                dialog.show();

                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                getDetails();
                adapter = new DistrictByStateAdapter(DashboardFrame.this, android.R.layout.support_simple_spinner_dropdown_item);

                search_by_district.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(search_by_district.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                search_by_district.setOnClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        search_by_district.setText(adapter);
                    }
                });
            }
        });





        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        bottomNav.setSelectedItemId(R.id.home);
//        bottomNav.setBackgroundColor(R.th);
        scrollBar.setVerticalScrollBarEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    public void getDetails() {
        ApiService api = (ApiService) ApiHelper.getInstance().getService(ApiService.class);
        call = api.getDistrictByState();
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    String resValue = response.body().toString();
                    if (resValue != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(resValue);
                            String resCode = jsonObject.getString("success");
                            if (resCode.equals("true")) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                if (data.has("1")) {
                                    JSONObject insidedata = data.getJSONObject("1");

                                    if (insidedata.has("district")) {
                                        JSONArray districtdata = insidedata.getJSONArray("district");

                                        arrayList = new ArrayList<>();
                                        for (int i = 0; i < districtdata.length(); i++) {
                                            JSONObject districtdata2 = districtdata.getJSONObject(i);
                                            state_id = districtdata2.getString("state_id");
                                            state_name = districtdata2.getString("state_name");
                                            district_id = districtdata2.getString("district_id");
                                            district_name = districtdata2.getString("district_name");

                                            arrayList.add(new DistrictByStateModel(state_id, state_name, district_id, district_name));
                                        }

                                        adapter = new DistrictByStateAdapter(arrayList, getApplicationContext());
                                        recyclerView.setAdapter(adapter);
                                    }
                                }
                            }

                        } catch (
                                JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

}