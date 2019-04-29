package com.mystudy.studyviews.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mystudy.studyviews.R;
import com.mystudy.studyviews.adapter.RefreshAdapter;
import com.mystudy.studyviews.views.RefreshItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipeRefreshActivity extends BaseActivity {

    @BindView(R.id.rlv_recyclerview)
    RecyclerView rlvRecyclerview;
    @BindView(R.id.srl_swiperefresh)
    SwipeRefreshLayout srlSwiperefresh;

    List<String> mDatas = new ArrayList<>();
    private RefreshAdapter mRefreshAdapter;//一般的Adapter
//    private CommonAdapter<String> commonAdapter;//使用的是通用的单一的Adapter
//    private MultipleItemTypeAdapter<String> multipleItemTypeAdapter;//使用是多种ItemType的adapter

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initData() {//初始化数据
        for (int i = 0; i < 10; i++) {
            mDatas.add(" Item "+i);
        }
        initRecylerView();
    }

    @SuppressLint("WrongConstant")
    private void initRecylerView() {
        mRefreshAdapter = new RefreshAdapter(this,mDatas);
//        commonAdapter=new CommonAdapter<String>(mDatas, this, new MultiItemTypeSupport<String>() {
//            @Override
//            public int getLayoutId(int itemType) {
//                if (itemType==commonAdapter.TYPE_FOOTER){
//                    return R.layout.load_more;
//                }else if (itemType==commonAdapter.TYPE_ITEM){
//                    return R.layout.item_refresh_recylerview;
//                }
//                return 0;
//            }
//            @Override
//            public int getItemViewType(int position, String s) {
//                if (position+1==commonAdapter.getItemCount()){
//                    return commonAdapter.TYPE_FOOTER;
//                }else{
//                    return commonAdapter.TYPE_ITEM;
//                }
//            }
//        }) {
//            @Override
//            public void convert(ViewHolder holder, String s,int position) {
//                if (position+1==commonAdapter.getItemCount()){
//                    switch (commonAdapter.mLoadMoreStatus){
//                        case PULLUP_LOAD_MORE:
//                            holder.setText(R.id.tvLoadText,"上拉加载更多...");
//                            holder.setVisibility(R.id.pbLoad,View.GONE);
//                            break;
//                        case LOADING_MORE:
//                            holder.setText(R.id.tvLoadText,"正加载更多...");
//                            holder.setVisibility(R.id.pbLoad,View.VISIBLE);
//                            break;
//                        case NO_LOAD_MORE:
//                            holder.setVisibility(R.id.loadLayout, View.GONE);
//                            break;
//                    }
//                }else {
//                    holder.setText(R.id.tv_content, s);
//                }
//
//            }
//        };
//        commonAdapter=new CommonAdapter<String>(mDatas,this,R.layout.item_refresh_recylerview) {
//            @Override
//            public void convert(ViewHolder holder, String s, int position) {
//                holder.setText(R.id.tv_content,s);
//            }
//        };
//
//        multipleItemTypeAdapter= new MultipleItemTypeAdapter<String>(mDatas, this, new MultipleItemTypeSupport<String>() {
//            @Override
//            public int getLayoutId(int itemViewType) {
//                return itemViewType;
//            }
//
//            @Override
//            public int getItemViewType(int position, String s) {
//                if (s.startsWith("H")){
//                    return R.layout.item_refresh_recyclerviewright;
//                }
//                return R.layout.item_refresh_recylerview;
//            }
//        }) {
//            @Override
//            public void converts(ViewHolder holder, String s, int position) {
//                holder.setText(R.id.tv_content,s);
//            }
//        };

        mLinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,
                false);
        rlvRecyclerview.setLayoutManager(mLinearLayoutManager);

        //添加动画
        rlvRecyclerview.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        rlvRecyclerview.addItemDecoration(new RefreshItemDecoration(this,RefreshItemDecoration.VERTICAL_LIST));
        rlvRecyclerview.setLayoutManager(mLinearLayoutManager);
        rlvRecyclerview.setAdapter(mRefreshAdapter);
    }
    private void initListener() {

        initPullRefresh();

        initLoadMoreListener();

    }

    private void initPullRefresh() {
        srlSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> headDatas = new ArrayList<String>();
                        for (int i = 20; i <30 ; i++) {
                            headDatas.add("Heard Item "+i);
                        }
                        mDatas.addAll(0,headDatas);
                        mRefreshAdapter.notifyDataSetChanged();
//                        commonAdapter.notifyDataSetChanged();
//                        mRefreshAdapter.AddHeaderItem(headDatas);
                        //刷新完成
                        srlSwiperefresh.setRefreshing(false);
                        Toast.makeText(SwipeRefreshActivity.this, "更新了 "
                                +headDatas.size()+" 条目数据", Toast.LENGTH_SHORT).show();
                    }
                },3000);
            }
        });
    }

    private void initLoadMoreListener() {
        rlvRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem ;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mRefreshAdapter.getItemCount()) {
                    //设置正在加载更多
                    mRefreshAdapter.changeMoreStatus(mRefreshAdapter.LOADING_MORE);
//                    commonAdapter.mLoadMoreStatus=commonAdapter.LOADING_MORE;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            List<String> footerDatas = new ArrayList<String>();
                            for (int i = 0; i < 10; i++) {
                                footerDatas.add("footer  item" + i);
                            }
                            mDatas.addAll(footerDatas);
                            mRefreshAdapter.notifyDataSetChanged();
//                            mRefreshAdapter.AddFooterItem(footerDatas);
                            Toast.makeText(SwipeRefreshActivity.this,
                                    "更新了 " + footerDatas.size() + " 条目数据", Toast.LENGTH_SHORT).show();
                        }
                    }, 2000);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //最后一个可见的ITEM
                lastVisibleItem=layoutManager.findLastVisibleItemPosition();
            }
        });
    }
    private void initView() {//设置SwipeRefershView的加载颜色
        srlSwiperefresh.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);
    }
}
