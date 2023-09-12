package com.example.reciview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText textUsername;
    private EditText textPassword;

    //Firebase classes.
    FirebaseDatabase rootNode;
    DatabaseReference dbRef1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //Locate textviews and button.
        btnLogin = findViewById(R.id.button_login);
        textUsername = findViewById(R.id.editText_username);
        textPassword = findViewById(R.id.editText_password);

        //Search the db for an account with the entered username and password.
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the entered email and password.
                String enteredUsername = textUsername.getText().toString();
                String enteredPassword = textPassword.getText().toString();

                dbRef1 = FirebaseDatabase.getInstance().getReference("accounts");

                //TODO: Figure out how this actually works, make it work with email instead of username.
                //Search for account that has the username entered by the user.
                //orderByChild instructs which keyword to search for, then checks for entry that
                //matches the value entered by the user.
                Query findAccount = dbRef1.orderByChild("username").equalTo(enteredUsername);

                findAccount.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Check if there is actually data inside the snapshot and retrieve it.
                        if(snapshot.exists()){
                            //Retrieve from the snapshot the password of the account that matched
                            //with the email entered by the user.
                            String accountDBPassword = snapshot.child(enteredUsername).
                                    child("password").getValue(String.class);

                            //TODO: SMS login?
                            //Check if both passwords actually match. Then retrieve account info.
                            //Account info is then used by all other activites to check who is
                            //logged in.
                            if(accountDBPassword.equals(enteredPassword)){
                                String accountDBEmail = snapshot.child(enteredUsername).
                                        child("emailAddress").getValue(String.class);

                                String accountDBUsername = snapshot.child(enteredUsername).
                                        child("username").getValue(String.class);

                                String accountDBPhonenum = snapshot.child(enteredUsername).
                                        child("phonenum").getValue(String.class);

                                //Start homepage activity and pass account info into intent.
                                Intent intentHomescreen = new Intent(getApplicationContext(), HomeScreen.class);
                                intentHomescreen.putExtra("username",accountDBUsername);
                                intentHomescreen.putExtra("password",accountDBPassword);
                                intentHomescreen.putExtra("emailAddress",accountDBEmail);
                                intentHomescreen.putExtra("phonenum",accountDBPhonenum);
                                startActivity(intentHomescreen);
                            }//end if

                            //Else, the passwords don't match so don't sign in.
                            else{
                                Toast.makeText(SignInActivity.this,
                                        "Wrong password entered.", Toast.LENGTH_SHORT).show();
                                return;
                            }//end else

                        }//end if

                        //Else, no data in snapshot so can't check. User doesn't exist.
                        else{
                            Toast.makeText(SignInActivity.this,
                                    "Error retrieving data, no such user.", Toast.LENGTH_SHORT).show();
                            return;
                        }//end else

                    }//end onDataChange()

                    //TODO: Create better messages and errors.
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SignInActivity.this,
                                "Database error.", Toast.LENGTH_SHORT).show();
                        return;

                    }
                });

            }
        });//end OnClickListener()

    }//end OnCreate()
}