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

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.RelativeLayout;

public class CircularLayout extends RelativeLayout{
    public CircularLayout(Context context) {
        super(context);
    }

    public CircularLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CircularLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private Path mClipPath= new Path();
//    @Override
//    public void dispatchDraw(Canvas canvas){
//        mClipPath.reset();
//        float radius = Math.min((float)getMeasuredWidth() / 2f, (float)getMeasuredHeight() / 2f) + 5;
//        mClipPath.addCircle((float)getMeasuredWidth() / 2f, (float)getMeasuredHeight() / 2f, radius, Path.Direction.CCW);
//        canvas.clipPath(mClipPath);
//        super.dispatchDraw(canvas);
//    }
private Adapter adapter;
    private DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            reloadChildViews();
        }
    };

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)

    public void setAdapter(Adapter adapter) {
        if (this.adapter == adapter) return;
        this.adapter = adapter;
        if (adapter != null) adapter.registerDataSetObserver(dataSetObserver);
        reloadChildViews();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (adapter != null) adapter.unregisterDataSetObserver(dataSetObserver);
    }

    private void reloadChildViews() {
        removeAllViews();

        if (adapter == null) return;

        int count = adapter.getCount();
        for (int position = 0; position < count; ++position) {
            View v = adapter.getView(position, null, this);
            if (v != null) addView(v);
        }

        requestLayout();
    }
}
