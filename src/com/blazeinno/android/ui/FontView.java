//------------------------------------------------------------------------------
// KeyView.java
// Copyright (C) 2010 Krich Charoenpoldee
//------------------------------------------------------------------------------

package com.blazeinno.android.ui;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class FontView extends View {
    private int					m_nNameFontSize;
    private int					m_nSampleFontSize;
    private Paint				m_Paint;
    private	int					m_nBorderWidth;

    private	int					m_nHeight		= 0;

    private	Typeface			m_TypeFace		= Typeface.DEFAULT;
    private String				m_Name			= "";
    private String				m_TextLine1		= "The quick brown fox jumps over the lazy dog";
    private String				m_TextLine2		= "ชีวิตไม่แน่นอน แต่เราต้องนอนแน่ๆ";
    private	int					m_nNameColor	= Color.WHITE;
    private	int					m_nSampleColor	= Color.GRAY;

    private float				m_ScreenDensityDpi;

    public FontView(Context context) {
        super(context);
        initView();
    }

    public FontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
    	int	parentWidth = MeasureSpec.getSize(widthMeasureSpec);
   		setMeasuredDimension(parentWidth, m_nHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    	Paint	paint	= m_Paint;
    	int		x		= m_nBorderWidth;
    	int		y		= m_nBorderWidth + m_nNameFontSize;

		paint.setTypeface(Typeface.DEFAULT);
		paint.setTextSize(m_nNameFontSize);
		paint.setColor(m_nNameColor);
		canvas.drawText(m_Name, x, y, paint);

		paint.setTypeface(m_TypeFace);
    	y				= y + m_nBorderWidth + m_nSampleFontSize;
		paint.setTextSize(m_nSampleFontSize);
		paint.setColor(m_nSampleColor);
		canvas.drawText(m_TextLine1, x, y, paint);

    	y				= y + m_nBorderWidth + m_nSampleFontSize;
		canvas.drawText(m_TextLine2, x, y, paint);
    }

    private void getWindowMetrics() {
     	DisplayMetrics metrics		= new DisplayMetrics();
     	WindowManager windowManager	= (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
     	windowManager.getDefaultDisplay().getMetrics(metrics);

     	m_ScreenDensityDpi			= metrics.densityDpi;
	}

    private float getHeightPerMM(float mm) {
    	return mm * (m_ScreenDensityDpi / (float)25.4);
    }

    private void initView() {
    	getWindowMetrics();

    	m_nBorderWidth		= (int)getHeightPerMM(1);
    	m_nNameFontSize		= (int)getHeightPerMM(2);
    	m_nSampleFontSize	= (int)getHeightPerMM(3);
    	m_nHeight			= m_nNameFontSize + (m_nSampleFontSize * 2) + m_nBorderWidth * 5;

        m_Paint				= new Paint();
        m_Paint.setAntiAlias(true);//it is just MUCH better looking
        m_Paint.setStrokeWidth(0);
    }

    public void setTypeFace(Typeface typeFace)	{ m_TypeFace		= typeFace; }
    public void setName(String name)			{ m_Name			= name; }
    public void setTextLine1(String textLine1)	{ m_TextLine1		= textLine1; }
    public void setTextLine2(String textLine2)	{ m_TextLine2		= textLine2; }
    public void setNameColor(int color)			{ m_nNameColor		= color; }
    public void setSampleColor(int color)		{ m_nSampleColor	= color; }
    public void setFontSize(int nameFontSize, int sampleFontSize) {
    	m_nNameFontSize 	= nameFontSize;
    	m_nSampleFontSize 	= sampleFontSize;
    	m_nHeight			= m_nNameFontSize + (m_nSampleFontSize * 2) + m_nBorderWidth * 4;
    }
}