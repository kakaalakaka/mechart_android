package com.melib.Base;

import java.util.ArrayList;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public abstract interface INativeBaseMe {
	public abstract boolean AllowScaleSize();

	public abstract void SetAllowScaleSize(boolean paramBoolean);

	public abstract SIZE GetDisplaySize();

	public abstract void SetDisplaySize(SIZE paramSIZE);

	public abstract CControlMe GetFocusedControl();

	public abstract void SetFocusedControl(CControlMe paramControlA);

	public abstract CControlHostMe GetHost();

	public abstract void SetHost(CControlHostMe paramControlHost);

	public abstract CControlMe GetHoveredControl();

	public abstract boolean IsDisposed();

	public abstract POINT GetMousePoint();

	public abstract float GetOpacity();

	public abstract void SetOpacity(float paramFloat);

	public abstract CPaintMe GetPaint();

	public abstract void SetPaint(CPaintMe paramCPaint);

	public abstract CControlMe GetPushedControl();

	public abstract String GetResourcePath();

	public abstract void SetResourcePath(String paramString);

	public abstract int GetRotateAngle();

	public abstract void SetRotateAngle(int paramInt);

	public abstract SIZE GetScaleSize();

	public abstract void SetScaleSize(SIZE paramSIZE);

	public abstract void AddControl(CControlMe paramControlA);

	public abstract void BringToFront(CControlMe paramControlA);

	public abstract void CancelDragging();

	public abstract void ClearControls();

	public abstract int ClientX(CControlMe paramControlA);

	public abstract int ClientY(CControlMe paramControlA);

	public abstract boolean ContainsControl(CControlMe paramControlA);

	public abstract void Dispose();

	public abstract CControlMe FindControl(POINT paramPOINT);

	public abstract CControlMe FindControl(POINT paramPOINT, CControlMe paramControlA);

	public abstract CControlMe FindControl(String paramString);

	public abstract ArrayList<CControlMe> GetControls();

	public abstract void InsertControl(int paramInt, CControlMe paramControlA);

	public abstract void Invalidate();

	public abstract void Invalidate(CControlMe paramControlA);

	public abstract void Invalidate(RECT paramRECT);

	public abstract void OnPaint(RECT paramRECT);

	public abstract void OnResize();

	public abstract void OnTimer(int paramInt);

	public abstract void OnTouchBegin(ArrayList<CTouch> paramArrayList);

	public abstract void OnTouchCancel(ArrayList<CTouch> paramArrayList);

	public abstract void OnTouchEnd(ArrayList<CTouch> paramArrayList);

	public abstract void OnTouchMove(ArrayList<CTouch> paramArrayList);

	public abstract void RemoveControl(CControlMe paramControlA);

	public abstract void SetAlign(ArrayList<CControlMe> paramArrayList);

	public abstract void SetAnchor(ArrayList<CControlMe> paramArrayList, SIZE paramSIZE);

	public abstract void SendToBack(CControlMe paramControlA);

	public abstract void SetDock(ArrayList<CControlMe> paramArrayList);

	public abstract void StartTimer(CControlMe paramControlA, int paramInt1, int paramInt2);

	public abstract void StopTimer(int paramInt);

	public abstract void Update();
}
