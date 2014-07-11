package com.musurgio.greeksrhythms;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;

import com.musurgio.greeksrhythms.ValueSpinner.OnRhythmClickListener;

public class MainActivity extends Activity implements OnRhythmClickListener {
	GridView mGvRhythm;

	ArrayList<ValueSpinner> mListRhythm;
	GridAdapter mAdapter;

	private MediaPlayer mPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		mGvRhythm = (GridView) findViewById(R.id.gvRhythm);
		mListRhythm = setListRhythm();
		mAdapter = new GridAdapter(getApplicationContext(), mListRhythm);

		mGvRhythm.setAdapter(mAdapter);
		mGvRhythm.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mListRhythm.get(arg2).setIsSelect(true);
				mAdapter.notifyDataSetChanged();
			}
		});
		mGvRhythm.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				mListRhythm.get(arg2).setIsSelect(true);
				mAdapter.notifyDataSetChanged();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		mPlayer = new MediaPlayer();
		// spn1 = (ValueSpinner) findViewById(R.id.valueSpinner1);
		// spn1.setNextBackgroundResource(R.drawable.btn_down);
		// spn1.setPrevBackgroundResource(R.drawable.btn_up);
		// spn1.setMinMax(20, 40);
		// spn2 = (ValueSpinner) findViewById(R.id.valueSpinner2);
		// spn2.setNextBackgroundResource(R.drawable.btn_down);
		// spn2.setPrevBackgroundResource(R.drawable.btn_up);
		// spn2.setMinMax(50, 60);
	}

	private ArrayList<ValueSpinner> setListRhythm() {
		ArrayList<ValueSpinner> ret = new ArrayList<ValueSpinner>();
		ValueSpinner rhythm1 = new ValueSpinner(this, true);
		rhythm1.setNextBackgroundResource(R.drawable.btn_down);
		rhythm1.setPrevBackgroundResource(R.drawable.btn_up);
		rhythm1.setMinMax(70, 90);
		rhythm1.setIdRhythm(0);
		rhythm1.setCallBack(this);
		rhythm1.setIsSelect(true);
		rhythm1.setFileName("Kamilieriko");
		rhythm1.setDisplayName(getString(R.string.kamilieriko));
		ret.add(rhythm1);

		ValueSpinner rhythm2 = new ValueSpinner(this, true);
		rhythm2.setNextBackgroundResource(R.drawable.btn_down);
		rhythm2.setPrevBackgroundResource(R.drawable.btn_up);
		rhythm2.setMinMax(90, 110);
		rhythm2.setIdRhythm(1);
		rhythm2.setCallBack(this);
		rhythm2.setFileName("Karsilamas");
		rhythm2.setDisplayName(getString(R.string.karsilamas));
		ret.add(rhythm2);

		ValueSpinner rhythm3 = new ValueSpinner(this, true);
		rhythm3.setNextBackgroundResource(R.drawable.btn_down);
		rhythm3.setPrevBackgroundResource(R.drawable.btn_up);
		rhythm3.setMinMax(110, 130);
		rhythm3.setIdRhythm(2);
		rhythm3.setCallBack(this);
		rhythm3.setFileName("Nisiotiko");
		rhythm3.setDisplayName(getString(R.string.nisiotiko));
		ret.add(rhythm3);

		ValueSpinner rhythm4 = new ValueSpinner(this, true);
		rhythm4.setNextBackgroundResource(R.drawable.btn_down);
		rhythm4.setPrevBackgroundResource(R.drawable.btn_up);
		rhythm4.setMinMax(80, 100);
		rhythm4.setIdRhythm(3);
		rhythm4.setCallBack(this);
		rhythm4.setFileName("rumba");
		rhythm4.setDisplayName(getString(R.string.rumba_greek));
		ret.add(rhythm4);

		ValueSpinner rhythm5 = new ValueSpinner(this, false);
		rhythm5.setNextBackgroundResource(R.drawable.btn_down);
		rhythm5.setPrevBackgroundResource(R.drawable.btn_up);
		rhythm5.setMinMax(80, 120);
		rhythm5.setIdRhythm(4);
		rhythm5.setCallBack(this);
		rhythm5.setFileName("sixeight");
		rhythm5.setDisplayName(getString(R.string.sixeight));
		ret.add(rhythm5);

		ValueSpinner rhythm6 = new ValueSpinner(this, false);
		rhythm6.setNextBackgroundResource(R.drawable.btn_down);
		rhythm6.setPrevBackgroundResource(R.drawable.btn_up);
		rhythm6.setMinMax(104, 130);
		rhythm6.setIdRhythm(5);
		rhythm6.setCallBack(this);
		rhythm6.setFileName("Tsifti");
		rhythm6.setDisplayName(getString(R.string.tsifteteli));
		ret.add(rhythm6);

		ValueSpinner rhythm7 = new ValueSpinner(this, false);
		rhythm7.setNextBackgroundResource(R.drawable.btn_down);
		rhythm7.setPrevBackgroundResource(R.drawable.btn_up);
		rhythm7.setMinMax(100, 130);
		rhythm7.setIdRhythm(6);
		rhythm7.setCallBack(this);
		rhythm7.setFileName("Valse");
		rhythm7.setDisplayName(getString(R.string.valse));
		ret.add(rhythm7);

		ValueSpinner rhythm8 = new ValueSpinner(this, false);
		rhythm8.setNextBackgroundResource(R.drawable.btn_down);
		rhythm8.setPrevBackgroundResource(R.drawable.btn_up);
		rhythm8.setMinMax(90, 120);
		rhythm8.setIdRhythm(7);
		rhythm8.setCallBack(this);
		rhythm8.setFileName("Xasapiko");
		rhythm8.setDisplayName(getString(R.string.xasapiko));
		ret.add(rhythm8);
		return ret;
	}

	@Override
	public void onRhythmClick(int id) {
		for (int i = 0; i < mListRhythm.size(); i++) {
			if (i == id)
				mListRhythm.get(i).setIsSelect(true);
			else
				mListRhythm.get(i).setIsSelect(false);
		}
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onRhythmChange(String filename) {
		playBeep(filename);
	}

	public void playBeep(String name) {
		try {

			if (mPlayer.isPlaying()) {
				mPlayer.stop();
				mPlayer.release();
				mPlayer = new MediaPlayer();
			}

			AssetFileDescriptor descriptor = getAssets().openFd(name);
			mPlayer.setDataSource(descriptor.getFileDescriptor(),
					descriptor.getStartOffset(), descriptor.getLength());
			descriptor.close();

			mPlayer.prepare();
			mPlayer.setLooping(true);
			mPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_stop) {
			for (int i = 0; i < mListRhythm.size(); i++) {
				mListRhythm.get(i).setIsSelect(false);
				mAdapter.notifyDataSetChanged();
				try {
					if (mPlayer != null && mPlayer.isPlaying()) {
						mPlayer.stop();
						mPlayer.release();
						mPlayer = new MediaPlayer();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
