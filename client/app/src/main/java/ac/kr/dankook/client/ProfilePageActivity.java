package ac.kr.dankook.client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfilePageActivity extends AppCompatActivity {

    Dialog mEditNicknameDialog;
    Dialog mEditIntrodutionMyselfDialog;
    TextView mNickNameText;
    TextView mIntroductionForUserMBTI;
    TextView mIntroductionMyself;
    ImageView mImageViewOfUserMBTI;

    ImageButton mEditNickNameButton;

    ImageButton mGoMBTITestButton;

    ImageButton mEditIntrodutionMySelfButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        LoadComponent();
        LoadDialog();
    }

    void LoadComponent(){
        mNickNameText = findViewById(R.id.nick_name);
        mIntroductionForUserMBTI = findViewById(R.id.user_mbti_introduction);
        mIntroductionMyself = findViewById(R.id.introduce_in_profile_page);
        mImageViewOfUserMBTI = findViewById(R.id.user_mbit_image);
        mEditNickNameButton = findViewById(R.id.edit_nickname_button);
        mGoMBTITestButton = findViewById(R.id.mbti_test_button_in_profile_page);
        mEditIntrodutionMySelfButton = findViewById(R.id.edit_introduce_myself_button);
    }

    void LoadDialog(){
        mEditNicknameDialog = new Dialog(this);
        mEditNicknameDialog.setContentView(R.layout.edit_nickname_dialog);

        Button editNickNameStoreButton = mEditNicknameDialog.findViewById(R.id.edit_nickname_store_button);
        editNickNameStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextNickName=findViewById(R.id.edit_nick_name_text);
                String write_nickname=editTextNickName.getText().toString();
                if(CheckVaildNickName(write_nickname)){
                    mNickNameText.setText(write_nickname);
                    mEditNicknameDialog.dismiss();
                }
            }
        });

        Button editNickNameCloseButton = mEditNicknameDialog.findViewById(R.id.edit_nickname_dialog_close_button);
        editNickNameCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditNicknameDialog.dismiss();
            }
        });

        mEditIntrodutionMyselfDialog = new Dialog(this);
        mEditIntrodutionMyselfDialog.setContentView(R.layout.edit_introduce_myself_dialog);

        Button editIntroductionStoreButton=mEditIntrodutionMyselfDialog.findViewById(R.id.edit_introduction_store_button);
        editIntroductionStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextNickName=findViewById(R.id.edit_introduce_myself_text);
                String write_introduction=editTextNickName.getText().toString();
                if(CheckVaildNickName(write_introduction)){
                    mIntroductionMyself.setText(write_introduction);
                    mEditIntrodutionMyselfDialog.dismiss();
                }
            }
        });

        Button editIntroductionCloseButton = mEditNicknameDialog.findViewById(R.id.edit_introduction_dialog_close_button);
        editNickNameCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditNicknameDialog.dismiss();
            }
        });
    }

    boolean CheckVaildNickName(String nickname){
        return true;
    }

    boolean CheckVaildIntroductionMySelf(String introdution){
        return true;
    }

}