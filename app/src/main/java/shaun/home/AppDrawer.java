package shaun.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppDrawer extends AppCompatActivity {

    PackageManager pm;
    RAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pm = getPackageManager();
        adapter = new RAdapter(this);
        new myThread().execute();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_drawer);
        ((RecyclerView)findViewById(R.id.appsList)).setAdapter(adapter);
        ((RecyclerView)findViewById(R.id.appsList)).setLayoutManager(new LinearLayoutManager(this));

    }

    public class myThread extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... Params) {

            Intent i = new Intent(Intent.ACTION_MAIN, null);
            i.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
            for(ResolveInfo ri:allApps) {
                AppInfo app = new AppInfo();
                app.label = ri.loadLabel(pm);
                app.packageName = ri.activityInfo.packageName;
                app.icon = ri.activityInfo.loadIcon(pm);
                adapter.appsList.add(app);
            }
            Collections.sort(adapter.appsList,new Comparator<AppInfo>(){
                public int compare(AppInfo one, AppInfo two) {
                    return one.label.toString().compareTo(two.label.toString());
                }});
            return "Success";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            adapter.updateStuff();
        }

    }
}