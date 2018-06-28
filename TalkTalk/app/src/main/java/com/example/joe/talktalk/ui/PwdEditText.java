package com.example.joe.talktalk.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;

import com.example.joe.talktalk.R;
/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class PwdEditText extends AppCompatEditText {

    private boolean isShowPwd;

    public PwdEditText(Context context) {
        super(context);
    }

    public PwdEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PwdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        isShowPwd = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawableRight = getCompoundDrawables()[2];
            int startX = getRight() - drawableRight.getBounds().width() - getPaddingRight();
            int endX = getRight() - getPaddingRight();

            if (drawableRight != null && (event.getX() >= startX && event.getX() <= endX)) {
                changeStatus();
                event.setAction(MotionEvent.ACTION_CANCEL);
            }
        }
        return super.onTouchEvent(event);
    }

    private void changeStatus() {
        isShowPwd = !isShowPwd;
        if (isShowPwd) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_password_show, 0);
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo
                    .TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_password_hide, 0);
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        }
        setSelection(getText().length());
    }
}
