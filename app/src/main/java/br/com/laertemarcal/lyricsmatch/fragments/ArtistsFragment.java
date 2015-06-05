package br.com.laertemarcal.lyricsmatch.fragments;

/**
 * Created by Laerte on 02/06/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import br.com.laertemarcal.lyricsmatch.R;
import br.com.laertemarcal.lyricsmatch.adapters.ArtistsAdapter;
import br.com.laertemarcal.lyricsmatch.listeners.RecyclerItemClickListener;
import br.com.laertemarcal.lyricsmatch.model.Artist;

public class ArtistsFragment extends Fragment {

    private RecyclerView mArtistsRecyclerView;
    private ProgressBar mSpinner;

    public ArtistsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mArtistsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mArtistsRecyclerView.setHasFixedSize(true);
        mArtistsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mArtistsRecyclerView.setAdapter(new ArtistsAdapter(new ArrayList<Artist>()));
        mArtistsRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ArtistsAdapter ad = (ArtistsAdapter) mArtistsRecyclerView.getAdapter();
                        ArrayList<Artist> artists = ad.getDataset();
                        Artist artist = artists.get(position);

                        Bundle args = new Bundle();
                        args.putString("artist_name", artist.getName());

                        TracksFragment tracksFragment = new TracksFragment();
                        tracksFragment.setArguments(args);

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack if needed
                        transaction.replace(R.id.container, tracksFragment);
                        transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();
                    }
                })
        );

        mSpinner = (ProgressBar) rootView.findViewById(R.id.progressBar);

        return rootView;
    }

    public void setArtistsOnView(ArrayList<Artist> artists) {
        mArtistsRecyclerView.swapAdapter(new ArtistsAdapter(artists), false);
    }

    public ProgressBar getSpinner() {
        return mSpinner;
    }

}
