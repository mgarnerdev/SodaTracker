package com.sodatracker.apptastic.sodatracker.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sodatracker.apptastic.sodatracker.R;
import com.sodatracker.apptastic.sodatracker.objects.Soda;
import com.sodatracker.apptastic.sodatracker.adapters.SodaAdapter;
import com.sodatracker.apptastic.sodatracker.SodaTrackerApp;
import com.sodatracker.apptastic.sodatracker.utilities.DialogUtils;
import com.sodatracker.apptastic.sodatracker.utilities.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SodaAdapter.SodaClickListener {
    public static final String CLASS_NAME = MainActivity.class.getName();
    public static final boolean DEBUG_CLASS = false;
    public static final boolean DEBUG_METHOD_CALLS = false;

    public static final String EXTRA_SODA = "extra_extra_soda_plz";
    public static final int REQUEST_CODE_FOR_SODA = 1234;
    private RecyclerView mRecyclerView;
    private SodaAdapter mSodaAdapter;
    private ArrayList<Soda> mSodaList = null;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_activity_rv);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        initUI();
    }

    private void initUI() {
        mSodaList = SodaTrackerApp.getSodaList(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSodaAdapter = new SodaAdapter(mSodaList);
        mSodaAdapter.setSodaClickListener(this);
        mRecyclerView.setAdapter(mSodaAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN | ItemTouchHelper.UP, ItemTouchHelper.START | ItemTouchHelper.END) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int targetPosition = target.getAdapterPosition();
                if (mSodaList != null) {
                    Soda soda = mSodaList.get(fromPosition);
                    mSodaList.remove(fromPosition);
                    mSodaList.add(targetPosition, soda);
                    mSodaAdapter.updateSodaList(mSodaList);
                    mSodaAdapter.notifyItemMoved(fromPosition, targetPosition);
                }
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                mSodaList.remove(position);
                mSodaAdapter.updateSodaList(mSodaList);
                mSodaAdapter.notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SodaTrackerApp.addMoreSoda(MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        MenuItem miAppVersion = menu.findItem(R.id.action_app_version);
        miAppVersion.setTitle(SodaTrackerApp.MENU_APP_VERSION);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rate_the_app:
                startActivity(Utils.rateApp(this));
                break;
            case R.id.action_share_the_app:
                startActivity(Utils.shareApp(this));
                break;
            case R.id.action_about_the_app:
                DialogUtils.showAboutAppDialog(this);
                break;
            case R.id.action_app_version:
                startActivity(Utils.rateApp(this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FOR_SODA && resultCode == RESULT_OK) {
            Soda soda = data.getParcelableExtra(EXTRA_SODA);
            if (soda != null) {
                if (mSodaList == null) {
                    mSodaList = new ArrayList<>();
                }
                mSodaList.add(soda);
                SodaTrackerApp.saveSodaList(this, mSodaList);
                mSodaAdapter.updateSodaList(mSodaList);
                mSodaAdapter.notifyItemInserted(mSodaList.size() - 1);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SodaTrackerApp.saveSodaList(this, mSodaList);
    }

    @Override
    public void onItemLongClick(final int position, View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setTitle("Delete?");
        dialogBuilder.setMessage("Are you sure you want to delete this soda?");
        dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSodaList.remove(position);
                mSodaAdapter.updateSodaList(mSodaList);
                mSodaAdapter.notifyItemRemoved(position);
                dialog.cancel();
            }
        });
        if (!isFinishing()) {
            dialogBuilder.show();
        }
    }

    @Override
    public void onChildClick(int adapterPosition) {
        mSodaAdapter.notifyItemChanged(adapterPosition);
    }
}
