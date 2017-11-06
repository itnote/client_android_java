package bootpay.co.kr.samplepayment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.CancelListener;
import kr.co.bootpay.ConfirmListener;
import kr.co.bootpay.DoneListener;
import kr.co.bootpay.ErrorListener;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.enums.PG;

public class MainActivity extends AppCompatActivity {

    private int stuck = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 해당 프로젝트(안드로이드)의 application id 값을 설정합니다. 통계를 위해 꼭 필요합니다.
        BootpayAnalytics.init(this, "59a7e647396fa64fcad4a8c2");

        // 유저 로그인 시점에 호출
        BootpayAnalytics.login(
                "testUser", // user 고유 id 혹은 로그인 아이디
                "testUser@gmail.com", // user email
                "username", // user 이름
                "0", //1: 남자, 0: 여자
                "861014", // user 생년월일 앞자리
                "01012345678", // user 휴대폰 번호
                "충청"); //  서울|인천|대구|대전|광주|부산|울산|경기|강원|충청북도|충북|충청남도|충남|전라북도|전북|전라남도|전남|경상북도|경북|경상남도|경남|제주|세종 중 택 1
    }

    public void onClick_request(View v) {
        BootpayAnalytics.start(
                "item_list", // 페이지를 구분하는 주소
                "아이템", // 페이지 유형|카테고리|태그
                "", // 대표 상품 이미지 url
                "1", // 대표 상품의 고유 키
                "철산동핫도그"); // 대표 상품명

        Bootpay.init(getFragmentManager())
                .setApplicationId("59a7e647396fa64fcad4a8c2") // 해당 프로젝트(안드로이드)의 application id 값
                .setPG(PG.KCP) // 결제할 PG 사
                .setMethod(Method.CARD) // 결제수단
                .setName("맥북프로임다") // 결제할 상품명
                .setOrderId("1234") // 결제 고유번호
                .setPrice(1000) // 결제할 금액
                .addItem("마우스", 1, "123", 100) // 주문정보에 담길 상품정보, 통계를 위해 사용
                .addItem("키보드", 1, "122", 200) // 주문정보에 담길 상품정보, 통계를 위해 사용
                .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                    @Override
                    public void onConfirmed(@Nullable String message) {
                        if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                        Log.d("confirm", message);
                    }
                })
                .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                    @Override
                    public void onDone(@Nullable String message) {
                        Log.d("done", message);
                    }
                })
                .onCancel(new CancelListener() { // 결제 취소시 호출
                    @Override
                    public void onCancel(@Nullable String message) {
                        Log.d("cancel", message);
                    }
                })
                .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                    @Override
                    public void onError(@Nullable String message) {
                        Log.d("error", message);
                    }
                })
                .show();
    }
}

