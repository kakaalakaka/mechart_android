package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Chart.CChartMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotPeriodic extends CPlotBaseMe {
	public PlotPeriodic() {
		SetPlotType("PERIODIC");
	}

	private int m_period = 5;

	private int m_beginPeriod = 1;

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		com.melib.Base.POINT mp = GetMouseOverPoint();
		double[] param = GetPLParams(this.m_marks);
		float y = GetWorkingAreaHeight() / 2;
		for (int i = 0; i < param.length; i++) {
			int rI = (int) param[i];
			float x1 = PX(rI);
			if (SelectPoint(mp, x1, y)) {
				action = ActionType.AT1;
				this.m_marks.put(Integer.valueOf(0), new CPlotMarkMe(0, this.m_dataSource.GetXValue(rI), 0.0D));
				this.m_beginPeriod = this.m_period;
				return action;
			}
			if ((mp.x >= x1 - this.m_lineWidth * 2.5F) && (mp.x <= x1 + this.m_lineWidth * 2.5F)) {
				action = ActionType.MOVE;
				return action;
			}
		}
		return action;
	}

	private double[] GetPLParams(HashMap<Integer, CPlotMarkMe> pList) {
		if (pList.isEmpty()) {
			return null;
		}
		double fValue = ((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue();
		int aIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		java.util.ArrayList<Double> fValueList = new java.util.ArrayList();
		CChartMe chart = GetChart();
		for (int i = chart.GetFirstVisibleIndex(); i < chart.GetLastVisibleIndex(); i++) {
			if (Math.abs(i - aIndex) % this.m_period == 0) {
				fValueList.add(Double.valueOf(i));
			}
		}
		int fValueListSize = fValueList.size();
		double[] fValueArray = new double[fValueListSize];
		for (int i = 0; i < fValueListSize; i++) {
			fValueArray[i] = ((Double) fValueList.get(i)).doubleValue();
		}
		return fValueArray;
	}

	public boolean OnCreate(com.melib.Base.POINT mp) {
		int rIndex = this.m_dataSource.GetRowsCount();
		if (rIndex > 0) {
			int currentIndex = GetIndex(mp);
			double y = GetNumberValue(mp);
			double date = this.m_dataSource.GetXValue(currentIndex);
			this.m_marks.clear();
			this.m_marks.put(Integer.valueOf(0), new CPlotMarkMe(0, date, y));
			CChartMe chart = GetChart();
			this.m_period = (chart.GetMaxVisibleRecord() / 10);
			if (this.m_period < 1) {
				this.m_period = 1;
			}
			return true;
		}
		return false;
	}

	public void OnMoveStart() {
		this.m_action = GetAction();
		this.m_startMarks.clear();
		this.m_startPoint = GetMouseOverPoint();
		if (this.m_action != ActionType.NO) {
			this.m_startMarks.put(Integer.valueOf(0), this.m_marks.get(Integer.valueOf(0)));
		}
	}

	public void OnMoving() {
		com.melib.Base.POINT mp = GetMovingPoint();
		CChartMe chart = GetChart();
		int mouseIndex = chart.GetMouseOverIndex();
		if (mouseIndex < 0) {
			mouseIndex = 0;
		}
		if (mouseIndex > chart.GetLastVisibleIndex()) {
			mouseIndex = chart.GetLastVisibleIndex();
		}
		int bI = GetIndex(this.m_startPoint);
		int eI = GetIndex(mp);
		switch (this.m_action) {
		case MOVE:
			Move(mp);
			break;
		case AT1:
			this.m_period = (this.m_beginPeriod + (eI - bI));
			if (this.m_period < 1) {
				this.m_period = 1;
			}
			break;
		}

	}

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		double[] param = GetPLParams(pList);
		for (int i = 0; i < param.length; i++) {
			int rI = (int) param[i];
			float x1 = PX(rI);
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, 0.0F, x1, GetWorkingAreaHeight());
			if (IsSelected()) {
				DrawSelect(paint, lineColor, x1, GetWorkingAreaHeight() / 2);
			}
		}
	}
}
