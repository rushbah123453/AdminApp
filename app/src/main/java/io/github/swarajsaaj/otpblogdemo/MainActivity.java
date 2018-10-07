package io.github.swarajsaaj.otpblogdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import io.github.swarajsaaj.otpblogdemo.NetworkCall.AsyncResponse;
import io.github.swarajsaaj.otpblogdemo.NetworkCall.BckgroundTask;




public class MainActivity extends AppCompatActivity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                Log.d("Text",messageText);
                String[] transaction = messageText.split(",");
                Toast.makeText(MainActivity.this,"Message: "+transaction[0]+"/"+transaction[1],Toast.LENGTH_LONG).show();
                String smsString = messageText.replaceAll("\\D+", "");
                Toast.makeText(MainActivity.this,"Message: "+smsString,Toast.LENGTH_LONG).show();

                BckgroundTask bckgroundTask1=new BckgroundTask(getApplicationContext());
                bckgroundTask1.output=MainActivity.this;
                bckgroundTask1.execute("payMoney",transaction[0],transaction[1],transaction[2]);



            }
        });
    }

    @Override
    public void AsyncFinnished(String output) throws JSONException {

    }
}
