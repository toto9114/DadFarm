package rnd.plani.co.kr.dadfarm.Manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.digits.sdk.android.models.DigitsUser;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rnd.plani.co.kr.dadfarm.Data.AuthData;
import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.ProductListResultData;
import rnd.plani.co.kr.dadfarm.Data.ProfileData;
import rnd.plani.co.kr.dadfarm.Data.TermsOrPrivacy;
import rnd.plani.co.kr.dadfarm.MyApplication;

/**
 * Created by dongja94 on 2016-02-05.
 */
public class NetworkManager {
    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    OkHttpClient mClient;
    private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;

    private NetworkManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Context context = MyApplication.getContext();
        File cachefile = new File(context.getExternalCacheDir(), "mycache");
        if (!cachefile.exists()) {
            cachefile.mkdirs();
        }
        Cache cache = new Cache(cachefile, MAX_CACHE_SIZE);
        builder.cache(cache);
        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));
//        disableCertificateValidation(context, builder);

        mClient = builder.build();
    }

    private static final String STAGE_BASE_URL = "http://restapi-stage.pafarm.kr:9100/api";
    private static final String PRODUCTION_BASE_URL = "https://restapi.pafarm.kr/api";

    private static final String PAFARM_CLIENT_ID = "H3ok6aSEZRrMsRKvoWpBWkgjE9dnGNUCXuGLWS3l";
    private static final String PAFARM_CLIENT_SECRET = "absQR2PtJ2A3b2qe5vzSuRRezkkr2a2bv9aYNPeVCKm98kiXXkxYLYh1Ejel4hdBSfLApI3305KH1k4mbXLi2YA59WNI0l5vuDtBLYmyolXhk9NeU2aOXcQ7fPXbDbMB";

    public Request getTerms(Context context, OnResultListener<TermsOrPrivacy> listener) {

        String url = STAGE_BASE_URL + "/terms/";

        final CallbackObject<TermsOrPrivacy> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "bearer " + PropertyManager.getInstance().getPafarmToken())
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                TermsOrPrivacy data = gson.fromJson(response.body().string(), TermsOrPrivacy.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request getPrivacy(Context context, OnResultListener<TermsOrPrivacy> listener) {

        String url = STAGE_BASE_URL + "/privacy/";

        final CallbackObject<TermsOrPrivacy> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "bearer " + PropertyManager.getInstance().getPafarmToken())
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                TermsOrPrivacy data = gson.fromJson(response.body().string(), TermsOrPrivacy.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request convertToken(Context context, String serviceProvider,
                                String credentials, String token, String userId, OnResultListener<AuthData> listener) {

        String url = STAGE_BASE_URL + "/o/convert-token/";
//        String url = PRODUCTION_BASE_URL+"/o/convert_token";
//
        RequestBody body = null;

        body = new FormBody.Builder()
                .add("grant_type", "convert_token")
                .add("client_id", PAFARM_CLIENT_ID)
                .add("client_secret", PAFARM_CLIENT_SECRET)
                .add("service_provider", serviceProvider)
                .add("verify_credentials", credentials)
                .add("token", token)
                .add("user_id", userId).build();


        final CallbackObject<AuthData> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "bearer HdV0aQoeC5zDUbtQKLJ9cNOpu46h9K")
                .post(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                AuthData data = gson.fromJson(response.body().string(), AuthData.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String REFRESH_TOKEN_URL = "http://restapi-stage.pafarm.kr:9100/api/o/token/";

    public Request refreshToken(Context context, String refreshToken, OnResultListener<AuthData> listener) {

        String url = REFRESH_TOKEN_URL;
//
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "refresh_token")
                .add("client_id", PAFARM_CLIENT_ID)
                .add("client_secret", PAFARM_CLIENT_SECRET)
                .add("refresh_token", refreshToken)
                .build();


        final CallbackObject<AuthData> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "bearer HdV0aQoeC5zDUbtQKLJ9cNOpu46h9K")
                .post(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                AuthData data = gson.fromJson(response.body().string(), AuthData.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String REVOKE_TOKEN_URL = "http://restapi-stage.pafarm.kr:9100/api/o/revoke-token/";

    public Request revokeToken(Context context, String token, OnResultListener<Boolean> listener) {

        String url = REVOKE_TOKEN_URL;
//
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "refresh_token")
                .add("client_id", PAFARM_CLIENT_ID)
                .add("client_secret", PAFARM_CLIENT_SECRET)
                .add("token", token)
                .build();


        final CallbackObject<Boolean> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "bearer HdV0aQoeC5zDUbtQKLJ9cNOpu46h9K")
                .post(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                if (response.code() == 200) {
                    callbackObject.result = true;
                } else {
                    callbackObject.result = false;
                }
//                AuthData data = gson.fromJson(response.body().string(), AuthData.class);

                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request uploadFriends(Context context, List<DigitsUser> friends, OnResultListener<Boolean> listener) {

        String url = STAGE_BASE_URL + "/friends/";
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        for (DigitsUser friend : friends) {
            array.put(friend.idStr);
        }
        try {
            jsonObject.put("friends",array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = array.toString();
        RequestBody body = new FormBody.Builder()
                .add("friends", String.valueOf(jsonObject))
                .build();
        String bodyStr = body.toString();
        final CallbackObject<Boolean> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "bearer " + PropertyManager.getInstance().getPafarmToken())
                .put(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
//                PersonalData data = gson.fromJson(response.body().string(), PersonalData.class);
                if (response.code() == 200) {
                    callbackObject.result = true;
                } else {
                    callbackObject.result = false;
                }
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request getUserInfo(Context context, OnResultListener<PersonalData> listener) {

        String url = STAGE_BASE_URL + "/me/";

        final CallbackObject<PersonalData> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "bearer " + PropertyManager.getInstance().getPafarmToken())
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                PersonalData data = gson.fromJson(response.body().string(), PersonalData.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request updateUserInfo(Context context, long id, String firstName, String lastName, OnResultListener<PersonalData> listener) {

        String url = STAGE_BASE_URL + "/me/";
//
        RequestBody body = new FormBody.Builder()
                .add("id", String.valueOf(id))
                .add("first_name", firstName)
                .add("last_name", lastName)
                .build();


        final CallbackObject<PersonalData> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "bearer " + PropertyManager.getInstance().getPafarmToken())
                .put(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                PersonalData data = gson.fromJson(response.body().string(), PersonalData.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request deleteUserInfo(Context context, OnResultListener<Boolean> listener) {

        String url = STAGE_BASE_URL + "/me/";

        final CallbackObject<Boolean> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "bearer " + PropertyManager.getInstance().getPafarmToken())
                .delete()
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                boolean isSuccess = false;
                if (response.code() == 204) {
                    isSuccess = true;
                } else {
                    isSuccess = false;
                }
                callbackObject.result = isSuccess;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request getProfilImage(Context context, OnResultListener<PersonalData> listener) {

        String url = STAGE_BASE_URL + "/users/" + PropertyManager.getInstance().getUserId() + "/profile.jpg";

        final CallbackObject<PersonalData> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .addHeader("Authorization", "bearer " + PropertyManager.getInstance().getPafarmToken())
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                PersonalData data = gson.fromJson(response.body().string(), PersonalData.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public Request setMyProfileImage(Context context, File file, long profileId, final OnResultListener<ProfileData> listener) {

        String url = STAGE_BASE_URL + "/profiles" + "/" + profileId + "/";

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file))
                .build();

        final CallbackObject<ProfileData> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .put(body)
                .tag(context)
                .addHeader("Authorization", "bearer " + PropertyManager.getInstance().getPafarmToken())
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                ProfileData data = gson.fromJson(response.body().string(), ProfileData.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }


    public Request getProductList(Context context, OnResultListener<ProductListResultData> listener) {

        String url = STAGE_BASE_URL + "/products-of-friends/?page=%d";
        url = String.format(url, 1);

        final CallbackObject<ProductListResultData> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "bearer " + PropertyManager.getInstance().getPafarmToken())
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String text = response.body().string();
                ProductListResultData data = gson.fromJson(text, ProductListResultData.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }
//    static void disableCertificateValidation(Context context, OkHttpClient.Builder builder) {
//
//        try {
//            CertificateFactory cf = CertificateFactory.getInstance("X.509");
//            InputStream caInput = context.getResources().openRawResource(R.raw.ca);
//            Certificate ca;
//            try {
//                ca = cf.generateCertificate(caInput);
//                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
//            } finally {
//                caInput.close();
//            }
//            String keyStoreType = KeyStore.getDefaultType();
//            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
//            keyStore.load(null, null);
//            keyStore.setCertificateEntry("ca", ca);
//
//            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//            tmf.init(keyStore);
//
//            SSLContext sc = SSLContext.getInstance("TLS");
//            sc.init(null, tmf.getTrustManagers(), null);
//            HostnameVerifier hv = new HostnameVerifier() {
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            };
//            sc.init(null, tmf.getTrustManagers(), null);
//            builder.sslSocketFactory(sc.getSocketFactory());
//            builder.hostnameVerifier(hv);
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public void cancelAll() {
        mClient.dispatcher().cancelAll();
    }

    public void cancelTag(Object tag) {
        Dispatcher dispatcher = mClient.dispatcher();
        List<Call> calls = dispatcher.queuedCalls();
        for (Call call : calls) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
        calls = dispatcher.runningCalls();
        for (Call call : calls) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
    }

    public interface OnResultListener<T> {
        public void onSuccess(Request request, T result);

        public void onFailure(Request request, int code, Throwable cause);
    }

    private static final int MESSAGE_SUCCESS = 0;
    private static final int MESSAGE_FAILURE = 1;

    static class NetworkHandler extends Handler {
        public NetworkHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CallbackObject object = (CallbackObject) msg.obj;
            Request request = object.request;
            OnResultListener listener = object.listener;
            switch (msg.what) {
                case MESSAGE_SUCCESS:
                    listener.onSuccess(request, object.result);
                    break;
                case MESSAGE_FAILURE:
                    listener.onFailure(request, -1, object.exception);
                    break;
            }
        }
    }

    Handler mHandler = new NetworkHandler(Looper.getMainLooper());

    static class CallbackObject<T> {
        Request request;
        T result;
        IOException exception;
        OnResultListener<T> listener;
    }

    //    public Request sendPhoneNumber(Context context,String phoneNum,
//                                  final OnResultListener<> listener) {       //답변 받아오기 네트워크 통신
//        String url = String.format(BASE_URL + "/question/");
//
//        final CallbackObject<> callbackObject = new CallbackObject<>();
//
//
//
//        RequestBody body = new FormBody.Builder()
//                .add();
//
//        Request request = new Request.Builder().url(url)
//                .tag(context)
//                .post(body)
//                .build();
//
//        callbackObject.request = request;
//        callbackObject.listener = listener;
//        mClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                callbackObject.exception = e;
//                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
//                mHandler.sendMessage(msg);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Gson gson = new Gson();
//                String text = response.body().string();
//                 data = gson.fromJson(text, );
//                callbackObject.result = data;
//                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
//                mHandler.sendMessage(msg);
//            }
//        });
//
//        return request;
//    }
    public void cancelAll(Object tag) {

    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


}
