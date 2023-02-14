package ac.kr.dankook.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.airbnb.lottie.LottieAnimationView;

public class MainPageActivity extends Activity {
    LottieAnimationView loading;
    ImageView image;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        loading = findViewById(R.id.loading);
        image = findViewById(R.id.login_page_logoImageView);

        loading.setAnimation("loading.json");
        loading.loop(true);
        loading.bringToFront();
        loading.playAnimation();

        image.bringToFront();


        ImageView matching = (ImageView) findViewById(R.id.login_page_logoImageView);


        matching.bringToFront();
    }
    }
