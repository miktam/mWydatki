package com.developand.mwydatki;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeDialog extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Dialog dialog = new Dialog(WelcomeDialog.this);
		dialog.setContentView(R.layout.dialog);
		dialog.setTitle("mWydatki - instrukcja obslugi");
		dialog.setCancelable(true);
		TextView text = (TextView) dialog.findViewById(R.id.textViewDialog);
		text.setText(R.string.howto);

		// open mail or cancel
		Button button = (Button) dialog.findViewById(R.id.openMail);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		dialog.show();
	}
}
