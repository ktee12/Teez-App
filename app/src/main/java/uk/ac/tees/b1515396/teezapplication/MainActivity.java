package uk.ac.tees.b1515396.teezapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import uk.ac.tees.b1515396.teezapplication.R;
import uk.ac.tees.b1515396.teezapplication.main.ApiActivity;
import uk.ac.tees.b1515396.teezapplication.main.HomeFragment;
import uk.ac.tees.b1515396.teezapplication.main.MapsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declare DrawerLayout
        drawerLayout = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        //Navigation
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener( this);
        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, new HomeFragment());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_draw_open,
                R.string.navigation_draw_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, new HomeFragment()).commit();
                break;
            case R.id.map:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, new MapsFragment()).commit();
                break;
            case R.id.api:
                Intent intent = new Intent(this, ApiActivity.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }
}