package com.sunmi.tafani_printerhelper.nextgen_sharique.voly_url;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sunmi.tafani_printerhelper.nextgen_sharique.interf.InterHttpServerResponse;

public class VollyRequestResponse {

    Context ctx;
    int requestNo;
    String bodyString;
    String url;

    InterHttpServerResponse interHttpServerResponse;

    JSONObject jsonObject;

    public VollyRequestResponse(final Context ctx, InterHttpServerResponse inter, final int requestNo, final String url, final String body) {

        this.ctx = ctx;
        this.requestNo = requestNo;
        this.interHttpServerResponse = inter;
        this.url = url;
        this.bodyString = body;



            try {

                RequestQueue queue = Volley.newRequestQueue(ctx);
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(bodyString),

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject serverResponse)
                            {

                                Log.e("######################","######################");
                                Log.e("--- RequestNo ---",""+requestNo);
                                Log.e("--- URL ---",""+url);
                                Log.e("---Server Request ---",""+body);
                                Log.e("---Server Response---",""+serverResponse);
                                Log.e("######################","######################");


                                interHttpServerResponse.serverResponse(requestNo,serverResponse);


                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println(error);
                                //   hideProgressDialog();


                                String json = "{\"Time Out\":\"Time Out\"}";

                                try {
                                    JSONObject obj = new JSONObject(json);

                                    interHttpServerResponse.serverResponse(requestNo,obj);

                                } catch (JSONException e) {

                                    Toast.makeText(ctx,e.toString(), Toast.LENGTH_LONG).show();

                                    e.printStackTrace();
                                }
                                }


                        }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        Map<String, String> headers = new HashMap<String, String>();

                        //  headers.put("Content-Type", "application/json");
                        //  headers.put("Authorization", "Basic c29hcHVzZXI6c2t2QDI1ODA=");


                        return headers;
                    }
                };

                jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(

                        30000, //30000

                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(jsObjRequest);


            } catch (Exception e) {
                System.out.println(e);
                //  hideProgressDialog();

                String json = "{\"Please try again later\":\"Please try again later\"}";

                try {
                    JSONObject obj = new JSONObject(json);

                    interHttpServerResponse.serverResponse(requestNo,obj);

                } catch (JSONException e2) {

                    Toast.makeText(ctx,e.toString(), Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }
            }

            }
        }
