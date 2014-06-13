package com.musurgio.greeksrhythms;

import java.util.ArrayList;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

public class ValueSpinner extends LinearLayout {
	public static final int NUMBER = 0;
	public static final int TEXT = 1;

	private int style = 0;
	private TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
	private int min = 0;
	private int max = 100;

	private ArrayList<String> valueString = new ArrayList<String>();
	private int value = 0;

	// ---
	Button incButton;
	Button decButton;
	LinearLayout llWheel;
	private WheelView valueView;

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
		this.setOrientation(LinearLayout.VERTICAL);

		reCalculateWidthAndCaption();

		decButton.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					touchSet(0);
				}
				return false;
			}
		});
		incButton.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					touchSet(1);
				}
				return false;
			}
		});

	}

	private WheelView initWheel() {
		WheelView wheel = new WheelView(getContext());
		SpeedAdapter speedAdapter = new SpeedAdapter(getContext(), 245, 5);
		wheel.setViewAdapter(speedAdapter);
		wheel.setCurrentItem((int) (Math.random() * 10));
		wheel.setVisibleItems(1);
		// wheel.addChangingListener(changedListener);
		// wheel.addScrollingListener(scrolledListener);
		wheel.setCyclic(true);
		wheel.setInterpolator(new AnticipateOvershootInterpolator());
		return wheel;
	}

	/**
	 * Returns wheel by Id
	 * 
	 * @param id
	 *            the wheel Id
	 * @return the wheel with passed Id
	 */
	private WheelView getWheel(int id) {
		return (WheelView) findViewById(id);
	}

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

	private String getValueString() {
		String ret = "";
		switch (style) {
		case NUMBER:
			ret = String.valueOf(value);
			break;
		case TEXT:
			try {
				ret = valueString.get(value) == null ? "" : valueString
						.get(value);
			} catch (Exception e) {
				ret = "";
			}

			break;
		}
		return ret;
	}

	public int getStyle() {
		return style;
	}

	private void reCalculateWidthAndCaption() {
		MarginLayoutParams lp = (MarginLayoutParams) valueView
				.getLayoutParams();
		lp.width = (int) StaticLayout.getDesiredWidth(" WWW ", paint);
		if (valueString.size() > 0) {
			String s = "";
			for (int i = 0; i < valueString.size(); i++) {
				s = s.length() > valueString.get(i).length() ? s : valueString
						.get(i);
			}
			int w = (int) StaticLayout.getDesiredWidth(s, paint);
			lp.width = w > lp.width ? w : lp.width;
		}
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

	public void setStyle(int style) {
		this.style = style;
		reCalculateWidthAndCaption();
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
		value = value < min ? min : value;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
		value = value > max ? max : value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setValueString(String[] valueString) {
		if (valueString == null)
			return;
		if (valueString.length == 0)
			return;
		this.valueString.clear();
		for (int i = 0; i < valueString.length; i++)
			this.valueString.add(valueString[i]);

		this.min = 0;
		this.max = this.valueString.size() - 1;
		this.value = 0;
		this.style = TEXT;
		reCalculateWidthAndCaption();
	}

	public void setValueString(ArrayList<String> valueString) {
		if (valueString == null)
			return;
		if (valueString.size() == 0)
			return;
		this.valueString = valueString;

		this.min = 0;
		this.max = this.valueString.size() - 1;
		this.value = 0;
		this.style = TEXT;
		reCalculateWidthAndCaption();
	}

	private class SpeedAdapter extends NumericWheelAdapter {
		// Items step value
		private int step;

		/**
		 * Constructor
		 */
		public SpeedAdapter(Context context, int maxValue, int step) {
			super(context, 0, maxValue / step);
			this.step = step;

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
				int value = index * step;
				return Integer.toString(value);
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

}