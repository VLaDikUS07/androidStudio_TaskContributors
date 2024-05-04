package com.example.contributors;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class ContributorAdapter extends ArrayAdapter<Contributor> {
    private List<Contributor> mContributors;
    private Context mContext;

    public ContributorAdapter(Context context, List<Contributor> contributorName) {
        super(context, R.layout.item_contributor, contributorName);
        this.mContributors = contributorName;
        this.mContext = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contributor, parent, false);

        ImageView flowerimg = (ImageView) view.findViewById(R.id.imageView);
        TextView nameTextView = (TextView) view.findViewById(R.id.flowerName);
        TextView descripTextView = (TextView) view.findViewById(R.id.flowerDesc);

        Log.d("GIT", ""+this.mContributors.get(position).getAvatarURL());
        Picasso
                .with(view.getContext())
                .load(this.mContributors.get(position).getAvatarURL())
                .into(flowerimg);
        ;

        nameTextView.setText(this.mContributors.get(position).getName());
        descripTextView.setText(this.mContributors.get(position).getContributions());

        return view;
    }
}