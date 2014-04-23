package eu.trentorise.smartcampus.ifame.utils;

import com.github.espiandev.showcaseview.TutorialHelper;
import com.github.espiandev.showcaseview.TutorialItem;
import com.github.espiandev.showcaseview.TutorialHelper.TutorialProvider;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.IFameMain;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

public class TutorialUtils {

	private static final String NAME = "ifametut";

	private static final String FIRSTLAUNCH = "ifametut";

	private static SharedPreferences getPrefs(Context ctx) {
		return ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	}

	public static void disableTutorial(Context ctx) {
		SharedPreferences sp = getPrefs(ctx);
		sp.edit().putBoolean(FIRSTLAUNCH, false).commit();
	}

	public static void enableTutorial(Context ctx) {
		SharedPreferences sp = getPrefs(ctx);
		sp.edit().putBoolean(FIRSTLAUNCH, true).commit();
	}

	public static boolean isTutorialEnabled(Context ctx) {
		return getPrefs(ctx).getBoolean(FIRSTLAUNCH, true);
	}

	public static TutorialHelper getTutorial(final IFameMain act) {
		TutorialProvider tutProvider = new TutorialProvider() {

			@Override
			public int size() {
				return 5;
			}

			@Override
			public void onTutorialFinished() {
				disableTutorial(act);
				MensaUtils.getAndSaveMensaList(act, false);
			}

			@Override
			public void onTutorialCancelled() {
				disableTutorial(act);
				MensaUtils.getAndSaveMensaList(act, false);
			}

			@Override
			public TutorialItem getItemAt(int pos) {
				View v = null;
				int[] location = new int[2];
				switch (pos) {
				case 0:
					v = act.findViewById(R.id.favourite_mensa_ifame_main);
					if(v!=null && v.isShown()){
					v.getLocationOnScreen(location);
					return new TutorialItem("search", location, v.getWidth(),
							act.getString(R.string.iFameMain_favourite_mensa),
							act.getString(R.string.tut_star));
					}
					break;
				case 1:
					v = act.findViewById(R.id.iDeciso_button);
					if(v!=null && v.isShown()){
					v.getLocationOnScreen(location);
					return new TutorialItem("search", location, v.getWidth(),
							act.getString(R.string.iFame_main_title_activity),
							act.getString(R.string.tut_ifame));
					}
					break;
				case 2:
					v = act.findViewById(R.id.iFretta_button);
					v.getLocationOnScreen(location);
					if(v!=null && v.isShown()){
					return new TutorialItem("search", location, v.getWidth(),
							act.getString(R.string.iFretta_details_title_activity),
							act.getString(R.string.tut_ifame));
					}
					break;
				case 3:
					v = act.findViewById(R.id.iSoldi_button);
					v.getLocationOnScreen(location);
					if(v!=null && v.isShown()){
					return new TutorialItem("search", location, v.getWidth(),
							act.getString(R.string.iSoldi_title_activity),
							act.getString(R.string.tut_isoldi));
					}
					break;
				case 4:
					v = act.findViewById(R.id.iGradito_button);
					v.getLocationOnScreen(location);
					if(v!=null && v.isShown()){
					return new TutorialItem("search", location, v.getWidth(),
							act.getString(R.string.iGradito_Activity),
							act.getString(R.string.tut_igradito));
					}
					break;
				}
				return null;
			}
		};
		return new TutorialHelper(act, tutProvider);
	}
}
