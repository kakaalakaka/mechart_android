package com.melib.Button;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CStrMe;
import com.melib.Base.EVENTID;
import com.melib.Base.FONT;
import com.melib.Base.HorizontalAlignA;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CCheckBoxMe extends CButtonMe {
	public CCheckBoxMe() {
		SetBackColor(COLOR.EMPTY);
		SetBorderColor(COLOR.EMPTY);
	}

	protected HorizontalAlignA m_buttonAlign = HorizontalAlignA.Left;

	public HorizontalAlignA GetButtonAlign() {
		return this.m_buttonAlign;
	}

	public void SetButtonAlign(HorizontalAlignA value) {
		this.m_buttonAlign = value;
	}

	protected long m_buttonBackColor = COLOR.CONTROLBORDER;

	public long GetButtonBackColor() {
		return this.m_buttonBackColor;
	}

	public void SetButtonBackColor(long value) {
		this.m_buttonBackColor = value;
	}

	protected long m_buttonBorderColor = COLOR.CONTROLBORDER;

	public long GetButtonBorderColor() {
		return this.m_buttonBorderColor;
	}

	public void SetButtonBorderColor(long value) {
		this.m_buttonBorderColor = value;
	}

	protected SIZE m_buttonSize = new SIZE(16, 16);

	public SIZE GetButtonSize() {
		return this.m_buttonSize.Clone();
	}

	public void SetButtonSize(SIZE value) {
		this.m_buttonSize = value.Clone();
	}

	protected boolean m_checked = false;
	protected String m_checkedBackImage;
	protected String m_checkHoveredBackImage;

	public boolean IsChecked() {
		return this.m_checked;
	}

	public void SetChecked(boolean value) {
		if (this.m_checked != value) {
			this.m_checked = value;
			OnCheckedChanged();
		}
	}

	protected String m_checkPushedBackImage;
	protected String m_disableCheckedBackImage;

	public String GetCheckedBackImage() {
		return this.m_checkedBackImage;
	}

	public void SetCheckedBackImage(String value) {
		this.m_checkedBackImage = value;
	}

	public String GetCheckHoveredBackImage() {
		return this.m_checkHoveredBackImage;
	}

	public void SetCheckHoveredBackImage(String value) {
		this.m_checkHoveredBackImage = value;
	}

	public String GetCheckPushedBackImage() {
		return this.m_checkPushedBackImage;
	}

	public void SetCheckPushedBackImage(String value) {
		this.m_checkPushedBackImage = value;
	}

	public String GetDisableCheckedBackImage() {
		return this.m_disableCheckedBackImage;
	}

	public void SetDisableCheckedBackImage(String value) {
		this.m_disableCheckedBackImage = value;
	}

	public String GetControlType() {
		return "CheckBox";
	}

	protected long GetPaintingBackColor() {
		long backColor = GetBackColor();
		if ((backColor != COLOR.EMPTY) && (COLOR.DISABLEDCONTROL != COLOR.EMPTY)) {
			if (!IsPaintEnabled(this)) {
				return COLOR.DISABLEDCONTROL;
			}
		}
		return backColor;
	}

	protected long GetPaintingButtonBackColor() {
		long buttonBackColor = this.m_buttonBackColor;
		if ((buttonBackColor != COLOR.EMPTY) && (COLOR.DISABLEDCONTROL != COLOR.EMPTY)) {
			if (!IsPaintEnabled(this)) {
				return COLOR.DISABLEDCONTROL;
			}
		}
		return buttonBackColor;
	}

	protected long GetPaintingButtonBorderColor() {
		return this.m_buttonBorderColor;
	}

	protected String GetPaintingBackImage() {
		String backImage = null;
		INativeBaseMe nativeBase = GetNative();
		if (this.m_checked) {
			if (IsEnabled()) {
				if (this == nativeBase.GetPushedControl()) {
					backImage = this.m_checkPushedBackImage;
				} else if (this == nativeBase.GetHoveredControl()) {
					backImage = this.m_checkHoveredBackImage;
				} else {
					backImage = this.m_checkedBackImage;
				}

			} else {
				backImage = this.m_disableCheckedBackImage;
			}
		}
		if (backImage != null) {
			return backImage;
		}

		return super.GetPaintingBackImage();
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("buttonalign")) {
			type.argvalue = "enum:HorizontalAlignA";
			value.argvalue = CStrMe.ConvertHorizontalAlignToStr(GetButtonAlign());
		} else if (name.equals("buttonbackcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetButtonBackColor());
		} else if (name.equals("buttonbordercolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetButtonBorderColor());
		} else if (name.equals("buttonsize")) {
			type.argvalue = "size";
			value.argvalue = CStrMe.ConvertSizeToStr(GetButtonSize());
		} else if (name.equals("checked")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsChecked());
		} else if (name.equals("checkedbackimage")) {
			type.argvalue = "text";
			value.argvalue = GetCheckedBackImage();
		} else if (name.equals("checkhoveredbackimage")) {
			type.argvalue = "text";
			value.argvalue = GetCheckHoveredBackImage();
		} else if (name.equals("checkpushedbackimage")) {
			type.argvalue = "text";
			value.argvalue = GetCheckPushedBackImage();
		} else if (name.equals("disablecheckedbackimage")) {
			type.argvalue = "text";
			value.argvalue = GetDisableCheckedBackImage();
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "ButtonAlign", "ButtonBackColor", "ButtonBorderColor",
				"ButtonSize", "Checked", "CheckedBackImage", "CheckHoveredBackimage", "CheckPushedBackImage",
				"DisableCheckedBackImage" }));
		return propertyNames;
	}

	public void OnCheckedChanged() {
		CallEvents(EVENTID.CHECKEDCHANGED);
		Update();
	}

	public void OnClick(POINT mp, MouseButtonsA button, int clicks, int delta) {
		SetChecked(!IsChecked());
		CallMouseEvents(EVENTID.CLICK, mp.Clone(), button, clicks, delta);
		Invalidate();
	}

	public void OnPaintBackground(CPaintMe paint, RECT clipRect) {
		RECT rect = new RECT(0, 0, GetWidth(), GetHeight());
		paint.FillRoundRect(GetPaintingBackColor(), rect, this.m_cornerRadius);
	}

	public void OnPaintCheckButton(CPaintMe paint, RECT clipRect) {
		String backImage = GetPaintingBackImage();
		if ((backImage != null) && (backImage.length() > 0)) {
			paint.DrawImage(backImage, clipRect);
		} else {
			if (this.m_checked) {
				RECT rect = new RECT(clipRect.left + 2, clipRect.top + 2, clipRect.right - 2, clipRect.bottom - 2);
				if ((clipRect.right - clipRect.left < 4) || (clipRect.bottom - clipRect.top < 4)) {
					rect = clipRect.Clone();
				}
				paint.FillRect(GetPaintingButtonBackColor(), rect);
			}
			paint.DrawRect(GetPaintingButtonBorderColor(), 1.0F, 0, clipRect);
		}
	}

	public void OnPaintForeground(CPaintMe paint, RECT clipRect) {
		String text = GetText();
		int width = GetWidth();
		int height = GetHeight();
		if ((width > 0) && (height > 0)) {
			RECT rect = new RECT(5, (height - this.m_buttonSize.cy) / 2, 5 + this.m_buttonSize.cx,
					(height + this.m_buttonSize.cy) / 2);
			POINT point = new POINT();
			SIZE size = new SIZE();
			FONT font = GetFont();
			if ((text != null) && (text.length() > 0)) {
				size = paint.TextSize(text, font);
				point.x = (rect.right + 5);
				point.y = ((height - size.cy) / 2);
			}

			if (this.m_buttonAlign == HorizontalAlignA.Center) {
				rect.left = ((width - this.m_buttonSize.cx) / 2);
				rect.right = ((width + this.m_buttonSize.cx) / 2);
				point.x = (rect.right + 5);
			} else if (this.m_buttonAlign == HorizontalAlignA.Right) {
				rect.left = (width - this.m_buttonSize.cx - 5);
				rect.right = (width - 5);
				point.x = (rect.left - size.cx - 5);
			}
			OnPaintCheckButton(paint, rect);
			if ((text != null) && (text.length() > 0)) {
				RECT rect2 = new RECT(point.x, point.y, point.x + size.cx + 1, point.y + size.cy);
				long foreColor = GetPaintingForeColor();
				if ((AutoEllipsis()) && ((rect2.right > clipRect.right) || (rect2.bottom > clipRect.bottom))) {
					if (rect2.right > clipRect.right) {
						rect2.right = clipRect.right;
					}
					if (rect2.bottom > clipRect.bottom) {
						rect2.bottom = clipRect.bottom;
					}
					paint.DrawTextAutoEllipsis(text, foreColor, font, rect2);
				} else {
					paint.DrawText(text, foreColor, font, rect2);
				}
			}
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("buttonalign")) {
			SetButtonAlign(CStrMe.ConvertStrToHorizontalAlign(value));
		} else if (name.equals("buttonbackcolor")) {
			SetButtonBackColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("buttonbordercolor")) {
			SetButtonBorderColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("buttonsize")) {
			SetButtonSize(CStrMe.ConvertStrToSize(value));
		} else if (name.equals("checked")) {
			SetChecked(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("checkedbackimage")) {
			SetCheckedBackImage(value);
		} else if (name.equals("checkhoveredbackimage")) {
			SetCheckHoveredBackImage(value);
		} else if (name.equals("checkpushedbackimage")) {
			SetCheckPushedBackImage(value);
		} else if (name.equals("disablecheckedbackimage")) {
			SetDisableCheckedBackImage(value);
		} else {
			super.SetProperty(name, value);
		}
	}
}
