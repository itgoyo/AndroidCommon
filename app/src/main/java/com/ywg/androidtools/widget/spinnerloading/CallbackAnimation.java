package com.ywg.androidtools.widget.spinnerloading;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CallbackAnimation extends Animation {
    public interface TransformationListener {
        void onApplyTrans(float interpolatedTime);
    }

    private TransformationListener mListener;

    public CallbackAnimation(TransformationListener listener) {
        mListener = listener;
        if (listener == null) {
            mListener = listener;
        }
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        mListener.onApplyTrans(interpolatedTime);
    }

    public void setListener(TransformationListener listener) {
        if (listener == null) {
            return;
        }
        mListener = listener;
    }
}