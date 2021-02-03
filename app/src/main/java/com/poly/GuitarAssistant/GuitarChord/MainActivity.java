package com.poly.GuitarAssistant.GuitarChord;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewFlipper;

import androidx.appcompat.app.ActionBar;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.GuitarAssistant.R;

public class MainActivity extends AppCompatActivity {

    GuitarChordSelecter mainChordSelecter,
            sideChordSelecter;
    PajiSelecter pajiSelecter;
    GuitarChordView guitarChordView;

    Boolean lineDirection = GuitarPajiView.up;
    ViewFlipper chordSelecter;
    BtnChordSelecter btnChordSelecter;

    UnderbarMenu underbarMenu;

    final int stateBtn = 1,
              stateList = 0;
    int chordSelecterState = stateBtn;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.guitarchord_bar_layout);

        guitarChordView = findViewById(R.id.GuitarCode_View);
        chordSelecter = findViewById(R.id.ChordSelectFilpper);

        underbarMenu = findViewById(R.id.UnderBarMenu1);
        underbarMenu.setActivity(this);

        pajiSelecter = findViewById(R.id.pajiSelecter);
        pajiSelecter.setPajiSelected(new PajiSelecter.PajiSelected() {
            @Override
            public void pajiSelected(int paji, int line) {
                guitarChordView.GuitarPajiChanged(paji, line);
            }
        });

        //코드셀렉터 콜백
        mainChordSelecter = findViewById(R.id.mainCodeSelecter);
        mainChordSelecter.setChordChanged(new GuitarChordSelecter.chordChanged() {
            @Override
            public void chordChange(String code) {
                pajiSelecter.chordChanged(code,PajiSelecter.main);
            }
        });

        sideChordSelecter = findViewById(R.id.sideCodeSelecter);
        sideChordSelecter.setChordChanged(new GuitarChordSelecter.chordChanged() {
            @Override
            public void chordChange(String code) {
                pajiSelecter.chordChanged(code, PajiSelecter.side);
            }
        });

        btnChordSelecter = findViewById(R.id.btnChordSelecter);
        btnChordSelecter.setChordChanged(new BtnChordSelecter.ChordChanged() {
            @Override
            public void chordChange(String chord, boolean type) {
                pajiSelecter.chordChanged(chord, type);
            }
        });

        //설정 불러오기
        chordSelecterState = preferences.getInt("chordState",stateList);
        chordSelecter.setDisplayedChild(chordSelecterState);
        lineDirection = preferences.getBoolean("lineDirection",GuitarPajiView.up);
        //guitarCodeView.setLineDirection(lineDirection);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.guitar_chord_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.selectBtn:
                if(chordSelecterState != stateBtn) {
                    chordSelecterState = stateBtn;
                    String str1 = mainChordSelecter.getChord();
                    String str2 = sideChordSelecter.getChord();
                    btnChordSelecter.setChord(str1, str2);
                }
                break;
            case R.id.selectList:
                chordSelecterState = stateList;
                String[] str3 = btnChordSelecter.getChord();
                mainChordSelecter.setChord(str3[0]);
                sideChordSelecter.setChord(str3[1]);
                break;
            case R.id.lineDerectDown:
                lineDirection = GuitarPajiView.down;
                break;
            case R.id.lineDerectUp:
                lineDirection = GuitarPajiView.up;
                break;
        }
        chordSelecter.setDisplayedChild(chordSelecterState);
        guitarChordView.setLineDirection(lineDirection);
        editor.putInt("chordState",chordSelecterState);
        editor.putBoolean("lineDirection",lineDirection);
        editor.apply();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("종료 하시겠습니까");

        builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                finish();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
            }
        });
        builder.show();
    }

}
