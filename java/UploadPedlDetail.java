package com.example.dhruvpatel.login;

import com.google.firebase.database.Exclude;

class UploadPedlDetail {

    private String mpedlname;
    private String mpedlcc;
    private String mpedlprice;
    private String mpedlyear;
    private String mImageUrl;
    private String mKey;

    public String getpedlname() {
        return mpedlname;
    }

    public void setpedlname(String mpedlname) {
        this.mpedlname = mpedlname;
    }

    public String getpedlcc() {
        return mpedlcc;
    }

    public void setpedlcc(String mpedlcc) {
        this.mpedlcc = mpedlcc;
    }

    public String getpedlprice() {
        return mpedlprice;
    }

    public void setpedlprice(String mpedlprice) {
        this.mpedlprice = mpedlprice;
    }

    public String getpedlyear() {
        return mpedlyear;
    }

    public void setpedlyear(String mpedlyear) {
        this.mpedlyear = mpedlyear;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String mKey) {
        this.mKey = mKey;
    }



    UploadPedlDetail(){}

    UploadPedlDetail(String mpedlname,String mpedlyear,String mpedlcc,String mpedlprice,String mImageUrl){

        this.mpedlname=mpedlname;
        this.mpedlyear=mpedlyear;
        this.mpedlcc=mpedlcc;
        this.mpedlprice=mpedlprice;
        this.mImageUrl=mImageUrl;
    }








}
