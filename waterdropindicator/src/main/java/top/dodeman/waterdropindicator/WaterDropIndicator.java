package top.dodeman.waterdropindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author juyao
 * @date 2018/6/22
 * @email juyao0909@gmail.com
 */
public class WaterDropIndicator extends View {
    Paint mPaint;
    DrawFilter drawFilter;
    Path mPath;
    int defaultColor;
    int selectColor;
    int itemNum;
    int itemSpace;
    int bigRadius;
    int currentItemPosition;
    ViewPager mViewPager;
    int smallRadius;
    int leftRadius;
    int rightRadius;
    int leftX;
    int rightX;
    int centerX;
    public WaterDropIndicator(Context context) {
        super(context);
        init();
    }
    public WaterDropIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WaterDropIndicator);
        defaultColor=ta.getColor(R.styleable.WaterDropIndicator_defaultColor, Color.WHITE);
        selectColor=ta.getColor(R.styleable.WaterDropIndicator_selectColor, Color.BLACK);
        bigRadius= (int) ta.getDimension(R.styleable.WaterDropIndicator_bigRadius,30);
        itemSpace= (int) ta.getDimension(R.styleable.WaterDropIndicator_itemSpace,90);
        leftRadius=bigRadius;
        rightRadius=bigRadius;
        smallRadius=bigRadius/3;
        leftX=bigRadius;
        rightX=bigRadius;
        centerX=bigRadius;
        ta.recycle();
        init();

    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
        invalidate();
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Log.i("qqq===","position="+position+",positionOffset="+positionOffset+",positionOffsetPixels="+positionOffsetPixels);
                int eachDistance=itemSpace+bigRadius*2;
                centerX=(int) (bigRadius+(eachDistance)*(position-1)+eachDistance*positionOffset);
                int totalLength= (int) ((itemSpace+bigRadius*2)*0.66);
                if(positionOffset<0.33f){
                    leftX=bigRadius+(itemSpace+bigRadius*2)*(position-1);
                    rightX=leftX+(centerX-leftX)*2;
                }else if(0.33f<positionOffset&&positionOffset<0.66f){
                    leftX=centerX-totalLength/2;
                    rightX=centerX+totalLength/2;
                }else{
                    if(rightX<bigRadius+(itemSpace+bigRadius*2)*((position-1)+1)){
                        leftX=centerX-totalLength/2;
                        rightX=centerX+totalLength/2;
                    }else{
                        rightX=bigRadius+(itemSpace+bigRadius*2)*((position-1)+1);
                        leftX=rightX-(rightX-centerX)*2;
                    }
                }
                int left=bigRadius+(itemSpace+bigRadius*2)*(position-1);
                int right=bigRadius+(itemSpace+bigRadius*2)*((position-1)+1);
                float leftRate=((float)(leftX-left))/((float)(right-left));
                if(leftRate<0.5){
                    leftRadius= (int) (2*(smallRadius-bigRadius)*leftRate+bigRadius);
                }else{
                    leftRadius= (int) (2*(bigRadius-smallRadius)*leftRate+2*smallRadius-bigRadius);
                }

                float rightRate=((float)(rightX-left))/((float)(right-left));
                if(rightRate<0.5){
                    rightRadius= (int) (2*(smallRadius-bigRadius)*rightRate+bigRadius);
                }else{
                    rightRadius= (int) (2*(bigRadius-smallRadius)*rightRate+2*smallRadius-bigRadius);
                }
                Log.i("leftRadius","leftRadius="+ leftRadius);
                invalidate();
            }
            @Override
            public void onPageSelected(int position) {
                currentItemPosition=(position-1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
               // Log.i("qqq===","当前状态："+state);

            }
        });
    }

    private void init(){

        drawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG);
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(defaultColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath=new Path();
        canvas.setDrawFilter(drawFilter);
        for(int i=0;i<itemNum;i++){
            canvas.drawCircle(bigRadius+((itemSpace+bigRadius*2)*i),bigRadius,bigRadius,mPaint);
        }
        if(itemNum!=0){
            Paint sPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
            sPaint.setColor(selectColor);
            canvas.drawCircle(leftX,bigRadius,leftRadius,sPaint);
            canvas.drawCircle(rightX,bigRadius,rightRadius,sPaint);
            mPath.moveTo(leftX,bigRadius-leftRadius);
            mPath.quadTo((rightX+leftX)/2,bigRadius-leftRadius+leftRadius/2,rightX,bigRadius-rightRadius);
            mPath.lineTo(rightX,bigRadius+rightRadius);
            mPath.quadTo((rightX+leftX)/2,bigRadius+rightRadius-rightRadius/2,leftX,bigRadius+leftRadius);
            mPath.lineTo(leftX,bigRadius-leftRadius);
            canvas.drawPath(mPath,sPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bigRadius*itemNum+(itemNum-1)*(itemSpace+bigRadius*2),bigRadius*2);
    }


}
