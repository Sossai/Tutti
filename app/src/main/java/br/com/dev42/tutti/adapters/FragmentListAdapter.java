package br.com.dev42.tutti.adapters;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

import br.com.dev42.tutti.fragments.TelaEscalasFragment;

/**
 * Created by sossai on 29/03/17.
 */

public class FragmentListAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

    private List<Fragment> listaFragments;

    public FragmentListAdapter(FragmentManager fm, List<Fragment> listaFragments) {
        super(fm);
        this.listaFragments = listaFragments;
    }

    @Override
    public int getCount() {
        return listaFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return this.listaFragments.get(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

//        Log.e("DEV42", "OAP");
//        TelaEscalasFragment frg = (TelaEscalasFragment)this.listaFragments.get(position);
//        frg.updateLayout();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        Log.e("DEV42", "opa 2");
    }

    @Override
    public int getItemPosition(Object object) {
//        return super.getItemPosition(object);
        return POSITION_NONE;
    }

}
