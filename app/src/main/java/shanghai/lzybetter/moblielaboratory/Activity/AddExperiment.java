package shanghai.lzybetter.moblielaboratory.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import shanghai.lzybetter.moblielaboratory.Adapter.AddExperimentAdapter;
import shanghai.lzybetter.moblielaboratory.Adapter.AddExperimentListAdapter;
import shanghai.lzybetter.moblielaboratory.Adapter.ConfirmListAdapter;
import shanghai.lzybetter.moblielaboratory.Class.AddExperimentViewPager;
import shanghai.lzybetter.moblielaboratory.Class.SaveExperiment;
import shanghai.lzybetter.moblielaboratory.Class.SensorList;
import shanghai.lzybetter.moblielaboratory.R;


public class AddExperiment extends AppCompatActivity {

    private View addTitle,selectSensor,confirmView;
    private AddExperimentViewPager addExperiment;
    private List<View> viewList;
    private EditText inputName;
    private TextView confirmText;
    private RecyclerView selectList,confirmList;
    private CheckBox startNow;
    private Button preViewButton,finishButton,nextViewButton;
    private String expName;
    private List<String> sensorNames = new ArrayList<>();
    private AddExperimentListAdapter adapter;
    private List<String> selectedSensorNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_experiment);

        addExperiment = (AddExperimentViewPager)findViewById(R.id.addExperimentPager);

        LayoutInflater lf = getLayoutInflater().from(this);
        addTitle = lf.inflate(R.layout.addexperimentinput,null);
        selectSensor = lf.inflate(R.layout.addexperimentselect,null);
        confirmView = lf.inflate(R.layout.addexperimentconfirm,null);

        inputName = (EditText)addTitle.findViewById(R.id.expNameInput);
        selectList = (RecyclerView)selectSensor.findViewById(R.id.selectList);
        confirmText = (TextView)confirmView.findViewById(R.id.confirmText);
        confirmList = (RecyclerView)confirmView.findViewById(R.id.confirmList);
        startNow = (CheckBox)confirmView.findViewById(R.id.startNow);

        preViewButton = (Button)findViewById(R.id.preViewButton);
        nextViewButton = (Button)findViewById(R.id.nextViewButton);
        finishButton = (Button)findViewById(R.id.finishButton);

        viewList = new ArrayList<>();
        viewList.add(addTitle);
        viewList.add(selectSensor);
        viewList.add(confirmView);

        AddExperimentAdapter addExperimentAdapter = new AddExperimentAdapter(viewList,this);
        addExperiment.setAdapter(addExperimentAdapter);

        finishButton.setVisibility(View.GONE);
        preViewButton.setClickable(false);

        nextViewButton.setOnClickListener(new ButtonListener());
        preViewButton.setOnClickListener(new ButtonListener());
        finishButton.setOnClickListener(new ButtonListener());

    }

    private void initList() {

        LinearLayoutManager ll = new LinearLayoutManager(this);
        selectList.setLayoutManager(ll);

        List<SensorList> sensors = DataSupport.findAll(SensorList.class);
        for(SensorList sensor:sensors){
            sensorNames.add(sensor.getSensorName());
        }
        HashMap<Integer,Boolean> isSelected = new HashMap<Integer, Boolean>();
        adapter = new AddExperimentListAdapter(sensorNames,isSelected);
        selectList.setAdapter(adapter);
    }

    class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.nextViewButton:
                    if(addExperiment.getCurrentItem() == 0) {
                        if(inputName.getText().toString().isEmpty()){
                            Calendar calendar = Calendar.getInstance();
                            expName = "新实验" + calendar.getTimeInMillis();
                        }else{
                            expName = inputName.getText().toString();
                        }
                    }else if(addExperiment.getCurrentItem() == 1){
                        HashMap<Integer,Boolean> isSelected = adapter.getIsSelected();
                        for(int i=0;i<isSelected.size();i++){
                            if(isSelected.get(i)){
                                selectedSensorNames.add(sensorNames.get(i));
                            }
                        }
                    }
                    if(addExperiment.getCurrentItem() < 2){
                        addExperiment.setCurrentItem(addExperiment.getCurrentItem() + 1);
                    }
                    if(addExperiment.getCurrentItem() == 2){
                        finishButton.setVisibility(View.VISIBLE);
                        nextViewButton.setVisibility(View.GONE);
                        confirmText.setText(expName);
                        ConfirmListAdapter confirmAdapter = new ConfirmListAdapter(selectedSensorNames);
                        confirmList.setLayoutManager(new LinearLayoutManager(AddExperiment.this));
                        confirmList.setAdapter(confirmAdapter);
                    }else if(addExperiment.getCurrentItem() == 1){
                        initList();
                    }
                    if(addExperiment.getCurrentItem() != 0){
                        preViewButton.setClickable(true);
                    }
                    break;
                case R.id.preViewButton:
                    if(addExperiment.getCurrentItem() > 0){
                        addExperiment.setCurrentItem(addExperiment.getCurrentItem() - 1);
                    }
                    if(addExperiment.getCurrentItem() == 1){
                        selectedSensorNames.clear();
                    }else if(addExperiment.getCurrentItem() == 0){
                        preViewButton.setClickable(false);
                    }
                    if (addExperiment.getCurrentItem() != 2){
                        finishButton.setVisibility(View.GONE);
                        nextViewButton.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.finishButton:
                    if(finishButton.getVisibility() == View.VISIBLE){
                        SaveExperiment saveExperiment = new SaveExperiment();
                        saveExperiment.setExperimentName(expName);
                        saveExperiment.setIsSelected(adapter.getIsSelected());
                        Intent intent = null;
                        if(startNow.isChecked()){
                            intent = new Intent(AddExperiment.this,MulitSensor.class);
                        }else {
                            intent = new Intent(AddExperiment.this,SavedExperimentShow.class);
                        }
                        startActivity(intent);
                        finish();
                    }
                    break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorNames.clear();
        adapter = null;
    }
}
