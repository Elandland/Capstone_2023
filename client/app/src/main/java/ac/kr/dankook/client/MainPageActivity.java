package ac.kr.dankook.client;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import ac.kr.dankook.client.MBTI_ResultActivity;
import ac.kr.dankook.client.ProfilePageActivity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainPageActivity extends Activity {
    LottieAnimationView loading;
    ImageView image;
    ImageButton hart;
    ImageButton home;
    ImageButton profile;


    private FusedLocationProviderClient fusedLocationClient;

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
                Intent intent = new Intent(MainPageActivity.this, MBTI_StartActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setPressed(true);
                Intent intent = new Intent(MainPageActivity.this, ProfilePageActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        // 팝업 창
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, MainPagePopupActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        Boolean check = false;
        if (check == true) {
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

    private void getCurrentLocation() {
        // 위치 권한 체크
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 위치 권한이 없는 경우, 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        // 위치 정보 요청 설정
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        // 위치 정보 요청 시작
        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                // 위치 정보를 받아서
                Location location = locationResult.getLastLocation();

                // 위도 경도를 변수에 저장
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();


            }
        }, null);
    }
}
