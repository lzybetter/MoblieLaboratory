package shanghai.lzybetter.moblielaboratory.Action;

import android.hardware.SensorEvent;
import android.widget.TextView;

/**
 * Created by lzybetter on 2017/4/10.
 */

public class SensorListener {

    public static void acc(SensorEvent event, TextView dataShow){
        float x_value = event.values[0];
        float y_value = event.values[1];
        float z_value = event.values[2];
        StringBuilder sb = new StringBuilder();
        sb.append("x轴加速度为： ").append(x_value).append("\n");
        sb.append("y轴加速度为： ").append(y_value).append("\n");
        sb.append("z轴加速度为： ").append(z_value);
        dataShow.setText(sb);
    }

    public static void mac(SensorEvent event, TextView dataShow){
        float x_value = event.values[0];
        float y_value = event.values[1];
        float z_value = event.values[2];
        StringBuilder sb = new StringBuilder();
        sb.append("x轴磁通量为： ").append(x_value).append("\n");
        sb.append("y轴磁通量为： ").append(y_value).append("\n");
        sb.append("z轴磁通量为： ").append(z_value);
        dataShow.setText(sb);
    }

    public static void gyr(SensorEvent event,TextView dataShow){
        float x_value = event.values[0];
        float y_value = event.values[1];
        float z_value = event.values[2];
        StringBuilder sb = new StringBuilder();
        sb.append("x轴角加速度为： ").append(x_value).append("\n");
        sb.append("y轴角加速度为： ").append(y_value).append("\n");
        sb.append("z轴角加速度为： ").append(z_value);
        dataShow.setText(sb);
    }

    public static void pro(SensorEvent event, TextView dataShow){
        float value = event.values[0];
        StringBuilder sb = new StringBuilder();
        sb.append("距离： ").append(value);
        dataShow.setText(sb);
    }

    public  static void lig(SensorEvent event, TextView dataShow){
        float value = event.values[0];
        StringBuilder sb = new StringBuilder();
        sb.append("光强度： ").append(value);
        dataShow.setText(sb);
    }

    public static void gra(SensorEvent event, TextView dataShow){
        float x_value = event.values[0];
        float y_value = event.values[1];
        float z_value = event.values[2];
        StringBuilder sb = new StringBuilder();
        sb.append("x轴重力加速度： ").append(x_value).append("\n");
        sb.append("y轴重力加速度： ").append(y_value).append("\n");
        sb.append("z轴重力加速度： ").append(z_value);
        dataShow.setText(sb);
    }

    public static void lin(SensorEvent event, TextView dataShow){
        float x_value = event.values[0];
        float y_value = event.values[1];
        float z_value = event.values[2];
        StringBuilder sb = new StringBuilder();
        sb.append("x轴线加速度为： ").append(x_value).append("\n");
        sb.append("y轴线加速度为： ").append(y_value).append("\n");
        sb.append("z轴线加速度为： ").append(z_value);
        dataShow.setText(sb);
    }

    public static void rot(SensorEvent event, TextView dataShow){
        float x_value = event.values[0];
        float y_value = event.values[1];
        float z_value = event.values[2];
        StringBuilder sb = new StringBuilder();
        sb.append("x轴旋转矢量为： ").append(x_value).append("\n");
        sb.append("y轴旋转矢量为： ").append(y_value).append("\n");
        sb.append("z轴旋转矢量为： ").append(z_value);
        dataShow.setText(sb);
    }

    public static void rel(SensorEvent event, TextView dataShow){
        float value = event.values[0];
        StringBuilder sb = new StringBuilder();
        sb.append("相对湿度为： ").append(value);
        dataShow.setText(sb);
    }

    public static void tem(SensorEvent event, TextView dataShow){
        float value = event.values[0];
        StringBuilder sb = new StringBuilder();
        sb.append("当前环境温度为： ").append(value);
        dataShow.setText(sb);
    }

    public static void pre(SensorEvent event, TextView dataShow){
        float value = event.values[0];
        StringBuilder sb = new StringBuilder();
        sb.append("当前环境压力为： ").append(value);
        dataShow.setText(sb);
    }
}
