package go.id.kominfo.pendaftaran_beasiswa;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import go.id.kominfo.pendaftaran_beasiswa.adapter.DataBeasiswaAdapter;
import go.id.kominfo.pendaftaran_beasiswa.model.DataBeasiswaModel;

public class ListActivity extends AppCompatActivity {
    RequestQueue queue;
    ListView listView;
    List<DataBeasiswaModel> itemsDataBeasiswa = new ArrayList<>();
    DataBeasiswaAdapter dataBeasiswaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView =  findViewById(R.id.listView);
        dataBeasiswaAdapter = new DataBeasiswaAdapter(this,itemsDataBeasiswa);
        listView.setAdapter(dataBeasiswaAdapter);
    }

    @Override
    protected void onResume() {
        getDataBeasiswa();
        super.onResume();
    }
    private void getDataBeasiswa() {
        queue = Volley.newRequestQueue(this);
        String url = "https://5836-125-165-138-64.ngrok.io/api/data-beasiswa";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i =0; i< response.length(); i++) {
                        JSONObject dataBeasiswa = response.getJSONObject(i);
                        DataBeasiswaModel dataBeasiswaModel = new DataBeasiswaModel();
                        String id = dataBeasiswa.getString("id");
                        String nama = dataBeasiswa.getString("nama");
                        dataBeasiswaModel.setId(id);
                        dataBeasiswaModel.setNama(nama);

                        String alamat = dataBeasiswa.getString("alamat");
                        dataBeasiswaModel.setAlamat(alamat);
                        String noHp = dataBeasiswa.getString("no_hp");
                        dataBeasiswaModel.setNoHp(noHp);
                        String jenisKelamin = dataBeasiswa.getString("jenis_kelamin");
                        dataBeasiswaModel.setJenisKelamin(jenisKelamin);

                        String photo = dataBeasiswa.getString("upload_photo");
                        String urlphoto = "https://5836-125-165-138-64.ngrok.io/storage/data/"+photo;
                        dataBeasiswaModel.setUploadPhoto(urlphoto);
                        String lokasiPhoto = dataBeasiswa.getString("lokasi_user");
                        dataBeasiswaModel.setLokasiUser(lokasiPhoto);
                        itemsDataBeasiswa.add(dataBeasiswaModel);


                    }
                }catch(JSONException error) {
                    error.printStackTrace();
                }
                dataBeasiswaAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error" , "" +error.toString());
            }
        }
        );
        queue.add(jsonArrayRequest);
    }
}