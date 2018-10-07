package io.github.swarajsaaj.otpblogdemo.NetworkCall;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;



import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by rushabh on 18/2/18.
 */

public class BckgroundTask  extends AsyncTask<String, Void, String> {

    Context ctx;
    static MediaType JSON = null;
    Response responseGlobal;
    String methodType=null;
    ResponseBody responseBody;
    String finalResult;
    public AsyncResponse output=null;

    public BckgroundTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... voids) {


        String addmoney = "http://18.219.106.142:8080/addmoney";
        String pay_url="http://18.219.106.142:8080/transfer";

        String method = voids[0];

        Response response = null;

        //int responseCode = 0;
        try {

            JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();






             if(method.equals("addmoney"))
            {
                System.out.print("In Add Money block");
                String phone = voids[1];
                String amt = voids[2];
                methodType="addmoney";
                JSONObject jsonParam1 = new JSONObject();
                jsonParam1.put("tophone", phone);
                jsonParam1.put("amount", amt);
                RequestBody body = RequestBody.create(JSON, jsonParam1.toString());
                Request request = new Request.Builder()
                        .url(addmoney)
                        .post(body)
                        .build();
                response = client.newCall(request).execute();
//                System.out.print("Response:" + response.body());
            }
           else if(method.equals("payMoney")){

                String fromMobile = voids[1];
                String toMobile= voids[2];
                String payAmount = voids[3];

                methodType="payMoney";

                JSONObject jsonParam3 = new JSONObject();
                jsonParam3.put("fromphone", fromMobile);
                jsonParam3.put("tophone", toMobile);
                jsonParam3.put("amount", payAmount);


                RequestBody body = RequestBody.create(JSON, jsonParam3.toString());
                Request request = new Request.Builder()
                        .url(pay_url)
                        .post(body)
                        .build();

                response = client.newCall(request).execute();

            }
            // conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(ctx,"exception"+e.toString(),Toast.LENGTH_SHORT).show();
            System.out.print(e.getMessage().toString());
          //  String err = String.format("{\"result\":\"false\",\"error\":\"%s\"}", e.getMessage());
            //System.out.print(err);

        }

       // responseGlobal = response;

      //  Toast.makeText(ctx,"result"+client.newCall(request).execute(),Toast.LENGTH_SHORT).show();
      /*  try {
           Log.e("in try",response.body().string());
            return  response.body().string();       //name,aadhaar,email,phone,password,dob
          //  Log.e("Resonse",response.body().string());




        } catch (IOException ioe) {
            return "" + ioe.getStackTrace();

        }*/
        String content = null;



          try {

                  responseBody = response.body();
              content = responseBody.string();
          } catch (Exception e) {
              e.printStackTrace();
              content="Server_error";
          }

            /*  ResponseBody responseBodyCopy = response.peekBody(Long.MAX_VALUE);
            String content= responseBodyCopy.string();*/


        return content;


       // return "abc";


    }






    public BckgroundTask() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

   @Override
    protected void onPostExecute(String result) {



        finalResult=result;
            /*    Intent intentnew=new Intent(ctx,MainActivity.class);
        ctx.startActivity(intentnew);*/


            //Toast.makeText(ctx,"this is response "+result,Toast.LENGTH_SHORT).show();



             if (methodType.equals("payMoney") && result.equals("1452"))
            {
                Toast.makeText(ctx,"Reciepient User Not Existing", Toast.LENGTH_SHORT).show();
                try {
                    output.AsyncFinnished(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            else if (methodType.equals("payMoney") && result.equals("1644"))
            {
                Toast.makeText(ctx,"Insufficient balance", Toast.LENGTH_SHORT).show();
                try {
                    output.AsyncFinnished(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            else if (methodType.equals("payMoney") && result.equals("1"))
            {
                Toast.makeText(ctx,"Added Money", Toast.LENGTH_SHORT).show();
                try {
                    output.AsyncFinnished(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }




    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }





}
