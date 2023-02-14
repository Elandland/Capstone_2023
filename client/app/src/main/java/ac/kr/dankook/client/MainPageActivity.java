package ac.kr.dankook.client;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.airbnb.lottie.LottieAnimationView;

public class MainPageActivity extends Activity {
    LottieAnimationView loading;
    ImageView image;
    ImageButton hart;
    ImageButton home;
    ImageButton profile;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        loading = findViewById(R.id.loading);
        image = findViewById(R.id.login_page_logoImageView);
        hart = findViewById(R.id.hart_navigation);
        home = findViewById(R.id.home_navigation);
        profile = findViewById(R.id.profile_navigation);

        loading.setAnimation("loading.json");
        loading.loop(true);
        loading.bringToFront();
        loading.playAnimation();

        image.bringToFront();
        ImageView matching = (ImageView)findViewById(R.id.login_page_logoImageView);
        matching.bringToFront();


        // 내비게이션 바 버튼
        hart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hart.setPressed(true);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setPressed(true);
            }
        });


    }
}
