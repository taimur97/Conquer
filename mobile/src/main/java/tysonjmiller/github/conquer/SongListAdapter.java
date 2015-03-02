package tysonjmiller.github.conquer;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;

import java.util.ArrayList;

import roboguice.RoboGuice;

/**
 * Created by Tyson Miller on 3/1/2015.
 */
public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder> {
    @Inject MediaDAO mediaDAO;
    private ArrayList<Song> mSongs;

    // Create a constuctor, using a list of songs
    public SongListAdapter(ArrayList<Song> songs, Context context) {
        mSongs = songs;
        RoboGuice.injectMembers(context, this);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_item, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song song = mSongs.get(position);
        holder.song = song; // needed for onClick

        holder.mTextView.setText(song.title + "\n" + song.artist);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (mSongs != null) ? mSongs.size() : 0;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        // each data item is just a string for now
        public Song song;
        public TextView mTextView;
        public CardView mCardView;
        public ViewHolder(LinearLayout cardLayout) {
            super(cardLayout);
            mTextView = (TextView)cardLayout.findViewById(R.id.song_info_text_view);
            mCardView = (CardView)cardLayout.findViewById(R.id.card_view);
            mCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (song == null) {
                Log.d("ViewHolder", "No Song data is set for the ViewHolder");
                return;
            }
            Log.d("ViewHolder", "onClick, Playing " + song.title + " " + song.artist);
            mediaDAO.sendActionToMediaService(v.getContext(), song, Constants.ACTION_PLAY);
            Toast.makeText(v.getContext(), "Playing " + song.title + " " + song.artist, Toast.LENGTH_LONG).show();
        }

        @Override
        public boolean onLongClick(View v) {
            if (song == null) {
                Log.d("ViewHolder", "No Song data is set for the ViewHolder");
                return false;
            }
            Log.d("ViewHolder", "onLongClick, Pausing " + song.title + " " + song.artist);
            mediaDAO.sendActionToMediaService(v.getContext(), song, Constants.ACTION_PAUSE);
            Toast.makeText(v.getContext(), "Pausing " + song.title + " " + song.artist, Toast.LENGTH_LONG).show();
            return true;
        }
    }
}
