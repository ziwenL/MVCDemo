package com.ziwenl.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ziwenl.library.R;
import com.ziwenl.library.R2;
import com.ziwenl.library.utils.StringUtil;
import com.ziwenl.library.widget.BGButton;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * PackageName : com.ziwenl.library.dialog
 * Author : Ziwen Lan
 * Date : 2019/3/11
 * Time : 13:52
 * Introduction : 提示弹窗
 */
public class HintDialog extends Dialog {


    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_content)
    TextView mTvContent;
    @BindView(R2.id.btn_left)
    BGButton mBtnLeft;
    @BindView(R2.id.btn_right)
    BGButton mBtnRight;
    @BindView(R2.id.ll_btn_root)
    LinearLayout mLlBtnRoot;
    @BindView(R2.id.btn_confirm)
    BGButton mBtnConfirm;

    /**
     * @param context        上下文
     * @param title          标题  无则不显示标题
     * @param content        提示的文字
     * @param btnTextStrings 按钮显示的文字，最多长度为两个
     */
    public HintDialog(@NonNull Context context, CharSequence title, CharSequence content, String... btnTextStrings) {
        super(context, R.style.Dialog);
        this.setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_hint);
        ButterKnife.bind(this);
        updateText(title, content, btnTextStrings);
    }

    @OnClick({R2.id.btn_left, R2.id.btn_right, R2.id.btn_confirm})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_left) {
            //左键
            dismiss();
            if (mCallback != null) {
                mCallback.onClickLeftBtn();
            }
        } else if (i == R.id.btn_right) {
            //右键
            dismiss();
            if (mCallback != null) {
                mCallback.onClickRightBtn();
            }
        } else if (i == R.id.btn_confirm) {
            //中间键
            dismiss();
            if (mCallback != null) {
                mCallback.onClickConfirm();
            }
        }
    }

    public void updateText(CharSequence title, CharSequence content, String... btnTextStrings) {
        if (StringUtil.isEmpty(title.toString())) {
            mTvTitle.setVisibility(View.GONE);
        } else {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(title);
        }

        mTvContent.setText(content);
        if (btnTextStrings != null && btnTextStrings.length > 0) {
            if (btnTextStrings.length > 1) {
                mBtnLeft.setText(btnTextStrings[0]);
                mBtnRight.setText(btnTextStrings[1]);
                mLlBtnRoot.setVisibility(View.VISIBLE);
                mBtnConfirm.setVisibility(View.GONE);
            } else {
                mLlBtnRoot.setVisibility(View.GONE);
                mBtnConfirm.setVisibility(View.VISIBLE);
                mBtnConfirm.setText(btnTextStrings[0]);
            }
        }
    }

    private Object mTag;

    public void setTag(Object tag) {
        mTag = tag;
    }

    public Object getTag() {
        return mTag;
    }

    private Callback mCallback;

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onClickLeftBtn();

        void onClickRightBtn();

        void onClickConfirm();
    }
}
