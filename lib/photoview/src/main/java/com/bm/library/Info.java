package com.bm.library;

import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

/**
 * Created by liuheng on 2015/8/19.
 */
public class Info implements Parcelable {

    // 内部图片在整个手机界面的位置
    RectF mRect = new RectF();

    // 控件在窗口的位置
    RectF mImgRect = new RectF();

    RectF mWidgetRect = new RectF();

    RectF mBaseRect = new RectF();

    PointF mScreenCenter = new PointF();

    float mScale;

    float mDegrees;

    ImageView.ScaleType mScaleType;

    public Info() {
    }

    public Info(RectF rect, RectF img, RectF widget, RectF base, PointF screenCenter, float scale, float degrees, ImageView.ScaleType scaleType) {
        mRect.set(rect);
        mImgRect.set(img);
        mWidgetRect.set(widget);
        mScale = scale;
        mScaleType = scaleType;
        mDegrees = degrees;
        mBaseRect.set(base);
        mScreenCenter.set(screenCenter);
    }

    protected Info(Parcel in) {
        mRect = in.readParcelable(RectF.class.getClassLoader());
        mImgRect = in.readParcelable(RectF.class.getClassLoader());
        mWidgetRect = in.readParcelable(RectF.class.getClassLoader());
        mBaseRect = in.readParcelable(RectF.class.getClassLoader());
        mScreenCenter = in.readParcelable(PointF.class.getClassLoader());
        mScale = in.readFloat();
        mDegrees = in.readFloat();
    }

    public RectF getmRect() {
        return mRect;
    }

    public void setmRect(RectF mRect) {
        this.mRect = mRect;
    }

    public RectF getmImgRect() {
        return mImgRect;
    }

    public void setmImgRect(RectF mImgRect) {
        this.mImgRect = mImgRect;
    }

    public RectF getmWidgetRect() {
        return mWidgetRect;
    }

    public void setmWidgetRect(RectF mWidgetRect) {
        this.mWidgetRect = mWidgetRect;
    }

    public RectF getmBaseRect() {
        return mBaseRect;
    }

    public void setmBaseRect(RectF mBaseRect) {
        this.mBaseRect = mBaseRect;
    }

    public PointF getmScreenCenter() {
        return mScreenCenter;
    }

    public void setmScreenCenter(PointF mScreenCenter) {
        this.mScreenCenter = mScreenCenter;
    }

    public float getmScale() {
        return mScale;
    }

    public void setmScale(float mScale) {
        this.mScale = mScale;
    }

    public float getmDegrees() {
        return mDegrees;
    }

    public void setmDegrees(float mDegrees) {
        this.mDegrees = mDegrees;
    }

    public ImageView.ScaleType getmScaleType() {
        return mScaleType;
    }

    public void setmScaleType(ImageView.ScaleType mScaleType) {
        this.mScaleType = mScaleType;
    }

    public static Creator<Info> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mRect, flags);
        dest.writeParcelable(mImgRect, flags);
        dest.writeParcelable(mWidgetRect, flags);
        dest.writeParcelable(mBaseRect, flags);
        dest.writeParcelable(mScreenCenter, flags);
        dest.writeFloat(mScale);
        dest.writeFloat(mDegrees);
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

}
