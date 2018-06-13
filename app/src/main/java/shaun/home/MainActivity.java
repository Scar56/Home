package shaun.home;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.service.wallpaper.WallpaperService;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewPager mPager = (ViewPager) findViewById(R.id.homescreenPager);
        FragmentStatePagerAdapter mPagerAdapter = new HomescreenAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        WallpaperManager wm = WallpaperManager.getInstance(this);
        WallpaperManager.getInstance(this).setWallpaperOffsetSteps(.5f,-1f);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int p1, float p2, int p3) {
                WallpaperManager.getInstance(getBaseContext()).setWallpaperOffsets(mPager.getWindowToken(), (p1+p2) / (2), 0);
            }

            @Override
            public void onPageSelected(int p1) {
//                WallpaperManager.getInstance(getBaseContext()).setWallpaperOffsets(mPager.getWindowToken(),(float)p1/(2), 0);
            }

            @Override
            public void onPageScrollStateChanged(int p1) {

            }});
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
