package ac.kr.dankook.client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import ac.kr.dankook.client.connect.RetrofitClient;
import ac.kr.dankook.client.connect.UserDto;
import ac.kr.dankook.client.connect.apiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private EditText NickNameEditText;

    private EditText PasswordEditText;

    private Spinner sexSpinner;

    private Spinner ageSpinner;

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






        sexSpinner = (Spinner)findViewById(R.id.spinner_gender);
        ageSpinner = (Spinner)findViewById(R.id.spinner_age);
        SignupButton = findViewById(R.id.signup_button);

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=NickNameEditText.getText().toString();
                String password=PasswordEditText.getText().toString();
                String sex = sexSpinner.getSelectedItem().toString();
                Long age = Long.parseLong(ageSpinner.getSelectedItem().toString());


                int result=RequestSignup(name, password, sex, age);
                Intent intent=new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
            }
        });


    }


    private int RequestSignup(String name, String password, String sex, Long age){
        String phone_num = "010-1234-1234";
        Character csex = 'm';

        if (sex.equals("남")) {
            csex = 'm';
        }
        else if(sex.equals("여")) {
            csex = 'w';
        }

        UserDto dto = new UserDto(name, csex, age, phone_num, password);


        // 서버 주소 설정
        RetrofitClient client = new RetrofitClient();
        Retrofit retrofit = client.getClient();
        apiService api = retrofit.create(apiService.class);



        api.register(dto).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("response", response.toString());
                Log.d("성송","성공");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("call", call.toString());
                Log.d("response", t.toString());
                Log.d("실패","실패");
            }
        });


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

