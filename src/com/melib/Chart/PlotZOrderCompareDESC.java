package com.melib.Chart;

import java.util.Comparator;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotZOrderCompareDESC implements Comparator<CPlotMe> {
	public int compare(CPlotMe x, CPlotMe y) {
		return new Integer(y.GetZOrder()).compareTo(Integer.valueOf(x.GetZOrder()));
	}
}
