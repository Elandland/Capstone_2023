package ac.kr.dankook.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import io.socket.client.IO;
import io.socket.client.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

public class ChatingPageActivity extends AppCompatActivity {


    ArrayList<ChatData> mChatList;
    ChatingViewAdapter mApt;


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
        Button matching_cancel_button=findViewById(R.id.matching_cancel_button);
        Button matching_success_button=findViewById(R.id.matching_accept_button);


        try {
            UserInfomation.GetSocket().on("chat_message", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String content=(String) args[0];
                    String sendingUserId=(String) args[1];
                    String date=(String)args[2];
                    mChatList.add(new ChatData(
                            content,
                            UserInfomation.GetUserIDOrNull()==sendingUserId,
                            date));
                    mApt.notifyDataSetChanged();
                }
            });
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
                        UserInfomation.GetSocket().emit("Send_chat_message", sendData);
                    }
                }
            });


            matching_cancel_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject sendMatchingCancelData=new JSONObject();
                    try {
                        sendMatchingCancelData.put("chating_room_id", null);
                        sendMatchingCancelData.put("click_user_id", UserInfomation.GetUserIDOrNull());
                        UserInfomation.GetSocket().emit("matching_cancel",sendMatchingCancelData);
                    }catch (Exception e){

                    }

                }
            });
            matching_success_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject sendMatchingCancelData=new JSONObject();
                    try {
                        sendMatchingCancelData.put("chating_room_id", null);
                        sendMatchingCancelData.put("clicker", null);
                        UserInfomation.GetSocket().emit("matching_success",sendMatchingCancelData)
                                .on("matching_success", new Emitter.Listener() {
                                    @Override
                                    public void call(Object... args) {
                                        Intent intent=new Intent(getApplicationContext(), ReviewActivity.class);
                                        startActivity(intent);
                                    }
                                });
                    }catch (Exception e){

                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Excep");
            e.printStackTrace();
        }

    }

}