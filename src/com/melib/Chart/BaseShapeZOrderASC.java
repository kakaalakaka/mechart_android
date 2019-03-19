package com.melib.Chart;

import java.util.Comparator;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class BaseShapeZOrderASC implements Comparator<CBaseShapeMe> {
	public int compare(CBaseShapeMe x, CBaseShapeMe y) {
		return new Integer(x.GetZOrder()).compareTo(Integer.valueOf(y.GetZOrder()));
	}
}
