package shaun.home;
/*
    adapter for corner menus of home screen
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
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CornerAdapter  extends BaseAdapter {
    private LayoutInflater homeInf;
    protected String name;
    //used for code reusability of corners
    protected int ymod;
    protected int xmod;
    public CornerAdapter(Context c, String name){
        homeInf = LayoutInflater.from(c);
        this.name = name;
        switch (name){
            case "UL":
                ymod = 1;
                xmod= -1;
                break;
            case "UR":
                ymod = 1;
                xmod= 1;
                break;
            case "LL":
                ymod = -1;
                xmod= -1;
                break;
            case "LR":
                ymod = -1;
                xmod= 1;
                break;
        }
    }
    @Override
    public int getCount(){
//        return homeInf.getContext().getSharedPreferences("corners", Context.MODE_PRIVATE).getInt("LR", 5);
    return 5;
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
        //set position as tag
        homeLay.setTag(position);
        homeLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                v.getTag();
                RelativeLayout main = ((RelativeLayout) v.getRootView().findViewById(R.id.main_activity));
                main.removeView(v.getRootView().findViewById(R.id.corner));
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                context.startActivity(launchIntent);
            }
        });
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) homeLay.getLayoutParams();
        params.setMargins(0, Math.max((getCount()-position-1)*200*-ymod,(position)*200*ymod),0,0);
        params.setMarginStart(Math.max((position)*200*xmod,(getCount()-position-1)*200*-xmod));
        homeLay.setLayoutParams(params);
        return homeLay;
    }


}
