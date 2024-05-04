package com.example.contributors;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    TableLayout mTableLayout;
    List<Contributor> mContributors;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mContributors = new ArrayList<>();
        mTableLayout = findViewById(R.id.tableLayout);
        mProgressBar.setVisibility(View.VISIBLE);


        GitHubAPI gitHubAPI = GitHubAPI.retrofit.create(GitHubAPI.class);
        final Call<List<Contributor>> call = gitHubAPI.getData("square", "picasso");

        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                if (response.isSuccessful()){
                    mContributors.addAll(response.body());
                    populateTable();
                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    // Обрабатываем ошибку
                    ResponseBody errorBody = response.errorBody();
                    try {
                        Toast.makeText(MainActivity.this, errorBody.string(),
                                Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.INVISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable throwable) {
                Log.d("GIT", ""+throwable.getMessage());
                Toast.makeText(MainActivity.this, "Что-то пошло не так. ",
                        Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);

            }
        });

    }

    private void populateTable() {
        int contributorPerRow = 3;
        int numRows = (int) Math.ceil((double) mContributors.size() / contributorPerRow);

        for (int i = 0; i < numRows; i++) {
            TableRow row = new TableRow(this);

            for (int j = 0; j < contributorPerRow; j++) {
                int index = i * contributorPerRow + j;
                if (index < mContributors.size()) {
                    Contributor contributor = mContributors.get(index);

                    View cellView = getLayoutInflater().inflate(R.layout.item_contributor, null);
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT
                    );

                    layoutParams.width = 300;
                    layoutParams.height = 120;

                    ImageView avatarImageView = cellView.findViewById(R.id.imageView);
                    Picasso.with(this)
                            .load(contributor.getAvatarURL())
                            .into(avatarImageView);

                    TextView usernameTextView = cellView.findViewById(R.id.flowerName);
                    usernameTextView.setText(contributor.getName());

                    TextView contributionsTextView = cellView.findViewById(R.id.flowerDesc);
                    contributionsTextView.setText(contributor.getContributions());

                    cellView.setLayoutParams(layoutParams);

                    row.addView(cellView);
                }
            }

            mTableLayout.addView(row);
        }

    }
}