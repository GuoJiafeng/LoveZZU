package com.gjf.lovezzu.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.flashview.FlashView;
import com.gc.flashview.constants.EffectConstants;
import com.gc.flashview.listener.FlashViewListener;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.schoolnewsActivity.SchoolNewsWebView;
import com.gjf.lovezzu.entity.SchoolNewsData;
import com.gjf.lovezzu.entity.SchoolNewsResult;
import com.gjf.lovezzu.network.SchoolNewsMethods;
import com.gjf.lovezzu.view.SchoolLastAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;

/**
 * Created by lenovo047 on 2017/3/9.
 */

public class School_shoolfragment extends Fragment {
    @BindView(R.id.last_school_listview)
    ListView lastSchoolListview;
    private View view;
    private FlashView flashView;
    private ArrayList<String> imageUrls = new ArrayList<String>();
    private List<SchoolNewsResult> schoolNewsResultList = new ArrayList<>();
    private int Page;
    private RecyclerView recyclerView1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Subscriber subscriber;
    SchoolLastAdapter adapter1;
    ListView listView;
    String urlString;
    Map<String,String> links=new HashMap<String,String>();;
    ArrayAdapter<String> adapter;
    URLConnection con;
    InputStream inputStream;
    ByteArrayOutputStream outputStream;
    Handler handler;
    String html;
    LinearLayout id_rl_loading;
    ProgressBar id_pull_to_refresh_load_progress;
    TextView id_pull_to_refresh_loadmore_text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.inchool_school_view, container, false);
           initSchoolList();
           showTopImage();
            listView=(ListView)view.findViewById(R.id.last_school_listview);

            urlString="http://www16.zzu.edu.cn/msgs/vmsgisapi.dll/vmsglist?mtype=m&lan=101,102,103&pn=1";

            init(urlString);

            View listview_footer_view = LayoutInflater.from(getActivity()).inflate(R.layout.listview_footer, null);
            id_rl_loading=(LinearLayout)listview_footer_view.findViewById(R.id.id_rl_loading);
            id_pull_to_refresh_load_progress = (ProgressBar) listview_footer_view.findViewById(R.id.id_pull_to_refresh_load_progress);
            id_pull_to_refresh_load_progress.setVisibility(View.GONE);
            id_pull_to_refresh_loadmore_text = (TextView) listview_footer_view.findViewById(R.id.id_pull_to_refresh_loadmore_text);
            id_pull_to_refresh_loadmore_text.setClickable(false);

            listView.addFooterView(listview_footer_view);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String key=parent.getItemAtPosition(position).toString();
                    String url=links.get(key);
                    Intent intent=new Intent();
                    intent.setClass(getActivity(),SchoolNewsWebView.class);
                    intent.putExtra("url",url);
                    startActivity(intent);
                }
            });

        } else {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
            onRefresh();
            doUpResfresh();
        }
        ButterKnife.bind(this, view);
        return view;
    }


    //下拉刷新操作
    private void onRefresh() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.last_school_content_flash);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshlast();
            }
        });
    }


    //上拉加载


    private void doUpResfresh() {
        if (recyclerView1 == null) {
            recyclerView1 = (RecyclerView) view.findViewById(R.id.last_school_content);
        }

    }


    private void refreshlast() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getSchoolNews(Page++);
                        adapter1.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });


            }
        }).start();
    }

    private void initSchoolList() {

        imageUrls.add("http://202.196.64.199/zzupic/p005.jpg");
        imageUrls.add("http://202.196.64.199/zzupic/p034.jpg");
        imageUrls.add("http://202.196.64.199/zzupic/p003.jpg");

        getSchoolNews(Page);

    }

    private void showTopImage() {
        flashView = (FlashView) view.findViewById(R.id.flash_view);
        flashView.setImageUris(imageUrls);
        flashView.setEffect(EffectConstants.DEFAULT_EFFECT);//更改图片切换的动画效果
        flashView.setOnPageClickListener(new FlashViewListener() {
            @Override
            public void onClick(int position) {
                String mUrl;
                Intent mintent=new Intent();
                mintent.setClass(getActivity(),SchoolNewsWebView.class);
                switch (position){
                    case 0:
                        Toast.makeText(view.getContext(), "郑大官网",
                                Toast.LENGTH_SHORT).show();
                        mUrl="http://202.196.64.199/";
                        mintent.putExtra("url",mUrl);
                        startActivity(mintent);
                        break;
                    case 1:
                        Toast.makeText(view.getContext(), "学校简介",
                                Toast.LENGTH_SHORT).show();
                        mUrl="http://202.196.64.199/gaikuang.htm";
                        mintent.putExtra("url",mUrl);
                        startActivity(mintent);
                        break;
                    case 2:
                        Toast.makeText(view.getContext(), "郑大教务在线",
                                Toast.LENGTH_SHORT).show();
                        mUrl="http://jw.zzu.edu.cn/";
                        mintent.putExtra("url",mUrl);
                        startActivity(mintent);
                        break;
                }

            }
        });

    }

    private void getSchoolNews(int page) {

        subscriber = new Subscriber<SchoolNewsData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("ggggg", e.getMessage().toString());
            }

            @Override
            public void onNext(SchoolNewsData schoolNewsData) {
                List<SchoolNewsResult> list = schoolNewsData.getResults();

                schoolNewsResultList.addAll(list);
                adapter1.notifyDataSetChanged();

            }
        };
        SchoolNewsMethods.getInstance().getSchoolNews(subscriber, page);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void init(String urlString){
        try {
            send(urlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle date=msg.getData();
                byte b[]=date.getByteArray("html");
                try {
                    html=new String(b,"utf-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                links=getLinks(html,links);
                String []array=new String[links.size()];
                int i=0;
                for(String key:links.keySet()){
                    array[i]=key;
                    i++;
                }
                adapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,array);
                listView.setAdapter(adapter);
            }
        };
    }

    public void send(String urlString) throws IOException {
        URL url = new URL(urlString);
        // 打开连接
        con = url.openConnection();
        new Thread(){
            @Override
            public void run() {
                try {
                    inputStream=con.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                byte[] bs = new byte[1024];

                int len;
                outputStream=new ByteArrayOutputStream();
                try {
                    while ((len = inputStream.read(bs)) != -1) {
                        outputStream.write(bs, 0, len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"请检查网络是否通畅",Toast.LENGTH_SHORT).show();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte date[]=outputStream.toByteArray();

                Bundle bundle=new Bundle();
                bundle.putByteArray("html",date);

                Message message=new Message();
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }.start();

    }

    public Map<String,String> getLinks(String html,Map<String,String> links){
        Pattern pattern=Pattern.compile("class=\"zzj_5\">(.*)class=\"zzj_4",Pattern.DOTALL);
        Matcher matcher=pattern.matcher(html);
        String doc = null;
        if(matcher.find()){
            doc=matcher.group(1);
        }
        links=new HashMap<String,String>();
        Document doc2= Jsoup.parse(doc);
        Elements hrefs=doc2.select("a[href]");         //获取带有href属性的a标签
        int i=0;
        for(Element link : hrefs){                   //遍历每个链接
            String linkHref=link.attr("href");          //得到href属性的值，即URL地址
            String linkText=link.text();                //获取链接文本
            links.put(linkText, linkHref);
        }
        return links;
    }


}
