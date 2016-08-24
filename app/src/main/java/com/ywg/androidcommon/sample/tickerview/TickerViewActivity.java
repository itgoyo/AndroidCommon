package com.ywg.androidcommon.sample.TickerView;

import android.os.Bundle;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.TickerView.TickerUtils;
import com.ywg.androidcommon.widget.TickerView.TickerView;

import java.util.Random;

public class TickerViewActivity extends BaseActivity {

    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    private static final char[] CURRENCY_LIST = TickerUtils.getDefaultListForUSCurrency();
    private char[] alphabetlist;

    private TickerView ticker1, ticker2, ticker3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker_view);

        alphabetlist = new char[53];
        alphabetlist[0] = TickerUtils.EMPTY_CHAR;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 26; j++) {
                // Add all lowercase characters first, then add the uppercase characters.
                alphabetlist[1 + i * 26 + j] = (char) ((i == 0) ? j + 97 : j + 65);
            }
        }

        ticker1 = (TickerView) findViewById(R.id.ticker1);
        ticker2 = (TickerView) findViewById(R.id.ticker2);
        ticker3 = (TickerView) findViewById(R.id.ticker3);

        ticker1.setCharacterList(NUMBER_LIST);
        ticker2.setCharacterList(CURRENCY_LIST);
        ticker3.setCharacterList(alphabetlist);

        ticker1.setAnimationDuration(500);
        ticker2.setAnimationDuration(500);
        ticker3.setAnimationDuration(500);

      /*  findViewById(R.id.perfBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TickerViewActivity.this, TickerViewActivity.class));
            }
        });*/
    }

    @Override
    protected void onUpdate() {
        final int digits = RANDOM.nextInt(2) + 6;

        ticker1.setText(getRandomNumber(digits));
        ticker2.setText("$" + Float.toString(RANDOM.nextFloat()).substring(0, digits));
        ticker3.setText(generateChars(RANDOM, alphabetlist, digits));
    }

    private String generateChars(Random random, char[] list, int numDigits) {
        final char[] result = new char[numDigits];
        for (int i = 0; i < numDigits; i++) {
            result[i] = list[random.nextInt(list.length)];
        }
        return new String(result);
    }
}
