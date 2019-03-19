package com.melib.Chart;

import java.util.Comparator;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotZOrderCompareASC implements Comparator<CPlotMe> {
	public int compare(CPlotMe x, CPlotMe y) {
		return new Integer(x.GetZOrder()).compareTo(Integer.valueOf(y.GetZOrder()));
	}
}
