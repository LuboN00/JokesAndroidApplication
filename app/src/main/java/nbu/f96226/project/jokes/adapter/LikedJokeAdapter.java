package nbu.f96226.project.jokes.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import nbu.f96226.project.jokes.R;
import nbu.f96226.project.jokes.model.JokesApiResponse;

public class LikedJokeAdapter extends ArrayAdapter<JokesApiResponse> {

    private List<JokesApiResponse> items;
    private int layoutResourceId;
    private Context context;

    public LikedJokeAdapter(List<JokesApiResponse> items, int layoutResourceId, Context context) {
        super(context, layoutResourceId, items);
        this.items = items;
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    public static class LikedJokeHolder {
        JokesApiResponse likedJoke;
        TextView category;
        TextView jokeText;
        ImageButton unlikeBtn;
    }

    private void setupItem(LikedJokeHolder holder) {
        holder.category.setText(holder.likedJoke.getCategory());
        holder.jokeText.setText(holder.likedJoke.getJoke());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        LikedJokeHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new LikedJokeHolder();
        holder.likedJoke = items.get(position);
        holder.unlikeBtn = (ImageButton) row.findViewById(R.id.likedJokeUnlikeBtn);
        holder.unlikeBtn.setTag(holder.likedJoke);

        holder.category = (TextView) row.findViewById(R.id.likedJokeCategoryTextField);
        holder.jokeText = (TextView) row.findViewById(R.id.likedJokeTextField);

        row.setTag(holder);

        setupItem(holder);
        return row;
    }
}
