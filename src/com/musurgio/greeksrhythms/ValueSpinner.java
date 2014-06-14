package com.musurgio.greeksrhythms;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ValueSpinner extends LinearLayout {

	private TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
	private int mMinValue;
	private int mMaxValue;
	private int mCurrentValue = 0;
	private int mIdRhythm;
	private String mDisplayName;
	private String mFileName;
	Button incButton;
	Button decButton;
	TextView mTvName;

	private WheelView valueView;

	private boolean mIsSelect;

	public interface OnRhythmClickListener {
		public void onRhythmClick(int id, String fileName);
	}
	
	private OnRhythmClickListener mCallBack;
	public void setCallBack(OnRhythmClickListener mCallBack) {
		this.mCallBack = mCallBack;
	}

	public ValueSpinner(Context context) {
		super(context);
		init();
	}

	public ValueSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public ValueSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {

		decButton = new Button(getContext());
		incButton = new Button(getContext());
		this.addView(decButton, new MarginLayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		valueView = initWheel();
		this.addView(valueView, new MarginLayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(incButton, new MarginLayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mTvName = new TextView(getContext());
		this.addView(mTvName, new MarginLayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		this.setOrientation(LinearLayout.VERTICAL);
		this.setGravity(Gravity.CENTER_HORIZONTAL);
		reCalculateWidthAndCaption();

		decButton.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					touchSet(0);
				}
				mCallBack.onRhythmClick(mIdRhythm, "");
				return false;
			}
		});
		incButton.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					touchSet(1);
				}
				mCallBack.onRhythmClick(mIdRhythm, "");
				return false;
			}
		});
		this.setFocusable(true);
		;
		this.setFocusableInTouchMode(true);
		this.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				Log.d("haipn", "focus change: " + arg1);
				if (arg1)
					ValueSpinner.this.setBackgroundColor(getContext()
							.getResources().getColor(R.color.red));
				else
					ValueSpinner.this.setBackgroundColor(getContext()
							.getResources().getColor(android.R.color.white));
			}
		});
	}

	private WheelView initWheel() {
		WheelView wheel = new WheelView(getContext());
		wheel.setOnTouchListener(null);
		wheel.addChangingListener(changedListener);
		wheel.setCyclic(true);
		wheel.setInterpolator(new AnticipateOvershootInterpolator());
		return wheel;
	}

	// Wheel scrolled listener
	// Wheel changed listener
	private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			// if (!wheelScrolled) {
			SpeedAdapter adapter = (SpeedAdapter) wheel.getViewAdapter();
			Log.d("haipn", "new value:" + (newValue * 2 + adapter.min)
					+ " oldValue:" + (oldValue * 2 + adapter.min));
			// }
		}
	};

	protected void touchSet(int i) {
		int p = i ^ getOrientation();
		switch (p) {
		case 0:
			valueView.setCurrentItem(valueView.getCurrentItem() - 1, true);
			break;
		case 1:
			valueView.setCurrentItem(valueView.getCurrentItem() + 1, true);
			break;
		}
		invalidate();
	}

	@Override
	public void setOrientation(int orientation) {
		super.setOrientation(orientation);
		reCalculateWidthAndCaption();
	}

	private void reCalculateWidthAndCaption() {
		MarginLayoutParams lp = (MarginLayoutParams) valueView
				.getLayoutParams();
		lp.width = (int) StaticLayout.getDesiredWidth(" WWW ", paint);
		switch (getOrientation()) {
		case LinearLayout.HORIZONTAL:
			lp.width += 10;
			lp.leftMargin = 5;
			lp.rightMargin = 5;
			break;
		case LinearLayout.VERTICAL:
			lp.height = 100;
			lp.width = MarginLayoutParams.MATCH_PARENT;
			lp.topMargin = 5;
			lp.bottomMargin = 5;
			break;
		}

	}

	public int getMin() {
		return mMinValue;
	}

	public void setMinMax(int min, int max) {
		this.mMinValue = min;
		this.mMaxValue = max;
		mCurrentValue = mCurrentValue < min ? min : mCurrentValue;
		mCurrentValue = mCurrentValue > max ? max : mCurrentValue;
		SpeedAdapter speedAdapter = new SpeedAdapter(getContext(), max, min, 2);
		valueView.setViewAdapter(speedAdapter);
		valueView.setCurrentItem(0);
		valueView.setVisibleItems(1);
	}

	public int getMax() {
		return mMaxValue;
	}

	public int getValue() {
		return mCurrentValue;
	}

	public void setValue(int value) {
		this.mCurrentValue = value;
	}

	// public void setValueString(String[] valueString) {
	// if (valueString == null)
	// return;
	// if (valueString.length == 0)
	// return;
	// this.valueString.clear();
	// for (int i = 0; i < valueString.length; i++)
	// this.valueString.add(valueString[i]);
	//
	// this.min = 0;
	// this.max = this.valueString.size() - 1;
	// this.value = 0;
	// this.style = TEXT;
	// reCalculateWidthAndCaption();
	// }

	// public void setValueString(ArrayList<String> valueString) {
	// if (valueString == null)
	// return;
	// if (valueString.size() == 0)
	// return;
	// this.valueString = valueString;
	//
	// this.min = 0;
	// this.max = this.valueString.size() - 1;
	// this.value = 0;
	// this.style = TEXT;
	// reCalculateWidthAndCaption();
	// }

	private class SpeedAdapter extends NumericWheelAdapter {
		// Items step value
		private int step;
		private int min;

		/**
		 * Constructor
		 */
		public SpeedAdapter(Context context, int maxValue, int minValue,
				int step) {
			super(context, 0, maxValue / step - minValue / step);
			this.step = step;
			this.min = minValue;
			setItemResource(R.layout.wheel_text_item);
			setItemTextResource(R.id.text);
		}

		/**
		 * Sets units
		 */
		public void setUnits(String units) {
			// this.units = units;
		}

		@Override
		public CharSequence getItemText(int index) {
			if (index >= 0 && index < getItemsCount()) {
				mCurrentValue = index * step + min;
				return Integer.toString(mCurrentValue);
			}
			return null;
		}
	}

	public void setNextBackgroundResource(int background) {
		incButton.setBackgroundResource(background);
		// MarginLayoutParams lp = (MarginLayoutParams) incButton
		// .getLayoutParams();
		// incButton.setText("");
		// lp.width = 50;
		// lp.height = 50;
	}

	public void setPrevBackgroundResource(int background) {
		decButton.setBackgroundResource(background);
		// MarginLayoutParams lp = (MarginLayoutParams) decButton
		// .getLayoutParams();
		// decButton.setText("");
		// lp.width = 50;
		// lp.height = 50;
	}

	public String getDisplayName() {
		return mDisplayName;
	}

	public void setDisplayName(String mDisplayName) {
		this.mDisplayName = mDisplayName;
		mTvName.setText(mDisplayName);
	}

	public String getFileName() {
		return mFileName;
	}

	public void setFileName(String mFileName) {
		this.mFileName = mFileName;
	}

	public int getIdRhythm() {
		return mIdRhythm;
	}

	public void setIdRhythm(int mIdRhythm) {
		this.mIdRhythm = mIdRhythm;
	}

	public boolean mIsSelect() {
		return mIsSelect;
	}

	public void setIsSelect(boolean mIsSelect) {
		this.mIsSelect = mIsSelect;
	}

}