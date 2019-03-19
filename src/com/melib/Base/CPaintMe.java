package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public abstract interface CPaintMe {
	public abstract void AddArc(RECT paramRECT, float paramFloat1, float paramFloat2);

	public abstract void AddBezier(POINT[] paramArrayOfPOINT);

	public abstract void AddCurve(POINT[] paramArrayOfPOINT);

	public abstract void AddEllipse(RECT paramRECT);

	public abstract void AddLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

	public abstract void AddRect(RECT paramRECT);

	public abstract void AddPie(RECT paramRECT, float paramFloat1, float paramFloat2);

	public abstract void AddText(String paramString, FONT paramFONT, RECT paramRECT);

	public abstract void BeginPaint(int paramInt, RECT paramRECT1, RECT paramRECT2);

	public abstract void BeginPath();

	public abstract void ClearCaches();

	public abstract void ClipPath();

	public abstract void CloseFigure();

	public abstract void ClosePath();

	public abstract void Dispose();

	public abstract void DrawArc(long paramLong, float paramFloat1, int paramInt, RECT paramRECT, float paramFloat2,
			float paramFloat3);

	public abstract void DrawBezier(long paramLong, float paramFloat, int paramInt, POINT[] paramArrayOfPOINT);

	public abstract void DrawCurve(long paramLong, float paramFloat, int paramInt, POINT[] paramArrayOfPOINT);

	public abstract void DrawEllipse(long paramLong, float paramFloat, int paramInt, RECT paramRECT);

	public abstract void DrawEllipse(long paramLong, float paramFloat, int paramInt1, int paramInt2, int paramInt3,
			int paramInt4, int paramInt5);

	public abstract void DrawImage(String paramString, RECT paramRECT);

	public abstract void DrawLine(long paramLong, float paramFloat, int paramInt1, int paramInt2, int paramInt3,
			int paramInt4, int paramInt5);

	public abstract void DrawLine(long paramLong, float paramFloat, int paramInt, POINT paramPOINT1, POINT paramPOINT2);

	public abstract void DrawPath(long paramLong, float paramFloat, int paramInt);

	public abstract void DrawPie(long paramLong, float paramFloat1, int paramInt, RECT paramRECT, float paramFloat2,
			float paramFloat3);

	public abstract void DrawPolygon(long paramLong, float paramFloat, int paramInt, POINT[] paramArrayOfPOINT);

	public abstract void DrawPolyline(long paramLong, float paramFloat, int paramInt, POINT[] paramArrayOfPOINT);

	public abstract void DrawRect(long paramLong, float paramFloat, int paramInt, RECT paramRECT);

	public abstract void DrawRect(long paramLong, float paramFloat, int paramInt1, int paramInt2, int paramInt3,
			int paramInt4, int paramInt5);

	public abstract void DrawRoundRect(long paramLong, float paramFloat, int paramInt1, RECT paramRECT, int paramInt2);

	public abstract void DrawText(String paramString, long paramLong, FONT paramFONT, RECT paramRECT);

	public abstract void DrawText(String paramString, long paramLong, FONT paramFONT, RECTF paramRECTF);

	public abstract void DrawTextAutoEllipsis(String paramString, long paramLong, FONT paramFONT, RECT paramRECT);

	public abstract void EndPaint();

	public abstract void ExcludeClipPath();

	public abstract void FillEllipse(long paramLong, RECT paramRECT);

	public abstract void FillEllipse(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

	public abstract void FillGradientEllipse(long paramLong1, long paramLong2, RECT paramRECT, int paramInt);

	public abstract void FillGradientPath(long paramLong1, long paramLong2, RECT paramRECT, int paramInt);

	public abstract void FillGradientPolygon(long paramLong1, long paramLong2, POINT[] paramArrayOfPOINT, int paramInt);

	public abstract void FillGradientRect(long paramLong1, long paramLong2, RECT paramRECT, int paramInt1,
			int paramInt2);

	public abstract void FillPath(long paramLong);

	public abstract void FillPie(long paramLong, RECT paramRECT, float paramFloat1, float paramFloat2);

	public abstract void FillPolygon(long paramLong, POINT[] paramArrayOfPOINT);

	public abstract void FillRect(long paramLong, RECT paramRECT);

	public abstract void FillRect(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

	public abstract void FillRoundRect(long paramLong, RECT paramRECT, int paramInt);

	public abstract long GetColor(long paramLong);

	public abstract long GetPaintColor(long paramLong);

	public abstract POINT GetOffset();

	public abstract POINT Rotate(POINT paramPOINT1, POINT paramPOINT2, int paramInt);

	public abstract void SetClip(RECT paramRECT);

	public abstract void SetLineCap(int paramInt1, int paramInt2);

	public abstract void SetOffset(POINT paramPOINT);

	public abstract void SetOpacity(float paramFloat);

	public abstract void SetResourcePath(String paramString);

	public abstract void SetRotateAngle(int paramInt);

	public abstract void SetScaleFactor(double paramDouble1, double paramDouble2);

	public abstract void SetSmoothMode(int paramInt);

	public abstract void SetTextQuality(int paramInt);

	public abstract boolean SupportTransparent();

	public abstract SIZE TextSize(String paramString, FONT paramFONT);

	public abstract SIZEF TextSizeF(String paramString, FONT paramFONT);
}
