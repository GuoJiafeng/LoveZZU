package com.gjf.lovezzu.entity;

/**
 * Created by lenovo047 on 2017/5/30.
 */

public class Topic2main {
    private  String mtitle;
    private String mcontent;
    private  String mimgurl;
    private int mpinglunnum;
    private  int mguanzhunum;
    private int mtime;
    private  String mname;

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getMcontent() {
        return mcontent;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent;
    }

    public String getMimgurl() {
        return mimgurl;
    }

    public void setMimgurl(String mimgurl) {
        this.mimgurl = mimgurl;
    }

    public int getMpinglunnum() {
        return mpinglunnum;
    }

    public void setMpinglunnum(int mpinglunnum) {
        this.mpinglunnum = mpinglunnum;
    }

    public int getMguanzhunum() {
        return mguanzhunum;
    }

    public void setMguanzhunum(int mguanzhunum) {
        this.mguanzhunum = mguanzhunum;
    }

    public int getMtime() {
        return mtime;
    }

    public void setMtime(int mtime) {
        this.mtime = mtime;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public Topic2main(String mtitle, String mcontent, int mpinglunnum, int mguanzhunum, int mtime, String mname) {
        this.mtitle = mtitle;
        this.mcontent = mcontent;
        this.mpinglunnum = mpinglunnum;
        this.mguanzhunum = mguanzhunum;
        this.mtime = mtime;
        this.mname = mname;

    }
}
