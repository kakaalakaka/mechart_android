package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotGannbox extends CPlotBaseMe {
	public PlotGannbox() {
		SetPlotType("GANNBOX");
	}

	private POINT oppositePoint = new POINT();

	public ActionType GetAction() {
		ActionType action = ActionType.NO;
		if (this.m_marks.isEmpty()) {
			return action;
		}
		return GetClickStatus();
	}

	private ActionType GetClickStatus() {
		POINT mp = GetMouseOverPoint();
		RECT rect = GetRectangle((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0)),
				(CPlotMarkMe) this.m_marks.get(Integer.valueOf(1)));
		int x1 = rect.left;
		int y1 = rect.top;
		int x2 = rect.right;
		int y2 = rect.top;
		int x3 = rect.left;
		int y3 = rect.bottom;
		int x4 = rect.right;
		int y4 = rect.bottom;
		if (SelectPoint(mp, x1, y1)) {
			return ActionType.AT1;
		}
		if (SelectPoint(mp, x2, y2)) {
			return ActionType.AT2;
		}
		if (SelectPoint(mp, x3, y3)) {
			return ActionType.AT3;
		}
		if (SelectPoint(mp, x4, y4)) {
			return ActionType.AT4;
		}

		int sub = (int) (this.m_lineWidth * 2.5D);
		RECT bigRect = new RECT(rect.left - sub, rect.top - sub, rect.right + sub, rect.bottom + sub);
		if ((mp.x >= bigRect.left) && (mp.x <= bigRect.right) && (mp.y >= bigRect.top) && (mp.y <= bigRect.bottom)) {
			if ((rect.right - rect.left <= 4) || (rect.bottom - rect.top <= 4)) {
				return ActionType.MOVE;
			}

			RECT smallRect = new RECT(rect.left + sub, rect.top + sub, rect.right - sub, rect.bottom - sub);
			if ((mp.x < smallRect.left) || (mp.x > smallRect.right) || (mp.y < smallRect.top)
					|| (mp.y > smallRect.bottom)) {
				return ActionType.MOVE;
			}

			x1 = rect.left;
			y1 = rect.bottom;
			x2 = rect.right;
			y2 = rect.top;
			POINT startP = new POINT(x1, y1);
			POINT[] listP = GetGannBoxPoints(x1, y1, x2, y2);

			for (int i = 0; i < listP.length; i++) {
				boolean selected = SelectLine(mp, startP.x, startP.y, listP[i].x, listP[i].y);
				if (selected) {
					return ActionType.MOVE;
				}
			}
			boolean selected = SelectLine(mp, startP.x, startP.y, x2, y2);
			if (selected) {
				return ActionType.MOVE;
			}
			x1 = rect.left;
			y1 = rect.top;
			x2 = rect.right;
			y2 = rect.bottom;
			listP = GetGannBoxPoints(x1, y1, x2, y2);
			for (int i = 0; i < listP.length; i++) {
				selected = SelectLine(mp, startP.x, startP.y, listP[i].x, listP[i].y);
				if (selected) {
					return ActionType.MOVE;
				}
			}
			startP = new POINT(x1, y1);
			selected = SelectLine(mp, startP.x, startP.y, x2, y2);
			if (selected) {
				return ActionType.MOVE;
			}
		}

		return ActionType.NO;
	}

	private POINT[] GetGannBoxPoints(float x1, float y1, float x2, float y2) {
		POINT firstP = new POINT(x2, y2 - (y2 - y1) * 0.875F);
		POINT secondP = new POINT(x2, y2 - (y2 - y1) * 0.75F);
		POINT thirdP = new POINT(x2, y2 - (y2 - y1) * 0.67F);
		POINT forthP = new POINT(x2, y2 - (y2 - y1) * 0.5F);
		POINT fifthP = new POINT(x2 - (x2 - x1) * 0.875F, y2);
		POINT sixthP = new POINT(x2 - (x2 - x1) * 0.75F, y2);
		POINT seventhP = new POINT(x2 - (x2 - x1) * 0.67F, y2);
		POINT eighthP = new POINT(x2 - (x2 - x1) * 0.5F, y2);
		return new POINT[] { firstP, secondP, thirdP, forthP, fifthP, sixthP, seventhP, eighthP };
	}

	public boolean OnCreate(POINT mp) {
		return Create2PointsB(mp);
	}

	public void OnMoveStart() {
		this.m_moveTimes += 1;
		this.m_action = GetAction();
		RECT rect = GetRectangle((CPlotMarkMe) this.m_marks.get(Integer.valueOf(0)),
				(CPlotMarkMe) this.m_marks.get(Integer.valueOf(1)));
		int x1 = rect.left;
		int y1 = rect.top;
		int x2 = rect.right;
		int y2 = rect.top;
		int x3 = rect.left;
		int y3 = rect.bottom;
		int x4 = rect.right;
		int y4 = rect.bottom;
		switch (this.m_action) {
		case AT1:
			this.oppositePoint = new POINT(x4, y4);
			break;
		case AT2:
			this.oppositePoint = new POINT(x3, y3);
			break;
		case AT3:
			this.oppositePoint = new POINT(x2, y2);
			break;
		case AT4:
			this.oppositePoint = new POINT(x1, y1);
		}

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
		case MOVE:
			Move(mp);
			break;
		case AT1:
		case AT2:
		case AT3:
		case AT4:
			Resize(mp, this.oppositePoint);
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
		RECT rect = GetRectangle((CPlotMarkMe) pList.get(Integer.valueOf(0)), (CPlotMarkMe) pList.get(Integer.valueOf(1)));
		int leftVScaleWidth = GetChart().GetLeftVScaleWidth();
		int titleHeight = GetDiv().GetTitleBar().GetHeight();
		paint.SetClip(new RECT(rect.left + leftVScaleWidth, rect.top + titleHeight, rect.right + leftVScaleWidth,
				rect.bottom + titleHeight));
		if ((rect.right - rect.left > 0) && (rect.bottom - rect.top > 0)) {
			DrawRect(paint, lineColor, this.m_lineWidth, this.m_lineStyle, rect.left, rect.top, rect.right,
					rect.bottom);
		}
		POINT oP = new POINT(rect.left, rect.top);
		int x1 = rect.left;
		int y1 = rect.bottom;
		int x2 = rect.right;
		int y2 = rect.top;
		if ((x1 != x2) && (y1 != y2)) {
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2);
			POINT startP = new POINT(x1, y1);
			POINT[] listP = GetGannBoxPoints(x1, y1, x2, y2);
			float k = 0.0F;
			float b = 0.0F;
			for (int i = 0; i < listP.length; i++) {
				RefObject<Float> tempRef_k = new RefObject(Float.valueOf(k));
				RefObject<Float> tempRef_b = new RefObject(Float.valueOf(b));
				com.melib.Base.CMathLibMe.M107(startP.x, startP.y, listP[i].x, listP[i].y, 0.0F, 0.0F, tempRef_k,
						tempRef_b);
				k = ((Float) tempRef_k.argvalue).floatValue();
				b = ((Float) tempRef_b.argvalue).floatValue();
				float newX = 0.0F;
				float newY = 0.0F;
				if (x2 > x1) {
					newY = k * x2 + b;
					newX = x2;
				} else {
					newY = b;
					newX = x1;
				}
				DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, startP.x, startP.y, newX, newY);
			}
			x1 = rect.left;
			y1 = rect.top;
			x2 = rect.right;
			y2 = rect.bottom;
			listP = GetGannBoxPoints(x1, y1, x2, y2);
			startP = new POINT(x1, y1);
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, x1, y1, x2, y2);
			for (int i = 0; i < listP.length; i++) {
				RefObject<Float> tempRef_k2 = new RefObject(Float.valueOf(k));
				RefObject<Float> tempRef_b2 = new RefObject(Float.valueOf(b));
				com.melib.Base.CMathLibMe.M107(startP.x, startP.y, listP[i].x, listP[i].y, 0.0F, 0.0F, tempRef_k2,
						tempRef_b2);
				k = ((Float) tempRef_k2.argvalue).floatValue();
				b = ((Float) tempRef_b2.argvalue).floatValue();
				float newX = 0.0F;
				float newY = 0.0F;
				if (x2 > x1) {
					newY = k * x2 + b;
					newX = x2;
				} else {
					newY = b;
					newX = x1;
				}
				DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, startP.x, startP.y, newX, newY);
			}
		}
		if (IsSelected()) {
			DrawSelect(paint, lineColor, rect.left, rect.top);
			DrawSelect(paint, lineColor, rect.right, rect.top);
			DrawSelect(paint, lineColor, rect.left, rect.bottom);
			DrawSelect(paint, lineColor, rect.right, rect.bottom);
		}
	}
}
