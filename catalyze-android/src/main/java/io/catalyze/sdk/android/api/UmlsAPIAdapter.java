package io.catalyze.sdk.android.api;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class UmlsAPIAdapter {

    private static UmlsAPI api = new RestAdapter.Builder()
            .setEndpoint("https://umls.catalyze.io/v1/umls")
            .setRequestInterceptor(new UmlsInterceptor())
            .setClient(new OkClient(new OkHttpClient()))
            .build().create(UmlsAPI.class);

    private UmlsAPIAdapter() { }

    public static UmlsAPI getApi() {
        return api;
    }
}