package com.melib.Chart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import com.melib.Base.CMathLibMe;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlMe;
import com.melib.Base.CControlHostMe;
import com.melib.Base.FONT;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CChartMe extends CControlMe {
	public CChartMe() {
		this.m_timerID = GetNewTimerID();
	}

	protected void finalize() throws Throwable {
		Dispose();
	}

	protected ArrayList<CDivMe> m_divs = new ArrayList();

	protected int m_cross_y = -1;

	protected int m_hResizeType = 0;

	protected int m_lastUnEmptyIndex = -1;

	protected boolean m_lastRecordIsVisible;

	protected double m_lastVisibleKey;

	protected boolean m_isMouseMove = true;

	protected boolean m_isScrollCross = false;

	protected POINT m_lastMouseClickPoint = new POINT(-1, -1);

	protected POINT m_lastMouseMovePoint = new POINT();

	protected Calendar m_lastMouseMoveTime = Calendar.getInstance();

	protected int m_scrollStep = 1;

	protected boolean m_showingSelectArea = false;

	protected boolean m_showingToolTip = false;

	private int m_timerID;

	protected static int m_tooltip_dely = 2500000;

	protected CDivMe m_userResizeDiv = null;

	protected boolean m_autoFillHScale = false;
	protected int m_blankSpace;

	public boolean GetAutoFillHScale() {
		return this.m_autoFillHScale;
	}

	public void SetAutoFillHScale(boolean value) {
		this.m_autoFillHScale = value;
	}

	public int GetBlankSpace() {
		return this.m_blankSpace;
	}

	public void SetBlankSpace(int value) {
		this.m_blankSpace = value;
	}

	protected boolean m_canResizeV = true;

	public boolean CanResizeV() {
		return this.m_canResizeV;
	}

	public void SetCanResizeV(boolean value) {
		this.m_canResizeV = value;
	}

	protected boolean m_canResizeH = true;

	public boolean CanResizeH() {
		return this.m_canResizeH;
	}

	public void SetCanResizeH(boolean value) {
		this.m_canResizeH = value;
	}

	protected boolean m_canMoveShape = true;

	public boolean CanMoveShape() {
		return this.m_canMoveShape;
	}

	public void SetCanMoveShape(boolean value) {
		this.m_canMoveShape = value;
	}

	protected CrossLineMoveMode m_crossLineMoveMode = CrossLineMoveMode.FollowMouse;

	public CrossLineMoveMode GetCrossLineMoveMode() {
		return this.m_crossLineMoveMode;
	}

	public void SetCrossLineMoveMode(CrossLineMoveMode value) {
		this.m_crossLineMoveMode = value;
	}

	protected boolean m_canScroll = true;

	public boolean GetCanScroll() {
		return this.m_canScroll;
	}

	public void SetCanScroll(boolean value) {
		this.m_canScroll = value;
	}

	protected boolean m_canZoom = true;
	protected int m_crossStopIndex;
	protected CTableMe m_dataSource;

	public boolean CanZoom() {
		return this.m_canZoom;
	}

	public void SetCanZoom(boolean value) {
		this.m_canZoom = value;
	}

	public int GetCrossStopIndex() {
		return this.m_crossStopIndex;
	}

	public void SetCrossStopIndex(int value) {
		this.m_crossStopIndex = value;
	}

	public CTableMe GetDataSource() {
		return this.m_dataSource;
	}

	protected int m_firstVisibleIndex = -1;
	protected String m_hScaleFieldText;

	public int GetFirstVisibleIndex() {
		return this.m_firstVisibleIndex;
	}

	public String GetHScaleFieldText() {
		return this.m_hScaleFieldText;
	}

	public void SetHScaleFieldText(String value) {
		this.m_hScaleFieldText = value;
	}

	protected double m_hScalePixel = 7.0D;

	public double GetHScalePixel() {
		return this.m_hScalePixel;
	}

	public void SetHScalePixel(double value) {
		this.m_hScalePixel = value;
		if (this.m_hScalePixel > 1.0D) {
			this.m_hScalePixel = ((int) this.m_hScalePixel);
		}
		if ((this.m_hScalePixel > 1.0D) && (this.m_hScalePixel % 2.0D == 0.0D)) {
			this.m_hScalePixel += 1.0D;
		}
	}

	protected int m_lastVisibleIndex = -1;

	public int GetLastVisibleIndex() {
		return this.m_lastVisibleIndex;
	}

	protected int m_leftVScaleWidth = 80;
	protected int m_maxVisibleRecord;

	public int GetLeftVScaleWidth() {
		return this.m_leftVScaleWidth;
	}

	public void SetLeftVScaleWidth(int value) {
		this.m_leftVScaleWidth = value;
	}

	public int GetMaxVisibleRecord() {
		return this.m_maxVisibleRecord;
	}

	protected CBaseShapeMe m_movingShape = null;

	public CBaseShapeMe GetMovingShape() {
		return this.m_movingShape;
	}

	public void SetMovingShape(CBaseShapeMe value) {
		this.m_movingShape = value;
	}

	protected CPlotMe m_movingPlot = null;

	public CPlotMe GetMovingPlot() {
		return this.m_movingPlot;
	}

	public void SetMovingPlot(CPlotMe value) {
		this.m_movingPlot = value;
	}

	protected boolean m_reverseHScale = false;

	public boolean GetReverseHScale() {
		return this.m_reverseHScale;
	}

	public void SetReverseHScale(boolean value) {
		this.m_reverseHScale = value;
	}

	protected int m_rightVScaleWidth = 0;

	public int GetRightVScaleWidth() {
		return this.m_rightVScaleWidth;
	}

	public void SetRightVScaleWidth(int value) {
		this.m_rightVScaleWidth = value;
	}

	protected boolean m_scrollAddSpeed = false;

	public boolean ScrollAddSpeed() {
		return this.m_scrollAddSpeed;
	}

	public void SetScrollAddSpeed(boolean value) {
		this.m_scrollAddSpeed = value;
	}

	public CBaseShapeMe GetSelectedShape() {
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe cDiv : divsCopy) {
			ArrayList<CBaseShapeMe> shapesCopy = cDiv.GetShapes(SortType.NONE);
			for (CBaseShapeMe bs : shapesCopy) {
				if (bs.IsSelected()) {
					return bs;
				}
			}
		}
		return null;
	}

	public void SetSelectedShape(CBaseShapeMe value) {
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe cDiv : divsCopy) {
			ArrayList<CBaseShapeMe> shapesCopy = cDiv.GetShapes(SortType.ASC);
			for (CBaseShapeMe bs : shapesCopy) {
				if (bs == value) {
					bs.SetSelected(true);
				} else {
					bs.SetSelected(false);
				}
			}
		}
	}

	public CPlotMe GetSelectedPlot() {
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe div : divsCopy) {
			ArrayList<CPlotMe> plotsCopy = div.GetPlots(SortType.NONE);
			for (CPlotMe plot : plotsCopy) {
				if ((plot.IsVisible()) && (plot.IsSelected())) {
					return plot;
				}
			}
		}
		return null;
	}

	public void SetSelectedPlot(CPlotMe value) {
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe div : divsCopy) {
			ArrayList<CPlotMe> plotsCopy = div.GetPlots(SortType.NONE);
			for (CPlotMe plot : plotsCopy) {
				if (plot == value) {
					plot.SetSelected(true);
				} else {
					plot.SetSelected(false);
				}
			}
		}
	}

	public CDivMe GetSelectedDiv() {
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe div : divsCopy) {
			if (div.IsSelected()) {
				return div;
			}
		}
		return null;
	}

	public void SetSelectedDiv(CDivMe value) {
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe div : divsCopy) {
			if (div == value) {
				div.SetSelected(true);
			} else {
				div.SetSelected(false);
			}
		}
	}

	protected boolean m_showCrossLine = false;
	protected int m_workingAreaWidth;

	public boolean GetShowCrossLine() {
		return this.m_showCrossLine;
	}

	public void SetShowCrossLine(boolean value) {
		this.m_showCrossLine = value;
	}

	public int GetWorkingAreaWidth() {
		return this.m_workingAreaWidth;
	}

	public CDivMe AddDiv(int vPercent) {
		if (vPercent <= 0) {
			return null;
		}
		CDivMe cDiv = new CDivMe();
		cDiv.SetVerticalPercent(vPercent);
		cDiv.SetChart(this);
		this.m_divs.add(cDiv);
		Update();
		return cDiv;
	}

	public CDivMe AddDiv() {
		ArrayList<CDivMe> divsCopy = GetDivs();
		int pNum = divsCopy.size() + 1;
		return AddDiv(100 / pNum);
	}

	public void Adjust() {
		if (this.m_workingAreaWidth > 0) {
			this.m_lastUnEmptyIndex = -1;
			if ((this.m_firstVisibleIndex < 0) || (this.m_lastVisibleIndex > this.m_dataSource.GetRowsCount() - 1)) {
				return;
			}
			ArrayList<CDivMe> divsCopy = GetDivs();
			for (CDivMe cDiv : divsCopy) {
				cDiv.SetWorkingAreaHeight(
						cDiv.GetHeight() - cDiv.GetHScale().GetHeight() - cDiv.GetTitleBar().GetHeight() - 1);
				ArrayList<CBaseShapeMe> shapesCopy = cDiv.GetShapes(SortType.NONE);
				double leftMax = 0.0D;
				double leftMin = 0.0D;
				double rightMax = 0.0D;
				double rightMin = 0.0D;
				boolean leftMaxInit = false;
				boolean leftMinInit = false;
				boolean rightMaxInit = false;
				boolean rightMinInit = false;
				CVScaleMe leftVScale = cDiv.GetLeftVScale();
				CVScaleMe rightVScale = cDiv.GetRightVScale();
				if (this.m_dataSource.GetRowsCount() > 0) {
					for (CBaseShapeMe bs : shapesCopy) {
						if (bs.IsVisible()) {

							CBarShapeMe bar = (CBarShapeMe) ((bs instanceof CBarShapeMe) ? bs : null);
							int[] fields = bs.GetFields();
							if (fields != null) {

								for (int f = 0; f < fields.length; f++) {
									int field = this.m_dataSource.GetColumnIndex(fields[f]);
									for (int i = this.m_firstVisibleIndex; i <= this.m_lastVisibleIndex; i++) {
										double fieldValue = this.m_dataSource.Get3(i, field);
										if (!Double.isNaN(fieldValue)) {
											this.m_lastUnEmptyIndex = i;
											if (bs.GetAttachVScale() == AttachVScale.Left) {
												if ((fieldValue > leftMax) || (!leftMaxInit)) {
													leftMaxInit = true;
													leftMax = fieldValue;
												}
												if ((fieldValue < leftMin) || (!leftMinInit)) {
													leftMinInit = true;
													leftMin = fieldValue;
												}
											} else {
												if ((fieldValue > rightMax) || (!rightMaxInit)) {
													rightMaxInit = true;
													rightMax = fieldValue;
												}
												if ((fieldValue < rightMin) || (!rightMinInit)) {
													rightMinInit = true;
													rightMin = fieldValue;
												}
											}
										}
									}
								}
								if (bar != null) {
									if (bar.GetFieldName2() == CTableMe.NULLFIELD) {
										double midValue = 0.0D;
										if (bs.GetAttachVScale() == AttachVScale.Left) {
											if ((midValue > leftMax) || (!leftMaxInit)) {
												leftMaxInit = true;
												leftMax = midValue;
											}
											if ((midValue < leftMin) || (!leftMinInit)) {
												leftMinInit = true;
												leftMin = midValue;
											}
										} else {
											if ((midValue > rightMax) || (!rightMaxInit)) {
												rightMaxInit = true;
												rightMax = midValue;
											}
											if ((midValue < rightMin) || (!rightMinInit)) {
												rightMinInit = true;
												rightMin = midValue;
											}
										}
									}
								}
							}
						}
					}
					if (leftMax == leftMin) {
						leftMax *= 1.01D;
						leftMin *= 0.99D;
					}
					if (rightMax == rightMin) {
						rightMax *= 1.01D;
						rightMin *= 0.99D;
					}
				}
				if (leftVScale.AutoMaxMin()) {
					leftVScale.SetVisibleMax(leftMax);
					leftVScale.SetVisibleMin(leftMin);
				}
				if (rightVScale.AutoMaxMin()) {
					rightVScale.SetVisibleMax(rightMax);
					rightVScale.SetVisibleMin(rightMin);
				}
				if ((leftVScale.AutoMaxMin()) && (leftVScale.GetVisibleMax() == 0.0D)
						&& (leftVScale.GetVisibleMin() == 0.0D)) {
					leftVScale.SetVisibleMax(rightVScale.GetVisibleMax());
					leftVScale.SetVisibleMin(rightVScale.GetVisibleMin());
				}
				if ((rightVScale.AutoMaxMin()) && (rightVScale.GetVisibleMax() == 0.0D)
						&& (rightVScale.GetVisibleMin() == 0.0D)) {
					rightVScale.SetVisibleMax(leftVScale.GetVisibleMax());
					rightVScale.SetVisibleMin(leftVScale.GetVisibleMin());
				}
			}
		}
	}

	public void AddPlot(CPlotMe bpl, POINT mp, CDivMe div) {
		if ((div != null) && (this.m_dataSource.GetRowsCount() >= 2)) {
			int rIndex = GetIndex(mp);
			if ((rIndex < 0) || (rIndex > this.m_lastVisibleIndex)) {
				return;
			}
			if (bpl != null) {
				bpl.SetDiv(div);
				bpl.SetSelected(true);
				ArrayList<Double> zorders = new ArrayList();
				ArrayList<CPlotMe> plots = div.GetPlots(SortType.NONE);
				int plotSize = plots.size();
				for (int i = 0; i < plotSize; i++) {
					zorders.add(Double.valueOf(((CPlotMe) plots.get(i)).GetZOrder()));
				}
				int zordersSize = zorders.size();
				double[] zordersArray = new double[zordersSize];
				for (int i = 0; i < zordersSize; i++) {
					zordersArray[i] = ((Double) zorders.get(i)).doubleValue();
				}
				bpl.SetZOrder((int) CMathLibMe.GetMax(zordersArray, zordersSize) + 1);
				boolean flag = bpl.OnCreate(mp);
				if (flag) {
					div.AddPlot(bpl);
					SetMovingPlot(bpl);
					GetMovingPlot().OnMoveStart();
				}
			}
			CloseSelectArea();
		}
	}

	public void ChangeChart(ScrollType scrollType, int limitStep) {
		ArrayList<CDivMe> divsCopy = GetDivs();
		if ((divsCopy.isEmpty()) || (this.m_dataSource.GetRowsCount() == 0)) {
			return;
		}
		int fIndex = this.m_firstVisibleIndex;
		int lIndex = this.m_lastVisibleIndex;
		double axis = this.m_hScalePixel;
		boolean flag = false;
		boolean locateCrossHairFlag = false;
		switch (scrollType) {
		case Left:
			if (this.m_canScroll) {
				flag = true;
				if (this.m_showCrossLine) {
					if (limitStep > this.m_scrollStep) {
						ScrollCrossLineLeft(limitStep);
					} else {
						ScrollCrossLineLeft(this.m_scrollStep);
					}
					locateCrossHairFlag = true;

				} else if (limitStep > this.m_scrollStep) {
					ScrollLeft(limitStep);
				} else {
					ScrollLeft(this.m_scrollStep);
				}
			}

			break;
		case Right:
			if (this.m_canScroll) {
				flag = true;
				if (this.m_showCrossLine) {
					if (limitStep > this.m_scrollStep) {
						ScrollCrossLineRight(limitStep);
					} else {
						ScrollCrossLineRight(this.m_scrollStep);
					}
					locateCrossHairFlag = true;

				} else if (limitStep > this.m_scrollStep) {
					ScrollRight(limitStep);
				} else {
					ScrollRight(this.m_scrollStep);
				}
			}

			break;
		case ZoomIn:
			if (this.m_canZoom) {
				flag = true;
				ZoomIn();
			}
			break;
		case ZoomOut:
			if (this.m_canZoom) {
				flag = true;
				ZoomOut();
			}
			break;
		}
		if (flag) {
			int fIndex_after = this.m_firstVisibleIndex;
			int lIndex_after = this.m_lastVisibleIndex;
			double axis_after = this.m_hScalePixel;
			int fi = this.m_firstVisibleIndex;
			int li = this.m_lastVisibleIndex;
			RefObject<Integer> tempRef_fi = new RefObject(Integer.valueOf(fi));
			RefObject<Integer> tempRef_li = new RefObject(Integer.valueOf(li));
			CorrectVisibleRecord(this.m_dataSource.GetRowsCount(), tempRef_fi, tempRef_li);
			fi = ((Integer) tempRef_fi.argvalue).intValue();
			li = ((Integer) tempRef_li.argvalue).intValue();
			this.m_firstVisibleIndex = fi;
			this.m_lastVisibleIndex = li;
			if ((fIndex != fIndex_after) || (lIndex != lIndex_after)) {
				Adjust();
			}
			ResetCrossOverIndex();
			if (locateCrossHairFlag) {
				LocateCrossLine();
			}
			if ((fIndex == fIndex_after) && (lIndex == lIndex_after) && (axis == axis_after)) {
				Invalidate();
			} else {
				Update();
				Invalidate();
			}
		}
		if ((this.m_scrollAddSpeed) && ((scrollType == ScrollType.Left) || (scrollType == ScrollType.Right))) {
			if (this.m_scrollStep < 50) {
				this.m_scrollStep += 5;
			}

		} else {
			this.m_scrollStep = 1;
		}
	}

	public void CheckToolTip() {
		Calendar calendar = Calendar.getInstance();
		if (calendar.getTimeInMillis() - this.m_lastMouseMoveTime.getTimeInMillis() >= m_tooltip_dely) {
			if (this.m_isMouseMove) {
				boolean show = true;
				if (IsOperating()) {
					show = false;
				}
				ArrayList<CDivMe> divsCopy = GetDivs();
				for (CDivMe div : divsCopy) {
					if (div.GetSelectArea().IsVisible()) {
						show = false;
					}
				}
				if (show) {
					this.m_showingToolTip = true;
					int curRecord = GetMouseOverIndex();
					CBaseShapeMe bs = SelectShape(curRecord, 0);
					if (bs != null) {
						Invalidate();
					}
				}
				this.m_isMouseMove = false;
			}
		}
	}

	protected void CheckLastVisibleIndex() {
		if (this.m_lastVisibleIndex > this.m_dataSource.GetRowsCount() - 1) {
			this.m_lastVisibleIndex = (this.m_dataSource.GetRowsCount() - 1);
		}
		if (this.m_dataSource.GetRowsCount() > 0) {
			this.m_lastVisibleKey = this.m_dataSource.GetXValue(this.m_lastVisibleIndex);
			if (this.m_lastVisibleIndex == this.m_dataSource.GetRowsCount() - 1) {
				this.m_lastRecordIsVisible = true;
			} else {
				this.m_lastRecordIsVisible = false;
			}
		} else {
			this.m_lastVisibleKey = 0.0D;
			this.m_lastRecordIsVisible = true;
		}
	}

	public void Clear() {
		ArrayList<CDivMe> divsCopy = GetDivs();
		CDivMe cDiv;
		for (Iterator i$ = divsCopy.iterator(); i$.hasNext();) {
			cDiv = (CDivMe) i$.next();

			for (CPlotMe plot : cDiv.GetPlots(SortType.NONE)) {
				cDiv.RemovePlot(plot);
				plot.Dispose();
			}
		}
		CloseSelectArea();
		this.m_dataSource.Clear();
		this.m_firstVisibleIndex = -1;
		this.m_lastVisibleIndex = -1;
		this.m_lastRecordIsVisible = true;
		this.m_lastVisibleIndex = 0;
		this.m_lastVisibleKey = 0.0D;
		this.m_showCrossLine = false;
	}

	public void ClearSelectedShape() {
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe cDiv : divsCopy) {
			ArrayList<CBaseShapeMe> shapesCopy = cDiv.GetShapes(SortType.NONE);
			for (CBaseShapeMe bs : shapesCopy) {
				bs.SetSelected(false);
			}
		}
	}

	public void ClearSelectedPlot() {
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe cDiv : divsCopy) {
			ArrayList<CPlotMe> plotsCopy = cDiv.GetPlots(SortType.NONE);
			for (CPlotMe bls : plotsCopy) {
				bls.SetSelected(false);
			}
		}
		SetMovingPlot(null);
	}

	public void ClearSelectedDiv() {
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe div : divsCopy) {
			div.SetSelected(false);
		}
	}

	public double DivMaxOrMin(int index, CDivMe div, int flag) {
		if (index < 0) {
			return 0.0D;
		}
		if (div != null) {
			ArrayList<Double> vList = new ArrayList();
			ArrayList<CBaseShapeMe> shapesCopy = div.GetShapes(SortType.NONE);
			for (CBaseShapeMe bs : shapesCopy) {
				if (bs.IsVisible()) {

					int[] fields = bs.GetFields();
					if (fields != null) {
						for (int i = 0; i < fields.length; i++) {
							double value = this.m_dataSource.Get2(index, fields[i]);
							if (!Double.isNaN(value)) {
								vList.add(Double.valueOf(value));
							}
						}
					}
				}
			}
			int vListSize = vList.size();
			double[] vListArray = new double[vListSize];
			for (int i = 0; i < vListSize; i++) {
				vListArray[i] = ((Double) vList.get(i)).doubleValue();
			}
			if (flag == 0) {
				return CMathLibMe.GetMax(vListArray, vListSize);
			}

			return CMathLibMe.GetMin(vListArray, vListSize);
		}

		return 0.0D;
	}

	public void Dispose() {
		if (!IsDisposed()) {
			StopTimer(this.m_timerID);
			RemoveAll();
		}
		super.Dispose();
	}

	public CDivMe FindDiv(POINT mp) {
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe cDiv : divsCopy) {
			if ((mp.y >= cDiv.GetTop()) && (mp.y <= cDiv.GetTop() + cDiv.GetHeight())) {
				return cDiv;
			}
		}
		return null;
	}

	public CDivMe FindDiv(CBaseShapeMe shape) {
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe div : divsCopy) {
			if (div.ContainsShape(shape)) {
				return div;
			}
		}
		return null;
	}

	public String GetControlType() {
		return "Chart";
	}

	public ArrayList<CDivMe> GetDivs() {
		ArrayList<CDivMe> divsCopy = new ArrayList();
		if (this.m_divs != null) {
			int divSize = this.m_divs.size();
			for (int i = 0; i < divSize; i++) {
				divsCopy.add(this.m_divs.get(i));
			}
		}
		return divsCopy;
	}

	public String GetHScaleDateString(double date, double lDate, RefObject<DateType> dateType) {
		int tm_year = 0;
		int tm_mon = 0;
		int tm_mday = 0;
		int tm_hour = 0;
		int tm_min = 0;
		int tm_sec = 0;
		int tm_msec = 0;
		RefObject<Integer> tempRef_tm_year = new RefObject(Integer.valueOf(tm_year));
		RefObject<Integer> tempRef_tm_mon = new RefObject(Integer.valueOf(tm_mon));
		RefObject<Integer> tempRef_tm_mday = new RefObject(Integer.valueOf(tm_mday));
		RefObject<Integer> tempRef_tm_hour = new RefObject(Integer.valueOf(tm_hour));
		RefObject<Integer> tempRef_tm_min = new RefObject(Integer.valueOf(tm_min));
		RefObject<Integer> tempRef_tm_sec = new RefObject(Integer.valueOf(tm_sec));
		RefObject<Integer> tempRef_tm_msec = new RefObject(Integer.valueOf(tm_msec));
		CMathLibMe.M130(date, tempRef_tm_year, tempRef_tm_mon, tempRef_tm_mday, tempRef_tm_hour, tempRef_tm_min,
				tempRef_tm_sec, tempRef_tm_msec);
		tm_year = ((Integer) tempRef_tm_year.argvalue).intValue();
		tm_mon = ((Integer) tempRef_tm_mon.argvalue).intValue();
		tm_mday = ((Integer) tempRef_tm_mday.argvalue).intValue();
		tm_hour = ((Integer) tempRef_tm_hour.argvalue).intValue();
		tm_min = ((Integer) tempRef_tm_min.argvalue).intValue();
		tm_sec = ((Integer) tempRef_tm_sec.argvalue).intValue();
		tm_msec = ((Integer) tempRef_tm_msec.argvalue).intValue();
		int l_year = 0;
		int l_mon = 0;
		int l_mday = 0;
		int l_hour = 0;
		int l_min = 0;
		int l_sec = 0;
		int l_msec = 0;
		if (lDate > 0.0D) {
			RefObject<Integer> tempRef_l_year = new RefObject(Integer.valueOf(l_year));
			RefObject<Integer> tempRef_l_mon = new RefObject(Integer.valueOf(l_mon));
			RefObject<Integer> tempRef_l_mday = new RefObject(Integer.valueOf(l_mday));
			RefObject<Integer> tempRef_l_hour = new RefObject(Integer.valueOf(l_hour));
			RefObject<Integer> tempRef_l_min = new RefObject(Integer.valueOf(l_min));
			RefObject<Integer> tempRef_l_sec = new RefObject(Integer.valueOf(l_sec));
			RefObject<Integer> tempRef_l_msec = new RefObject(Integer.valueOf(l_msec));
			CMathLibMe.M130(lDate, tempRef_l_year, tempRef_l_mon, tempRef_l_mday, tempRef_l_hour, tempRef_l_min,
					tempRef_l_sec, tempRef_l_msec);
			l_year = ((Integer) tempRef_l_year.argvalue).intValue();
			l_mon = ((Integer) tempRef_l_mon.argvalue).intValue();
			l_mday = ((Integer) tempRef_l_mday.argvalue).intValue();
			l_hour = ((Integer) tempRef_l_hour.argvalue).intValue();
			l_min = ((Integer) tempRef_l_min.argvalue).intValue();
			l_sec = ((Integer) tempRef_l_sec.argvalue).intValue();
			l_msec = ((Integer) tempRef_l_msec.argvalue).intValue();
		}
		String num = "";
		if (tm_year > l_year) {
			dateType.argvalue = DateType.Year;
			return new Integer(tm_year).toString();
		}
		if (tm_mon > l_mon) {
			dateType.argvalue = DateType.Month;
			String mStr = new Integer(tm_mon).toString();
			if (tm_mon < 10) {
				mStr = "0" + mStr;
			}
			return mStr;
		}
		if (tm_mday > l_mday) {
			dateType.argvalue = DateType.Day;
			String dStr = new Integer(tm_mday).toString();
			if (tm_mday < 10) {
				dStr = "0" + dStr;
			}
			return dStr;
		}
		if ((tm_hour > l_hour) || (tm_min > l_min)) {
			dateType.argvalue = DateType.Minute;
			String hStr = new Integer(tm_hour).toString();
			if (tm_hour < 10) {
				hStr = "0" + hStr;
			}
			String mStr = new Integer(tm_min).toString();
			if (tm_min < 10) {
				mStr = "0" + mStr;
			}
			return hStr + ":" + mStr;
		}
		if (tm_sec > l_sec) {
			dateType.argvalue = DateType.Second;
			String sStr = new Integer(tm_sec).toString();
			if (tm_sec < 10) {
				sStr = "0" + sStr;
			}
			return sStr;
		}
		if (tm_msec > l_msec) {
			dateType.argvalue = DateType.Millisecond;
			return new Integer(l_msec).toString();
		}
		return "";
	}

	public int GetIndex(POINT mp) {
		if (this.m_reverseHScale) {
			mp.x = (this.m_workingAreaWidth - (mp.x - this.m_leftVScaleWidth) + this.m_leftVScaleWidth);
		}
		double pixel = this.m_hScalePixel;
		int index = GetChartIndex(mp.x, this.m_leftVScaleWidth, pixel, this.m_firstVisibleIndex);
		if (index < 0) {
			index = 0;
		}
		if (index > this.m_lastVisibleIndex) {
			index = this.m_lastVisibleIndex;
		}
		return index;
	}

	public CDivMe GetMouseOverDiv() {
		POINT mp = GetMousePoint();
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe cDiv : divsCopy) {
			if ((mp.y >= cDiv.GetTop()) && (mp.y <= cDiv.GetTop() + cDiv.GetHeight())) {
				return cDiv;
			}
		}
		return null;
	}

	public int GetMouseOverIndex() {
		POINT mp = GetMousePoint();
		if (this.m_reverseHScale) {
			mp.x = (this.m_workingAreaWidth - (mp.x - this.m_leftVScaleWidth) + this.m_leftVScaleWidth);
		}
		double pixel = this.m_hScalePixel;
		return GetChartIndex(mp.x, this.m_leftVScaleWidth, pixel, this.m_firstVisibleIndex);
	}

	public double GetNumberValue(CDivMe div, POINT mp, AttachVScale attachVScale) {
		CVScaleMe vScale = div.GetVScale(attachVScale);
		int vHeight = div.GetWorkingAreaHeight() - vScale.GetPaddingTop() - vScale.GetPaddingBottom();
		int cY = mp.y - div.GetTop() - div.GetTitleBar().GetHeight() - vScale.GetPaddingTop();
		if (vScale.IsReverse()) {
			cY = vHeight - cY;
		}
		if (vHeight > 0) {
			double max = 0.0D;
			double min = 0.0D;
			boolean isLog = false;
			if (attachVScale == AttachVScale.Left) {
				max = div.GetLeftVScale().GetVisibleMax();
				min = div.GetLeftVScale().GetVisibleMin();
				if ((max == 0.0D) && (min == 0.0D)) {
					max = div.GetRightVScale().GetVisibleMax();
					min = div.GetRightVScale().GetVisibleMin();
				}
				isLog = div.GetLeftVScale().GetSystem() == VScaleSystem.Logarithmic;
			} else if (attachVScale == AttachVScale.Right) {
				max = div.GetRightVScale().GetVisibleMax();
				min = div.GetRightVScale().GetVisibleMin();
				if ((max == 0.0D) && (min == 0.0D)) {
					max = div.GetLeftVScale().GetVisibleMax();
					min = div.GetLeftVScale().GetVisibleMin();
				}
				isLog = div.GetRightVScale().GetSystem() == VScaleSystem.Logarithmic;
			}
			if (isLog) {
				if (max >= 0.0D) {
					max = Math.log10(max);
				} else {
					max = -Math.log10(Math.abs(max));
				}
				if (min >= 0.0D) {
					min = Math.log10(min);
				} else {
					min = -Math.log10(Math.abs(min));
				}
				double value = GetVScaleValue(cY, max, min, vHeight);
				return Math.pow(10.0D, value);
			}

			return GetVScaleValue(cY, max, min, vHeight);
		}

		return 0.0D;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("autofillhscale")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(GetAutoFillHScale());
		} else if (name.equals("blankspace")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetBlankSpace());
		} else if (name.equals("canmoveshape")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(CanMoveShape());
		} else if (name.equals("canresizeh")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(CanResizeH());
		} else if (name.equals("canresizev")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(CanResizeV());
		} else if (name.equals("canscroll")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(GetCanScroll());
		} else if (name.equals("canzoom")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(CanZoom());
		} else if (name.equals("crosslinemovemode")) {
			type.argvalue = "enum:CrossLineMoveMode";
			if (GetCrossLineMoveMode() == CrossLineMoveMode.AfterClick) {
				value.argvalue = "AfterClick";
			} else {
				value.argvalue = "FollowMouse";
			}
		} else if (name.equals("hscalefieldtext")) {
			type.argvalue = "text";
			value.argvalue = GetHScaleFieldText();
		} else if (name.equals("hscalepixel")) {
			type.argvalue = "double";
			value.argvalue = CStrMe.ConvertDoubleToStr(GetHScalePixel());
		} else if (name.equals("leftvscalewidth")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetLeftVScaleWidth());
		} else if (name.equals("reversehscale")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(GetReverseHScale());
		} else if (name.equals("rightvscalewidth")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetRightVScaleWidth());
		} else if (name.equals("scrolladdspeed")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(ScrollAddSpeed());
		} else if (name.equals("showcrossline")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(GetShowCrossLine());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "AutoFillHScale", "BlankSpace", "CanMoveShape", "CanResizeH",
				"CanResizeV", "CanScroll", "CanZoom", "CrossLineMoveMode", "HScaleFieldText", "HScalePixel",
				"LeftVScaleWidth", "ReverseHScale", "RightVScaleWidth", "ScrollAddSpeed", "ShowCrossLine" }));
		return propertyNames;
	}

	public int GetShapesCount(int field) {
		int count = 0;
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe div : divsCopy) {
			ArrayList<CBaseShapeMe> shapesCopy = div.GetShapes(SortType.NONE);
			for (CBaseShapeMe bs : shapesCopy) {
				int[] fields = bs.GetFields();
				if (fields != null) {
					for (int i = 0; i < fields.length; i++) {
						if (fields[i] == field) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

	protected int GetVScaleBaseField(CDivMe div, CVScaleMe vScale) {
		int baseField = vScale.GetBaseField();
		if (baseField == CTableMe.NULLFIELD) {
			ArrayList<CBaseShapeMe> baseShapes = div.GetShapes(SortType.ASC);
			if (baseShapes.size() > 0) {
				baseField = ((CBaseShapeMe) baseShapes.get(0)).GetBaseField();
			}
		}
		return baseField;
	}

	public double GetVScaleBaseValue(CDivMe div, CVScaleMe vScale, int i) {
		double baseValue = 0.0D;
		int baseField = GetVScaleBaseField(div, vScale);
		if ((baseField != CTableMe.NULLFIELD) && (this.m_dataSource.GetRowsCount() > 0)) {
			if ((i >= this.m_firstVisibleIndex) && (i <= this.m_lastVisibleIndex)) {
				double value = this.m_dataSource.Get2(i, baseField);
				if (!Double.isNaN(value)) {
					baseValue = value;
				}

			}
		} else {
			baseValue = vScale.GetMidValue();
		}
		return baseValue;
	}

	public float GetX(int index) {
		float x = (float) (this.m_leftVScaleWidth + (index - this.m_firstVisibleIndex) * this.m_hScalePixel
				+ this.m_hScalePixel / 2.0D + 1.0D);
		if (this.m_reverseHScale) {
			return this.m_workingAreaWidth - (x - this.m_leftVScaleWidth) + this.m_leftVScaleWidth + this.m_blankSpace;
		}

		return x;
	}

	public float GetY(CDivMe div, double value, AttachVScale attach) {
		if (div != null) {
			CVScaleMe scale = div.GetVScale(attach);
			double max = scale.GetVisibleMax();
			double min = scale.GetVisibleMin();
			if (scale.GetSystem() == VScaleSystem.Logarithmic) {
				if (value > 0.0D) {
					value = Math.log10(value);
				} else if (value < 0.0D) {
					value = -Math.log10(Math.abs(value));
				}
				if (max > 0.0D) {
					max = Math.log10(max);
				} else if (max < 0.0D) {
					max = -Math.log10(Math.abs(max));
				}
				if (min > 0.0D) {
					min = Math.log10(scale.GetVisibleMin());
				} else if (min < 0.0D) {
					min = -Math.log10(Math.abs(min));
				}
			}
			if (max != min) {
				int wHeight = div.GetWorkingAreaHeight() - scale.GetPaddingTop() - scale.GetPaddingBottom();
				if (wHeight > 0) {
					float y = (float) ((max - value) / (max - min) * wHeight);
					if (scale.IsReverse()) {
						return div.GetTitleBar().GetHeight() + div.GetWorkingAreaHeight() - scale.GetPaddingBottom()
								- y;
					}

					return div.GetTitleBar().GetHeight() + scale.GetPaddingTop() + y;
				}
			}
		}

		return 0.0F;
	}

	protected void CloseSelectArea() {
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe div : divsCopy) {
			div.GetSelectArea().Close();
		}
		this.m_showingSelectArea = false;
	}

	public boolean IsOperating() {
		if ((this.m_movingPlot != null) || (this.m_movingShape != null) || (this.m_hResizeType != 0)
				|| (this.m_userResizeDiv != null)) {
			return true;
		}
		return false;
	}

	public void LocateCrossLine() {
		Iterator i$;
		CDivMe div;
		if (this.m_dataSource.GetRowsCount() > 0) {
			ArrayList<CDivMe> divsCopy = GetDivs();
			for (i$ = divsCopy.iterator(); i$.hasNext();) {
				div = (CDivMe) i$.next();

				if ((this.m_cross_y >= div.GetTop()) && (this.m_cross_y <= div.GetTop() + div.GetHeight())) {
					if ((div.GetWorkingAreaHeight() > 0) && (this.m_crossStopIndex >= 0)
							&& (this.m_crossStopIndex < this.m_dataSource.GetRowsCount())) {
						ArrayList<CBaseShapeMe> shapesCopy = div.GetShapes(SortType.DESC);
						for (CBaseShapeMe tls : shapesCopy) {
							if (tls.IsVisible()) {
								double value = this.m_dataSource.Get2(this.m_crossStopIndex, tls.GetBaseField());
								if (!Double.isNaN(value)) {
									this.m_cross_y = ((int) GetY(div, value, tls.GetAttachVScale()) + div.GetTop());
									return;
								}
							}
						}
					}
				}
			}
		}
	}

	public void MoveShape(CDivMe cDiv, CBaseShapeMe shape) {
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe div : divsCopy) {
			div.RemoveShape(shape);
		}
		if (cDiv != null) {
			cDiv.AddShape(shape);
		}
	}

	public void ResetCrossOverIndex() {
		if (this.m_showCrossLine) {
			this.m_crossStopIndex = ResetCrossOverIndex(this.m_dataSource.GetRowsCount(), this.m_maxVisibleRecord,
					this.m_crossStopIndex, this.m_firstVisibleIndex, this.m_lastVisibleIndex);
		}
		this.m_isScrollCross = true;
	}

	public void RemoveAll() {
		Clear();
		if (this.m_divs != null) {
			for (CDivMe div : this.m_divs) {
				div.Dispose();
			}
			this.m_divs.clear();
		}
		this.m_dataSource.Clear();
		this.m_cross_y = -1;
		this.m_showingToolTip = false;
	}

	public void RemoveDiv(CDivMe div) {
		this.m_divs.remove(div);
		Update();
	}

	public boolean ResizeDiv() {
		int width = GetWidth();
		int height = GetHeight();
		if (this.m_hResizeType > 0) {
			POINT mp = GetMousePoint();
			if (this.m_hResizeType == 1) {
				if ((mp.x > 0) && (mp.x < width - this.m_rightVScaleWidth - 50)) {
					this.m_leftVScaleWidth = mp.x;
				}
			} else if (this.m_hResizeType == 2) {
				if ((mp.x > this.m_leftVScaleWidth + 50) && (mp.x < width)) {
					this.m_rightVScaleWidth = (width - mp.x);
				}
			}
			this.m_hResizeType = 0;
			Update();
			return true;
		}
		if (this.m_userResizeDiv != null) {
			POINT mp = GetMousePoint();
			CDivMe nextCP = null;
			boolean rightP = false;
			ArrayList<CDivMe> divsCopy = GetDivs();
			for (CDivMe cDiv : divsCopy) {
				if (rightP) {
					nextCP = cDiv;
					break;
				}
				if (cDiv == this.m_userResizeDiv) {
					rightP = true;
				}
			}
			float sumPercent = 0.0F;
			for (CDivMe div : divsCopy) {
				sumPercent += div.GetVerticalPercent();
			}
			float originalVP = this.m_userResizeDiv.GetVerticalPercent();
			RECT uRect = this.m_userResizeDiv.GetBounds();
			if ((mp.x >= uRect.left) && (mp.x <= uRect.right) && (mp.y >= uRect.top) && (mp.y <= uRect.bottom)) {
				this.m_userResizeDiv
						.SetVerticalPercent(sumPercent * (mp.y - this.m_userResizeDiv.GetTop()) / GetHeight());
				if (this.m_userResizeDiv.GetVerticalPercent() < 1.0F) {
					this.m_userResizeDiv.SetVerticalPercent(1.0F);
				}
				if (nextCP != null) {
					nextCP.SetVerticalPercent(
							nextCP.GetVerticalPercent() + originalVP - this.m_userResizeDiv.GetVerticalPercent());
				}

			} else if (nextCP != null) {
				RECT nRect = nextCP.GetBounds();
				if ((mp.x >= nRect.left) && (mp.x <= nRect.right) && (mp.y >= nRect.top) && (mp.y <= nRect.bottom)) {
					this.m_userResizeDiv
							.SetVerticalPercent(sumPercent * (mp.y - this.m_userResizeDiv.GetTop()) / GetHeight());
					if (this.m_userResizeDiv.GetVerticalPercent() >= originalVP + nextCP.GetVerticalPercent()) {
						this.m_userResizeDiv.SetVerticalPercent(this.m_userResizeDiv.GetVerticalPercent() - 1.0F);
					}
					nextCP.SetVerticalPercent(
							originalVP + nextCP.GetVerticalPercent() - this.m_userResizeDiv.GetVerticalPercent());
				}
			}

			this.m_userResizeDiv = null;
			Update();
			return true;
		}
		return false;
	}

	public void Reset() {
		if (IsVisible()) {
			ResetVisibleRecord();
			Adjust();
			ResetCrossOverIndex();
		}
	}

	public void ResetVisibleRecord() {
		ArrayList<CDivMe> divs = GetDivs();
		if (divs.size() > 0) {
			int rowsCount = this.m_dataSource.GetRowsCount();
			if (this.m_autoFillHScale) {
				if ((this.m_workingAreaWidth > 0) && (rowsCount > 0)) {
					this.m_hScalePixel = (this.m_workingAreaWidth / rowsCount);
					this.m_maxVisibleRecord = rowsCount;
					this.m_firstVisibleIndex = 0;
					this.m_lastVisibleIndex = (rowsCount - 1);
				}
			} else {
				this.m_maxVisibleRecord = GetMaxVisibleCount(this.m_hScalePixel, this.m_workingAreaWidth);
				if (rowsCount == 0) {
					this.m_firstVisibleIndex = -1;
					this.m_lastVisibleIndex = -1;

				} else if (rowsCount < this.m_maxVisibleRecord) {
					this.m_lastVisibleIndex = (rowsCount - 1);
					this.m_firstVisibleIndex = 0;

				} else if ((this.m_firstVisibleIndex != -1) && (this.m_lastVisibleIndex != -1)
						&& (!this.m_lastRecordIsVisible)) {
					int index = this.m_dataSource.GetRowIndex(this.m_lastVisibleKey);
					if (index != -1) {
						this.m_lastVisibleIndex = index;
					}
					this.m_firstVisibleIndex = (this.m_lastVisibleIndex - this.m_maxVisibleRecord + 1);
					if (this.m_firstVisibleIndex < 0) {
						this.m_firstVisibleIndex = 0;
						this.m_lastVisibleIndex = (this.m_firstVisibleIndex + this.m_maxVisibleRecord);
						CheckLastVisibleIndex();
					}
				} else {
					this.m_lastVisibleIndex = (rowsCount - 1);
					this.m_firstVisibleIndex = (this.m_lastVisibleIndex - this.m_maxVisibleRecord + 1);
					if (this.m_firstVisibleIndex > this.m_lastVisibleIndex) {
						this.m_firstVisibleIndex = this.m_lastVisibleIndex;
					}
				}
			}
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("autofillhscale")) {
			SetAutoFillHScale(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("blankspace")) {
			SetBlankSpace(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("canmoveshape")) {
			SetCanMoveShape(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("canresizeh")) {
			SetCanResizeH(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("canresizev")) {
			SetCanResizeV(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("canscroll")) {
			SetCanScroll(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("canzoom")) {
			SetCanZoom(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("crosslinemovemode")) {
			if (value.equals("AfterClick")) {
				SetCrossLineMoveMode(CrossLineMoveMode.AfterClick);
			} else {
				SetCrossLineMoveMode(CrossLineMoveMode.FollowMouse);
			}
		} else if (name.equals("hscalefieldtext")) {
			SetHScaleFieldText(value);
		} else if (name.equals("hscalepixel")) {
			SetHScalePixel(CStrMe.ConvertStrToDouble(value));
		} else if (name.equals("leftvscalewidth")) {
			SetLeftVScaleWidth(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("reversehscale")) {
			SetReverseHScale(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("rightvscalewidth")) {
			SetRightVScaleWidth(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("scrolladdspeed")) {
			SetScrollAddSpeed(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("showcrossline")) {
			SetShowCrossLine(CStrMe.ConvertStrToBool(value));
		} else {
			super.SetProperty(name, value);
		}
	}

	protected void ScrollCrossLineLeft(int step) {
		int currentIndex = this.m_crossStopIndex;
		this.m_crossStopIndex = (currentIndex - step);
		if (this.m_crossStopIndex < 0) {
			this.m_crossStopIndex = 0;
		}
		if (currentIndex <= this.m_firstVisibleIndex) {
			ScrollLeft(step);
		}
	}

	public void ScrollCrossLineRight(int step) {
		int currentIndex = this.m_crossStopIndex;
		this.m_crossStopIndex = (currentIndex + step);
		if (this.m_dataSource.GetRowsCount() < this.m_maxVisibleRecord) {
			if (this.m_crossStopIndex >= this.m_maxVisibleRecord - 1) {
				this.m_crossStopIndex = (this.m_maxVisibleRecord - 1);
			}
		}
		if (currentIndex >= this.m_lastVisibleIndex) {
			ScrollRight(step);
		}
	}

	public void ScrollLeft(int step) {
		if (!this.m_autoFillHScale) {
			RefObject<Integer> tempRef_m_firstVisibleIndex = new RefObject(Integer.valueOf(this.m_firstVisibleIndex));
			RefObject<Integer> tempRef_m_lastVisibleIndex = new RefObject(Integer.valueOf(this.m_lastVisibleIndex));
			ScrollLeft(step, this.m_dataSource.GetRowsCount(), this.m_hScalePixel, this.m_workingAreaWidth,
					tempRef_m_firstVisibleIndex, tempRef_m_lastVisibleIndex);
			this.m_firstVisibleIndex = ((Integer) tempRef_m_firstVisibleIndex.argvalue).intValue();
			this.m_lastVisibleIndex = ((Integer) tempRef_m_lastVisibleIndex.argvalue).intValue();
			CheckLastVisibleIndex();
		}
	}

	public void ScrollLeftToBegin() {
		if ((!this.m_autoFillHScale) && (this.m_dataSource.GetRowsCount() > 0)) {
			this.m_firstVisibleIndex = 0;
			this.m_lastVisibleIndex = (this.m_maxVisibleRecord - 1);
			CheckLastVisibleIndex();
			this.m_crossStopIndex = this.m_firstVisibleIndex;
		}
	}

	public void ScrollRight(int step) {
		if (!this.m_autoFillHScale) {
			RefObject<Integer> tempRef_m_firstVisibleIndex = new RefObject(Integer.valueOf(this.m_firstVisibleIndex));
			RefObject<Integer> tempRef_m_lastVisibleIndex = new RefObject(Integer.valueOf(this.m_lastVisibleIndex));
			ScrollRight(step, this.m_dataSource.GetRowsCount(), this.m_hScalePixel, this.m_workingAreaWidth,
					tempRef_m_firstVisibleIndex, tempRef_m_lastVisibleIndex);
			this.m_firstVisibleIndex = ((Integer) tempRef_m_firstVisibleIndex.argvalue).intValue();
			this.m_lastVisibleIndex = ((Integer) tempRef_m_lastVisibleIndex.argvalue).intValue();
			CheckLastVisibleIndex();
		}
	}

	public void ScrollRightToEnd() {
		if ((!this.m_autoFillHScale) && (this.m_dataSource.GetRowsCount() > 0)) {
			this.m_lastVisibleIndex = (this.m_dataSource.GetRowsCount() - 1);
			CheckLastVisibleIndex();
			this.m_firstVisibleIndex = (this.m_lastVisibleIndex - this.m_maxVisibleRecord + 1);
			if (this.m_firstVisibleIndex < 0) {
				this.m_firstVisibleIndex = 0;
			}
			this.m_crossStopIndex = this.m_lastVisibleIndex;
		}
	}

	public boolean SelectBar(CDivMe div, float mpY, int fieldName, int fieldName2, int styleField,
			AttachVScale attachVScale, int curIndex) {
		int style = 1;
		if (styleField != CTableMe.NULLFIELD) {
			double defineStyle = this.m_dataSource.Get2(curIndex, styleField);
			if (!Double.isNaN(defineStyle)) {
				style = (int) defineStyle;
			}
		}
		if ((style == 55536) || (curIndex < 0) || (curIndex > this.m_lastVisibleIndex)
				|| (Double.isNaN(this.m_dataSource.Get2(curIndex, fieldName)))) {
			return false;
		}
		double midValue = 0.0D;
		if (fieldName2 != CTableMe.NULLFIELD) {
			midValue = this.m_dataSource.Get2(curIndex, fieldName2);
		}
		double volumn = this.m_dataSource.Get2(curIndex, fieldName);
		float y = GetY(div, volumn, attachVScale);
		float midY = GetY(div, midValue, attachVScale);
		float topY = y + div.GetTop();
		float bottomY = midY + div.GetTop();
		if (topY > bottomY) {
			topY = midY + div.GetTop();
			bottomY = y + div.GetTop();
		}
		topY -= 1.0F;
		bottomY += 1.0F;
		if ((topY >= div.GetTop()) && (bottomY <= div.GetTop() + div.GetHeight()) && (mpY >= topY)
				&& (mpY <= bottomY)) {
			return true;
		}
		return false;
	}

	public boolean SelectCandle(CDivMe div, float mpY, int highField, int lowField, int styleField,
			AttachVScale attachVScale, int curIndex) {
		int style = 1;
		if (styleField != CTableMe.NULLFIELD) {
			double defineStyle = this.m_dataSource.Get2(curIndex, styleField);
			if (!Double.isNaN(defineStyle)) {
				style = (int) defineStyle;
			}
		}
		double highValue = 0.0D;
		double lowValue = 0.0D;
		if ((style == 55536) || (curIndex < 0) || (curIndex > this.m_lastVisibleIndex)) {
			return false;
		}

		highValue = this.m_dataSource.Get2(curIndex, highField);
		lowValue = this.m_dataSource.Get2(curIndex, lowField);
		if ((Double.isNaN(highValue)) || (Double.isNaN(lowValue))) {
			return false;
		}

		float highY = GetY(div, highValue, attachVScale);
		float lowY = GetY(div, lowValue, attachVScale);
		float topY = highY + div.GetTop();
		float bottomY = lowY + div.GetTop();
		if (topY > bottomY) {
			float temp = topY;
			topY = bottomY;
			bottomY = temp;
		}
		topY -= 1.0F;
		bottomY += 1.0F;
		if ((topY >= div.GetTop()) && (bottomY <= div.GetTop() + div.GetHeight()) && (mpY >= topY)
				&& (mpY <= bottomY)) {
			return true;
		}
		return false;
	}

	public boolean SelectPolyline(CDivMe div, POINT mp, int fieldName, float lineWidth, AttachVScale attachVScale,
			int curIndex) {
		if ((curIndex < 0) || (curIndex > this.m_lastVisibleIndex)
				|| (Double.isNaN(this.m_dataSource.Get2(curIndex, fieldName)))) {
			return false;
		}
		double lineValue = this.m_dataSource.Get2(curIndex, fieldName);
		float topY = GetY(div, lineValue, attachVScale) + div.GetTop();
		if (this.m_hScalePixel <= 1.0D) {
			if ((topY >= div.GetTop()) && (topY <= div.GetTop() + div.GetHeight()) && (mp.y >= topY - 8.0F)
					&& (mp.y <= topY + 8.0F)) {
				return true;
			}
		} else {
			int index = curIndex;
			int scaleX = (int) GetX(index);
			float judgeTop = 0.0F;
			float judgeScaleX = scaleX;
			if (mp.x >= scaleX) {
				int leftIndex = curIndex + 1;
				if ((curIndex < this.m_lastVisibleIndex)
						&& (!Double.isNaN(this.m_dataSource.Get2(leftIndex, fieldName)))) {
					double rightValue = this.m_dataSource.Get2(leftIndex, fieldName);
					judgeTop = GetY(div, rightValue, attachVScale) + div.GetTop();
					if ((judgeTop > div.GetTop() + div.GetHeight() - div.GetHScale().GetHeight())
							|| (judgeTop < div.GetTop() + div.GetTitleBar().GetHeight())) {
						return false;
					}
				} else {
					judgeTop = topY;
				}
			} else {
				judgeScaleX = scaleX - (int) this.m_hScalePixel;
				int rightIndex = curIndex - 1;
				if ((curIndex > 0) && (!Double.isNaN(this.m_dataSource.Get2(rightIndex, fieldName)))) {
					double leftValue = this.m_dataSource.Get2(rightIndex, fieldName);
					judgeTop = GetY(div, leftValue, attachVScale) + div.GetTop();
					if ((judgeTop > div.GetTop() + div.GetHeight() - div.GetHScale().GetHeight())
							|| (judgeTop < div.GetTop() + div.GetTitleBar().GetHeight())) {
						return false;
					}
				} else {
					judgeTop = topY;
				}
			}
			float judgeX = 0.0F;
			float judgeY = 0.0F;
			float judgeW = 0.0F;
			float judgeH = 0.0F;
			if (judgeTop >= topY) {
				judgeX = judgeScaleX;
				judgeY = topY - 2.0F - lineWidth;
				judgeW = (float) this.m_hScalePixel;
				judgeH = judgeTop - topY + lineWidth < 4.0F ? 4.0F : judgeTop - topY + 4.0F + lineWidth;
			} else {
				judgeX = judgeScaleX;
				judgeY = judgeTop - 2.0F - lineWidth / 2.0F;
				judgeW = (float) this.m_hScalePixel;
				judgeH = topY - judgeTop + lineWidth < 4.0F ? 4.0F : topY - judgeTop + 4.0F + lineWidth;
			}
			if ((mp.x >= judgeX) && (mp.x <= judgeX + judgeW) && (mp.y >= judgeY) && (mp.y <= judgeY + judgeH)) {
				return true;
			}
		}
		return false;
	}

	public CBaseShapeMe SelectShape(int curIndex, int state) {
		CBaseShapeMe selectObj = null;
		POINT mp = GetMousePoint();
		ArrayList<CDivMe> divsCopy = GetDivs();
		CDivMe div;
		for (Iterator i$ = divsCopy.iterator(); i$.hasNext();) {
			div = (CDivMe) i$.next();

			ArrayList<CBaseShapeMe> sortedBs = div.GetShapes(SortType.DESC);
			for (CBaseShapeMe bShape : sortedBs) {
				if (bShape.IsVisible()) {
					if (selectObj != null) {
						if (state == 1) {
							bShape.SetSelected(false);
						}

					} else if ((this.m_firstVisibleIndex == -1) || (this.m_lastVisibleIndex == -1)) {
						if (state == 1) {
							bShape.SetSelected(false);
						}
					} else {
						boolean isSelect = false;
						if ((bShape instanceof CPolylineShapeMe)) {
							CPolylineShapeMe tls = (CPolylineShapeMe) ((bShape instanceof CPolylineShapeMe) ? bShape
									: null);
							isSelect = SelectPolyline(div, mp, tls.GetBaseField(), tls.GetWidth(),
									tls.GetAttachVScale(), curIndex);
						} else if ((bShape instanceof CCandleShapeMe)) {
							CCandleShapeMe cs = (CCandleShapeMe) ((bShape instanceof CCandleShapeMe) ? bShape : null);
							if (cs.GetStyle() == CandleStyle.CloseLine) {
								isSelect = SelectPolyline(div, mp, cs.GetCloseField(), 1.0F, cs.GetAttachVScale(),
										curIndex);
							} else {
								isSelect = SelectCandle(div, mp.y, cs.GetHighField(), cs.GetLowField(),
										cs.GetStyleField(), cs.GetAttachVScale(), curIndex);
							}
						} else if ((bShape instanceof CBarShapeMe)) {
							CBarShapeMe barS = (CBarShapeMe) ((bShape instanceof CBarShapeMe) ? bShape : null);
							isSelect = SelectBar(div, mp.y, barS.GetFieldName(), barS.GetFieldName2(),
									barS.GetStyleField(), barS.GetAttachVScale(), curIndex);
						}
						if (isSelect) {
							selectObj = bShape;
							if (state == 1) {
								bShape.SetSelected(true);
							}

						} else if (state == 1) {
							bShape.SetSelected(false);
						}
					}
				}
			}
		}
		return selectObj;
	}

	public void SetVisibleIndex(int firstVisibleIndex, int lastVisibleIndex) {
		double xScalePixel = this.m_workingAreaWidth / (lastVisibleIndex - firstVisibleIndex + 1);
		if (xScalePixel < 1000000.0D) {
			this.m_firstVisibleIndex = firstVisibleIndex;
			this.m_lastVisibleIndex = lastVisibleIndex;
			if (lastVisibleIndex != this.m_dataSource.GetRowsCount() - 1) {
				this.m_lastRecordIsVisible = false;
			} else {
				this.m_lastRecordIsVisible = true;
			}
			SetHScalePixel(xScalePixel);
			this.m_maxVisibleRecord = GetMaxVisibleCount(this.m_hScalePixel, this.m_workingAreaWidth);
			CheckLastVisibleIndex();
		}
	}

	public void Update() {
		super.Update();
		this.m_workingAreaWidth = (GetWidth() - this.m_leftVScaleWidth - this.m_rightVScaleWidth - this.m_blankSpace
				- 1);
		if (this.m_workingAreaWidth < 0) {
			this.m_workingAreaWidth = 0;
		}
		int locationY = 0;
		float sumPercent = 0.0F;
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe div : divsCopy) {
			sumPercent += div.GetVerticalPercent();
		}
		if (sumPercent > 0.0F) {
			for (CDivMe cDiv : divsCopy) {
				cDiv.SetBounds(new RECT(0, locationY, GetWidth(),
						locationY + (int) (GetHeight() * cDiv.GetVerticalPercent() / sumPercent)));
				cDiv.SetWorkingAreaHeight(
						cDiv.GetHeight() - cDiv.GetHScale().GetHeight() - cDiv.GetTitleBar().GetHeight() - 1);
				locationY += (int) (GetHeight() * cDiv.GetVerticalPercent() / sumPercent);
			}
		}
		Reset();
	}

	public void ZoomOut() {
		if (!this.m_autoFillHScale) {
			double hp = this.m_hScalePixel;
			RefObject<Integer> tempRef_m_firstVisibleIndex = new RefObject(Integer.valueOf(this.m_firstVisibleIndex));
			RefObject<Integer> tempRef_m_lastVisibleIndex = new RefObject(Integer.valueOf(this.m_lastVisibleIndex));
			RefObject<Double> tempRef_hp = new RefObject(Double.valueOf(hp));
			ZoomOut(this.m_workingAreaWidth, this.m_dataSource.GetRowsCount(), tempRef_m_firstVisibleIndex,
					tempRef_m_lastVisibleIndex, tempRef_hp);
			this.m_firstVisibleIndex = ((Integer) tempRef_m_firstVisibleIndex.argvalue).intValue();
			this.m_lastVisibleIndex = ((Integer) tempRef_m_lastVisibleIndex.argvalue).intValue();
			hp = ((Double) tempRef_hp.argvalue).doubleValue();
			SetHScalePixel(hp);
			this.m_maxVisibleRecord = GetMaxVisibleCount(this.m_hScalePixel, this.m_workingAreaWidth);
			CheckLastVisibleIndex();
		}
	}

	public void ZoomIn() {
		if (!this.m_autoFillHScale) {
			double hp = this.m_hScalePixel;
			RefObject<Integer> tempRef_m_firstVisibleIndex = new RefObject(Integer.valueOf(this.m_firstVisibleIndex));
			RefObject<Integer> tempRef_m_lastVisibleIndex = new RefObject(Integer.valueOf(this.m_lastVisibleIndex));
			RefObject<Double> tempRef_hp = new RefObject(Double.valueOf(hp));
			ZoomIn(this.m_workingAreaWidth, this.m_dataSource.GetRowsCount(), tempRef_m_firstVisibleIndex,
					tempRef_m_lastVisibleIndex, tempRef_hp);
			this.m_firstVisibleIndex = ((Integer) tempRef_m_firstVisibleIndex.argvalue).intValue();
			this.m_lastVisibleIndex = ((Integer) tempRef_m_lastVisibleIndex.argvalue).intValue();
			hp = ((Double) tempRef_hp.argvalue).doubleValue();
			SetHScalePixel(hp);
			this.m_maxVisibleRecord = GetMaxVisibleCount(this.m_hScalePixel, this.m_workingAreaWidth);
			CheckLastVisibleIndex();
		}
	}

	public void DrawText(CPaintMe paint, String text, long dwPenColor, FONT font, POINT point) {
		SIZE tSize = paint.TextSize(text, font);
		RECT tRect = new RECT(point.x, point.y, point.x + tSize.cx, point.y + tSize.cy);
		paint.DrawText(text, dwPenColor, font, tRect);
	}

	public void DrawThinLine(CPaintMe paint, long color, int width, float x1, float y1, float x2, float y2) {
		CControlHostMe host = GetNative().GetHost();
		if ((width > 1) || (GetNative().AllowScaleSize())) {
			paint.DrawLine(color, width, 0, (int) x1, (int) y1, (int) x2, (int) y2);
		} else {
			int x = (int) (x1 < x2 ? x1 : x2);
			int y = (int) (y1 < y2 ? y1 : y2);
			int w = (int) Math.abs(x1 - x2);
			int h = (int) Math.abs(y1 - y2);
			if (w < 1) {
				w = 1;
			}
			if (h < 1) {
				h = 1;
			}
			if (((w > 1) && (h == 1)) || ((w == 1) && (h > 1))) {
				paint.FillRect(color, x, y, x + w, y + h);
			} else {
				paint.DrawLine(color, width, 0, (int) x1, (int) y1, (int) x2, (int) y2);
			}
		}
	}

	public ArrayList<Double> GetVScaleStep(double max, double min, CDivMe div, CVScaleMe vScale) {
		ArrayList<Double> scaleStepList = new ArrayList();
		if ((vScale.GetType() == VScaleType.EqualDiff) || (vScale.GetType() == VScaleType.Percent)) {
			double step = 0.0D;
			int distance = div.GetVGrid().GetDistance();
			int digit = 0;
			int gN = div.GetWorkingAreaHeight() / distance;
			if (gN == 0) {
				gN = 1;
			}
			RefObject<Double> tempRef_step = new RefObject(Double.valueOf(step));
			RefObject<Integer> tempRef_digit = new RefObject(Integer.valueOf(digit));
			CMathLibMe.M012(min, max, div.GetWorkingAreaHeight(), distance, distance / 2, gN, tempRef_step,
					tempRef_digit);
			step = ((Double) tempRef_step.argvalue).doubleValue();
			digit = ((Integer) tempRef_digit.argvalue).intValue();
			if (step > 0.0D) {
				double start = 0.0D;
				if (min >= 0.0D) {
					while (start + step < min) {
						start += step;
					}
				}

				while (start - step > min) {
					start -= step;
				}

				while (start <= max) {
					scaleStepList.add(Double.valueOf(start));
					start += step;
				}
			}
		} else if (vScale.GetType() == VScaleType.EqualRatio) {
			int baseField = GetVScaleBaseField(div, vScale);
			double bMax = Double.MIN_VALUE;
			double bMin = Double.MAX_VALUE;
			if (baseField != -1) {
				for (int i = 0; i < this.m_dataSource.GetRowsCount(); i++) {
					double value = this.m_dataSource.Get2(i, baseField);
					if (!Double.isNaN(value)) {
						if (value > bMax) {
							bMax = value;
						}
						if (value < bMin) {
							bMin = value;
						}
					}
				}
				if ((bMax != Double.MIN_VALUE) && (bMin != Double.MAX_VALUE) && (bMin > 0.0D) && (bMax > 0.0D)
						&& (bMin < bMax)) {
					double num = bMin;
					while (num < bMax) {
						num *= 1.1D;
						if ((num >= min) && (num <= max)) {
							scaleStepList.add(Double.valueOf(num));
						}
					}
				}
			}
		} else if (vScale.GetType() == VScaleType.Divide) {
			scaleStepList.add(Double.valueOf(min + (max - min) * 0.25D));
			scaleStepList.add(Double.valueOf(min + (max - min) * 0.5D));
			scaleStepList.add(Double.valueOf(min + (max - min) * 0.75D));
		} else if (vScale.GetType() == VScaleType.GoldenRatio) {
			scaleStepList.add(Double.valueOf(min));
			scaleStepList.add(Double.valueOf(min + (max - min) * 0.191D));
			scaleStepList.add(Double.valueOf(min + (max - min) * 0.382D));
			scaleStepList.add(Double.valueOf(min + (max - min) * 0.5D));
			scaleStepList.add(Double.valueOf(min + (max - min) * 0.618D));
			scaleStepList.add(Double.valueOf(min + (max - min) * 0.809D));
		}
		if ((max != min) && (scaleStepList.isEmpty())) {
			if (!Double.isNaN(min)) {
				scaleStepList.add(Double.valueOf(min));
			}
		}
		return scaleStepList;
	}

	public void OnPaintBar(CPaintMe paint, CDivMe div, CBarShapeMe bs) {
		int ciFieldName1 = this.m_dataSource.GetColumnIndex(bs.GetFieldName());
		int ciFieldName2 = this.m_dataSource.GetColumnIndex(bs.GetFieldName2());
		int ciStyle = this.m_dataSource.GetColumnIndex(bs.GetStyleField());
		int ciClr = this.m_dataSource.GetColumnIndex(bs.GetColorField());
		int defaultLineWidth = 1;
		if ((!IsOperating()) && (this.m_crossStopIndex != -1)) {
			if (SelectBar(div, GetMousePoint().y, bs.GetFieldName(), bs.GetFieldName2(), bs.GetStyleField(),
					bs.GetAttachVScale(), this.m_crossStopIndex)) {
				defaultLineWidth = 2;
			}
		}
		for (int i = this.m_firstVisibleIndex; i <= this.m_lastVisibleIndex; i++) {
			int thinLineWidth = 1;
			if (i == this.m_crossStopIndex) {
				thinLineWidth = defaultLineWidth;
			}
			int style = 55536;
			switch (bs.GetStyle()) {
			case Line:
				style = 2;
				break;
			case Rect:
				style = 0;
			}

			if (ciStyle != -1) {
				double defineStyle = this.m_dataSource.Get3(i, ciStyle);
				if (!Double.isNaN(defineStyle)) {
					style = (int) defineStyle;
				}
			}
			if (style != 55536) {

				double value = this.m_dataSource.Get3(i, ciFieldName1);
				int scaleX = (int) GetX(i);
				double midValue = 0.0D;
				if (ciFieldName2 != -1) {
					midValue = this.m_dataSource.Get3(i, ciFieldName2);
				}
				float midY = GetY(div, midValue, bs.GetAttachVScale());
				if (!Double.isNaN(value)) {
					float barY = GetY(div, value, bs.GetAttachVScale());
					int startPX = scaleX;
					int startPY = (int) midY;
					int endPX = scaleX;
					int endPY = (int) barY;
					if (bs.GetStyle() == BarStyle.Rect) {
						if (startPY == div.GetHeight() - div.GetHScale().GetHeight()) {
							startPY = div.GetHeight() - div.GetHScale().GetHeight() + 1;
						}
					}
					int x = 0;
					int y = 0;
					int width = 0;
					int height = 0;
					width = (int) (this.m_hScalePixel * 2.0D / 3.0D);
					if (width % 2 == 0) {
						width++;
					}
					if (width < 3) {
						width = 1;
					}
					x = scaleX - width / 2;
					if (startPY >= endPY) {
						y = endPY;
					} else {
						y = startPY;
					}
					height = Math.abs(startPY - endPY);
					if (height < 1) {
						height = 1;
					}
					long barColor = COLOR.EMPTY;
					if (ciClr != -1) {
						double defineColor = this.m_dataSource.Get3(i, ciClr);
						if (!Double.isNaN(defineColor)) {
							barColor = (long) defineColor;
						}
					}
					if (barColor == COLOR.EMPTY) {
						if (startPY >= endPY) {
							barColor = bs.GetUpColor();
						} else {
							barColor = bs.GetDownColor();
						}
					}
					switch (style) {
					case -1:
						if (this.m_hScalePixel <= 3.0D) {
							DrawThinLine(paint, barColor, thinLineWidth, startPX, y, startPX, y + height);
						} else {
							RECT rect = new RECT(x, y, x + width, y + height);
							paint.FillRect(div.GetBackColor(), rect);
							paint.DrawRect(barColor, thinLineWidth, 2, rect);
						}
						break;
					case 0:
						if (this.m_hScalePixel <= 3.0D) {
							DrawThinLine(paint, barColor, thinLineWidth, startPX, y, startPX, y + height);
						} else {
							RECT rect = new RECT(x, y, x + width, y + height);
							paint.FillRect(barColor, rect);
							if (thinLineWidth > 1) {
								if (startPY >= endPY) {
									paint.DrawRect(bs.GetDownColor(), thinLineWidth, 0, rect);
								} else {
									paint.DrawRect(bs.GetUpColor(), thinLineWidth, 0, rect);
								}
							}
						}
						break;
					case 1:
						if (this.m_hScalePixel <= 3.0D) {
							DrawThinLine(paint, barColor, thinLineWidth, startPX, y, startPX, y + height);
						} else {
							RECT rect = new RECT(x, y, x + width, y + height);
							paint.FillRect(div.GetBackColor(), rect);
							paint.DrawRect(barColor, thinLineWidth, 0, rect);
						}
						break;
					case 2:
						if (startPY <= 0) {
							startPY = 0;
						}
						if (startPY >= div.GetHeight()) {
							startPY = div.GetHeight();
						}
						if (endPY <= 0) {
							endPY = 0;
						}
						if (endPY >= div.GetHeight()) {
							endPY = div.GetHeight();
						}
						if (bs.GetLineWidth() <= 1.0F) {
							DrawThinLine(paint, barColor, thinLineWidth, startPX, startPY, endPX, endPY);
						} else {
							float lineWidth = bs.GetLineWidth();
							if (lineWidth > this.m_hScalePixel) {
								lineWidth = (float) this.m_hScalePixel;
							}
							if (lineWidth < 1.0F) {
								lineWidth = 1.0F;
							}
							paint.DrawLine(barColor, lineWidth + thinLineWidth - 1.0F, 0, startPX, startPY, endPX,
									endPY);
						}
						break;
					}
					if (bs.IsSelected()) {
						int kPInterval = this.m_maxVisibleRecord / 30;
						if (kPInterval < 2) {
							kPInterval = 2;
						}
						if (i % kPInterval == 0) {
							if ((barY >= div.GetTitleBar().GetHeight())
									&& (barY <= div.GetHeight() - div.GetHScale().GetHeight())) {
								RECT sRect = new RECT(scaleX - 2, (int) barY - 3, scaleX + 3, (int) barY + 2);
								paint.FillRect(bs.GetSelectedColor(), sRect);
							}
						}
					}
				}
				if ((i == this.m_lastVisibleIndex) && (div.GetVScale(bs.GetAttachVScale()).GetVisibleMin() < 0.0D)) {
					if (this.m_reverseHScale) {
						float left = (float) (this.m_leftVScaleWidth + this.m_workingAreaWidth
								- (this.m_lastVisibleIndex - this.m_firstVisibleIndex + 1) * this.m_hScalePixel);
						paint.DrawLine(bs.GetDownColor(), 1.0F, 0, this.m_leftVScaleWidth + this.m_workingAreaWidth,
								(int) midY, (int) left, (int) midY);
					} else {
						float right = (float) (this.m_leftVScaleWidth
								+ (this.m_lastVisibleIndex - this.m_firstVisibleIndex + 1) * this.m_hScalePixel);
						paint.DrawLine(bs.GetDownColor(), 1.0F, 0, this.m_leftVScaleWidth, (int) midY, (int) right,
								(int) midY);
					}
				}
			}
		}
	}

	public void OnPaintCandle(CPaintMe paint, CDivMe div, CCandleShapeMe cs) {
		int visibleMaxIndex = -1;
		int visibleMinIndex = -1;
		double visibleMax = 0.0D;
		double visibleMin = 0.0D;
		int x = 0;
		int y = 0;
		ArrayList<POINT> points = new ArrayList();
		int ciHigh = this.m_dataSource.GetColumnIndex(cs.GetHighField());
		int ciLow = this.m_dataSource.GetColumnIndex(cs.GetLowField());
		int ciOpen = this.m_dataSource.GetColumnIndex(cs.GetOpenField());
		int ciClose = this.m_dataSource.GetColumnIndex(cs.GetCloseField());
		int ciStyle = this.m_dataSource.GetColumnIndex(cs.GetStyleField());
		int ciClr = this.m_dataSource.GetColumnIndex(cs.GetColorField());
		int defaultLineWidth = 1;
		if ((!IsOperating()) && (this.m_crossStopIndex != -1)) {
			if (SelectCandle(div, GetMousePoint().y, cs.GetHighField(), cs.GetLowField(), cs.GetStyleField(),
					cs.GetAttachVScale(), this.m_crossStopIndex)) {
				defaultLineWidth = 2;
			}
		}
		for (int i = this.m_firstVisibleIndex; i <= this.m_lastVisibleIndex; i++) {
			int thinLineWidth = 1;
			if (i == this.m_crossStopIndex) {
				thinLineWidth = defaultLineWidth;
			}
			int style = 55536;
			switch (cs.GetStyle()) {
			case Rect:
				style = 0;
				break;
			case American:
				style = 3;
				break;
			case CloseLine:
				style = 4;
				break;
			case Tower:
				style = 5;
			}

			if (ciStyle != -1) {
				double defineStyle = this.m_dataSource.Get3(i, ciStyle);
				if (!Double.isNaN(defineStyle)) {
					style = (int) defineStyle;
				}
			}
			if (style != 10000) {

				double open = this.m_dataSource.Get3(i, ciOpen);
				double high = this.m_dataSource.Get3(i, ciHigh);
				double low = this.m_dataSource.Get3(i, ciLow);
				double close = this.m_dataSource.Get3(i, ciClose);
				if (((!Double.isNaN(open)) && (!Double.isNaN(high)) && (!Double.isNaN(low)) && (!Double.isNaN(close)))
						|| (

						(i == this.m_lastVisibleIndex) && (style == 4))) {

					int scaleX = (int) GetX(i);
					if (cs.ShowMaxMin()) {
						if (i == this.m_firstVisibleIndex) {
							visibleMaxIndex = i;
							visibleMinIndex = i;
							visibleMax = high;
							visibleMin = low;
						} else {
							if (high > visibleMax) {
								visibleMax = high;
								visibleMaxIndex = i;
							}
							if (low < visibleMin) {
								visibleMin = low;
								visibleMinIndex = i;
							}
						}
					}
					float highY = GetY(div, high, cs.GetAttachVScale());
					float openY = GetY(div, open, cs.GetAttachVScale());
					float lowY = GetY(div, low, cs.GetAttachVScale());
					float closeY = GetY(div, close, cs.GetAttachVScale());
					int cwidth = (int) (this.m_hScalePixel * 2.0D / 3.0D);
					if (cwidth % 2 == 0) {
						cwidth++;
					}
					if (cwidth < 3) {
						cwidth = 1;
					}
					int xsub = cwidth / 2;
					if (xsub < 1) {
						xsub = 1;
					}
					switch (style) {

					case 3:
						long color = cs.GetUpColor();
						if (open > close) {
							color = cs.GetDownColor();
						}
						if (ciClr != -1) {
							double defineColor = this.m_dataSource.Get3(i, ciClr);
							if (!Double.isNaN(defineColor)) {
								color = (long) defineColor;
							}
						}
						if ((int) highY != (int) lowY) {
							if (this.m_hScalePixel <= 3.0D) {
								DrawThinLine(paint, color, thinLineWidth, scaleX, highY, scaleX, lowY);
							} else {
								DrawThinLine(paint, color, thinLineWidth, scaleX, highY, scaleX, lowY);
								DrawThinLine(paint, color, thinLineWidth, scaleX - xsub, openY, scaleX, openY);
								DrawThinLine(paint, color, thinLineWidth, scaleX, closeY, scaleX + xsub, closeY);
							}

						} else {
							DrawThinLine(paint, color, thinLineWidth, scaleX - xsub, closeY, scaleX + xsub, closeY);
						}

						break;

					case 4:
						RefObject<Integer> tempRef_x = new RefObject(Integer.valueOf(x));
						RefObject<Integer> tempRef_y = new RefObject(Integer.valueOf(y));
						OnPaintPolyline(paint, div, cs.GetUpColor(), COLOR.EMPTY, cs.GetColorField(), defaultLineWidth,
								PolylineStyle.SolidLine, close, cs.GetAttachVScale(), scaleX, (int) closeY, i, points,
								tempRef_x, tempRef_y);
						x = ((Integer) tempRef_x.argvalue).intValue();
						y = ((Integer) tempRef_y.argvalue).intValue();
						break;

					default:
						if (open <= close) {
							float recth = GetUpCandleHeight(close, open,
									div.GetVScale(cs.GetAttachVScale()).GetVisibleMax(),
									div.GetVScale(cs.GetAttachVScale()).GetVisibleMin(),
									div.GetWorkingAreaHeight() - div.GetVScale(cs.GetAttachVScale()).GetPaddingBottom()
											- div.GetVScale(cs.GetAttachVScale()).GetPaddingTop());
							if (recth < 1.0F) {
								recth = 1.0F;
							}
							int rcUpX = scaleX - xsub;
							int rcUpTop = (int) closeY;
							int rcUpBottom = (int) openY;
							int rcUpW = cwidth;
							int rcUpH = (int) recth;
							if (openY < closeY) {
								rcUpTop = (int) openY;
								rcUpBottom = (int) closeY;
							}
							long upColor = COLOR.EMPTY;
							int colorField = cs.GetColorField();
							if (colorField != CTableMe.NULLFIELD) {
								double defineColor = this.m_dataSource.Get2(i, colorField);
								if (!Double.isNaN(defineColor)) {
									upColor = (long) defineColor;
								}
							}
							if (upColor == COLOR.EMPTY) {
								upColor = cs.GetUpColor();
							}
							switch (style) {
							case 0:
							case 1:
							case 2:
								if ((int) highY != (int) lowY) {
									DrawThinLine(paint, upColor, thinLineWidth, scaleX, highY, scaleX, lowY);
									if (this.m_hScalePixel > 3.0D) {
										if ((int) openY == (int) closeY) {
											DrawThinLine(paint, upColor, thinLineWidth, rcUpX, rcUpBottom,
													rcUpX + rcUpW, rcUpBottom);
										} else {
											RECT rcUp = new RECT(rcUpX, rcUpTop, rcUpX + rcUpW, rcUpBottom);
											if ((style == 0) || (style == 1)) {
												if ((rcUpW > 0) && (rcUpH > 0) && (this.m_hScalePixel > 3.0D)) {
													paint.FillRect(div.GetBackColor(), rcUp);
												}
												paint.DrawRect(upColor, thinLineWidth, 0, rcUp);
											} else if (style == 2) {
												paint.FillRect(upColor, rcUp);
												if (thinLineWidth > 1) {
													paint.DrawRect(upColor, thinLineWidth, 0, rcUp);
												}
											}
										}
									}
								} else {
									DrawThinLine(paint, upColor, thinLineWidth, scaleX - xsub, closeY, scaleX + xsub,
											closeY);
								}
								break;

							case 5:
								double lOpen = this.m_dataSource.Get3(i - 1, ciOpen);
								double lClose = this.m_dataSource.Get3(i - 1, ciClose);
								double lHigh = this.m_dataSource.Get3(i - 1, ciHigh);
								double lLow = this.m_dataSource.Get3(i - 1, ciLow);
								float top = highY;
								float bottom = lowY;
								if ((int) highY > (int) lowY) {
									top = lowY;
									bottom = highY;
								}
								if ((i == 0) || (Double.isNaN(lOpen)) || (Double.isNaN(lClose)) || (Double.isNaN(lHigh))
										|| (Double.isNaN(lLow))) {
									if (this.m_hScalePixel <= 3.0D) {
										DrawThinLine(paint, upColor, thinLineWidth, rcUpX, top, rcUpX, bottom);
									} else {
										int rcUpHeight = (int) Math.abs(bottom - top == 0.0F ? 1.0F : bottom - top);
										if ((rcUpW > 0) && (rcUpHeight > 0)) {
											RECT rcUp = new RECT(rcUpX, top, rcUpX + rcUpW, top + rcUpHeight);
											paint.FillRect(upColor, rcUp);
											if (thinLineWidth > 1) {
												paint.DrawRect(upColor, thinLineWidth, 0, rcUp);
											}
										}
									}
								} else {
									if (this.m_hScalePixel <= 3.0D) {
										DrawThinLine(paint, upColor, thinLineWidth, rcUpX, top, rcUpX, bottom);
									} else {
										int rcUpHeight = (int) Math.abs(bottom - top == 0.0F ? 1.0F : bottom - top);
										if ((rcUpW > 0) && (rcUpHeight > 0)) {
											RECT rcUp = new RECT(rcUpX, top, rcUpX + rcUpW, top + rcUpHeight);
											paint.FillRect(upColor, rcUp);
											if (thinLineWidth > 1) {
												paint.DrawRect(upColor, thinLineWidth, 0, rcUp);
											}
										}
									}
									if ((lClose < lOpen) && (low < lHigh)) {
										int tx = rcUpX;
										int ty = (int) GetY(div, lHigh, cs.GetAttachVScale());
										if (high < lHigh) {
											ty = (int) highY;
										}
										int width = rcUpW;
										int height = (int) lowY - ty;
										if (height > 0) {
											if (this.m_hScalePixel <= 3.0D) {
												DrawThinLine(paint, cs.GetDownColor(), thinLineWidth, tx, ty, tx,
														ty + height);

											} else if ((width > 0) && (height > 0)) {
												RECT tRect = new RECT(tx, ty, tx + width, ty + height);
												paint.FillRect(cs.GetDownColor(), tRect);
												if (thinLineWidth > 1) {
													paint.DrawRect(cs.GetDownColor(), thinLineWidth, 0, tRect);
												}
											}
										}
									}
								}
								break;

							}

						} else {
							float recth = GetDownCandleHeight(close, open,
									div.GetVScale(cs.GetAttachVScale()).GetVisibleMax(),
									div.GetVScale(cs.GetAttachVScale()).GetVisibleMin(),
									div.GetWorkingAreaHeight() - div.GetVScale(cs.GetAttachVScale()).GetPaddingBottom()
											- div.GetVScale(cs.GetAttachVScale()).GetPaddingTop());
							if (recth < 1.0F) {
								recth = 1.0F;
							}
							int rcDownX = scaleX - xsub;
							int rcDownTop = (int) openY;
							int rcDownBottom = (int) closeY;
							int rcDownW = cwidth;
							int rcDownH = (int) recth;
							if (closeY < openY) {
								rcDownTop = (int) closeY;
								rcDownBottom = (int) openY;
							}
							long downColor = COLOR.EMPTY;
							if (ciClr != -1) {
								double defineColor = this.m_dataSource.Get3(i, ciClr);
								if (!Double.isNaN(defineColor)) {
									downColor = (long) defineColor;
								}
							}
							if (downColor == COLOR.EMPTY) {
								downColor = cs.GetDownColor();
							}
							switch (style) {
							case 0:
							case 1:
							case 2:
								if ((int) highY != (int) lowY) {
									DrawThinLine(paint, downColor, thinLineWidth, scaleX, highY, scaleX, lowY);
									if (this.m_hScalePixel > 3.0D) {
										RECT rcDown = new RECT(rcDownX, rcDownTop, rcDownX + rcDownW, rcDownBottom);
										if (style == 1) {
											if ((rcDownW > 0) && (rcDownH > 0) && (this.m_hScalePixel > 3.0D)) {
												paint.FillRect(div.GetBackColor(), rcDown);
											}
											paint.DrawRect(downColor, thinLineWidth, 0, rcDown);
										} else if ((style == 0) || (style == 1)) {
											paint.FillRect(downColor, rcDown);
											if (thinLineWidth > 1) {
												paint.DrawRect(downColor, thinLineWidth, 0, rcDown);
											}
										}
									}
								} else {
									DrawThinLine(paint, downColor, thinLineWidth, scaleX - xsub, closeY, scaleX + xsub,
											closeY);
								}
								break;
							case 5:
								double lOpen = this.m_dataSource.Get3(i - 1, ciOpen);
								double lClose = this.m_dataSource.Get3(i - 1, ciClose);
								double lHigh = this.m_dataSource.Get3(i - 1, ciHigh);
								double lLow = this.m_dataSource.Get3(i - 1, ciLow);
								float top = highY;
								float bottom = lowY;
								if ((int) highY > (int) lowY) {
									top = lowY;
									bottom = highY;
								}
								if ((i == 0) || (Double.isNaN(lOpen)) || (Double.isNaN(lClose)) || (Double.isNaN(lHigh))
										|| (Double.isNaN(lLow))) {
									if (this.m_hScalePixel <= 3.0D) {
										DrawThinLine(paint, downColor, thinLineWidth, rcDownX, top, rcDownX, bottom);
									} else {
										int rcDownHeight = (int) Math.abs(bottom - top == 0.0F ? 1.0F : bottom - top);
										if ((rcDownW > 0) && (rcDownHeight > 0)) {
											RECT rcDown = new RECT(rcDownX, top, rcDownX + rcDownW, rcDownBottom);
											paint.FillRect(downColor, rcDown);
											if (thinLineWidth > 1) {
												paint.DrawRect(downColor, thinLineWidth, 0, rcDown);
											}
										}
									}
								} else {
									if (this.m_hScalePixel <= 3.0D) {
										DrawThinLine(paint, downColor, thinLineWidth, rcDownX, top, rcDownX, bottom);
									} else {
										int rcDownHeight = (int) Math.abs(bottom - top == 0.0F ? 1.0F : bottom - top);
										if ((rcDownW > 0) && (rcDownHeight > 0)) {
											RECT rcDown = new RECT(rcDownX, top, rcDownX + rcDownW, rcDownBottom);
											paint.FillRect(downColor, rcDown);
											if (thinLineWidth > 1) {
												paint.DrawRect(downColor, thinLineWidth, 0, rcDown);
											}
										}
									}
									if ((lClose >= lOpen) && (high > lLow)) {
										int tx = rcDownX;
										int ty = (int) highY;
										int width = rcDownW;
										int height = (int) Math.abs(GetY(div, lLow, cs.GetAttachVScale()) - ty);
										if (low > lLow) {
											height = (int) lowY - ty;
										}
										if (height > 0) {
											if (this.m_hScalePixel <= 3.0D) {
												DrawThinLine(paint, cs.GetUpColor(), thinLineWidth, tx, ty, tx,
														ty + height);

											} else if ((width > 0) && (height > 0)) {
												RECT tRect = new RECT(tx, ty, tx + width, ty + height);
												paint.FillRect(cs.GetUpColor(),
														new RECT(tx, ty, tx + width, ty + height));
												if (thinLineWidth > 1) {
													paint.DrawRect(cs.GetUpColor(), thinLineWidth, 0, tRect);
												}
											}
										}
									}
								}
								break;
							}

						}
						break;
					}

					if (cs.IsSelected()) {
						int kPInterval = this.m_maxVisibleRecord / 30;
						if (kPInterval < 2) {
							kPInterval = 3;
						}
						if (i % kPInterval == 0) {
							if ((!Double.isNaN(open)) && (!Double.isNaN(high)) && (!Double.isNaN(low))
									&& (!Double.isNaN(close))) {
								if ((closeY >= div.GetTitleBar().GetHeight())
										&& (closeY <= div.GetHeight() - div.GetHScale().GetHeight())) {
									RECT rect = new RECT(scaleX - 2, (int) closeY - 3, scaleX + 3, (int) closeY + 2);
									paint.FillRect(cs.GetSelectedColor(), rect);
								}
							}
						}
					}
				}
			}
		}
		OnPaintCandleEx(paint, div, cs, visibleMaxIndex, visibleMax, visibleMinIndex, visibleMin);
	}

	public void OnPaintCandleEx(CPaintMe paint, CDivMe div, CCandleShapeMe cs, int visibleMaxIndex, double visibleMax,
			int visibleMinIndex, double visibleMin) {
		if (this.m_dataSource.GetRowsCount() > 0) {
			if ((visibleMaxIndex != -1) && (visibleMinIndex != -1) && (cs.ShowMaxMin())) {
				double max = visibleMax;
				double min = visibleMin;
				float scaleYMax = GetY(div, max, cs.GetAttachVScale());
				float scaleYMin = GetY(div, min, cs.GetAttachVScale());
				int scaleXMax = (int) GetX(visibleMaxIndex);
				int digit = div.GetVScale(cs.GetAttachVScale()).GetDigit();
				SIZE maxSize = paint.TextSize(CStrMe.GetValueByDigit(max, digit), div.GetFont());
				float maxPX = 0.0F;
				float maxPY = 0.0F;
				float strY = 0.0F;
				if (scaleYMax > scaleYMin) {
					RefObject<Float> tempRef_maxPX = new RefObject(Float.valueOf(maxPX));
					RefObject<Float> tempRef_maxPY = new RefObject(Float.valueOf(maxPY));
					GetCandleMinStringPoint(scaleXMax, scaleYMax, maxSize.cx, maxSize.cy, GetWidth(),
							this.m_leftVScaleWidth, this.m_rightVScaleWidth, tempRef_maxPX, tempRef_maxPY);
					maxPX = ((Float) tempRef_maxPX.argvalue).floatValue();
					maxPY = ((Float) tempRef_maxPY.argvalue).floatValue();
					strY = maxPY + maxSize.cy;
				} else {
					RefObject<Float> tempRef_maxPX2 = new RefObject(Float.valueOf(maxPX));
					RefObject<Float> tempRef_maxPY2 = new RefObject(Float.valueOf(maxPY));
					GetCandleMaxStringPoint(scaleXMax, scaleYMax, maxSize.cx, maxSize.cy, GetWidth(),
							this.m_leftVScaleWidth, this.m_rightVScaleWidth, tempRef_maxPX2, tempRef_maxPY2);
					maxPX = ((Float) tempRef_maxPX2.argvalue).floatValue();
					maxPY = ((Float) tempRef_maxPY2.argvalue).floatValue();
					strY = maxPY;
				}
				POINT maxP = new POINT((int) maxPX, (int) maxPY);
				DrawText(paint, CStrMe.GetValueByDigit(max, digit), cs.GetTagColor(), div.GetFont(), maxP);
				paint.DrawLine(cs.GetTagColor(), 1.0F, 0, scaleXMax, (int) scaleYMax, maxP.x + maxSize.cx / 2,
						(int) strY);
				SIZE minSize = paint.TextSize(CStrMe.GetValueByDigit(min, digit), div.GetFont());
				int scaleXMin = (int) GetX(visibleMinIndex);
				float minPX = 0.0F;
				float minPY = 0.0F;
				if (scaleYMax > scaleYMin) {
					RefObject<Float> tempRef_minPX = new RefObject(Float.valueOf(minPX));
					RefObject<Float> tempRef_minPY = new RefObject(Float.valueOf(minPY));
					GetCandleMaxStringPoint(scaleXMin, scaleYMin, minSize.cx, minSize.cy, GetWidth(),
							this.m_leftVScaleWidth, this.m_rightVScaleWidth, tempRef_minPX, tempRef_minPY);
					minPX = ((Float) tempRef_minPX.argvalue).floatValue();
					minPY = ((Float) tempRef_minPY.argvalue).floatValue();
					strY = minPY;
				} else {
					RefObject<Float> tempRef_minPX2 = new RefObject(Float.valueOf(minPX));
					RefObject<Float> tempRef_minPY2 = new RefObject(Float.valueOf(minPY));
					GetCandleMinStringPoint(scaleXMin, scaleYMin, minSize.cx, minSize.cy, GetWidth(),
							this.m_leftVScaleWidth, this.m_rightVScaleWidth, tempRef_minPX2, tempRef_minPY2);
					minPX = ((Float) tempRef_minPX2.argvalue).floatValue();
					minPY = ((Float) tempRef_minPY2.argvalue).floatValue();
					strY = minPY + minSize.cy;
				}
				POINT minP = new POINT((int) minPX, (int) minPY);
				DrawText(paint, CStrMe.GetValueByDigit(min, digit), cs.GetTagColor(), div.GetFont(), minP);
				paint.DrawLine(cs.GetTagColor(), 1.0F, 0, scaleXMin, (int) scaleYMin, minP.x + minSize.cx / 2,
						(int) strY);
			}
		}
	}

	public void OnPaintCrossLine(CPaintMe paint, CDivMe div) {
		POINT mousePoint = GetMousePoint();
		if (this.m_cross_y != -1) {
			int divWidth = div.GetWidth();
			int divHeight = div.GetHeight();
			int titleBarHeight = div.GetTitleBar().GetHeight();
			int hScaleHeight = div.GetHScale().GetHeight();
			int mpY = this.m_cross_y - div.GetTop();
			if ((this.m_dataSource.GetRowsCount() > 0) && (this.m_hResizeType == 0) && (this.m_userResizeDiv == null)) {
				if ((mpY >= titleBarHeight) && (mpY <= divHeight - hScaleHeight)) {
					CVScaleMe leftVScale = div.GetLeftVScale();
					CCrossLineTipMe crossLineTip = leftVScale.GetCrossLineTip();
					if ((this.m_leftVScaleWidth != 0) && (crossLineTip.IsVisible())) {
						if (crossLineTip.AllowUserPaint()) {
							RECT clipRect = new RECT(0, 0, this.m_leftVScaleWidth, divHeight - hScaleHeight);
							crossLineTip.OnPaint(paint, div, clipRect);
						} else {
							double lValue = GetNumberValue(div, mousePoint, AttachVScale.Left);
							String leftValue = CStrMe.GetValueByDigit(lValue, leftVScale.GetDigit());
							SIZE leftYTipFontSize = paint.TextSize(leftValue, crossLineTip.GetFont());
							if ((leftYTipFontSize.cx > 0) && (leftYTipFontSize.cy > 0)) {
								int lRtX = this.m_leftVScaleWidth - leftYTipFontSize.cx - 1;
								int lRtY = mpY - leftYTipFontSize.cy / 2;
								int lRtW = leftYTipFontSize.cx;
								int lRtH = leftYTipFontSize.cy;
								if ((lRtW > 0) && (lRtH > 0)) {
									RECT lRtL = new RECT(lRtX, lRtY, lRtX + lRtW, lRtY + lRtH);
									paint.FillRect(crossLineTip.GetBackColor(), lRtL);
									paint.DrawRect(crossLineTip.GetForeColor(), 1.0F, 0, lRtL);
								}
								DrawText(paint, leftValue, crossLineTip.GetForeColor(), crossLineTip.GetFont(),
										new POINT(lRtX, lRtY));
							}
						}
					}
					CVScaleMe rightVScale = div.GetRightVScale();
					crossLineTip = rightVScale.GetCrossLineTip();
					if ((this.m_rightVScaleWidth != 0) && (crossLineTip.IsVisible())) {
						if (crossLineTip.AllowUserPaint()) {
							RECT clipRect = new RECT(divWidth - this.m_rightVScaleWidth, 0, divWidth,
									divHeight - hScaleHeight);
							crossLineTip.OnPaint(paint, div, clipRect);
						} else {
							double rValue = GetNumberValue(div, mousePoint, AttachVScale.Right);
							String rightValue = CStrMe.GetValueByDigit(rValue, rightVScale.GetDigit());
							SIZE rightYTipFontSize = paint.TextSize(rightValue, crossLineTip.GetFont());
							if ((rightYTipFontSize.cx > 0) && (rightYTipFontSize.cy > 0)) {
								int rRtX = GetWidth() - this.m_rightVScaleWidth + 1;
								int rRtY = mpY - rightYTipFontSize.cy / 2;
								int rRtW = rightYTipFontSize.cx;
								int rRtH = rightYTipFontSize.cy;
								if ((rRtW > 0) && (rRtH > 0)) {
									RECT rRtL = new RECT(rRtX, rRtY, rRtX + rRtW, rRtY + rRtH);
									paint.FillRect(crossLineTip.GetBackColor(), rRtL);
									paint.DrawRect(crossLineTip.GetForeColor(), 1.0F, 0, rRtL);
								}
								DrawText(paint, rightValue, crossLineTip.GetForeColor(), crossLineTip.GetFont(),
										new POINT(rRtX, rRtY));
							}
						}
					}
				}
			}
			int verticalX = 0;
			if ((this.m_crossStopIndex >= this.m_firstVisibleIndex)
					&& (this.m_crossStopIndex <= this.m_lastVisibleIndex)) {
				verticalX = (int) GetX(this.m_crossStopIndex);
			}
			if (!this.m_isScrollCross) {
				verticalX = mousePoint.x;
			}
			CCrossLineMe crossLine = div.GetCrossLine();
			if (crossLine.AllowUserPaint()) {
				RECT clRect = new RECT(0, 0, divWidth, divHeight);
				crossLine.OnPaint(paint, div, clRect);
			} else {
				if (this.m_showCrossLine) {
					if ((mpY >= titleBarHeight) && (mpY <= divHeight - hScaleHeight)) {
						paint.DrawLine(crossLine.GetLineColor(), 1.0F, 0, this.m_leftVScaleWidth, mpY,
								GetWidth() - this.m_rightVScaleWidth, mpY);
					}
				}
				if ((this.m_crossStopIndex == -1) || (this.m_crossStopIndex < this.m_firstVisibleIndex)
						|| (this.m_crossStopIndex > this.m_lastVisibleIndex)) {
					if (this.m_showCrossLine) {
						int x = mousePoint.x;
						if ((x > this.m_leftVScaleWidth) && (x < this.m_leftVScaleWidth + this.m_workingAreaWidth)) {
							paint.DrawLine(crossLine.GetLineColor(), 1.0F, 0, x, titleBarHeight, x,
									divHeight - hScaleHeight);
						}
					}
					return;
				}
				if (this.m_showCrossLine) {
					paint.DrawLine(crossLine.GetLineColor(), 1.0F, 0, verticalX, titleBarHeight, verticalX,
							divHeight - hScaleHeight);
				}
			}
			if ((this.m_hResizeType == 0) && (this.m_userResizeDiv == null)) {
				CHScaleMe hScale = div.GetHScale();
				CCrossLineTipMe hScaleCrossLineTip = hScale.GetCrossLineTip();
				if ((hScale.IsVisible()) && (hScaleCrossLineTip.IsVisible())) {
					if (hScaleCrossLineTip.AllowUserPaint()) {
						RECT clipRect = new RECT(0, divHeight - hScaleHeight, divWidth, divHeight);
						hScaleCrossLineTip.OnPaint(paint, div, clipRect);
					} else {
						String tip = "";
						if (hScale.GetHScaleType() == HScaleType.Date) {
							Calendar calendar = CStrMe
									.ConvertNumToDate(this.m_dataSource.GetXValue(this.m_crossStopIndex));
							SimpleDateFormat format = null;
							if (calendar.get(11) != 0) {
								format = new SimpleDateFormat("HH:mm");
							} else {
								format = new SimpleDateFormat("yyyy-MM-dd");
							}
							tip = format.format(calendar.getTime());
						} else if (hScale.GetHScaleType() == HScaleType.Number) {
							tip = new Double(this.m_dataSource.GetXValue(this.m_crossStopIndex)).toString();
						}
						SIZE xTipFontSize = paint.TextSize(tip, hScaleCrossLineTip.GetFont());
						int xRtX = verticalX - xTipFontSize.cx / 2;
						int xRtY = div.GetHeight() - hScaleHeight + 2;
						int xRtW = xTipFontSize.cx + 2;
						int xRtH = xTipFontSize.cy;
						if (xRtX < this.m_leftVScaleWidth) {
							xRtX = this.m_leftVScaleWidth;
							xRtY = divHeight - hScaleHeight + 2;
						}
						if (xRtX + xRtW > divWidth - this.m_rightVScaleWidth) {
							xRtX = divWidth - this.m_rightVScaleWidth - xTipFontSize.cx - 1;
							xRtY = divHeight - hScaleHeight + 2;
						}
						if ((xRtW > 0) && (xRtH > 0)) {
							RECT xRtL = new RECT(xRtX, xRtY, xRtX + xRtW, xRtY + xRtH);
							paint.FillRect(hScaleCrossLineTip.GetBackColor(), xRtL);
							paint.DrawRect(hScaleCrossLineTip.GetForeColor(), 1.0F, 0, xRtL);
							DrawText(paint, tip, hScaleCrossLineTip.GetForeColor(), hScaleCrossLineTip.GetFont(),
									new POINT(xRtX, xRtY));
						}
					}
				}
			}
		}
	}

	public void OnPaintDivBackGround(CPaintMe paint, CDivMe div) {
		int width = div.GetWidth();
		int height = div.GetHeight();
		if (width < 1) {
			width = 1;
		}
		if (height < 1) {
			height = 1;
		}
		if ((width > 0) && (height > 0)) {
			RECT rect = new RECT(0, 0, width, height);
			if (div.AllowUserPaint()) {
				div.OnPaint(paint, rect);

			} else if ((div.GetBackColor() != COLOR.EMPTY) && (div.GetBackColor() != GetBackColor())) {
				paint.FillRect(div.GetBackColor(), rect);
			}
		}
	}

	public void OnPaintDivBorder(CPaintMe paint, CDivMe div) {
		int y = 0;
		int width = div.GetWidth();
		int height = div.GetHeight();
		if (width < 1) {
			width = 1;
		}
		if (height < 1) {
			height = 1;
		}
		if ((width > 0) && (height > 0)) {
			CDivMe lDiv = null;
			ArrayList<CDivMe> divsCopy = GetDivs();
			for (CDivMe cDiv : divsCopy) {
				if (div == cDiv)
					break;
				lDiv = cDiv;
			}

			if (lDiv != null) {
				if (!lDiv.GetHScale().IsVisible()) {
					paint.DrawLine(div.GetHScale().GetScaleColor(), 1.0F, 0, this.m_leftVScaleWidth, y,
							width - this.m_rightVScaleWidth, y);
				} else {
					paint.DrawLine(div.GetHScale().GetScaleColor(), 1.0F, 0, 0, y, width, y);
				}
			}
			if ((div.ShowSelect()) && (div.IsSelected())) {
				if (this.m_leftVScaleWidth > 0) {
					RECT leftRect = new RECT(1, 1, this.m_leftVScaleWidth,
							div.GetHeight() - div.GetHScale().GetHeight() - 1);
					if ((leftRect.right - leftRect.left > 0) && (leftRect.bottom - leftRect.top > 0)) {
						paint.DrawRect(div.GetLeftVScale().GetScaleColor(), 1.0F, 0, leftRect);
					}
				}
				if (this.m_rightVScaleWidth > 0) {
					RECT rightRect = new RECT(GetWidth() - this.m_rightVScaleWidth + 1, 1, GetWidth(),
							div.GetHeight() - div.GetHScale().GetHeight() - 1);
					if ((rightRect.right - rightRect.left > 0) && (rightRect.bottom - rightRect.top > 0)) {
						paint.DrawRect(div.GetRightVScale().GetScaleColor(), 1.0F, 0, rightRect);
					}
				}
			}
			if (div.GetBorderColor() != COLOR.EMPTY) {
				if ((width > 0) && (height > 0)) {
					paint.DrawRect(div.GetBorderColor(), 1.0F, 0, new RECT(0, y, width, y + height));
				}
			}
		}
	}

	public void OnPaintHScale(CPaintMe paint, CDivMe div) {
		CHScaleMe hScale = div.GetHScale();
		CScaleGridMe vGrid = div.GetVGrid();
		int width = div.GetWidth();
		int height = div.GetHeight();
		int hScaleHeight = hScale.GetHeight();
		if (((hScale.IsVisible()) || (vGrid.IsVisible())) && (height >= hScaleHeight)) {
			RECT hRect = new RECT(0, height - hScaleHeight, width, height);
			if (hScale.AllowUserPaint()) {
				hScale.OnPaint(paint, div, hRect);
			}
			if (vGrid.AllowUserPaint()) {
				vGrid.OnPaint(paint, div, hRect);
			}
			if ((hScale.AllowUserPaint()) && (vGrid.AllowUserPaint())) {
				return;
			}
			int divBottom = div.GetHeight();
			if ((hScale.IsVisible()) && (!hScale.AllowUserPaint())) {
				paint.DrawLine(hScale.GetScaleColor(), 1.0F, 0, 0, divBottom - hScaleHeight + 1, width,
						divBottom - hScaleHeight + 1);
			}
			if (this.m_firstVisibleIndex >= 0) {
				double xScaleWordRight = 0.0D;
				int pureH = this.m_workingAreaWidth;
				ArrayList<Double> scaleSteps = hScale.GetScaleSteps();
				int scaleStepsSize = scaleSteps.size();
				HashMap<Double, Integer> scaleStepsMap = new HashMap();
				for (int i = 0; i < scaleStepsSize; i++) {
					scaleStepsMap.put(scaleSteps.get(i), Integer.valueOf(0));
				}
				if (hScale.GetHScaleType() == HScaleType.Number) {
					for (int i = this.m_firstVisibleIndex; i <= this.m_lastVisibleIndex; i++) {
						double xValue = this.m_dataSource.GetXValue(i);
						if ((scaleStepsSize <= 0) || (scaleStepsMap.containsKey(Double.valueOf(xValue)))) {

							String xValueStr = new Double(xValue).toString();
							int scaleX = (int) GetX(i);
							SIZE xSize = paint.TextSize(xValueStr, hScale.GetFont());
							if ((scaleStepsSize > 0)
									|| (scaleX - xSize.cx / 2 - hScale.GetInterval() > xScaleWordRight)) {
								if ((hScale.IsVisible()) && (!hScale.AllowUserPaint())) {
									DrawThinLine(paint, hScale.GetScaleColor(), 1, scaleX, divBottom - hScaleHeight + 1,
											scaleX, divBottom - hScaleHeight + 4);
									DrawText(paint, xValueStr, hScale.GetForeColor(), hScale.GetFont(),
											new POINT(scaleX - xSize.cx / 2, divBottom - hScaleHeight + 6));
								}
								xScaleWordRight = scaleX + xSize.cx / 2;
								if ((vGrid.IsVisible()) && (!vGrid.AllowUserPaint())) {
									paint.DrawLine(vGrid.GetGridColor(), 1.0F, vGrid.GetLineStyle(), scaleX,
											div.GetTitleBar().GetHeight(), scaleX, div.GetHeight() - hScaleHeight);
								}
							}
						}
					}
				} else {
					ArrayList<Integer> xList = new ArrayList();
					for (int i = this.m_firstVisibleIndex; i <= this.m_lastVisibleIndex; i++) {
						if (scaleStepsSize > 0) {
							double date = this.m_dataSource.GetXValue(i);
							if (scaleStepsMap.containsKey(Double.valueOf(date))) {
								scaleStepsMap.remove(Double.valueOf(date));
								scaleStepsSize = scaleStepsMap.size();
								xList.add(Integer.valueOf(i));
								if (scaleStepsSize == 0) {
									break;
								}
							}
						}
					}

					int interval = hScale.GetInterval();
					ArrayList<Integer> lasts = new ArrayList();
					for (int p = 0; p < 7; p++) {
						int count = 0;
						int xListSize = xList.size();
						for (int i = 0; i < xListSize; i++) {
							int pos = ((Integer) xList.get(i)).intValue();
							double date = this.m_dataSource.GetXValue(pos);
							DateType dateType = DateType.Day;
							double lDate = 0.0D;
							if (pos > 0) {
								lDate = this.m_dataSource.GetXValue(pos - 1);
							}
							RefObject<DateType> tempRef_dateType = new RefObject(dateType);
							String xValue = GetHScaleDateString(date, lDate, tempRef_dateType);
							dateType = (DateType) tempRef_dateType.argvalue;
							int scaleX = (int) GetX(pos);
							if (dateType == DateType.ForValue(p)) {
								count++;
								boolean show = true;
								Iterator i$;
								if (scaleStepsSize == 0) {
									for (i$ = lasts.iterator(); i$.hasNext();) {
										int index = ((Integer) i$.next()).intValue();

										int getX = (int) GetX(index);
										if (index > pos) {
											if (this.m_reverseHScale) {
												if (getX + interval * 2 > scaleX) {
													show = false;
													break;
												}

											} else if (getX - interval * 2 < scaleX) {
												show = false;
												break;
											}

										} else if (index < pos) {
											if (this.m_reverseHScale) {
												if (getX - interval * 2 < scaleX) {
													show = false;
													break;
												}

											} else if (getX + interval * 2 > scaleX) {
												show = false;
												break;
											}
										}
									}
								}

								if (show) {
									lasts.add(Integer.valueOf(pos));
									if ((hScale.IsVisible()) && (!hScale.AllowUserPaint())) {
										SIZE xSize = paint.TextSize(xValue, hScale.GetFont());
										DrawThinLine(paint, hScale.GetScaleColor(), 1, scaleX,
												divBottom - hScaleHeight + 1, scaleX, divBottom - hScaleHeight + 4);
										long dateColor = hScale.GetDateColor(DateType.ForValue(p));
										DrawText(paint, xValue, dateColor, hScale.GetFont(),
												new POINT(scaleX - xSize.cx / 2, divBottom - hScaleHeight + 6));
									}
									if ((vGrid.IsVisible()) && (!vGrid.AllowUserPaint())) {
										paint.DrawLine(vGrid.GetGridColor(), 1.0F, vGrid.GetLineStyle(), scaleX,
												div.GetTitleBar().GetHeight(), scaleX, div.GetHeight() - hScaleHeight);
									}
									xList.remove(i);
									i--;
									xListSize--;
								}
							}
						}
						if (count > pureH / 40) {
							break;
						}
					}

					lasts.clear();
				}
			}
		}
		if (div.GetTitleBar().ShowUnderLine()) {
			SIZE sizeTitle = paint.TextSize(" ", div.GetTitleBar().GetFont());
			paint.DrawLine(div.GetTitleBar().GetUnderLineColor(), 1.0F, 2, this.m_leftVScaleWidth, 5 + sizeTitle.cy,
					width - this.m_rightVScaleWidth, 5 + sizeTitle.cy);
		}
	}

	public void OnPaintIcon(CPaintMe paint) {
		if (this.m_movingShape != null) {
			CDivMe div = FindDiv(this.m_movingShape);
			if (div != null) {
				POINT actualPoint = GetMousePoint();
				int x = actualPoint.x;
				int y = actualPoint.y;
				if ((this.m_lastMouseClickPoint.x != -1) && (this.m_lastMouseClickPoint.y != -1)
						&& (Math.abs(x - this.m_lastMouseClickPoint.x) > 5)
						&& (Math.abs(y - this.m_lastMouseClickPoint.y) > 5)) {
					SIZE sizeK = new SIZE(15, 16);
					int rectCsX = x - sizeK.cx;
					int rectCsY = y - sizeK.cy;
					int rectCsH = sizeK.cy;
					if ((this.m_movingShape instanceof CBarShapeMe)) {
						CBarShapeMe bs = (CBarShapeMe) ((this.m_movingShape instanceof CBarShapeMe) ? this.m_movingShape
								: null);
						paint.FillRect(bs.GetUpColor(),
								new RECT(rectCsX + 1, rectCsY + 10, rectCsX + 4, rectCsY + rectCsH - 1));
						paint.FillRect(bs.GetUpColor(),
								new RECT(rectCsX + 6, rectCsY + 3, rectCsX + 9, rectCsY + rectCsH - 1));
						paint.FillRect(bs.GetUpColor(),
								new RECT(rectCsX + 11, rectCsY + 8, rectCsX + 14, rectCsY + rectCsH - 1));
					} else if ((this.m_movingShape instanceof CCandleShapeMe)) {
						CCandleShapeMe cs = (CCandleShapeMe) ((this.m_movingShape instanceof CCandleShapeMe)
								? this.m_movingShape
								: null);
						paint.DrawLine(cs.GetDownColor(), 1.0F, 0, rectCsX + 4, rectCsY + 6, rectCsX + 4,
								rectCsY + rectCsH - 2);
						paint.DrawLine(cs.GetUpColor(), 1.0F, 0, rectCsX + 9, rectCsY + 2, rectCsX + 9,
								rectCsY + rectCsH - 4);
						paint.FillRect(cs.GetDownColor(),
								new RECT(rectCsX + 3, rectCsY + 8, rectCsX + 6, rectCsY + 13));
						paint.FillRect(cs.GetUpColor(), new RECT(rectCsX + 8, rectCsY + 4, rectCsX + 11, rectCsY + 9));
					} else if ((this.m_movingShape instanceof CPolylineShapeMe)) {
						CPolylineShapeMe tls = (CPolylineShapeMe) ((this.m_movingShape instanceof CPolylineShapeMe)
								? this.m_movingShape
								: null);
						paint.DrawLine(tls.GetColor(), 1.0F, 0, rectCsX + 2, rectCsY + 5, rectCsX + 12, rectCsY + 1);
						paint.DrawLine(tls.GetColor(), 1.0F, 0, rectCsX + 2, rectCsY + 10, rectCsX + 12, rectCsY + 6);
						paint.DrawLine(tls.GetColor(), 1.0F, 0, rectCsX + 2, rectCsY + 15, rectCsX + 12, rectCsY + 11);
					}
				}
			}
		}
	}

	public void OnPaintPlots(CPaintMe paint, CDivMe div) {
		ArrayList<CPlotMe> plotsCopy = div.GetPlots(SortType.ASC);
		RECT clipRect;
		if ((plotsCopy != null) && (plotsCopy.size() > 0)) {
			int wX = this.m_workingAreaWidth;
			int wY = div.GetWorkingAreaHeight();
			if ((wX > 0) && (wY > 0)) {
				clipRect = new RECT();
				clipRect.left = this.m_leftVScaleWidth;
				clipRect.top = (div.GetTitleBar().IsVisible() ? div.GetTitleBar().GetHeight() : 0);
				clipRect.right = (clipRect.left + wX);
				clipRect.bottom = (clipRect.top + wY);
				for (CPlotMe pl : plotsCopy) {
					if (pl.IsVisible()) {
						paint.SetClip(clipRect);
						pl.Render(paint);
					}
				}
			}
		}
	}

	public void OnPaintPolyline(CPaintMe paint, CDivMe div, CPolylineShapeMe ls) {
		int x = 0;
		int y = 0;
		ArrayList<POINT> points = new ArrayList();
		int ciFieldName = this.m_dataSource.GetColumnIndex(ls.GetBaseField());
		int ciClr = this.m_dataSource.GetColumnIndex(ls.GetColorField());
		float defaultLineWidth = ls.GetWidth();
		if ((!IsOperating()) && (this.m_crossStopIndex != -1)) {
			if (SelectPolyline(div, GetMousePoint(), ls.GetBaseField(), ls.GetWidth(), ls.GetAttachVScale(),
					this.m_crossStopIndex)) {
				defaultLineWidth += 1.0F;
			}
		}
		for (int i = this.m_firstVisibleIndex; i <= this.m_lastVisibleIndex; i++) {
			int scaleX = (int) GetX(i);
			double value = this.m_dataSource.Get3(i, ciFieldName);
			if (!Double.isNaN(value)) {
				int lY = (int) GetY(div, value, ls.GetAttachVScale());
				RefObject<Integer> tempRef_x = new RefObject(Integer.valueOf(x));
				RefObject<Integer> tempRef_y = new RefObject(Integer.valueOf(y));
				OnPaintPolyline(paint, div, ls.GetColor(), ls.GetFillColor(), ciClr, defaultLineWidth, ls.GetStyle(),
						value, ls.GetAttachVScale(), scaleX, lY, i, points, tempRef_x, tempRef_y);
				x = ((Integer) tempRef_x.argvalue).intValue();
				y = ((Integer) tempRef_y.argvalue).intValue();
				if (ls.IsSelected()) {
					int kPInterval = this.m_maxVisibleRecord / 30;
					if (kPInterval < 2) {
						kPInterval = 3;
					}
					if (i % kPInterval == 0) {
						if ((lY >= div.GetTitleBar().GetHeight())
								&& (lY <= div.GetHeight() - div.GetHScale().GetHeight())) {
							int lineWidth = (int) ls.GetWidth();
							int rl = scaleX - 2 - (lineWidth - 1);
							int rt = lY - 3 - (lineWidth - 1);
							RECT rect = new RECT(rl, rt, rl + 5 + (lineWidth - 1) * 2, rt + 5 + (lineWidth - 1) * 2);
							paint.FillRect(ls.GetSelectedColor(), rect);
						}
					}
				}
			} else {
				RefObject<Integer> tempRef_x2 = new RefObject(Integer.valueOf(x));
				RefObject<Integer> tempRef_y2 = new RefObject(Integer.valueOf(y));
				OnPaintPolyline(paint, div, ls.GetColor(), ls.GetFillColor(), ciClr, defaultLineWidth, ls.GetStyle(),
						value, ls.GetAttachVScale(), scaleX, 0, i, points, tempRef_x2, tempRef_y2);
				x = ((Integer) tempRef_x2.argvalue).intValue();
				y = ((Integer) tempRef_y2.argvalue).intValue();
			}
		}
	}

	public void OnPaintPolyline(CPaintMe paint, CDivMe div, long lineColor, long fillColor, int ciClr, float lineWidth,
			PolylineStyle lineStyle, double value, AttachVScale attachVScale, int scaleX, int lY, int i,
			ArrayList<POINT> points, RefObject<Integer> x, RefObject<Integer> y) {
		if (!Double.isNaN(value)) {
			if (this.m_dataSource.GetRowsCount() == 1) {
				int cwidth = (int) (this.m_hScalePixel / 4.0D);
				points.add(new POINT(scaleX - cwidth, lY));
				points.add(new POINT(scaleX - cwidth + cwidth * 2 + 1, lY));
			} else {
				int newX = 0;
				int newY = 0;
				if (i == this.m_firstVisibleIndex) {
					x.argvalue = Integer.valueOf(scaleX);
					y.argvalue = Integer.valueOf(lY);
				}
				newX = scaleX;
				newY = lY;
				if ((((Integer) y.argvalue).intValue() <= div.GetHeight() - div.GetHScale().GetHeight() + 1)
						&& (((Integer) y.argvalue).intValue() >= div.GetTitleBar().GetHeight() - 1)
						&& (newY < div.GetHeight() - div.GetHScale().GetHeight() + 1)
						&& (newY >= div.GetTitleBar().GetHeight() - 1)) {
					if ((((Integer) x.argvalue).intValue() != newX) || (((Integer) y.argvalue).intValue() != newY)) {
						if (points.isEmpty()) {
							points.add(new POINT(((Integer) x.argvalue).intValue(), ((Integer) y.argvalue).intValue()));
							points.add(new POINT(newX, newY));
						} else {
							points.add(new POINT(newX, newY));
						}
					}
				}
				x.argvalue = Integer.valueOf(newX);
				y.argvalue = Integer.valueOf(newY);
			}
			if (ciClr != -1) {
				double defineColor = this.m_dataSource.Get3(i, ciClr);
				if (!Double.isNaN(defineColor)) {
					lineColor = (long) defineColor;
				}
			}
		}
		if (points.size() > 0) {
			long lColor = lineColor;
			if (i > 0) {
				if (ciClr != -1) {
					double defineColor = this.m_dataSource.Get3(i - 1, ciClr);
					if (!Double.isNaN(defineColor)) {
						lColor = (long) defineColor;
					}
				}
			}
			if ((lineColor != lColor) || (i == this.m_lastVisibleIndex)) {
				long drawColor = lineColor;
				int width = (int) (this.m_hScalePixel / 2.0D);
				if (lColor != lineColor) {
					drawColor = lColor;
				}
				switch (lineStyle) {
				case Cycle:
					int ew = width - 1 > 0 ? width - 1 : 1;
					int pointsSize = points.size();
					for (int j = 0; j < pointsSize; j++) {
						POINT point = (POINT) points.get(j);
						RECT pRect = new RECT(point.x - ew / 2, point.y - ew / 2, point.x + ew / 2, point.y + ew / 2);
						paint.DrawEllipse(drawColor, lineWidth, 0, pRect);
					}
					break;

				case DashLine:
					paint.DrawPolyline(drawColor, lineWidth, 1, (POINT[]) points.toArray(new POINT[0]));
					break;

				case DotLine:
					paint.DrawPolyline(drawColor, lineWidth, 2, (POINT[]) points.toArray(new POINT[0]));
					break;

				case SolidLine:
					paint.DrawPolyline(drawColor, lineWidth, 0, (POINT[]) points.toArray(new POINT[0]));
				}

				if (fillColor != COLOR.EMPTY) {
					int zy = (int) GetY(div, 0.0D, attachVScale);
					int th = div.GetTitleBar().IsVisible() ? div.GetTitleBar().GetHeight() : 0;
					int hh = div.GetHScale().IsVisible() ? div.GetHScale().GetHeight() : 0;
					if (zy < th) {
						zy = th;
					} else if (zy > div.GetHeight() - hh) {
						zy = div.GetHeight() - hh;
					}
					points.add(0, new POINT(((POINT) points.get(0)).x, zy));
					points.add(new POINT(((POINT) points.get(points.size() - 1)).x, zy));
					paint.FillPolygon(fillColor, (POINT[]) points.toArray(new POINT[0]));
				}
				points.clear();
			}
		}
	}

	public void OnPaintResizeLine(CPaintMe paint) {
		if (this.m_hResizeType > 0) {
			POINT mp;
			mp = GetMousePoint();
			ArrayList<CDivMe> divsCopy = GetDivs();
			for (CDivMe div : divsCopy) {
				if (mp.x < 0) {
					mp.x = 0;
				}
				if (mp.x > GetWidth()) {
					mp.x = GetWidth();
				}
				paint.DrawLine(COLOR.Reverse(paint, div.GetBackColor()), 1.0F, 2, mp.x, 0, mp.x, GetWidth());
			}
		}
		if (this.m_userResizeDiv != null) {
			POINT mp = GetMousePoint();
			CDivMe nextCP = null;
			boolean rightP = false;
			ArrayList<CDivMe> divsCopy = GetDivs();
			for (CDivMe cDiv : divsCopy) {
				if (rightP) {
					nextCP = cDiv;
					break;
				}
				if (cDiv == this.m_userResizeDiv) {
					rightP = true;
				}
			}
			RECT uRect = this.m_userResizeDiv.GetBounds();
			boolean drawFlag = false;
			if ((mp.x >= uRect.left) && (mp.x <= uRect.right) && (mp.y >= uRect.top) && (mp.y <= uRect.bottom)) {
				drawFlag = true;

			} else if (nextCP != null) {
				RECT nRect = nextCP.GetBounds();
				if ((mp.x >= nRect.left) && (mp.x <= nRect.right) && (mp.y >= nRect.top) && (mp.y <= nRect.bottom)) {
					drawFlag = true;
				}
			}

			if (drawFlag) {
				paint.DrawLine(COLOR.Reverse(paint, this.m_userResizeDiv.GetBackColor()), 1.0F, 2, 0, mp.y, GetWidth(),
						mp.y);
			}
		}
	}

	public void OnPaintSelectArea(CPaintMe paint, CDivMe div) {
		CSelectAreaMe selectArea = div.GetSelectArea();
		if (selectArea.IsVisible()) {
			RECT bounds = selectArea.GetBounds();
			if (selectArea.AllowUserPaint()) {
				selectArea.OnPaint(paint, div, bounds);

			} else if ((bounds.right - bounds.left >= 5) && (bounds.bottom - bounds.top >= 5)) {
				paint.DrawRect(selectArea.GetLineColor(), 1.0F, 0, bounds);
				paint.FillRect(selectArea.GetBackColor(), bounds);
			}
		}
	}

	public void OnPaintShapes(CPaintMe paint, CDivMe div) {
		ArrayList<CBaseShapeMe> sortedBs = div.GetShapes(SortType.ASC);
		for (CBaseShapeMe bShape : sortedBs) {
			if ((bShape.IsVisible()) && (div.GetVScale(bShape.GetAttachVScale()).GetVisibleMax()
					- div.GetVScale(bShape.GetAttachVScale()).GetVisibleMin() != 0.0D)) {

				if (bShape.AllowUserPaint()) {
					RECT rect = new RECT(0, 0, div.GetWidth(), div.GetHeight());
					bShape.OnPaint(paint, div, rect);
				} else {
					CBarShapeMe bs = (CBarShapeMe) ((bShape instanceof CBarShapeMe) ? bShape : null);
					CCandleShapeMe cs = (CCandleShapeMe) ((bShape instanceof CCandleShapeMe) ? bShape : null);
					CPolylineShapeMe ls = (CPolylineShapeMe) ((bShape instanceof CPolylineShapeMe) ? bShape : null);
					CTextShapeMe ts = (CTextShapeMe) ((bShape instanceof CTextShapeMe) ? bShape : null);
					if (ls != null) {
						OnPaintPolyline(paint, div, ls);
					} else if (ts != null) {
						OnPaintText(paint, div, ts);
					} else if (bs != null) {
						OnPaintBar(paint, div, bs);
					} else if (cs != null) {
						OnPaintCandle(paint, div, cs);
					}
				}
			}
		}
	}

	public void OnPaintText(CPaintMe paint, CDivMe div, CTextShapeMe ts) {
		String drawText = ts.GetText();
		if ((drawText == null) || (drawText.length() == 0)) {
			return;
		}
		int ciFieldName = this.m_dataSource.GetColumnIndex(ts.GetFieldName());
		int ciStyle = this.m_dataSource.GetColumnIndex(ts.GetStyleField());
		int ciClr = this.m_dataSource.GetColumnIndex(ts.GetColorField());
		for (int i = this.m_firstVisibleIndex; i <= this.m_lastVisibleIndex; i++) {
			int style = 0;
			if (ciStyle != -1) {
				double defineStyle = this.m_dataSource.Get3(i, ciStyle);
				if (!Double.isNaN(defineStyle)) {
					style = (int) defineStyle;
				}
			}
			if (style != 55536) {

				double value = this.m_dataSource.Get3(i, ciFieldName);
				if (!Double.isNaN(value)) {
					int scaleX = (int) GetX(i);
					int y = (int) GetY(div, value, ts.GetAttachVScale());
					SIZE tSize = paint.TextSize(drawText, ts.GetFont());
					RECT tRect = new RECT(scaleX - tSize.cx / 2, y - tSize.cy / 2, scaleX + tSize.cx / 2,
							y + tSize.cy / 2);
					long textColor = ts.GetForeColor();
					if (ts.GetColorField() != CTableMe.NULLFIELD) {
						double defineColor = this.m_dataSource.Get3(i, ciClr);
						if (!Double.isNaN(defineColor)) {
							textColor = (long) defineColor;
						}
					}
					DrawText(paint, drawText, textColor, ts.GetFont(), new POINT(tRect.left, tRect.top));
				}
			}
		}
	}

	public void OnPaintTitle(CPaintMe paint, CDivMe div) {
		CTitleBarMe titleBar = div.GetTitleBar();
		int width = div.GetWidth();
		int height = div.GetHeight();
		if (titleBar.IsVisible()) {
			RECT tbRect = new RECT(0, 0, width, height);
			if (titleBar.AllowUserPaint()) {
				titleBar.OnPaint(paint, div, tbRect);
			} else {
				int titleLeftPadding = this.m_leftVScaleWidth;
				int rightPadding = width - this.m_rightVScaleWidth - 2;
				SIZE divNameSize = paint.TextSize(div.GetTitleBar().GetText(), div.GetTitleBar().GetFont());
				if (titleLeftPadding + divNameSize.cx <= GetWidth() - this.m_rightVScaleWidth) {
					DrawText(paint, titleBar.GetText(), titleBar.GetForeColor(), titleBar.GetFont(),
							new POINT(titleLeftPadding, 2));
				}
				titleLeftPadding += divNameSize.cx + 2;
				if ((this.m_firstVisibleIndex >= 0) && (this.m_lastVisibleIndex >= 0)) {
					int displayIndex = this.m_lastVisibleIndex;
					if (displayIndex > this.m_lastUnEmptyIndex) {
						displayIndex = this.m_lastUnEmptyIndex;
					}
					if (this.m_showCrossLine) {
						if (this.m_crossStopIndex <= this.m_lastVisibleIndex) {
							displayIndex = this.m_crossStopIndex;
						}
						if (this.m_crossStopIndex < 0) {
							displayIndex = 0;
						}
						if (this.m_crossStopIndex >= this.m_lastVisibleIndex) {
							displayIndex = this.m_lastVisibleIndex;
						}
					}
					int curLength = 1;
					int tTop = 2;
					ArrayList<CTitleMe> titles = div.GetTitleBar().GetTitles();
					int titleSize = titles.size();
					for (int i = 0; i < titleSize; i++) {
						CTitleMe title = (CTitleMe) titles.get(i);
						if ((title.IsVisible()) && (title.GetFieldTextMode() != TextMode.None)) {

							double value = this.m_dataSource.Get2(displayIndex, title.GetFieldName());
							if (Double.isNaN(value)) {
								value = 0.0D;
							}
							String showTitle = "";
							if (title.GetFieldTextMode() != TextMode.Value) {
								showTitle = title.GetFieldText() + title.GetFieldTextSeparator();
							}
							if (title.GetFieldTextMode() != TextMode.Field) {
								int digit = title.GetDigit();
								showTitle = showTitle + CStrMe.GetValueByDigit(value, digit);
							}
							SIZE conditionSize = paint.TextSize(showTitle, div.GetTitleBar().GetFont());
							if (titleLeftPadding + conditionSize.cx + 8 > rightPadding) {
								curLength++;
								if (curLength > div.GetTitleBar().GetMaxLine())
									break;
								tTop += conditionSize.cy + 2;
								titleLeftPadding = this.m_leftVScaleWidth;
								rightPadding = GetWidth() - this.m_rightVScaleWidth;

								if (tTop + conditionSize.cy >= div.GetHeight() - div.GetHScale().GetHeight()) {
									break;
								}
							}

							if (titleLeftPadding <= rightPadding) {
								DrawText(paint, showTitle, title.GetTextColor(), titleBar.GetFont(),
										new POINT(titleLeftPadding, tTop));
								titleLeftPadding += conditionSize.cx + 8;
							}
						}
					}
				}
			}
		}
	}

	public void OnPaintToolTip(CPaintMe paint) {
		if (!this.m_showingToolTip) {
			return;
		}
		CBaseShapeMe bs = SelectShape(GetMouseOverIndex(), 0);
		if (bs != null) {
			POINT mouseP = GetMousePoint();
			CDivMe mouseOverDiv = GetMouseOverDiv();
			int digit = mouseOverDiv.GetVScale(bs.GetAttachVScale()).GetDigit();
			if (mouseOverDiv == null) {
				return;
			}
			int index = GetIndex(mouseP);
			if (index == -1) {
				return;
			}
			CToolTipMe toolTip = mouseOverDiv.GetToolTip();
			if (toolTip.AllowUserPaint()) {
				RECT toolRect = new RECT(0, 0, GetWidth(), GetHeight());
				toolTip.OnPaint(paint, mouseOverDiv, toolRect);
				return;
			}
			int pWidth = 0;
			int pHeight = 0;
			StringBuilder sbValue = new StringBuilder();
			FONT toolTipFont = toolTip.GetFont();
			double xValue = this.m_dataSource.GetXValue(index);
			int sLeft = mouseOverDiv.GetLeft();
			int sTop = mouseOverDiv.GetTop();
			for (int t = 0; t < 2; t++) {
				int x = mouseP.x + 10;
				int y = mouseP.y + 2;
				if (t == 0) {
					sLeft = x + 2;
					sTop = y;
				}
				SIZE xValueSize = new SIZE();
				if (mouseOverDiv.GetHScale().GetHScaleType() == HScaleType.Date) {
					int tm_year = 0;
					int tm_mon = 0;
					int tm_mday = 0;
					int tm_hour = 0;
					int tm_min = 0;
					int tm_sec = 0;
					int tm_msec = 0;
					RefObject<Integer> tempRef_tm_year = new RefObject(Integer.valueOf(tm_year));
					RefObject<Integer> tempRef_tm_mon = new RefObject(Integer.valueOf(tm_mon));
					RefObject<Integer> tempRef_tm_mday = new RefObject(Integer.valueOf(tm_mday));
					RefObject<Integer> tempRef_tm_hour = new RefObject(Integer.valueOf(tm_hour));
					RefObject<Integer> tempRef_tm_min = new RefObject(Integer.valueOf(tm_min));
					RefObject<Integer> tempRef_tm_sec = new RefObject(Integer.valueOf(tm_sec));
					RefObject<Integer> tempRef_tm_msec = new RefObject(Integer.valueOf(tm_msec));
					CMathLibMe.M130(xValue, tempRef_tm_year, tempRef_tm_mon, tempRef_tm_mday, tempRef_tm_hour,
							tempRef_tm_min, tempRef_tm_sec, tempRef_tm_msec);
					tm_year = ((Integer) tempRef_tm_year.argvalue).intValue();
					tm_mon = ((Integer) tempRef_tm_mon.argvalue).intValue();
					tm_mday = ((Integer) tempRef_tm_mday.argvalue).intValue();
					tm_hour = ((Integer) tempRef_tm_hour.argvalue).intValue();
					tm_min = ((Integer) tempRef_tm_min.argvalue).intValue();
					tm_sec = ((Integer) tempRef_tm_sec.argvalue).intValue();
					tm_msec = ((Integer) tempRef_tm_msec.argvalue).intValue();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String formatDate = this.m_hScaleFieldText + ":"
							+ format.format(new Date(tm_year, tm_mon, tm_mday, tm_hour, tm_min, tm_sec));
					xValueSize = paint.TextSize(formatDate, toolTipFont);
					pHeight = xValueSize.cy;
					if (t == 1) {
						DrawText(paint, formatDate, toolTip.GetForeColor(), toolTipFont, new POINT(sLeft, sTop));
					}
				} else if (mouseOverDiv.GetHScale().GetHScaleType() == HScaleType.Number) {
					String xValueStr = this.m_hScaleFieldText + ":" + new Double(xValue).toString();
					xValueSize = paint.TextSize(xValueStr, toolTipFont);
					pHeight = xValueSize.cy;
					if (t == 1) {
						DrawText(paint, xValueStr, toolTip.GetForeColor(), toolTipFont, new POINT(sLeft, sTop));
					}
				}
				sTop += xValueSize.cy;
				int[] fields = bs.GetFields();
				int fieldsLength = fields.length;
				if (fieldsLength > 0) {
					for (int i = 0; i < fieldsLength; i++) {
						String fieldText = bs.GetFieldText(fields[i]);
						double value = 0.0D;
						if (index >= 0) {
							value = this.m_dataSource.Get2(index, fields[i]);
						}
						String valueDigit = fieldText + ":" + CStrMe.GetValueByDigit(value, digit);
						if (t == 1) {
							DrawText(paint, valueDigit, toolTip.GetForeColor(), toolTipFont, new POINT(sLeft, sTop));
						}
						SIZE strSize = paint.TextSize(valueDigit, toolTipFont);
						if (i == 0) {
							pWidth = xValueSize.cx;
						}
						if (xValueSize.cx > pWidth) {
							pWidth = xValueSize.cx;
						}
						if (strSize.cx > pWidth) {
							pWidth = strSize.cx;
						}
						sTop += strSize.cy;
						pHeight += strSize.cy;
					}
				}
				if (t == 0) {
					int width = GetWidth();
					int height = GetHeight();
					pWidth += 4;
					pHeight++;
					if (x + pWidth > width) {
						x = width - pWidth;
						if (x < 0) {
							x = 0;
						}
					}
					if (y + pHeight > height) {
						y = height - pHeight;
						if (y < 0) {
							y = 0;
						}
					}
					sLeft = x;
					sTop = y;
					int rectPX = x;
					int rectPY = y;
					int rectPW = pWidth;
					int rectPH = pHeight;
					if ((rectPW > 0) && (rectPH > 0)) {
						RECT rectP = new RECT(rectPX, rectPY, rectPX + rectPW, rectPY + rectPH);
						paint.FillRect(toolTip.GetBackColor(), rectP);
						paint.DrawRect(toolTip.GetBorderColor(), 1.0F, 0, rectP);
					}
				}
			}
		}
	}

	public void OnPaintVScale(CPaintMe paint, CDivMe div) {
		int divBottom = div.GetHeight();
		ArrayList<Integer> gridYList = new ArrayList();
		boolean leftGridIsShow = false;
		int width = GetWidth();
		if (this.m_leftVScaleWidth > 0) {
			CVScaleMe leftVScale = div.GetLeftVScale();
			CScaleGridMe hGrid = div.GetHGrid();
			boolean paintV = true;
			boolean paintG = true;
			if (leftVScale.AllowUserPaint()) {
				RECT leftVRect = new RECT(0, 0, this.m_leftVScaleWidth, divBottom);
				leftVScale.OnPaint(paint, div, leftVRect);
				paintV = false;
			}
			if (hGrid.AllowUserPaint()) {
				RECT hGridRect = new RECT(0, 0, width, divBottom);
				hGrid.OnPaint(paint, div, hGridRect);
				paintG = false;
			}
			if ((paintV) || (paintG)) {
				if ((this.m_leftVScaleWidth <= width) && (paintV)) {
					paint.DrawLine(leftVScale.GetScaleColor(), 1.0F, 0, this.m_leftVScaleWidth, 0,
							this.m_leftVScaleWidth, divBottom - div.GetHScale().GetHeight());
				}
				FONT leftYFont = leftVScale.GetFont();
				SIZE leftYSize = paint.TextSize(" ", leftYFont);
				double min = leftVScale.GetVisibleMin();
				double max = leftVScale.GetVisibleMax();
				if ((min == 0.0D) && (max == 0.0D)) {
					CVScaleMe rightVScale = div.GetRightVScale();
					if ((rightVScale.GetVisibleMin() != 0.0D) || (rightVScale.GetVisibleMax() != 0.0D)) {
						min = rightVScale.GetVisibleMin();
						max = rightVScale.GetVisibleMax();
						POINT point1 = new POINT(0, div.GetTop() + div.GetTitleBar().GetHeight());
						double value1 = GetNumberValue(div, point1, AttachVScale.Right);
						POINT point2 = new POINT(0, div.GetBottom() - div.GetHScale().GetHeight());
						double value2 = GetNumberValue(div, point2, AttachVScale.Right);
						max = Math.max(value1, value2);
						min = Math.min(value1, value2);
					}
				} else {
					POINT point1 = new POINT(0, div.GetTop() + div.GetTitleBar().GetHeight());
					double value1 = GetNumberValue(div, point1, AttachVScale.Left);
					POINT point2 = new POINT(0, div.GetBottom() - div.GetHScale().GetHeight());
					double value2 = GetNumberValue(div, point2, AttachVScale.Left);
					max = Math.max(value1, value2);
					min = Math.min(value1, value2);
				}
				ArrayList<Double> scaleStepList = leftVScale.GetScaleSteps();
				if (scaleStepList.isEmpty()) {
					scaleStepList = GetVScaleStep(max, min, div, leftVScale);
				}
				float lY = -1.0F;
				int stepSize = scaleStepList.size();
				for (int i = 0; i < stepSize; i++) {
					double lValue = ((Double) scaleStepList.get(i)).doubleValue() / leftVScale.GetMagnitude();
					if ((lValue != 0.0D) && (leftVScale.GetType() == VScaleType.Percent)) {
						double baseValue = GetVScaleBaseValue(div, leftVScale, this.m_firstVisibleIndex)
								/ leftVScale.GetMagnitude();
						lValue = 100.0D * (lValue - baseValue * leftVScale.GetMagnitude()) / lValue;
					}
					String number = CStrMe.GetValueByDigit(lValue, leftVScale.GetDigit());
					if (div.GetLeftVScale().GetType() == VScaleType.Percent) {
						number = number + "%";
					}
					int y = (int) GetY(div, ((Double) scaleStepList.get(i)).doubleValue(), AttachVScale.Left);
					leftYSize = paint.TextSize(number, leftYFont);
					if ((y - leftYSize.cy / 2 >= 0) && (y + leftYSize.cy / 2 <= divBottom)) {

						if ((hGrid.IsVisible()) && (paintG)) {
							leftGridIsShow = true;
							if (!gridYList.contains(Integer.valueOf(y))) {
								gridYList.add(Integer.valueOf(y));
								paint.DrawLine(hGrid.GetGridColor(), 1.0F, hGrid.GetLineStyle(), this.m_leftVScaleWidth,
										y, width - this.m_rightVScaleWidth, y);
							}
						}
						if (paintV) {
							DrawThinLine(paint, leftVScale.GetScaleColor(), 1, this.m_leftVScaleWidth - 4, y,
									this.m_leftVScaleWidth, y);
							if (leftVScale.IsReverse()) {
								if ((lY != -1.0F) && (y - leftYSize.cy / 2 < lY)) {
									continue;
								}

								lY = y + leftYSize.cy / 2;
							} else {
								if ((lY != -1.0F) && (y + leftYSize.cy / 2 > lY)) {
									continue;
								}

								lY = y - leftYSize.cy / 2;
							}
							long scaleForeColor = leftVScale.GetForeColor();
							long scaleForeColor2 = leftVScale.GetForeColor2();
							if (leftVScale.GetType() == VScaleType.Percent) {
								if ((scaleForeColor2 != COLOR.EMPTY) && (lValue < 0.0D)) {
									scaleForeColor = scaleForeColor2;

								} else if ((scaleForeColor2 != COLOR.EMPTY)
										&& (((Double) scaleStepList.get(i)).doubleValue() < leftVScale.GetMidValue())) {
									scaleForeColor = scaleForeColor2;
								}
							}

							if ((leftVScale.GetType() != VScaleType.Percent)
									&& (leftVScale.GetNumberStyle() == NumberStyle.UnderLine)) {
								String[] nbs = number.split("[.]");
								if (nbs[0].length() > 0) {
									if (nbs.length >= 1) {
										DrawText(paint, nbs[0], scaleForeColor, leftYFont, new POINT(
												this.m_leftVScaleWidth - 10 - leftYSize.cx, y - leftYSize.cy / 2));
									}
									if (nbs.length >= 2) {
										SIZE decimalSize = paint.TextSize(nbs[0], leftYFont);
										SIZE size2 = paint.TextSize(nbs[1], leftYFont);
										DrawText(paint, nbs[1], scaleForeColor, leftYFont,
												new POINT(this.m_leftVScaleWidth - 10 - leftYSize.cx + decimalSize.cx,
														y - leftYSize.cy / 2));
										DrawThinLine(paint, scaleForeColor, 1,
												this.m_leftVScaleWidth - 10 - leftYSize.cx + decimalSize.cx,
												y + leftYSize.cy / 2,
												this.m_leftVScaleWidth - 10 - leftYSize.cx + decimalSize.cx + size2.cx,
												y + leftYSize.cy / 2);
									}
								}
							} else {
								DrawText(paint, number, scaleForeColor, leftYFont,
										new POINT(this.m_leftVScaleWidth - 10 - leftYSize.cx, y - leftYSize.cy / 2));
							}
							if (leftVScale.GetType() == VScaleType.GoldenRatio) {
								String goldenRatio = "";
								if (i == 1) {
									goldenRatio = "19.1%";
								} else if (i == 2) {
									goldenRatio = "38.2%";
								} else if (i == 4) {
									goldenRatio = "61.8%";
								} else if (i == 5) {
									goldenRatio = "80.9%";
								}
								if ((goldenRatio != null) && (goldenRatio.length() > 0)) {
									SIZE goldenRatioSize = paint.TextSize(goldenRatio, leftYFont);
									DrawText(paint, goldenRatio, scaleForeColor, leftYFont, new POINT(
											this.m_leftVScaleWidth - 10 - goldenRatioSize.cx, y + leftYSize.cy / 2));
								}
							}
						}
					}
				}
				if ((div.GetLeftVScale().GetMagnitude() != 1) && (paintV)) {
					String str = "X" + new Integer(leftVScale.GetMagnitude()).toString();
					SIZE sizeF = paint.TextSize(str, leftYFont);
					int x = this.m_leftVScaleWidth - sizeF.cx - 6;
					int y = div.GetHeight() - div.GetHScale().GetHeight() - sizeF.cy - 2;
					paint.DrawRect(leftVScale.GetScaleColor(), 1.0F, 0,
							new RECT(x - 1, y - 1, x + sizeF.cx + 1, y + sizeF.cy));
					DrawText(paint, str, leftVScale.GetForeColor(), leftYFont, new POINT(x, y));
				}
			}
		}
		if (this.m_rightVScaleWidth > 0) {
			CVScaleMe rightVScale = div.GetRightVScale();
			CScaleGridMe hGrid = div.GetHGrid();
			boolean paintV = true;
			boolean paintG = true;
			if (rightVScale.AllowUserPaint()) {
				RECT rightVRect = new RECT(width - this.m_rightVScaleWidth, 0, width, divBottom);
				rightVScale.OnPaint(paint, div, rightVRect);
				paintV = false;
			}
			if (hGrid.AllowUserPaint()) {
				RECT hGridRect = new RECT(0, 0, width, divBottom);
				hGrid.OnPaint(paint, div, hGridRect);
				paintG = false;
			}
			if ((paintV) || (paintG)) {
				if ((width - this.m_rightVScaleWidth >= this.m_leftVScaleWidth) && (paintV)) {
					paint.DrawLine(rightVScale.GetScaleColor(), 1.0F, 0, width - this.m_rightVScaleWidth, 0,
							width - this.m_rightVScaleWidth, divBottom - div.GetHScale().GetHeight());
				}
				FONT rightYFont = rightVScale.GetFont();
				SIZE rightYSize = paint.TextSize(" ", rightYFont);
				double min = rightVScale.GetVisibleMin();
				double max = rightVScale.GetVisibleMax();
				if ((min == 0.0D) && (max == 0.0D)) {
					CVScaleMe leftVScale = div.GetLeftVScale();
					if ((leftVScale.GetVisibleMin() != 0.0D) || (leftVScale.GetVisibleMax() != 0.0D)) {
						min = leftVScale.GetVisibleMin();
						max = leftVScale.GetVisibleMax();
						POINT point1 = new POINT(0, div.GetTop() + div.GetTitleBar().GetHeight());
						double value1 = GetNumberValue(div, point1, AttachVScale.Left);
						POINT point2 = new POINT(0, div.GetBottom() - div.GetHScale().GetHeight());
						double value2 = GetNumberValue(div, point2, AttachVScale.Left);
						max = Math.max(value1, value2);
						min = Math.min(value1, value2);
					}
				} else {
					POINT point1 = new POINT(0, div.GetTop() + div.GetTitleBar().GetHeight());
					double value1 = GetNumberValue(div, point1, AttachVScale.Right);
					POINT point2 = new POINT(0, div.GetBottom() - div.GetHScale().GetHeight());
					double value2 = GetNumberValue(div, point2, AttachVScale.Right);
					max = Math.max(value1, value2);
					min = Math.min(value1, value2);
				}
				ArrayList<Double> scaleStepList = rightVScale.GetScaleSteps();
				if (scaleStepList.isEmpty()) {
					scaleStepList = GetVScaleStep(max, min, div, rightVScale);
				}
				float lY = -1.0F;
				int stepSize = scaleStepList.size();
				for (int i = 0; i < stepSize; i++) {
					double rValue = ((Double) scaleStepList.get(i)).doubleValue() / rightVScale.GetMagnitude();
					if ((rValue != 0.0D) && (rightVScale.GetType() == VScaleType.Percent)) {
						double baseValue = GetVScaleBaseValue(div, rightVScale, this.m_lastVisibleIndex)
								/ rightVScale.GetMagnitude();
						rValue = 100.0D * (rValue - baseValue * rightVScale.GetMagnitude()) / rValue;
					}
					String number = CStrMe.GetValueByDigit(rValue, rightVScale.GetDigit());
					if (rightVScale.GetType() == VScaleType.Percent) {
						number = number + "%";
					}
					int y = (int) GetY(div, ((Double) scaleStepList.get(i)).doubleValue(), AttachVScale.Right);
					rightYSize = paint.TextSize(number, rightYFont);
					if ((y - rightYSize.cy / 2 >= 0) && (y + rightYSize.cy / 2 <= divBottom)) {

						if ((hGrid.IsVisible()) && (paintG) && (!leftGridIsShow)) {
							if (!gridYList.contains(Integer.valueOf(y))) {
								gridYList.add(Integer.valueOf(y));
								paint.DrawLine(hGrid.GetGridColor(), 1.0F, hGrid.GetLineStyle(), this.m_leftVScaleWidth,
										y, width - this.m_rightVScaleWidth, y);
							}
						}
						if (paintV) {
							DrawThinLine(paint, rightVScale.GetScaleColor(), 1, width - this.m_rightVScaleWidth, y,
									width - this.m_rightVScaleWidth + 4, y);
							if (rightVScale.IsReverse()) {
								if ((lY != -1.0F) && (y - rightYSize.cy / 2 < lY)) {
									continue;
								}

								lY = y + rightYSize.cy / 2;
							} else {
								if ((lY != -1.0F) && (y + rightYSize.cy / 2 > lY)) {
									continue;
								}

								lY = y - rightYSize.cy / 2;
							}
							long scaleForeColor = rightVScale.GetForeColor();
							long scaleForeColor2 = rightVScale.GetForeColor2();
							if (rightVScale.GetType() == VScaleType.Percent) {
								if ((scaleForeColor2 != COLOR.EMPTY) && (rValue < 0.0D)) {
									scaleForeColor = scaleForeColor2;

								} else if ((scaleForeColor2 != COLOR.EMPTY) && (((Double) scaleStepList.get(i))
										.doubleValue() < rightVScale.GetMidValue())) {
									scaleForeColor = scaleForeColor2;
								}
							}

							if ((rightVScale.GetType() != VScaleType.Percent)
									&& (rightVScale.GetNumberStyle() == NumberStyle.UnderLine)) {
								String[] nbs = number.split("[.]");
								if (nbs[0].length() > 0) {
									if (nbs.length >= 1) {
										DrawText(paint, nbs[0], scaleForeColor, rightYFont,
												new POINT(width - this.m_rightVScaleWidth + 10, y - rightYSize.cy / 2));
									}
									if (nbs.length >= 2) {
										SIZE decimalSize = paint.TextSize(nbs[0], rightYFont);
										SIZE size2 = paint.TextSize(nbs[1], rightYFont);
										DrawText(paint, nbs[1], scaleForeColor, rightYFont,
												new POINT(width - this.m_rightVScaleWidth + 10 + decimalSize.cx,
														y - rightYSize.cy / 2));
										DrawThinLine(paint, scaleForeColor, 1,
												width - this.m_rightVScaleWidth + 10 + decimalSize.cx,
												y + rightYSize.cy / 2,
												GetWidth() - this.m_rightVScaleWidth + 10 + decimalSize.cx + size2.cx,
												y + rightYSize.cy / 2);
									}
								}
							} else {
								DrawText(paint, number, scaleForeColor, rightYFont,
										new POINT(width - this.m_rightVScaleWidth + 10, y - rightYSize.cy / 2));
							}
							if (rightVScale.GetType() == VScaleType.GoldenRatio) {
								String goldenRatio = "";
								if (i == 1) {
									goldenRatio = "19.1%";
								} else if (i == 2) {
									goldenRatio = "38.2%";
								} else if (i == 4) {
									goldenRatio = "61.8%";
								} else if (i == 5) {
									goldenRatio = "80.9%";
								}
								if ((goldenRatio != null) && (goldenRatio.length() > 0)) {
									DrawText(paint, goldenRatio, scaleForeColor, rightYFont,
											new POINT(width - this.m_rightVScaleWidth + 10, y + rightYSize.cy / 2));
								}
							}
						}
					}
				}
				if ((rightVScale.GetMagnitude() != 1) && (paintV)) {
					String str = "X" + new Integer(rightVScale.GetMagnitude()).toString();
					SIZE sizeF = paint.TextSize(str, rightYFont);
					int x = width - this.m_rightVScaleWidth + 6;
					int y = div.GetHeight() - div.GetHScale().GetHeight() - sizeF.cy - 2;
					paint.DrawRect(rightVScale.GetScaleColor(), 1.0F, 0,
							new RECT(x - 1, y - 1, x + sizeF.cx + 1, y + sizeF.cy));
					DrawText(paint, str, rightVScale.GetForeColor(), rightYFont, new POINT(x, y));
				}
			}
		}
	}

	public void OnLoad() {
		super.OnLoad();
		StartTimer(this.m_timerID, 10);
		if (this.m_dataSource == null) {
			this.m_dataSource = new CTableExMe();
		}
	}

	public void OnMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseDown(mp.Clone(), button, clicks, delta);
		if ((button == MouseButtonsA.Left) && (clicks == 2)) {
			ClearSelectedShape();
			this.m_showCrossLine = (!this.m_showCrossLine);
			Invalidate();
			return;
		}
		int width = GetWidth();
		this.m_userResizeDiv = null;
		int shapeCount = GetSelectedShape() == null ? 0 : 1;
		ArrayList<CDivMe> divsCopy = GetDivs();
		this.m_hResizeType = 0;
		if (button == MouseButtonsA.Left) {
			ClearSelectedPlot();
			CDivMe mouseOverDiv = GetMouseOverDiv();
			for (CDivMe div : divsCopy) {
				if (div == mouseOverDiv) {
					div.SetSelected(true);
				} else {
					div.SetSelected(false);
				}
			}
			if (clicks == 1) {
				CloseSelectArea();
				this.m_crossStopIndex = GetMouseOverIndex();
				this.m_cross_y = mp.y;
				if ((this.m_showCrossLine) && (this.m_crossLineMoveMode == CrossLineMoveMode.AfterClick)) {
					this.m_crossStopIndex = GetMouseOverIndex();
					this.m_cross_y = GetMousePoint().y;
					this.m_isScrollCross = false;
				}
				boolean outLoop = false;
				if (this.m_canResizeH) {
					if ((mp.x >= this.m_leftVScaleWidth - 4) && (mp.x <= this.m_leftVScaleWidth + 4)) {
						this.m_hResizeType = 1;
						outLoop = true;
					} else if ((mp.x >= GetWidth() - this.m_rightVScaleWidth - 4)
							&& (mp.x <= GetWidth() - this.m_rightVScaleWidth + 4)) {
						this.m_hResizeType = 2;
						outLoop = true;
					}
				}
				int pIndex;
				if ((!outLoop) && (this.m_canResizeV)) {
					pIndex = 0;
					for (CDivMe cDiv : divsCopy) {
						pIndex++;
						if (pIndex == divsCopy.size()) {
							break;
						}

						RECT resizeRect = new RECT(0, cDiv.GetBottom() - 4, cDiv.GetWidth(), cDiv.GetBottom() + 4);
						if ((mp.x >= resizeRect.left) && (mp.x <= resizeRect.right) && (mp.y >= resizeRect.top)
								&& (mp.y <= resizeRect.bottom)) {
							this.m_userResizeDiv = cDiv;
						}
					}
				}
				if ((mp.x >= this.m_leftVScaleWidth) && (mp.x <= width - this.m_rightVScaleWidth)) {
					if (mouseOverDiv != null) {
						ArrayList<CPlotMe> plotsCopy = mouseOverDiv.GetPlots(SortType.DESC);
						for (CPlotMe lsb : plotsCopy) {
							if ((lsb.IsEnabled()) && (lsb.IsVisible()) && (lsb.OnSelect())) {
								this.m_movingPlot = lsb;
								lsb.OnMoveStart();
								ArrayList<Double> zorders = new ArrayList();
								ArrayList<CPlotMe> plots = mouseOverDiv.GetPlots(SortType.DESC);
								int plotSize = plots.size();
								for (int i = 0; i < plotSize; i++) {
									zorders.add(Double.valueOf(((CPlotMe) plots.get(i)).GetZOrder()));
								}
								int zordersSize = zorders.size();
								double[] zordersArray = new double[zordersSize];
								for (int i = 0; i < zordersSize; i++) {
									zordersArray[i] = ((Double) zorders.get(i)).doubleValue();
								}
								lsb.SetZOrder((int) CMathLibMe.GetMax(zordersArray, zordersSize) + 1);
							}
						}
					}
					if (this.m_movingPlot != null) {
						this.m_movingPlot.SetSelected(true);
						if (shapeCount != 0) {
							ClearSelectedShape();
						}
					} else {
						CBaseShapeMe bs = SelectShape(this.m_crossStopIndex, 1);
						CDivMe div = null;
						if (bs == null) {
							div = mouseOverDiv;
							if ((div != null) && (div.GetSelectArea().IsEnabled())) {
								if ((mp.y >= div.GetTop() + 2)
										&& (mp.y <= div.GetBottom() - div.GetHScale().GetHeight() - 2)) {
									this.m_showingSelectArea = true;
								}
							}
						}
					}
				}
			}
		} else {
			this.m_isMouseMove = true;
			this.m_showingToolTip = false;
		}
		this.m_lastMouseClickPoint = mp;
		if (this.m_canMoveShape) {
			if (GetSelectedShape() != null) {
				this.m_movingShape = GetSelectedShape();
			}
		}
		Invalidate();
	}

	public void OnMouseMove(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseMove(mp.Clone(), button, clicks, delta);
		if ((mp.x != this.m_lastMouseMovePoint.x) || (mp.y != this.m_lastMouseMovePoint.y)) {
			int width = GetWidth();
			this.m_isMouseMove = true;
			this.m_showingToolTip = false;
			ArrayList<CDivMe> divsCopy = GetDivs();
			for (CDivMe div : divsCopy) {
				boolean resize = false;
				if ((div.GetSelectArea().IsVisible()) && (div.GetSelectArea().CanResize())) {
					resize = true;

				} else if (this.m_showingSelectArea) {
					if (button == MouseButtonsA.Left) {
						int subX = this.m_lastMouseClickPoint.x - this.m_lastMouseMovePoint.x;
						int subY = this.m_lastMouseMovePoint.y - this.m_lastMouseClickPoint.y;
						if ((Math.abs(subX) > this.m_hScalePixel * 2.0D)
								|| (Math.abs(subY) > this.m_hScalePixel * 2.0D)) {
							this.m_showingSelectArea = false;
							div.GetSelectArea().SetVisible(true);
							div.GetSelectArea().SetCanResize(true);
							resize = true;
						}
					}
				}

				if ((resize) && (button == MouseButtonsA.Left)) {
					int x1 = this.m_lastMouseClickPoint.x;
					int y1 = this.m_lastMouseClickPoint.y;
					int x2 = mp.x;
					int y2 = mp.y;
					if (x2 < this.m_leftVScaleWidth) {
						x2 = this.m_leftVScaleWidth;
					} else if (x2 > GetWidth() - this.m_rightVScaleWidth) {
						x2 = GetWidth() - this.m_rightVScaleWidth;
					}
					if (y2 > div.GetBottom() - div.GetHScale().GetHeight()) {
						y2 = div.GetBottom() - div.GetHScale().GetHeight();
					} else if (y2 < div.GetTop() + div.GetTitleBar().GetHeight()) {
						y2 = div.GetTop() + div.GetTitleBar().GetHeight();
					}
					int bx = 0;
					int by = 0;
					int bwidth = 0;
					int bheight = 0;
					RefObject<Integer> tempRef_bx = new RefObject(Integer.valueOf(bx));
					RefObject<Integer> tempRef_by = new RefObject(Integer.valueOf(by));
					RefObject<Integer> tempRef_bwidth = new RefObject(Integer.valueOf(bwidth));
					RefObject<Integer> tempRef_bheight = new RefObject(Integer.valueOf(bheight));
					CMathLibMe.M105(x1, y1 - div.GetTop(), x2, y2 - div.GetTop(), tempRef_bx, tempRef_by,
							tempRef_bwidth, tempRef_bheight);
					bx = ((Integer) tempRef_bx.argvalue).intValue();
					by = ((Integer) tempRef_by.argvalue).intValue();
					bwidth = ((Integer) tempRef_bwidth.argvalue).intValue();
					bheight = ((Integer) tempRef_bheight.argvalue).intValue();
					RECT bounds = new RECT(bx, by, bx + bwidth, by + bheight);
					div.GetSelectArea().SetBounds(bounds);
					Invalidate();
					this.m_lastMouseMovePoint = mp;
					return;
				}
				if (div.GetSelectArea().IsVisible()) {
					return;
				}
			}
			this.m_lastMouseMoveTime = Calendar.getInstance();
			if ((this.m_movingPlot != null) && (button == MouseButtonsA.Left)) {
				this.m_movingPlot.OnMoving();
			} else {
				boolean outLoop = false;
				if (this.m_canResizeH) {
					if (this.m_hResizeType == 0) {
						if (((mp.x >= this.m_leftVScaleWidth - 4) && (mp.x <= this.m_leftVScaleWidth + 4))
								|| ((mp.x >= width - this.m_rightVScaleWidth - 4)
										&& (mp.x <= width - this.m_rightVScaleWidth + 4))) {
							outLoop = true;
						}

					} else {
						outLoop = true;
					}
				}
				if ((!outLoop) && (this.m_canResizeV)) {
					int pIndex;
					if (this.m_userResizeDiv == null) {
						pIndex = 0;
						for (CDivMe cDiv : divsCopy) {
							pIndex++;
							if (pIndex == divsCopy.size()) {
								break;
							}

							RECT resizeRect = new RECT(0, cDiv.GetBottom() - 4, GetWidth(), cDiv.GetBottom() + 4);
							if ((mp.x >= resizeRect.left) && (mp.x <= resizeRect.right) && (mp.y >= resizeRect.top)
									&& (mp.y <= resizeRect.bottom)) {
								outLoop = true;
							}
						}
					} else {
						outLoop = true;
					}
				}
			}
			this.m_crossStopIndex = GetMouseOverIndex();
			this.m_cross_y = mp.y;
			if ((this.m_showCrossLine) && (this.m_crossLineMoveMode == CrossLineMoveMode.FollowMouse)) {
				this.m_isScrollCross = false;
			}
			Invalidate();
		}
		this.m_lastMouseMovePoint = mp;
	}

	public void OnMouseUp(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseUp(mp.Clone(), button, clicks, delta);
		boolean needUpdate = false;
		if (this.m_movingShape != null) {
			this.m_movingShape = null;
		}
		ArrayList<CDivMe> divsCopy = GetDivs();
		for (CDivMe div : divsCopy) {
			if (div.GetSelectArea().IsVisible()) {
				div.GetSelectArea().SetCanResize(false);
				Invalidate();
				return;
			}
		}
		CBaseShapeMe selectedShape = GetSelectedShape();
		CDivMe curDiv;
		if ((this.m_hResizeType == 0) && (this.m_userResizeDiv == null) && (button == MouseButtonsA.Left)
				&& (this.m_canMoveShape) && (selectedShape != null)) {
			curDiv = FindDiv(selectedShape);
			for (CDivMe cDiv : divsCopy) {
				if ((mp.y >= cDiv.GetTop()) && (mp.y <= cDiv.GetBottom())) {
					if (cDiv == curDiv) {
						break;
					}

					if (!cDiv.ContainsShape(selectedShape)) {
						for (CDivMe div : divsCopy) {
							if (div.ContainsShape(selectedShape)) {
								div.RemoveShape(selectedShape);
							}
						}
						cDiv.AddShape(selectedShape);
						needUpdate = true;
					}
				}
			}
		}
		if (this.m_movingPlot != null) {
			this.m_movingPlot = null;
		}
		if (ResizeDiv()) {
			needUpdate = true;
		}
		if (needUpdate) {
			Update();
		}
		Invalidate();
	}

	public void OnPaintForeground(CPaintMe paint, RECT clipRect) {
		if (IsVisible()) {
			ArrayList<CDivMe> divsCopy = GetDivs();
			POINT offset = paint.GetOffset();
			for (CDivMe div : divsCopy) {
				int offsetX = offset.x + div.GetLeft();
				int offsetY = offset.y + div.GetTop();
				paint.SetOffset(new POINT(offsetX, offsetY));
				RECT divClipRect = new RECT(0, 0, div.GetWidth(), div.GetHeight());
				paint.SetClip(divClipRect);
				OnPaintDivBackGround(paint, div);
				OnPaintVScale(paint, div);
				OnPaintHScale(paint, div);
				OnPaintShapes(paint, div);
				OnPaintDivBorder(paint, div);
				OnPaintCrossLine(paint, div);
				OnPaintTitle(paint, div);
				OnPaintSelectArea(paint, div);
				OnPaintPlots(paint, div);
			}
			paint.SetOffset(offset);
			paint.SetClip(clipRect);
			OnPaintResizeLine(paint);
			OnPaintToolTip(paint);
			OnPaintIcon(paint);
		}
	}

	public void OnTimer(int timerID) {
		super.OnTimer(timerID);
		if ((IsVisible()) && (this.m_timerID == timerID)) {
			INativeBaseMe inative = GetNative();
			if (this == inative.GetHoveredControl()) {
				CheckToolTip();
			}
		}
	}

	public static enum ScrollType {
		None(0), Left(1), Right(2), ZoomIn(3), ZoomOut(4);

		private int intValue;
		private static HashMap<Integer, ScrollType> mappings;

		private static synchronized HashMap<Integer, ScrollType> GetMappings() {
			if (mappings == null) {
				mappings = new HashMap();
			}
			return mappings;
		}

		private ScrollType(int value) {
			this.intValue = value;
			GetMappings().put(Integer.valueOf(value), this);
		}

		public int GetValue() {
			return this.intValue;
		}

		public static ScrollType ForValue(int value) {
			return (ScrollType) GetMappings().get(Integer.valueOf(value));
		}
	}

	protected int GetMaxVisibleCount(double hScalePixel, int pureH) {
		return (int) (pureH / hScalePixel);
	}

	protected void GetCandleMaxStringPoint(float scaleX, float scaleY, float stringWidth, float stringHeight,
			int actualWidth, int leftVScaleWidth, int rightVScaleWidth, RefObject<Float> x, RefObject<Float> y) {
		if (scaleX < leftVScaleWidth + stringWidth) {
			x.argvalue = Float.valueOf(scaleX);
		} else if (scaleX > actualWidth - rightVScaleWidth - stringWidth) {
			x.argvalue = Float.valueOf(scaleX - stringWidth);

		} else if (scaleX < actualWidth / 2) {
			x.argvalue = Float.valueOf(scaleX - stringWidth);
		} else {
			x.argvalue = Float.valueOf(scaleX);
		}

		y.argvalue = Float.valueOf(scaleY + stringHeight / 2.0F);
	}

	protected void GetCandleMinStringPoint(float scaleX, float scaleY, float stringWidth, float stringHeight,
			int actualWidth, int leftVScaleWidth, int rightVScaleWidth, RefObject<Float> x, RefObject<Float> y) {
		if (scaleX < leftVScaleWidth + stringWidth) {
			x.argvalue = Float.valueOf(scaleX);
		} else if (scaleX > actualWidth - rightVScaleWidth - stringWidth) {
			x.argvalue = Float.valueOf(scaleX - stringWidth);

		} else if (scaleX < actualWidth / 2) {
			x.argvalue = Float.valueOf(scaleX - stringWidth);
		} else {
			x.argvalue = Float.valueOf(scaleX);
		}

		y.argvalue = Float.valueOf(scaleY - stringHeight * 3.0F / 2.0F);
	}

	protected int GetChartIndex(int x, int leftVScaleWidth, double hScalePixel, int firstVisibleIndex) {
		return (int) ((x - leftVScaleWidth) / hScalePixel + firstVisibleIndex);
	}

	protected float GetUpCandleHeight(double close, double open, double max, double min, float divPureV) {
		if (close - open == 0.0D) {
			return 1.0F;
		}

		return (float) ((close - open) / (max - min) * divPureV);
	}

	protected float GetDownCandleHeight(double close, double open, double max, double min, float divPureV) {
		if (close - open == 0.0D) {
			return 1.0F;
		}

		return (float) ((open - close) / (max - min) * divPureV);
	}

	protected void ScrollLeft(int step, int dateCount, double hScalePixel, int pureH, RefObject<Integer> fIndex,
			RefObject<Integer> lIndex) {
		int max = GetMaxVisibleCount(hScalePixel, pureH);
		int right = -1;
		if (dateCount > max) {
			right = max - (((Integer) lIndex.argvalue).intValue() - ((Integer) fIndex.argvalue).intValue());
			if (right > 1) {
				fIndex.argvalue = Integer.valueOf(((Integer) lIndex.argvalue).intValue() - max + 1);
				if (((Integer) fIndex.argvalue).intValue() > ((Integer) lIndex.argvalue).intValue()) {
					fIndex.argvalue = lIndex.argvalue;
				}

			} else if (((Integer) fIndex.argvalue).intValue() - step >= 0) {
				fIndex.argvalue = Integer.valueOf(((Integer) fIndex.argvalue).intValue() - step);
				lIndex.argvalue = Integer.valueOf(((Integer) lIndex.argvalue).intValue() - step);

			} else if (((Integer) fIndex.argvalue).intValue() != 0) {
				lIndex.argvalue = Integer
						.valueOf(((Integer) lIndex.argvalue).intValue() - ((Integer) fIndex.argvalue).intValue());
				fIndex.argvalue = Integer.valueOf(0);
			}
		}
	}

	protected void ScrollRight(int step, int dataCount, double hScalePixel, int pureH, RefObject<Integer> fIndex,
			RefObject<Integer> lIndex) {
		int max = GetMaxVisibleCount(hScalePixel, pureH);
		if (dataCount > max) {
			if (((Integer) lIndex.argvalue).intValue() < dataCount - 1) {
				if (((Integer) lIndex.argvalue).intValue() + step > dataCount - 1) {
					fIndex.argvalue = Integer.valueOf(((Integer) fIndex.argvalue).intValue() + dataCount
							- ((Integer) lIndex.argvalue).intValue());
					lIndex.argvalue = Integer.valueOf(dataCount - 1);
				} else {
					fIndex.argvalue = Integer.valueOf(((Integer) fIndex.argvalue).intValue() + step);
					lIndex.argvalue = Integer.valueOf(((Integer) lIndex.argvalue).intValue() + step);
				}
			} else {
				fIndex.argvalue = Integer.valueOf(((Integer) lIndex.argvalue).intValue() - (int) (max * 0.9D));
				if (((Integer) fIndex.argvalue).intValue() > ((Integer) lIndex.argvalue).intValue()) {
					fIndex.argvalue = lIndex.argvalue;
				}
			}
		}
	}

	protected double GetVScaleValue(int y, double max, double min, float vHeight) {
		double every = (max - min) / vHeight;
		return max - y * every;
	}

	protected void CorrectVisibleRecord(int dataCount, RefObject<Integer> first, RefObject<Integer> last) {
		if (dataCount > 0) {
			if (((Integer) first.argvalue).intValue() == -1) {
				first.argvalue = Integer.valueOf(0);
			}
			if (((Integer) last.argvalue).intValue() == -1) {
				last.argvalue = Integer.valueOf(0);
			}
			if (((Integer) first.argvalue).intValue() > ((Integer) last.argvalue).intValue()) {
				first.argvalue = last.argvalue;
			}
			if (((Integer) last.argvalue).intValue() < ((Integer) first.argvalue).intValue()) {
				last.argvalue = first.argvalue;
			}
		} else {
			first.argvalue = Integer.valueOf(-1);
			last.argvalue = Integer.valueOf(-1);
		}
	}

	protected int ResetCrossOverIndex(int dataCount, int maxVisibleRecord, int crossStopIndex, int firstL, int lastL) {
		if ((dataCount > 0) && (dataCount >= maxVisibleRecord)) {
			if (crossStopIndex < firstL) {
				crossStopIndex = firstL;
			}
			if (crossStopIndex > lastL) {
				crossStopIndex = lastL;
			}
		}
		return crossStopIndex;
	}

	protected void ZoomIn(int pureH, int dataCount, RefObject<Integer> findex, RefObject<Integer> lindex,
			RefObject<Double> hScalePixel) {
		int max = -1;
		if (((Double) hScalePixel.argvalue).doubleValue() > 1.0D) {
			RefObject<Double> localRefObject = hScalePixel;
			localRefObject.argvalue = Double.valueOf(((Double) localRefObject.argvalue).doubleValue() - 2.0D);
		} else {
			hScalePixel.argvalue = Double.valueOf(((Double) hScalePixel.argvalue).doubleValue() * 2.0D / 3.0D);
		}
		max = GetMaxVisibleCount(((Double) hScalePixel.argvalue).doubleValue(), pureH);
		if (max >= dataCount) {
			if (((Double) hScalePixel.argvalue).doubleValue() < 1.0D) {
				hScalePixel.argvalue = Double.valueOf(pureH / max);
			}
			findex.argvalue = Integer.valueOf(0);
			lindex.argvalue = Integer.valueOf(dataCount - 1);
		} else {
			findex.argvalue = Integer.valueOf(((Integer) lindex.argvalue).intValue() - max + 1);
			if (((Integer) findex.argvalue).intValue() < 0) {
				findex.argvalue = Integer.valueOf(0);
			}
		}
	}

	protected void ZoomOut(int pureH, int dataCount, RefObject<Integer> findex, RefObject<Integer> lindex,
			RefObject<Double> hScalePixel) {
		int oriMax = -1;
		int max = -1;
		int deal = 0;
		if (((Double) hScalePixel.argvalue).doubleValue() < 30.0D) {
			oriMax = GetMaxVisibleCount(((Double) hScalePixel.argvalue).doubleValue(), pureH);
			if (dataCount < oriMax) {
				deal = 1;
			}
			if (((Double) hScalePixel.argvalue).doubleValue() >= 1.0D) {
				RefObject<Double> localRefObject = hScalePixel;
				localRefObject.argvalue = Double.valueOf(((Double) localRefObject.argvalue).doubleValue() + 2.0D);
			} else {
				hScalePixel.argvalue = Double.valueOf(((Double) hScalePixel.argvalue).doubleValue() * 1.5D);
				if (((Double) hScalePixel.argvalue).doubleValue() > 1.0D) {
					hScalePixel.argvalue = Double.valueOf(1.0D);
				}
			}
			max = GetMaxVisibleCount(((Double) hScalePixel.argvalue).doubleValue(), pureH);
			if (dataCount >= max) {
				if (deal == 1) {
					lindex.argvalue = Integer.valueOf(dataCount - 1);
				}
				findex.argvalue = Integer.valueOf(((Integer) lindex.argvalue).intValue() - max + 1);
				if (((Integer) findex.argvalue).intValue() < 0) {
					findex.argvalue = Integer.valueOf(0);
				}
			}
		}
	}
}
