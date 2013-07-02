/*
 * Copyright (C) 2013 catalyze.io, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.catalyze.android.library.external;

import com.google.common.base.Strings;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by marius on 6/18/13.
 */
public class CatalyzeObject {

    protected static final String sBasePath = "https://api.catalyze.io/v1";

    // API Map Keys
    protected static final String sAppIdKey = "app_key";

    protected static final String sSourceKey = "source";

    protected static final String sGenderKey = "gender";

    protected static final String sPhoneKey = "phone";

    protected static final String sAgeKey = "age";

    protected static final String sBirthDateKey = "birth_date";

    protected static final String sZipCodeKey = "zip_code";

    protected static final String sStateKey = "state";

    protected static final String sCityKey = "city";

    protected static final String sStreetKey = "street";

    protected static final String sEmailKey = "email";

    protected static final String sFirstNameKey = "first_name";

    protected static final String sLastNameKey = "last_name";

    protected static final String sPersonIdKey = "person_id";

    protected static final String sTransactionTypeKey = "transaction_type";

    protected static final String sDateCommittedKey = "date_committed";

    protected static final String sPasswordKey = "password";

    protected static final String sUsernameKey = "username";

    protected static final String sUserIdKey = "user_id";

    protected static final String sQuestionIdKey = "question_id";

    protected static final String sAnswerKey = "answer";

    protected static final String sResponseTypeKey = "response_type";

    protected static final String sQuestionTextKey = "content";

    protected static final String sTransactionIdKey = "transaction_id";

    static final String sSessionId = "session_token";

    static final String sSessionHeader = "X-Session";

    static String sSessionToken = "";

    private static String sAppId = "";

    protected Map<String, Object> mContent;

    protected CatalyzeObject() {
        mContent = new HashMap<String, Object>();
        mContent.put(sAppIdKey, getAppId());
    }

    static String getAppId() {
        sAppId = Strings.emptyToNull(sAppId);
        return checkNotNull(sAppId,
                "Specify the appId by calling " + CatalyzeObject.class.getSimpleName()
                        + ".setAppId()");
    }

    public static void setAppId(String appId) {
        sAppId = appId;
    }

    protected Request create(RequestQueue queue, Response.ErrorListener errorListener,
            String path) {

        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.POST,
                path, new JSONObject(mContent), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                sSessionToken = (String) object.remove(sSessionId);
            }
        }, errorListener);
        queue.add(request);
        return request;
    }

    protected Request retrieve(RequestQueue queue, Response.ErrorListener errorListener,
            String path) {
        final CatalyzeObject holder = this;
        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.GET,
                path, new JSONObject(mContent), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                sSessionToken = (String) object.remove(sSessionId);
                holder.inflateFromJson(object);
            }
        }, errorListener);
        queue.add(request);
        return request;
    }

    protected Request update(RequestQueue queue, Response.ErrorListener errorListener,
            String path) {
        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.PUT,
                path, new JSONObject(mContent), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                sSessionToken = (String) object.remove(sSessionId);
            }
        }, errorListener);
        queue.add(request);
        return request;
    }

    protected Request delete(RequestQueue queue, Response.ErrorListener errorListener,
            String path) {
        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.DELETE,
                path, new JSONObject(mContent), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                sSessionToken = (String) object.remove(sSessionId);
            }
        }, errorListener);
        queue.add(request);
        return request;
    }

    protected void inflateFromJson(JSONObject json) {
        Iterator<String> iter = json.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                mContent.put(key, json.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public int getInt(String key) {
        return (Integer) get(key);
    }

    public boolean getBoolean(String key) {
        return (Boolean) get(key);
    }

    public double getDouble(String key) {
        return (Double) get(key);
    }

    public String getString(String key) {
        return (String) get(key);
    }

    public long getLong(String key) {
        return (Long) get(key);
    }

    public Object get(String key) {
        return mContent.get(key);
    }

    public CatalyzeObject put(String k, Object v) {
        mContent.put(k, v);
        return this;
    }

    @Override
    public String toString() {
        return new JSONObject(mContent).toString();
    }

    public String toString(int indentSpaces) throws JSONException {
        return new JSONObject(mContent).toString(indentSpaces);
    }
}