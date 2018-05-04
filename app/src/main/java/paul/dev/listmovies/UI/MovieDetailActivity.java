package paul.dev.listmovies.UI;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;

import android.view.Window;

import paul.dev.listmovies.Fragments.MovieDetailActivityFragment;
import paul.dev.listmovies.MainActivity;
import paul.dev.listmovies.R;

public class MovieDetailActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.close);

        Window window = getWindow();

        Explode t0 = new Explode();
        window.setEnterTransition(t0);


        String id = getIntent().getStringExtra(MainActivity.EXTRA_MOVIE_ID);

        MovieDetailActivityFragment fragment = (MovieDetailActivityFragment)
                getSupportFragmentManager().findFragmentById(R.id.lawyer_detail_container);
        if (fragment == null) {
            fragment = MovieDetailActivityFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.lawyer_detail_container, fragment)
                    .commit();
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

