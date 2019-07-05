package com.ziwenl.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.ziwenl.library.R;

/**
 * PackageName : com.ziwenl.library.dialog
 * Author : Ziwen Lan
 * Date : 2019/2/20
 * Time : 15:27
 * Introduction :
 */
public class DialogLoading extends Dialog {

    private Context mContext;
//    private AVLoadingIndicatorView loadingView;

    public DialogLoading(Context context) {
        super(context, R.style.Dialog);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_layout, null);
        setContentView(view);
//        loadingView = view.findViewById(R.id.loadingView);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        super.show();
    }

    public void show(boolean isCancelable) {
        setCancelable(isCancelable);
        show();
    }

//    @Override
//    public void dismiss() {
//        super.dismiss();
//        loadingView.reset();
//    }
}