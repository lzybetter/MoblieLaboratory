package shanghai.lzybetter.moblielaboratory.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import shanghai.lzybetter.moblielaboratory.Class.AddExperimentViewPager;
import shanghai.lzybetter.moblielaboratory.R;

/**
 * Created by lzybetter on 2017/4/16.
 */

public class AddExperimentAdapter extends PagerAdapter {

    private List<View> viewList;
    private AppCompatActivity  activity;

    public AddExperimentAdapter(List<View> viewList,AppCompatActivity activity) {
        this.viewList = viewList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view = viewList.get(position);
        container.addView(view);

        switch (position){
            case 0:
                Toolbar inputToolbar = (Toolbar)view.findViewById(R.id.inputTitle);
                inputToolbar.setTitle("实验名称");
                activity.setSupportActionBar(inputToolbar);
                break;
            case 1:
                Toolbar selectToolbar = (Toolbar)view.findViewById(R.id.selectTitle);
                RecyclerView selectList = (RecyclerView)container.findViewById(R.id.selectList);
                selectToolbar.setTitle("请选择要使用的传感器");
                break;
            case 2:
                Toolbar confirmToolbar = (Toolbar)view.findViewById(R.id.confirmTitle);
                confirmToolbar.setTitle("请确认您的选择");
                activity.setSupportActionBar(confirmToolbar);
                break;
            default:
                break;
        }

        return view;
    }
}
