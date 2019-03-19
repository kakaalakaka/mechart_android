package com.melib.Chart;

import java.util.Comparator;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class BaseShapeZOrderDESC implements Comparator<CBaseShapeMe> {
	public int compare(CBaseShapeMe x, CBaseShapeMe y) {
		return new Integer(y.GetZOrder()).compareTo(Integer.valueOf(x.GetZOrder()));
	}
}
