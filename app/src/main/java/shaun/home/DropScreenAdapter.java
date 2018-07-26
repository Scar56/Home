package shaun.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DropScreenAdapter extends CornerAdapter{


    public DropScreenAdapter(Context c, String name) {
        super(c, name);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RelativeLayout homeLay = (RelativeLayout)inflater.inflate
                (R.layout.home_app, parent, false);

        ImageView Icon = homeLay.findViewById(R.id.icon);
        Icon.setImageDrawable(MainActivity.getActivityIcon(inflater.getContext(), "com.android.chrome", "com.google.android.apps.chrome.Main"));
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
        params.setMarginStart(Math.max((position)*150*xmod,(getCount()-position-1)*150*-xmod));
        homeLay.setLayoutParams(params);
        return homeLay;
    }
}
