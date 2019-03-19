package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Chart.CTableMe;
import com.melib.Chart.CChartMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotSechannel extends CPlotBaseMe {
	public PlotSechannel() {
		SetPlotType("SECHANNEL");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		com.melib.Base.POINT mp = GetMouseOverPoint();
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
			CChartMe chart = GetChart();
			int mouseIndex = chart.GetMouseOverIndex();
			if ((mouseIndex >= bIndex) && (mouseIndex <= chart.GetLastVisibleIndex())) {
				double yValue = a * (mouseIndex - bIndex + 1) + b;
				float y = PY(yValue);
				float x = PX(mouseIndex);
				if (SelectPoint(mp, x, y)) {
					action = ActionType.MOVE;
					return action;
				}
				double sd = GetSEChannelSD(this.m_marks);
				yValue = a * (mouseIndex - bIndex + 1) + b + sd;
				y = PY(yValue);
				x = PX(mouseIndex);
				if (SelectPoint(mp, x, y)) {
					action = ActionType.MOVE;
					return action;
				}
				yValue = a * (mouseIndex - bIndex + 1) + b - sd;
				y = PY(yValue);
				x = PX(mouseIndex);
				if (SelectPoint(mp, x, y)) {
					action = ActionType.MOVE;
					return action;
				}
			}
		}
		return action;
	}

	private double GetSEChannelSD(HashMap<Integer, CPlotMarkMe> pList) {
		if ((this.m_sourceFields != null) && (this.m_sourceFields.containsKey("CLOSE"))) {
			int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
			int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
			int len = eIndex - bIndex + 1;
			if (len > 0) {
				double[] ary = new double[len];
				for (int i = 0; i < len; i++) {
					double close = this.m_dataSource.Get2(i + bIndex,
							((Integer) this.m_sourceFields.get("CLOSE")).intValue());
					if (!Double.isNaN(close)) {
						ary[i] = close;
					}
				}
				double avg = com.melib.Base.CMathLibMe.M010(ary, len);
				double sd = com.melib.Base.CMathLibMe.M007(ary, len, avg, 2.0D);
				return sd;
			}
		}
		return 0.0D;
	}

	public boolean OnCreate(com.melib.Base.POINT mp) {
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
		com.melib.Base.POINT mp = GetMovingPoint();
		CChartMe chart = GetChart();
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
		CChartMe chart = GetChart();
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(chart.GetX(bIndex));
		float x2 = PX(chart.GetX(eIndex));
		float[] param = GetLRParams(pList);
		if (param != null) {
			float a = param[0];
			float b = param[1];
			double leftValue = a + b;
			double rightValue = (eIndex - bIndex + 1) * a + b;
			float y1 = PY(leftValue);
			float y2 = PY(rightValue);
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2);
			double sd = GetSEChannelSD(pList);
			double leftTop = leftValue + sd;
			double rightTop = rightValue + sd;
			double leftBottom = leftValue - sd;
			double rightBottom = rightValue - sd;
			float leftTopY = PY(leftTop);
			float rightTopY = PY(rightTop);
			float leftBottomY = PY(leftBottom);
			float rightBottomY = PY(rightBottom);
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, leftTopY, x2, rightTopY);
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, leftBottomY, x2, rightBottomY);
			rightValue = (chart.GetLastVisibleIndex() + 1 - bIndex) * a + b;
			float x3 = (float) ((chart.GetLastVisibleIndex() - chart.GetFirstVisibleIndex()) * chart.GetHScalePixel()
					+ chart.GetHScalePixel() / 2.0D);
			double dashTop = rightValue + sd;
			double dashBottom = rightValue - sd;
			float mValueY = PY(rightValue);
			float dashTopY = PY(dashTop);
			float dashBottomY = PY(dashBottom);
			DrawLine(paint, lineColor, this.m_lineWidth, 1, x2, rightTopY, x3, dashTopY);
			DrawLine(paint, lineColor, this.m_lineWidth, 1, x2, rightBottomY, x3, dashBottomY);
			DrawLine(paint, lineColor, this.m_lineWidth, 1, x2, y2, x3, mValueY);
			if (IsSelected()) {
				DrawSelect(paint, lineColor, x1, y1);
				DrawSelect(paint, lineColor, x2, y2);
			}
		}
	}
}
