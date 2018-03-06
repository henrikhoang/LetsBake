package com.example.henrikhoang.letsbake;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.henrikhoang.letsbake.fragment.StepFragment;

public class StepActivity extends AppCompatActivity implements
StepFragment.OnButtonClickListener {


    private int stepIndex;
    private Recipe mRecipe;
    private static final String TAG = StepActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

            if (savedInstanceState == null) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                StepFragment stepFragment = new StepFragment();

                fragmentManager.beginTransaction()
                        .add(R.id.step_fragment, stepFragment)
                        .commit();
            }
        }

    @Override
    public void onPreviousButtonClicked() {

    }

    @Override
    public void onNextButtonClicked() {
    }
}
