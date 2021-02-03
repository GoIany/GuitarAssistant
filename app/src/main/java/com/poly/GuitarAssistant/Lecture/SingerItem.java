package com.poly.GuitarAssistant.Lecture;

import java.text.MessageFormat;

public class SingerItem {
    String name;
    String mobile;
    int resId;
    String url;

    public SingerItem(String name,String mobile, int resId ,String url){
       this.name = name;
       this.mobile = mobile;
        this.resId = resId;
        this.url = url;
    }

  public String getName() {

       return name;
   }

    public void setName(String name)
    {
        this.name = name;
    }

  public String getMobile() {

       return mobile; }


    public void setMobile(String mobile)
   {
       this.mobile = mobile;
 }

    public int getResId() {
        return resId;
    }
    public String getUrl(){
        return  url;
    }
    public void setUrl(String Url){this.url = url; }
   @Override
   public  String toString(){
       return MessageFormat.format("SingerItem'{'name=''{0}'', mobile=''{1}'''}'", name, mobile);
   }

}
