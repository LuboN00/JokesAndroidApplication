package nbu.f96226.project.jokes;

import static java.lang.String.*;
import static nbu.f96226.project.jokes.service.SQLiteService.getSQLiteServiceInstance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Surface;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import nbu.f96226.project.jokes.Utils.Helper;
import nbu.f96226.project.jokes.api.JokesApi;
import nbu.f96226.project.jokes.model.JokesApiResponse;
import nbu.f96226.project.jokes.service.MusicService;
import nbu.f96226.project.jokes.service.SQLiteService;

public class MainActivity extends AppCompatActivity {

    Button newJokeBtn, likedJokesBtn;
    CheckBox likeCheckBox;
    EditText jokeTextField;
    SQLiteService sqliteService;
    JokesApiResponse latestResponse;
    Intent musicServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initElements();
        sqliteService = getSQLiteServiceInstance(this);

        setNewJokeBtnListener();
        setLikedJokesBtnListener();
        setLikeBtnListener();

        newJokeBtn.callOnClick();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
    }




    private void initElements() {
        newJokeBtn = findViewById(R.id.newJokeBtn);
        likedJokesBtn = findViewById(R.id.likedJokesBtn);
        likeCheckBox = findViewById(R.id.likeBtn);
        jokeTextField = findViewById(R.id.jokeText);
        musicServiceIntent = new Intent(this, MusicService.class);
    }

    private void setNewJokeBtnListener() {
        newJokeBtn.setOnClickListener(view -> {
            setJokeText();
        });
    }

    private void setLikedJokesBtnListener() {
        likedJokesBtn.setOnClickListener(view -> {
            switchToLikedJokesActivity();
        });
    }

    public void setLikeBtnListener() {
        likeCheckBox.setOnClickListener(view -> {
            toggleLikeButton();
        });
    }

    private void setJokeText() {
        try {
            JokesApiResponse response = new JokesApi.fetchJoke().execute().get();
            latestResponse = response;
            jokeTextField.setText(
                    Helper.formatJokeText(response.getJoke())
            );
            isJokeAlreadyLiked();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void isJokeAlreadyLiked() {
        if (sqliteService.isJokeLiked(latestResponse.getId())) {
            likeCheckBox.setBackgroundResource(R.drawable.icon_liked);
            likeCheckBox.setChecked(true);
        } else {
            likeCheckBox.setBackgroundResource(R.drawable.icon_not_liked);
            likeCheckBox.setChecked(false);
        }
    }

    private void toggleLikeButton() {
        if (likeCheckBox.isChecked()) {
            System.out.println(format("Joke %d is liked", latestResponse.getId()));
            likeCheckBox.setBackgroundResource(R.drawable.icon_liked);
            sqliteService.insertNewJoke(
                    latestResponse.getId(),
                    latestResponse.getJoke(),
                    latestResponse.getCategory()
            );
            startMusicService();

        } else {
            likeCheckBox.setBackgroundResource(R.drawable.icon_not_liked);
            System.out.println(format("Joke %d was unliked", latestResponse.getId()));
            sqliteService.unlikeJoke(latestResponse.getId());
        }
    }

    private void startMusicService() {
        startService(musicServiceIntent);
    }

    private void stopMusicService() {
        stopService(musicServiceIntent);
    }

    private void switchToLikedJokesActivity() {
        startActivity(
                new Intent(this, LikedJokesActivity.class)
        );
    }
}