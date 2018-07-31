package shaun.home;

import android.content.ClipData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class DropScreenAdapter extends CornerAdapter{


    public DropScreenAdapter(Context c, String name) {
        super(c, name);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Context c = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(c);
        RelativeLayout homeLay = (RelativeLayout)inflater.inflate
                (R.layout.home_app, parent, false);
        final String _package = c.getSharedPreferences(name, Context.MODE_PRIVATE).getString(Integer.toString(position), "com.android.chrome");

        PackageManager pm = c.getPackageManager();
        ImageView Icon = homeLay.findViewById(R.id.icon);
        TextView text = homeLay.findViewById(R.id.title);
        try {

            Drawable icon = c.getPackageManager().getApplicationIcon(_package);
            Icon.setImageDrawable(icon);

            ApplicationInfo app = pm.getApplicationInfo(_package, 0);
            text.setText(pm.getApplicationLabel(app).toString());

        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //set position as tag
        homeLay.setTag(position);
//        homeLay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = v.getContext();
//                v.getTag();
//                RelativeLayout main = ((RelativeLayout) v.getRootView().findViewById(R.id.main_activity));
//                main.removeView(v.getRootView().findViewById(R.id.corner));
//                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.android.chrome");
//                context.startActivity(launchIntent);
//            }
//        });
        homeLay.setOnDragListener(new View.OnDragListener(){
            public boolean onDrag(View v, DragEvent event) {

                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:

                        // Gets the item containing the dragged data
                        ClipData.Item item = event.getClipData().getItemAt(0);

                        // Gets the text data from the item.
//                    dragData = item.getText();

                        v.getContext().getSharedPreferences(name, MODE_PRIVATE).edit().putString(Integer.toString(position), (String) item.getText()).apply();
                        // Displays a message containing the dragged data.
                        Toast.makeText(v.getContext(), "heyyy", Toast.LENGTH_SHORT).show();

                        // Invalidates the view to force a redraw
                        v.invalidate();

                        //kill drawer
                        Context context = v.getContext();
                        while (context instanceof ContextWrapper) {
                            if (context instanceof AppDrawer) {
                                break;
                            }
                            context = ((ContextWrapper)context).getBaseContext();
                        }
                        ((AppDrawer) context).finish();

                        // Returns true. DragEvent.getResult() will return true.
                        return true;
                    default:
                        return true;
                }
            }

        });
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) homeLay.getLayoutParams();
        params.setMargins(0, Math.max((getCount()-position-1)*200*-ymod,(position)*200*ymod),0,0);
        params.setMarginStart(Math.max((position)*150*xmod,(getCount()-position-1)*150*-xmod));
        homeLay.setLayoutParams(params);
        return homeLay;
    }
}
