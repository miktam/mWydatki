package com.developand.mwydatki;

import com.developand.mwydatki.tools.ToastMaker;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

public class Main extends TabActivity {
	private static final String TAG = Main.class.getName();

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		try {

			// Create an Intent to launch an Activity for the tab (to be reused)
			intent = new Intent().setClass(this, AllExpensesView.class);

			// Initialize a TabSpec for each tab and add it to the TabHost
			spec = tabHost
					.newTabSpec("all")
					.setIndicator("Wszystko",
							res.getDrawable(R.drawable.tab_all))
					.setContent(intent);
			tabHost.addTab(spec);

			intent = new Intent().setClass(this, DetailedBillsView.class);
			spec = tabHost
					.newTabSpec("minus")
					.setIndicator("Pogrupowane",
							res.getDrawable(R.drawable.tab_detailes))
					.setContent(intent);
			tabHost.addTab(spec);

			tabHost.setCurrentTab(0);
		} catch (Exception e) {
			Log.w(TAG, "really bad: " + e.getMessage());
			ToastMaker.getToast(this, "File is corrupted?");
			this.finish();
		}
	}
}