package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.MensaAdapter;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.utils.ListViewUtils;
import eu.trentorise.smartcampus.ifame.utils.MensaUtils;

public class MensaPreferita extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ifretta);

		ListView mListViewMensa = (ListView) findViewById(R.id.ifretta_page_list);

		// setup actionbar (supportActionBar is initialized in super.onCreate())
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// initialize and setup the adapter with the listener
		final MensaAdapter adapterMensaList = new MensaAdapter(this);

		ArrayList<Mensa> lista = MensaUtils.getMensaList(this);
		for (Mensa mensa : lista) {
			adapterMensaList.add(mensa);
		}

		mListViewMensa.setAdapter(adapterMensaList);
		mListViewMensa.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {

				MensaUtils.setFavouriteMensa(MensaPreferita.this,
						(Mensa) adapter.getItemAtPosition(position));
				adapterMensaList.notifyDataSetChanged();
			}
		});

		ListViewUtils.setListViewHeightBasedOnChildren(mListViewMensa);

		Button done = (Button) findViewById(R.id.favourite_mensa_select_done);
		done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showToastFavouriteCanteen();
				setResult(RESULT_OK);
				finish();
			}
		});
	}

	private void showToastFavouriteCanteen() {

		View toastLayout = getLayoutInflater().inflate(
				R.layout.layout_custom_toast_favourite_mensa,
				(ViewGroup) findViewById(R.id.custom_toast_favourite_mensa));

		TextView mensaName = (TextView) toastLayout
				.findViewById(R.id.text_favourite_canteen);
		mensaName.setText(MensaUtils.getFavouriteMensaName(MensaPreferita.this));

		Toast customToast = new Toast(MensaPreferita.this);
		customToast.setDuration(Toast.LENGTH_SHORT);
		customToast.setView(toastLayout);
		customToast.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
