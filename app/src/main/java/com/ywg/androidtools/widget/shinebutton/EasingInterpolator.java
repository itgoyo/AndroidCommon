package com.ywg.androidtools.widget.shinebutton;

import android.animation.TimeInterpolator;
import android.support.annotation.NonNull;

public class EasingInterpolator implements TimeInterpolator {

    private final Ease ease;

    public EasingInterpolator(@NonNull Ease ease) {
        this.ease = ease;
    }

    @Override
    public float getInterpolation(float input) {
        return EasingProvider.get(this.ease, input);
    }

    public Ease getEase() {
        return ease;
    }
}