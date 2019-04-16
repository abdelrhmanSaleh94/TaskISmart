package com.example.abdelrahmansaleh.taskismart.helper;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.abdelrahmansaleh.taskismart.R;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

class MediaLoader implements AlbumLoader {
    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load( imageView, albumFile.getPath() );
    }

    @Override
    public void load(ImageView imageView, String url) {
        HelperMethods.useGlide( imageView.getContext(), imageView, url );
    }
}
