package com.melib.Chart.Plot;

import java.util.HashMap;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotGA extends PlotTriangle {
	public PlotGA() {
		SetPlotType("GA");
	}

	public ActionType GetAction() {
		ActionType action = super.GetAction();
		if (action != ActionType.NO) {
			return action;
		}

		if (HLinesSelect(GetGoldenRatioAimParams(this.m_marks), 6)) {
			action = ActionType.MOVE;
		}
		return action;
	}

	private float[] GetGoldenRatioAimParams(HashMap<Integer, CPlotMarkMe> pList) {
		double baseValue = ((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue();
		if (((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue() >= ((CPlotMarkMe) pList.get(Integer.valueOf(2)))
				.GetValue()) {
			return GoldenRatioParams(baseValue, baseValue + ((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue()
					- ((CPlotMarkMe) pList.get(Integer.valueOf(2))).GetValue());
		}

		return GoldenRatioParams(baseValue + ((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue()
				- ((CPlotMarkMe) pList.get(Integer.valueOf(2))).GetValue(), baseValue);
	}

	public void OnMoveStart() {
		this.m_action = GetAction();
		this.m_startMarks.clear();
		this.m_startPoint = GetMouseOverPoint();
		this.m_startMarks.put(Integer.valueOf(0), this.m_marks.get(Integer.valueOf(0)));
		this.m_startMarks.put(Integer.valueOf(1), this.m_marks.get(Integer.valueOf(1)));
		this.m_startMarks.put(Integer.valueOf(2), this.m_marks.get(Integer.valueOf(2)));
	}

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		float y1 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue());
		float y3 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(2))).GetValue());
		int aIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		int cIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(2))).GetKey());
		float x1 = PX(aIndex);
		float x2 = PX(bIndex);
		float x3 = PX(cIndex);
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2);
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x2, y2, x3, y3);
		float[] lineParam = GetGoldenRatioAimParams(pList);
		String[] str = { "0.00%", "23.60%", "38.20%", "50.00%", "61.80%", "100.00%" };
		for (int i = 0; i < lineParam.length; i++) {
			com.melib.Base.SIZE sizeF = TextSize(paint, str[i], this.m_font);
			float yP = lineParam[i];
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, 0.0F, yP, GetWorkingAreaWidth(), yP);
			DrawText(paint, str[i], lineColor, this.m_font, GetWorkingAreaWidth() - sizeF.cx, yP - sizeF.cy);
		}
		if ((IsSelected()) || ((x1 == x2) && (x2 == x3) && (y1 == y2) && (y2 == y3))) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
			DrawSelect(paint, lineColor, x3, y3);
		}
	}
}
