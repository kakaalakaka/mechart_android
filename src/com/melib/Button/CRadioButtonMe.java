package com.melib.Button;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.CPaintMe;
import com.melib.Base.CControlMe;
import com.melib.Base.EVENTID;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CRadioButtonMe extends CCheckBoxMe {
	protected String groupName;

	public String GetGroupName() {
		return this.groupName;
	}

	public void SetGroupName(String value) {
		this.groupName = value;
	}

	public String GetControlType() {
		return "RadioButton";
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("groupname")) {
			type.argvalue = "text";
			value.argvalue = GetGroupName();
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "GroupName" }));
		return propertyNames;
	}

	public void OnClick(POINT mp, MouseButtonsA button, int clicks, int delta) {
		if (!IsChecked()) {
			SetChecked(!IsChecked());
		}
		CallMouseEvents(EVENTID.CLICK, mp.Clone(), button, clicks, delta);
		Invalidate();
	}

	public void OnPaintCheckButton(CPaintMe paint, RECT clipRect) {
		String backImage = GetPaintingBackImage();
		if ((backImage != null) && (backImage.length() > 0)) {
			paint.DrawImage(backImage, clipRect);
		} else {
			if (IsChecked()) {
				RECT rect = new RECT(clipRect.left + 2, clipRect.top + 2, clipRect.right - 3, clipRect.bottom - 3);
				if ((clipRect.right - clipRect.left < 4) || (clipRect.bottom - clipRect.top < 4)) {
					rect = clipRect.Clone();
				}
				paint.FillEllipse(GetPaintingButtonBackColor(), rect);
			}
			paint.DrawEllipse(GetPaintingButtonBorderColor(), 1.0F, 0, clipRect);
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("groupname")) {
			SetGroupName(value);
		} else {
			super.SetProperty(name, value);
		}
	}

	public void Update() {
		if (IsChecked()) {
			ArrayList<CControlMe> controls = null;
			if (GetParent() != null) {
				controls = GetParent().GetControls();
			} else {
				controls = GetNative().GetControls();
			}

			int controlsSize = controls.size();
			for (int idx = 0; idx < controlsSize; idx++) {
				CRadioButtonMe button = (CRadioButtonMe) ((controls.get(idx) instanceof CRadioButtonMe)
						? (CControlMe) controls.get(idx)
						: null);
				if ((button != null) && (button != this)) {
					if ((button.GetGroupName() != null) && (button.GetGroupName().equals(GetGroupName()))
							&& (button.IsChecked() == true)) {
						button.SetChecked(false);
						button.Invalidate();
					}
				}
			}
		}
	}
}
