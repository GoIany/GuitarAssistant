package com.poly.GuitarAssistant.GuitarChord;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GuitarChordSelecter extends HorizontalScrollView {

    int postposition;
    int itemCount , nowTv = 1, postTv = 1, sendedTv = -1;
    int scrollWidth, tvWidth;
    float bigTextSize, smallTextSize;
    boolean firstLayout = true,
            setSwitch = false;
    TextView textView[];
    private chordChanged mChordChanged = null;
    String setChord ="";


    interface chordChanged {
        void chordChange(String code);
    }

    public void setChordChanged(chordChanged cc){
        mChordChanged = cc;
    }

    public String getChord(){
        return textView[nowTv-1].getText().toString();
    }

    public void setChord(String chord){
        setChord = chord;
        if(textView != null) {
            setSwitch = false;
            for (int c = 0; c < textView.length; c++) {
                if (setChord.equals(textView[c].getText()))
                    scrollTo((2 * (c + 1) - 1) * tvWidth / 2, 0);
            }
        }else{
            setSwitch = true;
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int nowPosition = getScrollX();
            if(postposition == nowPosition){
                if(mChordChanged != null)
                    if(sendedTv != nowTv)
                        mChordChanged.chordChange(textView[nowTv-1].getText().toString());
                sendedTv = nowTv;
                smoothScrollTo((2*nowTv - 1)*tvWidth/2 ,0);
            }else{
                postposition = nowPosition;
                GuitarChordSelecter.this.postDelayed(runnable,100);
            }
        }
    };

    public GuitarChordSelecter(Context context) {
        super(context);
        init(context);
    }

    public GuitarChordSelecter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GuitarChordSelecter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        scrollWidth = dm.widthPixels;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if(ev.getAction() == MotionEvent.ACTION_UP){
            postposition = getScrollX();
            GuitarChordSelecter.this.postDelayed(runnable,100);
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if(firstLayout) {                                                           //레이아웃 초기화
            LinearLayout linearLayout = (LinearLayout) this.getChildAt(0);      //구성 택스트뷰 갯수 가져오기
            itemCount = linearLayout.getChildCount() - 2;

            TextView emptyTv = (TextView) linearLayout.getChildAt(0);       //양 끝 빈 텍스트뷰 너비 초기화
            emptyTv.setWidth(scrollWidth/2);
            emptyTv = (TextView) linearLayout.getChildAt(itemCount+1);
            emptyTv.setWidth(scrollWidth/2);

            textView = new TextView[itemCount];         //모든 택스트뷰 객체 생성
            for (int c = 0; c < itemCount; c++) {
                textView[c] = (TextView) linearLayout.getChildAt(c+1);
                textView[c].setHeight(textView[c].getHeight());                         //뷰 높이 고정
                //텍스트뷰 클릭시 해당 텍스트뷰로 이동
                final int count = c+1;

                textView[c].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        smoothScrollTo((2*count - 1)*tvWidth/2 ,0);
                        mChordChanged.chordChange(textView[count-1].getText().toString());
                    }
                });

                if (c == 0) {
                    bigTextSize = textView[c].getTextSize();
                    smallTextSize = bigTextSize * 7 / 10;
                    tvWidth =textView[c].getWidth();
                } else {
                    textView[c].setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    textView[c].setTextColor(Color.argb(128, 0, 0, 0));
                }
            }
            scrollTo(tvWidth/2, 0);                     //초기 위치로 이동
            firstLayout = false;
        }
        if(setSwitch)
            setChord(setChord);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        postTv = nowTv;                                                             //현재 텍스트 위치 구하기
        if(x/tvWidth >= itemCount)
            nowTv = itemCount;
        else
            nowTv = x/tvWidth + 1;
        int distanceToMid = Math.abs( (2*nowTv-1)*tvWidth/2-x );

        if(textView != null) {                                                          //텍스트 위치에 따른 크기, 색 변경
            textView[nowTv - 1].setTextColor(Color.argb(255 - distanceToMid * 255 / tvWidth, 0, 0, 0));
            textView[nowTv - 1].setTextSize(TypedValue.COMPLEX_UNIT_PX, bigTextSize - distanceToMid*(bigTextSize - smallTextSize) * 2 / tvWidth);

            if(nowTv != postTv){                                                        //뷰 지나갔을시 크기 색 변경
                textView[postTv - 1].setTextColor(Color.argb(128, 0, 0, 0));
                textView[postTv - 1].setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
            }
        }

    }

}
