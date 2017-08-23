package com.gjf.lovezzu.entity;

import android.content.Context;

import com.gjf.lovezzu.entity.parttimejob.JobResult;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/8/20.
 */

public class ImagesUpload {
    private List<File> list;
    private Context context;
    private String action;
    private Map map = new HashMap<String,Object>();
    private Boolean res=false;

    public void setRes(Boolean res) {
        this.res = res;
    }

    public Boolean getRes() {
        return res;
    }

    public ImagesUpload(List<File> fileList, Context context1, String action1, HashMap<String,Object> T){
        list=fileList;
        context=context1;
        action=action1;
        map=T;
    }

    public boolean upFiles(){

        RequestParams requestParams=new RequestParams(LOGIN_URL+action);
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Object val = entry.getValue();
            requestParams.addParameter(key,val);
        }
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    setRes(jsonObject.getBoolean("isSuccessful"));
                }catch (Exception e){
                    setRes(false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                setRes(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
        return res;
    }

}
