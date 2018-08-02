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
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity{
    WallpaperManager wm;
    public static int currentTapped = 0;
    public int viewID = R.id.corner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //create window
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO vertical pages
        final ViewPager mPager = (ViewPager) findViewById(R.id.homescreenPager);
        FragmentStatePagerAdapter mPagerAdapter = new HomescreenAdapter(this, getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        //setup wallpaper scrolling
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

        //set buttons in corners
        ImageButton ULButton = (ImageButton) findViewById(R.id.ULButton);
        ULButton.setOnClickListener(new CornerListener(corner.UL));

        ImageButton URButton = (ImageButton) findViewById(R.id.URButton);
        URButton.setOnClickListener(new CornerListener(corner.UR));

        ImageButton LLButton = (ImageButton) findViewById(R.id.LLButton);
        LLButton.setOnClickListener(new CornerListener(corner.LL));

        ImageButton LRButton = (ImageButton) findViewById(R.id.LRButton);
        LRButton.setOnClickListener(new CornerListener(corner.LR));
    }

    /**
     * Listener for menu clicks in home screen corners
     */
    private class CornerListener implements View.OnClickListener {
        private String cornerLabel;

        /**
         * constructor
         * @param corner the name (and position) of the button the listener is being applied to
         */
        public CornerListener(corner corner){
            super();
            cornerLabel=corner.toString();
        }
        @Override
        public void onClick(View v) {
            RelativeLayout main = ((RelativeLayout) findViewById(R.id.main_activity));
            //if there is an open corner menu already, remove it
            int temp = currentTapped;
            if(currentTapped!=0){
                currentTapped=0;
                main.removeView(findViewById(viewID));
            }

            //dont reopen the corner we just closed
            if(temp != v.getId()) {
                //set new opened corner
                currentTapped = v.getId();

                //create new corner layout
                LayoutInflater inflater = LayoutInflater
                        .from(getApplicationContext());
                CircularLayout corner = (CircularLayout) inflater.inflate(R.layout.corner, null);

                //add it to the main view
                main.addView(corner);

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

    /**
     * open the app drawer
     * @param view the app drawer button clicked
     */
    public void appDrawerClick(View view){
        //remove any open corner view
        ((RelativeLayout) findViewById(R.id.main_activity)).removeView(findViewById(viewID));
        //open the drawer
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
