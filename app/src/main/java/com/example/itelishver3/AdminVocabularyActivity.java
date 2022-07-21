package com.example.itelishver3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.itelishver3.databinding.ActivityMainBinding;
import com.example.itelishver3.mapclass.VocabularyList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AdminVocabularyActivity extends AppCompatActivity {

    private RecyclerView rcvVocabulary;
    private Button btnAdd, btnBack;
    private ArrayList<VocabularyList> vocabularyLists = new ArrayList<>();
    private DatabaseReference databaseReference;
    private MyAdapter myAdapter;
    private Uri uriAudio;
    String idUrlAudio = "";
    String idUri = "";
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_vocabulary);

        Mapping();
        UploadRecyclerView();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminVocabularyActivity.this, AdminActivity.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUploadAndAddVocabulary();
            }
        });


    }

    private void Mapping(){
        rcvVocabulary = (RecyclerView) findViewById(R.id.rcv_vocabulary_admin_activity);
        btnAdd = (Button) findViewById(R.id.btn_add_vocabulary_admin);
        btnBack = (Button) findViewById(R.id.btn_back_vocabulary_admin);
    }

    private void UploadRecyclerView(){
        databaseReference = FirebaseDatabase.getInstance().getReference("VocabularyList").child("TVCN").child("Beginner");
        rcvVocabulary.setHasFixedSize(true);
        rcvVocabulary.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(this, vocabularyLists, new MyAdapter.ItemClickListener() {
            @Override
            public void onItemClick(VocabularyList vocabularyList) {
                Dialog dialog = new Dialog(AdminVocabularyActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setContentView(R.layout.dialog_upload_vocabulary_admin);
                EditText edtVocab = dialog.findViewById(R.id.edt_vocab_upload_vocabulary_admin_dialog);
                edtVocab.setText(vocabularyList.getVocab());
                EditText edtPronounce = dialog.findViewById(R.id.edt_pronounce_upload_vocabulary_admin_dialog);
                edtPronounce.setText(vocabularyList.getPronounce());
                EditText edtMean = dialog.findViewById(R.id.edt_mean_upload_vocabulary_admin_dialog);
                edtMean.setText(vocabularyList.getExample());
                dialog.show();
                dialog.findViewById(R.id.btn_update_dialog_edit_vocabulary_admin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myAdapter.notifyDataSetChanged();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        databaseReference = database.getReference(VocabularyList.class.getSimpleName());

                        //Upload Firestore TVCN Beginner
                        DatabaseReference beginnerRef = databaseReference.child("TVCN").child("Beginner");
                        VocabularyList vocabularyList = new VocabularyList();
                        EditText edtVocab = dialog.findViewById(R.id.edt_vocab_upload_vocabulary_admin_dialog);
                        vocabularyList.setVocab(edtVocab.getText().toString());

                        EditText edtPronounce = dialog.findViewById(R.id.edt_pronounce_upload_vocabulary_admin_dialog);
                        vocabularyList.setPronounce(edtPronounce.getText().toString());

                        EditText edtMean = dialog.findViewById(R.id.edt_mean_upload_vocabulary_admin_dialog);
                        vocabularyList.setExample (edtMean.getText().toString());

                        HashMap VocabUpdate = new HashMap();
                        VocabUpdate.put("vocab", vocabularyList.getVocab());
                        VocabUpdate.put("pronounce", vocabularyList.getPronounce());
                        VocabUpdate.put("example", vocabularyList.getExample());
                        VocabUpdate.put("audioUrl", idUrlAudio);
                        beginnerRef.child(edtVocab.getText().toString()).updateChildren(VocabUpdate).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                Toast.makeText(AdminVocabularyActivity.this, "Upload success", Toast.LENGTH_SHORT).show();
                                myAdapter.notifyDataSetChanged();
                                AdminVocabularyActivity.this.recreate();
                            }
                        });
                    }
                });
                dialog.findViewById(R.id.btn_back_dialog_upload_vocabulary_admin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btn_delete_dialog_edit_vocabulary_admin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        databaseReference = database.getReference(VocabularyList.class.getSimpleName());

                        //Upload Firestore TVCN Beginner
                        DatabaseReference beginnerRef = databaseReference.child("TVCN").child("Beginner");
                        beginnerRef.child(edtVocab.getText().toString()).removeValue().addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                Toast.makeText(AdminVocabularyActivity.this, "Remove success", Toast.LENGTH_SHORT).show();
                                myAdapter.notifyDataSetChanged();
                                AdminVocabularyActivity.this.recreate();
                            }
                        });
                    }
                });
            }
        });
        rcvVocabulary.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    VocabularyList vocabularyList = dataSnapshot.getValue(VocabularyList.class);
                    vocabularyLists.add(vocabularyList);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void DialogUploadAndAddVocabulary(){
        Dialog dialog = new Dialog(this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setContentView(R.layout.dialog_upload_vocabulary_admin);
        dialog.show();
        dialog.findViewById(R.id.btn_update_dialog_edit_vocabulary_admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference(VocabularyList.class.getSimpleName());

                //Upload Firestore TVCN Beginner
                DatabaseReference beginnerRef = databaseReference.child("TVCN").child("Beginner");
                VocabularyList vocabularyList = new VocabularyList();
                EditText edtVocab = dialog.findViewById(R.id.edt_vocab_upload_vocabulary_admin_dialog);
                vocabularyList.setVocab(edtVocab.getText().toString());

                EditText edtPronounce = dialog.findViewById(R.id.edt_pronounce_upload_vocabulary_admin_dialog);
                vocabularyList.setPronounce(edtPronounce.getText().toString());

                EditText edtMean = dialog.findViewById(R.id.edt_mean_upload_vocabulary_admin_dialog);
                vocabularyList.setExample (edtMean.getText().toString());


                beginnerRef.child(""+edtVocab.getText().toString()).setValue(vocabularyList).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Toast.makeText(AdminVocabularyActivity.this, "Add success", Toast.LENGTH_SHORT).show();
                        myAdapter.notifyDataSetChanged();
                        AdminVocabularyActivity.this.recreate();
                    }
                });
            }
        });

        dialog.findViewById(R.id.btn_audio_dialog_upload_vocabulary_admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAudio();
                uploadAudio(uriAudio);

                        //getFileExtension(uriAudio);
                //Toast.makeText(AdminVocabularyActivity.this, "Audio Click", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.findViewById(R.id.btn_back_dialog_upload_vocabulary_admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    private void uploadAudio(Uri uri){
            Toast.makeText(AdminVocabularyActivity.this, "Please Select Audio", Toast.LENGTH_SHORT).show();
            StorageReference fileRef = storageReference.child(System.currentTimeMillis() +"."+getFileExtension(uri));
            fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(@NonNull Uri uri) {
//                            VocabularyList vocabularyList = new VocabularyList()
                            idUrlAudio = databaseReference.push().getKey();
                            idUri = uri.toString();
                            Toast.makeText(AdminVocabularyActivity.this, idUri.toString() + idUrlAudio.toString() + "", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    Toast.makeText(AdminVocabularyActivity.this, "Uploading Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminVocabularyActivity.this, "Uploading Failed", Toast.LENGTH_SHORT).show();
                }
            });
//            progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading File...");
//            progressDialog.show();
//
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
//            Date now = new Date();
//            String fileName = simpleDateFormat.format(now);
//            storageReference = FirebaseStorage.getInstance().getReference("audio/"+fileName);
//
//            storageReference.putFile(uriAudio).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(AdminVocabularyActivity.this, "Upload Succeesfully",Toast.LENGTH_SHORT).show();
//                    if(progressDialog.isShowing())
//                        progressDialog.dismiss();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    if(progressDialog.isShowing()){
//                        progressDialog.dismiss();
//                    }
//                    Toast.makeText(AdminVocabularyActivity.this, "Failed to Upload",Toast.LENGTH_SHORT).show();
//                }
//            });
 //       }

    }


    private String getFileExtension(Uri Muri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(Muri));
    }

    private void selectAudio(){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("audio/*");
        startActivityForResult(galleryIntent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode == RESULT_OK && data != null){
            uriAudio = data.getData();
        }
    }

}