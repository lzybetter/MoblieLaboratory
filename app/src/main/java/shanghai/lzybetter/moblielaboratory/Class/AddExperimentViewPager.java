package shanghai.lzybetter.moblielaboratory.Class;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lzybetter on 2017/4/16.
 */

public class AddExperimentViewPager extends ViewPager {
    public AddExperimentViewPager(Context context) {
        super(context);
    }

    public AddExperimentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
