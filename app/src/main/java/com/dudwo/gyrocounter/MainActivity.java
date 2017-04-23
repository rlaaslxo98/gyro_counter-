package com.dudwo.gyrocounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.add_work).setOnClickListener(this);
        findViewById(R.id.run).setOnClickListener(this);


        if(user != null){
            //화면 유지
            Log.d("MAIN","first");

        }
        else{
            Intent intent = new Intent(this, EmailPasswordActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void signOut() {
        firebaseAuth.signOut();
    }

    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.sign_out_button) {
            signOut();
            Intent intent = new Intent(this, EmailPasswordActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim01, R.anim.anim02);
            finish();
        }
        else if (i == R.id.add_work) {
            Intent intent = new Intent(this, AddWork.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim01, R.anim.anim02);
            finish();
        }
        else if (i == R.id.run) {
            Intent intent = new Intent(this, RunActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim01, R.anim.anim02);
            finish();
        }

    }
}
