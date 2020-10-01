package cdac.org.anganvadistaffutility.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.data.PaymentDetails;
import cdac.org.anganvadistaffutility.utils.AppUtils;


public class SalaryDetailsTblActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recycler_view;
    private List<PaymentDetails.Empdatum> empDatumList;
    private String employeeName = "";
    private String fromYear = "";
    private String toYear = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_details_tbl);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recycler_view = findViewById(R.id.recycler_view);
        AppCompatButton btn_download_salary_slip = findViewById(R.id.btn_download_salary_slip);

        String emdData = getIntent().getStringExtra("salary_data");
        fromYear = getIntent().getStringExtra("fromYear");
        toYear = getIntent().getStringExtra("toYear");

        empDatumList = AppUtils.convertToGet(emdData);
        if (empDatumList != null) {
            if (!empDatumList.isEmpty()) {
                employeeName = empDatumList.get(0).getEmployeeNameEnglish();
            }
        }

        setRecyclerView();
        btn_download_salary_slip.setOnClickListener(this);
    }

    private void setRecyclerView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        PaymentAdapter adapter = new PaymentAdapter(this, empDatumList);
        recycler_view.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_download_salary_slip) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (AppUtils.hasPermissions(context, AppUtils.STORAGE_PERMISSIONS)) {
                    downloadSalarySlip();
                } else {
                    requestPermissions(AppUtils.STORAGE_PERMISSIONS, AppUtils.STORAGE_PERMISSION_REQUEST_CODE);
                }
            } else {
                downloadSalarySlip();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppUtils.STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                downloadSalarySlip();
            }
        }
    }

    private void downloadSalarySlip() {
        int serialNo = 0;
        String outPath = "";

        // String[] headers = new String[]{"Serial No.", "Employee ID", "Employee Name", "Salary Month", "Salary Amount"};
        String[] headers = new String[]{"Sr. No.", "Payment Month", "Bill Name", "Amount (Rs.)"};
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        //   document.setMargins(16, 14, 14, 14);

        try {
            outPath = String.valueOf(AppUtils.writeFilesToAppFolder(employeeName + fromYear + "-" + toYear.substring(2, 4), ".pdf"));
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(new File(outPath)));
            document.open();
            //   document.setPageSize(PageSize.LETTER);
            // document.setMargins(16, 14, 42, 38);

            Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
            Font fontRow = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL);

            Paragraph p1 = new Paragraph("Employee Name: " + empDatumList.get(0).getEmployeeNameEnglish());
            Paragraph p2 = new Paragraph("Employee ID: " + empDatumList.get(0).getEmployeeId());
            Paragraph p3 = new Paragraph("Payment Data of Financial Year: " + fromYear + "-" + toYear.substring(2, 4));
            Font paraFont = new Font(fontHeader);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);

            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p2.setFont(paraFont);

            p3.setAlignment(Paragraph.ALIGN_CENTER);
            p3.setFont(paraFont);

            document.add(p1);
            document.add(p2);
            document.add(p3);

            float[] columnWidths = new float[]{1f, 2f, 2f, 1f};
            PdfPTable table = new PdfPTable(headers.length);
            table.setSpacingAfter(0f);
            table.setSpacingBefore(24f);
            table.setTotalWidth(document.right() - document.left());
            table.setLockedWidth(true);
            table.setWidths(columnWidths);

            for (String header : headers) {
                PdfPCell cell = new PdfPCell();
                cell.setGrayFill(1.0f);
                cell.setPhrase(new Phrase(header.toUpperCase(), fontHeader));
                cell.setFixedHeight(36f);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
            table.completeRow();

            for (PaymentDetails.Empdatum empDatum : empDatumList) {
                serialNo = ++serialNo;
                String[][] rows = new String[][]{
                        {"" + serialNo, empDatum.getMonth(), empDatum.getSubbillname(), empDatum.getSalary()},
                };

                int dataNo = 0;
                for (String[] row : rows) {
                    for (String data : row) {
                        dataNo = ++dataNo;
                        Phrase phrase = new Phrase(data, fontRow);
                        PdfPCell cell = new PdfPCell(phrase);
                        cell.setFixedHeight(36f);
                        if (dataNo < row.length) {
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        } else {
                            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        }
                        table.addCell(cell);
                    }
                    table.completeRow();
                }
            }

            if (writer.getVerticalPosition(true) - table.getRowHeight(0) - table.getRowHeight(1) < document.bottom()) {
                document.newPage();
            }
            document.add(table);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        openEmployeeSalaryPDF(outPath);
    }

    private void openEmployeeSalaryPDF(String pdfPath) {
        File file = new File(pdfPath);
        if (file.exists()) {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri pdfURI = FileProvider.getUriForFile(
                    context,
                    context.getApplicationContext()
                            .getPackageName() + ".provider", file);
            intent.setDataAndType(pdfURI, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                AppUtils.showToast(context, getResources().getString(R.string.no_app_available_to_view_pdf));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    private static class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

        Context context;
        List<PaymentDetails.Empdatum> payment_list;


        public PaymentAdapter(SalaryDetailsTblActivity context, List<PaymentDetails.Empdatum> payment_list) {
            this.context = context;
            this.payment_list = payment_list;
        }

        @NonNull
        @Override
        public PaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (payment_list != null && payment_list.size() > 0) {
                PaymentDetails.Empdatum empDatum = payment_list.get(position);
                holder.id_tv.setText(empDatum.getEmployeeId());
                holder.name_tv.setText(empDatum.getMonth());
                holder.payment_tv.setText(empDatum.getSalary());
            }
        }

        @Override
        public int getItemCount() {
            return payment_list.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView id_tv, name_tv, payment_tv;

            public ViewHolder(View itemView) {
                super(itemView);

                id_tv = itemView.findViewById(R.id.id_tv);
                name_tv = itemView.findViewById(R.id.name_tv);
                payment_tv = itemView.findViewById(R.id.payment_tv);

            }
        }
    }

}