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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class homeGridAdapter extends BaseAdapter {
    private LayoutInflater homeInf;
    public homeGridAdapter(Context c){
        homeInf = LayoutInflater.from(c);
    }
    @Override
    public int getCount(){
        return 0;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout homeLay = (RelativeLayout)homeInf.inflate
                (R.layout.home_app, parent, false);

        ImageView Icon = homeLay.findViewById(R.id.icon);
        Icon.setImageDrawable(MainActivity.getActivityIcon(homeInf.getContext(), "com.android.chrome", "com.google.android.apps.chrome.Main"));
        TextView text = homeLay.findViewById(R.id.title);
        text.setText("Chrome");
//        text.setTextColor(0xFFFFFF);
        //set position as tag
        homeLay.setTag(position);
        return homeLay;
    }


}
