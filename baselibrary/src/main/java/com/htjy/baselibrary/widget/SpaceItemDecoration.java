package com.htjy.baselibrary.widget;

import android.graphics.Rect;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
/**
 * <pre>
 *     author : jiangwei
 *     e-mail : jiangwei_android@163.com
 *     time   : 2017/08/14
 *     desc   :
 *     version: 1.0
 *     目前用在考勤的选择条件界面
 * </pre>
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private  int spacingY;
    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public SpaceItemDecoration(int spanCount, int spacing, int spacingY, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.spacingY = spacingY;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacingY;
            }
            outRect.bottom = spacingY; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacingY; // item top
            }
        }
    }

}