package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotPercent extends CPlotBaseMe {
	public PlotPercent() {
		SetPlotType("PERCENT");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		POINT mp = GetMouseOverPoint();
		float y1 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		float x1 = PX(bIndex);
		if (this.m_moveTimes == 1) {
			action = ActionType.AT1;
			return action;
		}
		if ((mp.x >= x1 - this.m_lineWidth * 2.5F) && (mp.x <= x1 + this.m_lineWidth * 2.5F)) {
			if ((mp.y >= y1 - this.m_lineWidth * 2.5F) && (mp.y <= y1 + this.m_lineWidth * 2.5F)) {
				action = ActionType.AT1;
				return action;
			}
			if ((mp.y >= y2 - this.m_lineWidth * 2.5F) && (mp.y <= y2 + this.m_lineWidth * 2.5F)) {
				action = ActionType.AT2;
				return action;
			}
		}
		if (HLinesSelect(GetPercentParams(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue(),
				((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue()), 5)) {
			action = ActionType.MOVE;
		}
		return action;
	}

	protected float[] GetPercentParams(double value1, double value2) {
		float y1 = PY(value1);
		float y2 = PY(value2);
		float y0 = 0.0F;
		float y25 = 0.0F;
		float y50 = 0.0F;
		float y75 = 0.0F;
		float y100 = 0.0F;
		y0 = y1;
		y25 = y1 <= y2 ? y1 + (y2 - y1) / 4.0F : y2 + (y1 - y2) * 3.0F / 4.0F;
		y50 = y1 <= y2 ? y1 + (y2 - y1) / 2.0F : y2 + (y1 - y2) / 2.0F;
		y75 = y1 <= y2 ? y1 + (y2 - y1) * 3.0F / 4.0F : y2 + (y1 - y2) / 4.0F;
		y100 = y2;
		return new float[] { y0, y25, y50, y75, y100 };
	}

	public boolean OnCreate(POINT mp) {
		return Create2PointsB(mp);
	}

	public void OnMoveStart() {
		this.m_moveTimes += 1;
		this.m_action = GetAction();
		this.m_startMarks.clear();
		this.m_startPoint = GetMouseOverPoint();
		if (this.m_action != ActionType.NO) {
			this.m_startMarks.put(Integer.valueOf(0), this.m_marks.get(Integer.valueOf(0)));
			this.m_startMarks.put(Integer.valueOf(1), this.m_marks.get(Integer.valueOf(1)));
		}
	}

	public void OnMoving() {
		POINT mp = GetMovingPoint();
		switch (this.m_action) {
		case AT1:
			Resize(0);
			break;
		case AT2:
			Resize(1);
			break;
		case MOVE:
			double subY = mp.y - this.m_startPoint.y;
			double maxValue = this.m_div.GetVScale(this.m_attachVScale).GetVisibleMax();
			double minValue = this.m_div.GetVScale(this.m_attachVScale).GetVisibleMin();
			double yAddValue = subY / GetWorkingAreaHeight() * (minValue - maxValue);
			this.m_marks.put(Integer.valueOf(0),
					new CPlotMarkMe(0, ((CPlotMarkMe) this.m_startMarks.get(Integer.valueOf(0))).GetKey(),
							((CPlotMarkMe) this.m_startMarks.get(Integer.valueOf(0))).GetValue() + yAddValue));
			this.m_marks.put(Integer.valueOf(1),
					new CPlotMarkMe(1, ((CPlotMarkMe) this.m_startMarks.get(Integer.valueOf(1))).GetKey(),
							((CPlotMarkMe) this.m_startMarks.get(Integer.valueOf(1))).GetValue() + yAddValue));
		}

	}

	public void OnPaintGhost(com.melib.Base.CPaintMe paint) {
		if (this.m_moveTimes > 1) {
			Paint(paint, this.m_startMarks, GetSelectedColor());
		}
	}

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		float y1 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue());
		float y2 = PY(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue());
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		float x1 = PX(bIndex);
		float[] lineParam = GetPercentParams(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue(),
				((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue());
		String[] str = { "0.00%", "25.00%", "50.00%", "75.00%", "100.00%" };
		for (int i = 0; i < lineParam.length; i++) {
			com.melib.Base.SIZE sizeF = TextSize(paint, str[i], this.m_font);
			float yP = lineParam[i];
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, 0.0F, yP, GetWorkingAreaWidth(), yP);
			DrawText(paint, str[i], lineColor, this.m_font, GetWorkingAreaWidth() - sizeF.cx, yP - sizeF.cy);
		}
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x1, y2);
		}
	}
}
