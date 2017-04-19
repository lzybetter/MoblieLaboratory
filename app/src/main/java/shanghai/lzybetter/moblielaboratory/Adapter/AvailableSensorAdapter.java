package shanghai.lzybetter.moblielaboratory.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import shanghai.lzybetter.moblielaboratory.Activity.SensorDetail;
import shanghai.lzybetter.moblielaboratory.Class.AvailableSensor;
import shanghai.lzybetter.moblielaboratory.R;

/**
 * Created by lzybetter on 2017/4/1.
 */

public class AvailableSensorAdapter extends RecyclerView.Adapter<AvailableSensorAdapter.ViewHolder> {

    private List<AvailableSensor> availableSensorList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView availableSensorImg;
        TextView availableSensorName;
        View availableSensorView;


        public ViewHolder(View itemView) {
            super(itemView);
            availableSensorView = itemView;
            availableSensorImg = (ImageView)itemView.findViewById(R.id.availableSensorImg);
            availableSensorName = (TextView)itemView.findViewById(R.id.availableSensorName);
        }
    }

    public AvailableSensorAdapter(List<AvailableSensor> availableSensorList,Context context) {
        this.availableSensorList = availableSensorList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.availablesensorlistitem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        AvailableSensor availableSensor = availableSensorList.get(position);
        holder.availableSensorImg.setImageResource(R.mipmap.ic_launcher);
        holder.availableSensorName.setText(availableSensor.getSensorName());
        holder.availableSensorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                AvailableSensor availableSensor = availableSensorList.get(position);
                int type = availableSensor.getSensorType();
                String name = availableSensor.getSensorName();
                Intent intent = new Intent(mContext, SensorDetail.class);
                intent.putExtra("sensorType",type);
                intent.putExtra("sensorName",name);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return availableSensorList.size();
    }


}
