package shaun.home;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class homeGridAdapter extends BaseAdapter {
    private LayoutInflater homeInf;
    public homeGridAdapter(Context c){
        homeInf = LayoutInflater.from(c);
    }
    @Override
    public int getCount(){
        return 3;
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
        ConstraintLayout homeLay = (ConstraintLayout)homeInf.inflate
                (R.layout.home_app, parent, false);

        ImageView Icon = homeLay.findViewById(R.id.ico);
        Icon.setImageDrawable(MainActivity.getActivityIcon(homeInf.getContext(), "com.android.chrome", "com.google.android.apps.chrome.Main"));
        TextView text = homeLay.findViewById(R.id.tie);
        text.setText("Chrome");
        text.setTextColor(0xFFFFFF);
        //set position as tag
        homeLay.setTag(position);
        return homeLay;
    }


}
