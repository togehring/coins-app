package de.tgehring.coins.app.utils;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.net.Uri;

public class NetworkUtility {

	private HttpClient httpClient = null;

	public NetworkUtility() {

	}

	private HttpClient httpClient() {
		if (httpClient == null) {
			httpClient = new DefaultHttpClient();
		}		
		return httpClient;
	}

	private URI convertUri(Uri uri) throws URISyntaxException {
		URI result;
		result = new URI(uri.getScheme(),
				uri.getUserInfo(),
				uri.getHost(),
				uri.getPort(),
				uri.getPath(),
				uri.getQuery(),
				uri.getFragment());		
		return result;
	}

	public String get(Uri source) {
		String result = "";
		try {
			HttpGet get = new HttpGet(convertUri(source));
			HttpParams httpParameters = new BasicHttpParams();
			int timeoutConnection = 30;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		    HttpResponse response = httpClient().execute(get);
		    HttpEntity entity = response.getEntity();
		    result = convertResponse(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String post(Uri source, JSONObject json) {
		String result = "";
		try {
			HttpPost post = new HttpPost(convertUri(source));

			StringEntity se = new StringEntity(json.toString());
            se.setContentType("application/json;charset=UTF-8");

            post.setEntity(se);

		    HttpResponse response = httpClient().execute(post);
		    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				HttpEntity entity = response.getEntity();
				result = convertResponse(entity);
			}
		} catch (Exception e) {

		}
		return result;
	}

	public String put(Uri source, JSONObject json) {
		String result = "";
		try {
			HttpPut put = new HttpPut(convertUri(source));

			StringEntity se = new StringEntity(json.toString());
            se.setContentType("application/json;charset=UTF-8");

            put.setEntity(se);

		    HttpResponse response = httpClient().execute(put);
		    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				HttpEntity entity = response.getEntity();
				result = convertResponse(entity);
			}
		} catch (Exception e) {

		}
		return result;
	}

	public String delete(Uri source) {
		String result = "";
		try {
			HttpDelete delete = new HttpDelete(convertUri(source));

		    HttpResponse response = httpClient().execute(delete);
		    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				HttpEntity entity = response.getEntity();
				result = convertResponse(entity);
			}
		} catch (Exception e) {

		}
		return result;
	}

	// Konvertiert die HttpEntity zu einem String
	private String convertResponse(HttpEntity entity) throws Exception {
		String result = "";
		InputStream is = entity.getContent();
		long clen = entity.getContentLength();
		String encoding = "iso-8859-1";
		if (entity.getContentEncoding()!=null) {

			encoding = entity.getContentEncoding().getValue();

		}
		if (clen>0) {

		  byte[] buffer = new byte[(int)clen];
		  is.read(buffer);
		  result = new String(buffer);

		} else {

			byte[] buffer = new byte[10*1024];
			long read = 0; 
			do {				
				read = is.read(buffer);
				if (read>0) {
					result += new String(buffer,0,(int)read,encoding);
				}
			} while (read>=0);
		}
		return result;
	}

}
