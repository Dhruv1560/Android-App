package com.example.dhruvpatel.login;

import com.google.firebase.database.Exclude;

public class TwoList {
    private String mpedlname;
    private String mpedlyear;
    private String mpedlcc;
    private String mpedlprice;
    private String mImagepedlurl;
    private String mkey;


    TwoList(){}

    TwoList(String pedlname,String pedlyear,String pedlcc,String pedlprice,String mImageurl){
        mpedlname=pedlname;
        mpedlyear=pedlyear;
        mpedlcc=pedlcc;
        mpedlprice=pedlprice;
        this.mImagepedlurl=mImageurl;

    }

    public String getpedlname() {
        return mpedlname;
    }

    public void setpedlname(String pedlname) {
        this.mpedlname = pedlname;
    }

    public String getpedlyear() {
        return mpedlyear;
    }

    public void setpedlyear(String pedlyear) {
        this.mpedlyear = pedlyear;
    }

    public String getpedlcc() {
        return mpedlcc;
    }

    public void setpedlcc(String pedlcc) {
        this.mpedlcc = pedlcc;
    }

    public String getpedlprice() {
        return mpedlprice;
    }

    public void setpedlprice(String pedlprice) {
        this.mpedlprice = pedlprice;
    }

    public String getImageurl() {
        return mImagepedlurl;
    }

    public void setImageurl(String imageurl) {
            mImagepedlurl = imageurl;
    }

    @Exclude
    public String getkey() {
        return mkey;
    }

    @Exclude
    public void setkey(String key) {
        mkey = key;
    }
}
