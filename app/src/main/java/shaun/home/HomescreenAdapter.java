package shaun.home;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomescreenAdapter extends FragmentStatePagerAdapter {
    private int NUM_PAGES=3;
    private Context c;
    public HomescreenAdapter(Context c, FragmentManager fm) {
        super(fm);
        this.c = c;
    }

    @Override
    public Fragment getItem(int position) {
        Homescreen h = new Homescreen();
        h.setPageNum(position);
        return h;
    }

    @Override
    public int getCount() {
        return c.getSharedPreferences("screens", Context.MODE_PRIVATE).getInt("horiz",1);

    }

}