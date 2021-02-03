package com.poly.GuitarAssistant.GuitarChord;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GuitarChordView extends FrameLayout {

    GuitarFretView guitarFretView;
    GuitarPajiView guitarPajiView;
    boolean init = true;

    public GuitarChordView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GuitarChordView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public GuitarChordView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(init) {
            guitarFretView = (GuitarFretView) this.getChildAt(1);
            guitarPajiView = (GuitarPajiView) this.getChildAt(2);
        }
    }

    public  void setLineDirection(Boolean direction){
        guitarPajiView.setLineDirection(direction);
    }

    public void GuitarPajiChanged(int paji, int line){
        //기타 파지 변경
        int p[] = {0,0,0,0};
        boolean l[] = {false, false, false, false, false, false,};
        int pow = 1000000000,
            pow2 = 10000;
        boolean first = true;

        if(guitarPajiView != null && guitarFretView != null) {
            //프렛 파지 디코드
            for (int c = 0; c < 4; c++) {
                int c1 = paji % pow / (pow / 100),
                    c2 = line % pow2 / (pow2 / 10) ;
                if (c1 != 99) {
                    if (first) {        //전체프랫
                        guitarFretView.setFret(paji%10*10 + c2);
                        first = false;
                    } else               //파지 프렛
                        p[c] += c2 * 100;
                    p[c] += c1;         //파지 대입
                }
                pow /= 100;
                pow2 /= 10;
            }

            // 라인 디코드
            pow = 1000000000;
            if(line / pow == 1)
                l[0] = true;
            for (int c = 1; c < 6; c++) {
                if (line % pow / (pow / 10) == 1)
                    l[c] = true;
                pow /= 10;
            }
            guitarPajiView.setPaji(p, l);
        }
    }


}
