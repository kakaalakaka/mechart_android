package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotNullPoint extends CPlotBaseMe {
	public PlotNullPoint() {
		SetPlotType("NULLPOINT");
	}

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		com.melib.Base.POINT mp = GetMouseOverPoint();
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		double[] closeParam = GetNullPointParams(this.m_marks);
		double leftClose = closeParam[1];
		double rightClose = closeParam[0];
		float y1 = PY(leftClose);
		float y2 = PY(rightClose);
		float x1 = PX(bIndex >= eIndex ? bIndex : eIndex);
		float x2 = PX(bIndex >= eIndex ? eIndex : bIndex);
		float[] param = GetLineParams((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0)),
				(CPlotMarkMe) this.m_marks.get(Integer.valueOf(1)));
		if (param != null) {
			if ((mp.x >= x1 - this.m_lineWidth * 2.5F) && (mp.x <= x1 + this.m_lineWidth * 2.5F)) {
				action = ActionType.AT1;
				return action;
			}
			if ((mp.x >= x2 - this.m_lineWidth * 2.5F) && (mp.x <= x2 + this.m_lineWidth * 2.5F)) {
				action = ActionType.AT2;
				return action;
			}
		} else {
			if ((mp.y >= y1 - this.m_lineWidth * 5) && (mp.y <= y1 + this.m_lineWidth * 5)) {
				action = ActionType.AT1;
				return action;
			}
			if ((mp.y >= y2 - this.m_lineWidth * 5) && (mp.y <= y2 + this.m_lineWidth * 5)) {
				action = ActionType.AT2;
				return action;
			}
		}
		float x3 = 0.0F;
		float y3 = 0.0F;
		if (y1 != y2) {
			RefObject<Float> tempRef_x3 = new RefObject(Float.valueOf(x3));
			RefObject<Float> tempRef_y3 = new RefObject(Float.valueOf(y3));
			NullPoint(x1, y1, x2, y2, tempRef_x3, tempRef_y3);
			x3 = ((Float) tempRef_x3.argvalue).floatValue();
			y3 = ((Float) tempRef_y3.argvalue).floatValue();
		}
		if (SelectTriangle(mp, x1, y1, x2, y2, x3, y3)) {
			action = ActionType.MOVE;
			return action;
		}
		return action;
	}

	private double[] GetNullPointParams(HashMap<Integer, CPlotMarkMe> pList) {
		if ((pList.isEmpty()) || (this.m_sourceFields == null) || (this.m_sourceFields.isEmpty())
				|| (!this.m_sourceFields.containsKey("CLOSE"))) {
			return null;
		}
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		double leftClose = 0.0D;
		double rightClose = 0.0D;
		double close1 = this.m_dataSource.Get2(bIndex, ((Integer) this.m_sourceFields.get("CLOSE")).intValue());
		double close2 = this.m_dataSource.Get2(eIndex, ((Integer) this.m_sourceFields.get("CLOSE")).intValue());
		if (eIndex >= bIndex) {
			leftClose = this.m_dataSource.Get2(bIndex, ((Integer) this.m_sourceFields.get("CLOSE")).intValue());
			rightClose = this.m_dataSource.Get2(eIndex, ((Integer) this.m_sourceFields.get("CLOSE")).intValue());
		} else {
			leftClose = this.m_dataSource.Get2(eIndex, ((Integer) this.m_sourceFields.get("CLOSE")).intValue());
			rightClose = this.m_dataSource.Get2(bIndex, ((Integer) this.m_sourceFields.get("CLOSE")).intValue());
		}
		return new double[] { leftClose, rightClose };
	}

	private void NullPoint(float x1, float y1, float x2, float y2, RefObject<Float> nullX, RefObject<Float> nullY) {
		float k1 = 0.0F;
		float k2 = 0.0F;
		float b1 = 0.0F;
		float b2 = 0.0F;
		if (y1 >= y2) {
			k1 = -(float) Math.tan(45.0D);
			k2 = -(float) Math.tan(60.0D);
			b1 = y1 - k1 * x1;
			b2 = y2 - k2 * x2;
		} else {
			k1 = (float) Math.tan(45.0D);
			k2 = (float) Math.tan(60.0D);
			b1 = y1 - k1 * x1;
			b2 = y2 - k2 * x2;
		}
		nullX.argvalue = Float.valueOf((b2 - b1) / (k1 - k2));
		nullY.argvalue = Float.valueOf(((Float) nullX.argvalue).floatValue() * k1 + b1);
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

	protected void Paint(com.melib.Base.CPaintMe paint, HashMap<Integer, CPlotMarkMe> pList, long lineColor) {
		if (pList.isEmpty()) {
			return;
		}
		int bIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(0))).GetKey());
		int aIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		double[] closeParam = GetNullPointParams(pList);
		double leftClose = closeParam[1];
		double rightClose = closeParam[0];
		float y1 = PY(leftClose);
		float y2 = PY(rightClose);
		float x1 = PX(bIndex >= aIndex ? bIndex : aIndex);
		float x2 = PX(bIndex >= aIndex ? aIndex : bIndex);
		DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2);
		float nullX = 0.0F;
		float nullY = 0.0F;
		if (y1 != y2) {
			RefObject<Float> tempRef_nullX = new RefObject(Float.valueOf(nullX));
			RefObject<Float> tempRef_nullY = new RefObject(Float.valueOf(nullY));
			NullPoint(x1, y1, x2, y2, tempRef_nullX, tempRef_nullY);
			nullX = ((Float) tempRef_nullX.argvalue).floatValue();
			nullY = ((Float) tempRef_nullY.argvalue).floatValue();
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, nullX, nullY);
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x2, y2, nullX, nullY);
		}
		if (IsSelected()) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
		}
	}
}
