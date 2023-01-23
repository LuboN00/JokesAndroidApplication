package nbu.f96226.project.jokes;
import static nbu.f96226.project.jokes.service.SQLiteService.getSQLiteServiceInstance;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import nbu.f96226.project.jokes.adapter.LikedJokeAdapter;
import nbu.f96226.project.jokes.model.JokesApiResponse;
import nbu.f96226.project.jokes.service.SQLiteService;

public class LikedJokesActivity extends AppCompatActivity {

    ListView likedJokesList;
    LikedJokeAdapter adapter;
    SQLiteService sqliteService = getSQLiteServiceInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_jokes);

        initElements();
        loadLikedJokes();
    }

    private void initElements() {
        likedJokesList = findViewById(R.id.likedJokesList);
    }

    private void loadLikedJokes() {
        List<JokesApiResponse> likedJokes = sqliteService.getAllLikedJokes();
        adapter =
                new LikedJokeAdapter(likedJokes, R.layout.liked_joke, this);
        likedJokesList.setAdapter(adapter);
    }

    public void unlikeJokeHandler(View view) {
        JokesApiResponse targetJoke = (JokesApiResponse) view.getTag();

        adapter.remove(targetJoke);
        sqliteService.unlikeJoke(targetJoke.getId());
    }
}
