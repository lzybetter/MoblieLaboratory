package shanghai.lzybetter.moblielaboratory.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import shanghai.lzybetter.moblielaboratory.Activity.MulitSensor;
import shanghai.lzybetter.moblielaboratory.Class.SaveExperiment;
import shanghai.lzybetter.moblielaboratory.Class.SaveExperimentItem;
import shanghai.lzybetter.moblielaboratory.Class.SensorList;
import shanghai.lzybetter.moblielaboratory.R;

/**
 * Created by lzy17 on 2017/4/21.
 */

public class SavedExpermentAdapter extends RecyclerView.Adapter<SavedExpermentAdapter.ViewHolder> {

    private List<SaveExperimentItem> items;
    private Context context;
    private int position;

    public SavedExpermentAdapter(List<SaveExperimentItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView saveExpName;
        private TextView selectedSensor;
        private LinearLayout savedExperimentItem;


        public ViewHolder(View itemView) {
            super(itemView);
            saveExpName = (TextView)itemView.findViewById(R.id.savedExperimentName);
            selectedSensor = (TextView)itemView.findViewById(R.id.selectedSensor);
            savedExperimentItem = (LinearLayout)itemView.findViewById(R.id.savedExperimentItem);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.savedexperimentitem,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.savedExperimentItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickPosition = viewHolder.getAdapterPosition();
                SaveExperimentItem item = items.get(clickPosition);
                Intent intent = new Intent(context,MulitSensor.class);
                intent.putExtra("name",item.getSaveExpName());
                context.startActivity(intent);
            }
        });
        viewHolder.savedExperimentItem.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                position = viewHolder.getAdapterPosition();
                menu.add(0,0,0,"开始试验").setOnMenuItemClickListener(new SavedItemLongClickListener());
                menu.add(0,1,0,"删除").setOnMenuItemClickListener(new SavedItemLongClickListener());
            }
        });
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

    class SavedItemLongClickListener implements MenuItem.OnMenuItemClickListener{

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            SaveExperimentItem selectedItem = items.get(position);
            switch (item.getItemId()){
                case 0:
                    Intent intent = new Intent(context,MulitSensor.class);
                    intent.putExtra("name",selectedItem.getSaveExpName());
                    context.startActivity(intent);
                    break;
                case 1:
                    SharedPreferences sp = context.getSharedPreferences(
                            selectedItem.getSaveExpName(),Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.apply();
                    DataSupport.deleteAll(SaveExperiment.class,"experimentName=?",
                            selectedItem.getSaveExpName());
                    Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                    break;
            }
            items.remove(selectedItem);
            notifyDataSetChanged();
            return true;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
