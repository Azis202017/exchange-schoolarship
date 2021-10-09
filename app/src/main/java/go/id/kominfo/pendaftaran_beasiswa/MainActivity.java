package go.id.kominfo.pendaftaran_beasiswa;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements LocationListener {
    private final String boundary = "apiclient-" + System.currentTimeMillis();

    EditText nameEdtText;
    EditText alamatEdtText;
    EditText noEdtText;
    RadioGroup radioGroup;
    RadioButton priaRB;
    RadioButton wanitaRB;
    String jk; // jenis  kelamin
    Button saveButton;
    Button currentLocationButton;
    Button uploadPhotoButton;
    ImageView resultPic;
    TextView locationUser;
    LocationManager locationManager;
    Uri uri;
    String address;
    List<Address> addresses;
    Bitmap bitmap;
    private String filePath;
    Button listActivityButton;



    String url = "https://5836-125-165-138-64.ngrok.io/api/data-beasiswa/post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdtText = findViewById(R.id.nameEdtTxt);
        alamatEdtText = findViewById(R.id.alamatEdtText);
        noEdtText = findViewById(R.id.noEdtText);
        saveButton = findViewById(R.id.saveButton);
        radioGroup = findViewById(R.id.jenisKelamin);
        priaRB = findViewById(R.id.priaRB);
        wanitaRB = findViewById(R.id.wanitaRB);
        resultPic = findViewById(R.id.resultg);
        locationUser = findViewById(R.id.locationText);
        uploadPhotoButton = findViewById(R.id.uploadPhotoButton);
        currentLocationButton = findViewById(R.id.checkLocationButton);
        listActivityButton = findViewById(R.id.listActivityButton);

//      Check permission
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedRadioButton =  radioGroup.getCheckedRadioButtonId();

                if(selectedRadioButton == priaRB.getId()) {
                    jk = "pria";
                }
                else if(selectedRadioButton == wanitaRB.getId() ) {
                    jk = "wanita";
                }
                Log.e("" , "" + jk);
                uploadBitmap(bitmap);

                Toast.makeText(MainActivity.this , "Data berhasil dimasukkan", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);


            }
        });
        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationUser.setVisibility(View.VISIBLE);
                getLocation();
            }
        });

        uploadPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.Companion.with(MainActivity.this).start();

            }
        });
        listActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });







    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5,MainActivity.this);

        }catch(Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            try {


                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);

                resultPic.setImageBitmap( bitmap);

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this,""+ location.getLatitude() + location.getLongitude(), Toast.LENGTH_LONG).show();
        try {
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 100);
            address =  addresses.get(0).getAddressLine(0);
            locationUser.setText(address);
        }catch(Exception e ){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
    private void uploadBitmap(final Bitmap bitmap) {

        VolleyRequestt volleyMultipartRequest = new VolleyRequestt(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("GotError",""+error.getMessage());
                    }
                }) {


            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("nama", ""+nameEdtText.getText());
                params.put("alamat",""+alamatEdtText.getText());
                params.put("no_hp",""+noEdtText.getText() );
                params.put("jenis_kelamin", jk );
                params.put("lokasi_user",address);

                return params;
            }

            protected Map<String, VolleyRequestt.DataPart> getByteData() {
                Map<String, VolleyRequestt.DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();

                params.put("upload_photo", new VolleyRequestt.DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

}