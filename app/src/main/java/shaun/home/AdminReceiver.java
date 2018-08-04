package shaun.home;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class AdminReceiver extends DeviceAdminReceiver {
    public static final String ACTION_DISABLED = "device_admin_action_disabled";
    public static final String ACTION_ENABLED = "device_admin_action_enabled";

    private Context c;
    public AdminReceiver(){
        super();
    }

    public AdminReceiver(Context c){
        super();

        this.c = c;
    }
    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        LocalBroadcastManager.getInstance(context).sendBroadcast(
                new Intent(ACTION_DISABLED));
    }
    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        LocalBroadcastManager.getInstance(context).sendBroadcast(
                new Intent(ACTION_ENABLED));
    }
    public void lock() {

        PowerManager pm = (PowerManager) c.getSystemService(Context.POWER_SERVICE);
        if (pm.isInteractive()) {
            DevicePolicyManager policy = (DevicePolicyManager)
                    c.getSystemService(Context.DEVICE_POLICY_SERVICE);

            try {
                policy.lockNow();
            } catch (SecurityException ex) {
                Toast.makeText(
                        c,
                        "must enable device administrator",
                        Toast.LENGTH_LONG).show();
                ComponentName admin = new ComponentName(c, AdminReceiver.class);
                Intent intent = new Intent(
                        DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).putExtra(
                        DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin);
                c.startActivity(intent);
            }
        }
    }
}