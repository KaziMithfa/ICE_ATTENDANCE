package com.example.ice_attendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentsSettings extends AppCompatActivity {

    Toolbar studentToolbar;
    private TextView closeTextView,updateTextView;
    private CircleImageView profile;
    private TextView  updateProfileTextView;
    private EditText emailEditText,phoneEditText;
    private Uri imageUri;
    private StorageReference storageReference;
    private StorageTask uploadTask;
    private DatabaseReference studentRef;

    private String selectedBatch,selectedTerm,id;
    private  String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_settings);

        selectedBatch = getIntent().getStringExtra("batch");
        selectedTerm = getIntent().getStringExtra("term");
        id = getIntent().getStringExtra("key");

        storageReference = FirebaseStorage.getInstance().getReference().child("Student pictures");
        studentRef = FirebaseDatabase.getInstance().getReference()
                .child("Batches").child(selectedBatch).child("Students")
                .child(selectedTerm).child(id);

        studentToolbar = findViewById(R.id.studenttoolbarsettings);
        closeTextView = findViewById(R.id.studentclose_settings_btn);
        updateTextView = findViewById(R.id.studentupdate_settings_btn);
        profile = findViewById(R.id.studentimageupdate);
        updateProfileTextView = findViewById(R.id.updatesettingstextId);
        emailEditText = findViewById(R.id.studentEmailsetting);
        phoneEditText = findViewById(R.id.studentphonesetting);

        StudentDisplayInformation(profile,emailEditText,phoneEditText);

        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentsSettings.this,StudentHome.class);
                intent.putExtra("key",id);
                intent.putExtra("batch",selectedBatch);
                intent.putExtra("term",selectedTerm);
                startActivity(intent);
                finish();
            }
        });



        updateProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";
                CropImage.activity(imageUri)
                        .setAspectRatio(1,1).start(StudentsSettings.this);




            }
        });

        updateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checker.equals("clicked")){
                    userInfosaved();
                }
                else{
                    userInfosavedOnly();
                }
            }
        });


    }

   

    private void userInfosavedOnly() {



        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        HashMap<String,Object>studenhashMap = new HashMap<>();
        studenhashMap.put("email",email);
        studenhashMap.put("phone",phone);
        studentRef.updateChildren(studenhashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(StudentsSettings.this, "your profile is updated....", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StudentsSettings.this,StudentHome.class);
                    intent.putExtra("key",id);
                    intent.putExtra("batch",selectedBatch);
                    intent.putExtra("term",selectedTerm);
                    startActivity(intent);
                    finish();
                }
            }
        });




    }

    private void userInfosaved() {



        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please, write your email first", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please, write your phone number....", Toast.LENGTH_SHORT).show();
        }

        else{

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Upload Profile....");
            progressDialog.setMessage("please wait while we are checking the credentials....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            if(imageUri != null){
                StorageReference fileRef = storageReference.child(id+".jpg");
                uploadTask = fileRef.putFile(imageUri);

                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {

                        if(!task.isSuccessful()){
                            throw  task.getException();
                        }
                        return fileRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri downloadUrl = task.getResult();
                            String myUrl = downloadUrl.toString();

                            HashMap<String,Object>hashMap = new HashMap<>();
                            hashMap.put("email",email);
                            hashMap.put("phone",phone);
                            hashMap.put("image",myUrl);

                            studentRef.updateChildren(hashMap);
                            Toast.makeText(StudentsSettings.this, "Profile is updated successfully...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StudentsSettings.this,StudentHome.class);
                            intent.putExtra("key",id);
                            intent.putExtra("batch",selectedBatch);
                            intent.putExtra("term",selectedTerm);
                            startActivity(intent);
                            finish();
                        }

                    }
                });

            }

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            profile.setImageURI(imageUri);

        }

        else{
            Toast.makeText(this, "Please, try again!!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void StudentDisplayInformation(CircleImageView profile ,EditText emailEditText, EditText phoneEditText){

        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference()
                .child("Batches").child(selectedBatch).child("Students")
                .child(selectedTerm).child(id);
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    if(snapshot.hasChild("image")){
                        String image = snapshot.child("image").getValue().toString();
                        String email = snapshot.child("email").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();
                        Picasso.get().load(image).into(profile);
                        emailEditText.setText(email);
                        phoneEditText.setText(phone);
                    }
                    else{
                        String email = snapshot.child("email").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();

                        emailEditText.setText(email);
                        phoneEditText.setText(phone);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}