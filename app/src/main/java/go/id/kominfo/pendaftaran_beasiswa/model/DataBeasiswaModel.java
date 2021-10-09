package go.id.kominfo.pendaftaran_beasiswa.model;

public class DataBeasiswaModel {
    private String id,nama, alamat, noHp,jenisKelamin ,lokasiUser, uploadPhoto;

    public DataBeasiswaModel() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getLokasiUser() {
        return lokasiUser;
    }

    public void setLokasiUser(String lokasiUser) {
        this.lokasiUser = lokasiUser;
    }

    public String getUploadPhoto() {
        return uploadPhoto;
    }

    public void setUploadPhoto(String uploadPhoto) {
        this.uploadPhoto = uploadPhoto;
    }

    public DataBeasiswaModel(String id, String nama, String alamat, String noHp, String jenisKelamin, String lokasiUser, String uploadPhoto) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.noHp = noHp;
        this.jenisKelamin = jenisKelamin;
        this.lokasiUser = lokasiUser;
        this.uploadPhoto = uploadPhoto;
    }

    public String getAlamat() {
        return alamat;
    }

}
