package shanghai.lzybetter.moblielaboratory.Class;

import org.litepal.crud.DataSupport;

/**
 * Created by lzybetter on 2017/4/7.
 */

public class SensorList extends DataSupport{

    private int sensorType;
    private String sensorName;

    public int getSensorType() {
        return sensorType;
    }

    public void setSensorType(int sensorType) {
        this.sensorType = sensorType;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
}
