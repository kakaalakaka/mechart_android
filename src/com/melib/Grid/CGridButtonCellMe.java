package com.melib.Grid;

import com.melib.Base.CControlMe;
import com.melib.Button.CButtonMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CGridButtonCellMe extends CGridControlCellMe {
	public CGridButtonCellMe() {
		CButtonMe button = new CButtonMe();
		button.SetBorderColor(com.melib.Base.COLOR.EMPTY);
		button.SetDisplayOffset(false);
		SetControl(button);
	}

	public CButtonMe GetButton() {
		if (GetControl() != null) {
			CControlMe control = GetControl();
			return (CButtonMe) ((control instanceof CButtonMe) ? control : null);
		}
		return null;
	}
}
