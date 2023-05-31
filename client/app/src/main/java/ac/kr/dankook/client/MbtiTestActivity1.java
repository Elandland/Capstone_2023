package ac.kr.dankook.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;

import ac.kr.dankook.client.connect.RetrofitClient;
import ac.kr.dankook.client.connect.apiService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MbtiTestActivity1 extends Activity {
    int mCurrentQuestionIndex;
    boolean[] mIsSeletedUpAnswer;

    private final String[] UP_ANSWER_TEXT_ARRAY=new String[]{
            "사람들과 놀러 다니길 좋아한다.",
            "활발하고 적극적이라고 한다.",
            "사람들과 \n시간을 보내고 싶어하는 편이다.",
            "말이 되냐?",
            "이렇게 끝난다고?? 아쉽네...",
            "과거의 아픔이 비수처럼 꽂힌다.",
            "역시 나야!",
            "왜?(진짜 궁금함)",
            "무슨 일 있어?",
            "시간 맞춰 끝내야지",
            "우리 몇 명이지? 저번에 이거 먹었으니 이건 빼자",
            "아이 망했네!"
    };

    private final String[] QUESTION_TEXT_ARRAY=new String[]{
            "평소에 나는",
            "주위 사람들은 나에게",
            "휴일에 나는",
            "티라노사우르스랑 트리케라톱스랑 싸우면...",
            "열린 결말이 나면?",
            "이어폰을 꽂고 슬픈 노래를 들으며 길을 걷고 있다.",
            "와 그정도 밖에 안했는데 그만큼 나온다고!?",
            "너를 그냥 좋아해",
            "오늘 술먹자",
            "할 일은?",
            "친구들과 음식을 시킬 때?",
            "다음주가 시험이라면?"
    };

    private final String[] DOWN_ANSWER_TEXT_ARRAY=new String[]{
            "사람들과 놀러 다니길 좋아한다.",
            "활발하고 적극적이라고 한다.",
            "사람들과 \n시간을 보내고 싶어하는 편이다.",
            "말이 되냐?",
            "이렇게 끝난다고?? 아쉽네...",
            "과거의 아픔이 비수처럼 꽂힌다.",
            "역시 나야!",
            "왜?(진짜 궁금함)",
            "무슨 일 있어?",
            "시간 맞춰 끝내야지",
            "우리 몇 명이지? 저번에 이거 먹었으니 이건 빼자",
            "아이 망했네!"
    };
    Button mUpAnswerButton;
    TextView mQuestionAnswerTextView;
    Button mDownAnswerButton;

    ImageView mMBTITestProgressImageView;

    int result;

    String name;

    String mbti;

    private final int QUESTION_COUNT=12;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbti_test_1);

        mCurrentQuestionIndex=0;
        mIsSeletedUpAnswer=new boolean[QUESTION_COUNT];
        mUpAnswerButton=findViewById(R.id.mbti_answer_button_up);
        mDownAnswerButton=findViewById(R.id.mbti_answer_down_button);
        mQuestionAnswerTextView=findViewById(R.id.mbti_queustion_textView);
        mMBTITestProgressImageView=findViewById(R.id.mbti_test_progress_imageView);

        mUpAnswerButton.setText(UP_ANSWER_TEXT_ARRAY[mCurrentQuestionIndex]);
        mDownAnswerButton.setText(DOWN_ANSWER_TEXT_ARRAY[mCurrentQuestionIndex]);
        mQuestionAnswerTextView.setText(QUESTION_TEXT_ARRAY[mCurrentQuestionIndex]);

        mbti = "ISFP";

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    result = setMbti(mbti);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(result);
            }
        });
        thread.start();



        SetEvent();


    }

    int setMbti(String mbti) throws IOException {
        Retrofit retrofit = RetrofitClient.getClient();
        apiService api = retrofit.create(apiService.class);

        Call<String> call = api.setMbti("ISFP");
        Response<String> response = call.execute();

        if (response.isSuccessful()) {
            return 1;
        } else {
            return -1;
        }
    }


    void LoadMBTI_ResultPage(int mbti){
        Intent intent=new Intent(getApplicationContext(), MainPageActivity.class);
        startActivity(intent);
    }

    int GetMbti(){
        int mbti=0;
        for(int i=0;i<4;++i){
            int up_cnt=0;
            for(int j=0;j<3;++j){
                if(mIsSeletedUpAnswer[i*3+j]){
                    ++up_cnt;
                }
            }
            if(up_cnt>1){
                mbti|=(1<<(3-i));
            }
        }
        return mbti;
    }



    void ProgressUpdate(boolean isClickUpButton){


        mIsSeletedUpAnswer[mCurrentQuestionIndex]=isClickUpButton;
        mCurrentQuestionIndex++;

        if(mCurrentQuestionIndex == QUESTION_COUNT){
            int mbti_result=GetMbti();
            LoadMBTI_ResultPage(mbti_result);
            return;
        }

        mUpAnswerButton.setText(UP_ANSWER_TEXT_ARRAY[mCurrentQuestionIndex]);
        mDownAnswerButton.setText(DOWN_ANSWER_TEXT_ARRAY[mCurrentQuestionIndex]);
        mQuestionAnswerTextView.setText(QUESTION_TEXT_ARRAY[mCurrentQuestionIndex]);
        ConstraintLayout.LayoutParams progressImageViewParms
                =(ConstraintLayout.LayoutParams)mMBTITestProgressImageView.getLayoutParams();
        progressImageViewParms.matchConstraintPercentWidth=mCurrentQuestionIndex/(float)(QUESTION_COUNT);
        mMBTITestProgressImageView.setLayoutParams(progressImageViewParms);
    }
    void SetEvent(){

        mUpAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressUpdate(true);

            }
        });

        mDownAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressUpdate(false);
            }
        });
    }
}
