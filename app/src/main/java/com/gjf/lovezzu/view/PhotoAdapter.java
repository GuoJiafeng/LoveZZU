package com.gjf.lovezzu.view;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.network.UpLoadIconMethods;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.iwf.photopicker.utils.AndroidLifecycleUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by BlackBeard丶 on 2017/03/15.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private ArrayList<String> photoPaths = new ArrayList<String>();
    private LayoutInflater inflater;
    private Uri uri;
    private Context mContext;
    private Subscriber subscriber;
    public final static int TYPE_ADD = 1;
    public final static int TYPE_PHOTO = 2;

    public final static int MAX = 9;

    public PhotoAdapter(Context mContext, ArrayList<String> photoPaths) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);

    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case TYPE_ADD:
                itemView = inflater.inflate(R.layout.iconpick_add, parent, false);
                break;
            case TYPE_PHOTO:
                itemView = inflater.inflate(R.layout.__picker_item_photo, parent, false);
                break;
        }
        return new PhotoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

        if (getItemViewType(position) == TYPE_PHOTO) {
            uri = Uri.fromFile(new File(photoPaths.get(position)));

            //Map<String, RequestBody> photos = new HashMap<>();
            File file = new File(uri.getPath());

           /* if (file.exists()){
                Log.d("ggggg","yicunzai");
            }*/
            //testReq(file);
            //RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            //photos.put("image", requestFile);
            //photos.put("phone", RequestBody.create(MediaType.parse("multipart/form-data"), "123456"));
           /* Log.d("ggggg", photos.toString());
            Log.d("ggggg", uri.toString());
            Log.d("ggggg", file.getName());
*/

            boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(holder.ivPhoto.getContext());

            if (canLoadImage) {

                    // upLoad(photos);
                    Glide.with(mContext)
                            .load(uri)
                            .centerCrop()
                            .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                            .error(R.drawable.__picker_ic_broken_image_black_48dp)
                            .into(holder.ivPhoto);


            }
        }
    }

   /* private void testReq(File file){
//        x.Ext.init(mContext.getApplicationContext());
        RequestParams requestParams=new RequestParams(LOGIN_URL+"upload");
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("phone","1222");
        requestParams.addBodyParameter("myUpload",file);
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }*/


    @Override
    public int getItemCount() {
        int count = photoPaths.size() + 1;
        if (count > MAX) {
            count = MAX;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == photoPaths.size() && position != MAX) ? TYPE_ADD : TYPE_PHOTO;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private View vSelected;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            vSelected = itemView.findViewById(R.id.v_selected);
            if (vSelected != null) vSelected.setVisibility(View.GONE);
        }
    }

   /* public void upLoad(Map<String, RequestBody> photos) {
        subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.d("ggggg", "success");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("ggggg", e.getMessage().toString());
            }

            @Override
            public void onNext(ResponseBody userInfoResult) {

                Log.d("ggggg", "xiyibu");
            }
        };
        String phone = null;
        UpLoadIconMethods.upLoadIconMethods().goToUploadIcon(subscriber, photos);
    }*/
}
