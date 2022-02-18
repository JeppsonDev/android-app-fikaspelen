package cs.umu.se.fikaspelen;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * An adapter for sliding inbetween pages in ViewPager
 *
 * @since 2021-03-22
 * @author Jesper Byström
 */
public class FikaFragmentSlideAdapter extends FragmentStatePagerAdapter {

    private List<FikaItem> fikaItems;
    private FoundItemListener foundItemListener;

    /**
     * Constructor
     *
     * @param fm
     */
    public FikaFragmentSlideAdapter(FragmentManager fm, List<FikaItem> fikaItems) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fikaItems = fikaItems;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        FikaFragment f = FikaFragment.newInstance(fikaItems.get(position));
        f.setRetainInstance(true);
        f.setFoundItemListener(new FoundItemListener() {
            @Override
            public void invoke() {
                if(foundItemListener != null){
                    foundItemListener.invoke();
                }
            }
        });
        return f;
    }

    @Override
    public int getCount() {
        return 10;
    }

    public void setFoundItemListener(FoundItemListener i){
        this.foundItemListener = i;
    }
}
