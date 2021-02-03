package com.poly.GuitarAssistant.GuitarChord;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poly.GuitarAssistant.R;

public class GuitarFretView extends HorizontalScrollView {

    boolean inti = true;
    int fretBetween, fretWidth;

    public GuitarFretView(Context context) {
        super(context);
    }

    public GuitarFretView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GuitarFretView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(inti){
            fretWidth = (int) getResources().getDimension(R.dimen.GuitarFretWidth);
            //프렛간 거리 초기화
            Resources res = getResources();
            fretBetween = (int) ((this.getWidth() - res.getDimension(R.dimen.GuitarFretSideMargin)*2 - res.getDimension(R.dimen.GuitarFretWidth)*5 )/4);

            LinearLayout linearLayout = (LinearLayout) this.getChildAt(0);
            int tvCount = (linearLayout.getChildCount()-3)/2;
            int count = 2;

            for(int c=0; c<tvCount; c++){
                TextView tv = (TextView) linearLayout.getChildAt(count);
                ViewGroup.LayoutParams params =  tv.getLayoutParams();
                params.width = fretBetween;
                tv.setLayoutParams(params);
                count += 2;
            }
            inti = false;
        }
    }

    public  void setFret(int fret){
        if(fret > 21)
            fret = 21;
        smoothScrollTo((fret-1)*(fretWidth + fretBetween),0);
    }

    //스크롤 제한
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

}
