package ac.kr.dankook.client;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        // 진동 효과
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        long[] vibratePattern = new long[]{1000, 500};
        int repeat = 1;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(vibratePattern, repeat));
        } else {
            vibrator.vibrate(vibratePattern, repeat);
        }

    }
}
