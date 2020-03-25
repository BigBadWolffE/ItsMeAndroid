package com.indocyber.itsmeandroid.viewremastered.AddKartuPersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.ImageCardModel;
import com.indocyber.itsmeandroid.utilities.GlobalVariabel;
import com.indocyber.itsmeandroid.utilities.UtilitiesCore;
import com.indocyber.itsmeandroid.viewremastered.loginandregister.SetPinActivityRemastered;

import java.io.IOException;
import java.util.Calendar;

public class AddKartuPersonal extends AppCompatActivity {

    private TextView lblmasaBerlaku;
    private TextView lbltipeSIM;
    private TextView lblNPWP;
    private TextView lblSIM;
    private TextView txtKantorPajak;
    private TextView txtJenisKelamin;
    private TextView txtKewarganegaraan;
    private TextView txtPekerjaan;
    private TextView txtStatus;
    private TextView txtAgama;
    private RelativeLayout layoutTglLahir;
    private RadioGroup rdoJenisKelamin;
    private TextView lblNik;
    private EditText txtmasaBerlaku;
    private EditText txtNPWP;
    private EditText txtSIM;
    private EditText txtNik;
    private EditText mKantorPajak;
    private EditText mCardHolderInput;
    private EditText mExpiryInput;
    private EditText mBirthDate;
    private ImageView mCardImage;
    private int mCardImageResource;
    private Spinner spnTipeSIM;
    private Spinner mTipeKartu;
    private Spinner spnAgama;
    private Spinner spnStatus;
    private Spinner spnPekerjaan;
    private Spinner spnKewarganegaraan;
    private Spinner spnKelurahan;
    private Spinner spnKecamatan;
    private ImageView ktpImage;
    private static final int GET_KTP_PICTURE_FROM_GALLERY = 0;
    private static final int GET_KTP_PICTURE_FROM_CAMERA = 1;
    private final static String[] SIM_LIST = {"Jenis SIM","SIM A", "SIM B", "SIM C"};
    private final static String[] MONTH_LIST = {"Januari", "Februari", "Maret", "April",
            "Mei", "Juni", "Juli", "Agustus", "September",
            "Oktober", "November", "Desember"};
    private static final String[] TIPE_KARTU_OPTIONS = {"Pilih Kartu","KTP", "NPWP", "SIM"};
    private static final String[] AGAMA_OPTIONS =
            { "Pilih Agama", "Islam", "Hindu", "Buddha", "Kristen", "Katolik", "Konghuchu" };
    private static final String[] STATUS_OPTIONS =
            { "Pilih Status", "Lajang", "Menikah" };
    private static final String[] PEKERJAAN_OPTIONS =
            { "Pilih Pekerjaan", "Karyawan", "Wirausaha" };
    private static final String[] KEWARGANEGARAAN_OPTIONS =
            { "Pilih Status", "WNI", "WNA" };
    private static final String[] KELURAHAN_OPTIONS =
            { "Pilih Status", "Tanjung Duren", "Duri Kepa" };
    private static final String[] KECAMATAN_OPTIONS =
            { "Pilih Status", "Grogol Petamburan", "Kebon Jeruk" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kartu_personal);
        createToolbar();
        initializeField();
        initializeButton();
        System.out.println("isi tipe kartu : " + mTipeKartu.getSelectedItem().toString());
        //cekKartu();
    }

    private void initializeField() {
        lbltipeSIM = findViewById(R.id.lblTipeSIM);
        txtNPWP = findViewById(R.id.txtNPWP);
        txtSIM = findViewById(R.id.txtSIM);
        txtNik = findViewById(R.id.txtNik);
        txtmasaBerlaku = findViewById(R.id.txtMasaBerlaku);
        txtKantorPajak = findViewById(R.id.txtKantorPajak);
        mKantorPajak = findViewById(R.id.txtAlamatKantorPajak);
        txtKewarganegaraan = findViewById(R.id.txtKewarganegaraan);
        txtPekerjaan = findViewById(R.id.txtPekerjaan);
        txtStatus = findViewById(R.id.txtStatus);
        txtAgama = findViewById(R.id.txtAgama);
        layoutTglLahir = findViewById(R.id.layoutTglLahir);
        txtJenisKelamin = findViewById(R.id.txtJenisKelamin);
        rdoJenisKelamin = findViewById(R.id.radioJenisKelamin);
        lblNPWP = findViewById(R.id.lblNPWP);
        lblNik = findViewById(R.id.lblNik);
        lblSIM = findViewById(R.id.lblSIM);
        lblmasaBerlaku = findViewById(R.id.lblMasaBerlaku);
        mTipeKartu = findViewById(R.id.spnTipeKartu);
        mTipeKartu.setAdapter(new ArrayAdapter<>(this,
                R.layout.spinner_item_text, TIPE_KARTU_OPTIONS));
        mCardHolderInput = findViewById(R.id.txtCardHolder);
        mBirthDate = findViewById(R.id.txtTanggalLahir);
        mBirthDate.setOnFocusChangeListener((view, isFocus) -> {
            if (isFocus) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR) - 18;
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        R.style.Theme_MaterialComponents_Light_Dialog_Alert,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String dateValue = dayOfMonth + " " + MONTH_LIST[monthOfYear] + " " + year;
                                mBirthDate.setText(dateValue);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        spnTipeSIM = findViewById(R.id.spnTipeSIM);
        spnTipeSIM.setAdapter(new ArrayAdapter<>(this,
                R.layout.spinner_item_text, SIM_LIST));
        spnAgama = findViewById(R.id.spnAgama);
        spnAgama.setAdapter(new ArrayAdapter<>(this,
                R.layout.spinner_item_text, AGAMA_OPTIONS));
        spnStatus = findViewById(R.id.spnStatus);
        spnStatus.setAdapter(new ArrayAdapter<>(this,
                R.layout.spinner_item_text, STATUS_OPTIONS));
        spnPekerjaan = findViewById(R.id.spnPekerjaan);
        spnPekerjaan.setAdapter(new ArrayAdapter<>(this,
                R.layout.spinner_item_text, PEKERJAAN_OPTIONS));
        spnKewarganegaraan = findViewById(R.id.spnKewarganegaraan);
        spnKewarganegaraan.setAdapter(new ArrayAdapter<>(this,
                R.layout.spinner_item_text, KEWARGANEGARAAN_OPTIONS));
        spnKelurahan = findViewById(R.id.spnKelurahanKtp);
        spnKelurahan.setAdapter(new ArrayAdapter<>(this,
                R.layout.spinner_item_text, KELURAHAN_OPTIONS));
        spnKelurahan = findViewById(R.id.spnKecamatanKtp);
        spnKelurahan.setAdapter(new ArrayAdapter<>(this,
                R.layout.spinner_item_text, KECAMATAN_OPTIONS));
        mExpiryInput = findViewById(R.id.txtExpireDate);
        ktpImage = findViewById(R.id.fotoKtpResult);
        ImageView pictureButton = findViewById(R.id.addPicture);
        pictureButton.setOnClickListener(view -> {
            startTakePhotoDialog();
        });
    }

    private void cekKartu(){
        String sTipeKartu = String.valueOf(mTipeKartu.getSelectedItemPosition());
        System.out.println("isi kartu : "+sTipeKartu);
        if(sTipeKartu.equals("1")){
            System.out.println("kartu KTP");
            lblNik.setVisibility(View.VISIBLE);
            txtNik.setVisibility(View.VISIBLE);
            txtJenisKelamin.setVisibility(View.VISIBLE);
            rdoJenisKelamin.setVisibility(View.VISIBLE);
            layoutTglLahir.setVisibility(View.VISIBLE);
            txtAgama.setVisibility(View.VISIBLE);
            spnAgama.setVisibility(View.VISIBLE);
            txtStatus.setVisibility(View.VISIBLE);
            spnStatus.setVisibility(View.VISIBLE);
            txtPekerjaan.setVisibility(View.VISIBLE);
            spnPekerjaan.setVisibility(View.VISIBLE);
            txtKewarganegaraan.setVisibility(View.VISIBLE);
            spnKewarganegaraan.setVisibility(View.VISIBLE);

            lblNPWP.setVisibility(View.GONE);
            txtNPWP.setVisibility(View.GONE);
            txtKantorPajak.setVisibility(View.GONE);
            mKantorPajak.setVisibility(View.GONE);
            lblSIM.setVisibility(View.GONE);
            txtSIM.setVisibility(View.GONE);
            lbltipeSIM.setVisibility(View.GONE);
            spnTipeSIM.setVisibility(View.GONE);
            lblmasaBerlaku.setVisibility(View.GONE);
            txtmasaBerlaku.setVisibility(View.GONE);
        }
        else if(sTipeKartu.equals("2")){
            System.out.println("kartu NPWP");
            lblNPWP.setVisibility(View.VISIBLE);
            txtNPWP.setVisibility(View.VISIBLE);
            txtKantorPajak.setVisibility(View.VISIBLE);
            mKantorPajak.setVisibility(View.VISIBLE);
            lblNik.setVisibility(View.GONE);
            txtNik.setVisibility(View.GONE);
            txtJenisKelamin.setVisibility(View.GONE);
            rdoJenisKelamin.setVisibility(View.GONE);
            layoutTglLahir.setVisibility(View.GONE);
            txtAgama.setVisibility(View.GONE);
            spnAgama.setVisibility(View.GONE);
            txtStatus.setVisibility(View.GONE);
            spnStatus.setVisibility(View.GONE);
            txtPekerjaan.setVisibility(View.GONE);
            spnPekerjaan.setVisibility(View.GONE);
            txtKewarganegaraan.setVisibility(View.GONE);
            spnKewarganegaraan.setVisibility(View.GONE);
            lblmasaBerlaku.setVisibility(View.GONE);
            txtmasaBerlaku.setVisibility(View.GONE);
            lblSIM.setVisibility(View.GONE);
            txtSIM.setVisibility(View.GONE);
        }
        else if(sTipeKartu.equals("3")){
            System.out.println("kartu SIM");
            lblSIM.setVisibility(View.VISIBLE);
            txtSIM.setVisibility(View.VISIBLE);
            lbltipeSIM.setVisibility(View.VISIBLE);
            spnTipeSIM.setVisibility(View.VISIBLE);
            lblmasaBerlaku.setVisibility(View.VISIBLE);
            txtmasaBerlaku.setVisibility(View.VISIBLE);
            layoutTglLahir.setVisibility(View.VISIBLE);
            lblNik.setVisibility(View.GONE);
            txtNik.setVisibility(View.GONE);
            lblNPWP.setVisibility(View.GONE);
            txtNPWP.setVisibility(View.GONE);
            txtJenisKelamin.setVisibility(View.GONE);
            rdoJenisKelamin.setVisibility(View.GONE);
            txtAgama.setVisibility(View.GONE);
            spnAgama.setVisibility(View.GONE);
            txtStatus.setVisibility(View.GONE);
            spnStatus.setVisibility(View.GONE);
            txtKewarganegaraan.setVisibility(View.GONE);
            spnKewarganegaraan.setVisibility(View.GONE);
            txtKantorPajak.setVisibility(View.GONE);
            mKantorPajak.setVisibility(View.GONE);
        }
        else{

        }
    }

    private void startTakePhotoDialog() {
        final AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this,
                R.style.Theme_MaterialComponents_Light_Dialog_Alert);
        myAlertDialog.setTitle("Select Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        pickFromGallery();
                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        takeFromCamera();
                    }
                });

        myAlertDialog.show();
    }

    private void pickFromGallery() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent, GET_KTP_PICTURE_FROM_GALLERY);
    }

    private void takeFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, GET_KTP_PICTURE_FROM_CAMERA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case GET_KTP_PICTURE_FROM_CAMERA:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ktpImage.setImageBitmap(imageBitmap);
                    ktpImage.setVisibility(View.VISIBLE);

