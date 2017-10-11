package nutc.imac.edu.bishuntf;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cheng on 2017/10/6.
 */

public class CameraFinishDialog extends DialogFragment implements View.OnClickListener {
    private TextView title;
    private Button submit;
    private Button cancel;
    private String ip;
    private String str;
    private RequestQueue queue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_camera_finish,container,false);
        title=view.findViewById(R.id.title);
        submit=view.findViewById(R.id.submit);
        cancel=view.findViewById(R.id.cancel);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ip=getArguments().getString("ip");
        str=getArguments().getString("str");
        title.setText(str);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        queue = SingleRequestQueue.getQueue(getActivity());
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.submit){
            upload();
        }else if(view.getId()==R.id.cancel){
            ((CameraFinishActivity)getActivity()).uploadFinish();
            dismiss();
        }
    }
    private void upload(){
        String url = "http://"+ip+":3001/piano";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(),"上傳成功",Toast.LENGTH_SHORT).show();
                ((CameraFinishActivity)getActivity()).uploadFinish();
                dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"上傳失敗",Toast.LENGTH_SHORT).show();
                ((CameraFinishActivity)getActivity()).uploadFinish();
                dismiss();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                SplitArray mSplitArray = new SplitArray();
                HashMap<String,Integer> params=new HashMap();
                params.put("num",Integer.valueOf(str));
                String request = mSplitArray.getJsonInteger(params);
                return request.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap mHeaderHashMap = new HashMap<>();
                mHeaderHashMap.put("Content-Type", "application/json");
                mHeaderHashMap.put("Accept", "application/json");
                return mHeaderHashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    // 對 Volley 的 RequestQueue 做單例模式雙重檢查，避免重複產生 Queue，造成 OutOfMemory 錯誤
    private static final class SingleRequestQueue {
        private volatile static RequestQueue queue;

        private SingleRequestQueue() {
        }

        private static RequestQueue getQueue(Context context) {
            if (queue == null) {
                synchronized (SingleRequestQueue.class) {
                    if (queue == null) {
                        queue = Volley.newRequestQueue(context);
                    }
                }
            }
            return queue;
        }
    }
}
