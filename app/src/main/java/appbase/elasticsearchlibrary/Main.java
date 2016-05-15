package appbase.elasticsearchlibrary;

import org.asynchttpclient.HttpResponseBodyPart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * Created by Tirth Shah on 10-05-2016 for Appbase.io
 */

// http://scalr.api.appbase.io/Trial1796/product/1
public class Main {

    public static void main(String[] args) {


        String user = "vspynv5Dg", pass = "f54091f5-ff77-4c71-a14c-1c29ab93fd15", app = "Trial1796", type = "product", id = "1";
        String URL = "http://scalr.api.appbase.io";
        System.out.println("working");

        Elastic elastic = new Elastic(URL, app, user, pass);
        elastic.getStream(type, id, new ElasticHandler() {

            @Override
            public State onBodyPartReceived(HttpResponseBodyPart bodyPart)
                    throws Exception {
                ByteArrayOutputStream localBytes = new ByteArrayOutputStream();

                try {
                    localBytes.write(bodyPart.getBodyPartBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(localBytes);
                return State.CONTINUE;
            }
        } );

    }
}

/*
 * 
 * 
 * String user = "vspynv5Dg", pass = "f54091f5-ff77-4c71-a14c-1c29ab93fd15", app
 * = "Trial1796", type = "product", id = "1"; String URL =
 * "http://scalr.api.appbase.io"+"/" + app + "/" + type + "/"+ id; String
 * baseURL="http://scalr.api.appbase.io"; System.out.println(URL);
 * 
 * String Auth = user + ":" + pass;
 * 
 * Elastic elastic = new Elastic(baseURL, app, user, pass); Param stream = new
 * Param("stream", "true"); ArrayList<Param> parameters = new
 * ArrayList<Param>(); parameters.add(stream); AsyncHttpClient client = new
 * DefaultAsyncHttpClient(); ListenableFuture<String> f = client
 * .prepareGet(elastic.getURL(type, id)) .addHeader("Authorization", "Basic " +
 * Base64.encode(Auth.getBytes()))
 * .addQueryParams(parameters).setRequestTimeout(6000) .execute(new
 * AsyncHandler<String>() { private ByteArrayOutputStream bytes = new
 * ByteArrayOutputStream();
 * 
 * public State onStatusReceived(HttpResponseStatus arg0) throws Exception { if
 * (arg0.getStatusCode() > 500) return State.ABORT;
 * 
 * return State.CONTINUE; }
 * 
 * public State onHeadersReceived(HttpResponseHeaders arg0) throws Exception {
 * System.out.println(arg0.getHeaders().toString()); return State.CONTINUE; }
 * 
 * public State onBodyPartReceived(HttpResponseBodyPart arg0) {
 * 
 * ByteArrayOutputStream localBytes = new ByteArrayOutputStream(); try {
 * bytes.write(arg0.getBodyPartBytes()); } catch (IOException e) {
 * e.printStackTrace(); } try { localBytes.write(arg0.getBodyPartBytes()); }
 * catch (IOException e) { e.printStackTrace(); }
 * System.out.println(localBytes); return State.CONTINUE; }
 * 
 * public String onCompleted() throws Exception {
 * 
 * return bytes.toString("UTF-8"); }
 * 
 * public void onThrowable(Throwable arg0) {
 * 
 * } });
 * 
 * String bodyResponse = null; try { bodyResponse = f.get(); } catch
 * (InterruptedException e) { e.printStackTrace(); } catch (ExecutionException
 * e) { e.printStackTrace(); } System.out.println(bodyResponse); try {
 * client.close(); } catch (IOException e) { e.printStackTrace(); }
 */
/*
AsyncHandler<String>() {
			private ByteArrayOutputStream bytes = new ByteArrayOutputStream();

			public State onStatusReceived(HttpResponseStatus arg0)
					throws Exception {
				if (arg0.getStatusCode() > 500)
					return State.ABORT;

				return State.CONTINUE;
			}

			public State onHeadersReceived(HttpResponseHeaders arg0)
					throws Exception {
				System.out.println(arg0.getHeaders().toString());
				return State.CONTINUE;
			}

			public State onBodyPartReceived(HttpResponseBodyPart arg0) {

				ByteArrayOutputStream localBytes = new ByteArrayOutputStream();
				try {
					bytes.write(arg0.getBodyPartBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					localBytes.write(arg0.getBodyPartBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(localBytes);
				return State.CONTINUE;
			}

			public String onCompleted() throws Exception {

				return bytes.toString("UTF-8");
			}

			public void onThrowable(Throwable arg0) {

			}
		}
*
*/
