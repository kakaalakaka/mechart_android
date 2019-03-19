package com.melib.Indicator;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class LPDATA {
	public double lastvalue;

	public double first_value;

	public int mode;

	public double sum;

	public LPDATA Clone() {
		LPDATA varCopy = new LPDATA();

		varCopy.lastvalue = this.lastvalue;
		varCopy.first_value = this.first_value;
		varCopy.mode = this.mode;
		varCopy.sum = this.sum;

		return varCopy;
	}
}
