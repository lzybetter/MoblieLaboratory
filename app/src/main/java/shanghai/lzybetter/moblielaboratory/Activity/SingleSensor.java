package shanghai.lzybetter.moblielaboratory.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import shanghai.lzybetter.moblielaboratory.Action.SensorListener;
import shanghai.lzybetter.moblielaboratory.R;

import static android.os.Environment.MEDIA_MOUNTED;
import static android.view.View.GONE;

public class SingleSensor extends AppCompatActivity {

    private TextView dataShow,detail,unit1,unit2;
    private int type;
    private String name;
    private Sensor sensor;
    private LineChart singleSensorShow;
    private YAxis yAxisLeft;
    private long startRecordTime;
    private boolean isWriting,isWtringFirst;
    private FloatingActionButton startRecordFab,pauseRecordFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_sensor);

        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        name = intent.getStringExtra("name");

        Toolbar title = (Toolbar)findViewById(R.id.singleSensor);
        title.setTitle(name);
        setSupportActionBar(title);

        if(ContextCompat.checkSelfPermission(SingleSensor.this, Manifest
                .permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SingleSensor.this,new
            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        dataShow = (TextView)findViewById(R.id.dataShow);
        detail = (TextView)findViewById(R.id.detail);
        unit1 = (TextView)findViewById(R.id.unit1);
        unit2 = (TextView)findViewById(R.id.unit2);
        startRecordFab = (FloatingActionButton)findViewById(R.id.startRecordFab);
        pauseRecordFab = (FloatingActionButton)findViewById(R.id.pauseRecordFab);
        startRecordFab.setOnClickListener(new ButtonListener());
        pauseRecordFab.setOnClickListener(new ButtonListener());
        pauseRecordFab.setVisibility(GONE);
        singleSensorShow = (LineChart)findViewById(R.id.singleSensorShow);

        LineData data = new LineData();
        singleSensorShow.setData(data);

        singleSensorShow.setDragEnabled(true);
        singleSensorShow.setScaleEnabled(true);
        singleSensorShow.setDrawGridBackground(true);
        singleSensorShow.setPinchZoom(true);
        singleSensorShow.setBackgroundColor(Color.LTGRAY);

        isWriting = false;

        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(type);

        textInit();

    }

    private void pictureInit(SensorEvent event,boolean isThree){

        LineData data = singleSensorShow.getLineData();

        if(data != null){
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null){
                if(isThree){
                    set = createXSet();
                    data.addDataSet(set);
                    set = createYSet();
                    data.addDataSet(set);
                    set = createZSet();
                    data.addDataSet(set);
                }else{
                    set = createXSet();
                    data.addDataSet(set);
                }
            }
            if(isThree){
                data.addEntry(new Entry(set.getEntryCount(),event.values[0]),0);
                data.addEntry(new Entry(set.getEntryCount(),event.values[1]),1);
                data.addEntry(new Entry(set.getEntryCount(),event.values[2]),2);
            }else {
                data.addEntry(new Entry(set.getEntryCount(),event.values[0]),0);
            }
            data.notifyDataChanged();
            singleSensorShow.notifyDataSetChanged();
            singleSensorShow.setVisibleXRangeMaximum(120);
            singleSensorShow.moveViewToX(data.getEntryCount());

            Legend legend = singleSensorShow.getLegend();
            legend.setForm(Legend.LegendForm.LINE);
            legend.setTextColor(Color.WHITE);

            XAxis xAxis = singleSensorShow.getXAxis();

            xAxis.setTextColor(Color.WHITE);
            xAxis.setDrawGridLines(true);
            xAxis.setAvoidFirstLastClipping(true);
            xAxis.setEnabled(true);

            yAxisLeft = singleSensorShow.getAxisLeft();
            yAxisLeft.setTextColor(Color.WHITE);
            yAxisLeft.setDrawGridLines(true);
            yAxisLeft.setEnabled(true);
            yAxisLeft.setAxisMaximum(sensor.getMaximumRange());
            yAxisLeft.setAxisMinimum(-sensor.getMaximumRange());

            YAxis yAxisRight = singleSensorShow.getAxisRight();
            yAxisRight.setEnabled(false);
        }
    }

    private LineDataSet createXSet() {

        LineDataSet set = new LineDataSet(null, "x轴");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setLineWidth(2f);
        set.setFillAlpha(65);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setDrawCircleHole(false);
        return set;
    }

    private LineDataSet createYSet() {

        LineDataSet set = new LineDataSet(null, "y轴");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.RED);
        set.setLineWidth(2f);
        set.setFillAlpha(65);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setDrawCircleHole(false);
        return set;
    }

    private LineDataSet createZSet() {

        LineDataSet set = new LineDataSet(null, "z轴");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.GREEN);
        set.setLineWidth(2f);
        set.setFillAlpha(65);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setDrawCircleHole(false);
        return set;
    }


    private void textInit(){

        SpannableString unit = null;
        StringBuilder dataSb = new StringBuilder();
        StringBuilder unitSb = new StringBuilder();

        switch (name){
            case "加速度传感器":
                dataSb.append("X轴加速度：").append("0").append("\n");
                dataSb.append("Y轴加速度：").append("0").append("\n");
                dataSb.append("Z轴加速度：").append("0");
                unit = new SpannableString("米/秒2(m/s2)\n米/秒2(m/s2)\n米/秒2(m/s2)");
                unit.setSpan(new SuperscriptSpan(),3,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),3,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),8,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),8,9,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),14,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),14,15,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),19,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),19,20,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),25,26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),25,26,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),30,31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),30,31,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit1.setText(unit);
                unit = new SpannableString("米/秒2(m/s2)\n米/秒2(m/s2)");
                unit.setSpan(new SuperscriptSpan(),3,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),3,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),8,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),8,9,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),14,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),14,15,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),19,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),19,20,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit2.setText(unit);
                break;
            case "磁力传感器":
                dataSb.append("X轴磁通量：").append("0").append("\n");
                dataSb.append("Y轴磁通量：").append("0").append("\n");
                dataSb.append("Z轴磁通量：").append("0");
                unit = new SpannableString("微特斯拉(μT)\n微特斯拉(μT)\n微特斯拉(μT)");
                unit1.setText(unit);
                unit = new SpannableString("微特斯拉(μT)\n微特斯拉(μT)");
                unit2.setText(unit);
                break;
            case "陀螺仪":
                dataSb.append("X轴角速度：").append("0").append("\n");
                dataSb.append("Y轴角速度：").append("0").append("\n");
                dataSb.append("Z轴角速度：").append("0");
                unit = new SpannableString("弧度/秒(rad/s)\n弧度/秒(rad/s)\n弧度/秒(rad/s)");
                unit1.setText(unit);
                unit = new SpannableString("弧度/秒(rad/s)\n弧度/秒(rad/s)");
                unit2.setText(unit);
                break;
            case "接近传感器（可以用来测距）":
                dataSb.append("距离：").append("0");
                unit = new SpannableString("厘米(cm)");
                unit1.setText(unit);
                unit = new SpannableString("厘米(cm)\n厘米(cm)");
                unit2.setText(unit);
                break;
            case "光强传感器":
                dataSb.append("光强度： ").append("0");
                unit = new SpannableString("勒克斯(lx)");
                unit1.setText(unit);
                unit = new SpannableString("勒克斯(lx)\n勒克斯(lx)");
                unit2.setText(unit);
                break;
            case "重力传感器":
                dataSb.append("X轴重力加速度：").append("0").append("\n");
                dataSb.append("Y轴重力加速度：").append("0").append("\n");
                dataSb.append("Z轴重力加速度：").append("0");
                unit = new SpannableString("米/秒2(m/s2)\n米/秒2(m/s2)\n米/秒2(m/s2)");
                unit.setSpan(new SuperscriptSpan(),3,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),3,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),8,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),8,9,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),14,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),14,15,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),19,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),19,20,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),25,26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),25,26,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),30,31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),30,31,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit1.setText(unit);
                unit = new SpannableString("米/秒2(m/s2)\n米/秒2(m/s2)");
                unit.setSpan(new SuperscriptSpan(),3,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),3,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),8,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),8,9,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),14,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),14,15,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),19,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),19,20,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit2.setText(unit);
                break;
            case "线加速度传感器":
                dataSb.append("X轴线加速度：").append("0").append("\n");
                dataSb.append("Y轴线加速度：").append("0").append("\n");
                dataSb.append("Z轴线加速度：").append("0");
                unit = new SpannableString("米/秒2(m/s2)\n米/秒2(m/s2)\n米/秒2(m/s2)");
                unit.setSpan(new SuperscriptSpan(),3,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),3,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),8,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),8,9,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),14,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),14,15,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),19,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),19,20,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),25,26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),25,26,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),30,31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),30,31,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit1.setText(unit);
                unit = new SpannableString("米/秒2(m/s2)\n米/秒2(m/s2)");
                unit.setSpan(new SuperscriptSpan(),3,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),3,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),8,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),8,9,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),14,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),14,15,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new SuperscriptSpan(),19,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit.setSpan(new RelativeSizeSpan(0.5f),19,20,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                unit2.setText(unit);
                break;
            case "旋转矢量传感器":
                dataSb.append("X轴旋转矢量：").append("0").append("\n");
                dataSb.append("Y轴旋转矢量：").append("0").append("\n");
                dataSb.append("Z轴旋转矢量：").append("0");
                break;
            case "相对湿度传感器":
                dataSb.append("相对湿度：").append("0");
                unit = new SpannableString("%");
                unit1.setText(unit);
                unit = new SpannableString("%\n%");
                unit2.setText(unit);
                break;
            case "环境温度传感器":
                dataSb.append("当前环境温度为：").append("0");
                unit = new SpannableString("℃");
                unit1.setText(unit);
                unit = new SpannableString("℃\n℃");
                unit2.setText(unit);
                break;
            case "压力传感器":
                dataSb.append("当前环境压力为：").append("0");
                unit = new SpannableString("百帕斯卡(hPa)");
                unit1.setText(unit);
                unit2.setText(unit);
                break;
            default:
                break;
        }
        dataShow.setText(dataSb);
        unitSb.append("最大量程为： ").append(sensor.getMaximumRange()).append("\n");
        unitSb.append("分辨率为： ").append(sensor.getResolution());
        detail.setText(unitSb);
    }

    private void numberShow(SensorEvent event){
        switch (name){
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

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            numberShow(event);
            boolean isThree = false;
            switch (name){
                case "加速度传感器":
                    isThree = true;
                    break;
                case "磁力传感器":
                    isThree = true;
                    break;
                case "陀螺仪":
                    isThree = true;
                    break;
                case "接近传感器（可以用来测距）":
                    isThree = false;
                    break;
                case "光强传感器":
                    isThree = false;
                    break;
                case "重力传感器":
                    isThree = true;
                    break;
                case "线加速度传感器":
                    isThree = true;
                    break;
                case "旋转矢量传感器":
                    isThree = true;
                    break;
                case "相对湿度传感器":
                    isThree = false;
                    break;
                case "环境温度传感器":
                    isThree = false;
                    break;
                case "压力传感器":
                    isThree = false;
                    break;
                default:
                    break;
            }
            pictureInit(event,isThree);
            if(isWriting){
                writeToFile(event,isThree);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void writeToFile(SensorEvent event, boolean isThree) {

        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        long nowTime = calendar.getTimeInMillis();
        long recordTime = nowTime - startRecordTime;
        if(isWtringFirst){
            sb.append("传感器名称： ").append(name).append(" ");
            Date currentTime = new Date(startRecordTime);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(currentTime);
            sb.append("实验时间： ").append(dateString).append("\n");
            sb.append("传感器制造商： ").append(sensor.getVendor()).append("\n");
            sb.append("传感器最大量程： ").append(sensor.getMaximumRange())
                    .append(unit2.getText().toString().split("\n")[0]).append("\n");
            sb.append("传感器分辨率： ").append(sensor.getResolution())
                    .append(unit2.getText().toString().split("\n")[0]).append("\n");
            sb.append("单位： ").append(unit2.getText().toString().split("\n")[0]).append("\n");
            sb.append("时间(ms)：");
            if(isThree){
                sb.append(" X轴： ").append(" Y轴： ").append(" Z轴： ").append("\n");
            }else{
                sb.append(" 数据： ");
            }
            isWtringFirst = false;
        }
        sb.append(recordTime).append(",");
        if(isThree){
            sb.append(event.values[0]).append(",")
                    .append(event.values[1]).append(",")
                    .append(event.values[2]);
            sb.append("\n");
        }else {
            sb.append(event.values[0]);
            sb.append("\n");
        }
        try {
            File path = new File(Environment.getExternalStorageDirectory().getCanonicalPath()
                    + File.separator + "SensorData");
            if(!path.exists()){
                path.mkdir();
            }
            File targetFile = new File (path.getCanonicalPath() + File.separator + "Data"
                    + startRecordTime + ".txt");
            RandomAccessFile raf = new RandomAccessFile(targetFile,"rw");
            raf.seek(targetFile.length());
            raf.write(sb.toString().getBytes("gbk"));
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.startRecordFab:
                    if(!isWriting && (startRecordTime == 0 || startRecordFab == null)){
                        Calendar calendar = Calendar.getInstance();
                        startRecordTime = calendar.getTimeInMillis();
                        isWriting = true;
                        isWtringFirst = true;
                        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
                        sensor = sensorManager.getDefaultSensor(type);
                        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_GAME);
                        Toast.makeText(SingleSensor.this,"开始记录",Toast.LENGTH_SHORT).show();
                        startRecordFab.setImageResource(R.drawable.ic_stop);
                        pauseRecordFab.setVisibility(View.VISIBLE);
                    }else {
                        isWriting = false;
                        startRecordTime = 0;
                        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
                        sensorManager.unregisterListener(listener);
                        Toast.makeText(SingleSensor.this,"停止记录",Toast.LENGTH_SHORT).show();
                        startRecordFab.setImageResource(R.drawable.ic_play);
                        pauseRecordFab.setVisibility(GONE);
                    }

                    break;
                case R.id.pauseRecordFab:
                    if(isWriting){
                        isWriting = false;
                        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
                        sensorManager.unregisterListener(listener);
                        Toast.makeText(SingleSensor.this,"记录暂停",Toast.LENGTH_SHORT).show();
                        pauseRecordFab.setImageResource(R.drawable.ic_replay);
                    }else {
                        isWriting = true;
                        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
                        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_GAME);
                        Toast.makeText(SingleSensor.this,"恢复记录",Toast.LENGTH_SHORT).show();
                        pauseRecordFab.setVisibility(GONE);
                    }
                    break;

            }
        }
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
