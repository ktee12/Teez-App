package uk.ac.tees.b1515396.teezapplication.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import uk.ac.tees.b1515396.teezapplication.MainActivity;
import uk.ac.tees.b1515396.teezapplication.R;


public class HomeFragment extends Fragment {

    private Button btn_scan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btn_scan = view.findViewById(R.id.btn_scan2);

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scanCode();
            }
        });


        return view;
    }

    private void scanCode() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Volume up to flash on");
        scanOptions.setBeepEnabled(true);
        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(CaptureAct.class);
        scanLauncher.launch(scanOptions);
    }

    //AppCompatActivity activity = (AppCompatActivity) getActivity();

    ActivityResultLauncher<ScanOptions> scanLauncher = registerForActivityResult(new ScanContract(), result -> {

        if(result.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });

}