package com.melib.ToolTip;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.COLOR;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlMe;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.POINT;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class ToolTipMe extends CControlMe {
	private POINT m_lastMousePoint;

	public ToolTipMe() {
		SetAutoSize(true);
		SetBackColor(COLOR.ARGB(255, 255, 40));
		SetBorderColor(COLOR.CONTROLBORDER);
		SetTopMost(true);
		SetVisible(false);
	}

	private int m_timerID = GetNewTimerID();

	protected int m_remainAutoPopDelay;

	protected int m_remainInitialDelay;

	protected int m_autoPopDelay = 5000;

	public int AutoPopDelay() {
		return this.m_autoPopDelay;
	}

	public void SetAutoPopDelay(int autoPopDelay) {
		this.m_autoPopDelay = autoPopDelay;
	}

	protected int m_initialDelay = 500;
	protected boolean m_showAlways;
	protected boolean m_useAnimation;

	public int InitialDelay() {
		return this.m_initialDelay;
	}

	public void SetInitialDelay(int initialDelay) {
		this.m_initialDelay = initialDelay;
	}

	public boolean ShowAlways() {
		return this.m_showAlways;
	}

	public void SetShowAlways(boolean showAlways) {
		this.m_showAlways = showAlways;
	}

	public boolean UseAnimation() {
		return this.m_useAnimation;
	}

	public void SetUseAnimation(boolean useAnimation) {
		this.m_useAnimation = useAnimation;
	}

	public void Dispose() {
		if (!IsDisposed()) {
			StopTimer(this.m_timerID);
		}
		super.Dispose();
	}

	public String GetControlType() {
		return "ToolTip";
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("autopopupdelay")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(AutoPopDelay());
		} else if (name.equals("initialdelay")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(InitialDelay());
		} else if (name.equals("showalways")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(ShowAlways());
		} else if (name.equals("useanimation")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(UseAnimation());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames
				.addAll(Arrays.asList(new String[] { "AutoPopupDelay", "InitialDelay", "ShowAlways", "UseAnimation" }));
		return propertyNames;
	}

	public void Hide() {
		SetVisible(false);
	}

	public void OnLoad() {
		super.OnLoad();
		this.m_lastMousePoint = GetMousePoint();
		StartTimer(this.m_timerID, 10);
	}

	public void OnTimer(int timerID) {
		super.OnTimer(timerID);
		if (this.m_timerID == timerID) {
			POINT mp = GetMousePoint();
			if (!this.m_showAlways) {
				if ((this.m_lastMousePoint.x != mp.x) || (this.m_lastMousePoint.y != mp.y)) {
					SetVisible(false);
				}
			}
			this.m_lastMousePoint = mp;
			if (this.m_remainAutoPopDelay > 0) {
				this.m_remainAutoPopDelay -= 10;
				if (this.m_remainAutoPopDelay <= 0) {
					SetVisible(false);
				}
			}
			if (this.m_remainInitialDelay > 0) {
				this.m_remainInitialDelay -= 10;
				if (this.m_remainInitialDelay <= 0) {
					SetVisible(true);
				}
			}
		}
	}

	public void OnVisibleChanged() {
		super.OnVisibleChanged();
		if (this.m_native != null) {
			if (IsVisible()) {
				this.m_native.AddControl(this);
				this.m_remainAutoPopDelay = this.m_autoPopDelay;
				this.m_remainInitialDelay = 0;
			} else {
				this.m_native.RemoveControl(this);
				StartTimer(this.m_timerID, 10);
				this.m_remainAutoPopDelay = 0;
				this.m_remainInitialDelay = 0;
			}
			GetNative().Invalidate();
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("autopopupdelay")) {
			SetAutoPopDelay(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("initialdelay")) {
			SetInitialDelay(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("showalways")) {
			SetShowAlways(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("useanimation")) {
			SetUseAnimation(CStrMe.ConvertStrToBool(value));
		} else {
			super.SetProperty(name, value);
		}
	}

	public void Show() {
		this.m_remainAutoPopDelay = 0;
		this.m_remainInitialDelay = this.m_initialDelay;
		SetVisible(this.m_initialDelay == 0);
		GetNative().Invalidate();
	}
}
