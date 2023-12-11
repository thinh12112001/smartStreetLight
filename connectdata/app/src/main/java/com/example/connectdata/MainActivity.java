//package com.example.connectdata;
//
//import static com.example.connectdata.R.id.showBtn;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class MainActivity extends AppCompatActivity {
//    TextView a,b,c;
//    Button btn,bts;
//    DatabaseReference reff;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        a = (TextView) findViewById(R.id.nameView);
//        b = (TextView) findViewById(R.id.ageView);
//        c = (TextView) findViewById(R.id.phoneView);
//        btn = (Button) findViewById(R.id.showBtn);
//        bts = (Button) findViewById(R.id.sentbtn);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                reff = FirebaseDatabase.getInstance().getReference().child("1");
//                reff.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                       String name = dataSnapshot.child("Ho-Ten").getValue().toString();
//                        String age = dataSnapshot.child("Tuoi").getValue().toString();
//                        String phone = dataSnapshot.child("Phone").getValue().toString();
//                       a.setText(name);
//                        b.setText(age);
//                        c.setText(phone);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });
//        bts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                reff = FirebaseDatabase.getInstance().getReference().child("1");
//                reff.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        reff.child("Ho-Ten").setValue(a.getText().toString());
//                        reff.child("Tuoi").setValue(b.getText().toString());
//                        reff.child("Phone").setValue(c.getText().toString());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });
//    }
//}
////btnSendData.setOnClickListener(new View.OnClickListener(){
////@Override
////public void onClick(View v){
////        reff = FirebaseDatabase.getInstance().getReference().child("1");
////        reff.addValueEventListener(new ValueEventListener() {
////@Override
////public void onDataChange(@NonNull DataSnapshot snapshot) {
////        if (snapshot.exists()) {
////
//////                            String name = snapshot.child("Ho-ten").getValue().toString();
////        reff.child("Ho-Ten").setValue(a.getText().toString());
////        reff.child("Tuoi").setValue(b.getText().toString());
////        reff.child("Phone").setValue(c.getText().toString());
//////                            String age = snapshot.child("Tuoi").getValue().toString();
//////                            String phone = snapshot.child("Phone").getValue().toString();
//////                            a.setText(name);
//////                            b.setText(age);
//////                            c.setText(phone);
////        }
////        }
////
////
////@Override
////public void onCancelled(@NonNull DatabaseError error) {
////
////        }
////        });
////        }
////        });

package com.example.connectdata;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.slider.Slider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import kotlinx.coroutines.selects.WhileSelectKt;

public class MainActivity extends AppCompatActivity {
    Button  btnSendData,btnGetData;
    DatabaseReference reff;
    TextView motion, light,water;

    float sliderNo, sliderYes;
    Slider ledYes, ledNo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        motion = (TextView) findViewById(R.id.anyBody);
        light = (TextView) findViewById(R.id.isDarkOrNot);
        water = (TextView) findViewById(R.id.isRainOrNot);
        btnGetData = (Button) findViewById(R.id.btnGetData);

        ledYes = findViewById(R.id.ledYes);
        ledNo = findViewById(R.id.ledNo);
        btnSendData = (Button) findViewById((R.id.btnSendData));

        btnGetData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                reff = FirebaseDatabase.getInstance().getReference();
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String motionSensor = snapshot.child("anybody").getValue().toString();
                            String lightSensor = snapshot.child("isDarkOrLight").getValue().toString();
                            String waterSensor = snapshot.child("isRainOrNot").getValue().toString();

                            float slider_Yes = snapshot.child("ledYes").getValue(float.class);
                            ledYes.setValue(slider_Yes);
                            float slider_No = snapshot.child("ledNo").getValue(float.class);
                            ledNo.setValue(slider_No);

                            motion.setText(motionSensor);
                            light.setText(lightSensor);
                            water.setText(waterSensor);

                            Toast toast = Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER,20,30);
                            toast.show();
                        } else {
                            Toast toast = Toast.makeText(MainActivity.this, "Có lỗi đã xảy ra", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER,20,30);
                            toast.show();
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });





        final Slider.OnSliderTouchListener touchListenerYes =
                new Slider.OnSliderTouchListener() {
                    @Override
                    public void onStartTrackingTouch(Slider slider) {

                    }

                    @Override
                    public void onStopTrackingTouch(Slider slider) {
                        sliderYes = ledYes.getValue();
                    }
                };

        final Slider.OnSliderTouchListener touchListenerNo =
                new Slider.OnSliderTouchListener() {
                    @Override
                    public void onStartTrackingTouch(Slider slider) {

                    }

                    @Override
                    public void onStopTrackingTouch(Slider slider) {
                        sliderNo = ledNo.getValue();
                    }
                };

        ledYes.addOnSliderTouchListener(touchListenerYes);

        ledNo.addOnSliderTouchListener(touchListenerNo);

        btnSendData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                reff = FirebaseDatabase.getInstance().getReference();
//                reff = FirebaseDatabase.getInstance().getReference().child("LED");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            reff.child("ledYes").setValue(sliderYes);
                            reff.child("ledNo").setValue(sliderNo);
                            Toast toast = Toast.makeText(MainActivity.this, "Gửi dữ liệu thành công", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER,20,30);
                            toast.show();

                        } else {
                            Toast toast = Toast.makeText(MainActivity.this, "Có lỗi đã xảy ra", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER,20,30);
                            toast.show();
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

//        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//            }
//
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked, DataSnapshot snapshot) {
//                String status = snapshot.child("Status").getValue().toString();
//                if (isChecked) {
//                    reff.child("Status").setValue("ON");
//                } else {
//                    reff.child("Status").setValue("OFF");
//                }
//                b.setText(status);
//            }
//        });
    }
}