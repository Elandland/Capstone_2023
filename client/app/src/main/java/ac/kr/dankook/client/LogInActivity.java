package ac.kr.dankook.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import ac.kr.dankook.client.connect.RetrofitClient;
import ac.kr.dankook.client.connect.apiService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LogInActivity extends AppCompatActivity {


    private TextView mSignUpText;
    private EditText mIdEditText;
    private EditText mPassWordEditText;
    private Button mLoginButton;
    private CheckBox mCheckBox;

    Toast toast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);
        LoadViewById();

        // login시 id password 가져오기
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String name = mIdEditText.getText().toString();
                        String password=mPassWordEditText.getText().toString();

                        int result = -1;

                        try {
                            result=RequestLogin(name,password);
                        } catch (IOException e) {
                            // 예외 처리 코드 작성
                        }
                        Log.d("result", String.valueOf(result));
                        if (result == 1) {
                            // 메인(UI) 스레드에서 UI 업데이트 작업 실행
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent=new Intent(getApplicationContext(), MainPageActivity.class);
                                    startActivity(intent);
                                }
                            });

                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                                }
                            }, 0);
                        }
                        else {
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run()
                                {
                                    toast = Toast.makeText(getApplicationContext(), "유효하지 않은 아이디 혹은 비밀번호 입니다.", Toast.LENGTH_SHORT);
                                }
                            }, 0);

                        }
                    }

                });

                thread.start();

            }

        });
        mSignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    // 로그인
    private int RequestLogin(String name, String password) throws IOException {

        Retrofit retrofit = RetrofitClient.getClient();
        apiService api = retrofit.create(apiService.class);

        Call<String> call = api.login(name, password);
        Response<String> response = call.execute();

        if(response.isSuccessful()) {
            // client에서 session id를 안전한 저장소에 저장
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("sessionID", response.body());
            editor.apply();

            return 1;
        }
        else {
            return -1;
        }
    }

    // 누락 값 확인
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
