package rnd.plani.co.kr.dadfarm.Setting;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import rnd.plani.co.kr.dadfarm.Certification.FriendsData;
import rnd.plani.co.kr.dadfarm.Data.TermsOrPrivacy;

/**
 * Created by toto9114 on 2016-10-20.
 */

public interface NetworkService {
    @Headers("Content-Type: application/json")
    @GET("terms/")
    Call<TermsOrPrivacy> getTerms(@Header("Authorization") String authorization);

    @Headers({"Content-Type: application/json"})
    @PUT("friends/")
    Call<ResponseBody> uploadFriends(@Header("Authorization") String authorization, @Body FriendsData friends);

}
