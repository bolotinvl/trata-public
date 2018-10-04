package com.bolotin.trata.ui.editTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.listeners.DateTimePickerListener;
import com.bolotin.trata.ui.base.BaseActivity;
import com.bolotin.trata.ui.category.selectCategory.SelectCategoryActivity;
import com.bolotin.trata.ui.datePicker.DatePickerDialog;
import com.bolotin.trata.ui.timePicker.TimePickerDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditTransactionActivity extends BaseActivity
        implements EditTransactionMvpView, DateTimePickerListener, OnMapReadyCallback {

    private static final int CHANGE_CATEGORY_REQUEST = 0;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 99;
    private static final int DEFAULT_ZOOM = 15;

    @Inject
    EditTransactionMvpPresenter<EditTransactionMvpView> presenter;

    @Inject
    FusedLocationProviderClient fusedLocationProviderClient;

    @BindView(R.id.edit_transaction_toolbar)
    Toolbar toolbar;

    @BindView(R.id.edit_text_amount)
    EditText valueEditText;

    @BindView(R.id.date_picker_view)
    TextView datePickerView;

    @BindView(R.id.time_picker_view)
    TextView timePickerView;

    @BindView(R.id.edit_transaction_category_name)
    TextView categoryNamePicker;

    @BindView(R.id.edit_transaction_category_icon)
    ImageView categoryIcon;

    @BindView(R.id.edit_transaction_note)
    EditText noteEdit;

    private Fragment mapFragment;
    private GoogleMap map;
    private Location lastKnownLocation;
    private Double latitude;
    private Double longitude;
    private boolean hasLocationPermission;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, EditTransactionActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_transaction);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        setUp(toolbar);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp(Toolbar toolbar) {
        super.setUp(toolbar);

        String transactionId = getIntent().getStringExtra("transactionId");
        presenter.onViewPrepared(transactionId);

        mapFragment = getSupportFragmentManager().findFragmentById(R.id.map);
        getSupportFragmentManager().beginTransaction().hide(mapFragment).commit();

        hasLocationPermission = hasPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (!hasLocationPermission) {
            requestPermissionsSafely(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            setUpMapObjects();
        }
    }

    @Override
    public void onBackPressed() {
        presenter.updateTransaction(Double.valueOf(valueEditText.getText().toString()),
                noteEdit.getText().toString());

        finish();
        super.onBackPressed();
    }

    @OnClick(R.id.edit_transaction_back_button)
    void onBackButtonClick() {
        onBackPressed();
    }

    @OnClick(R.id.edit_transaction_category)
    void onChangeCategoryButtonClick() {
        Intent intent = SelectCategoryActivity.getStartIntent(this);
        startActivityForResult(intent, CHANGE_CATEGORY_REQUEST);
    }

    @OnClick(R.id.date_picker_view)
    void onDatePickerClick() {
        presenter.getTransactionDateAndStartDatePicker();
    }

    @OnClick(R.id.time_picker_view)
    void onTimePickerClick() {
        presenter.getTransactionTimeAndStartTimePicker();
    }

    @Override
    public void updateUITransactionData(String date, String time, String value, String note) {
        datePickerView.setText(date);
        timePickerView.setText(time);
        valueEditText.setText(value);
        noteEdit.setText(note);
    }

    @Override
    public void updateUICategoryData(String categoryIconId, String categoryName) {
        categoryIcon.setImageResource(getResources().getIdentifier(categoryIconId,
                "drawable",
                getPackageName()));
        categoryNamePicker.setText(categoryName);
    }

    @Override
    public void updateUIMapData(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        updateMapUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHANGE_CATEGORY_REQUEST && resultCode == RESULT_OK) {
            presenter.updateCategoryId(data.getStringExtra("categoryId"));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        getSupportFragmentManager().beginTransaction().show(mapFragment).commit();
        getLastKnownLocation();
        presenter.getTransactionLocationAndUpdateMap();

        map.setOnMapClickListener(latLng -> {
            map.clear();
            Marker marker = map.addMarker(new MarkerOptions().position(latLng).title(noteEdit.getText().toString()));
            marker.showInfoWindow();

            latitude = latLng.latitude;
            longitude = latLng.longitude;
            presenter.updateTransactionLocation(latitude, longitude);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        hasLocationPermission = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hasLocationPermission = true;
                    setUpMapObjects();
                }
            }
        }
    }

    @Override
    public void showDatePicker(int year, int month, int dayOfMonth) {
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        bundle.putInt("dayOfMonth", dayOfMonth);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance();
        datePickerDialog.setArguments(bundle);
        datePickerDialog.show(getSupportFragmentManager());
    }

    @Override
    public void showTimePicker(int hours, int minutes) {
        Bundle bundle = new Bundle();
        bundle.putInt("hours", hours);
        bundle.putInt("minutes", minutes);
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance();
        timePickerDialog.setArguments(bundle);
        timePickerDialog.show(getSupportFragmentManager());
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        presenter.updateTransactionDate(year, month, day);
    }

    @Override
    public void onTimeSet(int hours, int minutes) {
        presenter.updateTransactionTime(hours, minutes);
    }

    private void updateMapUI() {
        if (latitude != null && longitude != null) {
            // Set the map's camera position to the transaction's location.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(latitude, longitude), DEFAULT_ZOOM));
            addMarkerToMap(new LatLng(latitude, longitude));
        } else {
            //If transaction doesn't have location, then set the map's camera position to the last known device location
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(presenter.getLastKnownLatitude(), presenter.getLastKnownLongitude()), DEFAULT_ZOOM));
        }
        setUserLocationEnabled();
    }

    private void setUserLocationEnabled() {
        try {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void addMarkerToMap(LatLng location) {
        map.addMarker(new MarkerOptions()
                .position(location).title(noteEdit.getText().toString()));
    }

    private void getLastKnownLocation() {
        try {
            Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    lastKnownLocation = task.getResult();
                    presenter.saveLastKnownLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                }
            });
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void setUpMapObjects() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
}