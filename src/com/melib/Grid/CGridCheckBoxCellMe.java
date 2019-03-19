package com.melib.Grid;

import com.melib.Base.HorizontalAlignA;
import com.melib.Button.CCheckBoxMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridCheckBoxCellMe extends CGridControlCellMe {
	public CGridCheckBoxCellMe() {
		CCheckBoxMe checkBox = new CCheckBoxMe();
		checkBox.SetDisplayOffset(false);
		checkBox.SetButtonAlign(HorizontalAlignA.Center);
		SetControl(checkBox);
	}

	public CCheckBoxMe GetCheckBox() {
		if (GetControl() != null) {
			com.melib.Base.CControlMe control = GetControl();
			return (CCheckBoxMe) ((control instanceof CCheckBoxMe) ? control : null);
		}

		return null;
	}

	public boolean GetBool() {
		CCheckBoxMe checkBox = GetCheckBox();
		if (checkBox != null) {
			return checkBox.IsChecked();
		}

		return false;
	}

	public double GetDouble() {
		CCheckBoxMe checkBox = GetCheckBox();
		if (checkBox != null) {
			return checkBox.IsChecked() ? 1.0D : 0.0D;
		}

		return 0.0D;
	}

	public float GetFloat() {
		CCheckBoxMe checkBox = GetCheckBox();
		if (checkBox != null) {
			return checkBox.IsChecked() ? 1.0F : 0.0F;
		}

		return 0.0F;
	}

	public int GetInt() {
		CCheckBoxMe checkBox = GetCheckBox();
		if (checkBox != null) {
			return checkBox.IsChecked() ? 1 : 0;
		}

		return 0;
	}

	public long GetLong() {
		CCheckBoxMe checkBox = GetCheckBox();
		if (checkBox != null) {
			return checkBox.IsChecked() ? 1L : 0L;
		}

		return 0L;
	}

	public String GetString() {
		CCheckBoxMe checkBox = GetCheckBox();
		if (checkBox != null) {
			return checkBox.IsChecked() ? "true" : "false";
		}
		return "false";
	}

	public void SetBool(boolean value) {
		CCheckBoxMe checkBox = GetCheckBox();
		if (checkBox != null) {
			checkBox.SetChecked(value);
		}
	}

	public void SetDouble(double value) {
		CCheckBoxMe checkBox = GetCheckBox();
		if (checkBox != null) {
			checkBox.SetChecked(value > 0.0D);
		}
	}

	public void SetFloat(float value) {
		CCheckBoxMe checkBox = GetCheckBox();
		if (checkBox != null) {
			checkBox.SetChecked(value > 0.0F);
		}
	}

	public void SetInt(int value) {
		CCheckBoxMe checkBox = GetCheckBox();
		if (checkBox != null) {
			checkBox.SetChecked(value > 0);
		}
	}

	public void SetLong(long value) {
		CCheckBoxMe checkBox = GetCheckBox();
		if (checkBox != null) {
			checkBox.SetChecked(value > 0L);
		}
	}

	public void SetString(String value) {
		CCheckBoxMe checkBox = GetCheckBox();
		if (checkBox != null) {
			checkBox.SetChecked(value.equals("true"));
		}
	}
}
