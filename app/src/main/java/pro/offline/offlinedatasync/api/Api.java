package pro.offline.offlinedatasync.api;
import pro.offline.offlinedatasync.response.SignUpResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("signup.php")
    Call<SignUpResponse> createUser(@Field("name") String name,
                                    @Field("email") String email,
                                    @Field("phone") String phone,
                                    @Field("company_name") String company_name,
                                    @Field("designation") String designation,
                                    @Field("password") String password);
}
