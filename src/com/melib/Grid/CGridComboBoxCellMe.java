package com.melib.Grid;

import com.melib.Base.CControlMe;
import com.melib.TextBox.CComboBoxMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridComboBoxCellMe extends CGridControlCellMe {
	public CGridComboBoxCellMe() {
		CComboBoxMe comboBox = new CComboBoxMe();
		comboBox.SetBorderColor(com.melib.Base.COLOR.EMPTY);
		comboBox.SetDisplayOffset(false);
		SetControl(comboBox);
	}

	public CComboBoxMe GetComboBox() {
		if (GetControl() != null) {
			CControlMe comboBox = GetControl();
			return (CComboBoxMe) ((comboBox instanceof CComboBoxMe) ? comboBox : null);
		}
		return null;
	}

	public boolean GetBool() {
		CComboBoxMe comboBox = GetComboBox();
		if (comboBox != null) {
			return comboBox.GetSelectedIndex() > 0;
		}
		return false;
	}

	public double GetDouble() {
		CComboBoxMe comboBox = GetComboBox();
		if (comboBox != null) {
			return comboBox.GetSelectedIndex();
		}
		return 0.0D;
	}

	public float GetFloat() {
		CComboBoxMe comboBox = GetComboBox();
		if (comboBox != null) {
			return comboBox.GetSelectedIndex();
		}
		return 0.0F;
	}

	public int GetInt() {
		CComboBoxMe comboBox = GetComboBox();
		if (comboBox != null) {
			return comboBox.GetSelectedIndex();
		}
		return 0;
	}

	public long GetLong() {
		CComboBoxMe comboBox = GetComboBox();
		if (comboBox != null) {
			return comboBox.GetSelectedIndex();
		}
		return 0L;
	}

	public String GetString() {
		CComboBoxMe comboBox = GetComboBox();
		if (comboBox != null) {
			return comboBox.GetSelectedValue();
		}
		return "";
	}

	public void SetBool(boolean value) {
		CComboBoxMe comboBox = GetComboBox();
		if (comboBox != null) {
			comboBox.SetSelectedIndex(value ? 1 : 0);
		}
	}

	public void SetDouble(double value) {
		CComboBoxMe comboBox = GetComboBox();
		if (comboBox != null) {
			comboBox.SetSelectedIndex((int) value);
		}
	}

	public void SetFloat(float value) {
		CComboBoxMe comboBox = GetComboBox();
		if (comboBox != null) {
			comboBox.SetSelectedIndex((int) value);
		}
	}

	public void SetInt(int value) {
		CComboBoxMe comboBox = GetComboBox();
		if (comboBox != null) {
			comboBox.SetSelectedIndex(value);
		}
	}

	public void SetLong(long value) {
		CComboBoxMe comboBox = GetComboBox();
		if (comboBox != null) {
			comboBox.SetSelectedIndex((int) value);
		}
	}

	public void SetString(String value) {
		CComboBoxMe comboBox = GetComboBox();
		if (comboBox != null) {
			comboBox.SetSelectedValue(value);
		}
	}
}
