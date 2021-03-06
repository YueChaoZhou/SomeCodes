package com.ist.flyframeview.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.LinearInterpolator;

import com.ist.flyframeview.R;

public class FlyFrameView extends View {
    String TAG = getClass().getSimpleName();
    private ViewParent mRoot;
    private long mDuration = 250;

    public FlyFrameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setBackground(getResources().getDrawable(R.drawable.bg_fly_view));
    }

    public void moveToTarget(View targetView) {
        int leftDiff,topDiff;
        if (true) {//这种方式需要考虑标题栏是否存在
            int[] dxy = useCoordinateOnScreen(targetView);
            leftDiff = dxy[0];
            topDiff = dxy[1];//如果标题栏存在，需要减去标题栏高度
            Log.e(TAG, "after moveToTarget leftDiff: " + leftDiff + ",topDiff: " + topDiff);
        }else{
            Rect currentRect = getCoorRelativeToRootView(this);
            Rect targetRect = getCoorRelativeToRootView(targetView);
            leftDiff = targetRect.left - currentRect.left;
            topDiff = targetRect.top - currentRect.top;
            Log.e(TAG, "moveToTarget leftDiff: " + leftDiff + ",topDiff: " + topDiff);
        }

        //改变大小
        if (getWidth() != targetView.getWidth() || getHeight() != targetView.getHeight()) {
            changeSize(targetView.getWidth(), targetView.getHeight());
        }
        //改变位置
        if (leftDiff != 0 || topDiff != 0) {
            changeLocation(leftDiff, topDiff);
        }
    }

    private int[] useCoordinateOnScreen(View targetView) {
        int[] targetCoordinateOnScreen = getCoordinateOnScreen(targetView);
//        Log.e(TAG, "testUseCoordinateOnScreen dx: " + (targetCoordinateOnScreen[0] - currentCoordinateOnScreen[0]));
//        Log.e(TAG, "testUseCoordinateOnScreen dy: " + (targetCoordinateOnScreen[1] - currentCoordinateOnScreen[1]));
        return targetCoordinateOnScreen;
    }

    /**
     * 位移动画
     *
     * @param leftDiff X轴偏移量
     * @param topDiff  Y轴偏移量
     */
    private void changeLocation(int leftDiff, int topDiff) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(this, "translationX", leftDiff);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(this, "translationY", topDiff);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(mDuration);
        animatorSet.playTogether(translationX, translationY);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }

    private void changeSize(int width, final int height) {
        final ViewGroup.LayoutParams params = getLayoutParams();
        final int currentHeight = params.height;
        ValueAnimator widthAnimator = ValueAnimator.ofInt(getWidth(), width);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                params.width = value;

                //根据百分比改变高度
                float fraction = animation.getAnimatedFraction();
                params.height = (int) (currentHeight + (height - currentHeight) * fraction);

                setLayoutParams(params);
            }
        });
        widthAnimator.setDuration(mDuration);
        widthAnimator.setInterpolator(new LinearInterpolator());
        widthAnimator.start();
    }


    /**
     * 用于获取目标控件相对（同一个）父容器的坐标
     *
     * @param target 目标
     * @return
     */
    private Rect getCoorRelativeToRootView(View target) {
        Rect rect = new Rect();
        mRoot = getParent();
        ((ViewGroup) mRoot).offsetDescendantRectToMyCoords(target, rect);
//        Log.e(TAG, "getCoor: " + rect);
        return rect;
    }

    /**
     * 使用view位于屏幕上的坐标，注意标题栏是否存在
     *
     * @param target
     * @return
     */
    private int[] getCoordinateOnScreen(View target) {
        int[] coordinates = new int[2];
        target.getLocationOnScreen(coordinates);
//        Log.e(TAG, "getCoordinateOnScreen left: " + coordinates[0] + ",top: " + coordinates[1]);
        return coordinates;
    }
}
