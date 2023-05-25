package ac.kr.dankook.client.connect;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
}
