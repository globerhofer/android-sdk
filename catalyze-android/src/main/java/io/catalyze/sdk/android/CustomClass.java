package io.catalyze.sdk.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Response;

/**
 * Created by mvolkhart on 8/26/13.
 */
public class CustomClass extends CatalyzeObject {

	// URL CONSTANTS
	private static final String CUSTOM_CLASS_URL = Catalyze.BASE_URL + "classes";

	private static final String CONTENT = "content";
	private static final String PHI = "phi";
	private static final String PARENT_ID = "parentId";
	private static final String SCHEMA = "schema";
	private static final String ID = "id";
	private static final String REF = "ref";

	private String className;
	private CatalyzeUser user;

	public CustomClass() {
		this(new JSONObject());
		try {
			mJson.put(CONTENT, new JSONObject());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public CustomClass(JSONObject json) {
		super(json);
	}

	private CustomClass(CatalyzeUser user) {
		this(new JSONObject());
		try {
			mJson.put(CONTENT, new JSONObject());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.user = user;
	}

	public void setName(String className) {
		this.className = className;
	}

	public String getName() {
		return className;
	}

	public Object getContent(String key) {
		Object value = mJson.opt(key);
		if (value instanceof JSONArray) {
			return handleJSONArray((JSONArray) value);
		} else if (value instanceof JSONObject) {
			return handleJSONObject((JSONObject) value);
		}
		return value;
	}

	public CustomClass putContent(String key, Object value) {
		try {
			mJson.getJSONObject(CONTENT).put(key, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public boolean isPHI() {
		return mJson.optBoolean(PHI, true);
	}

	public CustomClass setPHI(boolean phi) {
		try {
			mJson.put(PHI, phi);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public String getParentId() {
		String id = "";
		try {
			id = mJson.get(PARENT_ID).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return id;
	}

	CustomClass setParentId(String id) {
		try {
			mJson.put(PARENT_ID, id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public String getId() {
		String id = "";
		try {
			id = mJson.getString(ID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return id;
	}

	CustomClass setId(String id) {
		try {
			mJson.put(ID, id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public boolean isSchema() {
		return mJson.optBoolean(SCHEMA, false);
	}

	/**
	 * This method generates a custom class instance with information for the
	 * CatalyzeUser user ***Must be passed an authenticated user to generate the
	 * custom class instance
	 * 
	 * @param user
	 * @return CustomClass instance for CatalyzeUser user
	 */
	public static CustomClass getInstance(String className, CatalyzeUser user) {
		CustomClass cc = new CustomClass(user);
		cc.setName(className);
		return cc;
	}

	/******
	 * Perform= API call to get a custom class schema
	 * 
	 * @param callbackHandler
	 */
	public void getSchema(CatalyzeListener<CustomClass> callbackHandler) {
		responseListener = createListenerReturnNewInstance(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName()), null,
				responseListener, errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.get(user.catalyze.getContext());
	}

	/**
	 * Perform API call to add an entry to a custom class
	 * 
	 * @param callbackHandler
	 */
	public void createEntry(CatalyzeListener<CustomClass> callbackHandler) {
		responseListener = createListenerReturnUpdatedInstance(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request;
		try {
			request = new CatalyzeRequest<JSONObject>(CUSTOM_CLASS_URL + "/" + getName(), mJson.getJSONObject(CONTENT),
					responseListener, errorListener);
			request.setHeaders(user.getAuthorizedHeaders());
			request.post(user.catalyze.getContext());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Perform API call to retrieve a custom class entry.
	 * 
	 * @param entryId
	 * @param callbackHandler
	 */
	public void getEntry(String entryId, CatalyzeListener<CustomClass> callbackHandler) {
		responseListener = createListenerReturnUpdatedInstance(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), entryId),
				null, responseListener, errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.get(user.catalyze.getContext());
	}

	/**
	 * Perform api call to update an entry in a custom class, will write data to
	 * server as it is passed, so to do partial update first do a get.
	 * 
	 * @param entryId
	 *            ID of entry to update
	 * @param callbackHandler
	 */
	public void updateEntry(CatalyzeListener<CustomClass> callbackHandler) {
		responseListener = createListenerReturnUpdatedInstance(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request;
		try {
			request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), getId()),
					mJson.getJSONObject(CONTENT), responseListener, errorListener);
			request.setHeaders(user.getAuthorizedHeaders());
			request.put(user.catalyze.getContext());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Perform api call to delete an entry within a custom class
	 * 
	 * @param entryId
	 *            ID of entry to delete
	 * @param callbackHandler
	 */
	public void deleteEntry(CatalyzeListener<CustomClass> callbackHandler) {
		responseListener = createListenerReturnUpdatedInstance(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), getId()),
				null, responseListener, errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.delete(user.catalyze.getContext());
	}

	/**
	 * Perform api call to retrieve array of custom class objects
	 * 
	 * @param entryId
	 *            ID of entry to delete
	 * @param refName
	 * @param callbackHandler
	 */
	public void getArray(String refName, CatalyzeListener<CustomClass[]> callbackHandler) {
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(getCustomClassUrl(getName(), getId(), REF,
				refName), null, createArrayListener(callbackHandler), errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.get(user.catalyze.getContext());
	}

	/**
	 * Add a new reference to the array refName
	 * 
	 * @param entryId
	 * @param refName
	 * @param callbackHandler
	 */
	public void addReferenceArray(String refName, String refID, CatalyzeListener<CustomClass> callbackHandler) {
		errorListener = createErrorListener(callbackHandler);
		JSONArray newArrayEntry = new JSONArray();
		JSONObject json = new JSONObject();
		try {
			json.put("type", "__ref");
			json.put("class", refName);
			json.put("id", refID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		newArrayEntry.put(json);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(newArrayEntry, getCustomClassUrl(
				getName(), getId(), REF, refName), createListenerReturnNewInstance(callbackHandler), errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.put(user.catalyze.getContext());
	}

	/**
	 * Performs api call to retrieve the referenced a referenced array instance.
	 * The entire custom class instance referenced will be returned to the
	 * callback handler
	 * 
	 * @param entryId
	 * @param refName
	 * @param refId
	 * @param callbackHandler
	 */
	public void getArrayRef(String refName, String refId, CatalyzeListener<CustomClass> callbackHandler) {
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), getId(),
				REF, refName, refId), null, createListenerReturnNewInstance(callbackHandler), errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.get(user.catalyze.getContext());
	}

	/**
	 * 
	 * @param entryId
	 * @param refName
	 * @param refId
	 * @param callbackHandler
	 */
	public void deleteArrayRef(String refName, String refId, CatalyzeListener<CustomClass> callbackHandler) {
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), getId(),
				REF, refName, refId), null, createListenerReturnNewInstance(callbackHandler), errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.delete(user.catalyze.getContext());
	}

	/**
	 * Get custom class url with arguments appended to base url with "/"
	 * separating each
	 * 
	 * @param args
	 * @return
	 */
	private String getCustomClassUrl(String... args) {
		String url = CUSTOM_CLASS_URL;
		for (String s : args) {
			url += "/" + s;
		}
		return url;
	}

	/**
	 * Volley response handler for custom class, takes the JSONObject response
	 * and sets the CustomClasses json to this then returns it to user callback
	 * handler
	 * 
	 * @param callbackHandler
	 * @param cc
	 * @return
	 */
	private Response.Listener<JSONObject> createListenerReturnNewInstance(
			final CatalyzeListener<CustomClass> callbackHandler) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CustomClass cc = new CustomClass(response);
				cc.user = CustomClass.this.user;
				cc.className = CustomClass.this.className;
				callbackHandler.onSuccess(cc);
			}
		};
	}

	/**
	 * Volley response handler for custom class, takes the JSONObject response
	 * and sets the CustomClasses json to this then returns it to user callback
	 * handler
	 * 
	 * @param callbackHandler
	 * @param cc
	 * @return
	 */
	private Response.Listener<JSONObject> createListenerReturnUpdatedInstance(
			final CatalyzeListener<CustomClass> callbackHandler) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CustomClass.this.mJson = response;
				callbackHandler.onSuccess(CustomClass.this);
			}
		};
	}

	/**
	 * Handle volley response when response will be a JSONArray
	 * 
	 * @param callbackHandler
	 * @param cc
	 * @return
	 */
	private static Response.Listener<JSONArray> createArrayListener(
			final CatalyzeListener<CustomClass[]> callbackHandler) {
		return new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				CustomClass[] ccArray = new CustomClass[response.length()];
				for (int i = 0; i < response.length(); i++) {
					try {
						ccArray[i] = new CustomClass();
						ccArray[i].mJson = response.getJSONObject(i);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				callbackHandler.onSuccess(ccArray);
			}
		};
	}

}
