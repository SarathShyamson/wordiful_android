package com.shyamapps.wordiful;

//import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.KeyEvent;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    // private TextInputEditText textInput1;
    //  private int i=2;
    //  private String column="message";
    TextView p_clue;
    TextInputEditText p_userinput;
    ProgressBar p_progress;
    TextView p_debug, p_answer;
    TextWatcher text = null;
    int pro;
    int one_prog;
    int compareNum;
    String cluein;
    String path1;
    String path2;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p_clue = findViewById(R.id.clue);
        p_userinput = findViewById(R.id.userinput);
        p_progress = findViewById(R.id.progress);
        p_debug = findViewById(R.id.debug);
        p_answer = findViewById(R.id.answer);
        //p_progress.setMin(0);
        //p_progress.setMax(100);

        //  textView1.setText("hi");
        //  textInput1 = findViewById(R.id.textInput);

        Button buttonPlay = findViewById(R.id.play);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWord();
                //  storeText();
            }
        });
        // Write a message to the database


    }

    // private void storeText()
    // {
    //     column="message";
    //    i++;
    //    column = column + i;



    //    myRef.setValue(textInput1.getText());
    //  String answer = String.valueOf(textInput1.getText());
    // myRef.setValue(answer);
//        String answer = String.valueOf(textInput1.getText());

    //      if(answer.equals("rude")) {
    //        textView1.setText(textInput1.getText());
    //  }

    // }

    private void setWord() {
        p_userinput.setText("");
        p_answer.setText("answer");
        int ran = Math.toIntExact(Math.round(getRandomIntegerBetweenRange(1, 4519)));
        path1 = Integer.toString(ran) + "/capable";
        System.out.println(path1);
        myRef = database.getReference(path1);
        //myRef.setValue("Hello, World!");
        // Read from the database

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("shyam","Value is: " + value);
                p_clue.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("shyam", "Failed to read value.", error.toException());
            }
        });
        // Attach a listener to read the data at our posts reference
        path1 = Integer.toString(ran) + "/able";
        myRef = database.getReference(path1);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("shyam","Value is: " + value);
                cluein=value;
                p_debug.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("shyam", "Failed to read value.", error.toException());
            }
        });

    //    String value_data = myRef.getKey();
    //    p_debug.setText(value_data);
        // DatabaseReference myRef = database.getReference("1");
        //   String keys = myRef.child("1").getKey();
        //  String message = "hi";



        String answer = String.valueOf(p_userinput.getText());
     //   if (answer.equals("ability")) {
    //        p_progress.setProgress(10);
    //    }

        text = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                compareNum = 0;
               // String cluein = p_clue.getText().toString();


                String usrinp = p_userinput.getText().toString();

                for(int i=0; i<usrinp.length(); i++) {
                    for (int j = i; j < cluein.length(); j++) {
                        if (usrinp.charAt(i) == cluein.charAt(j)) {
                            one_prog = 100/cluein.length();
                            compareNum++;
                            one_prog=one_prog*compareNum;
                            p_progress.setProgress(one_prog);

                        }
                    }

                        }
                if(compareNum==cluein.length()+1)
                {
                    p_answer.setText("Yes the answer is " + cluein);
                }
                if(usrinp.length()==0)
                {
                    p_progress.setProgress(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        p_userinput.addTextChangedListener(text);
    }

    public static double getRandomIntegerBetweenRange(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    /*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_A:
            {
                //your Action code
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}*/
}


