package com.example.carboncreditapplication.bottomnavigation.home.sign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.carboncreditapplication.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView[] checkImageList = new ImageView[7]; //存放7张check图片的数组，累计签到n次，点亮n张图片
    private Button buttonSignIn;
    private Button buttonToStore;
    private int signInNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonToStore = findViewById(R.id.buttonToStore);

        checkImageList[0] = findViewById(R.id.imageCheck1);
        checkImageList[1] = findViewById(R.id.imageCheck2);
        checkImageList[2] = findViewById(R.id.imageCheck3);
        checkImageList[3] = findViewById(R.id.imageCheck4);
        checkImageList[4] = findViewById(R.id.imageCheck5);
        checkImageList[5] = findViewById(R.id.imageCheck6);
        checkImageList[6] = findViewById(R.id.imageCheck7);

        buttonSignIn.setOnClickListener(this);
        buttonToStore.setOnClickListener(this);

    }

    public void reloadCheckImages(){
        if(signInNumber == 0){
            for (int i=0; i<checkImageList.length; i++){
                checkImageList[i].setImageResource(R.drawable.check_gray);
            }
        }else {
            for(int i=0; i<signInNumber; i++){
                checkImageList[i].setImageResource(R.drawable.check_blue);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSignIn:{
                Toast.makeText(SignInActivity.this, "you clicked buttonSignIn", Toast.LENGTH_SHORT).show();
                signInNumber++;
                reloadCheckImages();
                break;
            }
            case R.id.buttonToStore:{
                Toast.makeText(SignInActivity.this, "you clicked buttonToStore", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.buttonSignInBack:{
                finish();
                break;
            }

            default:break;
        }
    }
}
