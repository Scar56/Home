package shaun.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class AppDrawer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_drawer);
        ((RecyclerView)findViewById(R.id.appsList)).setAdapter(new RAdapter(this));
        ((RecyclerView)findViewById(R.id.appsList)).setLayoutManager(new LinearLayoutManager(this));

    }
}