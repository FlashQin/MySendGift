package com.zxm.giftlistdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout idotLayout;//知识圆点
    private List<View> mPagerList;//页面集合
    private List<Model> mDataList;//数据集合；
    private LayoutInflater mInflater;

    private Context mContext;
    private int currPage;

    /*总的页数*/
    private int pageCount;
    /*每一页显示的个数*/
    private int pageSize = 8;
    /*当前显示的是第几页*/
    private int curIndex = 0;
    private GridViewAdapter[] arr = new GridViewAdapter[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        initValues();
    }


    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        idotLayout = (LinearLayout) findViewById(R.id.ll_dot);
    }

    private void initValues() {

        mDataList = new ArrayList<>();
        //初始化图标资源
        for (int i = 0; i < 18; i++) {
            int imageId = getResources().getIdentifier("gift" + i, "mipmap", getPackageName());
            Model model = new Model();
            model.setImage(imageId);
            model.setMoney("520钻石");
            mDataList.add(model);
        }

        mInflater = LayoutInflater.from(mContext);
        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(mDataList.size() * 1.0 / pageSize);

        //viewpager
        mPagerList = new ArrayList<View>();
        for (int j = 0; j < pageCount; j++) {
            final GridView gridview = (GridView) mInflater.inflate(R.layout.bottom_vp_gridview, viewPager, false);
            final GridViewAdapter gridAdapter = new GridViewAdapter(mContext, mDataList, j);
            gridview.setAdapter(gridAdapter);
            arr[j] = gridAdapter;
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "点击位置position：" + position + "..id:" + id, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < mDataList.size(); i++) {
                        Model model = mDataList.get(i);
                        if (i == id) {
                            if (model.isSelected()) {
                                model.setSelected(false);
                            } else {
                                model.setSelected(true);
                            }
                            Log.i("tag", "==点击位置：" + i + "..id:" + id);
                        } else {
                            model.setSelected(false);
//                            Log.i("tag","==位置2："+i+"..id:"+id);
                        }
                    }
                    Log.i("tag", "状态：" + mDataList.toString());
                    gridAdapter.notifyDataSetChanged();
                }
            });
            mPagerList.add(gridview);
        }

        viewPager.setAdapter(new ViewPagerAdapter(mPagerList, MainActivity.this));
        setOvalLayout();
    }

    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            idotLayout.addView(mInflater.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        idotLayout.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                arr[0].notifyDataSetChanged();
                arr[1].notifyDataSetChanged();
                arr[2].notifyDataSetChanged();

                // 取消圆点选中
                idotLayout.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                idotLayout.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
}
