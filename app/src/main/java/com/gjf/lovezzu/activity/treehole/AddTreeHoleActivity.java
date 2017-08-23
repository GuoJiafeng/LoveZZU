package com.gjf.lovezzu.activity.treehole;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.view.WheelView;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/5/4.
 */

public class AddTreeHoleActivity extends AppCompatActivity {
    @BindView(R.id.tree_add_back)
    ImageView treeAddBack;
    @BindView(R.id.send_tree)
    TextView sendTree;
    @BindView(R.id.add_tree_content)
    EditText addTreeContent;
    @BindView(R.id.add_tree_textNumber)
    TextView addTreeTextNumber;
    @BindView(R.id.tree_add_school)
    TextView treeAddSchool;
    public static AddTreeHoleActivity addTreeHoleActivity;

    private String sessionID;
    private String tree_school="郑州大学（主校区）";
    private static final String[] TREE_SCHOOL={"郑州大学（主校区）","郑州大学（北校区）","郑州大学（南校区）","郑州大学（东校区）"};
    private android.app.AlertDialog goods_schoolDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tree_add_view);
        ButterKnife.bind(this);
        addTreeHoleActivity=this;
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        sessionID = sharedPreferences.getString("SessionID", "");
        //字数监听
        addTreeContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = addTreeContent.getText().toString();
                addTreeTextNumber.setText(text.length() + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.tree_add_back, R.id.send_tree,R.id.tree_add_school})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tree_add_back:
                finish();
                break;
            case R.id.send_tree:
                if (addTreeContent.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "不能为空!", Toast.LENGTH_SHORT);
                } else {
                    addTree();
                }
                break;
            case R.id.tree_add_school:
                View outerView1 = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView wv1 = (WheelView) outerView1.findViewById(R.id.wheel_view_wv);
                wv1.setOffset(1);
                wv1.setItems(Arrays.asList(TREE_SCHOOL));
                wv1.setSeletion(1);
                wv1.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        tree_school=item;
                    }
                });

                goods_schoolDialog= new android.app.AlertDialog.Builder(this)
                        .setTitle("选择校区")
                        .setView(outerView1)
                        .show();
                TextView txtSure1= (TextView) outerView1.findViewById(R.id.txt_sure);
                txtSure1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        treeAddSchool.setText(tree_school);
                        goods_schoolDialog.dismiss();
                    }
                });
                TextView txtCancle1= (TextView) outerView1.findViewById(R.id.txt_cancel);
                txtCancle1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        treeAddSchool.setText("");
                        goods_schoolDialog.dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }

    private void addTree() {
        RequestParams requestParams = new RequestParams(LOGIN_URL + "TreeHoleAction");
        requestParams.addBodyParameter("treeHoleContent", addTreeContent.getText().toString());
        requestParams.addBodyParameter("SessionID", sessionID);
        requestParams.addBodyParameter("campus",treeAddSchool.getText().toString());
        requestParams.addBodyParameter("action","发布树洞");
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try{
                    JSONObject jsonObject=new JSONObject(result);
                    Boolean res=jsonObject.getBoolean("isSuccessful");
                    if (res){
                        Toast.makeText(addTreeHoleActivity,"发布成功！",Toast.LENGTH_SHORT).show();
                        addTreeContent.setText("");
                        finish();
                    }else {
                        Toast.makeText(addTreeHoleActivity,"请重新登录！",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(addTreeHoleActivity,"请检查网络是否异常！",Toast.LENGTH_SHORT).show();
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
    }
}
