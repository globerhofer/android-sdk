package io.catalyze.android.example;


import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.catalyze.sdk.android.*;
import io.catalyze.sdk.android.user.Gender;
import io.catalyze.sdk.android.user.ZipCode;



public class MainActivity extends Activity {

    private MyApplication mMyApplication;
    private CatalyzeUser mUser;
    private Catalyze catalyze;
    private CustomClass customClass;
    private static final String APIKey = "1ca769ce-07b3-40bf-95c0-2feda3e3c909";//io.catalyze.example or something
    private static final String api =    "1f077962-18cc-4ade-8075-d9fa1642f316";
    private static final String identifier = "android.example";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMyApplication = (MyApplication) getApplication();
        mMyApplication.getRequestQueue().start();
        
        catalyze = new Catalyze(api, identifier, MainActivity.this);
        //Catalyze.setAppID(AppId);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	EditText mEdit = (EditText)findViewById(R.id.userNameTextField);
            	String username = mEdit.getText().toString();
            	mEdit = (EditText)findViewById(R.id.passwordTextField);
            	
                catalyze.getUser(username, mEdit.getText().toString(), newUserResponseHandler());
            }
        });
        
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	catalyze.logoutCurrentUser(newResponseHandler());
            }
        });

        findViewById(R.id.GetUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	JSONObject newInstance = new JSONObject();
            	try{
            	newInstance.put("", "philip");
            	newInstance.put("occupation", "barber");
            	newInstance.put("location", "somewhere");
            	newInstance.put("age", 55);
            	}
            	catch(JSONException e){
            		
            	}
            	CustomClass.addInstance("customClassTest2x", newInstance, catalyze, newCCHandler());
            	
            	
            	
            	
//            	JSONObject schema = new JSONObject();
//            	try {
//					schema.put("name", "string");
//					schema.put("occupation", "string");
//					schema.put("location", "string");
//					schema.put("age", "integer");
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//            	
//            	
//            	CustomClass cc = new CustomClass("customClassTest1", false, schema, catalyze, newCCHandler());
            	
            	//CustomClass.getInstance(catalyze, "customClassTest", newCCHandler());
            }
        });
        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	EditText mEdit = (EditText)findViewById(R.id.userNameTextField);
            	
            	String username = mEdit.getText().toString();
            	mEdit = (EditText)findViewById(R.id.passwordTextField);
            	Response.Listener<JSONObject> responseHandler = createListener();
            	catalyze.signUp(username, mEdit.getText().toString(), "John", "johnson", newUserResponseHandler());
            	
            	
            }
        });
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	//catalyze.getUser("test@user.com", "testpass", newUserResposneHandler());
            	
            	mUser.setAge(55);
            	mUser.setCity("Madison");
            	mUser.update(newUserResponseHandler(), MainActivity.this);
            }
        });
        
        findViewById(R.id.customClasses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	CustomClass cc = new CustomClass();
            	
            	cc.getCustomClass("customClassTest1", catalyze, newCCHandler());
            	
            }
        });
        
        findViewById(R.id.DeleteUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	catalyze.deleteCurrentUser(newResponseHandler());
            }
        });
        
    }
    
    private Response.Listener<JSONObject> createListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            	//stop loading screen code goes here
            	String s = "hello we are deoingn somet stuff";
            	System.out.println(mUser.getUsername());
            	System.out.println(mUser.getAge());
            	System.out.println(s);
            }
        };
    }
    
    private CatalyzeListener<CatalyzeUser> newUserResponseHandler() {
        return new CatalyzeListener<CatalyzeUser>() {

			@Override
			public void onError(CatalyzeError response) {
				// TODO Auto-generated method stub
				System.out.println("SOMETHING WENT WRONG");
			}

			@Override
			public void onSuccess(CatalyzeUser response) {
				// TODO Auto-generated method stub
				mUser = response;
				String s = "Successful operation!!!";
            	System.out.println(s);
			}
        };
    }
    
    private CatalyzeListener<CatalyzeUser> newResponseHandler() {
        return new CatalyzeListener<CatalyzeUser>() {

			@Override
			public void onError(CatalyzeError response) {
				// TODO Auto-generated method stub
				System.out.println("SOMETHING WENT WRONG");
			}

			@Override
			public void onSuccess(CatalyzeUser response) {
				// TODO Auto-generated method stub
				
			}
        };
    }
    
    private CatalyzeListener<CustomClass> newCCHandler(){
    	return new CatalyzeListener<CustomClass>(){

			@Override
			public void onError(CatalyzeError response) {
				// TODO Auto-generated method stub
				System.out.println("SOMETHING WENT WRONG");
			}

			@Override
			public void onSuccess(CustomClass response) {
				// TODO Auto-generated method stub
				customClass = response;
			}
    		
    	};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
