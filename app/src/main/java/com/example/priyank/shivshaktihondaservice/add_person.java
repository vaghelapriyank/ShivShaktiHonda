package com.example.priyank.shivshaktihondaservice;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class add_person extends AppCompatActivity {


    private FirebaseAuth pkAuth;
    private Button add;
    private EditText pkname,pkemail,pknumber,pkpassword;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        CharSequence title = "Add Person";
        SpannableString s = new SpannableString(title);
        s.setSpan(new BackgroundColorSpan(Color.RED), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        pkAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            }
        };


        pkname=(EditText)findViewById(R.id.add_edtName);
        pkemail=(EditText)findViewById(R.id.add_edtEmail);
        pkpassword=(EditText)findViewById(R.id.add_edtPassword);
        pknumber=(EditText)findViewById(R.id.add_edtMobileNumber);
        add=(Button)findViewById(R.id.add_btnAdd) ;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email= pkemail.getText().toString();
                final String name= pkname.getText().toString();
                final String no= pknumber.getText().toString();
                final String password= pkpassword.getText().toString();


                pkAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(add_person.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(add_person.this, "sign up error", Toast.LENGTH_SHORT).show();

                                } else {
                                    String user_id = pkAuth.getCurrentUser().getUid();
                                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("user").child(user_id);
                                    current_user_db.setValue(true);


                                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("user").child(user_id);
                                    mRef.child("name").setValue(name);
                                    mRef.child("userid").setValue(email);
                                    mRef.child("pass").setValue(password);
                                    mRef.child("Mobile").setValue(no);
                                    Toast.makeText(add_person.this, "register successful", Toast.LENGTH_SHORT).show();

                                }



                            }
                        });

            }
        });



    }
    @Override
    protected void onStop(){
        super.onStop();
        pkAuth.removeAuthStateListener(firebaseAuthListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        pkAuth.addAuthStateListener(firebaseAuthListener);
    }


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(add_person.this,AdminActivity.class);
        startActivity(intent);
        finish();
        return;
    }
}
