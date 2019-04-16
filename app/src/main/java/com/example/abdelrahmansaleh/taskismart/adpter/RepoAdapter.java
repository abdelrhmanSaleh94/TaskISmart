package com.example.abdelrahmansaleh.taskismart.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.abdelrahmansaleh.taskismart.R;
import com.example.abdelrahmansaleh.taskismart.data.model.getHubRepos.GetHubRepo;
import com.example.abdelrahmansaleh.taskismart.helper.CustomDialogClass;
import com.example.abdelrahmansaleh.taskismart.helper.HelperMethods;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    private Context context;
    private List<GetHubRepo> repoList = new ArrayList<>();
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    Button showDetails;
    private int selected_position = -1;

    public RepoAdapter(Context context, List<GetHubRepo> repoList, Button showDetails) {
        this.context = context;
        this.repoList = repoList;
        this.showDetails = showDetails;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.item_swipe_card, parent, false );
        return new RepoViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        viewBinderHelper.bind( holder.itemCardSwipeParent, repoList.get( position ).getId().toString() );
        HelperMethods.useGlide( context, holder.swipeCardImage, "no Image" );
        holder.cardNewsProgressImgeLoading.setVisibility( View.GONE );
        holder.swipeCardTvTitle.setText( repoList.get( position ).getFullName() );
        holder.cardTvDec.setText( repoList.get( position ).getNodeId() );
        action( holder, position );
    }

    private void action(RepoViewHolder holder, final int position) {
        holder.deleteButtonSwipe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repoList.remove( position );
                notifyDataSetChanged();
                StyleableToast.makeText( context, "Delete Repo", R.style.delete ).show();
            }
        } );
        holder.editButtonSwipe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomDialogClass dialog = new CustomDialogClass( context
                        , repoList.get( position ).getFullName(), repoList.get( position ).getNodeId(), "no Image" );
                dialog.show();
                dialog.save.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText( context, "save", Toast.LENGTH_SHORT ).show();
                        String name = dialog.name.getText().toString();
                        String decs = dialog.disc.getText().toString();
                        repoList.get( position ).setFullName( name );
                        repoList.get( position ).setNodeId( decs );
                        notifyDataSetChanged();
                        StyleableToast.makeText( context, "Update Repo ", R.style.Done ).show();
                        dialog.dismiss();
                    }
                } );
            }
        } );
        if (selected_position == position) {
            holder.swipeCardCheckBox.setChecked( true );

        } else {
            holder.swipeCardCheckBox.setChecked( false );
        }
        holder.swipeCardCheckBox.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    selected_position = position;
                    showDetails.setVisibility( View.VISIBLE );
                } else {
                    selected_position = -1;
                    showDetails.setVisibility( View.GONE );
                }
                notifyDataSetChanged();
                showDetails.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final CustomDialogClass dialog = new CustomDialogClass( context
                                , repoList.get( selected_position ).getFullName(), repoList.get( selected_position ).getNodeId(), "no Image" );
                        dialog.show();
                        dialog.save.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText( context, "save", Toast.LENGTH_SHORT ).show();
                                String name = dialog.name.getText().toString();
                                String decs = dialog.disc.getText().toString();
                                repoList.get( position ).setFullName( name );
                                repoList.get( position ).setNodeId( decs );
                                notifyDataSetChanged();
                                StyleableToast.makeText( context, "Update Repo ", R.style.Done ).show();
                                dialog.dismiss();
                            }
                        } );

                    }
                } );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.delete_buttonSwipe)
        ImageButton deleteButtonSwipe;
        @BindView(R.id.edit_buttonSwipe)
        ImageButton editButtonSwipe;
        @BindView(R.id.swipeCardImage)
        ImageView swipeCardImage;
        @BindView(R.id.cardImageSwipe)
        CardView cardImageSwipe;
        @BindView(R.id.cardNewsProgressImgeLoading)
        ProgressBar cardNewsProgressImgeLoading;
        @BindView(R.id.swipeCardTvTitle)
        TextView swipeCardTvTitle;
        @BindView(R.id.cardTvDec)
        TextView cardTvDec;
        @BindView(R.id.itemCardSwipeParent)
        SwipeRevealLayout itemCardSwipeParent;
        @BindView(R.id.swipeCardCheckBox)
        CheckBox swipeCardCheckBox;
        private View view;

        public RepoViewHolder(View itemView) {
            super( itemView );
            view = itemView;
            ButterKnife.bind( this, view );
        }
    }
}
