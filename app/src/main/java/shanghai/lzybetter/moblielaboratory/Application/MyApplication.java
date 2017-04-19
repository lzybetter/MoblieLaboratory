package shanghai.lzybetter.moblielaboratory.Application;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

/**
 * Created by lzybetter on 2017/4/1.
 */

public class MyApplication extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePalApplication.initialize(context);
    }

}
