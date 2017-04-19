package shanghai.lzybetter.moblielaboratory.Class;

/**
 * Created by lzybetter on 2017/4/1.
 */

public class AvailableSensor {

    private int sensorType;
    private String sensorName;

    public AvailableSensor(int sensorType, String sensorName) {
        this.sensorType = sensorType;
        this.sensorName = sensorName;
    }

    public int getSensorType() {
        return sensorType;
    }

    public String getSensorName() {
        return sensorName;
    }
}
