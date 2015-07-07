package com.exploreru.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 * Created by Sam on 6/2/2015.
 */
public class HTTPNetwork{

    Context c = null;
    String uri = null;

    public HTTPNetwork(Context c, String uri){

        this.c = c;
        this.uri = uri;
    }

    public boolean isOnline(){

        ConnectivityManager net = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = net.getActiveNetworkInfo();
        if(info != null && info.isConnectedOrConnecting()){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isOnline(Context c){

        ConnectivityManager net = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = net.getActiveNetworkInfo();
        if(info != null && info.isConnectedOrConnecting()){
            return true;
        } else {
            return false;
        }
    }

    public String getData(){

        AndroidHttpClient client = AndroidHttpClient.newInstance("AndroidAgent");
        HttpGet request = new HttpGet(uri);
        HttpResponse response;

        try{
            if(isOnline()) {
                response = client.execute(request);
                return EntityUtils.toString(response.getEntity());
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            client.close();

        }

    }

    public void setURL(String uri){

        this.uri = uri;
    }

}
