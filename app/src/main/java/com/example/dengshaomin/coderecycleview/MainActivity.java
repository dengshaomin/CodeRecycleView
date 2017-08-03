package com.example.dengshaomin.coderecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.CodeRecycleView;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.CommonAdapter;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.MultiItemTypeAdapter;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NormalActivity.class));
            }
        });
        findViewById(R.id.withVaLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WithValayoutActivity.class));
            }
        });
    }


}
