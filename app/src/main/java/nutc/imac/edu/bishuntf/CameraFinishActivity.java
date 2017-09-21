package nutc.imac.edu.bishuntf;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

        percent=getIntent().getDoubleExtra("percent",0);
    }
    private void setImage(){
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        Bitmap rotate=Bitmap.createBitmap(myBitmap,0,0,myBitmap.getWidth(),myBitmap.getHeight(),matrix,true);
        int x=rotate.getWidth();
        int y=rotate.getHeight();
        int deviationWidth=(int)Math.round(width/4);
        int deviationHeight=Math.round(x/4);
        Double x1=(width/8)*(x/width)+(deviationWidth*(percent/100));
        Double y1=(width/8)*(y/height)+(deviationHeight*(percent/100));
        Double width1=((width*7)/8)*(x/width)-x1-(deviationWidth*(percent/100));
        Double height1=((width*7)/8)*(y/height)-y1-(deviationHeight*(percent/100));
        endBitmap=Bitmap.createBitmap(rotate,x1.intValue(),y1.intValue(),width1.intValue(),height1.intValue());
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ruler.getW(80),ruler.getW(80));
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(params);
        imageView.setImageBitmap(endBitmap);
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.submit){
            finish();
        }else if(view.getId()==R.id.cancel){
            finish();
        }
    }
}
