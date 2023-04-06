package ac.kr.dankook.client;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainPopupLoadingActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        // 현재는 3초간 지연 -> 특정 조건을 만족할 때 까지 지연
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainPopupLoadingActivity.this,  MapPageActivity.class);
                startActivity(intent);
            }
        }, 3000);
        // 3초 후 지도 창 이동


        finish();
    }

}
