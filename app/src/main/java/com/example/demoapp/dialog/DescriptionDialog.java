package com.example.demoapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.demoapp.R;

public class DescriptionDialog {
    private static final String TAG = "DescriptionDialog";
    private Dialog dialog;

    public DescriptionDialog(Context context) {
        dialog = new Dialog(context, R.style.Theme_Transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_alert_dialog);

    }

    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    public void showDialog() {
        Log.d(TAG, "showDialog: ");
        try {
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            Log.e(TAG, "showDialog: ", e);
        }
    }

    public void dismissLoader() {
        Log.d(TAG, "dismissLoader: ");
        if (dialog != null && dialog.isShowing()) {
            Context context = ((ContextWrapper) dialog.getContext()).getBaseContext();

            if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                ((Activity) context).runOnUiThread(() -> dialog.dismiss());
            }
        }
    }

    public void setSubmitButtonListener(Button.OnClickListener buttonListener) {
        if (dialog != null) {
            dialog.findViewById(R.id.btnOk).setOnClickListener(buttonListener);
        }
    }


    public void setTextDescription(String textMessage) {
        if (dialog != null) {
            TextView t = dialog.findViewById(R.id.txtDescriptionText);
            t.setText("" + textMessage);
        }
    }
    public void setAuthor(String textMessage) {
        if (dialog != null) {
            TextView t = dialog.findViewById(R.id.txtAuthor);
            t.setText("" + textMessage);
        }
    }

}
