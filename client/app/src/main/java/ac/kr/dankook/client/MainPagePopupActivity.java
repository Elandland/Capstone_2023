package ac.kr.dankook.client;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainPagePopupActivity extends AppCompatActivity {

    Button yesbtn,nobtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_page_popup);
        yesbtn = (Button)findViewById(R.id.button1);
        nobtn = (Button)findViewById(R.id.button2);
    }

    public void yes(View v) {
        finish();
    }

    public void no(View v) {
        finish();
    }
}
