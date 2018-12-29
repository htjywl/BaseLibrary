package com.htjy.baselibrary.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.htjy.baselibrary.utils.SizeUtils;
import com.lyb.besttimer.pluginwidget.view.recyclerview.decoration.DecorateDetail;

/**
 * <pre>
 *     author : jiangwei
 *     e-mail : jiangwei_android@163.com
 *     time   : 2017/01/30
 *     desc   : 通用grid分割线 可自定义 上下左右外部 以及内部
 * </pre>
 */
public class HongtuSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int oritation;
    private DecorateDetail decorateDetail;
    private int outLeft;
    private int outTop;
    private int outRight;
    private int outBottom;
    private int spacingY;
    private int spanCount;
    private int spacingX;

    public HongtuSpaceItemDecoration(int oritation, int spanCount, int outLeft, int outTop, int outRight, int outBottom, int spacingX, int spacingY, DecorateDetail decorateDetail) {
        this.spanCount = spanCount;
        this.spacingX = SizeUtils.dp2px(spacingX);
        this.spacingY = SizeUtils.dp2px(spacingY);
        this.outLeft = SizeUtils.dp2px(outLeft);
        this.outTop = SizeUtils.dp2px(outTop);
        this.outRight = SizeUtils.dp2px(outRight);
        this.outBottom = SizeUtils.dp2px(outBottom);
        this.decorateDetail = decorateDetail;
        this.oritation = oritation;
    }

    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        c.save();
        for (int index = 0; index < parent.getChildCount(); index++) {
            View childView = parent.getChildAt(index);
            int decoratedLeft = parent.getLayoutManager().getDecoratedLeft(childView);
            int left = childView.getLeft();
            int decoratedTop = parent.getLayoutManager().getDecoratedTop(childView);
            int top = childView.getTop();
            int decoratedRight = parent.getLayoutManager().getDecoratedRight(childView);
            int right = childView.getRight();
            int decoratedBottom = parent.getLayoutManager().getDecoratedBottom(childView);
            int bottom = childView.getBottom();
            if (decorateDetail != null) {
                decorateDetail.drawLeft(c,childView,parent,decoratedLeft, decoratedTop, left, bottom);
                decorateDetail.drawTop(c,childView,parent, left, decoratedTop, decoratedRight, top);
                decorateDetail.drawRight(c,childView,parent, right, top, decoratedRight, decoratedBottom);
                decorateDetail.drawBottom(c,childView,parent, decoratedLeft, bottom, right, decoratedBottom);
            }
        }
        c.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {


        if (oritation == GridLayoutManager.VERTICAL) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column
            if (position < spanCount) {
                outRect.top = outTop;
            } else {
                outRect.top = spacingY / 2;
            }

            if (position > parent.getAdapter().getItemCount() - spanCount - 1) {
                outRect.bottom = outBottom;
            } else {
                outRect.bottom = spacingY / 2;
            }


            switch (column) {
                case 0:
                    outRect.left = outLeft;
                    outRect.right = spacingX / 2;
                    break;

                case 1:
                    outRect.right = outRight;
                    outRect.left = spacingX / 2;
                    break;

                default:
                    outRect.left = spacingX / 2;
                    outRect.right = spacingX / 2;
                    break;

            }
        } else {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column


            if (position < spanCount) {
                outRect.left = outLeft;
            } else {
                outRect.left = spacingX / 2;
            }

            if (position > parent.getAdapter().getItemCount() - spanCount - 1) {
                outRect.right = outRight;
            } else {
                outRect.right = spacingX / 2;
            }

            switch (column) {
                case 0:
                    outRect.top = outTop;
                    outRect.bottom = spacingY / 2;
                    break;

                case 1:
                    outRect.bottom = outBottom;
                    outRect.top = spacingY / 2;
                    break;

                default:
                    outRect.top = spacingY / 2;
                    outRect.bottom = spacingY / 2;
                    break;

            }
        }


       /* if (column == 0) {

        } else {
            outRect.left = spacingX / 2;
        }


        if (includeEdge) {
            outRect.left = spacingX - column * spacingX / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacingX / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacingY;
            }
            outRect.bottom = spacingY; // item bottom
        } else {
            outRect.left = column * spacingX / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacingX - (column + 1) * spacingX / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacingY; // item top
            }
        }*/
    }

}