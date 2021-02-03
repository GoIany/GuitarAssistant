package com.poly.GuitarAssistant.GuitarChord;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.poly.GuitarAssistant.R;

public class PajiSelecter extends FrameLayout {

    public static final boolean main = true,
                                side = false;

    private PajiSelected mPajiSelected = null;
    String mainChord = "C",
            sideChord = "Major";
    boolean isFirst =true;
    ImageButton leftBtn,rightBtn;
    LinearLayout pajiLinear;
    ImageView[] paji = new ImageView[10];
    Resources res = getResources();
    int nowPaji = 0;        //0 ~ postPajiNum-1


    public PajiSelecter(@NonNull Context context) {
        super(context);
    }

    public PajiSelecter(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PajiSelecter(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    interface PajiSelected{
        void pajiSelected(int paji, int line);
    }

    public void setPajiSelected(PajiSelected p){
        mPajiSelected = p;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(isFirst) {
            leftBtn = findViewById(R.id.paji_left_btn);             //레이아웃 객체 가져오기
            rightBtn = findViewById(R.id.paji_right_btn);
            pajiLinear = findViewById(R.id.paji_linear);
            //파지 변경
           Button.OnClickListener pajiPtnListener = new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(0 <= nowPaji && nowPaji < postPajiNum) {
                        paji[nowPaji].setImageResource(R.drawable.paji_img);

                        switch (v.getId()) {
                            case R.id.paji_left_btn:
                                if(0 < nowPaji) {
                                    nowPaji--;
                                }
                                break;
                            case R.id.paji_right_btn:
                                if(nowPaji < postPajiNum-1)
                                nowPaji++;
                                break;
                        }
                        paji[nowPaji].setImageResource(R.drawable.paji_selected_img);
                        Log.d("태그 ", "main: "+ mainChord +"/ sid: "+ sideChord +"/ nowpaji: "+nowPaji  );
                        Log.d("태그", "CMajor1: " + res.getIdentifier("CMajor1","integer","com.poly.GuitarAssistant"));
                    mPajiSelected.pajiSelected(

                            res.getInteger(res.getIdentifier(mainChord + sideChord + nowPaji,"integer","com.poly.GuitarAssistant") ),
                            res.getInteger(res.getIdentifier(mainChord + sideChord + nowPaji +"Line","integer","com.poly.GuitarAssistant") ));       //콜백 호출
                    }
                }
           };       //파지 변경 끝

            leftBtn.setOnClickListener(pajiPtnListener);
            rightBtn.setOnClickListener(pajiPtnListener);

            for (int c = 0; c < pajiLinear.getChildCount(); c++) {
                paji[c] = (ImageView) pajiLinear.getChildAt(c);
            }
            //pajiNumChange(8);       //파지 갯수 초기화
            chordChanged(mainChord,main);     //기타코드뷰 초기화

            isFirst = false;
        }
    }

    public void chordChanged(String chord, boolean type){
        if(type){               //메인 코드
            switch (chord){          //특수문자 제거
                case "C#":
                    mainChord = "Cs";
                    break;
                case "D#":
                    mainChord = "Ds";
                    break;
                case "F#":
                    mainChord = "Fs";
                    break;
                case "G#":
                    mainChord = "Gs";
                    break;
                case "A#":
                    mainChord = "As";
                    break;
                default:
                    mainChord = chord;
                    break;
            }
        }else{                  //사이드 코드
            switch (chord){           //특수문자 제거
                case "maj7/9":
                    sideChord = "maj7p9";
                    break;
                case "m(maj7)":
                    sideChord = "mmaj7";
                    break;
                case "7#5":
                    sideChord = "7s5";
                    break;
                case "6/9":
                    sideChord = "6p9";
                    break;
                case "7b5":
                    sideChord = "7b5";
                    break;
                case "7b9":
                    sideChord = "7b9";
                    break;
                default:
                    sideChord = chord;
                    break;
            }
        }
        pajiNumChange(res.getInteger(res.getIdentifier(mainChord + sideChord +"Num","integer","com.poly.GuitarAssistant") ) );
        mPajiSelected.pajiSelected(
                res.getInteger(res.getIdentifier(mainChord + sideChord + nowPaji,"integer","com.poly.GuitarAssistant") ),
                res.getInteger(res.getIdentifier(mainChord + sideChord + nowPaji+"Line","integer","com.poly.GuitarAssistant") ) );       //콜백 호출
    }


    int postPajiNum = 10;       //1~10

    public void pajiNumChange(int pajiNum){
        if(postPajiNum != pajiNum){
            if(pajiNum-postPajiNum > 0) {     //갯수증가
                for(int c=postPajiNum; c < pajiNum; c++) {
                    pajiLinear.addView(paji[c]);
                }
            }else{                              //갯수 감소
                for(int c=pajiNum; c < postPajiNum; c++) {
                    pajiLinear.removeView(paji[c]);
                }
            }
            postPajiNum = pajiNum;
        }
        paji[nowPaji].setImageResource(R.drawable.paji_img);
        paji[0].setImageResource(R.drawable.paji_selected_img);
        nowPaji = 0;
    }

}
