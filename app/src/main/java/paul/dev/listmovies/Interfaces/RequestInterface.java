package paul.dev.listmovies.Interfaces;


import paul.dev.listmovies.Models.Movie;
import paul.dev.listmovies.utils.JSONResponse;
import paul.dev.listmovies.utils.JSONResponseVideos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by paulotrujillo on 5/1/18.
 */

public interface RequestInterface {

    @GET("3/movie/{opcion}")
    Call<JSONResponse> getJSON(@Path("opcion") String opcion, @Query("api_key") String apiKey,@Query("page") int page);

    @GET("3/movie/{idMovie}")
    Call<Movie> getDetailMovie(@Path("idMovie") String opcion, @Query("api_key") String apiKey);

    @GET("3/movie/{idMovie}/videos")
    Call<JSONResponseVideos> getTrailer(@Path("idMovie") String opcion, @Query("api_key") String apiKey);







}
