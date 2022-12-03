package uk.ac.tees.b1515396.teezapplication.main;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import uk.ac.tees.b1515396.teezapplication.MainActivity;
import uk.ac.tees.b1515396.teezapplication.R;

public class ScanActivity extends AppCompatActivity {

    Button btn_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        btn_scan = findViewById(R.id.btn_scan);

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });
    }

    private void scanCode() {

        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Volume up to flash on");
        scanOptions.setBeepEnabled(true);
        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(CaptureAct.class);
        scanLauncher.launch(scanOptions);

    }

    ActivityResultLauncher<ScanOptions> scanLauncher = registerForActivityResult(new ScanContract(), result -> {

        if(result.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(ScanActivity.this);
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