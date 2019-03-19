package com.melib.Chart;

import com.melib.Base.CPaintMe;
import com.melib.Base.FONT;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public abstract class CPlotMe {
	public abstract AttachVScale GetAttachVScale();

	public abstract void SetAttachVScale(AttachVScale paramAttachVScale);

	public abstract long GetColor();

	public abstract void SetColor(long paramLong);

	public abstract CDivMe GetDiv();

	public abstract void SetDiv(CDivMe paramCDiv);

	public abstract boolean GetDrawGhost();

	public abstract void SetDrawGhost(boolean paramBoolean);

	public abstract boolean IsEnabled();

	public abstract void SetEnabled(boolean paramBoolean);

	public abstract FONT GetFont();

	public abstract void SetFont(FONT paramFONT);

	public abstract boolean IsDisposed();

	public abstract int GetLineStyle();

	public abstract void SetLineStyle(int paramInt);

	public abstract int GetLineWidth();

	public abstract void SetLineWidth(int paramInt);

	public abstract String GetPlotType();

	public abstract void SetPlotType(String paramString);

	public abstract boolean IsSelected();

	public abstract void SetSelected(boolean paramBoolean);

	public abstract long GetSelectedColor();

	public abstract void SetSelectedColor(long paramLong);

	public abstract SelectedPoint GetSelectedPoint();

	public abstract void SetSelectedPoint(SelectedPoint paramSelectedPoint);

	public abstract String GetText();

	public abstract void SetText(String paramString);

	public abstract boolean IsVisible();

	public abstract void SetVisible(boolean paramBoolean);

	public abstract int GetZOrder();

	public abstract void SetZOrder(int paramInt);

	public abstract void Dispose();

	public abstract boolean OnCreate(POINT paramPOINT);

	public abstract void OnMoveEnd();

	public abstract void OnMoveStart();

	public abstract void OnMoving();

	public abstract boolean OnSelect();

	public abstract void Render(CPaintMe paramCPaint);
}
