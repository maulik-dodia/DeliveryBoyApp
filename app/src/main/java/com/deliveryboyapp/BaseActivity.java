package com.deliveryboyapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.deliveryboyapp.beans.Delivery;

public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupProgressDialog();
    }

    private void setupProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.str_loading));
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
            intent.putExtra(Constants.KEY_PUT_EXTRA, delivery.toJson());
        }

        startActivity(intent);

        if (needToFinishActivity) {
            finish();
        }
    }
}