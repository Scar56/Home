package shaun.home;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RAdapter extends RecyclerView.Adapter<RAdapter.ViewHolder> {
    public ArrayList<AppInfo> appsList;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView img;


        //This is the subclass ViewHolder which simply
        //'holds the views' for us to show on each row
        public ViewHolder(View itemView) {
            super(itemView);

            //Finds the views from our row.xml
            textView = (TextView) itemView.findViewById(R.id.textView);
            img = (ImageView) itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick (View v) {
            int pos = getAdapterPosition();
            Context context = v.getContext();

            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(appsList.get(pos).packageName.toString());
            context.startActivity(launchIntent);

        }
    }
    public void addItem(AppInfo a){
        appsList.add(a);
    }


    public RAdapter(Context c) {

        //This is where we build our list of app details, using the app
        //object we created to store the label, package name and icon

        appsList = new ArrayList<AppInfo>();
//
//        Intent i = new Intent(Intent.ACTION_MAIN, null);
//        i.addCategory(Intent.CATEGORY_LAUNCHER);
//
//        List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
//        for(ResolveInfo ri:allApps) {
//            AppInfo app = new AppInfo();
//            app.label = ri.loadLabel(pm);
//            app.packageName = ri.activityInfo.packageName;
//            app.icon = ri.activityInfo.loadIcon(pm);
//            appsList.add(app);
//        }
//        Collections.sort(appsList,new Comparator<AppInfo>(){
//            public int compare(AppInfo one, AppInfo two) {
//                return one.label.toString().compareTo(two.label.toString());
//            }});

    }

    @Override
    public void onBindViewHolder(RAdapter.ViewHolder viewHolder, int i) {

        //Here we use the information in the list we created to define the views

        String appLabel = appsList.get(i).label.toString();
        String appPackage = appsList.get(i).packageName.toString();
        Drawable appIcon = appsList.get(i).icon;

        TextView textView = viewHolder.textView;
        textView.setText(appLabel);
        ImageView imageView = viewHolder.img;
        imageView.setImageDrawable(appIcon);
        textView.setTag(i);
    }


    @Override
    public int getItemCount() {

        //This method needs to be overridden so that Androids knows how many items
        //will be making it into the list

        return appsList.size();
    }


    @Override
    public RAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        //This is what adds the code we've written in here to our target view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.row, parent, false);
        view.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view){

                ClipData.Item item = new ClipData.Item((CharSequence)appsList.get((int)view.findViewById(R.id.textView).getTag()).packageName);
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                view.startDragAndDrop(new ClipData(((TextView)view.findViewById(R.id.textView)).getText(),mimeTypes, item), new View.DragShadowBuilder(view.findViewById(R.id.img)),null,0);
                Context context = view.getContext();
                while (context instanceof ContextWrapper) {
                    if (context instanceof AppDrawer) {
                        break;
                    }
                    context = ((ContextWrapper)context).getBaseContext();
                }
                ((AppDrawer) context).inflateDrop();
                return true;
            }
        });
//        view.setOnDragListener(new View.OnDragListener(){
//            public boolean onDrag(View v, DragEvent event) {
//                switch(event.getAction()) {
//                    case DragEvent.ACTION_DRAG_STARTED:
//                        break;
//
//                    case DragEvent.ACTION_DRAG_ENTERED:
//                        int x_cord = (int) event.getX();
//                        int y_cord = (int) event.getY();
//                        break;
//
//                    case DragEvent.ACTION_DRAG_EXITED :
//                        x_cord = (int) event.getX();
//                        y_cord = (int) event.getY();
//    //                    layoutParams.leftMargin = x_cord;
//    //                    layoutParams.topMargin = y_cord;
//    //                    v.setLayoutParams(layoutParams);
//                        break;
//
//                    case DragEvent.ACTION_DRAG_LOCATION  :
//                        x_cord = (int) event.getX();
//                        y_cord = (int) event.getY();
//                        break;
//
//                    case DragEvent.ACTION_DRAG_ENDED   :
//                        // Do nothing
//                        break;
//
//                    case DragEvent.ACTION_DROP:
//                        // Do nothing
//                        break;
//                    default: break;
//                }
//                return true;
//            }
//        });

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void updateStuff() {
        notifyItemInserted(getItemCount()-1);

    }
}