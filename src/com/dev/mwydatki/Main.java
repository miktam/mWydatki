package com.dev.mwydatki;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import com.dev.mwydatki.tools.ToastMaker;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class Main extends TabActivity {
	private static final String TAG = Main.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

		AdView adView = (AdView) this.findViewById(R.id.adView);
		adView.loadAd(new AdRequest());
		
		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		tabHost.clearAllTabs();
		TabHost.TabSpec spec;
		Intent intent; 

		try {

			// Create an Intent to launch an Activity for the tab (to be reused)
			intent = new Intent().setClass(this, DetailedIncomeActivity.class);

			// Initialize a TabSpec for each tab and add it to the TabHost
			spec = tabHost
					.newTabSpec("income")
					.setIndicator("Dochody",
							res.getDrawable(R.drawable.tab_all))
					.setContent(intent);
			tabHost.addTab(spec);

			intent = new Intent().setClass(this, DetailedExpensesActivity.class);
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
}