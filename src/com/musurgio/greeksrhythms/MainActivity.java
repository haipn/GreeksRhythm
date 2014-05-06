package com.musurgio.greeksrhythms;

import com.musurgio.greeksrhythms.R;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	ValueSpinner spn1;
	ValueSpinner spn2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		spn1 = (ValueSpinner) findViewById(R.id.valueSpinner1);
		spn1.setNextBackgroundResource(R.drawable.btn_down);
		spn1.setPrevBackgroundResource(R.drawable.btn_up);
		spn2 = (ValueSpinner) findViewById(R.id.valueSpinner2);
		spn2.setNextBackgroundResource(R.drawable.btn_down);
		spn2.setPrevBackgroundResource(R.drawable.btn_up);
	}
}
