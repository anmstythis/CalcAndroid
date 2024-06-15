package com.example.calculator;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView formula, endResult;
    private Button one, two, three, four, five, six, seven, eight, nine, zero;
    private Button plus, minus, division, multiply, result,
            plus_minus, procent, sqrt, square, erase;
    private char action;
    private double valueFirst = Double.NaN;
    private double valueSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setupView();

        //ввод чисел
        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                if (endResult.getText().toString().isEmpty()
                        || endResult.getText().toString().equals("0"))
                {
                    formula.setText(formula.getText().toString() + button.getText().toString());
                }
            }
        };

        one.setOnClickListener(numberClickListener); //прослушивание нажатия на число
        two.setOnClickListener(numberClickListener);
        three.setOnClickListener(numberClickListener);

        four.setOnClickListener(numberClickListener);
        five.setOnClickListener(numberClickListener);
        six.setOnClickListener(numberClickListener);

        seven.setOnClickListener(numberClickListener);
        eight.setOnClickListener(numberClickListener);
        nine.setOnClickListener(numberClickListener);
        zero.setOnClickListener(numberClickListener);

        //выполнение действий
        View.OnClickListener actionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                try
                {
                    calculate();
                    action = button.getText().charAt(0);
                    formula.setText(String.valueOf(valueFirst) + action);
                }
                catch (Exception e){

                }
                finally {
                    endResult.setText(null);
                }
            }
        };

        plus.setOnClickListener(actionClickListener); //прослушивание нажатия на базовые действия
        minus.setOnClickListener(actionClickListener);
        multiply.setOnClickListener(actionClickListener);
        division.setOnClickListener(actionClickListener);


        //подчет результата
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!formula.getText().toString().isEmpty())
                {
                    if (formula.getText().toString().contains("+") ||
                            formula.getText().toString().contains("-") ||
                            formula.getText().toString().contains("*") ||
                            formula.getText().toString().contains("/"))
                    {
                        try{ //если формула полноценна
                            calculate(); //выполнение вычислений
                            action = '='; //действие
                            endResult.setText(String.valueOf(valueFirst)); //результат
                            formula.setText(null); //опустошение формулы
                        }
                        catch (Exception e) { //если формула неполноценна, то ничего не происходит

                        }
                    } // если формула содержит в себе базовые операторы, то посчитывается результат действий
                    else
                    {
                        endResult.setText(formula.getText().toString());
                        valueFirst = Double.parseDouble(endResult.getText().toString());
                        formula.setText(null);
                    } //в противном случае в панель результата выводится только первое число
                }
            }
        });

        //удаление предыдущего подсчета
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //происходит обнуление всех значений
                valueFirst = Double.NaN;
                endResult.setText("0");
                formula.setText(null);
            }
        });

        //изменение положительного числа на отрицательное
        //возможно это произвести, только если в панели с результатом есть какое-то положительное число
        plus_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!endResult.getText().toString().equals("0") &&
                        !endResult.getText().toString().isEmpty())
                {
                    try{
                        endResult.setText("-" + endResult.getText().toString());
                        valueFirst = Double.parseDouble(endResult.getText().toString());
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        });

        //извлечение корня из числа
        sqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Double root = Math.sqrt(Double.parseDouble(endResult.getText().toString()));
                    endResult.setText(root.toString());
                    valueFirst = root;
                }
                catch (Exception e)
                {

                }
            }
        });

        //возведение числа в квадрат
        square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Double square = Math.pow(Double.parseDouble(endResult.getText().toString()), 2);
                    endResult.setText(square.toString());
                    valueFirst = square;
                }
                catch (Exception e)
                {

                }
            }
        });

        //вычисление процента
        procent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Double proc = Double.parseDouble(endResult.getText().toString()) / 100;
                    endResult.setText(proc.toString());
                    valueFirst = proc;
                }
                catch (Exception e)
                {

                }
            }
        });
    }

    //настройка всех кнопок
    private void setupView()
    {
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);

        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);

        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);

        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        division = (Button) findViewById(R.id.division);
        multiply = (Button) findViewById(R.id.multiply);

        result = (Button) findViewById(R.id.result);
        endResult = (TextView) findViewById(R.id.endResult);
        formula = (TextView) findViewById(R.id.formula);

        plus_minus = (Button) findViewById(R.id.plus_minus);
        procent = (Button) findViewById(R.id.procent);
        sqrt = (Button) findViewById(R.id.sqrt);
        square = (Button) findViewById(R.id.square);
        erase = (Button) findViewById(R.id.erase);
    }

    //функция для выполнения расчетов
    private void calculate()
    {
        if (!Double.isNaN(valueFirst))
        {
            String textFormula = formula.getText().toString();
            int indexAction = textFormula.indexOf(action);

            if (indexAction != -1) {
                String numberValue = textFormula.substring(indexAction + 1);
                valueSecond = Double.parseDouble(numberValue);

                switch (action) {
                    case '/':
                        if (valueSecond == 0)
                        {
                            valueFirst = 0.0;
                        }
                        else
                        {
                            valueFirst /= valueSecond;
                        }
                        break;

                    case '*':
                        valueFirst *= valueSecond;
                        break;

                    case '-':
                        valueFirst -= valueSecond;
                        break;

                    case '+':
                        valueFirst += valueSecond;
                        break;

                    case '=':
                        valueFirst = valueSecond;
                        break;

                }
            }
        } else {
            valueFirst = Double.parseDouble(formula.getText().toString());
        }

        endResult.setText(String.valueOf(valueFirst));
        formula.setText("");
    }
}