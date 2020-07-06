package com.example.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText mLoginEdit;
    private EditText mPasswordEdit;
    private Button btnLogin;
    private Button btnReg;
    private static final String FILE_LOGIN = "log_user.txt";
    private static final String FILE_PASSWORD = "pass_user.txt";
    private String loginValue;
    private String passValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        regBtnClick();
        logBtnClick();
    }

    private void logBtnClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginValue = mLoginEdit.getText().toString();
                passValue = mPasswordEdit.getText().toString();
                try {
                    FileInputStream fileLogin = openFileInput(FILE_LOGIN);
                    FileInputStream filePass = openFileInput(FILE_PASSWORD);
                    InputStreamReader inputLoginRead = new InputStreamReader(fileLogin);
                    InputStreamReader inputPassRead = new InputStreamReader(filePass);
                    BufferedReader readerLogin = new BufferedReader(inputLoginRead);
                    BufferedReader readerPass = new BufferedReader(inputPassRead);
                    String lineLogin = readerLogin.readLine();
                    String linePass = readerPass.readLine();
                    StringBuilder outputLogin = new StringBuilder();
                    StringBuilder outputPass = new StringBuilder();
                    while (lineLogin != null && linePass != null) {
                        outputLogin = outputLogin.append(lineLogin);
                        lineLogin = readerLogin.readLine();
                        outputPass = outputPass.append(linePass);
                        linePass = readerPass.readLine();
                    }
                    mLoginEdit.setText("");
                    mPasswordEdit.setText("");
                    if (outputLogin.toString().equals(loginValue) &&
                            outputPass.toString().equals(passValue)) {
                        Toast.makeText(v.getContext(), R.string.access,
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(v.getContext(), R.string.no_access,
                                Toast.LENGTH_LONG).show();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });
    }

    private void regBtnClick() {
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginValue = mLoginEdit.getText().toString();
                passValue = mPasswordEdit.getText().toString();
                try {
                    if (!loginValue.equals("") && !passValue.equals("")) {
                        FileOutputStream fileLogin = openFileOutput(FILE_LOGIN,
                                Context.MODE_PRIVATE);
                        FileOutputStream filePass = openFileOutput(FILE_PASSWORD,
                                Context.MODE_PRIVATE);
                        OutputStreamWriter outputLoginWriter =
                                new OutputStreamWriter(fileLogin);
                        OutputStreamWriter outputPassWriter =
                                new OutputStreamWriter(filePass);
                        BufferedWriter bwLogin = new BufferedWriter(outputLoginWriter);
                        BufferedWriter bwPass = new BufferedWriter(outputPassWriter);
                        bwLogin.write(loginValue);
                        bwPass.write(passValue);
                        bwLogin.close();
                        bwPass.close();
                        mLoginEdit.setText("");
                        mPasswordEdit.setText("");
                        Toast.makeText(v.getContext(), R.string.success_reg,
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(v.getContext(), R.string.fill_blanks,
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView () {
        mLoginEdit = findViewById(R.id.edit_login);
        mPasswordEdit = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnReg);
    }
}