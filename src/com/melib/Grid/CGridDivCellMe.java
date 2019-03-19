package com.melib.Grid;

import com.melib.Base.CControlMe;
import com.melib.Layout.CDivMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridDivCellMe extends CGridControlCellMe {
	public CGridDivCellMe() {
		CDivMe div = new CDivMe();
		div.SetBorderColor(com.melib.Base.COLOR.EMPTY);
		div.SetDisplayOffset(false);
		SetControl(div);
	}

	public CDivMe GetRadioButton() {
		if (GetControl() != null) {
			CControlMe div = GetControl();
			return (CDivMe) ((div instanceof CDivMe) ? div : null);
		}

		return null;
	}
}
