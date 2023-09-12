package com.example.reciview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterAccount extends AppCompatActivity {

    //Declare variables/buttons from layout.
    private TextInputLayout newPhoneNum;
    private TextInputLayout newEmailAddress;
    private TextInputLayout newUsername;
    private TextInputLayout newPassword;
    private TextInputLayout newConfirmPassword;
    private Button btnCreateAccount;

    //Firebase classes.
    FirebaseDatabase rootNode;
    DatabaseReference dbRef1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);

        //Locate variables from layout
        newPhoneNum = findViewById(R.id.editText_phonenum);
        newEmailAddress = findViewById(R.id.editText_email_register);
        newUsername = findViewById(R.id.editText_username_register);
        newPassword = findViewById(R.id.editText_password_register);
        newConfirmPassword = findViewById(R.id.editText_passwordConfirm_register);
        btnCreateAccount = findViewById(R.id.button_create_account);

        //Add new user to db.
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Call the root node of the db.
                rootNode = FirebaseDatabase.getInstance();
                //Instructs the app to store accounts in this path.
                dbRef1 = rootNode.getReference("accounts");

                //Store the values from the text fields.
                //TODO: Checks for proper formatting of email, phone num, etc. Should be part of xml
                // already.
                String phonenum = newPhoneNum.getEditText().getText().toString();
                String email = newEmailAddress.getEditText().getText().toString();
                String username = newUsername.getEditText().getText().toString();
                String password = newPassword.getEditText().getText().toString();
                String confirmPassword = newConfirmPassword.getEditText().getText().toString();
                /*
                Log.d("DebugTagVALUES", "Password: "+password);
                Log.d("DebugTagVALUES", "Username: "+username);
                Log.d("DebugTagVALUES", "Email: "+email);
                Log.d("DebugTagVALUES", "Phonenum: "+phonenum);*/

                //If password doesn't match confirm password, don't create account
                if(password.equals(confirmPassword) != true){

                    Toast.makeText(RegisterAccount.this,
                            "Passwords don't match.", Toast.LENGTH_SHORT).show();
                    return;
                }//end if

                //Else, create account by adding to db.
                else{
                    Account userAccount = new Account(phonenum, email, username, password);
                    //The .child() value will determine how the account is identified.
                    //11/9/23: Currently using "username" as account ID.
                    //TODO: Check username is unique/not in db already.
                    dbRef1.child(username).setValue(userAccount);
                    Toast.makeText(RegisterAccount.this,
                            "Registration successful.", Toast.LENGTH_SHORT).show();
                    //return;
                    startActivity(new Intent(RegisterAccount.this,
                            MainActivity.class));
                }//end else

            }
        });

    }//end OnCreate()
}