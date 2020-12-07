package com.example.primertrabajo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText correo, pass;
    Button btn_ingresar, btn_registrar;

    String email = "";
    String password = "";

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        correo = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.txt_ingresar);
        btn_ingresar = (Button) findViewById(R.id.btn_ingresar);
        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = correo.getText().toString();
                password = pass.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()) {
                    login();
                } else {
                    Toast.makeText(MainActivity.this, "Complete los datos ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_registrar = (Button) findViewById(R.id.btn_registrarse);
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registrar();
            }
        });


    }

    public void login() {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this, MainActivity3.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "No se pudo inicar sesion", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


   public void Registrar(){
        Intent registrar = new Intent(this, MainActivity2.class);
        startActivity(registrar);
    }


}