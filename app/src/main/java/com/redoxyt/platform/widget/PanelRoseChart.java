package com.redoxyt.platform.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz.
 * description:
 */

public class PanelRoseChart extends View {
    private int ScrWidth,ScrHeight;

    private float arrPer[] = null;
    //时间段
    private List<String> timeList = null;
    //时间段内已预约车辆
    private List<String> resersNum = new ArrayList<>();

    public PanelRoseChart(Context context) {
        super(context);
    }

    public PanelRoseChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelRoseChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //屏幕信息
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ScrHeight = dm.heightPixels;
        ScrWidth = dm.widthPixels-dip2px(35);

    }


    public void onDraw(Canvas canvas){
        if (timeList == null) return;
        //画布背景
        canvas.drawColor(Color.WHITE);
        //位置计算类
        XChartCalc xcalc = new XChartCalc();

        float cirX = ScrWidth / 2;
        float cirY = dip2px(160);
        float radius = dip2px(100) ;//半径

        float arcLeft = cirX - radius-dip2px(18);
        float arcTop = cirY - radius -dip2px(18);
        float arcRight = cirX + radius +dip2px(18);
        float arcBottom = cirY + radius +dip2px(18);
        RectF arcRF0 = new RectF(arcLeft ,arcTop,arcRight,arcBottom);
        Paint paintCircle = new Paint();
        paintCircle.setAntiAlias(true);
        paintCircle.setStrokeWidth(dip2px(16));
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setColor(Color.parseColor("#f2be85"));

        //扇形画笔
        Paint PaintArc = new Paint();
        PaintArc.setAntiAlias(true);

        //已经预约车辆画笔
        Paint PaintLabel = new Paint();
        PaintLabel.setColor(Color.BLACK);
        PaintLabel.setTextSize(24);
        PaintLabel.setTypeface(Typeface.DEFAULT_BOLD);
        PaintLabel.setAntiAlias(true);

        float Percentage = 0.0f;//每一个扇形的角度
        float CurrPer = -90f;//开始角度
        float NewRaidus = 0.0f;
        int i= 0;

        //将百分比转换为扇形半径长度
        Percentage = (360f / arrPer.length)-1;//29
        Percentage = (float)(Math.round(Percentage *100))/100;

        for(i=0; i<arrPer.length; i++)
        {
            if (i == arrPer.length-1){//最后一份补全
                Percentage = 360f-((Percentage+1)*(arrPer.length-1))-1;
            }
            //画最外面的圆
            canvas.drawArc(arcRF0, CurrPer, Percentage, false, paintCircle);
            //获取文字的宽度
            float fontWidth = getFontWidth(PaintLabel, timeList.get(i));
            xcalc.CalcTextEndPointXY(cirX, cirY,dip2px(130), CurrPer,fontWidth);
            //画最外面的文字--时间段
            canvas.drawText(timeList.get(i),xcalc.getPosX(), xcalc.getPosY() ,PaintLabel);

            //将百分比转换为新扇区的半径
            NewRaidus = radius * (arrPer[i]/ 100);
            NewRaidus = (float)(Math.round(NewRaidus *100))/100;

            float NewarcLeft = cirX - NewRaidus;
            float NewarcTop = cirY - NewRaidus ;
            float NewarcRight = cirX + NewRaidus ;
            float NewarcBottom = cirY + NewRaidus ;
            RectF NewarcRF = new RectF(NewarcLeft ,NewarcTop,NewarcRight,NewarcBottom);

            NewRaidus = dip2px(100);
            RectF NewarcRF1 = new RectF(cirX - NewRaidus ,cirY - NewRaidus,cirX + NewRaidus,cirY + NewRaidus);
            //画中心阴影
            PaintArc.setColor(Color.parseColor("#fbebda"));
            canvas.drawArc(NewarcRF1, CurrPer, Percentage, true, PaintArc);
            //分配颜色
            int v = (int)arrPer[i];
            if (v == 100){
                PaintArc.setColor(Color.parseColor("#ee7850"));
                canvas.drawArc(NewarcRF, CurrPer, Percentage, true, PaintArc);
            }else {
                if (v != 0){
                    //在饼图中显示所占比例
                    PaintArc.setColor(Color.parseColor("#4b93df"));
                    canvas.drawArc(NewarcRF, CurrPer, Percentage, true, PaintArc);
                }
            }

            //计算百分比标签
            xcalc.CalcArcEndPointXY(cirX, cirY, radius - radius/4, CurrPer + Percentage/2);
            //标识
            if (!resersNum.get(i).equals("0")){
                canvas.drawText(resersNum.get(i),xcalc.getPosX(), xcalc.getPosY() ,PaintLabel);
            }
            //下次的起始角度
            CurrPer += Percentage+1;
        }

    }

    public float getFontWidth(Paint paint, String text) {
        return paint.measureText(text);
    }

    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return fm.descent - fm.ascent;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, getResources().getDisplayMetrics());
    }

    /**
     *
     * @param total 总车辆数
     * @param prcent 时间段内已预约数
     * @param timeList 时间段
     */
    public void setDataList(float total,List<Integer> prcent,List<String> timeList) {
        this.timeList = timeList;
        resersNum.clear();
        arrPer = new float[prcent.size()];
        if (prcent != null && prcent.size()>0){
            for (int i = 0; i <prcent.size() ; i++) {
                int aInt = prcent.get(i);
                if (aInt >= total){
                    arrPer[i] = 100f;
                }else if (aInt == 0){
                    arrPer[i] = 0;
                }else if (aInt == 1){
                    arrPer[i] = 50f;
                }else {
                    arrPer[i] = 50f+((aInt-1)/total)*50f;
                }
                resersNum.add(Integer.toString(aInt));
            }

            invalidate();
        }
    }
}
