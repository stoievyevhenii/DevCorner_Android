package com.stoiev.devcorner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.firestore.FirebaseFirestore;
import com.stoiev.devcorner.DB.FirebaseActions;
import com.stoiev.devcorner.DB.RoomActions;
import com.stoiev.devcorner.helpers.Message;
import com.stoiev.devcorner.helpers.ThemeChanger;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText newUserLoginField;
    private EditText newUserPasswordField;
    private String newUserLogin;
    private String newUserPassword;

    private Button regBtn;
    private ConstraintLayout constraintLayout;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> newUser = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /////////////////////
        // --- Actions --- //
        /////////////////////
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        newUserLoginField = findViewById(R.id.registerLogin);
        newUserPasswordField = findViewById(R.id.registerPasswd);

        regBtn = findViewById(R.id.reg_btn);

        regBtn.setOnClickListener(view -> {
            newUserLogin = newUserLoginField.getText().toString();
            newUserPassword = newUserPasswordField.getText().toString();
            regNewUser(newUserLogin, newUserPassword);
        });

        final View activityRootView = findViewById(R.id.registerPage);
        ThemeChanger.setTheme(activityRootView, "BOTH");

        constraintLayout = findViewById(R.id.registerPage);
        constraintLayout.post(() -> {
            int height = constraintLayout.getHeight();
            constraintLayout.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
                if ((bottom - top) == height) {
                    hideElementsWheKeyboardIsActive("visible");
                } else {
                    hideElementsWheKeyboardIsActive("gone");
                }
            });
        });

    }

    @SuppressLint("PrivateResource")
    protected void regNewUser(final String newUserLogin, final String newUserPassword) {

        if (newUserLogin.isEmpty() || newUserPassword.isEmpty()) {
            Message successMessage = new Message("Oooooops!", "One of the fields is empty!");
            successMessage.show(getSupportFragmentManager(), "success dialog");
        } else {
            FirebaseActions user = new FirebaseActions();
            user.regNewUser(newUserLogin, newUserPassword);

            setUserStatusAndRedirectToHome(newUserLogin);
        }
    }

    private void hideElementsWheKeyboardIsActive(String visibility){
        ImageView logInIcon = findViewById(R.id.signin_icon);

        switch(visibility){
            case "gone":
                logInIcon.setVisibility(View.GONE);
                break;
            case "visible":
                logInIcon.setVisibility(View.VISIBLE);
                break;
        }

    }

    public void setUserStatusAndRedirectToHome(String login) {
        RoomActions.setUserStatus(login);
        Intent openHomePage = new Intent(this, HomeActivity.class);
        startActivity(openHomePage);
        finish();
    }

}
