package com.example.abdelrahmansaleh.taskismart.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class HelperMethods {
    public static ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable( Color.parseColor( "#ffeead" ) ),
                    new ColorDrawable( Color.parseColor( "#93cfb3" ) ),
                    new ColorDrawable( Color.parseColor( "#fd7a7a" ) ),
                    new ColorDrawable( Color.parseColor( "#faca5f" ) ),
                    new ColorDrawable( Color.parseColor( "#1ba798" ) ),
                    new ColorDrawable( Color.parseColor( "#6aa9ae" ) ),
                    new ColorDrawable( Color.parseColor( "#ffbf27" ) ),
                    new ColorDrawable( Color.parseColor( "#d93947" ) )
            };

    public static ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt( vibrantLightColorList.length );
        return vibrantLightColorList[idx];
    }

    public static void useGlide(Context context, ImageView imageView, String imageUrl) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error( getRandomDrawbleColor() );
        Glide.with( context ).load( imageUrl ).transition( DrawableTransitionOptions.withCrossFade() )
                .apply( requestOptions )
                .into( imageView )
        ;

    }

    public static void useGlide(Context context, CircleImageView imageView, String imageUrl) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error( getRandomDrawbleColor() );
        Glide.with( context ).load( imageUrl ).transition( DrawableTransitionOptions.withCrossFade() )
                .apply( requestOptions )
                .into( imageView )
        ;

    }

    public static void openAlbum(int Counter, Context context,
                                 final ArrayList<AlbumFile> ImagesFiles, Action<ArrayList<AlbumFile>> action) {
        Album album = new Album();
        Album.initialize( AlbumConfig.newBuilder( context )
                .setAlbumLoader( new MediaLoader() )
                .setLocale( Locale.ENGLISH ).build() );
        album.image( context )// Image and video mix options.
                .multipleChoice()// Multi-Mode, Single-Mode: singleChoice().
                .columnCount( 3 ) // The number of columns in the page list.
                .selectCount( Counter )  // Choose up to a few images.
                .camera( true ) // Whether the camera appears in the Item.
                .checkedList( ImagesFiles ) // To reverse the list.
                .widget(
                        Widget.newLightBuilder( context )
                                .title( "" )
                                .statusBarColor( Color.WHITE ) // StatusBar color.
                                .toolBarColor( Color.WHITE ) // Toolbar color.
                                .navigationBarColor( Color.WHITE ) // Virtual NavigationBar color of Android5.0+.
                                .mediaItemCheckSelector( Color.BLUE, Color.GREEN ) // Image or video selection box.
                                .bucketItemCheckSelector( Color.RED, Color.YELLOW ) // Select the folder selection box.
                                .build()
                )
                .onResult( action )
                .onCancel( new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
// The user canceled the operation.
                    }
                } )
                .start();
    }
}
