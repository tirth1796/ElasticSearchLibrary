package appbase.elasticsearchlibrary;

import android.util.Base64;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;


/**
 * Created by Tirth Shah on 10-05-2016.
 */





public class Elastic {

    AsyncHttpClient httpClient;
    private String baseURL,URL,password,userName,app;

    private static final String SEPARATOR ="/";


    //Do not include a / anywhere.
    //URL is the base URL.
    //App is the app name.
    //userName is the userName for your app.
    //password which matches with the username.
    //use the setters to set the the URL, app, userName, password.

    public class BulkRequestObject{
        private String type,id,jsonDoc;
        private int method;
        public static final int INDEX=0,DELETE=1,UPDATE=2;

        //Put jsonDoc as null if delete wanted.
        public BulkRequestObject(String type, String id, int method,String jsonDoc) {
            this.type = type;
            this.id = id;
            this.method = method;
            this.jsonDoc=jsonDoc;
        }


    }

    public Elastic(String URL, String password, String userName, String app) {
        this.baseURL =URL;
        this.password = password;
        this.userName = userName;
        this.app = app;
        constructURL();
        httpClient=new DefaultAsyncHttpClient();

    }


    public String getURL(String type) {

        return URL+ SEPARATOR +app+ SEPARATOR +type+ SEPARATOR;
    }
    public String getURL(String type,String id) {

        return URL+ SEPARATOR +app+ SEPARATOR +type+ SEPARATOR +id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSearchUrl(String term) {
        return URL+ SEPARATOR +app + SEPARATOR + "_search?q=" + term;
    }


    public void setURL(String URL) {
        this.baseURL = URL;
        constructURL();
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private void constructURL(){
        this.URL=this.baseURL+SEPARATOR+app;
    }

    private String getAuth() {
        String Auth = this.userName+ ":" + this.password;
        return Base64.encodeToString(Auth.getBytes(), 0);
    }



    //Main library methods



/*
* index()
* update()
* delete()
* bulk()
* get()
* getTypes()
* search()
* getStream()
* searchStream()
* searchStreamToURL()
*
* */





    public void index(String type, String id, String jsonDoc) {

        RequestBuilder builder = new RequestBuilder("PUT");
        Request request = builder.setUrl(getURL(type, id))
                .addHeader("Authorization", "Basic " + getAuth())
                .setBody(jsonDoc)
                .build();
        httpClient.executeRequest(request);

    }

    public void updateDocument(String type, String id, String jsonDoc) {
        index(type, id, jsonDoc);
    }

    public void deleteDocument(String type, String id) {
        httpClient.prepareDelete(getURL(type, id))
                .addHeader("Authorization", "Basic " + getAuth())
                .execute();

    }

    public void bulk(BulkRequestObject[] objects){
        for (int i = 0; i < objects.length; i++) {
            switch (objects[i].method){
                case 0:
                    index(objects[i].type,objects[i].id,objects[i].jsonDoc);
                    break;
                case 1:
                    deleteDocument(objects[i].type,objects[i].id);
                    break;
                case 2:
                    updateDocument(objects[i].type,objects[i].id,objects[i].jsonDoc);
                    break;
                default:
                    index(objects[i].type,objects[i].id,objects[i].jsonDoc);
                    break;
            }

        }
    }

    public void get(String type,String id){
        httpClient.prepareGet(getURL(type,id))
                .addHeader("Authorization", "Basic " + getAuth())
                .execute();
    }

    public void getTypes(){

    }


    public void search(String json, String type) {
        httpClient.prepareGet(getURL(type) + "_search")
                .addHeader("Authorization", "Basic " + getAuth())
                .execute();
    }


    //Search Document
    public void searchDocument(String type, String id) {
        httpClient.prepareGet(getURL(type, id))
                .addHeader("Authorization", "Basic " + getAuth())
                .execute();


    }





    public void searchUri(String term) {

        httpClient.prepareGet(getSearchUrl(term))
                .addHeader("Authorization", "Basic " + getAuth())
                .execute();

    }


    public void searchStream(){}





    //Extremely doubtful.
    public void autoId(String type, String jsonDoc) {
        RequestBuilder builder = new RequestBuilder("PUT");
        Request request = builder.setUrl(getURL(type))
                .addHeader("Authorization", "Basic " + getAuth())
                .setBody(jsonDoc)
                .build();
        httpClient.executeRequest(request);
    }
}
// debug links

    /*private String URL="http://scalr.api.appbase.io",
            app="Trial1796",
            userName="vspynv5Dg",
            password="f54091f5-ff77-4c71-a14c-1c29ab93fd15";*/