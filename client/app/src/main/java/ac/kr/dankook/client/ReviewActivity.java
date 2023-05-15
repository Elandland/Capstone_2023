package ac.kr.dankook.client;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

public class ReviewActivity extends AppCompatActivity {

    private EditText ReviewEditText;

    private Button ReviewSendButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_page);
        LoadViewById();

        ReviewSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String review=ReviewEditText.getText().toString();
                int result=RequestSignup(review);

            }
        });


    }


    private int RequestSignup(String review){
        return 0;

    }
    private void LoadViewById(){
        if(ReviewEditText == null){
            ReviewEditText = findViewById(R.id.review_editText);
            assert ReviewEditText != null : "fail load ReviewEditText";
        }
    }
}