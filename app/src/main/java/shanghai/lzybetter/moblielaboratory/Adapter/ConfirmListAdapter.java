package shanghai.lzybetter.moblielaboratory.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import shanghai.lzybetter.moblielaboratory.R;

/**
 * Created by lzybetter on 2017/4/19.
 */

public class ConfirmListAdapter extends RecyclerView.Adapter<ConfirmListAdapter.ViewHolder> {

    private List<String> names;

    public ConfirmListAdapter(List<String> names) {
        this.names = names;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView confirmName;

        public ViewHolder(View itemView) {
            super(itemView);
            confirmName = (TextView)itemView.findViewById(R.id.confirmName);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.confirm_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = names.get(position);
        holder.confirmName.setText(name);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

}
