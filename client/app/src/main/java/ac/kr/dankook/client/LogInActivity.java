package ac.kr.dankook.client;

import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {


    private TextView mSignUpText;
    private EditText mIdEditText;
    private EditText mPassWordEditText;
    private Button mLoginButton;
    private CheckBox mCheckBox;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);
        LoadViewById();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id=mIdEditText.getText().toString();
                String password=mPassWordEditText.getText().toString();
                int result=RequestLogin(id,password);

            }
        });


    }


    private int RequestLogin(String id, String password){

        return 0;
    }
    private void LoadViewById(){
        if(mSignUpText == null){
            mSignUpText = findViewById(R.id.signup_text);
            assert mSignUpText != null : "fail load signUpText";
        }
        if(mIdEditText == null){
            mIdEditText = findViewById(R.id.login_id_editText);
            assert mIdEditText != null : "fail load idEditText";
        }
        if(mPassWordEditText == null){
            mPassWordEditText = findViewById(R.id.login_password_editText);
            assert mPassWordEditText != null : "fail load password editText";
        }
        if(mLoginButton == null){
            mLoginButton = findViewById(R.id.login_button);
            assert mLoginButton != null : "fail load login button";
        }
        if(mCheckBox == null ){
            mCheckBox = findViewById(R.id.save_id_checkBox);
            assert mCheckBox != null : "fail load save id check box";
        }
    }
}
