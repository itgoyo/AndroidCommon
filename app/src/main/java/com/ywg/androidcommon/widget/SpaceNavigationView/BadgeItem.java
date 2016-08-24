package com.ywg.androidcommon.widget.SpaceNavigationView;

import java.io.Serializable;

/**
 * Created by Chatikyan on 17.08.2016-18:07.
 */

class BadgeItem implements Serializable {

    private int badgeIndex;

    private int badgeText;

    private int badgeColor;

    BadgeItem(int badgeIndex, int badgeText, int badgeColor) {
        this.badgeIndex = badgeIndex;
        this.badgeText = badgeText;
        this.badgeColor = badgeColor;
    }

    int getBadgeIndex() {
        return badgeIndex;
    }

    int getBadgeColor() {
        return badgeColor;
    }

    int getIntBadgeText(){
        return badgeText;
    }

    String getBadgeText() {
        String badgeStringText;
        if (badgeText > 9)
            badgeStringText = 9 + "+";
        else
            badgeStringText = String.valueOf(badgeText);
        return badgeStringText;
    }
}