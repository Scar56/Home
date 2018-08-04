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
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

        //TODO move this to a new fragment
        ((RecyclerView)findViewById(R.id.appsList)).setAdapter(adapter);
        ((RecyclerView)findViewById(R.id.appsList)).setLayoutManager(new LinearLayoutManager(this));

    }

    public class myThread extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... Params) {

            PackageManager pm = getPackageManager();

            Intent i = new Intent(Intent.ACTION_MAIN, null);
            i.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
            for(ResolveInfo ri:allApps) {
                AppInfo app = new AppInfo();
                app.label = ri.loadLabel(pm);
                app.packageName = ri.activityInfo.packageName;
                app.icon = ri.activityInfo.loadIcon(pm);
                adapter.addItem(app);
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

    /**
     * inflate the view to drop apps into
     */
    public void inflateDrop(){
        //add a container for the fragment
        ((ConstraintLayout)findViewById(R.id.appDrawer)).addView(getLayoutInflater().inflate(R.layout.content_drop_screen, null));
        Fragment dropFragment = new DropScreenFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.layoutContainer,dropFragment, null)
        .addToBackStack(null)
        .commit();
    }
}