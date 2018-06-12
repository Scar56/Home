package shaun.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Homescreen extends Fragment implements View.OnClickListener{
    public Homescreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homescreen, container, false);
        GridView grid= v.findViewById(R.id.homeGrid);
        grid.setAdapter(new homeGridAdapter(this.getContext()));
//        ImageView Icon = v.findViewById(R.id.icon);
//        Icon.setImageDrawable(MainActivity.getActivityIcon(this.getContext(), "com.android.chrome", "com.google.android.apps.chrome.Main"));
//        TextView text = v.findViewById(R.id.title);
//        text.setText("Chrome");
        return v;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.icon:
                Intent launchIntent = this.getContext().getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                startActivity(launchIntent);
                break;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);}




}
