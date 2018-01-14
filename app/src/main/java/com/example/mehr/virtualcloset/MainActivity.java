package com.example.mehr.virtualcloset;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mehr.virtualcloset.model.WeatherList;
import com.example.mehr.virtualcloset.model.WeatherResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;

    APIInterface api;

    final static String APIKEY = "7f61a207ba6210853ca701866e2325d6";

    static int MY_PERMISSIONS_REQUEST_LOCATION = 1;


    public void getLocation() {

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            api.getWeatherInfoCoords(location.getLatitude(), location.getLongitude(), "metric", APIKEY).enqueue(new Callback<WeatherResponse>() {
                                @Override
                                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                                    String weatherInfo1 = "";
                                    String weatherInfo2 = "";
                                    String weatherInfo3 = "";

                                    WeatherResponse firstCall = response.body();

                                    WeatherList firstHour = firstCall.getList().get(0);
                                    WeatherList scndHour = firstCall.getList().get(1);
                                    WeatherList thrdHour = firstCall.getList().get(2);

                                    weatherInfo1 += "temp: " + firstHour.getMain().getTemp() +
                                            " description: " + firstHour.getWeather().get(0).getMain() +
                                            " date: " + firstHour.getDtTxt() +
                                            " city: " + firstCall.getCity().getName();

                                    Log.d("MainActivity", weatherInfo1);

                                    weatherInfo2 += "temp: " + scndHour.getMain().getTemp() +
                                            " description: " + scndHour.getWeather().get(0).getMain() +
                                            " date: " + scndHour.getDtTxt();

                                    Log.d("MainActivity", weatherInfo2);

                                    weatherInfo3 += "temp: " + thrdHour.getMain().getTemp() +
                                            " description: " + thrdHour.getWeather().get(0).getMain() +
                                            " date: " + thrdHour.getDtTxt();

                                    Log.d("MainActivity", weatherInfo3);

                                }

                                @Override
                                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                                    Log.d("MainActivity", t.toString());
                                }
                            });
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Log.d("MainActivity", "location permission denied");
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = APIClient.getClient().create(APIInterface.class);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        int locPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (locPermission == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

        } else {
            getLocation();
        }



       /* api.getWeatherInfoCity("Toronto,ca", "metric", APIKEY).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                String weatherInfo = "";

                WeatherResponse firstCall = response.body();
                WeatherList firstHour = firstCall.getList().get(0);

                weatherInfo += "temp: " + firstHour.getMain().getTemp() +
                        " description: " + firstHour.getWeather().get(0).getMain() +
                        " date: " + firstHour.getDtTxt();

                Log.d("MainActivity", weatherInfo);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.d("MainActivity", t.toString());
            }
        });*/


    }
    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "temp"; //name according to user pick/entry
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); //
        File image = new File(storageDir, imageFileName);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast error = Toast.makeText(this,"Failed to create file",Toast.LENGTH_SHORT);
                error.show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            File captured = new File(mCurrentPhotoPath);
            Uri uri = Uri.fromFile(captured);
            final View savePicLayout = getLayoutInflater().inflate(R.layout.activity_save_photo, null);
            final ImageView currentPic = (ImageView) savePicLayout.findViewById(R.id.current_pic);
            //currentPic.setImageURI(null);
            currentPic.setImageURI(uri);
            Intent saveIntent = new Intent(MainActivity.this, SavePhotoActivity.class);
            startActivity(saveIntent);

        }
    }
}
