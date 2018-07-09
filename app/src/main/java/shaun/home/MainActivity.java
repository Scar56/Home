package shaun.home;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    WallpaperManager wm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSharedPreferences("screens", MODE_PRIVATE).edit().putInt("horiz",3).commit();

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
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater
                        .from(getApplicationContext());
                ImageView corner = (ImageView) inflater.inflate(R.layout.corner, null);
                //se we figured out how to inflate the image, still nee to figure out the positioning
                corner.setImageDrawable(MainActivity.getActivityIcon(getBaseContext(), "com.android.chrome", "com.google.android.apps.chrome.Main"));
                ((RelativeLayout) findViewById(R.id.main_activity)).addView(corner);

            }
        });
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
}
