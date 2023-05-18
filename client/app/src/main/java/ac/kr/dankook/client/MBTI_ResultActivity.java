package ac.kr.dankook.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.TreeMap;

public class MBTI_ResultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbti_result);
        Intent intent=getIntent();
        assert intent!=null ;
        int mbti=intent.getIntExtra("mbti",16);
        assert mbti<16;

        eMBTI mbti_enum=eMBTI.values()[mbti];
        LoadView(mbti_enum);
        SetExplainText(mbti_enum);
        SetGoodMBTItextView(mbti_enum);
        SetGoodMBTItextView(mbti_enum);

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



    void SetExplainText(eMBTI mbti){
        String explainTextFilePath = getFilesDir() + "/explainMBTI/" + mbti.toString() + ".txt";
        File f= new File(explainTextFilePath);
        String line;
        TextView mbtiExplainTextView=findViewById(R.id.mbti_explain_text_in_mbti_result_page);
        StringBuilder sb=new StringBuilder();
        try {
            BufferedReader reader=new BufferedReader(new FileReader(f));
            while((line=reader.readLine())!=null){
                sb.append(line);
                sb.append("\n");
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        mbtiExplainTextView.setText(sb.toString());
    }

    void SetGoodMBTItextView(eMBTI mbti){
        eMBTI[] gool_mbti_array=GetGoodMBTIArray(mbti);
        /*
        for(int i=0;i<gool_mbti_array.length;++i){
            ConstraintSet set=new ConstraintSet();
            String view_id="mbti_result_good_textview" + i;
            TextView textView=new TextView(getApplicationContext());
            textView.setText(gool_mbti_array[i].toString());
            TextViewCompat.setAutoSizeTextTypeWithDefaults(textView,TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        }
        */

    }
    void SetBadMBTItextView(eMBTI mbti){
        eMBTI[] gool_mbti_array=GetGoodMBTIArray(mbti);
        /*
        for(int i=0;i<gool_mbti_array.length;++i){
            ConstraintSet set=new ConstraintSet();
            String view_id="mbti_result_good_textview" + i;
            TextView textView=new TextView(getApplicationContext());
            textView.setText(gool_mbti_array[i].toString());
            TextViewCompat.setAutoSizeTextTypeWithDefaults(textView,TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        }
        */

    }



}