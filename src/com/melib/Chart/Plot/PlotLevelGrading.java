package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotLevelGrading extends CPlotBaseMe {
	public PlotLevelGrading() {
		SetPlotType("LEVELGRADING");
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
		if (HLinesSelect(LevelGradingParams(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetValue(),
				((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetValue()), 5)) {
			action = ActionType.MOVE;
		}
		return action;
	}

	private float[] LevelGradingParams(double value1, double value2) {
		float y1 = PY(value1);
		float y2 = PY(value2);
		float yA = 0.0F;
		float yB = 0.0F;
		float yC = 0.0F;
		float yD = 0.0F;
		float yE = 0.0F;
		yA = y1;
		yB = y2;
		yC = y1 <= y2 ? y2 + (y2 - y1) * 0.382F : y2 - (y1 - y2) * 0.382F;
		yD = y1 <= y2 ? y2 + (y2 - y1) * 0.618F : y2 - (y1 - y2) * 0.618F;
		yE = y1 <= y2 ? y2 + (y2 - y1) : y2 - (y1 - y2);
		return new float[] { yA, yB, yC, yD, yE };
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
		float[] lineParam = LevelGradingParams(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetValue(),
				((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetValue());
		String[] str = null;
		if (y1 >= y2) {
			str = new String[] { "-100%", "0.00%", "38.20%", "61.80%", "100%" };
		} else {
			str = new String[] { "100%", "0.00%", "-38.20%", "-61.80%", "-100%" };
		}
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
