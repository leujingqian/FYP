package com.example.myapplication1.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication1.Model.Createclass;
import com.example.myapplication1.Model.Joinclass;
import com.example.myapplication1.R;
import com.google.android.material.navigation.NavigationView;

public class classroom extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar) ;

        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1, new CreatedFragment()).commit();
            navigationView.setCheckedItem(R.id.Created);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.Created:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,new CreatedFragment(),"Created Fragment").commit();
                break;
            case R.id.Joined:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,new JoinedFragmnet(),"Joined Fragment").commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START );
        return true;
    }



    public void onbackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.classroom_rightmenu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        switch(item.getItemId()){
            case R.id.joinclass:
            Intent intent=new Intent(classroom.this, JoinClass.class);
                startActivity(intent);
                break;

            case R.id.createclass:
                Intent intent1=new Intent(classroom.this, CreateClass.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
