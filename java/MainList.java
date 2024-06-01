package com.example.dhruvpatel.login;

import com.google.firebase.database.Exclude;

public class MainList{
    private String mcarname;
    private String mcarseater;
    private String mcaryear;
    private String mcartype;
    private String mcarprice;
    private String mImageurl;
    private String mkey;

    MainList(){

    }

    MainList(String carname,String carseater,String caryear,String cartype,String carprice,String imageurl){

        mcarname=carname;
        mcarseater=carseater;
        mcaryear=caryear;
        mcartype=cartype;
        mcarprice=carprice;
        mImageurl=imageurl;

    }

    public String getcarname() {
        return mcarname;
    }

    public void setcarname(String carname) {
        mcarname = carname;
    }

    public String getcarseater() {
        return mcarseater;
    }

    public void setcarseater(String carseater) {
        mcarseater = carseater;
    }

    public String getcaryear() {
        return mcaryear;
    }

    public String getImageurl() {
        return mImageurl;
    }

    public void setcaryear(String caryear) {
        mcaryear = caryear;
    }

    public String getcartype() {
        return mcartype;
    }

    public void setcartype(String cartype) {
        mcartype = cartype;
    }

    public String getcarprice() {
        return mcarprice;
    }

    public void setcarprice(String carprice) {
        mcarprice = carprice;
    }

    public void setImageurl(String imageurl) {
        mImageurl = imageurl;
    }

    @Exclude
    public void setKey(String key){ mkey = key; }

    @Exclude
    public String getKey(){ return mkey; }
}
