package com.developand.mwydatki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class Main extends Activity {
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		
		Intent prodigy = new Intent(this, BillsView.class);
		this.startActivity(prodigy);

//		Resources res = getResources(); // Resource object to get Drawables
//		TabHost tabHost = getTabHost(); // The activity TabHost
//		TabHost.TabSpec spec; // Resusable TabSpec for each tab
//		Intent intent; // Reusable Intent for each tab
//
//		// Create an Intent to launch an Activity for the tab (to be reused)
//		intent = new Intent().setClass(this, AllExpensesView.class);
//
//		// Initialize a TabSpec for each tab and add it to the TabHost
//		spec = tabHost.newTabSpec("all").setIndicator("All", null)
//				.setContent(intent);
//		tabHost.addTab(spec);
//
//		// Do the same for the other tabs
//		intent = new Intent().setClass(this, IncomeView.class);
//		spec = tabHost.newTabSpec("plus").setIndicator("Dochody", null)
//				.setContent(intent);
//		tabHost.addTab(spec);
//
//		intent = new Intent().setClass(this, BillsView.class);
//		spec = tabHost.newTabSpec("minus").setIndicator("Wydatki", null)
//				.setContent(intent);
//		tabHost.addTab(spec);
//
//		tabHost.setCurrentTab(1);
	}
}