package nutc.imac.edu.bishuntf;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;

/**
 * Created by cheng on 2017/9/29.
 */

public class UploadFileAsync extends AsyncTask<Object,Object,String>{
    private ProgressDialog progressBar;
    private CameraFinishActivity context;
    private Bitmap endBitmap;
    private String ip;
    public UploadFileAsync(CameraFinishActivity context, Bitmap endBitmap,String ip){
        this.context=context;
        this.endBitmap=endBitmap;
        this.ip=ip;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = new ProgressDialog(context);
        progressBar.setMessage("upload..");
        progressBar.setIndeterminate(true);
        progressBar.setCancelable(false);
        progressBar.show();
    }

    @Override
    protected String doInBackground(Object[] objects) {
        StringBuilder s = new StringBuilder();

        try
        {
//            Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.user);

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest;
            if(ip.equals("")){
                postRequest = new HttpPost("http://10.21.20.152:5000/upload");
            }else {
                postRequest = new HttpPost("http://"+ip+":5000/upload");
            }

            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            try{
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                endBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] data = bos.toByteArray();
                ByteArrayBody bab = new ByteArrayBody(data, "image.jpg");
                reqEntity.addPart("file", bab);
            }
            catch(Exception e){
                //Log.v("Exception in Image", ""+e);
                reqEntity.addPart("picture", new StringBody(""));
            }
            postRequest.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String sResponse;

            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }

        }catch(Exception e){
            e.getStackTrace();
        }

        Log.e("respond picture", " " + s);

        return s.toString();
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        Log.e(UploadFileAsync.class.getSimpleName(),o);
        progressBar.dismiss();
        context.imageIdentification(o);
    }
}
