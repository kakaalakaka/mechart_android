package com.melib.Base;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public abstract class CControlHostMe {
	public abstract boolean AllowOperate();

	public abstract void SetAllowOperate(boolean paramBoolean);

	public abstract boolean AllowPartialPaint();

	public abstract void SetAllowPartialPaint(boolean paramBoolean);

	public abstract INativeBaseMe GetNative();

	public abstract void SetNative(INativeBaseMe paramINativeBase);

	public abstract void BeginInvoke(CControlMe paramControlA, Object paramObject);

	public abstract void Copy(String paramString);

	public abstract CControlMe CreateInternalControl(CControlMe paramControlA, String paramString);

	public abstract POINT GetMousePoint();

	public abstract int GetIntersectRect(RefObject<RECT> paramRefObject1, RefObject<RECT> paramRefObject2,
			RefObject<RECT> paramRefObject3);

	public abstract SIZE GetSize();

	public abstract int GetUnionRect(RefObject<RECT> paramRefObject1, RefObject<RECT> paramRefObject2,
			RefObject<RECT> paramRefObject3);

	public abstract void Invalidate(RECT paramRECT);

	public abstract void Invalidate();

	public abstract void Invoke(CControlMe paramControlA, Object paramObject);

	public abstract String Paste();

	public abstract void ShowToolTip(String paramString, POINT paramPOINT);

	public abstract void StartTimer(int paramInt1, int paramInt2);

	public abstract void StopTimer(int paramInt);
}
