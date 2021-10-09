package go.id.kominfo.pendaftaran_beasiswa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import go.id.kominfo.pendaftaran_beasiswa.R;
import go.id.kominfo.pendaftaran_beasiswa.model.DataBeasiswaModel;

public class DataBeasiswaAdapter extends BaseAdapter {
    Activity activity;
    List<DataBeasiswaModel> items;
    private LayoutInflater inflater;
    public DataBeasiswaAdapter(Activity activity, List<DataBeasiswaModel> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null) inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) convertView = inflater.inflate(R.layout.activity_list_view_beasiswa, null);

        ImageView uploadResult = (ImageView) convertView.findViewById(R.id.uploadImageView);
        TextView id = (TextView) convertView.findViewById(R.id.idTextView);
        TextView name = (TextView) convertView.findViewById(R.id.nameTextView);
        TextView alamat = (TextView) convertView.findViewById(R.id.alamatTextView);
        TextView jenisKelamin = (TextView) convertView.findViewById(R.id.jenisKelaminTextView);
        TextView lokasiUser = (TextView) convertView.findViewById(R.id.lokasiTextView);
        TextView noHp = (TextView) convertView.findViewById(R.id.noHpTextView);

        DataBeasiswaModel data = items.get(position);
        id.setText("Nomor Pendaftaran : " + data.getId());
        name.setText("Nama Pendaftar : " + data.getNama());
        alamat.setText("Alamat Pendaftar : " + data.getAlamat());
        jenisKelamin.setText("Jenis Kelamin Pendaftar : " + data.getJenisKelamin());
        lokasiUser.setText("Lokasi Pendaftar : " + data.getLokasiUser());
        noHp.setText("Nomor Handphone Pendaftar : " + data.getNoHp());

        Glide.with(convertView).load(data.getUploadPhoto()).into(uploadResult);
        return convertView;


    }
}