//                    fotoBase64 = UtilitiesCore.encodeToBase64Only(imageBitmap, extension);
                    break;
                case GET_KTP_PICTURE_FROM_GALLERY:
                    Uri uri = data.getData();
                    String filePath = uri.getPath();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        UtilitiesCore.loadImage(ktpImage, bitmap, this);
                        ktpImage.setImageBitmap(bitmap);
//                        fotoBase64  = UtilitiesCore.encodeToBase64Only(bitmap, extension);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    Log.d("AddKartuPersonal", "Canceled taking picture");
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private  void createToolbar() {
        AppBarLayout appbar = findViewById(R.id.actionBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.toolbar_text);
        VectorDrawable backButton = (VectorDrawable) getDrawable(R.drawable.ic_ico_arrow_back);
        int iconDimension = (int) getResources().getDimension(R.dimen._20sdp);
        Drawable resizedBackButton =
                UtilitiesCore.resizeDrawable(backButton, this, iconDimension , iconDimension);
        textView.setText("Tambah Kartu Personal");
        textView.setAllCaps(false);
        toolbar.setNavigationIcon(resizedBackButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0f);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeButton() {
        Button addMemberCardButton;
        addMemberCardButton = findViewById(R.id.btnAddMember);
        addMemberCardButton.setOnClickListener(view -> submit());

        mTipeKartu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                // position is what you want i think
                if(position == 0){

                }else{
//                    String sTipe_Kartu = TIPE_KARTU_OPTIONS.get(position - 1)
////                    System.out.println("Tipe Kartu : " + sTipe_Kartu);
                    //editor.putString(StaticValues.sWilayah_Code, sWilayah_Code);
                    //editor.commit();
                    cekKartu();

                }


            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }
        });



    }

    private boolean formIsValid(){
        return true;
    }

    private void submit() {
        if(!formIsValid()){
            UtilitiesCore.buildAlertDialog(
                    this,
                    getString(R.string.form_incomplete_warning),
                    R.drawable.ic_invalid,
                    null
            );
            return;
        }

        CheckBox termsAgreement = findViewById(R.id.chkTermsAgreement);
        if (!termsAgreement.isChecked()) {
            UtilitiesCore.buildAlertDialog(
                    this,
                    "Please read and indicate your acceptance of the site's Terms of Service",
                    R.drawable.ic_invalid,
                    null
            );
            return;
        }

        Intent intent = new Intent(this, SetPinActivityRemastered.class);
        intent.putExtra("parentCode", GlobalVariabel.TAMBAH_PERSONAL);
        startActivity(intent);
    }

}
