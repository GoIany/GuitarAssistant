package com.poly.GuitarAssistant.Lecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.poly.GuitarAssistant.GuitarChord.UnderbarMenu;
import com.poly.GuitarAssistant.R;

import java.util.ArrayList;

public class tong1 extends AppCompatActivity implements FrameLayout.OnClickListener {
    FrameLayout startBtn, stopBtn, button;
    ViewFlipper viewFlipper;
    ImageView img_lecture1,img_lecture2,img_lecture3;
    ListView listView;
    SingerAdapter adapter;
    TextView lecture_Title;
    UnderbarMenu underbarMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tong1);
        listView = findViewById(R.id.listview);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.lecture_bar_layout);
        lecture_Title = findViewById(R.id.lecture_title);



        Resources res = getResources();
        img_lecture1 = findViewById(R.id.img_lecture_1);
        img_lecture2 = findViewById(R.id.img_lecture_2);
        img_lecture3 = findViewById(R.id.img_lecture_3);
        img_lecture1 .setColorFilter(res.getColor(R.color.lecture_icon_color));
        img_lecture2.setColorFilter(res.getColor(R.color.lecture_icon_color));
        img_lecture3.setColorFilter(res.getColor(R.color.lecture_icon_color));

        adapter = new SingerAdapter();

        startBtn = findViewById(R.id.let1);
        stopBtn = findViewById(R.id.let2);
        button = findViewById(R.id.let3);
        viewFlipper = findViewById(R.id.viewFlipper);

        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        button.setOnClickListener(this);

        underbarMenu = findViewById(R.id.UnderBarMenu2);
        underbarMenu.setActivity(this);
        underbarMenu.setFlipperAct2(viewFlipper);
    }

    class SingerAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(SingerItem item) {
            items.add(item);
        }

        public void deleteItem(){
            items.clear();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SingerItemView singerItemView = null;
            if(convertView == null){
                singerItemView = new SingerItemView(getApplicationContext());
            }
            else{
                singerItemView = (SingerItemView)convertView;
            }
            SingerItem item = items.get(position);
            singerItemView.setName(item.getName());
            singerItemView.setMobile(item.getMobile());
            singerItemView.setImage(item.getResId());
            return singerItemView;
        }
    }
    @Override
    public void onClick(View v) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id ) {
                SingerItem item=(SingerItem)adapter.getItem(position);
                Intent mInternet = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
                startActivity(mInternet);
            }
        });
        switch(v.getId())
        {
            case R.id.let1:
                listView.clearChoices();
                adapter.notifyDataSetChanged();
                adapter.deleteItem();
                listView.setAdapter(adapter);
                tongbeginer();
                tongmiddle();
                tonghigh();
                viewFlipper.setDisplayedChild(1);
                lecture_Title.setText("통기타");
                break;

            case R.id.let2:
                listView.clearChoices();
                adapter.notifyDataSetChanged();
                adapter.deleteItem();
                listView.setAdapter(adapter);
                illbeginer();
                illmiddle();
                illhigh();
                viewFlipper.setDisplayedChild(1);
                lecture_Title.setText("일렉기타");
                break;
            case R.id.let3:
                listView.clearChoices();
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                basebegin();
                basemiddle();
                basehigh();
                viewFlipper.setDisplayedChild(1);
                lecture_Title.setText("베이스");
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu01,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id ) {

                SingerItem item=(SingerItem)adapter.getItem(position);
                Intent mInternet = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
                startActivity(mInternet);
            }
        });

        viewFlipper.setDisplayedChild(1);
        switch(item.getItemId())
        {
            case R.id.all:
                adapter.deleteItem();
                listView.setAdapter(adapter);
                tongbeginer();
                tongmiddle();
                tonghigh();
                illbeginer();
                illmiddle();
                illhigh();
                basebegin();
                basemiddle();
                basehigh();
                lecture_Title.setText("강의영상");
                break;
            case R.id.base1:
                adapter.deleteItem();
                listView.setAdapter(adapter);
                basebegin();
                lecture_Title.setText("베이스-초급");
                break;
            case R.id.base2:
                adapter.deleteItem();
                listView.setAdapter(adapter);
                basemiddle();
                lecture_Title.setText("베이스-중급");
                break;
            case R.id.base3:
                adapter.deleteItem();
                listView.setAdapter(adapter);
                basehigh();
                lecture_Title.setText("베이스-고급");
                break;
            case R.id.tong1:
                adapter.deleteItem();
                listView.setAdapter(adapter);
                tongbeginer();
                lecture_Title.setText("통기타-초급");
                break;
            case R.id.tong2:
                adapter.deleteItem();
                listView.setAdapter(adapter);
                tongmiddle();
                lecture_Title.setText("통기타-중급");
                break;
            case R.id.tong3:
                adapter.deleteItem();
                listView.setAdapter(adapter);
                tonghigh();
                lecture_Title.setText("통기타-고급");
                break;
            case R.id.ill1:
                adapter.deleteItem();
                listView.setAdapter(adapter);
                illbeginer();
                lecture_Title.setText("일렉기타-초급");
                break;
            case R.id.ill2:
                adapter.deleteItem();
                listView.setAdapter(adapter);
                illmiddle();
                lecture_Title.setText("일렉기타-중급");
                break;
            case R.id.ill3:
                adapter.deleteItem();
                listView.setAdapter(adapter);
                illhigh();
                lecture_Title.setText("일렉기타-고급");
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if( (viewFlipper.getDisplayedChild() == 1)){
            listView.clearChoices();
            adapter.notifyDataSetChanged();
            adapter.deleteItem();
            listView.setAdapter(adapter);
            viewFlipper.setDisplayedChild(0);
            lecture_Title.setText("강의영상");
        }else{
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

    public void tongbeginer(){
        adapter.addItem(new SingerItem("기타 튜닝(조율)하기","\n채널:꿀잼기타\n        Honeyjam guitar",R.drawable.c1,"https://youtu.be/f0UREMH4s7E"));
        adapter.addItem(new SingerItem("왕초보를 위한\n통기타 강좌","채널:꿀잼기타\n        Honeyjam guitar",R.drawable.c2,"https://youtu.be/1CKOrteG3TA"));
        adapter.addItem(new SingerItem("칼립소 주법","\n채널:꿀잼기타\n        Honeyjam guitar",R.drawable.c3,"https://youtu.be/uhWp4KdJ2q8"));
        adapter.addItem(new SingerItem("악센트 연주하는법","\n 채널:꿀잼기타\n        Honeyjam guitar",R.drawable.c4,"https://youtu.be/xC9Z_AF2V-g"));

    }
    public void tongmiddle(){
        adapter.addItem(new SingerItem("통기타 더블 스트로크","\n채널:딩기리닷컴",R.drawable.c5,"https://youtu.be/XIkV1tNRVrs"));
        adapter.addItem(new SingerItem("해머링 온, 풀링 오프","\n채널:Grab the Guitar",R.drawable.c6,"https://youtu.be/Lkiz7WR8RYY"));
        adapter.addItem(new SingerItem("오른손 커팅|왼손 커팅 방법","\n채널:Grab the Guitar",R.drawable.c7,"https://youtu.be/7oC8ZLrkMr0"));
        adapter.addItem(new SingerItem("바레코드 완전 정복","\n채널:Grab the Guitar",R.drawable.c8,"https://youtu.be/w7M7vubClJc"));

    }
    public void tonghigh(){
        adapter.addItem(new SingerItem("퍼커시브 주법","\nGrab the Guitar",R.drawable.c9,"https://youtu.be/YDuIYNx9GQA"));
        adapter.addItem(new SingerItem("핑거스타일 연주\n(메이플스토리ost)","\nTongdangi 통단기",R.drawable.c10,"https://youtu.be/xChEHnQkyQE"));
        adapter.addItem(new SingerItem("핑거스타일 연주\n(Flower Dance)","\nTongdangi 통단기",R.drawable.c11,"https://youtu.be/nY2NXRLugpo"));
        adapter.addItem(new SingerItem("핑거스타일 연주 \n황혼 Twilight","\nTongdangi 통단기",R.drawable.c12,"https://youtu.be/eARJISQA1JQ"));
    }
    public void illbeginer() {

        adapter.addItem(new SingerItem("일렉기타, 코드 스트로크", "\nEmotional Guitarist AZ", R.drawable.c13, "https://youtu.be/TOLY6F1iJCs"));
        adapter.addItem(new SingerItem("일렉기타, 코드 아르페지오", "\nEmotional Guitarist AZ", R.drawable.c14, "https://youtu.be/Y8iH5kXBgfg"));
        adapter.addItem(new SingerItem("일렉기타, 커팅주법", "\nEmotional Guitarist AZ", R.drawable.c15, "https://youtu.be/K9EYda-M_1Y"));
        adapter.addItem(new SingerItem("일렉기타, 슬라이드", "\nEmotional Guitarist AZ", R.drawable.c16, "https://youtu.be/4uIvLopffJg"));
    }
    public void illmiddle(){
        adapter.addItem(new SingerItem("피킹하모닉스","\nEmotional Guitarist AZ",R.drawable.c17,"https://youtu.be/MBeIy9zI-tk"));
        adapter.addItem(new SingerItem("메이저 스케일","\nEmotional Guitarist AZ",R.drawable.c18,"https://youtu.be/VgPSNudlxts"));
        adapter.addItem(new SingerItem("펜타토닉 스케일","\nEmotional Guitarist AZ",R.drawable.c19,"https://youtu.be/W07tLZasgXQ"));
        adapter.addItem(new SingerItem("일레기타 테크닉,옥타브 주법","\nEmotional Guitarist AZ",R.drawable.c20,"https://youtu.be/hn1PXfoUPnc"));

    }
    public void illhigh(){

        adapter.addItem(new SingerItem("스윕 피킹","\nEmotional Guitarist AZ",R.drawable.c21,"https://youtu.be/2hjE5yfJd2I"));
        adapter.addItem(new SingerItem("테핑 (Triptych 태핑 레슨)","\nEmotional Guitarist AZ",R.drawable.c22,"https://youtu.be/o8N7kqfBzsA"));
        adapter.addItem(new SingerItem("라이크 주법","\nEmotional Guitarist AZ",R.drawable.c23,"https://youtu.be/2HXJMJFOKMU"));
        adapter.addItem(new SingerItem("아밍","\nEmotional Guitarist AZ",R.drawable.c24,"https://youtu.be/_yzeqIBo43c"));
    }
    public void basebegin(){
        adapter.addItem(new SingerItem("베이스 기초레슨","채널:ADAM TV Music Studio",R.drawable.c25,"https://youtu.be/VBMMGvwnwJM"));
        adapter.addItem(new SingerItem("베이스기타 악보의 \n종류와 보는법","채널:손록손록",R.drawable.c26,"https://youtu.be/IBwHGxJF9P4"));
        adapter.addItem(new SingerItem("크로매틱 연습","채널:Yc Bass",R.drawable.c27,"https://youtu.be/fazraiIhOwo"));
        adapter.addItem(new SingerItem("슬라이드 & 글리산도","채널:Guitarcamp 기타캠프",R.drawable.c28,"https://youtu.be/KLpH8AE6kHM"));
    }

    public void basemiddle(){
        adapter.addItem(new SingerItem("지판(계이름) 빨리외우는 \n방법","\n채널:Yc Bass",R.drawable.c29,"https://youtu.be/K3_RYEuP0K0"));
        adapter.addItem(new SingerItem("핑거루프","\n채널:Yc Bass",R.drawable.c30,"https://youtu.be/S0REaGpe_F"));
        adapter.addItem(new SingerItem("슬랩 초보 탈출","\n채널:베이스의선율",R.drawable.c31,"https://youtu.be/EArHofnr_Lo"));
        adapter.addItem(new SingerItem("4화음 코드(CM7, C7)","\n채널:베이스의선율",R.drawable.c32,"https://youtu.be/EOzrkEOfbPs"));


    }
    public void basehigh(){
        adapter.addItem(new SingerItem("필인 가이드","\n채널:ADAM TV Music Studio",R.drawable.c33,"https://youtu.be/NkTCqk3cqeI"));
        adapter.addItem(new SingerItem("슬랩연주","\n채널:쟈횽",R.drawable.c34,"https://youtu.be/HS50mCFWwok"));
        adapter.addItem(new SingerItem("스케일 활용 및 연습방법","\n채널:베이시스트안성진.",R.drawable.c35,"https://youtu.be/XJwcJB2mS5U"));
        adapter.addItem(new SingerItem("메이져 펜타토닉","\n채널:우선생의베이스아카데미",R.drawable.c36,"https://youtu.be/2baW_IXw4V4"));
    }


}