package shanghai.lzybetter.moblielaboratory.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SingleSensorService extends Service {
    public SingleSensorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
