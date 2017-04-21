package shanghai.lzybetter.moblielaboratory.Activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import shanghai.lzybetter.moblielaboratory.Adapter.SavedExpermentAdapter;
import shanghai.lzybetter.moblielaboratory.Class.SaveExperiment;
import shanghai.lzybetter.moblielaboratory.Class.SaveExperimentItem;
import shanghai.lzybetter.moblielaboratory.Class.SensorList;
import shanghai.lzybetter.moblielaboratory.R;

public class SavedExperimentShow extends AppCompatActivity {

    private NavigationView availableNav;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton addExperimentButton;
    private RecyclerView savedExperimentList;
    private List<SaveExperimentItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_experimement_show);

        Toolbar title = (Toolbar)findViewById(R.id.savedExperimentTitle);
        setSupportActionBar(title);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        availableNav = (NavigationView)findViewById(R.id.availableNav);
        savedExperimentList = (RecyclerView)findViewById(R.id.savedExperimentList);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        init();

        addExperimentButton = (FloatingActionButton)findViewById(R.id.addExperimentButton);
        addExperimentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedExperimentShow.this,AddExperiment.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        initList();
        initNavigation();
    }
    private void initNavigation() {
        List<SensorList> sensors = DataSupport.findAll(SensorList.class);
        int num = 0;
        for(SensorList sensor : sensors){
            availableNav.getMenu().add(sensor.getSensorName());
            availableNav.getMenu().getItem(num).setIcon(R.drawable.ic_menu);
            num++;
        }
        availableNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent(SavedExperimentShow.this,SingleSensor.class);
                intent.putExtra("name",item.getTitle().toString());
                switch (item.getTitle().toString()){
                    case "加速度传感器":
                        intent.putExtra("type", Sensor.TYPE_ACCELEROMETER);
                        break;
                    case "磁力传感器":
                        intent.putExtra("type", Sensor.TYPE_MAGNETIC_FIELD);
                        break;
                    case "陀螺仪":
                        intent.putExtra("type", Sensor.TYPE_GYROSCOPE);
                        break;
                    case "接近传感器（可以用来测距）":
                        intent.putExtra("type", Sensor.TYPE_PROXIMITY);
                        break;
                    case "光强传感器":
                        intent.putExtra("type", Sensor.TYPE_LIGHT);
                        break;
                    case "重力传感器":
                        intent.putExtra("type", Sensor.TYPE_GRAVITY);
                        break;
                    case "线加速度传感器":
                        intent.putExtra("type", Sensor.TYPE_LINEAR_ACCELERATION);
                        break;
                    case "旋转矢量传感器":
                        intent.putExtra("type", Sensor.TYPE_ROTATION_VECTOR);
                        break;
                    case "相对湿度传感器":
                        intent.putExtra("type", Sensor.TYPE_RELATIVE_HUMIDITY);
                        break;
                    case "环境温度传感器":
                        intent.putExtra("type", Sensor.TYPE_AMBIENT_TEMPERATURE);
                        break;
                    case "压力传感器":
                        intent.putExtra("type", Sensor.TYPE_PRESSURE);
                        break;
                    default:
                        break;
                }
                startActivity(intent);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initList() {
        List<SaveExperiment> saves = DataSupport.findAll(SaveExperiment.class);
        for(SaveExperiment save:saves){
            SaveExperimentItem item = new SaveExperimentItem(save.getExperimentName(),
                    save.getIsSelected());
            items.add(item);
        }
        SavedExpermentAdapter adapter = new SavedExpermentAdapter(items);
        savedExperimentList.setLayoutManager(new LinearLayoutManager(SavedExperimentShow.this));
        savedExperimentList.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
