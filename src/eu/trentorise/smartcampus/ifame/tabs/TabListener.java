package eu.trentorise.smartcampus.ifame.tabs;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class TabListener<T extends Fragment> implements ActionBar.TabListener {

	private Fragment mFragment;
	private final SherlockFragmentActivity mActivity;
	private final String mTag;
	private final Class<T> mClass;
	private int mViewGroup = android.R.id.content;

	/**
	 * Constructor used each time a new tab is created.
	 * 
	 * @param activity
	 *            The host Activity, used to instantiate the fragment
	 * @param tag
	 *            The identifier tag for the fragment
	 * @param clz
	 *            The fragment's Class, used to instantiate the fragment
	 */
	public TabListener(SherlockFragmentActivity activity, String tag,
			Class<T> clz, Integer viewGroup) {
		mActivity = activity;
		mTag = tag;
		mClass = clz;
		if (viewGroup != null) {
			mViewGroup = viewGroup;
		}
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		SherlockFragment preInitializedFragment = (SherlockFragment) mActivity
				.getSupportFragmentManager().findFragmentByTag(mTag);
		if (preInitializedFragment != null
				&& !preInitializedFragment.equals(mFragment)) {
			ft.remove(preInitializedFragment);
		}

		if (mFragment == null) {
			mFragment = Fragment.instantiate(mActivity, mClass.getName());
			ft.add(android.R.id.content, mFragment, mTag);

		} else {
			// If it exists, simply attach it in order to show it
			ft.attach(mFragment);
		}

	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		mActivity.getSupportFragmentManager().popBackStack(mTag,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
		if (mFragment != null) {
			// Detach the fragment, because another one is being attached
			ft.detach(mFragment);
		}

	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// User selected the already selected tab. Usually do nothing.
	}

}
