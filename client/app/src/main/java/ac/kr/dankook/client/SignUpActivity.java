package ac.kr.dankook.client;

import android.os.Bundle;
import android.os.Debug;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mPhoneNumEditText;
    private EditText mEmailEditText;
    private Button mSignupButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_page);
        LoadViewById();

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=mNameEditText.getText().toString();
                String phoneNum=mPhoneNumEditText.getText().toString();
                String email=mEmailEditText.getText().toString();
                int result=RequestSignup(name,phoneNum,email);

            }
        });


    }


    private int RequestSignup(String name, String phoneNum, String email){
        return 0;

    }
    private void LoadViewById(){
        if(mNameEditText == null){
            mNameEditText = findViewById(R.id.signup_name_editText);
            assert mNameEditText != null : "fail load nameEditText";
        }
        if(mPhoneNumEditText == null){
            mPhoneNumEditText = findViewById(R.id.signup_phoneNum_editText);
            assert mPhoneNumEditText != null : "fail load phoneNum editText";
        }
        if(mEmailEditText == null){
            mEmailEditText = findViewById(R.id.signup_email_editText);
            assert mEmailEditText != null : "fail load emailEditText";
        }
        if(mSignupButton == null){
            mSignupButton = findViewById(R.id.signup_button);
            assert mSignupButton != null : "fail load signup button";
        }
    }
}