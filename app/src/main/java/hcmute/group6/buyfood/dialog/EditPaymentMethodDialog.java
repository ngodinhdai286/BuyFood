package hcmute.group6.buyfood.dialog;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import hcmute.group6.buyfood.R;


public class EditPaymentMethodDialog {

    private int layout = R.layout.dialog_edit_payment_method;
    private View view;
    private Context context;
    private AlertDialog dialog;

    public CardView crdCOD, crdPaypal;


//findViewById = truy xuất vào control theo đúng id tương ứng truyền vào
    protected void mapping() {
        crdCOD = view.findViewById(R.id.editPaymentMethodDialog_crdCOD);
//        crdPaypal = view.findViewById(R.id.editPaymentMethodDialog_crdPaypal);
    }

    public EditPaymentMethodDialog(Context context, AlertDialog dialog) {
        this.context = context;
        this.dialog = dialog;
        view = View.inflate(this.context, layout, null);
        dialog.setView(view);
        onCreate();
    }

//    Khi chọn vào Edit Payment thì hộp thoại edit payment dialog sẽ được show lên
    private void onCreate() {
        mapping();
        setListenerEvent();
        dialog.show();
    }

    public View getView() {
        return this.view;
    }

    protected void setListenerEvent() {

    }
}
