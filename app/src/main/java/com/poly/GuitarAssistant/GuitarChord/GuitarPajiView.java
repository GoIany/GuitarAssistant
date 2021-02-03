package com.poly.GuitarAssistant.GuitarChord;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.poly.GuitarAssistant.R;

public class GuitarPajiView extends View {

    public final static boolean up = false,  down = true;

    Paint linePnt = new Paint(),
          pajiPnt = new Paint(),
          textPnt = new Paint(),
          hitLinePnt = new Paint();
    boolean inti = true,
            lineDirection = up;
    float height=0, width=0,
            firstPaji,
            pajiBetween,
            lineBetween;
    int paji[] = {0,0,0,0};
    boolean line[] = {false, false, false, false, false, false,};



    public GuitarPajiView(Context context) {
        super(context);
        inti(context);
    }

    public GuitarPajiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inti(context);
    }

    public GuitarPajiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inti(context);
    }

    private void inti(Context context){
        linePnt.setColor(Color.BLACK);
        linePnt.setStrokeWidth(1f);
        linePnt.setStyle(Paint.Style.STROKE);

        hitLinePnt.setColor(Color.BLUE);
        hitLinePnt.setStrokeWidth(1f);
        hitLinePnt.setStyle(Paint.Style.STROKE);

        pajiPnt.setAntiAlias(true);
        textPnt.setAntiAlias(true);
        textPnt.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(inti) {
            height = this.getHeight();
            width = this.getWidth();

            Resources res = getResources();
            float sideMargin = res.getDimension(R.dimen.GuitarFretSideMargin),
                  fretWidth = res.getDimension(R.dimen.GuitarFretWidth),
                  fretBetween =((width - sideMargin*2 - fretWidth*5 )/4);
            firstPaji = sideMargin + fretWidth + fretBetween/2;
            pajiBetween = fretBetween + fretWidth;
            lineBetween = height/6;

            pajiPnt.setColor(res.getColor(R.color.colorPaji));
            textPnt.setTextSize(lineBetween*4/5);
            inti = false;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int strokex = 3;
        int hitLineFret[] = {-1,-1,-1,-1,-1,-1};      // -1 = 플렛 없음

        //라인 그리기
        for(int c=0; c < 6; c++){
            if(c%2 == 0){
                linePnt.setStrokeWidth(strokex--);
            }
            int cc = c;
            if(lineDirection)
                cc = 5 - c;
            canvas.drawLine(0,lineBetween*cc + lineBetween/2  ,width,lineBetween*cc + lineBetween/2,linePnt);
        }

        //파지 그리기
        for(int c=0; c<4; c++){
            int paji1 = paji[c]%100/10, //  1~6 L1
                paji2 = paji[c]%10,     //  1~6 L2
                fret = paji[c]/100;     //  0~3
            float pajiX = firstPaji + fret * pajiBetween;

            if(paji1 != 0) {
                if(lineDirection){          //라인 방향 적용
                    paji1 = 7 - paji1;
                    paji2 = 7 - paji2;
                }

                canvas.drawCircle(pajiX, lineBetween / 2 + (6 - paji1) * lineBetween, lineBetween / 2, pajiPnt);
                if (paji1 != paji2) {           //여러줄 잡을때
                    canvas.drawRect(pajiX - lineBetween / 2,lineBetween / 2 + (6 - paji2) * lineBetween,pajiX + lineBetween / 2,lineBetween / 2 + (6 - paji1) * lineBetween,pajiPnt);
                    canvas.drawCircle(pajiX, lineBetween / 2 + (6 - paji2) * lineBetween, lineBetween / 2, pajiPnt);
                }
                canvas.drawText(Integer.toString(c+1),firstPaji + fret * pajiBetween,lineBetween *2/7 + (6 - paji2) * lineBetween + lineBetween/2,textPnt);

                //쳐야할 줄 체크
                if(lineDirection){          //라인 방향 적용
                    paji1 = 7 - paji1;
                    paji2 = 7 - paji2;
                }
                for(int cc=-1; cc < paji2-paji1; cc++){
                    if(hitLineFret[paji1+cc] < fret)
                        hitLineFret[paji1+cc] = fret;
                }

            }
        }

        //처야할 줄 그리기
        strokex = 3;
        for(int c=0; c < 6; c++){
            if(c%2 == 0)
                hitLinePnt.setStrokeWidth(strokex--);

            if(line[5-c]) {
                int cc = c;
                if(lineDirection)
                    cc = 5 - c;
                canvas.drawLine(firstPaji + hitLineFret[5 - c] * pajiBetween + lineBetween / 2, lineBetween * cc + lineBetween / 2, width, lineBetween * cc + lineBetween / 2, hitLinePnt);
            }
        }

    }

    public void setPaji(int[] p, boolean[] l){
        paji = p;
        line = l;
        invalidate();
    }

    public void setLineDirection(Boolean direction){
        lineDirection = direction;
        invalidate();
    }


}
