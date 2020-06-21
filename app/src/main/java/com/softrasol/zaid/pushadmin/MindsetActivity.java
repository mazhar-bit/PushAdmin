package com.softrasol.zaid.pushadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.softrasol.zaid.pushadmin.Adapters.PointsAdapter;
import com.softrasol.zaid.pushadmin.Helper.UploadMindSetData;
import com.softrasol.zaid.pushadmin.Helper.UploadVideoData;
import com.softrasol.zaid.pushadmin.Model.PointsModel;

import java.util.ArrayList;
import java.util.List;

public class MindsetActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private List<PointsModel> list = new ArrayList<>();
    private TextInputEditText mTxtTitle, mTxtDesription;
    private String title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindset);

        widgetsInitailization();

    }


    private void widgetsInitailization() {
        mRecyclerView = findViewById(R.id.recyclerview_breathwork);
        mTxtTitle = findViewById(R.id.txt_breathwork_title);
        mTxtDesription = findViewById(R.id.txt_mindset_description);
    }

    public void BackClick(View view) {
        onBackPressed();
    }

    public void AddNoteClick(View view) {

        showBottomSheetDialog();

    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog dialog = new BottomSheetDialog(MindsetActivity.this);
        dialog.setContentView(R.layout.bottom_sheet_points_data);

        dialog.show();

        final TextInputEditText mTitle = dialog.findViewById(R.id.txt_point_title_bottom_sheet);
        final TextInputEditText mDescription = dialog.findViewById(R.id.txt_point_desc_bottom_sheet);

        Button mBtnAdd = dialog.findViewById(R.id.btn_add_bottom_sheet);

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mTitle.getText().toString().trim();
                String description = mDescription.getText().toString();

                if (title.length()<3){
                    mTitle.setError("Title too short");
                    mTitle.requestFocus();
                    return;
                }

                if (description.length()<3){
                    mDescription.setError("Description too short");
                    mDescription.requestFocus();
                    return;
                }

                list.add(new PointsModel(title, description));
                dialog.cancel();

                showPointsOnRecyclerView();

            }
        });

    }

    private void showPointsOnRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        PointsAdapter adapter = new PointsAdapter(getApplicationContext(), list);
        mRecyclerView.setAdapter(adapter);

    }

    public void UploadDataClick(View view) {

        title = mTxtTitle.getText().toString().trim();
        description = mTxtDesription.getText().toString().trim();

        if (title.length()<3){
            mTxtTitle.setError("Title too short");
            mTxtTitle.requestFocus();
            return;
        }

        if (description.length()<3){
            mTxtDesription.setError("Title too short");
            mTxtDesription.requestFocus();
            return;
        }


        if (list.isEmpty()){
            Toast.makeText(this, "Kindly add a point", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean result = UploadMindSetData.uploadMindSetData(title, description, "mindset", list);

        if (result = true){
            Toast.makeText(this, "Data Uploaded", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this, "Failure Occurred", Toast.LENGTH_SHORT).show();
        }

    }
}