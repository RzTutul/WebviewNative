package com.rztechtunes.motherbdrecharge;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private NavigationView navigationView;
    boolean isExit;
    boolean isBack = false;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isNetworkAvailable();
       // checkExpire();

        /*final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
//                    case R.id.dashBoardFragment:
//                        isExit = true;
//                        break;
//                    case R.id.speakingPractices:
//                        isBack = true;
//                        isExit = false;
//                        break;
//                    default:
//                        isExit = false;
//                        break;
                }
            }
        });


    }

    public void isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
        } else {
            AlertDialog.Builder builder =new AlertDialog.Builder(this);
            builder.setTitle("No internet Connection");
            builder.setMessage("Please turn on internet connection to continue");
            builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    public void checkExpire() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading$#>>>");
        progressDialog.setCancelable(false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/RzTutul/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);

        service.chcekExpired("ApiResponse/main/pharma").enqueue(new Callback<ExpireResponse>() {
            @Override
            public void onResponse(Call<ExpireResponse> call, Response<ExpireResponse> response) {
                if (response.body() != null) {
                    if (!response.body().getStatus()) {
                        progressDialog.show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ExpireResponse> call, Throwable t) {

                Log.i(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.home_menu) {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.homeFrag);
        }


        else if (menuItem.getItemId() == R.id.result_menu) {

            Bundle bundle = new Bundle();
            bundle.putString("page", "previous_result");
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.homeFrag, bundle);
        }

        else if  (menuItem.getItemId() == R.id.common_menu) {

            Bundle bundle = new Bundle();
            bundle.putString("page", "common_number");
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.homeFrag, bundle);
        }

        else if  (menuItem.getItemId() == R.id.dream_menu) {

            Bundle bundle = new Bundle();
            bundle.putString("page", "dream_number");
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.homeFrag, bundle);
        }


        else if (menuItem.getItemId() == R.id.share_menu) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Morning Teer Today App");
                String shareMessage = "\nInstall this cool App.\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n"+"Visit Website: https://morningteertoday.com/";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

/*    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (isExit) {
//            new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
//                    .setTitleText("Rate this app?")
//                    .setCustomImage(R.drawable.ratingimage)
//                    .setContentText("if you enjoy this app,would you mind taking a moment to rate it? it won't take more than a minute.Thank you!")
//                    .setConfirmText("Rate Now")
//                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sDialog) {
//                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                            try {
//                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                            } catch (android.content.ActivityNotFoundException anfe) {
//                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                            }
//                            sDialog.dismissWithAnimation();
//                        }
//                    })
//                    .setCancelButton("Later", new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sDialog) {
//                            MainActivity.this.finish();
//                            sDialog.dismissWithAnimation();
//                        }
//                    })
//                    .show();
        } else if (isBack) {
            //  Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.dashBoardFragment);

        } else {
            super.onBackPressed();
        }
    }*/
}