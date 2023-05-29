package ac.kr.dankook.client.connect;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = "http://172.30.1.84:8080";
    private static Retrofit retrofit = null;

    public RetrofitClient() {
    }

    public static Retrofit getClient() {
        Gson gson = new GsonBuilder()
                .setLenient() // Lenient 모드 설정
                .create();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}