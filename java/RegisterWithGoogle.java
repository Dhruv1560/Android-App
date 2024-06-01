package com.example.dhruvpatel.login;

import com.google.firebase.database.Exclude;

public class RegisterWithGoogle {

    public String gusername;
    public String gemail;
    public String gid;
    public String gImageurl;
    public String gkey;


    RegisterWithGoogle(){}

    public RegisterWithGoogle(String gusername, String gemail, String gid,String gImageurl) {
        this.gusername = gusername;
        this.gemail = gemail;
        this.gid = gid;
        this.gImageurl=gImageurl;
    }

    public String getGusername() {
        return gusername;
    }

    public void setGusername(String gusername) {
        this.gusername = gusername;
    }

    public String getGemail() {
        return gemail;
    }

    public void setGemail(String gemail) {
        this.gemail = gemail;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getgImageurl() {
        return gImageurl;
    }

    public void setgImageurl(String gImageurl) {
        this.gImageurl = gImageurl;
    }

    @Exclude
    public String getGkey() {
        return gkey;
    }

    @Exclude
    public void setGkey(String gkey) {
        this.gkey = gkey;
    }
}
