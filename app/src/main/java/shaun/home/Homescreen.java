package shaun.home;


import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

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
        grid.setOnTouchListener(touchListener);
        return v;
    }
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            // save the X,Y coordinates
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.performClick();
                WallpaperManager.getInstance(v.getContext()).sendWallpaperCommand(v.getWindowToken(), WallpaperManager.COMMAND_TAP,(int)event.getX(),(int)event.getY(),0,new Bundle());
            }

            // let the touch event pass on to whoever needs it
            return false;
        }
    };
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
