package com.gjf.lovezzu.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.saylvoeActivity.SayloveReplyActivity;
import com.gjf.lovezzu.activity.saylvoeActivity.SayloveWallActivity;
import com.gjf.lovezzu.entity.saylove.Saylove;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;
import static com.thefinestartist.utils.content.ResourcesUtil.getAssets;


/**
 * Created by Leon on 2017/8/15.
 */

public class SayloveAdapter extends RecyclerView.Adapter<SayloveAdapter.ViewHolder> {

    @BindView(R.id.biaobai_zan)
    TextView biaobaiZan;
    private List<Saylove> sayloveList;
    private Saylove saylove;
    private LayoutInflater inflater;
    private String SesionID;
    private Boolean res = false;


    public SayloveAdapter(List<Saylove> sayloveList, String sesionID) {
        this.sayloveList = sayloveList;
        SesionID = sesionID;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sayloveall_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.saylovereplybutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Saylove saylove = sayloveList.get(holder.getAdapterPosition());
                Intent intent = new Intent();
                intent.setClass(SayloveWallActivity.sayloveWallActivity, SayloveReplyActivity.class);
                intent.putExtra("loveCard", saylove);
                SayloveWallActivity.sayloveWallActivity.startActivity(intent);

            }
        });
        holder.loveCardzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Saylove saylove = sayloveList.get(holder.getAdapterPosition());
                Toast.makeText(SayloveWallActivity.sayloveWallActivity, "+1", Toast.LENGTH_SHORT).show();
                Integer zan = Integer.parseInt(saylove.getThembCount()+"");
                holder.saylovezan.setText((zan + 1) + "");
                addThum(saylove.getLoveCardId().toString());
            }
        });

        return holder;
    }

    private boolean addThum(String mid) {
        RequestParams requestParams = new RequestParams(LOGIN_URL + "LoveCardCommentAction");
        requestParams.addBodyParameter("loveCardId",mid );
        requestParams.addBodyParameter("SessionID", SesionID);
        requestParams.addBodyParameter("action", "点赞表白卡");
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject json = new JSONObject(result);
                    res = json.getBoolean("isSuccessful");
                    if (res) {

                    } else {
                        Toast.makeText(SayloveWallActivity.sayloveWallActivity, "请重新登录！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(SayloveWallActivity.sayloveWallActivity, "请检查网络！", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Saylove  saylove2 = sayloveList.get(position);
        holder.saylovezan.setText(saylove2.getThembCount() + "");
        holder.sayloveContext.setText("\u3000\u3000" + saylove2.getLoveContent());
        holder.sayloveName.setText(saylove2.getSenderName());
        holder.BesaidName.setText(saylove2.getLovedName());

    }

    @Override
    public int getItemCount() {
        return sayloveList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView BesaidName;
        TextView sayloveName;
        TextView sayloveContext;
        TextView saylovezan;
        LinearLayout saylovereplybutton;
        ImageView loveCardzan;

        public ViewHolder(View itemView) {
            super(itemView);
            BesaidName = (TextView) itemView.findViewById(R.id.BesaidName);
            sayloveName = (TextView) itemView.findViewById(R.id.SayloveName);
            sayloveContext = (TextView) itemView.findViewById(R.id.Saylove_context);
            saylovezan = (TextView) itemView.findViewById(R.id.biaobai_zan);
            saylovereplybutton = (LinearLayout) itemView.findViewById(R.id.saylovereply_button);
            loveCardzan = (ImageView) itemView.findViewById(R.id.loveCard_zan);

        }
    }
}
