package ac.kr.dankook.client.connect;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface apiService {
    @FormUrlEncoded
    @POST("/users/register")
    Call<String> register(
            @Field("name") String name,
            @Field("sex") String sex,
            @Field("age") int age,
            @Field("phone_num") String phone_num,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/users/login")
    Call<String> login(
            @Field("name") String name,
            @Field("password") String password
    );

    @GET("/users/dashboard")
    Call<String> getDashboard(@Header("Cookie") String sessionID);

    @GET("/users/mbti")
    Call<String> getMbti(@Header("name") String name);

    @FormUrlEncoded
    @POST("/users/mbti")
    Call<String> setMbti(
            @Field("name") String name,
            @Field("mbti") String mbti
    );


}
