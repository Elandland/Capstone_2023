package ac.kr.dankook.client.connect;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface apiService {
    @POST("/users/register")
    Call<String> register(@Body UserDto dto);
}
