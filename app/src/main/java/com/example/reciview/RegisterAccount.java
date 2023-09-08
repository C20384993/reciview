package com.example.reciview;

import androidx.appcompat.app.AppCompatActivity;

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

        //TODO: Stop db from deleting old account when new one is made.
        //Add new user to db.
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Call the root node of the db.
                rootNode = FirebaseDatabase.getInstance();
                dbRef1 = rootNode.getReference("accounts");

                //Store the values from the text fields.
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
                    dbRef1.setValue(userAccount);
                }//end else

            }
        });

    }//end OnCreate()
}