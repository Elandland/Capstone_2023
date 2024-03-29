package ac.kr.dankook.client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import java.io.IOException;

import ac.kr.dankook.client.connect.RetrofitClient;
import ac.kr.dankook.client.connect.apiService;
//import Team.server.service.dto.UserDto;
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

    public boolean isit = false;

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
                // 클릭 이벤트 발생 시 백그라운드 스레드에서 작업 실행
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String name = NickNameEditText.getText().toString();
                        String password = PasswordEditText.getText().toString();
                        String sex = sexSpinner.getSelectedItem().toString();
                        int age = Integer.parseInt(ageSpinner.getSelectedItem().toString());

                        int result = -1;

                        try {
                            result = RequestSignup(name, password, sex, age);
                        } catch (IOException e) {
                            // 예외 처리 코드 작성
                        }
                        Log.d("result", String.valueOf(result));
                        if (result == 1) {
                            // 메인(UI) 스레드에서 UI 업데이트 작업 실행
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent=new Intent(getApplicationContext(), LogInActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });

                thread.start(); // 백그라운드 스레드 시작
            }
        });



    }


    private int RequestSignup(String name, String password, String sex, int age) throws IOException {


        String phone_num = "01012341234";

        if (sex.equals("남")) {
            sex = "m";
        }
        else if(sex.equals("여")) {
            sex = "w";
        }

        Log.d("name", name);
        Log.d("password", password);
        Log.d("sex", String.valueOf(sex));
        Log.d("age",String.valueOf(age));

//        UserDto dto = new UserDto(name, sex, age, phone_num, password);


        // 서버 주소 설정
        Retrofit retrofit = RetrofitClient.getClient();
        apiService api = retrofit.create(apiService.class);

        Call<String> call = api.register(name, sex, age, phone_num, password);
        Response<String> response = call.execute();

        if(response.isSuccessful()) {
            Log.d("response", response.toString());
            Log.d("성공", "성공");
            return 1; // 회원가입 성공
        }
        else {
            Log.d("response", response.toString());
            Log.d("실패", "실패");
            return -1; // 회원가입 실패
        }




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

