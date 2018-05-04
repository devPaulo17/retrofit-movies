package paul.dev.listmovies.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import paul.dev.listmovies.BuildConfig;
import paul.dev.listmovies.R;

public class VideoActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private TextView mName,mSite,mType,mLink;
    private Button btnExit;

    private String KeyVideo;



    // YouTube player view
    private YouTubePlayerView youTubeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_video);

        mName = (TextView)findViewById(R.id.txt_name) ;
        mSite = (TextView)findViewById(R.id.txt_site) ;
        mType = (TextView)findViewById(R.id.txt_type) ;
        mLink = (TextView)findViewById(R.id.txt_link) ;
        mLink = (TextView)findViewById(R.id.txt_link) ;
        btnExit = (Button) findViewById(R.id.exit) ;

        mName.setText(getIntent().getStringExtra("name"));
        mSite.setText(getIntent().getStringExtra("site"));
        mType.setText(getIntent().getStringExtra("type"));
        mLink.setText("https://www.youtube.com/watch?v="+getIntent().getStringExtra("link"));

        KeyVideo = getIntent().getStringExtra("link");

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        // Initializing VideoMovie player with developer key
        youTubeView.initialize(BuildConfig.API_YOUTUBE, this);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {

            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {

            // loadVideo() will auto play VideoMovie
            // Use cueVideo() method, if you don't want to play it automatically
            player.loadVideo(KeyVideo);

            // Hiding player controls
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(BuildConfig.API_YOUTUBE, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

}