package ac.kr.dankook.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import io.socket.engineio.client.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

public class ChatingPageActivity extends AppCompatActivity {

    ArrayList<ChatData> mChatList;
    ChatingViewAdapter mApt;

    Socket mSocket;

    MapView mMapFragment;
    LatLng mOtherLocation;
    LatLng mMyLocation;
    LocationManager mLocationManager;
    Marker mOtherMarker;
    MarkerOptions mOtherMarkerOption;
    Marker mMyMarker;
    MarkerOptions mMyMarkerOption;

    LocationListener mGPSListener;
    GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chating_page);
        mChatList = new ArrayList<>();
        mApt = new ChatingViewAdapter(this, R.layout.chating_view, mChatList);
        ListView chatListView = (ListView) findViewById(R.id.chat_list);
        chatListView.setAdapter(mApt);
        Button sendingMessageButton = findViewById(R.id.send_chat_button);
        EditText editMessageView = findViewById(R.id.chat_input_text);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mMapFragment = (MapView)findViewById(R.id.chating_back_ground_map);
        mGPSListener = new GPSListener();
        mMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mGoogleMap = googleMap;
                System.out.println("BBB");
                LatLng SEOUL = new LatLng(37.56, 126.97);
                mGoogleMap.addMarker(new MarkerOptions().position(SEOUL).title("Marker in Seoul"));


                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL)); // 초기 위치
            }
        });
        try {
            mSocket = new Socket(API_URL.SERVER_URL);
            mSocket.on(Socket.EVENT_OPEN, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Sucesss");
                }
            });
            mSocket.on(Socket.EVENT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Error");
                }
            });
            mSocket.open();

            sendingMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String inputMessage = editMessageView.getText().toString();
                    if (inputMessage.compareTo("") != 0) {
                        editMessageView.setText("");

                        JSONObject sendData = new JSONObject();
                        try {
                            sendData.put("content", inputMessage);
                            sendData.put("msg_date",
                                    new SimpleDateFormat("yyyyMMDDHHMMSS").
                                            format(System.currentTimeMillis()));
                            sendData.put("user_id", null);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Send");
                        mSocket.emit("Send_chat_message", sendData)
                                .on("send_success", new Emitter.Listener() {
                                    @Override
                                    public void call(Object... args) {
                                        JSONObject recivedData = (JSONObject) args[0];
                                        String message;
                                        String day;
                                        try {
                                            message = recivedData.getString("content");
                                            day = recivedData.getString("msg_date");
                                            mChatList.add(new ChatData(message, true, day));

                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                });


                    }
                }
            });

            mSocket.on("other_sending_message", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject recivedData = (JSONObject) args[0];
                    String message;
                    String day;
                    try {
                        message = recivedData.getString("content");
                        day = recivedData.getString("msg_date");
                        assert false;
                        mChatList.add(new ChatData(message, false, day));
                        mApt.notifyDataSetChanged();

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            mSocket.on("other_location_message", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject other_location_Data = (JSONObject) args[0];
                    double latitude;
                    double longtitude;
                    try {
                        latitude = other_location_Data.getDouble("latitude");
                        longtitude = other_location_Data.getDouble("longitude");
                        mOtherLocation = new LatLng(latitude,longtitude);
                        showOtherLocationMarker();
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(
                                        new LatLng(
                                                (mMyLocation.latitude+mOtherLocation.latitude)*0.5,
                                                (mMyLocation.longitude+mOtherLocation.longitude)*0.5)
                                )
                        );

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });


        } catch (Exception e) {
            System.out.println("Excep");
            e.printStackTrace();
        }

    }

    public void startLocationService() {
        try {
            Location location = null;

            long minTime = 60;        // 0초마다 갱신 - 바로바로갱신
            float minDistance = 0;

            if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    String message = "최근 위치1 -> Latitude : " + latitude + "\n Longitude : " + longitude;

                    showCurrentLocation(latitude, longitude);
                }

                //위치 요청하기
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, mGPSListener);

            } else if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    showCurrentLocation(latitude, longitude);

                }


                //위치 요청하기
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, mGPSListener);
                //mLocationManager.removeUpdates(mGPSListener);
                Log.i("MyLocTest", "requestLocationUpdates() 내 위치2에서 호출시작 ~~ ");
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    class GPSListener implements LocationListener {

        // 위치 확인되었을때 자동으로 호출됨 (일정시간 and 일정거리)
        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            String message = "내 위치는 Latitude : " + latitude + "\nLongtitude : " + longitude;

            showCurrentLocation(latitude, longitude);
            Log.i("MyLocTest", "onLocationChanged() 호출되었습니다.");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // GPS provider를 이용전에 퍼미션 체크
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            } else {

                if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mGPSListener);
                    //mLocationManager.removeUpdates(mGPSListener);
                } else if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mGPSListener);
                    //mLocationManager.removeUpdates(mGPSListener);
                }

                if (mGoogleMap != null) {
                    mGoogleMap.setMyLocationEnabled(true);
                }
                Log.i("MyLocTest", "onResume에서 requestLocationUpdates() 되었습니다.");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(mGPSListener);

        if (mGoogleMap != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mGoogleMap.setMyLocationEnabled(false);
        }
    }

    private void showCurrentLocation(double latitude, double longitude) {
        mMyLocation = new LatLng(latitude, longitude);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(
                        (mMyLocation.latitude+mOtherLocation.latitude)*0.5,
                        (mMyLocation.longitude+mOtherLocation.longitude)*0.5)
                )
        );
        showMyLocationMarker();
    }

    private void showMyLocationMarker() {
        if (mMyMarkerOption == null) {
            mMyMarkerOption = new MarkerOptions(); // 마커 객체 생성
            mMyMarkerOption.position(mMyLocation);
            mMyMarker = mGoogleMap.addMarker(mMyMarkerOption);
        } else {
            mMyMarker.remove(); // 마커삭제
            mMyMarkerOption.position(mMyLocation);
            mMyMarker = mGoogleMap.addMarker(mMyMarkerOption);
        }
    }

    private void showOtherLocationMarker() {
        if (mOtherMarkerOption == null) {
            mOtherMarkerOption = new MarkerOptions(); // 마커 객체 생성
            mOtherMarkerOption.position(mOtherLocation);
            mOtherMarker = mGoogleMap.addMarker(mMyMarkerOption);
        } else {
            mOtherMarker.remove(); // 마커삭제
            mOtherMarkerOption.position(mOtherLocation);
            mOtherMarker = mGoogleMap.addMarker(mOtherMarkerOption);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}