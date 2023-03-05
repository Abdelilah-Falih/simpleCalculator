package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView quiry, result;

    Button delete;
    private boolean hasDot = false;


    private void setResult(String s) {
        if ((s.contains("+") || s.contains("÷") || s.contains("*") || s.contains("-") || s.contains("%")) && Character.isDigit(s.charAt(s.length()-1)) ) {
            result.setText(String.valueOf(calculate(s)));
        }
        else result.setText("");
    }


    private double calculate(String query) {
        String s = query;
        int i = 0;
        char operation = '+';
        double result = 0;
        double num = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }
            if (!Character.isDigit(c) && c != ' ' || i == s.length() - 1) {
                if (operation == '+') {
                    result += num;
                } else if (operation == '-') {
                    result -= num;
                } else if (operation == '*') {
                    result *= num;
                } else if (operation == '÷') {
                    result /= num;
                } else if (operation == '%') {
                    result %= num;
                }

                operation = c;
                num = 0;
            }
            i++;
        }
        return result;
    }


    public void onClick(View v) {

        Button b = (Button) v;
        //Toast.makeText(this, b.getText(), Toast.LENGTH_SHORT).show();
        String s = quiry.getText().toString();
        if (s.length() == 0) hasDot = false;
        //quiry.setSelection(s.length());
        if (b.getText().equals("delete")) {
            if (s.length() == 0) return;
            s = s.substring(0, s.length() - 1);
            quiry.setText(s);
            return;
        }
        boolean b1 = s.length() == 0 || s.charAt(s.length() - 1) == '+' || s.charAt(s.length() - 1) == '÷' || s.charAt(s.length() - 1) == '-' || s.charAt(s.length() - 1) == '%' || s.charAt(s.length() - 1) == '*' || s.charAt(s.length() - 1) == '.';
        if (b.getText().equals("÷")) {
            if (b1) return;
            s += b.getText();
            quiry.setText(s);
            hasDot = false;
            return;
        }
        if (b.getText().equals("*")) {
            if (b1) return;
            s += b.getText();
            quiry.setText(s);
            hasDot = false;
            return;
        }
        if (b.getText().equals("-")) {
            if (s.length() > 0 && (s.charAt(s.length() - 1) == '+' || s.charAt(s.length() - 1) == '÷' || s.charAt(s.length() - 1) == '-' || s.charAt(s.length() - 1) == '%' || s.charAt(s.length() - 1) == '*' || s.charAt(s.length() - 1) == '.'))
                return;
            s += b.getText();
            quiry.setText(s);
            hasDot = false;
            return;
        }
        if (b.getText().equals("+")) {
            if (b1) return;
            s += b.getText();
            quiry.setText(s);
            hasDot = false;
            return;
        }
        if (b.getText().equals("%")) {
            if (b1) return;
            s += b.getText();
            quiry.setText(s);
            hasDot = false;
            return;
        }
        if (b.getText().equals(".")) {
            if (b1 || hasDot) return;
            s += b.getText();
            quiry.setText(s);
            hasDot = true;
            return;
        }

        if (b.getText().equals("=")) {
            if (s.length() == 0) return;
            hasDot = true;
            quiry.setText(String.valueOf(calculate(s)));
            result.setText("");
            return;
        }


        s += b.getText();
        quiry.setText(s);
        setResult(s);

    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quiry = findViewById(R.id.query);
        result = findViewById(R.id.result);
        quiry.setFocusable(false);
        quiry.setFocusableInTouchMode(false);
        //quiry.setSelection(quiry.getText().length());
        delete = findViewById(R.id.btnDelete);


        delete.setOnTouchListener(new View.OnTouchListener() {
            private Handler handler;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // If the delete button is still being pressed after 1 second, delete all text
                                quiry.setText("");
                                setResult(quiry.getText().toString());
                            }
                        }, 400);
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (handler != null) {
                            handler.removeCallbacksAndMessages(null);
                        }
                        // Delete the last character
                        String currentText = quiry.getText().toString();
                        if (currentText.length() > 0) {
                            String newText = currentText.substring(0, currentText.length() - 1);
                            quiry.setText(newText);
                            setResult(quiry.getText().toString());
                        }
                        return true;
                }
                return false;
            }
        });


    }
}