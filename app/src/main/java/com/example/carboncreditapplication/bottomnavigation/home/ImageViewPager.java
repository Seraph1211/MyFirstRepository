package com.example.carboncreditapplication.bottomnavigation.home;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carboncreditapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ImageViewPager {
    private Context context;
    private Activity activity;
    private View mView;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager mViewPager;
    private TextView mTvPagerTitle;

    //轮播的图片集合
    private List<ImageView> mImageList;
    private int imageNum; //轮播图片数量

    private String[] mImageTitles;//标题集合
    private int previousPosition = 0;//前一个被选中的position
    private List<View> mDots = new ArrayList<>();//小点

    private boolean isStop = false;//线程是否停止
    private static int PAGER_TIME = 5000;//间隔时间

    // 在values文件假下创建了pager_image_ids.xml文件，并定义了4张轮播图对应的id，用于点击事件
    private int[] image_ids = new int[]{R.id.pager_image1, R.id.pager_image2, R.id.pager_image3, R.id.pager_image4};

    public ImageViewPager(Context context, View mView){
        this.context = context;
        this.mView = mView;
        this.activity = (Activity) context;
    }


    /**
     * 第一步、初始化控件
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void init() {
        mViewPager = mView.findViewById(R.id.viewPager);
        mTvPagerTitle = mView.findViewById(R.id.tv_pager_title);

        initData();//初始化数据
        initView();//初始化View，设置适配器
        autoPlayView();//开启线程，自动播放
    }

    /**
     * 第二步、初始化数据（图片、标题、点击事件）
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void initData() {
        //初始化标题列表和图片
        imageNum = 4;
        mImageTitles = new String[]{"这是一个好看的标题1", "这是一个优美的标题2", "这是一个快乐的标题3", "这是一个开心的标题4"};
        int[] imageRes = new int[]{R.drawable.img8, R.drawable.img1, R.drawable.img12, R.drawable.img3};
        //添加图片到图片列表里
        mImageList = new ArrayList<>();
        ImageView iv;
        for (int i = 0; i < imageRes.length; i++) {
            iv = new ImageView(context);
            iv.setBackgroundResource(imageRes[i]);//设置图片
            iv.setId(image_ids[i]);//顺便给图片设置id
            iv.setOnClickListener(new pagerImageOnClick());//设置图片点击事件
            mImageList.add(iv);
        }

        //添加轮播点
        LinearLayout linearLayoutDots =  mView.findViewById(R.id.lineLayout_dot);
        mDots = addDots(linearLayoutDots, fromResToDrawable(context, R.drawable.ic_dot_normal), mImageList.size());//其中fromResToDrawable()方法是我自定义的，目的是将资源文件转成Drawable


    }

    //图片点击事件
    private class pagerImageOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pager_image1:
                    Toast.makeText(context, "图片1被点击", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pager_image2:
                    Toast.makeText(context, "图片2被点击", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pager_image3:
                    Toast.makeText(context, "图片3被点击", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pager_image4:
                    Toast.makeText(context, "图片4被点击", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /**
     * 第三步、给PagerViw设置适配器，并实现自动轮播功能
     */
    public void initView() {
        viewPagerAdapter = new ViewPagerAdapter(mImageList, mViewPager);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //伪无限循环，滑到最后一张图片又从新进入第一张图片
                int newPosition = position % mImageList.size();
                // 把当前选中的点给切换了, 还有描述信息也切换
                mTvPagerTitle.setText(mImageTitles[newPosition]);//图片下面设置显示文本

                //设置轮播点
                LinearLayout.LayoutParams newDotParams = (LinearLayout.LayoutParams) mDots.get(newPosition).getLayoutParams();
                newDotParams.width = 28;
                newDotParams.height = 28;


                LinearLayout.LayoutParams oldDotParams = (LinearLayout.LayoutParams) mDots.get(previousPosition).getLayoutParams();
                oldDotParams.width = 16;
                oldDotParams.height = 16;


                /*
                Log.d(TAG, "onPageSelected: new: "+mDots.get(newPosition).getLayoutParams().width);
                Log.d(TAG, "onPageSelected: previous: "+mDots.get(previousPosition).getLayoutParams().width);
                Log.d(TAG, "onPageSelected,newDotParams:"+newDotParams);
                Log.d(TAG, "onPageSelected, newPosition:"+newPosition);
                Log.d(TAG, "onPageSelected, previousPosition:"+previousPosition);
                Log.d(TAG, "onPageSelected: position: "+position);
                */

                // 把当前的索引赋值给前一个索引变量, 方便下一次再切换.
                previousPosition = newPosition;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setFirstLocation();
    }

    /**
     * 第四步：设置刚打开app时显示的图片和文字
     */
    private void setFirstLocation() {
        mTvPagerTitle.setText(mImageTitles[previousPosition]);
        // 把ViewPager设置为默认选中Integer.MAX_VALUE / t2，从十几亿次开始轮播图片，达到无限循环目的;
        int m = (Integer.MAX_VALUE / 2) % mImageList.size();
        int currentPosition = Integer.MAX_VALUE / 2 - m;
        mViewPager.setCurrentItem(currentPosition);
    }

    /**
     * 第五步: 设置自动播放,每隔PAGER_TIME秒换一张图片
     */
    private void autoPlayView() {
        //自动播放图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                        }
                    });
                    SystemClock.sleep(PAGER_TIME);
                }
            }
        }).start();
    }


    /**
     * 资源图片转Drawable
     *
     * @param context
     * @param resId
     * @return
     */
    public Drawable fromResToDrawable(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }


    /**
     * 动态添加一个点
     *
     * @param linearLayout 添加到LinearLayout布局
     * @param background   设置
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public int addDot(LinearLayout linearLayout, Drawable background) {
        View dot = new View(context);
        LinearLayout.LayoutParams dotParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dotParams.width = 16;
        dotParams.height = 16;
        dotParams.setMargins(4, 0, 4, 0);
        dot.setLayoutParams(dotParams);
        dot.setBackground(background);
        dot.setId(View.generateViewId());
        linearLayout.addView(dot);
        return dot.getId();
    }

    /**
     * 添加多个轮播小点到横向线性布局
     *
     * @param linearLayout
     * @param background 点的图片资源，所用的点共用一张图片
     * @param number  点的数量，与轮播的图片数量一致
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public List<View> addDots(LinearLayout linearLayout, Drawable background, int number) {
        List<View> dots = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            int dotId = addDot(linearLayout, background);
            dots.add(mView.findViewById(dotId));
        }
        return dots;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void reloadViewPager(List<ImageView> imageViewList, String[] mImageTitles, int imageNum){
        this.imageNum = imageNum;
        this.mImageList = imageViewList;
        this.mImageTitles = mImageTitles;

        //添加轮播点
        LinearLayout linearLayoutDots = activity.findViewById(R.id.lineLayout_dot);
        mDots = addDots(linearLayoutDots, fromResToDrawable(context, R.drawable.ic_dot_normal), mImageList.size());//其中fromResToDrawable()方法是我自定义的，目的是将资源文件转成Drawable

        initView();//初始化View，设置适配器
        // ???是否需要？   autoPlayView();//开启线程，自动播放
    }

    public List<ImageView> getmImageList() {
        return mImageList;
    }

    public void setmImageList(List<ImageView> mImageList) {
        this.mImageList = mImageList;
    }

    public String[] getmImageTitles() {
        return mImageTitles;
    }

    public void setmImageTitles(String[] mImageTitles) {
        this.mImageTitles = mImageTitles;
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }
}



