package shanghai.lzybetter.moblielaboratory.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import shanghai.lzybetter.moblielaboratory.Class.SaveExperimentItem;
import shanghai.lzybetter.moblielaboratory.Class.SensorList;
import shanghai.lzybetter.moblielaboratory.R;

/**
 * Created by lzy17 on 2017/4/21.
 */

public class SavedExpermentAdapter extends RecyclerView.Adapter<SavedExpermentAdapter.ViewHolder> {

    private List<SaveExperimentItem> items;

    public SavedExpermentAdapter(List<SaveExperimentItem> items) {
        this.items = items;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView saveExpName;
        private TextView selectedSensor;


        public ViewHolder(View itemView) {
            super(itemView);
            saveExpName = (TextView)itemView.findViewById(R.id.savedExperimentName);
            selectedSensor = (TextView)itemView.findViewById(R.id.selectedSensor);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.savedexperimentitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SaveExperimentItem item = items.get(position);
        holder.saveExpName.setText(item.getSaveExpName());
        StringBuilder sb = new StringBuilder();
        List<String> selectedSensor = item.getSelectedSensor();
        for(String name:selectedSensor){
            sb.append(name).append(" ");
        }
        holder.selectedSensor.setText(sb);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
