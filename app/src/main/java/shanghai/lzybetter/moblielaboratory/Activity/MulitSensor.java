package shanghai.lzybetter.moblielaboratory.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaScannerConnection;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import shanghai.lzybetter.moblielaboratory.Action.SQLHelper;
import shanghai.lzybetter.moblielaboratory.Action.SensorListener;
import shanghai.lzybetter.moblielaboratory.Class.SensorList;
import shanghai.lzybetter.moblielaboratory.R;

import static android.hardware.SensorManager.SENSOR_DELAY_GAME;
import static android.hardware.SensorManager.SENSOR_DELAY_NORMAL;

public class MulitSensor extends AppCompatActivity{

    private LinearLayout linearLayout;
    private List<String> selectedSensor = new ArrayList<>();
    private List<TextView> sensorShow = new ArrayList<>();
    private List<Integer> selectedSensorType = new ArrayList<>();
    private String name;
    private boolean isWriting;
    private long startRecordTime;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulit_sensor);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        Toolbar toolbar = (Toolbar)findViewById(R.id.mulitSensorName);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);

        linearLayout = (LinearLayout)findViewById(R.id.mulitsensor);

        isWriting = false;
        Calendar c = Calendar.getInstance();
        startRecordTime = c.getTimeInMillis();

        initView();

        initSensor();
    }

    private void initSensor() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        for(int type : selectedSensorType){
            Sensor sensor = sensorManager.getDefaultSensor(type);
            sensorManager.registerListener(listener,sensor,SENSOR_DELAY_NORMAL);
        }
    }

    private void initView() {
        List<SensorList> sensors = DataSupport.findAll(SensorList.class);
        SharedPreferences sp = getSharedPreferences(name, MODE_PRIVATE);
        for (SensorList sensor : sensors) {
            if (sp.getBoolean(sensor.getSensorName(), false)) {
                selectedSensor.add(sensor.getSensorName());
                selectedSensorType.add(sensor.getSensorType());
                TextView textView = new TextView(this);
                StringBuilder sb = new StringBuilder();
                sb.append(sensor.getSensorName()+"：\n");
                switch (sensor.getSensorName()){
                    case "加速度传感器":
                        sb.append("x轴加速度：0\ny轴加速度：0\nz轴加速度：0\n");
                        break;
                    case "磁力传感器":
                        sb.append("x轴磁通量：0\ny轴磁通量：0\nz轴磁通量：0\n");
                        break;
                    case "陀螺仪":
                        sb.append("x轴角加速度：0\ny轴角加速度：0\nz轴角加速度：0\n");
                        break;
                    case "接近传感器（可以用来测距）":
                        sb.append("距离：0\n");
                        break;
                    case "光强传感器":
                        sb.append("光强度：0\n");
                        break;
                    case "重力传感器":
                        sb.append("x轴加速度：0\ny轴加速度：0\nz轴加速度：0\n");
                        break;
                    case "线加速度传感器":
                        sb.append("x轴加速度：0\ny轴加速度：0\nz轴加速度：0\n");
                        break;
                    case "旋转矢量传感器":
                        sb.append("x轴旋转矢量：0\ny轴旋转矢量：0\nz轴旋转矢量：0\n");
                        break;
                    case "相对湿度传感器":
                        sb.append("相对湿度为：0%\n");
                        break;
                    case "环境温度传感器":
                        sb.append("当前环境温度为：0\n");
                        break;
                    case "压力传感器":
                        sb.append("当前环境压力为：0\n");
                        break;
                    default:
                        break;
                }
                textView.setText(sb);
                sensorShow.add(textView);
                linearLayout.addView(textView);
            }
        }
    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            int position = selectedSensorType.indexOf(event.sensor.getType());
            String name = selectedSensor.get(position);
            TextView dataShow = sensorShow.get(position);
            numberShow(event,name,dataShow);
            Calendar c = Calendar.getInstance();
            long nowTime = c.getTimeInMillis();
            long recordTime = nowTime - startRecordTime;
            writeToFile(event,name,recordTime);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void writeToFile(final SensorEvent event, final String sensorName,final long recordTime) {
        String db_name = name + "_" + startRecordTime + ".db";
        db = SQLHelper.getInstance(this,db_name);
        String table_name = null;
        boolean isThree = false;
        switch (sensorName){
            case "加速度传感器":
                table_name = "ACCELEROMETER";
                isThree = true;
                break;
            case "磁力传感器":
                table_name = "MAGNETIC_FIELD";
                isThree = true;
                break;
            case "陀螺仪":
                table_name = "GYROSCOPE";
                isThree = true;
                break;
            case "接近传感器（可以用来测距）":
                table_name = "PROXIMITY";
                isThree = false;
                break;
            case "光强传感器":
                table_name = "LIGHT";
                isThree = false;
                break;
            case "重力传感器":
                table_name = "GRAVITY";
                isThree = true;
                break;
            case "线加速度传感器":
                table_name = "LINEAR_ACCELERATION";
                isThree = true;
                break;
            case "旋转矢量传感器":
                table_name = "ROTATION_VECTOR";
                isThree = true;
                break;
            case "相对湿度传感器":
                table_name = "RELATIVE_HUMIDITY";
                isThree = false;
                break;
            case "环境温度传感器":
                table_name = "TEMPERATURE";
                isThree = false;
                break;
            case "压力传感器":
                table_name = "PRESSURE";
                isThree = false;
                break;
            default:
                break;
        }
        try{
            String createTable = null;
            if(isThree){
                createTable = "create table if not exists " + table_name
                        +  "(id integer primary key autoincrement, "
                        + "recordTime Long, "
                        + "x_value Integer, "
                        + "y_value Integer, "
                        + "z_value Integer)";
            }else {
                createTable = "create table if not exists " + table_name
                        +  "(id integer primary key autoincrement, "
                        + "recordTime Log, "
                        + "value Integer)";
            }
            db.execSQL(createTable);
            ContentValues value = new ContentValues();
            value.put("recordTime",recordTime);
            if(isThree){
                value.put("x_value",event.values[0]);
                value.put("y_value",event.values[1]);
                value.put("z_value",event.values[2]);
            }else {
                value.put("value",event.values[0]);
            }
            db.insert(table_name,null,value);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            db.close();
        }

    }

    private void numberShow(SensorEvent event,String sensorName,TextView dataShow){
        switch (sensorName){
            case "加速度传感器":
                SensorListener.acc(event,dataShow);
                break;
            case "磁力传感器":
                SensorListener.mac(event,dataShow);
                break;
            case "陀螺仪":
                SensorListener.gyr(event,dataShow);
                break;
            case "接近传感器（可以用来测距）":
                SensorListener.pro(event,dataShow);
                break;
            case "光强传感器":
                SensorListener.lig(event,dataShow);
                break;
            case "重力传感器":
                SensorListener.gra(event,dataShow);
                break;
            case "线加速度传感器":
                SensorListener.lin(event,dataShow);
                break;
            case "旋转矢量传感器":
                SensorListener.rot(event,dataShow);
                break;
            case "相对湿度传感器":
                SensorListener.rel(event,dataShow);
                break;
            case "环境温度传感器":
                SensorListener.tem(event,dataShow);
                break;
            case "压力传感器":
                SensorListener.pre(event,dataShow);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,SavedExperimentShow.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isWriting){

        }else{

            SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
            sensorManager.unregisterListener(listener);
        }
    }
}
