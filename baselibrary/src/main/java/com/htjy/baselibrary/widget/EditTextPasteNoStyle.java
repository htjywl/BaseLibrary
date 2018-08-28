package com.htjy.baselibrary.widget;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/6/7.
 */

public class EditTextPasteNoStyle extends AppCompatEditText {

    public EditTextPasteNoStyle(Context context) {
        super(context);
    }

    public EditTextPasteNoStyle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextPasteNoStyle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (id == android.R.id.paste) {
                id = android.R.id.pasteAsPlainText;
            }
        }
        return super.onTextContextMenuItem(id);
    }

}
