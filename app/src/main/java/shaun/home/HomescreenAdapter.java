package shaun.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class HomescreenAdapter extends FragmentStatePagerAdapter{
    private int NUM_PAGES = 3;
    private Context c;

    public HomescreenAdapter(Context c, FragmentManager fm) {
        super(fm);
        this.c = c;
    }


    //fulfill extension requirements
    @Override
    public Fragment getItem(int position) {
        Homescreen h = new Homescreen();
        h.setPageNum(position);
        return h;
    }

    public Fragment getItem(int position[]) {
        Homescreen h = new Homescreen();
        h.setPageNum(position);
        return h;
    }

    @Override
    public int getCount() {
        return c.getSharedPreferences("screens", Context.MODE_PRIVATE).getInt("horiz", 1);

    }

}