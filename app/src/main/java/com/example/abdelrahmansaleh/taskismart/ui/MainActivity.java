package com.example.abdelrahmansaleh.taskismart.ui;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abdelrahmansaleh.taskismart.R;
import com.example.abdelrahmansaleh.taskismart.adpter.RepoAdapter;
import com.example.abdelrahmansaleh.taskismart.data.model.getHubRepos.GetHubRepo;
import com.example.abdelrahmansaleh.taskismart.data.rest.ApiService;
import com.example.abdelrahmansaleh.taskismart.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.taskismart.helper.HelperMethods;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.mainProfileImage)
    ImageView mainProfileImage;
    @BindView(R.id.mainBtnImage)
    ImageButton mainBtnImage;
    @BindView(R.id.toolbarProfileName)
    TextView toolbarProfileName;
    @BindView(R.id.title_appbar)
    LinearLayout titleAppbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.mainProgress_bar)
    ProgressBar mainProgressBar;
    @BindView(R.id.mainRecyclerView)
    RecyclerView mainRecyclerView;
    @BindView(R.id.cardImage)
    CardView cardImage;
    @BindView(R.id.toolbarImage)
    CircleImageView toolbarImage;
    @BindView(R.id.mainAppbar)
    AppBarLayout appbar;
    @BindView(R.id.mainBtnShowDetails)
    Button mainBtnShowDetails;
    private ColorDrawable randomDrawbleColor;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private ApiService apiService;
    private List<GetHubRepo> repoList;
    private RepoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        ButterKnife.bind( this );
        mainRecyclerView.setNestedScrollingEnabled( false );
        collapsingToolbar.setTitle( "" );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( "" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        randomDrawbleColor = HelperMethods.getRandomDrawbleColor();
        appbar.addOnOffsetChangedListener( this );
        HelperMethods.useGlide( this, mainProfileImage, "no Image" );
        apiService = RetrofitClient.getClient().create( ApiService.class );
        repoList = new ArrayList<>();
        mainRecyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        mainRecyclerView.setItemAnimator( new DefaultItemAnimator() );
        mainRecyclerView.setHasFixedSize( true );
        mainRecyclerView.setItemViewCacheSize( 10 );
        mainRecyclerView.setDrawingCacheEnabled( true );
        adapter = new RepoAdapter( this, repoList,mainBtnShowDetails );
        mainRecyclerView.setAdapter( adapter );
        getRepoData();
    }

    private void getRepoData() {
        apiService.GET_HUB_REPO_CALL().enqueue( new Callback<List<GetHubRepo>>() {
            @Override
            public void onResponse(Call<List<GetHubRepo>> call, Response<List<GetHubRepo>> response) {

                repoList.addAll( response.body() );
                adapter.notifyDataSetChanged();
                mainProgressBar.setVisibility( View.GONE );
            }

            @Override
            public void onFailure(Call<List<GetHubRepo>> call, Throwable t) {
                StyleableToast.makeText( MainActivity.this, "No Internet Connection Plz Try Again", R.style.Error ).show();
            }
        } );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs( verticalOffset ) / (float) maxScroll;
        if (percentage < 1f) {
            cardImage.setVisibility( View.VISIBLE );
            titleAppbar.setVisibility( View.GONE );
            toolbar.setBackgroundResource( R.drawable.top_shadow );

        } else if (percentage == 1f) {
            cardImage.setVisibility( View.GONE );
            titleAppbar.setVisibility( View.VISIBLE );
            toolbar.setBackground( randomDrawbleColor );
            if (ImagesFiles.size() != 0) {
                HelperMethods.useGlide( MainActivity.this, toolbarImage, ImagesFiles.get( 0 ).getPath() );
            } else {
                HelperMethods.useGlide( MainActivity.this, toolbarImage, "no Image" );
            }
        }
    }

    @OnClick(R.id.mainBtnImage)
    public void onViewClicked() {
        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
            @Override
            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                ImagesFiles.clear();
                ImagesFiles.addAll( result );
                HelperMethods.useGlide( MainActivity.this, mainProfileImage, ImagesFiles.get( 0 ).getPath() );
            }
        };
        HelperMethods.openAlbum( 3, MainActivity.this, ImagesFiles, action );
    }
}
