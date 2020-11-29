package com.ist.fishtoucher.test.activity;

import android.widget.SeekBar;

import com.ist.fishtoucher.view.BScrollerControl;

public class BScrollerControlTester implements SeekBar.OnSeekBarChangeListener {

    private final BScrollerControl mScrollerControl;

    public BScrollerControlTester(BScrollerControl scrollerControl) {
        mScrollerControl = scrollerControl;
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mScrollerControl.setFraction(1f * progress / seekBar.getMax());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}