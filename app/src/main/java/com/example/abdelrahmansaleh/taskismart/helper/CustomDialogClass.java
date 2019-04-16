package com.example.abdelrahmansaleh.taskismart.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.abdelrahmansaleh.taskismart.R;

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {
    public Context context;
    public Dialog dialog;
    public Button save, cancel;
    public ImageView imageView;
    public EditText name, disc;
    private String nameText, discText;
    private String urlImage;

    public CustomDialogClass(@NonNull Context context, String nameText, String discText, String urlImage) {
        super( context );
        this.context = context;
        this.nameText = nameText;
        this.discText = discText;
        this.urlImage = urlImage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.custom_dialog );
        imageView = findViewById( R.id.dialogImage );
        name = findViewById( R.id.dialogEdName );
        disc = findViewById( R.id.dialogEdDec );
        save = findViewById( R.id.dialogBtnSave );
        cancel = findViewById( R.id.dialogBtnCancel );
        save.setOnClickListener( this );
        cancel.setOnClickListener( this );
        HelperMethods.useGlide( context, imageView, urlImage );
        name.setText( nameText );
        disc.setText( discText );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialogBtnSave:
                Toast.makeText( context, "save", Toast.LENGTH_SHORT ).show();
                break;
            case R.id.dialogBtnCancel:
                dismiss();
                break;
            default:
                dismiss();
                break;
        }
        dismiss();
    }
}
