package appbase.elasticsearchlibrary;

import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.HttpResponseHeaders;
import org.asynchttpclient.HttpResponseStatus;


/**
 * Created by Tirth Shah on 14-05-2016.
 */

public abstract class ElasticHandler implements AsyncHandler<String> {

    public State onStatusReceived(HttpResponseStatus arg0)
            throws Exception {
        if(arg0.getStatusCode()>500)
            return State.ABORT;

        return State.CONTINUE;
    }

    public State onHeadersReceived(HttpResponseHeaders arg0)
            throws Exception {
        System.out.println(arg0.getHeaders().toString());
        return State.CONTINUE;
    }

	/*public State onBodyPartReceived(HttpResponseBodyPart arg0)
			 {

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
	}*/

    public String onCompleted() throws Exception {

        return null;
    }

    public void onThrowable(Throwable arg0) {

    }

}