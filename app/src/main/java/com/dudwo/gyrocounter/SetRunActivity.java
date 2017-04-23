package com.dudwo.gyrocounter;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;

public class SetRunActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "NewPostActivity";

    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    FirebaseDatabase db;
    DatabaseReference dbref;

    private Iterator<DataSnapshot> category;
    private ArrayList<Work> WorkList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_run);

        findViewById(R.id.run_btn).setOnClickListener(this);


        // WorkList = new ArrayList<Map<String,Object>>();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        final String uid = user.getUid();

        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("work");

        WorkList = new ArrayList<Work>();

        dbref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value

                        category = dataSnapshot.getChildren().iterator();
                        //category = dataSnapshot.child("category").getChildren().iterator();
                        //type = dataSnapshot.child("category").child("type").getChildren().iterator();
                        // ...

                        while (category.hasNext()) {
                            DataSnapshot DS = category.next();
                            String cate = "";
                            String type = "";
                            int max, min;
                            if ((DS.child("id").getValue()).equals(uid)) {
                                cate = (String) DS.child("category").getValue();
                                type = (String) DS.child("type").getValue();
                                max = Integer.parseInt(DS.child("max").getValue().toString());
                                min = Integer.parseInt(DS.child("min").getValue().toString());

                                Work work = new Work(uid,cate,type,max,min);
                                WorkList.add(work);
                                Log.d("VALUE", String.valueOf(DS.getValue()));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
        /*
        //If no data
        if (WorkList.isEmpty()) {
            Toast.makeText(SetRunActivity.this, "NO DATA!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        ListView listview = (ListView)findViewById(R.id.listview);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_set_run, (ArrayList<Work>)WorkList) ;
        listview.setAdapter(adapter) ;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                // get TextView's Text.
                String strText = (String) parent.getItemAtPosition(position) ;

                // TODO : use strText

            }
        }) ;
        */
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.run_btn) {
            Intent intent = new Intent(this, RunActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
