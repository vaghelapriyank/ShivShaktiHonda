package com.example.priyank.shivshaktihondaservice;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_PriceList extends AppCompatActivity {

    private FirebaseAuth pkAuth;
    private Button add;
    private EditText pkbike,pktype,pkservice,pkprice,pkinsentive;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__price_list);

        CharSequence title = "Add Services";
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

        pkbike=(EditText)findViewById(R.id.pk_edtBike);
        pktype=(EditText)findViewById(R.id.pk_edtType);
        pkservice=(EditText)findViewById(R.id.pk_edtService);
        pkprice=(EditText)findViewById(R.id.pk_edtPrice);
        pkinsentive=(EditText)findViewById(R.id.pk_edtIncentiveAmount);
        add=(Button) findViewById(R.id.pk_btnEdit);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String bike= pkbike.getText().toString();
                final String type= pktype.getText().toString();
                final String service= pkservice.getText().toString();
                final String price= pkprice.getText().toString();
                final String insentive=pkinsentive.getText().toString();



                                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Services");
                                    mRef.child("Bike").setValue(bike);
                                    mRef.child("Bike").child("Type").setValue(type);
                                    mRef.child("Bike").child("Type").child("ServiceName").setValue(service);
                                    mRef.child("Bike").child("Type").child("ServiceName").child("Price").setValue(price);
                                    mRef.child("Bike").child("Type").child("ServiceName").child("Insentive").setValue(insentive);
                                    Toast.makeText(Add_PriceList.this, "all set successful", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        pkAuth.removeAuthStateListener(firebaseAuthListener);

    }
    @Override
    protected  void onStart()
    {
        super.onStart();
        pkAuth.addAuthStateListener(firebaseAuthListener);

    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Add_PriceList.this,AdminActivity.class);
        startActivity(intent);
        finish();
        return;
    }
}
