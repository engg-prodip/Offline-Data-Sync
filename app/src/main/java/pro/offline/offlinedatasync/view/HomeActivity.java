package pro.offline.offlinedatasync.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import pro.offline.offlinedatasync.MainActivity;
import pro.offline.offlinedatasync.R;
import pro.offline.offlinedatasync.response.SignUpResponse;
import pro.offline.offlinedatasync.service.RetrofitClient;
import pro.offline.offlinedatasync.sharedpreference.StoreData;
import pro.offline.offlinedatasync.sqlite.DatabaseAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DetailsPagerAdapter pagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private ProfileFragment profileFragment;
    private UserInfoFragment userInfoFragment;
    private StoreData store;
    private MyConnectivityReceiver myConnectivityReceiver;
    private DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Unifox Digital Media");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);
        checkPhoneStatePermission();
        store = StoreData.getInstance(getApplicationContext());
        databaseAdapter = new DatabaseAdapter(this);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.addTab(tabLayout.newTab().setText("Offline User List"));

        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);

        profileFragment = new ProfileFragment();
        userInfoFragment = new UserInfoFragment();

        fragmentList.add(profileFragment);
        fragmentList.add(userInfoFragment);

        pagerAdapter = new DetailsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

       // sendToServer();
    }

    private class DetailsPagerAdapter extends FragmentPagerAdapter {
        public DetailsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            store.setLoggedStatus(false);
            store.setOfflineStatus(0);
            store.setUserData("", "", "", "", "", "", "");
            Intent userMainIntent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(userMainIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean checkPhoneStatePermission(){
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_NETWORK_STATE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE},555);
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        myConnectivityReceiver = new MyConnectivityReceiver();
        registerReceiver(myConnectivityReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myConnectivityReceiver);
    }

    private class MyConnectivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                sendToServer();
                //Toast.makeText(HomeActivity.this, "Connection!", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(HomeActivity.this, "Disconnection!", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void sendToServer() {
        if (store.getLoggedStatus() == true && store.getOfflineStatus() == 0) {
            Call<SignUpResponse> call = RetrofitClient.getInstance()
                    .getApi().createUser(store.getUserName(), store.getUserEmail(), store.getUserPhone(),
                            store.getCompanyName(), store.getDesignation(), store.getPassword());
            call.enqueue(new Callback<SignUpResponse>() {
                @Override
                public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getError().equals(false)) {
                            long updateRow = databaseAdapter.UserOfflineStatus(Integer.valueOf(store.getUserID()), 1);
                            if (updateRow > 0) {
                                store.setOfflineStatus(1);

                                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                                builder.setMessage("Your Offline Data Updated!");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(HomeActivity.this, "Check Your Data From Sync User", Toast.LENGTH_LONG).show();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<SignUpResponse> call, Throwable t) {

                }
            });
        }
    }

}
