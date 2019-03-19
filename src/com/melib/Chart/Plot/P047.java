package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class P047 extends CPlotBaseMe {
	public P047() {
		SetPlotType("RAFFCHANNEL");
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
			if ((mouseIndex >= chart.GetFirstVisibleIndex()) && (mouseIndex <= chart.GetLastVisibleIndex())) {
				double yValue = a * (mouseIndex - bIndex + 1) + b;
				float y = PY(yValue);
				float x = PX(mouseIndex);
				if ((mp.x >= x - 5.0F) && (mp.x <= x + 5.0F) && (mp.y >= y - 5.0F) && (mp.y <= y + 5.0F)) {
					action = ActionType.MOVE;
					return action;
				}
				double parallel = GetRRCRange(this.m_marks, param);
				yValue = a * (mouseIndex - bIndex + 1) + b + parallel;
				y = PY(yValue);
				x = PX(mouseIndex);
				if ((mp.x >= x - 5.0F) && (mp.x <= x + 5.0F) && (mp.y >= y - 5.0F) && (mp.y <= y + 5.0F)) {
					action = ActionType.MOVE;
					return action;
				}
				yValue = a * (mouseIndex - bIndex + 1) + b - parallel;
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

	private double GetRRCRange(HashMap<Integer, CPlotMarkMe> pList, float[] param) {
		if ((param == null) || (this.m_sourceFields == null) || (this.m_sourceFields.isEmpty())
				|| (!this.m_sourceFields.containsKey("HIGH")) || (!this.m_sourceFields.containsKey("LOW"))) {
			return 0.0D;
		}
		float a = param[0];
		float b = param[1];
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		double upSubValue = 0.0D;
		double downSubValue = 0.0D;
		int pos = 0;
		for (int i = bIndex; i <= eIndex; i++) {
			double high = this.m_dataSource.Get2(i, ((Integer) this.m_sourceFields.get("HIGH")).intValue());
			double low = this.m_dataSource.Get2(i, ((Integer) this.m_sourceFields.get("LOW")).intValue());
			if ((!Double.isNaN(high)) && (!Double.isNaN(low))) {
				double midValue = (i - bIndex + 1) * a + b;
				if (pos == 0) {
					upSubValue = high - midValue;
					downSubValue = midValue - low;
				} else {
					if (high - midValue > upSubValue) {
						upSubValue = high - midValue;
					}
					if (midValue - low > downSubValue) {
						downSubValue = midValue - low;
					}
				}
				pos++;
			}
		}
		if (upSubValue >= downSubValue) {
			return upSubValue;
		}

		return downSubValue;
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
			float a = param[0];
			float b = param[1];
			int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
			int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
			float x1 = PX(bIndex);
			float x2 = PX(eIndex);
			double leftValue = a + b;
			double rightValue = (eIndex - bIndex + 1) * a + b;
			float y1 = PY(leftValue);
			float y2 = PY(rightValue);
			float[] param2 = GetLineParams(
					new CPlotMarkMe(0, ((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey(), leftValue),
					new CPlotMarkMe(1, ((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey(), rightValue));
			if (param2 != null) {
				a = param2[0];
				b = param2[1];
				float leftX = 0.0F;
				float leftY = leftX * a + b;
				float rightX = GetWorkingAreaWidth();
				float rightY = rightX * a + b;
				DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, leftX, leftY, rightX, rightY);
				double parallel = GetRRCRange(pList, param);
				double leftTop = leftValue + parallel;
				double rightTop = rightValue + parallel;
				param2 = GetLineParams(new CPlotMarkMe(0, ((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey(), leftTop),
						new CPlotMarkMe(1, ((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey(), rightTop));
				if (param2 != null) {
					a = param2[0];
					b = param2[1];
					leftX = 0.0F;
					leftY = leftX * a + b;
					rightX = GetWorkingAreaWidth();
					rightY = rightX * a + b;
					DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, leftX, leftY, rightX, rightY);
				}
				double leftBottom = leftValue - parallel;
				double rightBottom = rightValue - parallel;
				param2 = GetLineParams(new CPlotMarkMe(0, ((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey(), leftBottom),
						new CPlotMarkMe(1, ((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey(), rightBottom));
				if (param2 != null) {
					a = param2[0];
					b = param2[1];
					leftX = 0.0F;
					leftY = leftX * a + b;
					rightX = GetWorkingAreaWidth();
					rightY = rightX * a + b;
					DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, leftX, leftY, rightX, rightY);
				}
			}
			if (IsSelected()) {
				DrawSelect(paint, lineColor, x1, y1);
				DrawSelect(paint, lineColor, x2, y2);
			}
		}
	}
}
