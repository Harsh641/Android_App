package com.example.ecampus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SharedPrefActivity extends AppCompatActivity {

    EditText fn, ln;
    Button save, display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pref);

        fn = findViewById(R.id.edt_fn);
        ln = findViewById(R.id.edt_ln);
        save = findViewById(R.id.btn_save);
        display = findViewById(R.id.btn_display);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtFn = fn.getText().toString();
                String edtLn = ln.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("MYAPP", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("KEY_FN", edtFn);
                editor.putString("KEY_LN", edtLn);
                editor.commit();

                fn.setText("");
                ln.setText("");
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("MYAPP", MODE_PRIVATE);
                String strFn = sharedPreferences.getString("KEY_FN", "");
                String strLn = sharedPreferences.getString("KEY_LN", "");
                fn.setText(strFn);
                ln.setText(strLn);

            }
        });
    }
}