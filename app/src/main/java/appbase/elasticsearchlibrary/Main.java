package appbase.elasticsearchlibrary;

import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseHeaders;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Param;
import org.asynchttpclient.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Tirth Shah on 10-05-2016 for Appbase.io
 */
public class Main {

	public static void main(String[] args) {
		String user = "vspynv5Dg", pass = "f54091f5-ff77-4c71-a14c-1c29ab93fd15", app = "Trial1796/", type = "product/1";
		String URL = "http://scalr.api.appbase.io/" + app + type;
		System.out.println("working");

		String Auth = user + ":" + pass;
		String AuthHeader = Base64.encode(Auth.getBytes());
		System.out.println(URL);

		Param stream = new Param("stream", "true");
		ArrayList<Param> parameters = new ArrayList<Param>();
		parameters.add(stream);
		AsyncHttpClient client = new DefaultAsyncHttpClient();
		ListenableFuture<String> f = client.prepareGet(URL)
				.addHeader("Authorization", "Basic " + AuthHeader)
				.addQueryParams(parameters)
				.setRequestTimeout(0).execute(new AsyncHandler<String>() {
					private ByteArrayOutputStream bytes = new ByteArrayOutputStream();

					public State onStatusReceived(HttpResponseStatus arg0)
							throws Exception {
						// TODO Auto-generated method stub
						if(arg0.getStatusCode()>500)
							return State.ABORT;
						
						return State.CONTINUE;
					}

					public State onHeadersReceived(HttpResponseHeaders arg0)
							throws Exception {
						// TODO Auto-generated method stub
						System.out.println(arg0.getHeaders().toString());
						return State.CONTINUE;
					}

					public State onBodyPartReceived(HttpResponseBodyPart arg0)
							 {
						// TODO Auto-generated method stub

                        ByteArrayOutputStream localBytes = new ByteArrayOutputStream();
                        try {
							bytes.write(arg0.getBodyPartBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        try {
							localBytes.write(arg0.getBodyPartBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        System.out.println(localBytes);
						return State.CONTINUE;
					}

					public String onCompleted() throws Exception {
						// TODO Auto-generated method stub
						
						return bytes.toString("UTF-8");
					}

					public void onThrowable(Throwable arg0) {
						// TODO Auto-generated method stub

					}
				});

		String bodyResponse = null;
		try {
			bodyResponse = f.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(bodyResponse);

	}
}
