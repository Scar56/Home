package shaun.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

public class AppDrawer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_drawer);
        ((RecyclerView)findViewById(R.id.appsList)).setAdapter(new RAdapter(this));
        ((RecyclerView)findViewById(R.id.appsList)).setLayoutManager(new LinearLayoutManager(this));

    }
}