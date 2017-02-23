package io.ray.hexis;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import io.ray.hexis.presenter.NavigationAdapter;
import io.ray.hexis.view.MatrixFragment;
import io.ray.hexis.view.abs.IMatrixFragment;

public class MainActivity extends AppCompatActivity{

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get current view to pass to navigation adapter object
        View v = findViewById(android.R.id.content);

        // Initialize toolbar object
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize navigation Adapter to handle item selection
        NavigationAdapter navAdapter = new NavigationAdapter(v);

        // Initialize drawer layout object
        // This adds the three horizontal navigation drawer bars in the toolbar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =
            new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        toggle.syncState();

        IMatrixFragment matrix = MatrixFragment.newInstance();

        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container_fragment, matrix.toFragment(), "0");
        ft.commit();
    }

    // Close navigation drawer when back button is pressed
    @Override public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
