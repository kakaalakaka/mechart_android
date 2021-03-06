package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotLrBand extends CPlotBaseMe {
	public PlotLrBand() {
		SetPlotType("LRBAND");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		POINT mp = GetMouseOverPoint();
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		float[] param = GetLRParams(this.m_marks);
		if (param != null) {
			float a = param[0];
			float b = param[1];
			double leftValue = a + b;
			double rightValue = (eIndex - bIndex + 1) * a + b;
			float y1 = PY(leftValue);
			float y2 = PY(rightValue);
			if (SelectPoint(mp, x1, y1)) {
				action = ActionType.AT1;
				return action;
			}
			if (SelectPoint(mp, x2, y2)) {
				action = ActionType.AT2;
				return action;
			}
			com.melib.Chart.CChartMe chart = GetChart();
			int mouseIndex = chart.GetMouseOverIndex();
			if ((mouseIndex >= bIndex) && (mouseIndex <= eIndex)) {
				double yValue = a * (mouseIndex - bIndex + 1) + b;
				float y = PY(yValue);
				float x = PX(mouseIndex);
				if ((mp.x >= x - 5.0F) && (mp.x <= x + 5.0F) && (mp.y >= y - 5.0F) && (mp.y <= y + 5.0F)) {
					action = ActionType.MOVE;
					return action;
				}
				double[] parallel = GetLRBandRange(this.m_marks, param);
				yValue = a * (mouseIndex - bIndex + 1) + b + parallel[0];
				y = PY(yValue);
				x = PX(mouseIndex);
				if ((mp.x >= x - 5.0F) && (mp.x <= x + 5.0F) && (mp.y >= y - 5.0F) && (mp.y <= y + 5.0F)) {
					action = ActionType.MOVE;
					return action;
				}
				yValue = a * (mouseIndex - bIndex + 1) + b - parallel[1];
				y = PY(yValue);
				x = PX(mouseIndex);
				if ((mp.x >= x - 5.0F) && (mp.x <= x + 5.0F) && (mp.y >= y - 5.0F) && (mp.y <= y + 5.0F)) {
					action = ActionType.MOVE;
					return action;
				}
			}
		}
		return action;
	}

	public boolean OnCreate(POINT mp) {
		return Create2CandlePoints(mp);
	}

	public void OnMoveStart() {
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
		com.melib.Chart.CChartMe chart = GetChart();
		int mouseIndex = chart.GetMouseOverIndex();
		if (mouseIndex < 0) {
			mouseIndex = 0;
		}
		if (mouseIndex > chart.GetLastVisibleIndex()) {
			mouseIndex = chart.GetLastVisibleIndex();
		}
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		switch (this.m_action) {
		case MOVE:
			Move(mp);
			break;
		case AT1:
			if (mouseIndex < eIndex) {
				Resize(0);
			}
			break;
		case AT2:
			if (mouseIndex > bIndex) {
				Resize(1);
			}
			break;
		}

	}

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		float[] param = GetLRParams(pList);
		if (param != null) {
			int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
			int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
			float x1 = PX(bIndex);
			float x2 = PX(eIndex);
			float a = param[0];
			float b = param[1];
			double leftValue = a + b;
			double rightValue = (eIndex - bIndex + 1) * a + b;
			float y1 = PY(leftValue);
			float y2 = PY(rightValue);
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2);
			double[] parallel = GetLRBandRange(pList, param);
			double leftTop = leftValue + parallel[0];
			double rightTop = rightValue + parallel[0];
			double leftBottom = leftValue - parallel[1];
			double rightBottom = rightValue - parallel[1];
			float leftTopY = PY(leftTop);
			float rightTopY = PY(rightTop);
			float leftBottomY = PY(leftBottom);
			float rightBottomY = PY(rightBottom);
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, leftTopY, x2, rightTopY);
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, leftBottomY, x2, rightBottomY);
			if (IsSelected()) {
				DrawSelect(paint, lineColor, x1, y1);
				DrawSelect(paint, lineColor, x2, y2);
			}
		}
	}
}
