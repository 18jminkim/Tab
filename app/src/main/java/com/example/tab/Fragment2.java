package com.example.tab;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import  android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import java.nio.file.Files;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;


public class Fragment2 extends Fragment {
    private static final String TAG = "GalleryFragment";


    //constants
    private static final int NUM_GRID_COLUMNS = 3;
    private static final int CAMERA_REQUEST_CODE = 5;
    private int STORAGE_PERMISSION_CODE = 1;
    private int CAMERA_PERMISSION_CODE = 2;

    //widgets
    private GridView gridView;
    private ImageView galleryImage;
    private ProgressBar mProgressBar;
    private Spinner directorySpinner;
    private Button deleteButton;


    //vars
    private ArrayList<String> directories = new ArrayList<String>(); ;
    private String mAppend = "file:/";
    private String pathToFile;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            //Toast.makeText(MainActivity.this, "You have already granted this permission!",
            // Toast.LENGTH_SHORT).show();
        } else {
            requestStorageWritePermission();
        }


        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            /*
            Toast.makeText(MainActivity.this, "You have already granted this permission!",
                    Toast.LENGTH_SHORT).show();
             */
        } else {
            requestCameraPermission();
        }



        View view = inflater.inflate(R.layout.fragment2_layout, container, false);
        galleryImage = (ImageView) view.findViewById(R.id.galleryImageView);
        gridView = (GridView) view.findViewById(R.id.gridView);
        directorySpinner = (Spinner) view.findViewById(R.id.spinnerDirectory);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        directories = new ArrayList<String>();
        //deleteButton = (Button) view.findViewById(R.id.deleteButton); // instance of button class
        Log.d(TAG, "onCreateView: started.");

        //ImageView shareClose

        init();

        FloatingActionButton btnLaunchCamera = view.findViewById(R.id.btnLaunchCamera);
        btnLaunchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                Snackbar.make(view, "It's a trap :D", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                 */
                Log.d(TAG, "onClick: launching camera.");

                /*
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);

                 */
                dispatchPictureTakerAction();


            }

            private void dispatchPictureTakerAction(){
                Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = null;

                photoFile = createPhotoFile();
                if (photoFile != null) {
                    pathToFile = photoFile.getAbsolutePath();
                    Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.thecodecity.cameraandroid.fileprovider", photoFile);
                    takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePic, 1);



                }



            }

            private File createPhotoFile(){
                String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File image = null;
                try {
                    image = File.createTempFile(name, ".jpg", storageDir);
                } catch (IOException e) {
                    Log.d(TAG, "Excep : " +e.toString());
                }
                return image;
            }


        });

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);

            }

            Log.d(TAG, "onActivityResult: done taking a photo.");
            Log.d(TAG, "onActivityResult: attempting to return to gallery.");

            //navigate back to our gallery.

            init();
        }
    }

    private void init(){
        FilePaths filePaths = new FilePaths();

        if (FileSearch.getDirectoryPaths(filePaths.PICTURES) != null){
            directories = FileSearch.getDirectoryPaths(filePaths.PICTURES);
        }
        if (FileSearch.getDirectoryPaths(filePaths.PICTURES) != null){
            directories.add(filePaths.PICTURES);
        }




        if (FileSearch.getDirectoryPaths(filePaths.DOWNLOAD) != null){
            directories.add(filePaths.DOWNLOAD);
        }

        directories.add(filePaths.CAMERA);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, directories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        directorySpinner.setAdapter(adapter);

        directorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemSelected: selected: " + directories.get(i));

                //setup our image grid for the directory chosen
                setupGridView(directories.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //check for other folders inside "/storage/emulated/0/pictures"


    }


    private void setupGridView(String selectedDirectory){
        Log.d((TAG), "setupGridView: directory chosen: " + selectedDirectory);
        final ArrayList<String> imgURLs = FileSearch.getFilePaths(selectedDirectory);

        //set the grid column width
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth / NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);




        //use the grid adapter to adapter the images to gridview file ://
        GridImageAdapter adapter = new GridImageAdapter(getActivity(), R.layout.layout_grid_imageview, mAppend, imgURLs);
        gridView.setAdapter(adapter);


        //set the first image to be displayed when the activity fragment view is displayed

        if (imgURLs.size() > 0 ){
            setImage(imgURLs.get(0), galleryImage, mAppend);

        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick: selected an image: " + imgURLs.get(i));

                setImage(imgURLs.get(i), galleryImage, mAppend);
            }
        });

    }

    private void setImage(final String imgURL, ImageView image,final String append){
        Log.d(TAG, "setImage: setting Image");
        Log.d(TAG, "setImage: " + imgURL);
        Log.d(TAG, "setImage: " + append);

        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

        imageLoader.displayImage(append + imgURL, image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mProgressBar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                mProgressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mProgressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                mProgressBar.setVisibility(View.INVISIBLE);

            }
        });



        /* maybe: deleteButton?
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                this.deleteImage( imgURL,  append);
            }

            private void deleteImage(String imgURL, String append){
                String file_dj_path = imgURL;
                File fDelete = new File(imgURL);
                if (fDelete.exists()){
                    if (fDelete.delete()){
                        Log.d(TAG, "file deleted: " + file_dj_path);
                        //callBroadCast();
                        init();
                    } else {
                        Log.d(TAG, "file not deleted: " + file_dj_path);
                    }
                } else {
                    Log.d(TAG, "no such file: " + file_dj_path);
                }

            }

         */


            /*
            private void callBroadCast(){
                if (Build.VERSION.SDK_INT >= 15){
                    Log.e("-->", " >= 15");
                    MediaScannerConnection.scanFile(MainActivity.this, new String[]{Environment.getExternalStorageDirectory().toString()}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.e("ExternalStorage", "Scanned " + path + ":");
                                    Log.e("ExternalStorage", "-> uri=" + uri);
                                }
                            });
                } else{
                    Log.e("-->", " < 14");

                }

            }


        });


        Log.d(TAG, "setImage: button set");

             */


    }



/*
    private static final int REQUEST_PERMISSIONS = 1234;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    private  static final int PERMISSIONS_COUNT = 2;

    private boolean arePermissionDenied(){
        for (int i = 0; i < PERMISSIONS_COUNT; i++){
            if (checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

        }
    }
     */


    private void requestStorageWritePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Permission needed")
                    .setMessage("'Access to media' permission is needed to read or delete files in the gallery")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    private void requestCameraPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Permission needed")
                    .setMessage("'Access to media' permission is needed to take photos in the gallery")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
