package ac.kr.dankook.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapPageActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    Button cancel, complete;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map_page);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync((this));

        cancel = (Button)findViewById(R.id.button1);
        complete = (Button)findViewById(R.id.button2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapPageActivity.this,  MainPageActivity.class);
                startActivity(intent);
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapPageActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;

        LatLng SEOUL = new LatLng(37.56, 126.97);

        /* 마커
        MarkerOptions markerOptions = new MarkerOptions();         // 마커
        markerOptions.position(SEOUL);
        markerOptions.title("서울");                 // 마커 제목
        markerOptions.snippet("한국의 수도");         // 마커 설명
        map.addMarker(markerOptions);
        */

        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL)); // 초기 위치
        map.animateCamera(CameraUpdateFactory.zoomTo(15)); // 줌의 정도
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // 지도 유형 설정
    }
}
