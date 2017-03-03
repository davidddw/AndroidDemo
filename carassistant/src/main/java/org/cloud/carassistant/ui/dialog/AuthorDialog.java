package org.cloud.carassistant.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import org.cloud.carassistant.R;

/**
 * @author d05660ddw
 * @version 1.0 2017/3/3
 */
public class AuthorDialog extends Dialog {

    private Context mContext;

    public AuthorDialog(Context context) {
        super(context, R.style.Dialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_author, null);
        setContentView(view);
        getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
    }
}
