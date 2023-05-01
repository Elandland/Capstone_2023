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
import androidx.appcompat.widget.AppCompatCheckBox;

public class SignUpActivity extends AppCompatActivity {

    private EditText NickNameEditText;

    private EditText PasswordEditText;

    private Button SignupButton;

    //체크박스 체크 여부 no check=0, check=1
    private int TermsAgree0=0;
    private int TermsAgree1=0;
    private int TermsAgree2=0;
    private int TermsAgree3=0;
    private int TermsAgree4=0;
    private int TermsAgree5=0;

    //체크박스
    AppCompatCheckBox check0; //전체 동의
    AppCompatCheckBox check1; //첫번째 약관 동의
    AppCompatCheckBox check2; //두번째
    AppCompatCheckBox check3; //세번째
    AppCompatCheckBox check4; //네번째
    AppCompatCheckBox check5; //다섯번째

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_page);
        LoadViewById();

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=NickNameEditText.getText().toString();
                String password=PasswordEditText.getText().toString();
                int result=RequestSignup(name,password);

            }
        });


    }


    private int RequestSignup(String name, String password){
        return 0;

    }
    private void LoadViewById(){
        if(NickNameEditText == null){
            NickNameEditText = findViewById(R.id.signup_nickname_editText);
            assert NickNameEditText != null : "fail load nicknameEditText";
        }
        if(PasswordEditText == null){
            PasswordEditText = findViewById(R.id.signup_password_editText);
            assert PasswordEditText != null : "fail load password editText";
        }
    }
}