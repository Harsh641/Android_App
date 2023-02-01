package com.example.ecampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    String roleId[] = {"Select Item", "Student", "Faculty", "Admin"};
    EditText edt_fName, edt_lName, edt_email, edt_password;
    Button btn_register;
    TextView signin_txt;
    Spinner role;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edt_fName = findViewById(R.id.first_name);
        edt_lName = findViewById(R.id.last_name);
        edt_email = findViewById(R.id.email);
        edt_password = findViewById(R.id.password);
        btn_register = findViewById(R.id.register);
        signin_txt = findViewById(R.id.signin_txt);
        role = findViewById(R.id.role);

        firebaseAuth = FirebaseAuth.getInstance();


        firebaseDatabase = FirebaseDatabase.getInstance("https://ecampus-android-b7731-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference("Users");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roleId);
        role.setAdapter(arrayAdapter);

        signin_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = edt_fName.getText().toString();
                String lName = edt_lName.getText().toString();
                String Email = edt_email.getText().toString();
                String Pass = edt_password.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String uid = firebaseAuth.getUid();
                            Log.e("UID: ", uid);

                            UserModel userModel = new UserModel();
                            userModel.setFirstName(fName);
                            userModel.setLastName(lName);
                            userModel.setEmail(Email);
                            userModel.setPassword(Pass);

                            databaseReference.child(uid).setValue(userModel);

                            Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(i);

                            edt_fName.setText("");
                            edt_lName.setText("");
                            edt_email.setText("");
                            edt_password.setText("");

                            Toast.makeText(RegistrationActivity.this, "Account Created Successfully!!!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(RegistrationActivity.this, "USer Exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}