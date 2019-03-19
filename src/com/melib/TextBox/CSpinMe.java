package com.melib.TextBox;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlMe;
import com.melib.Base.CControlHostMe;
import com.melib.Base.ControlMouseEvent;
import com.melib.Base.EVENTID;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;
import com.melib.Button.CButtonMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CSpinMe extends CTextBoxMe implements ControlMouseEvent {
	private int m_tick = 0;

	private int m_timerID = GetNewTimerID();

	protected boolean m_autoFormat = true;

	public boolean AutoFormat() {
		return this.m_autoFormat;
	}

	public void SetAutoFormat(boolean value) {
		this.m_autoFormat = value;
	}

	protected int m_digit = 0;
	protected CButtonMe m_downButton;

	public int GetDigit() {
		return this.m_digit;
	}

	public void SetDigit(int value) {
		this.m_digit = value;
		if (this.m_autoFormat) {
			if (this.m_text.equals("")) {
				this.m_text = "0";
			}
			double text = CStrMe.ConvertStrToDouble(this.m_text);
			SetText(GetValueByDigit(GetValue(), this.m_digit));
		}
	}

	public CButtonMe GetDownButton() {
		return this.m_downButton;
	}

	public void SetDownButton(CButtonMe value) {
		this.m_downButton = value;
	}

	protected boolean m_isAdding = false;

	public boolean IsAdding() {
		return this.m_isAdding;
	}

	public void SetIsAdding(boolean value) {
		if (this.m_isAdding != value) {
			this.m_isAdding = value;
			this.m_tick = 0;
			if (this.m_isAdding) {
				StartTimer(this.m_timerID, 100);
			} else {
				StopTimer(this.m_timerID);
			}
		}
	}

	protected boolean m_isReducing = false;

	public boolean IsReducing() {
		return this.m_isReducing;
	}

	public void SetIsReducing(boolean value) {
		if (this.m_isReducing != value) {
			this.m_isReducing = value;
			this.m_tick = 0;
			if (this.m_isReducing) {
				StartTimer(this.m_timerID, 100);
			} else {
				StopTimer(this.m_timerID);
			}
		}
	}

	protected double m_maximum = 100.0D;

	public double GetMaximum() {
		return this.m_maximum;
	}

	public void SetMaximum(double value) {
		this.m_maximum = value;
		if (GetValue() > value) {
			SetValue(value);
		}
	}

	protected double m_minimum = 0.0D;
	protected boolean m_showThousands;

	public double GetMinimum() {
		return this.m_minimum;
	}

	public void SetMinimum(double value) {
		this.m_minimum = value;
		if (GetValue() < value) {
			SetValue(value);
		}
	}

	public boolean ShowThousands() {
		return this.m_showThousands;
	}

	public void SetShowThousands(boolean value) {
		this.m_showThousands = value;
	}

	protected double m_step = 1.0D;
	protected CButtonMe m_upButton;

	public double GetStep() {
		return this.m_step;
	}

	public void SetStep(double value) {
		this.m_step = value;
	}

	public void SetText(String value) {
		super.SetText(FormatNum(value.replace(",", "")));
	}

	public CButtonMe GetUpButton() {
		return this.m_upButton;
	}

	public void SetUpButton(CButtonMe value) {
		this.m_upButton = value;
	}

	public double GetValue() {
		return CStrMe.ConvertStrToDouble(GetText().replace(",", ""));
	}

	public void SetValue(double value) {
		if (value > this.m_maximum) {
			value = this.m_maximum;
		}
		if (value < this.m_minimum) {
			value = this.m_minimum;
		}
		double oldValue = GetValue();
		SetText(FormatNum(GetValueByDigit(value, this.m_digit)));
		OnValueChanged();
	}

	public void Add() {
		SetValue(GetValue() + this.m_step);
	}

	public void CallControlMouseEvent(int eventID, Object sender, POINT mp, MouseButtonsA button, int clicks,
			int delta) {
		if (eventID == EVENTID.MOUSEDOWN) {
			if (sender == this.m_downButton) {
				Reduce();
				SetIsReducing(true);
				Invalidate();
			} else if (sender == this.m_upButton) {
				Add();
				SetIsAdding(true);
				Invalidate();
			}
		} else if (eventID == EVENTID.MOUSEUP) {
			if (sender == this.m_downButton) {
				SetIsReducing(false);
			} else if (sender == this.m_upButton) {
				SetIsAdding(false);
			}
		}
	}

	public void Dispose() {
		if (!IsDisposed()) {
			if (this.m_downButton != null) {
				this.m_downButton.UnRegisterEvent(this, EVENTID.MOUSEDOWN);
				this.m_downButton.UnRegisterEvent(this, EVENTID.MOUSEUP);
			}
			if (this.m_upButton != null) {
				this.m_upButton.UnRegisterEvent(this, EVENTID.MOUSEDOWN);
				this.m_upButton.UnRegisterEvent(this, EVENTID.MOUSEUP);
			}
		}
		super.Dispose();
	}

	public String FormatNum(String inputText) {
		if (this.m_showThousands) {
			inputText = inputText.replace(",", "");
			String theNewText = "";
			int pos = 0;
			boolean hasMinusSign = false;
			if (inputText.indexOf("-") == 0) {
				hasMinusSign = true;
				inputText = inputText.substring(1);
			}
			String textAfterDot = "";
			boolean hasDot = false;
			if (inputText.contains(".")) {
				hasDot = true;
				textAfterDot = inputText.substring(inputText.indexOf(".") + 1);
				inputText = inputText.substring(0, inputText.indexOf("."));
			}
			pos = inputText.length();
			while (pos >= 0) {
				int logicPos = inputText.length() - pos;
				if ((logicPos % 3 == 0) && (logicPos > 1)) {
					if (theNewText.equals("")) {
						theNewText = inputText.substring(pos, pos + 3);
					} else {
						theNewText = inputText.substring(pos, pos + 3) + "," + theNewText;
					}

				} else if (pos == 0) {
					if (theNewText.equals("")) {
						theNewText = inputText.substring(pos, logicPos % 3 + pos);
					} else {
						theNewText = inputText.substring(pos, logicPos % 3 + pos) + "," + theNewText;
					}
				}

				pos--;
			}
			if (hasMinusSign) {
				theNewText = "-" + theNewText;
			}
			if (hasDot) {
				theNewText = theNewText + "." + textAfterDot;
			}
			if (theNewText.indexOf(".") == 0) {
				theNewText = "0" + theNewText;
			}
			if (theNewText.indexOf("-.") == 0) {
				theNewText = theNewText.substring(0, 1) + "0" + theNewText.substring(1);
			}
			return theNewText;
		}

		return inputText;
	}

	public String GetControlType() {
		return "Spin";
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("autoformat")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertBoolToStr(AutoFormat());
		} else if (name.equals("digit")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertDoubleToStr(GetDigit());
		} else if (name.equals("maximum")) {
			type.argvalue = "double";
			value.argvalue = CStrMe.ConvertDoubleToStr(GetMaximum());
		} else if (name.equals("minimum")) {
			type.argvalue = "double";
			value.argvalue = CStrMe.ConvertDoubleToStr(GetMinimum());
		} else if (name.equals("showthousands")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(ShowThousands());
		} else if (name.equals("step")) {
			type.argvalue = "double";
			value.argvalue = CStrMe.ConvertDoubleToStr(GetStep());
		} else if (name.equals("value")) {
			type.argvalue = "double";
			value.argvalue = CStrMe.ConvertDoubleToStr(GetValue());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(
				new String[] { "AutoFormat", "Digit", "Maximum", "Minimum", "ShowThousands", "Step", "Value" }));
		return propertyNames;
	}

	protected String GetValueByDigit(double value, int digit) {
		if (digit > 0) {
			String format = String.format("%d", new Object[] { Integer.valueOf(digit) });
			format = "%." + format + "f";
			String str = String.format(format, new Object[] { Double.valueOf(value) });
			return str;
		}

		return String.format("%d", new Object[] { Integer.valueOf((int) value) });
	}

	public void OnLoad() {
		super.OnLoad();
		CControlHostMe host = GetNative().GetHost();
		if (this.m_downButton == null) {
			CControlMe tempVar = host.CreateInternalControl(this, "downbutton");
			this.m_downButton = ((CButtonMe) ((tempVar instanceof CButtonMe) ? tempVar : null));
			AddControl(this.m_downButton);
			this.m_downButton.RegisterEvent(this, EVENTID.MOUSEDOWN);
			this.m_downButton.RegisterEvent(this, EVENTID.MOUSEUP);
		}
		if (this.m_upButton == null) {
			CControlMe tempVar = host.CreateInternalControl(this, "upbutton");
			this.m_upButton = ((CButtonMe) ((tempVar instanceof CButtonMe) ? tempVar : null));
			AddControl(this.m_upButton);
			this.m_upButton.RegisterEvent(this, EVENTID.MOUSEDOWN);
			this.m_upButton.RegisterEvent(this, EVENTID.MOUSEUP);
		}
		Update();
	}

	public void OnPaste() {
		if (this.m_autoFormat) {
			CallEvents(EVENTID.PASTE);
			CControlHostMe host = GetNative().GetHost();
			String insert = host.Paste();
			if ((insert != null) && (insert.length() > 0)) {
				InsertWord(insert);
				SetText(FormatNum(GetValueByDigit(GetValue(), this.m_digit)));
				OnTextChanged();
				OnValueChanged();
				Invalidate();
			}
		} else {
			super.OnPaste();
		}
	}

	public void OnTimer(int timerID) {
		super.OnTimer(timerID);
		if (timerID == this.m_timerID) {
			if (this.m_tick > 10) {
				if ((this.m_tick > 30) || (this.m_tick % 3 == 1)) {
					if (this.m_isAdding) {
						Add();
						Invalidate();
					} else if (this.m_isReducing) {
						Reduce();
						Invalidate();
					}
				}
			}
			this.m_tick += 1;
		}
	}

	public void OnValueChanged() {
		CallEvents(EVENTID.VALUECHANGED);
	}

	public void Reduce() {
		SetValue(GetValue() - this.m_step);
	}

	public void SetProperty(String name, String value) {
		if (name.equals("autoformat")) {
			SetAutoFormat(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("digit")) {
			SetDigit(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("maximum")) {
			SetMaximum(CStrMe.ConvertStrToDouble(value));
		} else if (name.equals("minimum")) {
			SetMinimum(CStrMe.ConvertStrToDouble(value));
		} else if (name.equals("showthousands")) {
			SetShowThousands(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("step")) {
			SetStep(CStrMe.ConvertStrToDouble(value));
		} else if (name.equals("value")) {
			SetValue(CStrMe.ConvertStrToDouble(value));
		} else {
			super.SetProperty(name, value);
		}
	}

	public void SetRegion() {
		String textValue = this.m_text.replace(",", "");
		if ((textValue == null) || (textValue.equals(""))) {
			textValue = "0";
		}
		if ((textValue.indexOf(".") != -1) && (textValue.indexOf(".") == 0)) {
			textValue = "0" + textValue;
		}
		double value = CStrMe.ConvertStrToDouble(textValue);
		if (value > GetMaximum()) {
			value = GetMaximum();
		}
		if (value < GetMinimum()) {
			value = GetMinimum();
		}
		SetText(FormatNum(GetValueByDigit(value, this.m_digit)));
	}

	public void Update() {
		super.Update();
		int width = GetWidth();
		int height = GetHeight();
		int uBottom = 0;
		if (this.m_upButton != null) {
			int uWidth = this.m_upButton.GetWidth();
			POINT location = new POINT(width - uWidth, 0);
			this.m_upButton.SetLocation(location);
			SIZE size = new SIZE(uWidth, height / 2);
			this.m_upButton.SetSize(size);
			uBottom = this.m_upButton.GetBottom();
		}
		if (this.m_downButton != null) {
			int dWidth = this.m_downButton.GetWidth();
			POINT location = new POINT(width - dWidth, uBottom);
			this.m_downButton.SetLocation(location);
			SIZE size = new SIZE(dWidth, height - uBottom);
			this.m_downButton.SetSize(size);
		}
	}
}
