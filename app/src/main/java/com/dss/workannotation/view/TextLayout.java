package com.dss.workannotation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class TextLayout extends ViewGroup {

    private int offset = 60;

    public TextLayout(Context context) {
        super(context);
    }

    public TextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
        int width = 0;
        int height = 0;
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    TextLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    int spacing = getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin;
                    int childWidth = i * offset + child.getMeasuredWidth() + spacing;
                    width = Math.max(width, childWidth);
                }
                break;
            default:
                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    TextLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    int spacing = lp.topMargin + lp.bottomMargin;
                    if (i == 0) {//第一个加paddingTop
                        spacing += getPaddingTop();
                    } else if (i == childCount - 1) {//最后一个加paddingBottom
                        spacing += getPaddingBottom();
                    }
                    height += (child.getMeasuredHeight() + spacing);
                }
                break;
            default:
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            TextLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
            top += lp.topMargin;
            if (i == 0) {//如果是第一个，处理paddingTop
                top += getPaddingTop();
            }
            left = i * offset + lp.leftMargin + getPaddingLeft();
            right = child.getMeasuredWidth() + left;
            bottom = child.getMeasuredHeight() + top;
            child.layout(left, top, right, bottom);
            top += child.getMeasuredHeight() + lp.bottomMargin;
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new TextLayout.LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

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
