package nutc.imac.edu.bishuntf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cheng on 2017/9/18.
 */

public class CameraBackground extends View{
    private int measureHeight, measureWidth;
    private Paint paint;
    private int deviation;
    private Double percent=0.0;
    private Double x1,x2;
    private Double y1,y2;
    public CameraBackground(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        paint.setColor(0xAF000000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        deviation=measureWidth/4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //上面
        canvas.drawRect(0,0,measureWidth,measureWidth/8+Math.round(deviation*(percent/100)),paint);
        //左面
        canvas.drawRect(0,measureWidth/8+Math.round(deviation*(percent/100)),measureWidth/8+Math.round(deviation*(percent/100)),(measureWidth*7)/8-+Math.round(deviation*(percent/100)),paint);
        //右面
        canvas.drawRect((measureWidth*7)/8-Math.round(deviation*(percent/100)),measureWidth/8+Math.round(deviation*(percent/100)),measureWidth,(measureWidth*7)/8-Math.round(deviation*(percent/100)),paint);
        //下面
        canvas.drawRect(0,(measureWidth*7)/8-Math.round(deviation*(percent/100)),measureWidth,measureHeight,paint);

        x1=Double.valueOf(measureWidth/8+Math.round(deviation*(percent/100)));
        x2=Double.valueOf((measureWidth*7)/8-Math.round(deviation*(percent/100)));
        y1=Double.valueOf(measureWidth/8+Math.round(deviation*(percent/100)));
        y2=Double.valueOf((measureWidth*7)/8-Math.round(deviation*(percent/100)));
    }
    public Double getPercent(){
        return percent;
    }
    public void setPercent(boolean bool){
        if(bool){
            if(percent>0){
                percent--;
            }
        }else{
            if (percent<100.0){
                percent++;
            }
        }
    }
    public Double getX1(){
        return x1;
    }
    public Double getX2(){
        return x2;
    }
    public Double getY1(){
        return y1;
    }
    public Double getY2(){
        return y2;
    }
}
