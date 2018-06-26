package top.dodeman.waterdropindicatordemo;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author juyao
 * @date 2018/6/25
 * @email juyao0909@gmail.com
 */
public class MyVpAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=new View(container.getContext());
        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        switch (position){
            case 0:
                view.setBackgroundColor(Color.GREEN);
                break;
            case 1:
                view.setBackgroundColor(Color.CYAN);
                break;
            case 2:
                view.setBackgroundColor(Color.YELLOW);
                break;
                default:
                    break;
        }
        container.addView(view,lp);
        return view;
    }
}
