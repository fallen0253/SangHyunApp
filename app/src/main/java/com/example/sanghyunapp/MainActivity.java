package com.example.sanghyunapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    CalendarView cDate;
    int cYear, cMonth, cDay;
    EditText edtHouseHold;
    String fileName, IncomeContent, ExpenseContent;
    Button btnSave, btnIncome, btnExpense;
    boolean Money = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cDate = findViewById(R.id.cDate);
        Calendar cal = Calendar.getInstance();
        cYear = cal.get(Calendar.YEAR);
        cMonth = cal.get(Calendar.MONTH);
        cDay = cal.get(Calendar.DAY_OF_MONTH);
        edtHouseHold = findViewById(R.id.edtHouseHold);
        btnSave = findViewById(R.id.btnSave);
        btnIncome = findViewById(R.id.btnIncome);
        btnExpense = findViewById(R.id.btnExpense);
        IncomeContent = cYear+"_"+(cMonth+1)+"_"+cDay+".txt";
        ExpenseContent = cYear+"_"+(cMonth+1)+"_"+cDay+".txt";

        cDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    Log.v("알림","캘린더가 정상 변경했습니다.");
                    if (Money){
                        edtHouseHold.setText(readDairy(IncomeContent));
                    }else{
                        edtHouseHold.setText(readDairy(ExpenseContent));
                    }

            }
        });
        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("수입이 선택되었습니다.");
                Log.v("알림","버튼이 정상 작동했습니다.");
                Money = true;
                if(Money){
                    Log.v("알림","Money값이 True입니다.");
                }
            }
        });
        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("지출이 선택되었습니다.");
                Log.v("알림","버튼이 정상 작동했습니다.");
                Money = false;
                if(Money==false){
                    Log.v("알림","Money값이 false입니다.");
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Money == true) {
                    try {
                        FileOutputStream IncomeFS = openFileOutput(IncomeContent, MODE_PRIVATE);
                        String str1 = edtHouseHold.getText().toString();
                        IncomeFS.write(str1.getBytes());
                        IncomeFS.close();
                        showToast("수입이 저장 되었습니다.");
                        Log.v("알림","버튼이 정상 작동했습니다.");
                    } catch (IOException e) {
                        showToast("해당 파일을 저장할 수 없습니다.");
                    }

                } else {
                    try {
                        FileOutputStream ExpenseFS = openFileOutput(ExpenseContent, MODE_PRIVATE);
                        String str = edtHouseHold.getText().toString();
                        ExpenseFS.write(str.getBytes());
                        ExpenseFS.close();
                        showToast("지출이 저장 되었습니다.");
                        Log.v("알림","버튼이 정상 작동했습니다.");
                    } catch (IOException e) {
                        showToast("해당 파일을 저장할 수 없습니다.");
                    }

                }

            }
        });

    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    String readDairy(String fileName){
        String diaryContent=null;
        try {
            FileInputStream inFS = openFileInput(fileName);
            byte txt[] = new byte[inFS.available()];
            inFS.read(txt);
            diaryContent=(new String(txt).trim());  //trim 좌우 공백지우기

            inFS.close();
        } catch (IOException e) {
            edtHouseHold.setHint("내용 없음");
            btnSave.setText("새로 저장");
        }
        return diaryContent;

    }
}



