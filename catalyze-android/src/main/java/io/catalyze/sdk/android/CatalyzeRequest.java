package io.catalyze.sdk.android;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

/**
 * This class handles all Catalyze JSON network requests. All calls are here are
 * made asynchronously using the Android Volley library. 
 * 
 * @param <T>
 */
public class CatalyzeRequest<T> extends JsonRequest<T> {

	public static final String BASE_PATH = "https://api.catalyze.io/v1";
	private Map<String, String> mHeaders = new HashMap<String, String>();
	private static RequestQueue mRequestQueue;
	private int mMethod;

	@SuppressWarnings("deprecation")
	public CatalyzeRequest(String url, JSONObject jsonRequest, Response.Listener<T> listener,
			Response.ErrorListener errorListener) {
		super(url, jsonRequest != null ? jsonRequest.toString() : null, listener, errorListener);
	}

	@SuppressWarnings("deprecation")
	public CatalyzeRequest(JSONArray jsonRequest, String url, Response.Listener<T> listener,
			Response.ErrorListener errorListener) {
		super(url, jsonRequest != null ? jsonRequest.toString() : null, listener, errorListener);
	}

	@Override
	public Map<String, String> getHeaders() {
		return mHeaders;
	}

	@Override
	public int getMethod() {
		return mMethod;
	}

	/**
	 * Set HTTP Request headers
	 * 
	 * @param customHeaders
	 */
	public void setHeaders(Map<String, String> customHeaders) {
		mHeaders = customHeaders;
	}

	/***
	 * Add this request to Volley queue as a post request
	 * 
	 * @param context
	 */
	public void post(Context context) {
		mMethod = Request.Method.POST;
		mRequestQueue = getRequestQueue(context);
		mRequestQueue.add(this);
	}

	/***
	 * Add this request to Volley queue as a get request
	 * 
	 * @param context
	 */
	public void get(Context context) {
		mMethod = Request.Method.GET;
		mRequestQueue = getRequestQueue(context);
		mRequestQueue.add(this);
	}

	/***
	 * Add this request to Volley queue as a put request
	 * 
	 * @param context
	 */
	public void put(Context context) {
		mMethod = Request.Method.PUT;
		mRequestQueue = getRequestQueue(context);
		mRequestQueue.add(this);
	}

	/***
	 * Add this request to Volley queue as a delete request
	 * 
	 * @param context
	 */
	public void delete(Context context) {
		mMethod = Request.Method.DELETE;
		mRequestQueue = getRequestQueue(context);
		mRequestQueue.add(this);
	}

	/**
	 * Return the Volley request queue or instantiate one if needed
	 * 
	 * @param context
	 * @return
	 */
	protected static RequestQueue getRequestQueue(Context context) {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(context);
			return mRequestQueue;
		} else
			return mRequestQueue;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

			if (jsonString.charAt(0) == '{') {
				return (Response<T>) Response.success(new JSONObject(jsonString),
						HttpHeaderParser.parseCacheHeaders(response));
			} else {
				return (Response<T>) Response.success(new JSONArray(jsonString),
						HttpHeaderParser.parseCacheHeaders(response));
			}
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}
}
