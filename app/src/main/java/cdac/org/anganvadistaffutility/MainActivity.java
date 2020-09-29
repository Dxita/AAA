package cdac.org.anganvadistaffutility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import cdac.org.anganvadistaffutility.activity.BaseActivity;

public class MainActivity extends Activity {
Button create;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create=findViewById(R.id.bt_createpdf);

        int permission = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );


            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createpdf(view);
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void createpdf(View view){
//reference to EditText
        TextView et=findViewById(R.id.pdf_creator_tv);
//create document object
        Document doc=new Document();
//output file path
        String outpath= Environment.getExternalStorageDirectory()+"/EmployeeSalarySlip.pdf";
        try {
//create pdf writer instance
            PdfWriter.getInstance(doc, new FileOutputStream(outpath));
//open the document for writing
            doc.open();

//add paragraph to the document
            doc.add(new Paragraph(et.getText().toString()));
//close the document
            doc.close();

        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}