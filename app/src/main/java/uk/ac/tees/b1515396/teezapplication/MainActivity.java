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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import uk.ac.tees.b1515396.teezapplication.api.ApiActivity;
import uk.ac.tees.b1515396.teezapplication.main.HomeFragment;
import uk.ac.tees.b1515396.teezapplication.main.MapsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;

    // Get the user details
    private FirebaseUser user;
    private DatabaseReference dbReference;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Navigation header
        //NavigationView navHeader = findViewById(R.id.nav)
        initHeader();

        // Declare DrawerLayout
        drawerLayout = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);


        //Navigation view
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

    private void initHeader() {
        // Get firebase details
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbReference = FirebaseDatabase.getInstance().getReference();
        userID = user.getUid();

        // use headerView to get nav_header
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        TextView fullNameTextView = headerView.findViewById(R.id.greeting);
        TextView emailTextView = headerView.findViewById(R.id.email);

        dbReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;

                    fullNameTextView.setText("Welcome, " + fullName);
                    emailTextView.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

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
                Intent intentApi = new Intent(this, ApiActivity.class);
                startActivity(intentApi);
                break;
            case R.id.logout:
                initLogout();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initLogout() {
        //TODO: Get the fullName here.
        FirebaseAuth.getInstance().signOut();
        Intent intentLogout = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intentLogout);

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