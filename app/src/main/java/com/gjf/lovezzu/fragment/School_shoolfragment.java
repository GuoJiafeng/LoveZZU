package com.gjf.lovezzu.fragment;

import android.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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


import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.entity.school.SchoolNewsData;
import com.gjf.lovezzu.entity.school.SchoolNewsResult;
import com.gjf.lovezzu.entity.school.TopNewsData;
import com.gjf.lovezzu.entity.school.TopNewsResult;
import com.gjf.lovezzu.network.SchoolNewsMethods;
import com.gjf.lovezzu.network.TopNewsMethods;

import com.gjf.lovezzu.view.ImageViewHolder;
import com.gjf.lovezzu.view.SchoolLastAdapter;

import com.thefinestartist.finestwebview.FinestWebView;

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
import rx.Subscriber;

/**
 * Created by lenovo047 on 2017/3/9.
 */

public class School_shoolfragment extends Fragment {
    @BindView(R.id.last_school_listview)
    ListView lastSchoolListview;
    private View view;

    private List<TopNewsResult> topNewsResults=new ArrayList<>();
    private List<SchoolNewsResult> schoolNewsResultList = new ArrayList<>();


    private ConvenientBanner convenientBanner;


    private int Page;
    private Subscriber subscriber;
    SchoolLastAdapter adapter1;
    ListView listView;
    String urlString;
    Map<String,String> links=new HashMap<String,String>();

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
            listView=(ListView)view.findViewById(R.id.last_school_listview);
            convenientBanner= (ConvenientBanner) view.findViewById(R.id.school_flush);
            urlString="http://www16.zzu.edu.cn/msgs/vmsgisapi.dll/vmsglist?mtype=m&lan=101,102,103&pn=1";
            init(urlString);
            initSchoolList();
            getTopSchoolNews();

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
                    new  FinestWebView.Builder(MainActivity.mainActivity)
                            .webViewSupportZoom(true)
                            .webViewBuiltInZoomControls(true)
                            .show(url);
                }
            });

        } else {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
        }
        ButterKnife.bind(this, view);
        return view;
    }

    private void initSchoolList() {
        getSchoolNews(Page);

    }

    private void showTopImage() {
        List<String> top=new ArrayList<>();
        for (TopNewsResult r:topNewsResults){
            top.add(r.getImageUrl());
        }
        convenientBanner.setPages(
                new CBViewHolderCreator<ImageViewHolder>() {
                    @Override
                    public ImageViewHolder createHolder() {
                        return new ImageViewHolder();
                    }
                },top).setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TopNewsResult t=topNewsResults.get(position);
                webView(t.getNewsUrl());
            }
        });
    }

    private void webView(String url){
        new  FinestWebView.Builder(MainActivity.mainActivity)
                .webViewSupportZoom(true)
                .webViewBuiltInZoomControls(true)
                .show(url);
    }
    //获取轮播
    private void getTopSchoolNews(){
        if (topNewsResults!=null){
            topNewsResults.clear();
        }
        subscriber=new Subscriber<TopNewsData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(TopNewsData topNewsData) {
                List<TopNewsResult> list=topNewsData.getValues();
                if (!list.isEmpty()){
                    topNewsResults.addAll(list);
                    showTopImage();
                }
            }
        };
        TopNewsMethods.getInstance().getTopNews(subscriber,"首页");
    }


    private void getSchoolNews(int page) {

        subscriber = new Subscriber<SchoolNewsData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

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
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(4000);
    }


    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
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
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.mainActivity,R.layout.support_simple_spinner_dropdown_item,array);
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
        Document doc2= Jsoup.parse(doc);
        Elements hrefs=doc2.select("a[href]");         //获取带有href属性的a标签
        for(Element link : hrefs){                   //遍历每个链接
            String linkHref=link.attr("href");          //得到href属性的值，即URL地址
            String linkText=link.text();                //获取链接文本
            links.put(linkText, linkHref);
        }
        return links;
    }


}
