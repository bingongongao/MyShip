package com.example.huuph.myship.uis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huuph.myship.ForgotPass;
import com.example.huuph.myship.R;
import com.example.huuph.myship.Signup;
import com.example.huuph.myship.uis.main_main;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private EditText edLoginUser;
    private EditText edLoginPass;

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    String email, name, id_facebook; //mail va name facebook

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);
        Initialization();

        setLogin_Button();
    }

    private void Initialization() {
        Log.d("anhxa", "anhxathanhcong");
        edLoginUser = (EditText) findViewById(R.id.edLoginUser);
        edLoginPass = (EditText) findViewById(R.id.edLoginPass);


        //đăng nhập lại mỗi khi vào ứng dụng
        //onStart();
    }

    //login facebook
    private void setLogin_Button() {

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_facebook);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //TODO đăng nhập fb thành công
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                //result();

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                //TODO Cancel fb
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                //TODO lỗi
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //dang nhap thanh cong
                    //todo lay thong tin avatar, mail
                    Log.d(TAG,"onAuthStateChanged:signed_in"+ user.getUid());
                }else{
                    Log.d(TAG,"onAuthStateChanged:signed_out");

                }

            }
        };
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(mAuthListener != null){
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    //log thong tin nguoi dung ve
//
//    private void result() {
//        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//
//                //xuất ra log thông tin id, name, mail khi đăng nhập thành công
//                Log.d("JSON", response.getJSONObject().toString());
//                //thấy thông tin
//                try {
//                    email = object.getString("email");
//                    name = object.getString("name");
//                    id_facebook = object.getString("id");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,email");
//        graphRequest.setParameters(parameters);
//        graphRequest.executeAsync();
//    }



    public void onclickLogin(View view) {
        //TODO gửi lên firrebase và check acc (Phước)
        Intent intent = new Intent(this, main_main.class);
        startActivity(intent);
    }


    public void onClickSignUp(View view) {
        //TODO Chuyển sang activity đăng kí
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

    public void onClickFogotpass(View view) {
        //TODO Chuyển sang layout quên mật khẩu
        Intent intent = new Intent(this, ForgotPass.class);
        startActivity(intent);
    }
}
