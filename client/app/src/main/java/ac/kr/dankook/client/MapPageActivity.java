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

        LatLng seoul = new LatLng(37.56, 126.97);

        MarkerOptions options = new MarkerOptions();
        options.position(seoul)
                .title("서울")
                .snippet("한국의 수도");
        map.addMarker(options);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 18));
    }
}
