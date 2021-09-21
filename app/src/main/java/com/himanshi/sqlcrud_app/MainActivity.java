package com.himanshi.sqlcrud_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText txtName, txtDelCusId, txtAddress, txtPhone;
    private EditText txtViewCusId, txtViewName, txtViewAddress, txtViewPhone;
    private EditText txtUpdateCusId, txtUpdateName, txtUpdateAddress, txtUpdatePhone;
    private TextView txtData;
    private Button btnInsert;
    private Button btnView;
    private Button btnViewAll;
    private Button btnUpdate;
    private Button btnDelete;

    private SQLiteOpenHelperMy dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDatabase();
        initViews();

    }

    private void createDatabase()
    {
        dbHelper = new SQLiteOpenHelperMy(this,"myDb.db",null,1);
//        Toast.makeText(this,"Database Created..",Toast.LENGTH_SHORT).show();
    }

    InputFilter[] myTextFilter(){
        return new InputFilter[]{
                (charSequence, i, i1, spanned, i2, i3) -> {
                    if (charSequence.equals("")) {
                        return charSequence;
                    }
                    if (charSequence.toString().matches("[a-zA-Z ]+")) {
                        return charSequence;
                    }
                    return "";
                }
        };
    }
    private void initViews() {

        txtName = findViewById(R.id.txtName);
        txtName.setFilters(myTextFilter());
        txtAddress = findViewById(R.id.txtAddress);
        txtAddress.setFilters(myTextFilter());
        txtPhone = findViewById(R.id.txtPhone);

        txtViewCusId = findViewById(R.id.txtViewCusId);
        txtViewName  = findViewById(R.id.txtViewName);
        txtViewAddress = findViewById(R.id.txtViewAddress);
        txtViewPhone = findViewById(R.id.txtViewPhone);

        txtData = findViewById(R.id.txtData);

        txtDelCusId = findViewById(R.id.txtDelCusId);

        txtUpdateCusId = findViewById(R.id.txtUpdateCusId);
        txtUpdateName = findViewById(R.id.txtUpdateName);
        txtUpdateName.setFilters(myTextFilter());
        txtUpdateAddress = findViewById(R.id.txtUpdateAddress);
        txtUpdateAddress.setFilters(myTextFilter());
        txtUpdatePhone = findViewById(R.id.txtUpdatePhone);

        btnInsert = findViewById(R.id.btnInsert);
        btnView = findViewById(R.id.btnView);
        btnViewAll = findViewById(R.id.btnViewAll);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnInsert.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.btnInsert:
            {
                String cusName = txtName.getText().toString();
                String cusAddress = txtAddress.getText().toString();
                String cusPhone = txtPhone.getText().toString();
                if(cusName.length()>0 && cusAddress.length()>0 && cusPhone.length()>0){
                boolean isInserted = dbHelper.insertData(cusName, cusAddress, cusPhone);
                if(isInserted){
                    txtName.setText("");
                    txtAddress.setText("");
                    txtPhone.setText("");
                    txtName.requestFocus();
                    Toast.makeText(this,"Inserted..",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(this,"Not Inserted..",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this,"Fill the missing data!",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btnView:
            {
                String cusData[] = dbHelper.viewData(txtViewCusId.getText().toString());
                txtViewName.setText(cusData[0]);
                txtViewAddress.setText(cusData[1]);
                txtViewPhone.setText(cusData[2]);
                break;
            }
            case R.id.btnUpdate:
            {
                String cusId = txtUpdateCusId.getText().toString();
                String cusNewName = txtUpdateName.getText().toString();
                String cusNewAdd = txtUpdateAddress.getText().toString();
                String cusNewPhone = txtUpdatePhone.getText().toString();
                if(cusNewName.length()>0 && cusNewAdd.length()>0 && cusNewPhone.length()>0){
                boolean isUpdated = dbHelper.updateData(cusId,cusNewName,cusNewAdd,cusNewPhone);
                if(isUpdated) {
                    txtUpdateCusId.setText("");
                    txtUpdateName.setText("");
                    txtUpdateAddress.setText("");
                    txtUpdatePhone.setText("");
                    txtUpdateCusId.requestFocus();
                    Toast.makeText(this, cusId + " Updated..", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this,cusId+" not exist for Update!",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this,"Fill the missing data!",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btnDelete:
            {
                boolean isDeleted = dbHelper.deleteData(txtDelCusId.getText().toString());
                if(isDeleted)
                    Toast.makeText(this,txtDelCusId.getText().toString()+" Deleted..",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,txtDelCusId.getText().toString()+" id not exits",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btnViewAll:
            {
                String allData = dbHelper.viewAllData();
                txtData.setText(allData);
                break;
            }
        }// Switch closed
    }
}