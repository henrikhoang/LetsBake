package com.example.henrikhoang.letsbake;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.henrikhoang.letsbake.fragment.StepFragment;

import org.parceler.Parcels;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity implements
StepFragment.OnButtonClickListener {


    private int stepIndex;
    private ArrayList<Step> mSteps;
    private Step mStep;
    private static final String TAG = StepActivity.class.getSimpleName();
    private int localStepIndex;
    private boolean initialClick = true;
    private static String LOCAL_STEP_INDEX = "local step index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Bundle bundle = getIntent().getExtras();

        stepIndex = bundle.getInt("step index");

        if (initialClick) localStepIndex = stepIndex;

        Log.d(TAG, "STEP ACTIVITY step index" + stepIndex);

        mSteps = Parcels.unwrap(getIntent().getParcelableExtra("list of steps"));
        if (savedInstanceState != null) {
            localStepIndex = savedInstanceState.getInt(LOCAL_STEP_INDEX);
        }
        mStep = mSteps.get(localStepIndex);

        FragmentManager fragmentManager = getSupportFragmentManager();

        StepFragment stepFragment = new StepFragment();
        stepFragment.updateDataStep(mStep);

        if (savedInstanceState == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.step_fragment, stepFragment)
                        .commit();
            }
        }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(LOCAL_STEP_INDEX, localStepIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        localStepIndex = savedInstanceState.getInt(LOCAL_STEP_INDEX);
    }

    @Override
    public void onPreviousButtonClicked() {
        if (localStepIndex == 0) {
            Toast.makeText(this, getResources().getString(R.string.first_step_notification), Toast.LENGTH_LONG).show();
        } else {
            initialClick = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            StepFragment newStepFragment = new StepFragment();
            newStepFragment.updateDataStep(mSteps.get(localStepIndex- 1));

            localStepIndex = localStepIndex - 1;
            fragmentManager.beginTransaction()
                        .replace(R.id.step_fragment, newStepFragment)
                        .commit();
        }
    }



    @Override
    public void onNextButtonClicked() {
        if (localStepIndex == mSteps.size() - 1) {
            Log.d(TAG, "onNextButtonClicked localStepIndex: " + localStepIndex + "Step size: " + mSteps.size());
            Toast.makeText(this, getResources().getString(R.string.last_step_notification), Toast.LENGTH_LONG).show();
        } else {
            initialClick = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            StepFragment newStepFragment = new StepFragment();
            newStepFragment.updateDataStep(mSteps.get(localStepIndex + 1));

            localStepIndex = localStepIndex + 1;
            fragmentManager.beginTransaction()
                        .replace(R.id.step_fragment, newStepFragment)
                        .commit();

        }
    }
}
