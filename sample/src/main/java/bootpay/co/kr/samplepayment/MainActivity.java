package bootpay.co.kr.samplepayment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;


import smartwork.co.kr.bootpay.BootpayDialog;
import smartwork.co.kr.bootpay.model.Test;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Request first = new Request();
//        first.setApplication_id("593f8febe13f332431a8ddae");
//        first.setPg("danal");
//        first.setPrice(2939);
//        first.setName("블링블리일");
//        first.setMethod("card");
        new Gson().toJson(new Object());

        findViewById(R.id.main_button).setOnClickListener(view ->
                        BootpayDialog.init(getFragmentManager())
//                                .setModel(first)
                                .setApplicationId("593f8febe13f332431a8ddae")
                                .setPG("inicis")
                                .setMethod("card")
                                .setName("맥북프로임다")
                                .setOrderId(String.valueOf(System.currentTimeMillis()))
                                .setPrice(1000)
                                .addItem("마우스", 1, "123", 100)
                                .addItem("키보드", 1, "122", 200)
                                .setParams(new Test("test", 100, 10000))
                                .onConfirm(s -> Log.d("confirm", s == null ? "null" : s))
                                .onDone(s -> Log.d("done", s == null ? "null" : s))
                                .onCancel(s -> Log.d("cancel", s == null ? "null" : s))
                                .onError(s -> Log.d("error", s == null ? "null" : s))
                                .show()
        );
    }

    private void confirm() {

    }
}