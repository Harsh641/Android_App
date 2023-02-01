package com.example.ecampus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecampus.databinding.ActivityDashBoardBinding;

import org.w3c.dom.Text;

public class DashBoardActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView name, email;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);

        View view = navigationView.getHeaderView(0);
        name = (TextView) view.findViewById(R.id.tv_name);
        email = (TextView) view.findViewById(R.id.tv_email);
        logout = navigationView.findViewById(R.id.btn_logout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(DashBoardActivity.this, drawerLayout,
                toolbar, R.string.navigation_drawer_close, R.string.navigation_drawer_open);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        SharedPreferences sharedPreferences = getSharedPreferences("MY_APP", MODE_PRIVATE);
        String strName = sharedPreferences.getString("Name", "");
        String strEmail = sharedPreferences.getString("Email", "");
        name.setText(strName);
        email.setText(strEmail);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.nav_home){
                    FragmentA fragmentA = new FragmentA();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.nav_fragment, fragmentA);
                    fragmentTransaction.commit();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MY_APP", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("Name");
                editor.remove("Email");
                editor.remove("Password");
                editor.clear();
                editor.commit();

                Intent i = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }

}