package com.example.ice_attendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherSettings extends AppCompatActivity {


    private CircleImageView profileImageView;
    private EditText phoneEditText,designationEditText,emailEditText;
    private TextView profileChangeTxtBtn,closeTxtBtn,saveTextBtn;
    String checker = "";
    private StorageReference storageprofilepictureRef;
    private StorageTask uploadTask;
    private Uri imageUri;
    private String myUrl = "";
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_settings);

        profileImageView = findViewById(R.id.settings_profile_image);
        phoneEditText = findViewById(R.id.settings_phone_number);
        designationEditText = findViewById(R.id.settings_designation);
        emailEditText= findViewById(R.id.settings_email);
        profileChangeTxtBtn = findViewById(R.id.profile_image_change_btn);
        closeTxtBtn = findViewById(R.id.close_settings_btn);
        saveTextBtn = findViewById(R.id.update_settings_btn);

        key = getIntent().getStringExtra("token");

        storageprofilepictureRef = FirebaseStorage.getInstance().getReference().child("Teacher Pictures");
        teacherInfoDisplay(profileImageView,phoneEditText,designationEditText,emailEditText);

        closeTxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TeacherSettings.this,TeacherHomeActivity.class);
                intent.putExtra("Key",key);
                startActivity(intent);
                finish();
            }
        });

        saveTextBtn.setOnClickListener(new View.OnClickListener() {
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

        profileChangeTxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checker = "clicked";
                CropImage.activity(imageUri).setAspectRatio(1,1)
                        .start(TeacherSettings.this);



            }
        });







    }

    private void userInfosavedOnly() {
        DatabaseReference teachersRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child(key);
        HashMap<String,Object> teacherMap = new HashMap<>();
        teacherMap.put("phone",phoneEditText.getText().toString());
        teacherMap.put("designation",designationEditText.getText().toString());
        teacherMap.put("email",emailEditText.getText().toString());
        teachersRef.updateChildren(teacherMap);

        Intent intent = new Intent(TeacherSettings.this,TeacherHomeActivity.class);
        intent.putExtra("Key",key);
        Toast.makeText(this, "Your information is updated successfully.....", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();





    }

    private void userInfosaved() {
        if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this, "Phone number is mandatory.....", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(designationEditText.getText().toString())){
            Toast.makeText(this, "Designation is mandatory.....", Toast.LENGTH_SHORT).show();

        }

        else if(TextUtils.isEmpty(emailEditText.getText().toString())){
            Toast.makeText(this, "Email is mandatory.....", Toast.LENGTH_SHORT).show();
        }

        else{
            uploadImage();

        }



    }

    private void uploadImage() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Upload Profile....");
        progressDialog.setMessage("please wait while we are checking the credentials....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if(imageUri != null){
            StorageReference fileRef = storageprofilepictureRef.child(key+".jpg");
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
                        myUrl = downloadUrl.toString();

                        DatabaseReference teachersRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child(key);
                        HashMap<String,Object>teacherMap = new HashMap<>();
                        teacherMap.put("phone",phoneEditText.getText().toString());
                        teacherMap.put("designation",designationEditText.getText().toString());
                        teacherMap.put("email",emailEditText.getText().toString());
                        teacherMap.put("image",myUrl);

                        teachersRef.updateChildren(teacherMap);

                        Toast.makeText(TeacherSettings.this, "Profile is updated successfully...", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(TeacherSettings.this,TeacherHomeActivity.class);
                        intent.putExtra("Key",key);
                        startActivity(intent);

                        finish();


                    }

                    else{
                        progressDialog.dismiss();
                        Toast.makeText(TeacherSettings.this, "Error!!!!", Toast.LENGTH_SHORT).show();
                    }

                }
            });




        }





    }

    private void teacherInfoDisplay(CircleImageView profileImageView, EditText phoneEditText, EditText designationEditText, EditText passwordEditText) {

        DatabaseReference teachersRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child(key);
        teachersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    Teacher teacher = snapshot.getValue(Teacher.class);
                    phoneEditText.setText(teacher.getPhone());
                    designationEditText.setText(teacher.getDesignation());
                    emailEditText.setText(teacher.getEmail());

                    String image = teacher.getImage();
                    Picasso.get().load(image).into(profileImageView);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data!= null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            profileImageView.setImageURI(imageUri);
        }
        else{
            Toast.makeText(this, "Please ,try again !!!!!", Toast.LENGTH_SHORT).show();
        }
    }
}