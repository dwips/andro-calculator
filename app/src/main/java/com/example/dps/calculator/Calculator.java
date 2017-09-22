package com.example.dps.calculator;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

public class Calculator extends AppCompatActivity {

    private String str="",result,num;
    private Toolbar mToolbar;
    private EditText editTextTop,editTextBot;
    private Button btnOpMul,btnOpDiv,btnOpPlus,btnOpMinus,btnDot;
    private ArrayList<String> arrayList;
    private Stack<String> stackN, stackO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editTextTop  = (EditText) findViewById(R.id.edit_text_top);
        editTextBot  = (EditText) findViewById(R.id.edit_text_bot);

//        this to make soft keyboard not appear
        editTextBot.setInputType(InputType.TYPE_NULL);
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            editTextBot.setRawInputType(InputType.TYPE_CLASS_TEXT);
            editTextBot.setTextIsSelectable(true);
        }



        btnOpDiv = (Button) findViewById(R.id.btnOpDiv);
        btnOpMinus = (Button) findViewById(R.id.btnOpMinus);
        btnOpMul = (Button) findViewById(R.id.btnOpMul);
        btnOpPlus = (Button) findViewById(R.id.btnOpPlus);
        btnDot = (Button) findViewById(R.id.btnDot) ;
    }

    //this method for calculate
