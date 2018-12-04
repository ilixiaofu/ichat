package com.lxf.ichat.util;


public class HttpCilentUtil {

//    public void doGet(String url, Callback responseCallback) {
//        HttpGet httpGet = new HttpGet( url );
//        HttpClient httpClient = new DefaultHttpClient();
//        try {
//            HttpResponse httpResponse = httpClient.execute( httpGet );
//            HttpEntity httpEntity = httpResponse.getEntity();
//            responseCallback.onResponse(httpEntity);
//        } catch (IOException e) {
//            responseCallback.onFailure(e);
//        }
//    }

//    public void doPost(String url, List<BasicNameValuePair> params, Callback responseCallback) {
//        try {
//            HttpPost httpPost = new HttpPost( url );
//            HttpClient httpClient = new DefaultHttpClient();
//            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity( params );
//            httpPost.setEntity( formEntity );
//            HttpResponse httpResponse = httpClient.execute( httpPost );
//            HttpEntity httpEntity = httpResponse.getEntity();
//            responseCallback.onResponse(httpEntity);
//        } catch (IOException e) {
//            responseCallback.onFailure(e);
//        }
//    }

//    public interface Callback {
//
//        void onFailure(IOException e);
//
//        void onResponse(HttpEntity response) throws IOException;
//    }
}
