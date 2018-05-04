package paul.dev.listmovies.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import paul.dev.listmovies.Adapters.DataAdapter;

import paul.dev.listmovies.BuildConfig;
import paul.dev.listmovies.DataDB.MoviesDbHelper;
import paul.dev.listmovies.Interfaces.RequestInterface;
import paul.dev.listmovies.Models.Movie;
import paul.dev.listmovies.R;
import paul.dev.listmovies.utils.Alerts;
import paul.dev.listmovies.utils.CheckConexion;
import paul.dev.listmovies.utils.JSONResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListMoviesFragment extends Fragment  {


    private RecyclerView recyclerView;
    private ArrayList<Movie> data;
    SearchView searchView;
    CheckConexion connected;
    private DataAdapter adapter;
    Alerts alert;

    private MoviesDbHelper mMoviesDbHelper;


    public ListMoviesFragment() {
        // Required empty public constructor
    }

    public static ListMoviesFragment newInstance() {
        return new ListMoviesFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.fragment_list_movies, container, false);


            connected = new CheckConexion();


                alert = new Alerts(getContext());



            // Instancia de helper
            mMoviesDbHelper = new MoviesDbHelper(getActivity());


        initViews(root);

            // Carga de datos
            if((connected.isOnline(getContext()) == false))
                loadMovies();
            else
                loadJSON();



        setHasOptionsMenu(true);


            return root;
    }

    private void initViews(View root){
        recyclerView = (RecyclerView) root.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

    }
    private void loadJSON(){

        alert.showAlert("Cargando Películas","Por favor espere...");

        getActivity().deleteDatabase(MoviesDbHelper.DATABASE_NAME);




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON("popular", BuildConfig.API_KEY,1);
        call.enqueue(new Callback<JSONResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getAndroid()));
                adapter = new DataAdapter(getActivity(),data);
                ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(adapter);
                recyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));

                for (int i = 0; i < jsonResponse.getAndroid().length; i++) {



                    mMoviesDbHelper.saveMovies(new Movie(jsonResponse.getAndroid()[i].getid(),
                            jsonResponse.getAndroid()[i].gettitle(),
                            jsonResponse.getAndroid()[i].getoverview(),
                            jsonResponse.getAndroid()[i].getoriginal_title(),
                            jsonResponse.getAndroid()[i].getVote_count(),
                            jsonResponse.getAndroid()[i].getVote_avergge(),
                            jsonResponse.getAndroid()[i].getPopularity(),
                            jsonResponse.getAndroid()[i].getoriginal_language(),
                            jsonResponse.getAndroid()[i].getImage(),
                            jsonResponse.getAndroid()[i].getPoster_path(),
                            jsonResponse.getAndroid()[i].getRelease_date(),
                            jsonResponse.getAndroid()[i].getHomepage(),
                            2));

                }

                alert.close();

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.w("Error",t.getMessage());
            }
        });
    }

    private class MovieLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alert.showAlert("Cargando Películas","Por favor espere...");
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mMoviesDbHelper.getAll(1);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {

            if (cursor != null && cursor.getCount() > 0) {
                ArrayList<Movie> list = new ArrayList<>();
                while(cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String title = cursor.getString(cursor.getColumnIndex("name"));
                    String original_title = cursor.getString(cursor.getColumnIndex("title"));
                    int vote_count = cursor.getInt(cursor.getColumnIndex("count"));
                    Double vote_average = cursor.getDouble(cursor.getColumnIndex("average"));
                    Double popularity = cursor.getDouble(cursor.getColumnIndex("demand"));
                    String languaje = cursor.getString(cursor.getColumnIndex("languajes"));
                    String image = cursor.getString(cursor.getColumnIndex("image"));
                    String overview = cursor.getString(cursor.getColumnIndex("descripcion"));
                    String image_poster = cursor.getString(cursor.getColumnIndex("poster"));
                    String release = cursor.getString(cursor.getColumnIndex("premier"));
                    String homepage = cursor.getString(cursor.getColumnIndex("homepage"));
                   Movie data = new Movie(id, title,overview,original_title,vote_count,vote_average,popularity,languaje,image,image_poster,release,homepage,1);
                    list.add(data);
                }



                adapter = new DataAdapter(getActivity(),list);
                ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(adapter);
                recyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
                alert.close();

            } else {

                Toast.makeText(getActivity(), "No hay datos", Toast.LENGTH_SHORT).show();
                // Mostrar empty state
            }
        }
    }


    private void loadMovies() {
        new MovieLoadTask().execute();
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_activity, menu);
        super.onCreateOptionsMenu(menu,inflater);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }








}
