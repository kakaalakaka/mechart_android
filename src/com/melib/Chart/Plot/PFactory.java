package com.melib.Chart.Plot;

import com.melib.Chart.CPlotMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PFactory {
	public static CPlotMe CreatePlot(String plotType) {
		CPlotMe iplot = null;
		if (plotType.equals("ANDREWSPITCHFORK")) {
			iplot = new P001();
		} else if (plotType.equals("ANGLELINE")) {
			iplot = new P002();
		} else if (plotType.equals("CIRCUMCIRCLE")) {
			iplot = new P003();
		} else if (plotType.equals("ARROWSEGMENT")) {
			iplot = new P004();
		} else if (plotType.equals("DOWNARROW")) {
			iplot = new P005();
		} else if (plotType.equals("DROPLINE")) {
			iplot = new P006();
		} else if (plotType.equals("ELLIPSE")) {
			iplot = new P009();
		} else if (plotType.equals("FIBOELLIPSE")) {
			iplot = new P010();
		} else if (plotType.equals("FIBOFANLINE")) {
			iplot = new P011();
		} else if (plotType.equals("FIBOTIMEZONE")) {
			iplot = new P012();
		} else if (plotType.equals("GANNBOX")) {
			iplot = new P014();
		} else if (plotType.equals("GANNLINE")) {
			iplot = new P016();
		} else if (plotType.equals("GOLDENRATIO")) {
			iplot = new P017();
		} else if (plotType.equals("HLINE")) {
			iplot = new P018();
		} else if (plotType.equals("LEVELGRADING")) {
			iplot = new P019();
		} else if (plotType.equals("LINE")) {
			iplot = new P020();
		} else if (plotType.equals("LRBAND")) {
			iplot = new P021();
		} else if (plotType.equals("LRCHANNEL")) {
			iplot = new P022();
		} else if (plotType.equals("LRLINE")) {
			iplot = new P023();
		} else if (plotType.equals("NULLPOINT")) {
			iplot = new P024();
		} else if (plotType.equals("PARALLEL")) {
			iplot = new P025();
		} else if (plotType.equals("PERCENT")) {
			iplot = new P026();
		} else if (plotType.equals("PERIODIC")) {
			iplot = new P027();
		} else if (plotType.equals("PRICE")) {
			iplot = new P028();
		} else if (plotType.equals("RANGERULER")) {
			iplot = new P029();
		} else if (plotType.equals("RASELINE")) {
			iplot = new P030();
		} else if (plotType.equals("RAY")) {
			iplot = new P031();
		} else if (plotType.equals("RECT")) {
			iplot = new P032();
		} else if (plotType.equals("SEGMENT")) {
			iplot = new P033();
		} else if (plotType.equals("SINE")) {
			iplot = new P034();
		} else if (plotType.equals("SPEEDRESIST")) {
			iplot = new P035();
		} else if (plotType.equals("SECHANNEL")) {
			iplot = new P036();
		} else if (plotType.equals("SYMMETRICLINE")) {
			iplot = new P037();
		} else if (plotType.equals("SYMMETRICTRIANGLE")) {
			iplot = new P038();
		} else if (plotType.equals("TIMERULER")) {
			iplot = new P041();
		} else if (plotType.equals("TRIANGLE")) {
			iplot = new P042();
		} else if (plotType.equals("UPARROW")) {
			iplot = new P043();
		} else if (plotType.equals("VLINE")) {
			iplot = new P044();
		} else if (plotType.equals("WAVERULER")) {
			iplot = new P045();
		} else if (plotType.equals("TIRONELEVELS")) {
			iplot = new P046();
		} else if (plotType.equals("RAFFCHANNEL")) {
			iplot = new P047();
		} else if (plotType.equals("QUADRANTLINES")) {
			iplot = new P048();
		} else if (plotType.equals("BOXLINE")) {
			iplot = new P049();
		} else if (plotType.equals("PARALLELOGRAM")) {
			iplot = new P050();
		} else if (plotType.equals("CIRCLE")) {
			iplot = new P051();
		} else if (plotType.equals("PRICECHANNEL")) {
			iplot = new P052();
		} else if (plotType.equals("GP")) {
			iplot = new P053();
		} else if (plotType.equals("GA")) {
			iplot = new P054();
		}
		return iplot;
	}
}
