package com.example.henrikhoang.letsbake;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.henrikhoang.letsbake.fragment.StepFragment;

public class StepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        FragmentManager fragmentManager = getSupportFragmentManager();

        StepFragment stepFragment = new StepFragment();

        fragmentManager.beginTransaction()
                .add(R.id.step_fragment, stepFragment)
                .commit();
    }

}
