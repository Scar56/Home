package shaun.home;
/*
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