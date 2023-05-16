package ac.kr.dankook.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class NotificationPageActivity extends AppCompatActivity {

    ToggleButton accept_alam_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        SetEvent();
    }

    private void SetEvent(){
        accept_alam_button=findViewById(R.id.toggle_alam_accept_Button);
        accept_alam_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //SharedPreferences shared=getSharedPreferences();
                assert false:"Not Implment alam_toggle_Button click Event";
                /*
                SharedPreferences.Editor editor=shared.edit();
                editor.putBoolean("isAceeptMatchingAlam",b);
                */

            }
        });
    }
}