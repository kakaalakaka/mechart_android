package com.melib.Chart.Plot;

import java.util.HashMap;
import com.melib.Base.POINT;
import com.melib.Base.POINTF;
import com.melib.Base.RefObject;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class PlotSpeedresist extends CPlotBaseMe {
	public PlotSpeedresist() {
		SetPlotType("SPEEDRESIST");
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
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) this.m_marks.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		if ((SelectPoint(mp, x1, y1)) || (this.m_moveTimes == 1)) {
			action = ActionType.AT1;
			return action;
		}
		if (SelectPoint(mp, x2, y2)) {
			action = ActionType.AT2;
			return action;
		}
		POINTF firstP = new POINTF(x2, y2 - (y2 - y1) / 3.0F);
		POINTF secondP = new POINTF(x2, y2 - (y2 - y1) * 2.0F / 3.0F);
		POINTF startP = new POINTF(x1, y1);
		float oK = 0.0F;
		float oB = 0.0F;
		float fK = 0.0F;
		float fB = 0.0F;
		float sK = 0.0F;
		float sB = 0.0F;
		RefObject<Float> tempRef_oK = new RefObject(Float.valueOf(oK));
		RefObject<Float> tempRef_oB = new RefObject(Float.valueOf(oB));
		com.melib.Base.CMathLibMe.M107(x1, y1, x2, y2, 0.0F, 0.0F, tempRef_oK, tempRef_oB);
		oK = ((Float) tempRef_oK.argvalue).floatValue();
		oB = ((Float) tempRef_oB.argvalue).floatValue();
		RefObject<Float> tempRef_fK = new RefObject(Float.valueOf(fK));
		RefObject<Float> tempRef_fB = new RefObject(Float.valueOf(fB));
		com.melib.Base.CMathLibMe.M107(startP.x, startP.y, firstP.x, firstP.y, 0.0F, 0.0F, tempRef_fK, tempRef_fB);
		fK = ((Float) tempRef_fK.argvalue).floatValue();
		fB = ((Float) tempRef_fB.argvalue).floatValue();
		RefObject<Float> tempRef_sK = new RefObject(Float.valueOf(sK));
		RefObject<Float> tempRef_sB = new RefObject(Float.valueOf(sB));
		com.melib.Base.CMathLibMe.M107(startP.x, startP.y, secondP.x, secondP.y, 0.0F, 0.0F, tempRef_sK, tempRef_sB);
		sK = ((Float) tempRef_sK.argvalue).floatValue();
		sB = ((Float) tempRef_sB.argvalue).floatValue();
		float smallX = x1 <= x2 ? x1 : x2;
		float smallY = y1 <= y2 ? y1 : y2;
		float bigX = x1 > x2 ? x1 : x2;
		float bigY = y1 > y2 ? y1 : y2;
		if ((mp.x >= smallX - 2.0F) && (mp.x <= bigX + 2.0F) && (mp.y >= smallY - 2.0F) && (mp.y <= bigY + 2.0F)) {
			if ((oK != 0.0F) || (oB != 0.0F)) {
				if ((mp.y / (mp.x * oK + oB) >= 0.9D) && (mp.y / (mp.x * oK + oB) <= 1.1D)) {
					action = ActionType.MOVE;
					return action;
				}
			} else {
				action = ActionType.MOVE;
				return action;
			}
		}
		if (((x2 > x1) && (mp.x >= x1 - 2.0F)) || ((mp.x <= x1 + 2.0F) && (x2 < x1))) {
			if ((fK != 0.0F) || (fB != 0.0F)) {
				if ((mp.y / (mp.x * fK + fB) >= 0.9D) && (mp.y / (mp.x * fK + fB) <= 1.1D)) {
					action = ActionType.MOVE;
					return action;
				}
			}
			if ((sK != 0.0F) || (sB != 0.0F)) {
				if ((mp.y / (mp.x * sK + sB) >= 0.9D) && (mp.y / (mp.x * sK + sB) <= 1.1D)) {
					action = ActionType.MOVE;
					return action;
				}
			}
		}
		return action;
	}

	public boolean OnCreate(POINT mp) {
		return Create2PointsA(mp);
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
		int eIndex = this.m_dataSource.GetRowIndex(((CPlotMarkMe) pList.get(Integer.valueOf(1))).GetKey());
		float x1 = PX(bIndex);
		float x2 = PX(eIndex);
		DrawLine(paint, lineColor, this.m_lineWidth, 1, x1, y1, x2, y2);
		if ((IsSelected()) || (x1 == x2)) {
			DrawSelect(paint, lineColor, x1, y1);
			DrawSelect(paint, lineColor, x2, y2);
		}
		if ((x1 != x2) && (y1 != y2)) {
			POINT firstP = new POINT(x2, y2 - (y2 - y1) / 3.0F);
			POINT secondP = new POINT(x2, y2 - (y2 - y1) * 2.0F / 3.0F);
			POINT startP = new POINT(x1, y1);
			float fK = 0.0F;
			float fB = 0.0F;
			float sK = 0.0F;
			float sB = 0.0F;
			RefObject<Float> tempRef_fK = new RefObject(Float.valueOf(fK));
			RefObject<Float> tempRef_fB = new RefObject(Float.valueOf(fB));
			com.melib.Base.CMathLibMe.M107(startP.x, startP.y, firstP.x, firstP.y, 0.0F, 0.0F, tempRef_fK, tempRef_fB);
			fK = ((Float) tempRef_fK.argvalue).floatValue();
			fB = ((Float) tempRef_fB.argvalue).floatValue();
			RefObject<Float> tempRef_sK = new RefObject(Float.valueOf(sK));
			RefObject<Float> tempRef_sB = new RefObject(Float.valueOf(sB));
			com.melib.Base.CMathLibMe.M107(startP.x, startP.y, secondP.x, secondP.y, 0.0F, 0.0F, tempRef_sK,
					tempRef_sB);
			sK = ((Float) tempRef_sK.argvalue).floatValue();
			sB = ((Float) tempRef_sB.argvalue).floatValue();
			float newYF = 0.0F;
			float newYS = 0.0F;
			float newX = 0.0F;
			if (x2 > x1) {
				newYF = fK * GetWorkingAreaWidth() + fB;
				newYS = sK * GetWorkingAreaWidth() + sB;
				newX = GetWorkingAreaWidth();
			} else {
				newYF = fB;
				newYS = sB;
				newX = 0.0F;
			}
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, startP.x, startP.y, newX, newYF);
			DrawLine(paint, lineColor, this.m_lineWidth, this.m_lineStyle, startP.x, startP.y, newX, newYS);
		}
	}
}
