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
			iplot = new PlotAndrewsPitchFork();
		} else if (plotType.equals("ANGLELINE")) {
			iplot = new PlotAngleLine();
		} else if (plotType.equals("CIRCUMCIRCLE")) {
			iplot = new PlotCircumCircle();
		} else if (plotType.equals("ARROWSEGMENT")) {
			iplot = new PlotArrowSegment();
		} else if (plotType.equals("DOWNARROW")) {
			iplot = new PlotDownArrow();
		} else if (plotType.equals("DROPLINE")) {
			iplot = new PlotDropLine();
		} else if (plotType.equals("ELLIPSE")) {
			iplot = new PlotEllipse();
		} else if (plotType.equals("FIBOELLIPSE")) {
			iplot = new PlotFiboEllipse();
		} else if (plotType.equals("FIBOFANLINE")) {
			iplot = new PlotFibofanLine();
		} else if (plotType.equals("FIBOTIMEZONE")) {
			iplot = new PlotFiboTimeZone();
		} else if (plotType.equals("GANNBOX")) {
			iplot = new PlotGannbox();
		} else if (plotType.equals("GANNLINE")) {
			iplot = new PlotGannLine();
		} else if (plotType.equals("GOLDENRATIO")) {
			iplot = new PlotGoldenRatio();
		} else if (plotType.equals("HLINE")) {
			iplot = new PlotHline();
		} else if (plotType.equals("LEVELGRADING")) {
			iplot = new PlotLevelGrading();
		} else if (plotType.equals("LINE")) {
			iplot = new PlotLine();
		} else if (plotType.equals("LRBAND")) {
			iplot = new PlotLrBand();
		} else if (plotType.equals("LRCHANNEL")) {
			iplot = new PlotLrChannel();
		} else if (plotType.equals("LRLINE")) {
			iplot = new PlotLrLine();
		} else if (plotType.equals("NULLPOINT")) {
			iplot = new PlotNullPoint();
		} else if (plotType.equals("PARALLEL")) {
			iplot = new PlotParallel();
		} else if (plotType.equals("PERCENT")) {
			iplot = new PlotPercent();
		} else if (plotType.equals("PERIODIC")) {
			iplot = new PlotPeriodic();
		} else if (plotType.equals("PRICE")) {
			iplot = new PlotPrice();
		} else if (plotType.equals("RANGERULER")) {
			iplot = new PlotRangeruler();
		} else if (plotType.equals("RASELINE")) {
			iplot = new PlotRaseline();
		} else if (plotType.equals("RAY")) {
			iplot = new PlotRay();
		} else if (plotType.equals("RECT")) {
			iplot = new PlotRect();
		} else if (plotType.equals("SEGMENT")) {
			iplot = new PlotSegement();
		} else if (plotType.equals("SINE")) {
			iplot = new PlotSine();
		} else if (plotType.equals("SPEEDRESIST")) {
			iplot = new PlotSpeedresist();
		} else if (plotType.equals("SECHANNEL")) {
			iplot = new PlotSechannel();
		} else if (plotType.equals("SYMMETRICLINE")) {
			iplot = new PlotSymmetricLine();
		} else if (plotType.equals("SYMMETRICTRIANGLE")) {
			iplot = new PlotSymmetricTriangle();
		} else if (plotType.equals("TIMERULER")) {
			iplot = new PlotTimeRuler();
		} else if (plotType.equals("TRIANGLE")) {
			iplot = new PlotTriangle();
		} else if (plotType.equals("UPARROW")) {
			iplot = new PlotUpArrow();
		} else if (plotType.equals("VLINE")) {
			iplot = new PlotVLine();
		} else if (plotType.equals("WAVERULER")) {
			iplot = new PlotWavRuler();
		} else if (plotType.equals("TIRONELEVELS")) {
			iplot = new PlotTironelevels();
		} else if (plotType.equals("RAFFCHANNEL")) {
			iplot = new PlotRaffchannel();
		} else if (plotType.equals("QUADRANTLINES")) {
			iplot = new PlotQuadrantLines();
		} else if (plotType.equals("BOXLINE")) {
			iplot = new PlotBoxLine();
		} else if (plotType.equals("PARALLELOGRAM")) {
			iplot = new PlotParallelogram();
		} else if (plotType.equals("CIRCLE")) {
			iplot = new PlotCircle();
		} else if (plotType.equals("PRICECHANNEL")) {
			iplot = new PlotPriceChannel();
		} else if (plotType.equals("GP")) {
			iplot = new PlotGP();
		} else if (plotType.equals("GA")) {
			iplot = new PlotGA();
		}
		return iplot;
	}
}
