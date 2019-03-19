package com.melib.Grid;

import com.melib.Base.CControlMe;
import com.melib.Button.CRadioButtonMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridRadioButtonCellMe extends CGridControlCellMe {
	public CGridRadioButtonCellMe() {
		CRadioButtonMe radioButton = new CRadioButtonMe();
		radioButton.SetBorderColor(com.melib.Base.COLOR.EMPTY);
		radioButton.SetDisplayOffset(false);
		SetControl(radioButton);
	}

	public CRadioButtonMe GetRadioButton() {
		if (GetControl() != null) {
			CControlMe radioButton = GetControl();
			return (CRadioButtonMe) ((radioButton instanceof CRadioButtonMe) ? radioButton : null);
		}

		return null;
	}

	public boolean GetBool() {
		CRadioButtonMe radioButton = GetRadioButton();
		if (radioButton != null) {
			return radioButton.IsChecked();
		}

		return false;
	}

	public double GetDouble() {
		CRadioButtonMe radioButton = GetRadioButton();
		if (radioButton != null) {
			return radioButton.IsChecked() ? 1.0D : 0.0D;
		}

		return 0.0D;
	}

	public float GetFloat() {
		CRadioButtonMe radioButton = GetRadioButton();
		if (radioButton != null) {
			return radioButton.IsChecked() ? 1.0F : 0.0F;
		}

		return 0.0F;
	}

	public int GetInt() {
		CRadioButtonMe radioButton = GetRadioButton();
		if (radioButton != null) {
			return radioButton.IsChecked() ? 1 : 0;
		}

		return 0;
	}

	public long GetLong() {
		CRadioButtonMe radioButton = GetRadioButton();
		if (radioButton != null) {
			return radioButton.IsChecked() ? 1L : 0L;
		}

		return 0L;
	}

	public String GetString() {
		CRadioButtonMe radioButton = GetRadioButton();
		if (radioButton != null) {
			return radioButton.IsChecked() ? "true" : "false";
		}

		return "false";
	}

	public void SetBool(boolean value) {
		CRadioButtonMe radioButton = GetRadioButton();
		if (radioButton != null) {
			radioButton.SetChecked(value);
		}
	}

	public void SetDouble(double value) {
		CRadioButtonMe radioButton = GetRadioButton();
		if (radioButton != null) {
			radioButton.SetChecked(value > 0.0D);
		}
	}

	public void SetFloat(float value) {
		CRadioButtonMe radioButton = GetRadioButton();
		if (radioButton != null) {
			radioButton.SetChecked(value > 0.0F);
		}
	}

	public void SetInt(int value) {
		CRadioButtonMe radioButton = GetRadioButton();
		if (radioButton != null) {
			radioButton.SetChecked(value > 0);
		}
	}

	public void SetLong(long value) {
		CRadioButtonMe radioButton = GetRadioButton();
		if (radioButton != null) {
			radioButton.SetChecked(value > 0L);
		}
	}

	public void SetString(String value) {
		CRadioButtonMe radioButton = GetRadioButton();
		if (radioButton != null) {
			radioButton.SetChecked(value.equals("true"));
		}
	}
}
