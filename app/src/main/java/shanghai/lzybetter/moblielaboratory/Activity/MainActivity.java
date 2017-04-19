package shanghai.lzybetter.moblielaboratory.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import shanghai.lzybetter.moblielaboratory.Adapter.AvailableSensorAdapter;
import shanghai.lzybetter.moblielaboratory.Class.AvailableSensor;
import shanghai.lzybetter.moblielaboratory.Class.SensorList;
import shanghai.lzybetter.moblielaboratory.R;

public class MainActivity extends AppCompatActivity {

    public static final String SAVE_FILE = "setting_file";
    public static final String ISSHOWEDFIRST = "isShowFirst";

    private RecyclerView availableSensorList;
    private List<AvailableSensor> availableSensors= new ArrayList<>();
    private AvailableSensorAdapter adapter;
    private CheckBox notShowAgain;
    private Button confirmButton;
    private boolean isSaved;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences(SAVE_FILE,MODE_PRIVATE);
        boolean isShowed = pref.getBoolean(ISSHOWEDFIRST,true);
        if(!isShowed){
            Intent intentNotShowed = new Intent(this,SavedExperimentShow.class);
            startActivity(intentNotShowed);
            finish();
        }
        setContentView(R.layout.activity_main);

        Toolbar title = (Toolbar)findViewById(R.id.availableSensorTitle);
        title.setTitle("可用的传感器列表");
        setSupportActionBar(title);

        availableSensorList = (RecyclerView)findViewById(R.id.availableSensorList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        availableSensorList.setLayoutManager(layoutManager);
        notShowAgain = (CheckBox)findViewById(R.id.notShowAgain);
        confirmButton = (Button)findViewById(R.id.confirmButton);

        notShowAgain.setOnClickListener(new MainButtonListener());
        confirmButton.setOnClickListener(new MainButtonListener());

        getAvailableSensorList();
    }

    private void getAvailableSensorList() {
        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> list = sensorManager.getSensorList(Sensor.TYPE_ALL);
        boolean isSensorSaved = false;
        for(Sensor sensor : list){
            int type = sensor.getType();
            String name = null;
            switch(type){
                case Sensor.TYPE_ACCELEROMETER:
                    name = "加速度传感器";
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    name = "磁力传感器";
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    name = "陀螺仪";
                    break;
                case Sensor.TYPE_PROXIMITY:
                    name = "接近传感器（可以用来测距）";
                    break;
                case Sensor.TYPE_LIGHT:
                    name = "光强传感器";
                    break;
                case Sensor.TYPE_GRAVITY:
                    name = "重力传感器";
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    name = "线加速度传感器";
                    break;
                case Sensor.TYPE_ROTATION_VECTOR:
                    name = "旋转矢量传感器";
                    break;
                case Sensor.TYPE_RELATIVE_HUMIDITY:
                    name = "相对湿度传感器";
                    break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    name = "环境温度传感器";
                    break;
                case Sensor.TYPE_PRESSURE:
                    name = "压力传感器";
                    break;
                default:
                    name = null;
                    break;
            }
            if(name != null){
                AvailableSensor availableSensor = new AvailableSensor(type,name);
                availableSensors.add(availableSensor);
                if(!pref.getBoolean("isSensorSaved",false)){
                    SensorList sensors = new SensorList();
                    sensors.setSensorType(type);
                    sensors.setSensorName(name);
                    sensors.save();
                    isSensorSaved=true;
                }
            }

            adapter = new AvailableSensorAdapter(availableSensors,this);
            availableSensorList.setAdapter(adapter);
        }
        if(isSensorSaved){
            SharedPreferences.Editor editor =
                    getSharedPreferences(SAVE_FILE,MODE_PRIVATE).edit();
            editor.putBoolean("isSensorSaved",true);
            editor.apply();
        }
    }

    class MainButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.notShowAgain:
                    isSaved = false;
                    break;
                case R.id.confirmButton:
                    if(!isSaved){
                        SharedPreferences.Editor editor =
                                getSharedPreferences(SAVE_FILE,MODE_PRIVATE).edit();
                        editor.putBoolean(ISSHOWEDFIRST,!notShowAgain.isChecked());
                        editor.apply();
                    }
                    Intent intent = new Intent(MainActivity.this,SavedExperimentShow.class);
                    startActivity(intent);
                    finish();
            }
        }
    }

}
