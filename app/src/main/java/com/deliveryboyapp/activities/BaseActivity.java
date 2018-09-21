package com.deliveryboyapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.deliveryboyapp.R;
import com.deliveryboyapp.beans.Delivery;
import com.deliveryboyapp.di.components.DaggerApplicationComponent;
import com.deliveryboyapp.net.APIEndPoints;
import com.deliveryboyapp.net.ViewModelFactory;

import javax.inject.Inject;

import static com.deliveryboyapp.Constants.KEY_PUT_EXTRA;

public class BaseActivity extends AppCompatActivity {

    private static String TAG = BaseActivity.class.getSimpleName();

    @Inject
    APIEndPoints mApiEndPoints;

    @Inject
    ViewModelFactory mViewModelFactory;

    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DaggerApplicationComponent.builder().build().inject(this);

        setupProgressDialog();
    }

    private void setupProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.str_wait_msg));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
    }

    protected void displayLoading() {
        if (mProgressDialog != null)
            mProgressDialog.show();
    }

    protected void hideLoading() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    protected void displayToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void navigateToOtherScreen(Activity activityFrom, Class activityTo, boolean putExtra,
                                         Delivery delivery, boolean needToFinishActivity) {

        Intent intent = new Intent(activityFrom, activityTo);

        if (putExtra) {
            intent.putExtra(KEY_PUT_EXTRA, delivery.toJson());
        }

        startActivity(intent);

        if (needToFinishActivity) {
            finish();
        }
    }
}