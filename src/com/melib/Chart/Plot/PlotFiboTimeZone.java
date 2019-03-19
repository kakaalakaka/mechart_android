package com.melib.Chart.Plot;

import java.util.ArrayList;
import java.util.HashMap;
import com.melib.Chart.CChartMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotFiboTimeZone extends CPlotBaseMe {
	public PlotFiboTimeZone() {
		SetPlotType("FIBOTIMEZONE");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		com.melib.Base.POINT mp = GetMouseOverPoint();
		int[] param = GetFibonacciTimeZonesParam(this.m_marks);
		for (int i = 0; i < param.length; i++) {
			int rI = param[i];
			CChartMe chart = GetChart();
			if ((rI >= chart.GetFirstVisibleIndex()) && (rI <= chart.GetLastVisibleIndex())) {
				float x1 = PX(rI);
				if ((mp.x >= x1 - this.m_lineWidth * 2.5F) && (mp.x <= x1 + this.m_lineWidth * 2.5F)) {
					action = ActionType.MOVE;
					return action;
				}
			}
		}
		return action;
	}

	private int[] GetFibonacciTimeZonesParam(HashMap<Integer, CPlotMarkMe> pList) {
		if (pList.isEmpty()) {
			return null;
		}
		double fValue = ((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue();
		int aIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int pos = 1;
		int fibonacciValue = 1;
		ArrayList<Integer> fValueList = new ArrayList();
		fValueList.add(Integer.valueOf(aIndex));
		CChartMe chart = GetChart();
		while (aIndex + fibonacciValue <= chart.GetLastVisibleIndex()) {
			fibonacciValue = com.melib.Base.CMathLibMe.M112(pos);
			fValueList.add(Integer.valueOf(aIndex + fibonacciValue));
			pos++;
		}
		int fValueListSize = fValueList.size();
		int[] fValueArray = new int[fValueListSize];
		for (int i = 0; i < fValueListSize; i++) {
			fValueArray[i] = ((Integer) fValueList.get(i)).intValue();
		}
		return fValueArray;
	}

	public boolean OnCreate(com.melib.Base.POINT mp) {
		return CreatePoint(mp);
	}

	public void OnMoveStart() {
		this.m_action = GetAction();
		this.m_startMarks.clear();
		this.m_startPoint = GetMouseOverPoint();
		if (this.m_action != ActionType.NO) {
			this.m_startMarks.put(Integer.valueOf(0), this.m_marks.get(Integer.valueOf(0)));
		}
	}

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		int[] param = GetFibonacciTimeZonesParam(pList);
		CChartMe chart = GetChart();
		for (int i = 0; i < param.length; i++) {
			int rI = param[i];
			if ((rI >= chart.GetFirstVisibleIndex()) && (rI <= chart.GetLastVisibleIndex())) {
				float x1 = PX(rI);
				DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, 0.0F, x1, GetWorkingAreaHeight());
				if ((i == 0) && (IsSelected())) {
					DrawSelect(paint, lineColor, x1, GetWorkingAreaHeight() / 2);
				}
				DrawText(paint, new Integer(rI).toString(), lineColor, this.m_font, x1, 0.0F);
			}
		}
	}
}
