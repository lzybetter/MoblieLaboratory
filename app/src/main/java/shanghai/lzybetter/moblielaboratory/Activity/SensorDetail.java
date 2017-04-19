package shanghai.lzybetter.moblielaboratory.Activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.widget.TextView;

import java.util.List;

import shanghai.lzybetter.moblielaboratory.R;

public class SensorDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_detail);

        Intent intent = getIntent();
        int type = intent.getIntExtra("sensorType",0);
        String name = intent.getStringExtra("sensorName");

        Toolbar title = (Toolbar)findViewById(R.id.sensorDetail);
        title.setTitle(name + "详细信息");
        setSupportActionBar(title);

        TextView sensorName = (TextView)findViewById(R.id.name);
        TextView sensorVendor = (TextView)findViewById(R.id.vendor);
        TextView sensorPower = (TextView)findViewById(R.id.power);
        TextView sensorMax = (TextView)findViewById(R.id.maximumRange);
        TextView maxUnit = (TextView)findViewById(R.id.max_Unit);
        TextView sensorRes = (TextView)findViewById(R.id.resolution);
        TextView resUnit = (TextView)findViewById(R.id.res_unit);
        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> list = sensorManager.getSensorList(type);

        StringBuilder sensorDetail = new StringBuilder();
        SpannableString unit = null;
        switch(type){
            case Sensor.TYPE_ACCELEROMETER:
                unit = new SpannableString("米/秒2(m/s2)");
                unit.setSpan(new SuperscriptSpan(),3,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),3,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),8,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),8,9,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                unit = new SpannableString("微特斯拉(uT)");
                break;
            case Sensor.TYPE_GYROSCOPE:
                unit = new SpannableString("弧度/秒(rad/s)");
                break;
            case Sensor.TYPE_PROXIMITY:
                unit = new SpannableString("厘米(cm)");
                break;
            case Sensor.TYPE_LIGHT:
                unit = new SpannableString("勒克斯(lx)");
                break;
            case Sensor.TYPE_GRAVITY:
                unit = new SpannableString("米/秒2(m/s2)");
                unit.setSpan(new SuperscriptSpan(),3,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),3,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),8,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),8,9,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                unit = new SpannableString("米/秒2(m/s2)");
                unit.setSpan(new SuperscriptSpan(),3,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),3,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),8,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),8,9,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                unit = new SpannableString(" ");
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                unit = new SpannableString("%");
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                unit = new SpannableString("℃");
                break;
            case Sensor.TYPE_PRESSURE:
                unit = new SpannableString("百帕斯卡(hPa)");
                break;
            default:
                unit = null;
                break;
        }
        maxUnit.setText(unit);
        resUnit.setText(unit);
        for (Sensor sensor : list){
            sensorName.setText(sensor.getName());
            sensorVendor.setText(sensor.getVendor());
            sensorPower.setText(sensor.getPower()+"");
            sensorMax.setText(sensor.getMaximumRange()+"");
            sensorRes.setText(sensor.getResolution()+"");
        }

    }
}
