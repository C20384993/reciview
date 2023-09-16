package com.example.reciview;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterAccount extends AppCompatActivity {

    //Declare variables/buttons from layout.
    //TODO: Add return to main screen button.
    //TODO: Improve visuals.
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
                //Store the values from the text fields.
                //Phonenum: 10 max and min.
                //email: must match proper email format.
                //username: min 6, max 14
                //password: must contain min 7, max 15, 1 special/number/capital
                String phonenum = newPhoneNum.getEditText().getText().toString();
                String email = newEmailAddress.getEditText().getText().toString();
                String username = newUsername.getEditText().getText().toString();
                String password = newPassword.getEditText().getText().toString();
                String confirmPassword = newConfirmPassword.getEditText().getText().toString();

                //TODO: Develop checks further if possible.
                //Field checks
                if(phonenum == null || phonenum.length() != 10){
                    Toast.makeText(RegisterAccount.this,
                            "Invalid phone number.", Toast.LENGTH_SHORT).show();
                    return;
                }//end else if

                //TODO: Create own method to check email.
                else if(!isEmailValid(email)){
                    Toast.makeText(RegisterAccount.this,
                            "Invalid email entered.", Toast.LENGTH_SHORT).show();
                    return;
                }//end else if

                else if(username == null || username.length() < 6){
                    Toast.makeText(RegisterAccount.this,
                            "Username is too short.", Toast.LENGTH_SHORT).show();
                    return;
                }//end else if

                else if(username.length() > 14){
                    Toast.makeText(RegisterAccount.this,
                            "Username is too long.", Toast.LENGTH_SHORT).show();
                    return;
                }//end else if

                //TODO: Check password contains at least 1 special char, number, and capital.
                else if(password == null || password.length() < 7){
                    Toast.makeText(RegisterAccount.this,
                            "Password is too short.", Toast.LENGTH_SHORT).show();
                    return;
                }//end else if

                //If password doesn't match confirm password, don't create account
                else if (password.equals(confirmPassword) != true) {
                    Toast.makeText(RegisterAccount.this,
                            "Passwords don't match.", Toast.LENGTH_SHORT).show();
                    return;
                }//end else if

                //If all checks pass.
                else {
                    //Call the root node of the db.
                    rootNode = FirebaseDatabase.getInstance();
                    //Instructs the app to store accounts in this path.
                    dbRef1 = rootNode.getReference("accounts");

                    /*
                    Log.d("DebugTagVALUES", "Password: "+password);
                    Log.d("DebugTagVALUES", "Username: "+username);
                    Log.d("DebugTagVALUES", "Email: "+email);
                    Log.d("DebugTagVALUES", "Phonenum: "+phonenum);*/

                    //Check if an account with the username exists.
                    Query findAccount = dbRef1.orderByChild("username").equalTo(username);
                    findAccount.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                if(username.equals(snapshot.child(username).child("username")
                                        .getValue(String.class))){
                                    Toast.makeText(RegisterAccount.this,
                                            "Username taken.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                //TODO: Check email and phone number not already used by another account. Currently returns null value.
                                //TODO: Fix email taken check code.
                                //Also check that email is not being used by another account.
                                else if(email.equals(snapshot.child(username)
                                        .child("emailAddress").getValue(String.class))){
                                    Toast.makeText(RegisterAccount.this,
                                            "Email taken.", Toast.LENGTH_SHORT).show();
                                    return;
                                }//end else if
                            }//end if
                            else{
                                Log.d("DebugTagVALUES", "DEBUGEmail: "+snapshot.child(username).child("emailAddress").getValue(String.class));
                                //Create account by adding to db.
                                Account userAccount = new Account(phonenum, email,
                                        username, password);
                                //The .child() value will determine how the account is identified.
                                //11/9/23: Currently using "username" as account ID.
                                dbRef1.child(username).setValue(userAccount);
                                Toast.makeText(RegisterAccount.this,
                                        "Registration successful.", Toast.LENGTH_SHORT).show();
                                //return;
                                startActivity(new Intent(RegisterAccount.this,
                                        MainActivity.class));
                            }//end else
                        }//end onDataChange()

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(RegisterAccount.this,
                                    "Database error.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    });//end SingleValueEventListener
                }//end else
            }//end OnClick()
        });//end SetOnClickListener()
    }//end OnCreate()

    //Check if email is valid.
    //Source: https://stackoverflow.com/questions/6119722/how-to-check-edittexts-text-is-email-address-or-not
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}