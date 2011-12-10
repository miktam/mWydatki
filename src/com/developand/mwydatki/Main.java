package com.developand.mwydatki;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import com.developand.mwydatki.tools.ToastMaker;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class Main extends TabActivity {
	private static final String TAG = Main.class.getName();

	@Override
	protected void onResume() {

		Log.i(TAG, "ON RESUME");
		Intent intent = getIntent();
		String value = intent.getStringExtra("exit");

		Log.i(TAG, "intent: " + intent + " \nwith exit flag = " + value);

		if (value != null && value.equals("yes")) {
			Log.i(TAG, "just exit");
			this.finish();
		} else {
			Log.i(TAG, "no EXIT!");
		}

		super.onResume();

	}	
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest req = new AdRequest();
		req.setTesting(true);
		req.addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB");

		adView.loadAd(req);

		Resources res = getResources();
		TabHost tabHost = getTabHost();
		tabHost.clearAllTabs();
		TabHost.TabSpec spec;

		try {

			// Create an Intent to launch an Activity for the tab (to be reused)
			Intent intent = new Intent().setClass(this, DetailedIncomeActivity.class);

			// Initialize a TabSpec for each tab and add it to the TabHost
			spec = tabHost
					.newTabSpec("income")
					.setIndicator("Dochody",
							res.getDrawable(R.drawable.tab_all))
					.setContent(intent);
			tabHost.addTab(spec);

			intent = new Intent()
					.setClass(this, DetailedExpensesActivity.class);
			spec = tabHost
					.newTabSpec("expenses")
					.setIndicator("Wydatki",
							res.getDrawable(R.drawable.tab_detailes))
					.setContent(intent);
			tabHost.addTab(spec);

			tabHost.setCurrentTab(0);
		} catch (Exception e) {
			Log.w(TAG, "really bad: " + e.getMessage());
			ToastMaker.getToast(this, "Plik nie rozpoznany");
			this.finish();
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
	}
}