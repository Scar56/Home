package shaun.home;
/*
    Nexus is a home launcher for the Android platform
    Copyright (C) 2018 Shaun Carpenter

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
import android.app.WallpaperManager;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.PowerManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    WallpaperManager wm;
    public static int currentTapped = 0;
    public int viewID;
    private OnGestureListener gestureScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.getSharedPreferences("screens", MODE_PRIVATE).edit().putInt("horiz",3).apply();
        this.getSharedPreferences("corners", MODE_PRIVATE).edit().putInt("LR",1).apply();

        //TODO vertical pages
        final ViewPager mPager = (ViewPager) findViewById(R.id.homescreenPager);
        FragmentStatePagerAdapter mPagerAdapter = new HomescreenAdapter(this, getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        wm = WallpaperManager.getInstance(this);
        wm.setWallpaperOffsetSteps(.5f,-1f);


        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageScrolled(int p1, float p2, int p3) {
                WallpaperManager.getInstance(getBaseContext()).setWallpaperOffsets(mPager.getWindowToken(), (p1+p2) / (2), 0);
            }});

//        //
//        mPager.addOnPageChangeListener(new TwoDimViewPager.SimpleOnPageChangeListener(){
//            @Override
//            public void onPageScrolled(int p1[], float p2, int p3) {
//                WallpaperManager.getInstance(getBaseContext()).setWallpaperOffsets(mPager.getWindowToken(), (p1[0]+p2) / (2), 0);
//            }});
        ImageButton button = (ImageButton) findViewById(R.id.LRButton);
        button.setOnClickListener(new CornerListener());
    }
    private class CornerListener implements View.OnClickListener {
        public CornerListener(){
            super();

        }
        @Override
        public void onClick(View v) {
            lock();
            RelativeLayout main = ((RelativeLayout) findViewById(R.id.main_activity));
            int temp = currentTapped;
            if(currentTapped!=0){
                currentTapped=0;
                main.removeView(findViewById(viewID));
            }
            if(temp != v.getId()) {
                currentTapped = v.getId();
                //create new corner layout
                LayoutInflater inflater = LayoutInflater
                        .from(getApplicationContext());
                CircularLayout corner = (CircularLayout) inflater.inflate(R.layout.corner, null);
                //give it an id
                int id = View.generateViewId();
                viewID = id;
                corner.setId(id);
                //add it to the main view
                main.addView(corner);
                //change images
                ((ImageView) findViewById(R.id.imageView2)).setImageDrawable(MainActivity.getActivityIcon(getBaseContext(), "com.android.chrome", "com.google.android.apps.chrome.Main"));
                ((ImageView) findViewById(R.id.imageView3)).setImageDrawable(MainActivity.getActivityIcon(getBaseContext(), "com.android.chrome", "com.google.android.apps.chrome.Main"));

//                DisplayMetrics displayMetrics = new DisplayMetrics();
//                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//                int width = displayMetrics.widthPixels;
//                int height = displayMetrics.heightPixels;

                //set position
                CircularLayout cornerView = findViewById(id);
                RelativeLayout.LayoutParams cLayParam = (RelativeLayout.LayoutParams) cornerView.getLayoutParams();
                cLayParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                cLayParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                cornerView.setLayoutParams(cLayParam);
                cornerView.setAdapter(new CornerAdapter(getBaseContext(),"LR"));

            }
        }


    }

    public void cornerClick(View view){
        view.getY();
    }

    public void appDrawerClick(View view){
        startActivity(new Intent(this, AppDrawer.class));
    }

    public static Drawable getActivityIcon(Context context, String packageName, String activityName) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityName));
        ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);

        return resolveInfo.loadIcon(pm);
    }


    public class AdminReceiver extends DeviceAdminReceiver {
        public static final String ACTION_DISABLED = "device_admin_action_disabled";
        public static final String ACTION_ENABLED = "device_admin_action_enabled";

        public AdminReceiver(){
            super();
        }
        @Override
        public void onDisabled(Context context, Intent intent) {
            super.onDisabled(context, intent);
            LocalBroadcastManager.getInstance(context).sendBroadcast(
                    new Intent(ACTION_DISABLED));
        }
        @Override
        public void onEnabled(Context context, Intent intent) {
            super.onEnabled(context, intent);
            LocalBroadcastManager.getInstance(context).sendBroadcast(
                    new Intent(ACTION_ENABLED));
        }
    }
    private void lock() {
        PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
        if (pm.isScreenOn()) {
            DevicePolicyManager policy = (DevicePolicyManager)
                    getSystemService(Context.DEVICE_POLICY_SERVICE);
            try {
                policy.lockNow();
            } catch (SecurityException ex) {
                Toast.makeText(
                        this,
                        "must enable device administrator",
                        Toast.LENGTH_LONG).show();
                ComponentName admin = new ComponentName(this, AdminReceiver.class);
                Intent intent = new Intent(
                        DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).putExtra(
                        DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin);
                this.startActivity(intent);
            }
        }
    }
}
