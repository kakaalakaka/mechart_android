package com.melib.Grid;

import com.melib.Base.CControlMe;
import com.melib.Label.CLabelMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridLabelCellMe extends CGridControlCellMe {
	public CGridLabelCellMe() {
		CLabelMe label = new CLabelMe();
		label.SetBorderColor(com.melib.Base.COLOR.EMPTY);
		label.SetDisplayOffset(false);
		SetControl(label);
	}

	public CLabelMe GetLabel() {
		if (GetControl() != null) {
			CControlMe label = GetControl();
			return (CLabelMe) ((label instanceof CLabelMe) ? label : null);
		}

		return null;
	}
}