//    public void onClickEqual(View view){
//
//        String cc="",cc2="",op="";
//        arrayList = new ArrayList<String>();
//        stackN = new Stack<String>();
//        stackO = new Stack<String>();
//        if (str.length()!=0){
//            for (int i=0;i<str.length();i++){
//                cc2 = Character.toString(str.charAt(i));
//
//                if (!isOp(cc2)){
//                    cc = cc + Character.toString(str.charAt(i));
//                }else{
//                    if (cc2.compareToIgnoreCase("+")==0 || cc2.compareToIgnoreCase("-")==0){
//                        stackN.push(cc);
//                        stackO.push(cc2);
//                    }
//                }
//            }
//
//            for (int j=0;j<stackO.size();j++){
//                cc=stackN.pop();
//                cc2=stackN.pop();
//                op=stackO.pop();
//                result= calculate(cc,cc2,op);
//                stackN.push(result);
//            }
//
//            result=stackN.pop();
//            editTextBot.setText(result);
//            editTextTop.setText(str);
//            editTextBot.setSelection(result.length());
//        }
//    }
//
//    public String calculate(String s1, String s2, String op){
//        double num1,num2;
//        num1 = Double.valueOf(s1);
//        num2 = Double.valueOf(s2);
//        if (op.compareToIgnoreCase("+")==0){
//            return String.valueOf(num1+num2);
//        }
//        else if (op.compareToIgnoreCase("-")==0){
//            return String.valueOf(num2-num1);
//        }else{
//            return "0";
//        }
//    }

    //this method for inserting number
    public void onClick(View view){
        num=(((Button) view).getText()).toString();
        int start = editTextBot.getSelectionStart();

        if (start==0){ //number beginning idx=0
            str = num+str;
            editTextBot.setText(str);
            editTextBot.setSelection(start+1);
        }
        else if (start==str.length()){ //number last
            str = str+num;
            editTextBot.setText(str);
            editTextBot.setSelection(start+1);
        }
        else { // number middle
            str = str.substring(0,start)+num+str.substring(start,str.length());
            editTextBot.setText(str);
            editTextBot.setSelection(start+1);
        }
    }

    //this method for clearing all
    public void btnClear(View view){
        str="";
        editTextTop.setText(str);
        editTextBot.setText(str);
    }

    //this method for backspace deleting
    public void btnDelete(View view){
        int start = editTextBot.getSelectionStart();

        if (str.length()==start && str.length()>1){
            str=str.substring(0,start-1);
            editTextBot.setText(str);
            editTextBot.setSelection(editTextBot.getText().length());
        }else if (str.length()>1){
            if (start!=0){
                str = str.substring(0,start-1) + str.substring(start,str.length());
                editTextBot.setText(str);
                editTextBot.setSelection(start-1);
            }
        }else if (str.length()<=1){
            str="";
            editTextBot.setText(str);
            editTextBot.setSelection(editTextBot.getText().length());
        }

    }

    //this method insert dot
    public void onClickDot(View view){
        int start = editTextBot.getSelectionStart();
        num = (((Button) view).getText()).toString();

        //in beginning
        if (str.length()==0){
            str = num;
            editTextBot.setText(str);
            editTextBot.setSelection(start+1);
        }
        else if (start==0 && checkAfter(start-1)!=3){
            str = num+str;
            editTextBot.setText(str);
            editTextBot.setSelection(start+1);
        }
        else if (start==str.length() && str.length()!=0 && checkPrev(start-1)!=3){
            str = str+num;
            editTextBot.setText(str);
            editTextBot.setSelection(start+1);
        }
        else if (checkPrev(start-1)!=3 && checkAfter(start-1)!=3){
            str = str.substring(0,start)+num+str.substring(start,str.length());
            editTextBot.setText(str);
            editTextBot.setSelection(start+1);
        }
    }

    //this method for inserting operator
    public void onClickOp(View view){
        num=(((Button) view).getText()).toString();
        int start = editTextBot.getSelectionStart();
        String cc;
        if (start==0 && num.compareToIgnoreCase("-")==0 && str.length()==0){ //op beginning only -
            str = num;
            editTextBot.setText(str);
            editTextBot.setSelection(start+1);
        }
        else if (start==0 && str.length()!=0 && num.compareToIgnoreCase("-")==0){//op beginning
            if (checkAfter(start-1)==1){ //check if after idx is a number then add if not do nothing
                str = num+str;
                editTextBot.setText(str);
                editTextBot.setSelection(start+1);
            }else{
                cc = Character.toString(str.charAt(start));
                if (cc.compareToIgnoreCase("-")==0){
                    str = str.substring(1,str.length());
                    editTextBot.setText(str);
                    editTextBot.setSelection(start);
                }
            }
        }else if (start==str.length() && str.length()!=0){ // op last
            if (checkPrev(start-1)==1){ //check if last is a number then add
                str = str+num;
                editTextBot.setText(str);
                editTextBot.setSelection(start+1);
            }
            else if (checkPrev(start-1)==2){ //check if last is an op then replace
                if (num.compareToIgnoreCase("-")!=0) {
                    str = str.substring(0, start - 1) + num;
                    editTextBot.setText(str);
                    editTextBot.setSelection(start);
                }
                else{

                }
            }
        }else{ //add op middle
            if (checkPrev(start-1)==1 && checkAfter(start-1)==1){ // check if prev is number and after is number then add
                str = str.substring(0,start)+num+str.substring(start,str.length());
                editTextBot.setText(str);
                editTextBot.setSelection(start+1);
            }
            else if (checkPrev(start-1)==1 && checkAfter(start-1)==2){ //check if prev is number and after is op then replace
                str = str.substring(0,start)+num+str.substring(start+1,str.length());
                editTextBot.setText(str);
                editTextBot.setSelection(start+1);
            }
            else if (checkPrev(start-1)==2 && checkAfter(start-1)==1){ //check if prev is op and after is number then replace
                str = str.substring(0,start-1)+num+str.substring(start,str.length());
                editTextBot.setText(str);
                editTextBot.setSelection(start);
            }
        }
    }

    //this method for button minus
    public void onClickMin(View view){
        num=(((Button) view).getText()).toString();
        int start = editTextBot.getSelectionStart();

        if (start==0 && str.length()==0){ //op beginning only -
            str = num;
            editTextBot.setText(str);
            editTextBot.setSelection(start+1);
        }

    }

    //this method for check prev
    public int checkPrev(int idx){
        if (str.length()!=0 && idx>=0){
            String s = Character.toString(str.charAt(idx));

            if (isNumber(s)){
                return 1;
            }else if (isOp(s)) {
                return 2;
            }else if (s.compareToIgnoreCase(".")==0){
                return 3;
            }else{
                return 0;
            }
        }else{
            return 0;
        }
    }

    //this method for check after
    public int checkAfter(int idx){
        if (str.length()!=0 && idx<str.length()){
            String s = Character.toString(str.charAt(idx+1));

            if (isNumber(s)){
                return 1;
            }else if (isOp(s)) {
                return 2;
            }else if (s.compareToIgnoreCase(".")==0){
                return 3;
            }else{
                return 0;
            }
        }else{return 0;}
    }

    //this method check if op
    public boolean isOp(String s){
        if (s.compareToIgnoreCase("-")==0 || s.compareToIgnoreCase("+")==0 || s.compareToIgnoreCase("x")==0 || s.compareToIgnoreCase("/")==0){
            return true;
        }else{
            return false;
        }
    }

    //this method check if number
    public boolean isNumber(String s){
        if (s.compareToIgnoreCase("0")==0 || s.compareToIgnoreCase("1")==0 || s.compareToIgnoreCase("2")==0 ||  s.compareToIgnoreCase("3")==0
                || s.compareToIgnoreCase("4")==0 || s.compareToIgnoreCase("5")==0 || s.compareToIgnoreCase("6")==0 || s.compareToIgnoreCase("7")==0
                || s.compareToIgnoreCase("8")==0 || s.compareToIgnoreCase("9")==0){
            return true;
        }else{
            return false;
        }
    }

}
