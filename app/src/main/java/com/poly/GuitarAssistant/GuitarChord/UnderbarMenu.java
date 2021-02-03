package com.poly.GuitarAssistant.GuitarChord;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;

import com.poly.GuitarAssistant.R;

public class UnderbarMenu extends LinearLayout {

    Activity activity;
    Context context;
    LinearLayout linearLayout;
    ImageButton[] actButton = new ImageButton[3];
    int actNum = 0;
    ViewFlipper viewFlipper;

    public UnderbarMenu(Context context) {
        super(context);
        init(context);
    }

    public UnderbarMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UnderbarMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) ( (LinearLayout) inflater.inflate(R.layout.underbar_manu,this) ).getChildAt(0);

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo runningTaskInfo = activityManager.getRunningTasks(1).get(0);
        String activityName = runningTaskInfo.topActivity.getClassName();
        if(activityName.equals("com.poly.GuitarAssistant.GuitarCode.MainActivity"))
            actNum = 0;
        if(activityName.equals("com.poly.GuitarAssistant.Lecture.tong1"))
            actNum = 1;
        if(activityName.equals("com.poly.GuitarAssistant.Instrument.Instrument"))
            actNum = 2;

        for(int c=0; c<linearLayout.getChildCount(); c++){
            actButton[c] = (ImageButton) linearLayout.getChildAt(c);
            actButton[c].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    switch (v.getId()){
                        case R.id.act1:
                            if(actNum != 0){
                                activity.startActivity(new Intent(activity,com.poly.GuitarAssistant.GuitarChord.MainActivity.class));
                                activity.overridePendingTransition(0,0);
                                activity.finish();}
                            break;
                        case R.id.act2:
                            if(actNum != 1){
                                activity.startActivity(new Intent(activity,com.poly.GuitarAssistant.Lecture.tong1.class));
                                activity.overridePendingTransition(0,0);
                                activity.finish();}
                            else{
                                if(viewFlipper.getDisplayedChild() == 1)
                                    viewFlipper.setDisplayedChild(0);
                            }
                            break;
                        case R.id.act3:
                            if(actNum != 2){
                                activity.startActivity(new Intent(activity,com.poly.GuitarAssistant.Instrument.Instrument.class));
                                activity.overridePendingTransition(0,0);
                                activity.finish();}
                            break;
                    }

                }
            });
        }

        Resources res = getResources();
        for (int c=0; c<linearLayout.getChildCount(); c++){
            if(c == actNum)
                actButton[c].setColorFilter(res.getColor(R.color.colorSelected));
            else
                actButton[c].setColorFilter(res.getColor(R.color.colorNotSelected));
        }

    }


    public void setActivity(Activity act){
        activity = act;
    }

    public void setFlipperAct2(ViewFlipper gviewFlipper){
        viewFlipper = gviewFlipper;
    }

}
