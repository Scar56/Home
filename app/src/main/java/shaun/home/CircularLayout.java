package shaun.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
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
}
