package com.poly.GuitarAssistant.GuitarChord;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.poly.GuitarAssistant.R;

public class BtnChordSelecter extends LinearLayout{
    TextView chordTv;
    Boolean selcectingRoot = true;
    String rootChord = "C", typeChord = "maj", rootSide = "";
    final String mType[] = {"m", "m6", "mb6", "m7", "mmaj7", "m7b5", "m9", "m11","7", "7b5", "7#5", "7b9","maj", "maj7", "maj79", "6", "dim7", "9", "69", "11", "5", "dim"};
    LinearLayout linearLayout;
    ChordChanged chordChanged = null;

    interface ChordChanged{
        void chordChange(String chord, boolean type);
    }

    public void setChordChanged(ChordChanged c){
        chordChanged = c ;
    }

    public BtnChordSelecter(Context context) {
        super(context);
        init(context);
    }

    public BtnChordSelecter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BtnChordSelecter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) ( (LinearLayout) inflater.inflate(R.layout.btnselecter_layout,this) ).getChildAt(0);

        chordTv = (TextView) linearLayout.getChildAt(0);
        TableLayout tableLayout = (TableLayout) linearLayout.getChildAt(1);

        for(int c1=0; c1 < tableLayout.getChildCount(); c1++){
            TableRow tableRow = (TableRow) tableLayout.getChildAt(c1);
            for(int c2=0; c2 < tableRow.getChildCount(); c2++){
                Button button = (Button) tableRow.getChildAt(c2);
                String btnString = button.getText().toString();
                Drawable drawable = getResources().getDrawable(R.drawable.btn_root_round);
                View.OnClickListener onClickListener;

                if(btnString.equals("sus2/4") || btnString.equals("b/#")){
                    onClickListener = new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            specialSelected( (String) ((Button) v).getText() );
                        }
                    };
                }else if(drawable.getConstantState().equals(button.getBackground().getConstantState() ) ){
                    onClickListener = new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rootChordSelected( (String) ((Button) v).getText() );
                        }
                    };
                }else{
                    onClickListener = new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            typeChordSelected( (String) ((Button) v).getText() );
                        }
                    };
                }
                button.setOnClickListener(onClickListener);
            }
        }

    }

    public void setChord(String rootside,String type){
        if(rootside.endsWith("#"))
            rootSide = "#";
        rootChord = rootside.replace("#","");

        switch (type){
            case "Major":
                typeChord = "maj";
                break;
            case "maj7/9":
                typeChord = "maj79";
                break;
            case "m(maj7)":
                typeChord = "mmaj7";
                break;
            case "6/9":
                typeChord = "69";
                break;
            default:
                typeChord = type;
        }

        chordTv.setText(rootChord+rootSide+" "+type);
    }



    public String[] getChord(){
        String[] s = {"",""};
        if(rootSide.equals("b")){
            switch (rootChord){
                case "D":
                    s[0]="C#";
                    break;
                case "E":
                    s[0]="D#";
                    break;
                case "G":
                    s[0]="F#";
                    break;
                case "A":
                    s[0]="G#";
                    break;
                case "B":
                    s[0]="A#";
                    break;
            }
        }else
            s[0]=rootChord+rootSide;

        switch (typeChord){
            case "maj":
                s[1] = "Major";
                break;
            case "maj79":
                s[1] = "maj7/9";
                break;
            case "mmaj7":
                s[1] = "m(maj7)";
                break;
            case "69":
                s[1] = "6/9";
                break;
            default:
                s[1] = typeChord;
        }
        return s;
    }

    public void rootChordSelected(String chord){
        selcectingRoot = true;
        String mChord = (String) chordTv.getText();
        mChord = mChord.replace(rootChord+rootSide,chord+"");

        rootChord = chord;
        rootSide = "";

        chordTv.setText(mChord);
        chordChanged.chordChange(rootChord,PajiSelecter.main);
    }

    public void typeChordSelected(String chord){
        selcectingRoot = false;
        if(     chord.equals("add9") ||
                chord.equals("13") ||
                chord.equals("aug")) {
            typeChord = chord;
            chordTv.setText(rootChord + rootSide + " " + typeChord);
            chordChanged.chordChange(typeChord,PajiSelecter.side);
        }else{
            isChordPossible(chord);
        }

    }

    public void isChordPossible(String chord){
        String s = typeChord + chord;

        for(int c=0; c<mType.length; c++){
            if(mType[c].startsWith(s)){
                typeChord = s;

                for(int cc=c; cc<mType.length; cc++){
                    if(mType[cc].equals(s)){
                        switch (typeChord){
                            case "maj":
                                chordTv.setText(rootChord+rootSide+" Major");
                                chordChanged.chordChange("Major", PajiSelecter.side);
                                break;
                            case "maj79":
                                chordTv.setText(rootChord+rootSide+" maj7/9");
                                chordChanged.chordChange("maj7/9", PajiSelecter.side);
                                break;
                            case "mmaj7":
                                chordTv.setText(rootChord+rootSide+" m(maj7)");
                                chordChanged.chordChange("m(maj7)", PajiSelecter.side);
                                break;
                            case "69":
                                chordTv.setText(rootChord+rootSide+" 6/9");
                                chordChanged.chordChange("6/9", PajiSelecter.side);
                                break;
                            default:
                                chordTv.setText(rootChord+rootSide+" "+typeChord);
                                chordChanged.chordChange(typeChord, PajiSelecter.side);
                        }
                        return;
                    }
                }
                chordTv.setText(rootChord+rootSide+" "+typeChord);
                return;
            }
        }
        typeChord = chord;
        if(typeChord.equals("maj")){
            chordTv.setText(rootChord+rootSide+" Major");
            chordChanged.chordChange("Major", PajiSelecter.side);
        }else if( typeChord.equals("m") ||
                typeChord.equals("6") ||
                typeChord.equals("7") ||
                typeChord.equals("9") ||
                typeChord.equals("11") ||
                typeChord.equals("5")){
            chordTv.setText(rootChord+rootSide+" "+typeChord);
            //pajiSelecter.codeChanged(typeChord,PajiSelecter.side);
            chordChanged.chordChange(typeChord, PajiSelecter.side);
        }else
            chordTv.setText(rootChord+rootSide+" "+typeChord);

    }

    public void specialSelected(String chord){
        String bSide = rootSide, bRoot = rootChord;

        if(chord.equals("sus2/4")){
            if(typeChord.equals("sus2"))
                typeChord = "sus4";
            else
                typeChord = "sus2";
            //pajiSelecter.codeChanged(typeChord,PajiSelecter.side);
            chordChanged.chordChange(typeChord, PajiSelecter.side);
            chordTv.setText(rootChord+rootSide+" "+typeChord);
        }else{
            if(selcectingRoot){
                if(     rootChord.equals("D") ||
                        rootChord.equals("G") ||
                        rootChord.equals("A")){
                    if(rootSide.equals("#"))
                        rootSide = "b";
                    else
                        rootSide = "#";
                }else if(rootChord.equals("C") ||
                        rootChord.equals("F")){
                    rootSide = "#";
                }else{
                    rootSide = "b";
                }
                if(rootSide.equals("b")){
                    switch (rootChord){
                        case "D":
                            chordChanged.chordChange("C#", PajiSelecter.main);
                            break;
                        case "E":
                            chordChanged.chordChange("D#", PajiSelecter.main);
                            break;
                        case "G":
                            chordChanged.chordChange("F#", PajiSelecter.main);
                            break;
                        case "A":
                            chordChanged.chordChange("G#", PajiSelecter.main);
                            break;
                        case "B":
                            chordChanged.chordChange("A#",PajiSelecter.main);
                            break;
                    }
                }else
                    chordChanged.chordChange(rootChord+rootSide,PajiSelecter.main);

                String mChord = (String) chordTv.getText();
                mChord = mChord.replace(bRoot+bSide,rootChord+rootSide);
                chordTv.setText(mChord);

                //chordTv.setText(rootChord+rootSide+" "+typeChord);
            }else{
                if(typeChord.equals("m"))
                    typeChord = "mb";
                else if(typeChord.equals("m7"))
                    typeChord = "m7b";
                else if(typeChord.equals("7#"))
                    typeChord = "7b";
                else if(typeChord.equals("7") ||
                        typeChord.equals("7b"))
                    typeChord = "7#";
                chordTv.setText(rootChord+rootSide+" "+typeChord);
            }
        }

    }

}


