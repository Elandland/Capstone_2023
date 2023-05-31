package ac.kr.dankook.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Debug;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.TreeMap;

public class MBTI_ResultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbti_result);
        Intent intent = getIntent();
        assert intent!=null ;
        int mbti=intent.getIntExtra("mbti",16);

        eMBTI mbti_enum=eMBTI.values()[mbti];
        LoadView(mbti_enum);
        SetExplainText(mbti_enum);
        SetGoodMBTItextView(mbti_enum);
        SetBadMBTItextView(mbti_enum);

        Button btn=findViewById(R.id.go_home_button_from_mbti_result);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainPageActivity.class);
                startActivity(intent);
            }
        });

    }
    void LoadView(eMBTI mbti){
        TextView mbti_result_text_view=findViewById(R.id.user_mbti_text_in_mbti_result_page);
        ImageView logoImageView=findViewById(R.id.mbti_result_imageView);
        mbti_result_text_view.setText(mbti.toString());

        switch (mbti) {
            case ISFJ:
                logoImageView.setImageResource(R.drawable.isfj_image);
                break;
            case INFJ:
                logoImageView.setImageResource(R.drawable.infj_image);
                break;
            case INFP:
                logoImageView.setImageResource(R.drawable.infp_image);
                break;
            case INTJ:
                logoImageView.setImageResource(R.drawable.intj_image);
                break;
            case INTP:
                logoImageView.setImageResource(R.drawable.intp_image);
                break;
            case ISFP:
                logoImageView.setImageResource(R.drawable.isfp_image);
                break;
            case ISTJ:
                logoImageView.setImageResource(R.drawable.istj_image);
                break;
            case ISTP:
                logoImageView.setImageResource(R.drawable.istp_image);
                break;
            case ENFJ:
                logoImageView.setImageResource(R.drawable.enfj_image);
                break;
            case ENFP:
                logoImageView.setImageResource(R.drawable.enfp_image);
                break;
            case ENTJ:
                logoImageView.setImageResource(R.drawable.entj_image);
                break;
            case ENTP:
                logoImageView.setImageResource(R.drawable.entp_image);
                break;
            case ESFJ:
                logoImageView.setImageResource(R.drawable.esfj_image);
                break;
            case ESFP:
                logoImageView.setImageResource(R.drawable.esfp_image);
                break;
            case ESTJ:
                logoImageView.setImageResource(R.drawable.estj_image);
                break;
            case ESTP:
                logoImageView.setImageResource(R.drawable.estp_image);
                break;
        }
    }


    eMBTI[] GetGoodMBTIArray(eMBTI mbti){
        switch (mbti){
            case ENTJ:
                return new eMBTI[]{eMBTI.ISFP,eMBTI.INFP,eMBTI.ESFP,eMBTI.ESTP};
            case ENTP:
                return new eMBTI[]{eMBTI.ISFJ,eMBTI.ISTJ,eMBTI.ENTP,eMBTI.ESTJ};
            case INTJ:
                return new eMBTI[]{eMBTI.ESFP,eMBTI.ESTP,eMBTI.ISFP,eMBTI.INFP};
            case INTP:
                return new eMBTI[]{eMBTI.ESFJ,eMBTI.ENFJ,eMBTI.ISFJ,eMBTI.INFJ};
            case ESTJ:
                return new eMBTI[]{eMBTI.INFP,eMBTI.ISFP,eMBTI.INTP,eMBTI.ENTP};
            case ESFJ:
                return new eMBTI[]{eMBTI.INTP,eMBTI.ISTP,eMBTI.ENTP,eMBTI.ENFP};
            case ISTJ:
                return new eMBTI[]{eMBTI.ENFP,eMBTI.ENTP,eMBTI.ISFP,eMBTI.INFP};
            case ISFJ:
                return new eMBTI[]{eMBTI.ENTP,eMBTI.ENFP,eMBTI.INTP,eMBTI.ISTP};
            case ENFJ:
                return new eMBTI[]{eMBTI.ISTP,eMBTI.INTP,eMBTI.ESTP,eMBTI.ESFP};
            case ENFP:
                return new eMBTI[]{eMBTI.ISTJ,eMBTI.ISFJ,eMBTI.ESFJ,eMBTI.ESTJ};
            case INFJ:
                return new eMBTI[]{eMBTI.ESTP,eMBTI.ESFP,eMBTI.ISTP,eMBTI.INTP};
            case INFP:
                return new eMBTI[]{eMBTI.ESTJ,eMBTI.ENTJ,eMBTI.INTJ,eMBTI.ISTJ};
            case ESTP:
                return new eMBTI[]{eMBTI.INFJ,eMBTI.INTJ,eMBTI.ENFJ,eMBTI.ENTJ};
            case ESFP:
                return new eMBTI[]{eMBTI.INTJ,eMBTI.INFJ,eMBTI.ENTJ,eMBTI.ENFJ};
            case ISTP:
                return new eMBTI[]{eMBTI.ENFJ,eMBTI.ESFJ,eMBTI.INFJ,eMBTI.ISFJ};
            case ISFP:
                return new eMBTI[]{eMBTI.ENTJ,eMBTI.ESTJ,eMBTI.INTJ,eMBTI.ISTJ};
        }
        return null;
    }

    eMBTI[] GetBadMBTIArray(eMBTI mbti){
        switch (mbti){
            case ENTJ:
                return new eMBTI[]{eMBTI.ISFJ,eMBTI.ESFJ,eMBTI.ENTJ,eMBTI.ESTJ};
            case ENTP:
                return new eMBTI[]{eMBTI.ISFP,eMBTI.ESFP,eMBTI.ENTJ,eMBTI.ESTP};
            case INTJ:
                return new eMBTI[]{eMBTI.ESFJ,eMBTI.ISFJ,eMBTI.ESTJ,eMBTI.INTP};
            case INTP:
                return new eMBTI[]{eMBTI.ESFP,eMBTI.ISFP,eMBTI.ESTP,eMBTI.INFP};
            case ESTJ:
                return new eMBTI[]{eMBTI.INFJ,eMBTI.ENFJ,eMBTI.ESTP,eMBTI.ENTJ};
            case ESFJ:
                return new eMBTI[]{eMBTI.INTJ,eMBTI.ENTJ,eMBTI.ESFP,eMBTI.ESTJ};
            case ISTJ:
                return new eMBTI[]{eMBTI.ENFJ,eMBTI.INFJ,eMBTI.ENTJ,eMBTI.ISTP};
            case ISFJ:
                return new eMBTI[]{eMBTI.ENTJ,eMBTI.INTJ,eMBTI.ISFP,eMBTI.INFJ};
            case ENFJ:
                return new eMBTI[]{eMBTI.ISTJ,eMBTI.ESTJ,eMBTI.ISFJ,eMBTI.ENTJ};
            case ENFP:
                return new eMBTI[]{eMBTI.ISTP,eMBTI.ESTP,eMBTI.ESFP,eMBTI.ENTP};
            case INFJ:
                return new eMBTI[]{eMBTI.ESTJ,eMBTI.ISTJ,eMBTI.INFP,eMBTI.ENFP};
            case INFP:
                return new eMBTI[]{eMBTI.ESTP,eMBTI.ISTP,eMBTI.INFJ,eMBTI.ISFP};
            case ESTP:
                return new eMBTI[]{eMBTI.INFP,eMBTI.ENFP,eMBTI.ESTJ,eMBTI.ENTP};
            case ESFP:
                return new eMBTI[]{eMBTI.INTP,eMBTI.ENTP,eMBTI.ENFP,eMBTI.ESFJ};
            case ISTP:
                return new eMBTI[]{eMBTI.ENFP,eMBTI.INFP,eMBTI.ISFP,eMBTI.ISTJ};
            case ISFP:
                return new eMBTI[]{eMBTI.ENTP,eMBTI.INTP,eMBTI.ISFJ,eMBTI.ISTP};
        }
        return null;
    }



    InputStream GetMBTIExplainTxtInputStream(eMBTI mbti){
        switch (mbti){
            case ENTJ:
                return getResources().openRawResource(R.raw.entj_explain);
            case ENTP:
                return getResources().openRawResource(R.raw.entp_explain);
            case INTJ:
                return getResources().openRawResource(R.raw.intj_explain);
            case INTP:
                return getResources().openRawResource(R.raw.intp_explain);
            case ESTJ:
                return getResources().openRawResource(R.raw.estj_explain);
            case ESFJ:
                return getResources().openRawResource(R.raw.esfj_explain);
            case ISTJ:
                return getResources().openRawResource(R.raw.istj_explain);
            case ISFJ:
                return getResources().openRawResource(R.raw.isfj_explain);
            case ENFJ:
                return getResources().openRawResource(R.raw.enfj_explain);
            case ENFP:
                return getResources().openRawResource(R.raw.enfp_explain);
            case INFJ:
                return getResources().openRawResource(R.raw.infj_explain);
            case INFP:
                return getResources().openRawResource(R.raw.infp_explain);
            case ESTP:
                return getResources().openRawResource(R.raw.estp_explain);
            case ESFP:
                return getResources().openRawResource(R.raw.esfp_explain);
            case ISTP:
                return getResources().openRawResource(R.raw.istp_explain);
            case ISFP:
                return getResources().openRawResource(R.raw.isfp_explain);
        }
        return null;
    }
    void SetExplainText(eMBTI mbti){

        InputStream explainTextStream=GetMBTIExplainTxtInputStream(mbti);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = explainTextStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = explainTextStream.read();
            }
        }
        catch (Exception e){
            e.printStackTrace();;
        }
        TextView mbtiExplainTextView=findViewById(R.id.mbti_explain_text_in_mbti_result_page);
        mbtiExplainTextView.setText(new String(byteArrayOutputStream.toByteArray()));


    }

    void SetGoodMBTItextView(eMBTI mbti){
        eMBTI[] good_mbti_array=GetGoodMBTIArray(mbti);
        ConstraintLayout parent=(ConstraintLayout)findViewById(R.id.good_mbti_layout);
        int previous_text_view_id=R.id.good_mbti_compatibility_textView;
        for(int i=0;i<good_mbti_array.length;++i){

            TextView textView=GetMBTITextView(previous_text_view_id,good_mbti_array[i].toString());
            parent.addView(textView);
            previous_text_view_id=textView.getId();
        }
    }

    TextView GetMBTITextView(int top_id, String mbti_text){
        TextView textView=new TextView(getApplicationContext());
        textView.setText(mbti_text.toString());
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(getResources().getFont(R.font.abeezee_regular));
        textView.setTextColor(Color.BLACK);
        textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        int view_id=View.generateViewId();
        textView.setLayoutParams(GetMBTITextParam(top_id));
        textView.setId(view_id);
        return textView;
    }
    ConstraintLayout.LayoutParams GetMBTITextParam(int top_id){
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
        params.matchConstraintPercentHeight=0.2f;
        params.endToEnd=ConstraintLayout.LayoutParams.PARENT_ID;
        params.startToStart=ConstraintLayout.LayoutParams.PARENT_ID;
        params.topToBottom=top_id;

        return params;
    }
    void SetBadMBTItextView(eMBTI mbti){
        eMBTI[] bad_mbti_array=GetBadMBTIArray(mbti);
        ConstraintLayout parent=(ConstraintLayout)findViewById(R.id.bad_mbti_layout);
        int previous_text_view_id=R.id.bad_mbti_compatibility_textView;
        for(int i=0;i<bad_mbti_array.length;++i){
            TextView textView=GetMBTITextView(previous_text_view_id,bad_mbti_array[i].toString());
            parent.addView(textView);
            textView.setLayoutParams(GetMBTITextParam(previous_text_view_id));
            previous_text_view_id=textView.getId();
        }
    }



}