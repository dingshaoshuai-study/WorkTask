package com.dss.workannotation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    //每一行的view
    private List<View> lineView;
    //行view集合
    private List<List<View>> lineViewList;
    //每一行的行高
    private List<Integer> heightList;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        lineViewList = new ArrayList<>();
        lineView = new ArrayList<>();
        heightList = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        init();
        int flowLayoutWidth = 0;//当前所有行中最宽的那一行
        int flowLayoutHeight = 0;//记录行高度之和
        int lineMaxHeight = 0;//记录当前行子view最高的值
        int lineWidth = 0;//记录当前行子view宽度之和
        int totalChildCount = getChildCount();
        for (int i = 0; i < totalChildCount; i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            if (lineWidth + view.getMeasuredWidth() > widthSize) {//说明放不下，需要换行
                lineViewList.add(lineView);//放入总集合
                lineView = new ArrayList<>();//new一个新的行View
                flowLayoutWidth = Math.max(flowLayoutWidth, lineWidth);//历史最宽与当前行对比
                flowLayoutHeight += lineMaxHeight;//历史行高累加当前行高
                heightList.add(lineMaxHeight);//记录每一行的高度
                lineWidth = 0;//换行了，行宽归0
                lineMaxHeight = 0;//换行了，行高归0
            }
            lineWidth += view.getMeasuredWidth();//累加view的宽度==当前行的宽度
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            if (lp.height != LayoutParams.MATCH_PARENT) {//暂时先不处理layout_heigth = match_parent
                lineMaxHeight = Math.max(lineMaxHeight, view.getMeasuredHeight());//每放入一个控件得出当前行最高的那一个
            }
            lineView.add(view);//放入当前行

            //最后一行的时候，不会走到上面换号操作里
            if (i == totalChildCount - 1) {
                lineViewList.add(lineView);//放入总集合
                flowLayoutWidth = Math.max(flowLayoutWidth, lineWidth);//历史最宽与当前行对比
                flowLayoutHeight += lineMaxHeight;//历史行高累加当前行高
                heightList.add(lineMaxHeight);//记录每一行的高度
            }
            remeasureChild(widthMeasureSpec, heightMeasureSpec);
        }
        int resultWidth = widthMode == MeasureSpec.EXACTLY ? widthSize : flowLayoutWidth;
        int resultHeight = heightMode == MeasureSpec.EXACTLY ? heightSize : flowLayoutHeight;
        setMeasuredDimension(resultWidth, resultHeight);
    }

    private void remeasureChild(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < lineViewList.size(); i++) {
            int lineHeight = heightList.get(i);//每一行行高
            List<View> lineViews = lineViewList.get(i);//每一行的子View
            int size = lineViews.size();
            for (int j = 0; j < size; j++) {
                View child = lineViews.get(j);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.height == LayoutParams.MATCH_PARENT) {
                    int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
                    int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, lineHeight);
                    child.measure(childWidthSpec, childHeightSpec);
                }
            }
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int currX = 0;
        int currY = 0;
        for (int i = 0; i < lineViewList.size(); i++) {//一行一行的布局
            List<View> lineView = lineViewList.get(i);
            for (int j = 0; j < lineView.size(); j++) {//排列当前行的子View
                View view = lineView.get(j);
                int left = currX;
                int top = currY;
                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom);
                currX += view.getMeasuredWidth();
            }
            currY += heightList.get(i);
            currX = 0;
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }


    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p) && p instanceof LayoutParams;
    }


    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
