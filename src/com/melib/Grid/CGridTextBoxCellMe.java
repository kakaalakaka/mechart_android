package com.melib.Grid;

import com.melib.Base.CControlMe;
import com.melib.TextBox.CTextBoxMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridTextBoxCellMe extends CGridControlCellMe {
	public CGridTextBoxCellMe() {
		CTextBoxMe textBox = new CTextBoxMe();
		textBox.SetBorderColor(com.melib.Base.COLOR.EMPTY);
		textBox.SetDisplayOffset(false);
		SetControl(textBox);
	}

	public CTextBoxMe GetTextBox() {
		if (GetControl() != null) {
			CControlMe textBox = GetControl();
			return (CTextBoxMe) ((textBox instanceof CTextBoxMe) ? textBox : null);
		}

		return null;
	}
}
