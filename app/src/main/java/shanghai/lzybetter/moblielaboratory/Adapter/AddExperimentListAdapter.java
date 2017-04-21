package shanghai.lzybetter.moblielaboratory.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import shanghai.lzybetter.moblielaboratory.R;

/**
 * Created by lzybetter on 2017/4/19.
 */

public class AddExperimentListAdapter extends RecyclerView.Adapter<AddExperimentListAdapter.ViewHolder> {

    private List<String> sensorNames;
    private static HashMap<Integer,Boolean> isSelected;

    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout selectItemLayout;
        CheckBox selectItemCB;
        TextView selectName;

        public ViewHolder(View itemView) {
            super(itemView);
            selectItemLayout = (LinearLayout)itemView.findViewById(R.id.selectItemLayout);
            selectItemCB = (CheckBox)itemView.findViewById(R.id.selectCB);
            selectName = (TextView)itemView.findViewById(R.id.selectName);
        }
    }

    public AddExperimentListAdapter(List<String> sensorNames,HashMap<Integer,Boolean> isSelected) {
        this.sensorNames = sensorNames;
        this.isSelected = isSelected;
        init();
    }

    private void init() {
        for(int i = 0;i<sensorNames.size();i++){
            getIsSelected().put(i,false);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String sensorName = sensorNames.get(position);
        holder.selectName.setText(sensorName);
        holder.selectItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIsSelected().get(position)){
                    getIsSelected().put(position,false);
                    setIsSelected(getIsSelected());
                }else {
                    getIsSelected().put(position,true);
                    setIsSelected(getIsSelected());
                }
                notifyDataSetChanged();
            }
        });
        holder.selectItemCB.setChecked(getIsSelected().get(position));
    }

    @Override
    public int getItemCount() {
        return sensorNames.size();
    }

    public static HashMap<Integer,Boolean> getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(HashMap<Integer,Boolean> isSelected){
        this.isSelected = isSelected;
    }

}
