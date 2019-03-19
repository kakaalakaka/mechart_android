package com.melib.Grid;

import com.melib.Base.CControlMe;
import com.melib.TextBox.CSpinMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridSpinCellMe extends CGridControlCellMe {
	public CGridSpinCellMe() {
		CSpinMe spin = new CSpinMe();
		spin.SetBorderColor(com.melib.Base.COLOR.EMPTY);
		spin.SetDisplayOffset(false);
		SetControl(spin);
	}

	public final CSpinMe GetSpin() {
		if (GetControl() != null) {
			CControlMe spin = GetControl();
			return (CSpinMe) ((spin instanceof CSpinMe) ? spin : null);
		}

		return null;
	}

	public boolean GetBool() {
		CSpinMe spin = GetSpin();
		if (spin != null) {
			return spin.GetValue() > 0.0D;
		}

		return false;
	}

	public double GetDouble() {
		CSpinMe spin = GetSpin();
		if (spin != null) {
			return spin.GetValue();
		}

		return 0.0D;
	}

	public float GetFloat() {
		CSpinMe spin = GetSpin();
		if (spin != null) {
			return (float) spin.GetValue();
		}

		return 0.0F;
	}

	public int GetInt() {
		CSpinMe spin = GetSpin();
		if (spin != null) {
			return (int) spin.GetValue();
		}

		return 0;
	}

	public long GetLong() {
		CSpinMe spin = GetSpin();
		if (spin != null) {
			return (long) spin.GetValue();
		}

		return 0L;
	}

	public void SetBool(boolean value) {
		CSpinMe spin = GetSpin();
		if (spin != null) {
			spin.SetValue(value ? 1.0D : 0.0D);
		}
	}

	public void SetDouble(double value) {
		CSpinMe spin = GetSpin();
		if (spin != null) {
			spin.SetValue(value);
		}
	}

	public void SetFloat(float value) {
		CSpinMe spin = GetSpin();
		if (spin != null) {
			spin.SetValue(value);
		}
	}

	public void SetInt(int value) {
		CSpinMe spin = GetSpin();
		if (spin != null) {
			spin.SetValue(value);
		}
	}

	public void SetLong(long value) {
		CSpinMe spin = GetSpin();
		if (spin != null) {
			spin.SetValue(value);
		}
	}
}
