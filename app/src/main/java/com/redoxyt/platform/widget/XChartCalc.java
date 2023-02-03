package com.redoxyt.platform.widget;

/**
 * Created by zz.
 * description:
 */

public class XChartCalc {
    //Position位置
    private float posX = 0.0f;
    private float posY = 0.0f;

    public XChartCalc()
    {

    }

    //依圆心坐标，半径，扇形角度，计算出扇形终射线与圆弧交叉点的xy坐标
    public void CalcArcEndPointXY(float cirX, float cirY, float radius, float cirAngle){

        //将角度转换为弧度
        float arcAngle = (float) (Math.PI * cirAngle / 180.0);
        if (cirAngle < 90)
        {
            posX = cirX + (float)(Math.cos(arcAngle)) * radius;
            posY = cirY + (float)(Math.sin(arcAngle)) * radius;
        }
        else if (cirAngle == 90)
        {
            posX = cirX;
            posY = cirY + radius;
        }
        else if (cirAngle > 90 && cirAngle < 180)
        {
            arcAngle = (float) (Math.PI * (180 - cirAngle) / 180.0);
            posX = cirX - (float)(Math.cos(arcAngle)) * radius;
            posY = cirY + (float)(Math.sin(arcAngle)) * radius;
        }
        else if (cirAngle == 180)
        {
            posX = cirX - radius;
            posY = cirY;
        }
        else if (cirAngle > 180 && cirAngle < 270)
        {
            arcAngle = (float) (Math.PI * (cirAngle - 180) / 180.0);
            posX = cirX - (float)(Math.cos(arcAngle)) * radius;
            posY = cirY - (float)(Math.sin(arcAngle)) * radius;
        }
        else if (cirAngle == 270)
        {
            posX = cirX;
            posY = cirY - radius;
        }
        else
        {
            arcAngle = (float) (Math.PI * (360 - cirAngle) / 180.0);
            posX = cirX + (float)(Math.cos(arcAngle)) * radius;
            posY = cirY - (float)(Math.sin(arcAngle)) * radius;
        }

    }

    //依圆心坐标，半径，扇形角度，计算出扇形终射线与圆弧交叉点的xy坐标
    public void CalcTextEndPointXY(float cirX, float cirY, float radius, float cirAngle,float fontWidth){

        //将角度转换为弧度
        float arcAngle = (float) (Math.PI * cirAngle / 180.0);
        if (cirAngle == -90)
        {
            posX = cirX + (float)(Math.cos(arcAngle)) * radius-(fontWidth/2);
            posY = cirY + (float)(Math.sin(arcAngle)) * radius -20;
        }
        else if (cirAngle > -45 && cirAngle< 0)
        {
            posX = cirX + (float)(Math.cos(arcAngle)) * radius+5;
            posY = cirY + (float)(Math.sin(arcAngle)) * radius-5;
        }
        else if (cirAngle < 0 && cirAngle> -90)
        {
            posX = cirX + (float)(Math.cos(arcAngle)) * radius;
            posY = cirY + (float)(Math.sin(arcAngle)) * radius-20;
        }
        else if (cirAngle == 0)
        {
            posX = cirX + (float)(Math.cos(arcAngle)) * radius+10;
            posY = cirY + (float)(Math.sin(arcAngle)) * radius;
        }
        else if (cirAngle >45 && cirAngle < 90)
        {
            posX = cirX + (float)(Math.cos(arcAngle)) * radius;
            posY = cirY + (float)(Math.sin(arcAngle)) * radius+25;
        }
        else if (cirAngle >0 && cirAngle < 90)
        {
            posX = cirX + (float)(Math.cos(arcAngle)) * radius+10;
            posY = cirY + (float)(Math.sin(arcAngle)) * radius+20;
        }
        else if (cirAngle == 90)
        {
            posX = cirX-(fontWidth/2);
            posY = cirY + radius + 30;
        }
        else if (cirAngle > 90 && cirAngle < 135)
        {
            arcAngle = (float) (Math.PI * (180 - cirAngle) / 180.0);
            posX = cirX - (float)(Math.cos(arcAngle)) * radius - fontWidth;
            posY = cirY + (float)(Math.sin(arcAngle)) * radius+30;
        }
        else if (cirAngle > 90 && cirAngle < 180)
        {
            arcAngle = (float) (Math.PI * (180 - cirAngle) / 180.0);
            posX = cirX - (float)(Math.cos(arcAngle)) * radius - fontWidth-5;
            posY = cirY + (float)(Math.sin(arcAngle)) * radius+30;
        }
        else if (cirAngle == 180)
        {
            posX = cirX - radius-fontWidth-5;
            posY = cirY + (float)(Math.sin(arcAngle)) * radius+5;
        }
        else if (cirAngle > 180 && cirAngle < 215)
        {
            arcAngle = (float) (Math.PI * (cirAngle - 180) / 180.0);
            posX = cirX - (float)(Math.cos(arcAngle)) * radius-fontWidth-10;
            posY = cirY - (float)(Math.sin(arcAngle)) * radius;
        }

        else if (cirAngle > 180 && cirAngle < 270)
        {
            arcAngle = (float) (Math.PI * (cirAngle - 180) / 180.0);
            posX = cirX - (float)(Math.cos(arcAngle)) * radius-fontWidth-10;
            posY = cirY - (float)(Math.sin(arcAngle)) * radius-10;
        }
    }


    //
    public float getPosX() {
        return posX;
    }


    public float getPosY() {
        return posY;
    }

}
