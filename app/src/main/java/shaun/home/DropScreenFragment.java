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
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * A placeholder fragment containing a simple view.
 */
public class DropScreenFragment extends Fragment {
    public enum corner {UL,UR,LL,LR}
    public DropScreenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout drop = (RelativeLayout)inflater.inflate(R.layout.fragment_drop_screen, container, false);
        for (corner x:corner.values()
             ) {
            CircularLayout crnr = (CircularLayout) inflater.inflate(R.layout.corner, null);
            crnr.setAdapter(new DropScreenAdapter(inflater.getContext(),x.toString()));
            RelativeLayout.LayoutParams cLayParam = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            switch (x.toString()){
                case "UL":
                    cLayParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    cLayParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    break;
                case "UR":
                    cLayParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    cLayParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    break;
                case "LL":
                    cLayParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    cLayParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    break;
                case "LR":
                    cLayParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    cLayParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    break;
            }
            crnr.setLayoutParams(cLayParam);
            drop.addView(crnr);
        }
        return drop;
    }
}
