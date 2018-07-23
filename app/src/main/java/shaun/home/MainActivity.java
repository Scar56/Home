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
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentStatePagerAdapter;
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

public class MainActivity extends AppCompatActivity{
    WallpaperManager wm;
    public static int currentTapped = 0;
    public int viewID = R.id.corner;
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
        ImageButton ULButton = (ImageButton) findViewById(R.id.ULButton);
        ULButton.setOnClickListener(new CornerListener("UL"));

        ImageButton URButton = (ImageButton) findViewById(R.id.URButton);
        URButton.setOnClickListener(new CornerListener("UR"));

        ImageButton LLButton = (ImageButton) findViewById(R.id.LLButton);
        LLButton.setOnClickListener(new CornerListener("LL"));

        ImageButton LRButton = (ImageButton) findViewById(R.id.LRButton);
        LRButton.setOnClickListener(new CornerListener("LR"));
    }
    private class CornerListener implements View.OnClickListener {
        private String cornerLabel;
        public CornerListener(String corner){
            super();
            cornerLabel=corner;
        }
        @Override
        public void onClick(View v) {
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
//                int id = View.generateViewId();
//                viewID = id;
//                corner.setId(id);
                //add it to the main view
                main.addView(corner);

//                DisplayMetrics displayMetrics = new DisplayMetrics();
//                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//                int width = displayMetrics.widthPixels;
//                int height = displayMetrics.heightPixels;

                //set position
                CircularLayout cornerView = findViewById(R.id.corner);
                RelativeLayout.LayoutParams cLayParam = (RelativeLayout.LayoutParams) cornerView.getLayoutParams();
                switch (cornerLabel){
                    case "UL":
                        cLayParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                        cLayParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        break;
                    case "UR":
                        cLayParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                        cLayParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        break;
                    case "LL":
                        cLayParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        cLayParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        break;
                    case "LR":
                        cLayParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        cLayParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        break;
                }
                cornerView.setLayoutParams(cLayParam);
                cornerView.setAdapter(new CornerAdapter(getBaseContext(),cornerLabel));

            }
        }


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

}
