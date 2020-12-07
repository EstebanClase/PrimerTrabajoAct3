package com.example.primertrabajo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    EditText nombre, correo, pass;
    Button btn_volver, btn_registrar;
    FirebaseAuth mAuth;

    String name= "";
    String email = "";
    String password = "";

    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nombre =(EditText)findViewById(R.id.txt_nombre);
        correo = (EditText)findViewById(R.id.txt_correo);
        pass = (EditText)findViewById(R.id.txt_pass);
        btn_volver = (Button) findViewById(R.id.btn_volver);
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cerrar();
            }
        });
        btn_registrar = (Button) findViewById(R.id.btn_guardar);
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nombre.getText().toString();
                email = correo.getText().toString();
                password = pass.getText().toString();

                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){

                    if(password.length() >= 6){
                        registar();
                    }
                    else{
                        Toast.makeText(MainActivity2.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity2.this, "Debe ingresar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void registar(){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("password", password);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(MainActivity2.this, MainActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(MainActivity2.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }
                else{
                    Toast.makeText(MainActivity2.this,"No se puedo registar",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        public void Cerrar(){
            finish();
        }
    }
