package nutc.imac.edu.bishuntf;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by cheng on 2017/9/18.
 */

public class CameraFinishActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView imageView;
    private File file;
    private Bitmap endBitmap;
    private Double width,height;
    private Ruler ruler;
    private Double percent;
    private Double squarex1;
    private Double squarex2;
    private Double squarey1;
    private Double squarey2;
    private String imageip;
    private String apiip;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ruler=new Ruler(this);
        init();

    }
    private void init(){
        findView();
        setImage();
    }
    private void findView(){
        findViewById(R.id.submit).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        imageView= (ImageView) findViewById(R.id.image);
        file= (File) getIntent().getSerializableExtra("file");
        width=Double.valueOf(getIntent().getIntExtra("width",0));
        height=Double.valueOf(getIntent().getIntExtra("height",0));
        squarex1=getIntent().getDoubleExtra("x1",0);
        squarex2=getIntent().getDoubleExtra("x2",0);
        squarey1=getIntent().getDoubleExtra("y1",0);
        squarey2=getIntent().getDoubleExtra("y2",0);
        percent=getIntent().getDoubleExtra("percent",0);
        imageip=getIntent().getStringExtra("imageip");
        apiip=getIntent().getStringExtra("apiip");
    }
    private void setImage(){
        Matrix matrix = new Matrix();
        matrix.postRotate(0);
        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        Bitmap rotate=Bitmap.createBitmap(myBitmap,0,0,myBitmap.getWidth(),myBitmap.getHeight(),matrix,true);
        int x=rotate.getWidth();
        int y=rotate.getHeight();
        int deviationWidth=(int)Math.round(width/4);
        int deviationHeight=(int)Math.round(height/4);
        Double x1=x*(squarex1/width);
        Double y1=y*(squarey1/height);
        Double width1=x*((squarex2-squarex1)/width);
        Double height1=y*((squarey2-squarey1)/height);
        matrix.reset();
        matrix.postScale(50/width1.floatValue(),50/height1.floatValue());
        endBitmap=Bitmap.createBitmap(rotate,x1.intValue(),y1.intValue(),width1.intValue(),height1.intValue(),matrix,true);
        Log.e("width",endBitmap.getWidth()+"");
        Log.e("height",endBitmap.getHeight()+"");
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ruler.getW(70),ruler.getW(70));
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(params);
        imageView.setImageBitmap(endBitmap);
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.submit){
            UploadFileAsync fileAsync=new UploadFileAsync(this,endBitmap,imageip);
            fileAsync.execute();

        }else if(view.getId()==R.id.cancel){
            finish();
        }
    }
    public void imageIdentification(String o){
        Bundle bundle=new Bundle();
        try {
            JSONObject jsonObject=new JSONObject(o);
            bundle.putString("str",jsonObject.getString("classes"));
            bundle.putString("ip",apiip);
            CameraFinishDialog cameraFinishDialog=new CameraFinishDialog();
            cameraFinishDialog.setArguments(bundle);
            cameraFinishDialog.show(getFragmentManager(),CameraFinishDialog.class.getName());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void uploadFinish(){
        finish();
    }

}
