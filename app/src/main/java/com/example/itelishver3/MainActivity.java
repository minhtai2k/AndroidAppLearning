package com.example.itelishver3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itelishver3.mapclass.QuestionList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itelishver3.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private String TAG = "DATA";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        uploadUserLogin();
        LogoutAccount();
        //UploadAndReadFirebase();
        ChangeToVocabulary();
        ChangeToConversation();
        //uploadFirebaseTVCNDatabase();

        textView = binding.navView.findViewById(R.id.tv_nav_header_name);

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


//    public void uploadFirebaseTVCNDatabase(){

//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference(QuestionList.class.getSimpleName());
//
//        //Upload Firestore TVCN Beginner
//        DatabaseReference beginnerRef = ref.child("TVCN");
//        Map<String, Object> questionBeginner = new HashMap<>();
//        QuestionList questionB1 = new QuestionList("What is a CODE", "Minh Tai", "Hong Van", "Dinh Anh", "Thanh Phet", "Minh Tai", "");
//        QuestionList questionB2 = new QuestionList("What is a CODE", "Minh Tai", "Hong Van", "Dinh Anh", "Thanh Phet", "Minh Tai", "");
//        questionBeginner.put("question1", questionB1);
//        questionBeginner.put("question2", questionB2);
//
//        beginnerRef.child("Beginner").setValue(questionBeginner);
//
//        //Upload Firestore TVCN Intermediate
//        DatabaseReference intermediateRef = ref.child("TVCN");
////        Map<String, Object> questionIntermediate= new HashMap<>();
//        QuestionList questionI1 = new QuestionList("What is a CODE", "Minh Tai", "Hong Van", "Dinh Anh", "Thanh Phet", "Minh Tai", "");
//        QuestionList questionI2 = new QuestionList("What is a CODE", "Minh Tai", "Hong Van", "Dinh Anh", "Thanh Phet", "Minh Tai", "");
//        questionBeginner.put("question1", questionI1);
//        questionBeginner.put("question2", questionI2);
//
//        beginnerRef.child("Intermediate").setValue(questionBeginner);
//
//        //Upload Firestore TVCN Advanced
//        DatabaseReference advancedRef = ref.child("TVCN");
////        Map<String, Object> questionAdvanced= new HashMap<>();
//        QuestionList questionA1 = new QuestionList("What is a CODE", "Minh Tai", "Hong Van", "Dinh Anh", "Thanh Phet", "Minh Tai", "");
//        QuestionList questionA2 = new QuestionList("What is a CODE", "Minh Tai", "Hong Van", "Dinh Anh", "Thanh Phet", "Minh Tai", "");
//        questionBeginner.put("question1", questionI1);
//        questionBeginner.put("question2", questionI2);
//
//        beginnerRef.child("Advanced").setValue(questionBeginner);
//    }

    private void uploadUserLogin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.tv_nav_header_name);
        TextView navEmail = (TextView) headerView.findViewById(R.id.textView);
        navEmail.setText(""+user.getEmail());
        if(navEmail.getText().equals("itelish71@admin21.com")){
            startActivity(new Intent(MainActivity.this, AdminActivity.class));
            finish();
        }
//        navUsername.setText(""+user.getDisplayName());
    }

    private void ChangeToVocabulary(){
        CardView cardView = (CardView) findViewById(R.id.cv_tvcn_home_fragment);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VocabularyActivity.class);
//                intent.putExtra("K1_Main_Vocab","Từ vựng");
                startActivity(intent);
                finish();
            }
        });

    }

    private void ChangeToConversation(){
        CardView cardView = (CardView) findViewById(R.id.cv_htcn_home_fragment);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConversationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void LogoutAccount(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            CreateAlertDialog();
            return true;
        });
    }

    private void CreateAlertDialog(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
//Thiết lập tiêu đề
        b.setTitle("Xác nhận");
        b.setMessage("Bạn có muốn đăng xuất không?");
// Nút Ok
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                dialog.cancel();
            }
        });

        b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
//Tạo dialog
        AlertDialog al = b.create();

//Hiển thị diaglog
        al.show();
        al.setCancelable(false);
    }

//    private void UploadAndReadFirebase(){
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Map<String, Object> question = new HashMap<>();
//        QuestionList question1 = new QuestionList("What is a CODE", "Minh Tai", "Hong Van", "Dinh Anh", "Thanh Phet", "Minh Tai", "");
//        QuestionList question2 = new QuestionList("What is a CODE", "Minh Tai", "Hong Van", "Dinh Anh", "Thanh Phet", "Minh Tai", "");
//        question.put("Question1", question1);
//        question.put("Question2", question2);
//
//        db.collection("QuestionList").document("TVCN").set(question).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        db.collection("QuestionList")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//    }

}