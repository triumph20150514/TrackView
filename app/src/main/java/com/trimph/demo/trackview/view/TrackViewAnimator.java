package com.trimph.demo.trackview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;

import com.trimph.demo.trackview.R;

/**
 * Created by tao on 2016/8/8.
 */

public class TrackViewAnimator extends View {

    public String TAG = "Trimph";
    public Paint paint;
    public Path path;
    public PathMeasure pathMeasure;
    public int mWidth, mHight;
    public float[] pos, tan;

    private PointF start, end, control;
    public int centerX, centerY;

    public TrackViewAnimator(Context context) {
        this(context, null);
    }

    public TrackViewAnimator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackViewAnimator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        init2();
    }

    private void init2() {

        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control = new PointF(0, 0);

    }

    public Context context;
    public Bitmap bitmap;
    public Matrix matrix;
    public float currentValue;


    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        path = new Path();


        pos = new float[2];//坐标值
        tan = new float[2];//正切值

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher, options);
        matrix = new Matrix();


    }


    public void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w / 2;
        mHight = h / 2;
        Log.e(TAG, "mwidth" + mWidth + "mhight" + mHight);

        //初始化值
        start.y = -mHight * 2 / 3;
        start.x = -mWidth;

        end.y = mHight;
        end.x = -mWidth;

        control.y = -mHight / 2;
        control.x = -mWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth, mHight);
        control.x = 185;
        control.y = -290;

        paint.setColor(Color.parseColor("#B7B7CF"));
//        path.addOval(Path.Direction.CCW);
        path.addCircle(0, 0, 300, Path.Direction.CW);

        paint.setColor(Color.parseColor("#B7B7CF"));
//        path.addCircle(0, 0, 100, Path.Direction.CCW);
        Log.e(TAG, "currentValue" + currentValue);

        currentValue = (float) (currentValue + 0.002);

        if (currentValue >= 1) {
            currentValue = 0;
        }
        pathMeasure = new PathMeasure();
        pathMeasure.setPath(path, false);
        int length = (int) pathMeasure.getLength();
        pathMeasure.getPosTan(pathMeasure.getLength() * currentValue, pos, tan); ///
        matrix.reset();
        // 获取当前位置的坐标以及趋势的矩阵
//        pathMeasure.getMatrix(pathMeasure.getLength() * currentValue, matrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
//        matrix.preTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2);   // <-- 将图片绘制中心调整到与当前点重合(注意:此处是前乘pre)

        float degress = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI); //计算角度
        matrix.postRotate(degress, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        matrix.postTranslate(pos[0] - bitmap.getWidth() / 2, pos[1] - bitmap.getHeight() / 2);//位移到当前点
        canvas.drawPath(path, paint);
        canvas.drawBitmap(bitmap, matrix, paint);

        //* 会只待俺
        paint.setStrokeWidth(4);
        paint.setColor(Color.BLACK);
        canvas.drawPoint(start.x, start.y, paint);
        canvas.drawPoint(end.x, end.y, paint);
        canvas.drawPoint(control.x, control.y, paint);
        ///绘制先
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
        canvas.drawLine(start.x, start.y, control.x, control.y, paint);
        canvas.drawLine(control.x, control.y, end.x, end.y, paint);

        ///被蛇儿先
        paint.setStrokeWidth(8);
        paint.setColor(Color.parseColor("#B7B7CF"));
        Path mPath = new Path();
        mPath.moveTo(end.x, end.y);
        mPath.quadTo(control.x, control.y, start.x, start.y);
        canvas.drawPath(mPath, paint);

        pathMeasure.setPath(mPath, false);
        pathMeasure.getPosTan(pathMeasure.getLength() * currentValue, pos, tan); ///
        matrix.reset();

        // 获取当前位置的坐标以及趋势的矩阵
        pathMeasure.getMatrix(pathMeasure.getLength() * currentValue, matrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
        matrix.preTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2);   // <-- 将图片绘制中心调整到与当前点重合(注意:此处是前乘pre)
        canvas.drawBitmap(bitmap, matrix, paint);

        invalidate();
//        pathMeasure.setPath(path, true);
//        length = (int) pathMeasure.getLength();
//        pathMeasure.setPath(path,false);
//        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        control.x = event.getX();
        control.y = event.getY();
        Log.e(TAG, "control" + control.x + "Y:" + control.y);
        invalidate();
        return true;
    }
}
