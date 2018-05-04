package paul.dev.listmovies.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import paul.dev.listmovies.DataDB.MoviesDbHelper;
import paul.dev.listmovies.MainActivity;
import paul.dev.listmovies.Models.Movie;
import paul.dev.listmovies.R;
import paul.dev.listmovies.UI.MovieDetailActivity;

/**
 * Created by paulotrujillo on 5/1/18.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable{
    private ArrayList<Movie> android;
    private ArrayList<Movie> androidFiltered;
    private Context mContext;



    public DataAdapter(Context context, ArrayList<Movie> android) {
        this.mContext = context;
        this.android = android;
        this.androidFiltered = android;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView txt_title,txt_date,txt_overview,txt_score;
        private ImageView poster_image;
        private String idMovie;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txt_title = (TextView)itemView.findViewById(R.id.txt_title);
            txt_date = (TextView)itemView.findViewById(R.id.txt_date);
            txt_overview = (TextView)itemView.findViewById(R.id.txt_overview);
            poster_image = (ImageView)itemView.findViewById(R.id.image_post);
            txt_score = (TextView)itemView.findViewById(R.id.txt_score);

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            //Toast.makeText(v.getContext() , txtTitle.getText() , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(itemView.getContext() , MovieDetailActivity.class);
            // here pass id through intent
            intent.putExtra("idMovie" , idMovie);




            itemView.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity)mContext).toBundle());
        }
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);




        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.txt_title.setText(android.get(i).gettitle());
        viewHolder.txt_date.setText(android.get(i).getRelease_date());
        viewHolder.txt_overview.setText(android.get(i).getoverview());
        viewHolder.txt_score.setText(String.valueOf(android.get(i).getVote_avergge()));
        viewHolder.idMovie = android.get(i).getid();
        String url = "https://image.tmdb.org/t/p/w500/"+android.get(i).getImage();




        Glide.with(mContext)
                .load(url)
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background))

                .into(viewHolder.poster_image);


    }





    @Override
    public int getItemCount() {
        return androidFiltered.size();
    }




    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();



                if (charString.isEmpty()) {
                    androidFiltered = android;
                } else {
                    ArrayList<Movie> filteredList = new ArrayList<>();
                    for (Movie row : android) {


                        if (row.gettitle().toLowerCase().contains(charSequence.toString().toLowerCase()) || row.getoriginal_title().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filteredList.add(row);

                        }
                    }

                    androidFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = androidFiltered.size();
                filterResults.values = androidFiltered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                androidFiltered = (ArrayList<Movie>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }








}
