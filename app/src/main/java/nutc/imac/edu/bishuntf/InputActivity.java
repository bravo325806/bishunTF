package nutc.imac.edu.bishuntf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by cheng on 2017/9/30.
 */

public class InputActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText imageEdit;
    private EditText apiEdit;
    private Button sumbit;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        sharedPreferences=getSharedPreferences("BinShun",0);
        editor=sharedPreferences.edit();
        imageEdit= (EditText) findViewById(R.id.image_edit);
        apiEdit= (EditText) findViewById(R.id.api_edit);
        sumbit=(Button)findViewById(R.id.submit);
        sumbit.setOnClickListener(this);
        imageEdit.setText(sharedPreferences.getString("image",""));
        apiEdit.setText(sharedPreferences.getString("api",""));
    }

    @Override
    public void onClick(View view) {
        String imageStr=imageEdit.getText().toString();
        String apiStr=apiEdit.getText().toString();
        editor.putString("image",imageStr);
        editor.putString("api",apiStr);
        editor.commit();
        Intent intent=new Intent(InputActivity.this,MainActivity.class);
        intent.putExtra("imageip",imageStr);
        intent.putExtra("apiip",apiStr);
        startActivity(intent);
    }
}
