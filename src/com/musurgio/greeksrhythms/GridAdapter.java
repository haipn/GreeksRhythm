package com.musurgio.greeksrhythms;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GridAdapter extends BaseAdapter {

	public GridAdapter(Context context, ArrayList<ValueSpinner> listRhythm) {
		super();
		this.mListRhythm = listRhythm;
		mContext = context;
	}


	private ArrayList<ValueSpinner> mListRhythm;
	private Context mContext;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListRhythm.size();
	}

	@Override
	public ValueSpinner getItem(int arg0) {
		// TODO Auto-generated method stub
		return mListRhythm.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ValueSpinner rhy = getItem(arg0);
		if (arg1 == null) {
			arg1 = rhy;
		}
		if (rhy.mIsSelect())
			arg1.setBackgroundColor(mContext.getResources().getColor(
					R.color.red));
		else
			arg1.setBackgroundColor(mContext.getResources().getColor(
					android.R.color.darker_gray));
		return arg1;
	}

}
