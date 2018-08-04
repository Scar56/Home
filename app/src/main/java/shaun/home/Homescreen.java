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

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Homescreen extends Fragment implements View.OnClickListener{
    private int pos;
    //TODO
    private int[] posA;

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
        grid.setOnTouchListener(new touchListener());
        return v;
    }

    public void setPageNum(int pos){
        this.pos=pos;
    }

    public void setPageNum(int[] pos){
        this.posA=pos;
    }

    int x;
    int y;


    private class touchListener implements View.OnTouchListener {
        public touchListener(){
            super();
        }
        private GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                new AdminReceiver(getContext()).lock();
                return super.onDoubleTap(e);
            }
            // implement here other callback methods like onFling, onScroll as necessary
        });
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gestureDetector.onTouchEvent(event);

            // save the X,Y coordinates
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                x=(int) event.getX();
                y=(int) event.getY();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick();
                if(Math.abs(x-(int) event.getX())<50&&Math.abs(y-(int) event.getY())<50)
                    WallpaperManager.getInstance(v.getContext()).sendWallpaperCommand(v.getWindowToken(), WallpaperManager.COMMAND_TAP,x,y+50,0,new Bundle());
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
