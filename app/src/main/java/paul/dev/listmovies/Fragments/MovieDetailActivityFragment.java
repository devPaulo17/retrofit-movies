package paul.dev.listmovies.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import paul.dev.listmovies.BuildConfig;
import paul.dev.listmovies.DataDB.MoviesDbHelper;
import paul.dev.listmovies.Interfaces.RequestInterface;
import paul.dev.listmovies.Models.Movie;
import paul.dev.listmovies.R;
import paul.dev.listmovies.UI.VideoActivity;
import paul.dev.listmovies.utils.Alerts;
import paul.dev.listmovies.utils.CheckConexion;
import paul.dev.listmovies.utils.JSONResponseVideos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    private static final String ARG_MOVIE_ID = "movieId";


    Alerts alert;


    CheckConexion connected;
    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mOverview;
    private TextView mRelease;
    private TextView mTitleOriginal;
    private TextView mScore;
    private TextView mVoteCount;
    private TextView mLanguaje;
    private TextView mWeb;
    private String idMovie;
    private Button btnvideo;
    private MoviesDbHelper mMoviesDbHelper;
    Retrofit retrofit;

    private String nametrailer;
    private String siteTrailer;
    private String typeTrailer;
    private String linktrailer;


    public MovieDetailActivityFragment() {
        // Required empty public constructor
    }

    public static MovieDetailActivityFragment newInstance(String movieID) {
        MovieDetailActivityFragment fragment = new MovieDetailActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MOVIE_ID, movieID);
        fragment.setArguments(args);
        return fragment;
    }



    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        btnvideo = (Button) getActivity().findViewById(R.id.view_video);





        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mOverview = (TextView) root.findViewById(R.id.txt_overview);
        mRelease = (TextView) root.findViewById(R.id.txt_release);
        mTitleOriginal = (TextView) root.findViewById(R.id.txt_title_original);
        mScore = (TextView) root.findViewById(R.id.txt_score);
        mVoteCount = (TextView) root.findViewById(R.id.txt_vote_count);
        mWeb = (TextView) root.findViewById(R.id.txt_web);
        mLanguaje = (TextView) root.findViewById(R.id.txt_languaje);


        connected = new CheckConexion();
        alert = new Alerts(getContext());
        idMovie = getActivity().getIntent().getStringExtra("idMovie");

        btnvideo = (Button) root.findViewById(R.id.view_video);



        btnvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext() , VideoActivity.class);
                intent.putExtra("name",nametrailer);
                intent.putExtra("site" ,siteTrailer);
                intent.putExtra("type",typeTrailer);
                intent.putExtra("link",linktrailer);
                startActivity(intent);
            }
        });




        // Instancia de helper
        mMoviesDbHelper = new MoviesDbHelper(getActivity());


        // Carga de datos
        if((connected.isOnline(getContext()) == false))
            loadMovies();
        else
            loadJSON();

        return root;
    }

    private void openVideo(View view){

    }





    private void loadMovies() {
        loadMovie();
    }



    private void loadJSON(){


         retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<Movie> call = request.getDetailMovie(idMovie,BuildConfig.API_KEY);
        call.enqueue(new Callback<Movie>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie jsonResponse = response.body();
                setData(jsonResponse);
                loadTrailerMovie();

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }


        });
    }

    private void loadTrailerMovie(){



         retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponseVideos> call = request.getTrailer(idMovie,BuildConfig.API_KEY);
        call.enqueue(new Callback<JSONResponseVideos>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<JSONResponseVideos> call, Response<JSONResponseVideos> response) {

                JSONResponseVideos jsonResponse = response.body();


                nametrailer = jsonResponse.getAndroid()[0].getName();
                siteTrailer = jsonResponse.getAndroid()[0].getSite();
                typeTrailer = jsonResponse.getAndroid()[0].getType();
                linktrailer = jsonResponse.getAndroid()[0].getKey();



            }

            @Override
            public void onFailure(Call<JSONResponseVideos> call, Throwable t) {
                Log.w("Error",t.getMessage());
            }
        });


    }


    public void setData(Movie jsonResponse){

        mCollapsingView.setTitle(jsonResponse.gettitle());
        mOverview.setText(jsonResponse.getoverview());
        mRelease.setText(jsonResponse.getRelease_date());
        mTitleOriginal.setText(jsonResponse.getoriginal_title());
        mLanguaje.setText(jsonResponse.getoriginal_language());
        mWeb.setText(jsonResponse.getHomepage());
        mScore.setText(String.valueOf(jsonResponse.getVote_avergge()));
        mVoteCount.setText("("+String.valueOf(jsonResponse.getVote_count())+" votos)");

        String url = "https://image.tmdb.org/t/p/w500/"+ jsonResponse.getImage();


        Glide.with(getActivity())
                .load(url)
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background))

                .into(mAvatar);

    }



    public void loadMovie() {
        new GetMovieByIdTask().execute();
    }



    private void showMovie(Movie movie) {

           setData(movie);

    }




    private class GetMovieByIdTask extends AsyncTask<Void, Void, Cursor> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alert.showAlert("Cargando Detalle","Por favor espere...");
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mMoviesDbHelper.getMovieById(idMovie);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showMovie(new Movie(cursor));
            } else {
                Toast.makeText(getContext(), "No hay Datos", Toast.LENGTH_SHORT).show();
            }

            alert.close();
        }

    }


}
