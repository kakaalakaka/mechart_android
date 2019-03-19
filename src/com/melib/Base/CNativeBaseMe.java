package com.melib.Base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CNativeBaseMe implements INativeBaseMe {
	protected void finalize() throws Throwable {
		Dispose();
	}

	protected ArrayList<CControlMe> m_controls = new ArrayList();
	protected POINT m_dragBeginPoint = new POINT();
	protected RECT m_dragBeginRect = new RECT();
	protected POINT m_dragStartOffset = new POINT();
	protected CControlMe m_draggingControl = null;
	protected CControlMe m_mouseDownControl = null;
	protected POINT m_mouseDownPoint = new POINT();
	protected CControlMe m_mouseMoveControl = null;
	protected HashMap<Integer, CControlMe> m_timers = new HashMap();
	protected boolean m_allowScaleSize = false;

	public boolean AllowScaleSize() {
		return this.m_allowScaleSize;
	}

	public void SetAllowScaleSize(boolean value) {
		this.m_allowScaleSize = value;
	}

	protected SIZE m_displaySize = new SIZE();

	public SIZE GetDisplaySize() {
		return this.m_displaySize;
	}

	public void SetDisplaySize(SIZE value) {
		this.m_displaySize = value;
	}

	protected CControlMe m_focusedControl = null;

	public CControlMe GetFocusedControl() {
		return this.m_focusedControl;
	}

	public void SetFocusedControl(CControlMe value) {
		if (this.m_focusedControl != value) {
			if (this.m_focusedControl != null) {
				CControlMe fControl = this.m_focusedControl;
				this.m_focusedControl = null;
				fControl.OnLostFocus();
			}
			this.m_focusedControl = value;
			if (this.m_focusedControl != null) {
				this.m_focusedControl.OnGotFocus();
			}
		}
	}

	protected CControlHostMe m_host = null;

	public CControlHostMe GetHost() {
		return this.m_host;
	}

	public void SetHost(CControlHostMe value) {
		this.m_host = value;
	}

	public CControlMe GetHoveredControl() {
		return this.m_mouseMoveControl;
	}

	protected boolean m_isDisposed = false;

	public boolean IsDisposed() {
		return this.m_isDisposed;
	}

	public POINT GetMousePoint() {
		if (this.m_host != null) {
			return this.m_host.GetMousePoint();
		}
		return new POINT(0, 0);
	}

	protected float m_opacity = 1.0F;

	public float GetOpacity() {
		return this.m_opacity;
	}

	public void SetOpacity(float value) {
		this.m_opacity = value;
	}

	protected CPaintMe m_paint = null;

	public CPaintMe GetPaint() {
		return this.m_paint;
	}

	public void SetPaint(CPaintMe value) {
		this.m_paint = value;
	}

	protected String m_resourcePath = "";

	public CControlMe GetPushedControl() {
		return this.m_mouseDownControl;
	}

	public String GetResourcePath() {
		return this.m_resourcePath;
	}

	public void SetResourcePath(String value) {
		this.m_resourcePath = value;
	}

	protected int m_rotateAngle = 0;

	public int GetRotateAngle() {
		return this.m_rotateAngle;
	}

	public void SetRotateAngle(int value) {
		this.m_rotateAngle = value;
	}

	protected SIZE m_scaleSize = new SIZE();

	public SIZE GetScaleSize() {
		return this.m_scaleSize.Clone();
	}

	public void SetScaleSize(SIZE value) {
		this.m_scaleSize = value.Clone();
	}

	public void AddControl(CControlMe control) {
		control.SetNative(this);
		this.m_controls.add(control);
		control.OnAdd();
	}

	public void BringToFront(CControlMe control) {
		CControlMe parent = control.GetParent();
		if (parent != null) {
			parent.BringChildToFront(control);

		} else if ((this.m_controls != null) && (this.m_controls.size() > 0)) {
			this.m_controls.remove(control);
			this.m_controls.add(control);
		}
	}

	public void CancelDragging() {
		if (this.m_draggingControl != null) {
			this.m_draggingControl.SetBounds(this.m_dragBeginRect);
			CControlMe draggingControl = this.m_draggingControl;
			this.m_draggingControl = null;
			draggingControl.OnDragEnd();
			CControlMe parent = draggingControl.GetParent();
			if (parent != null) {
				parent.Invalidate();
			} else {
				Invalidate();
			}
		}
	}

	public void ClearControls() {
		ArrayList<CControlMe> controls = new ArrayList();
		for (CControlMe control : this.m_controls) {
			controls.add(control);
		}
		for (CControlMe control : controls) {
			control.OnRemove();
			control.Dispose();
		}
		this.m_controls.clear();
	}

	public int ClientX(CControlMe control) {
		if (control != null) {
			CControlMe parent = control.GetParent();
			int cLeft = control.GetLeft();
			if (parent != null) {
				return cLeft - (control.DisplayOffset() ? parent.GetDisplayOffset().x : 0) + ClientX(parent);
			}

			return cLeft;
		}

		return 0;
	}

	public int ClientY(CControlMe control) {
		if (control != null) {
			CControlMe parent = control.GetParent();
			int cTop = control.GetTop();
			if (parent != null) {
				return cTop - (control.DisplayOffset() ? parent.GetDisplayOffset().y : 0) + ClientY(parent);
			}

			return cTop;
		}

		return 0;
	}

	public boolean ContainsControl(CControlMe control) {
		for (CControlMe subControl : this.m_controls) {
			if (subControl == control) {
				return true;
			}
		}
		return false;
	}

	public void Dispose() {
		if (!this.m_isDisposed) {
			this.m_focusedControl = null;
			this.m_timers.clear();
			ClearControls();
			this.m_isDisposed = true;
			if (this.m_paint != null) {
				this.m_paint.Dispose();
				this.m_paint = null;
			}
			this.m_host = null;
		}
	}

	protected CControlMe FindControl(POINT mp, ArrayList<CControlMe> controls) {
		int size = controls.size();
		for (int i = size - 1; i >= 0; i--) {
			CControlMe control = (CControlMe) controls.get(i);
			if (control.IsVisible()) {
				if (control.ContainsPoint(mp)) {
					ArrayList<CControlMe> subControls = new ArrayList();
					GetSortedControls(control, subControls);
					CControlMe subControl = FindControl(mp.Clone(), subControls);
					subControls.clear();
					if (subControl != null) {
						return subControl;
					}
					return control;
				}
			}
		}
		return null;
	}

	protected CControlMe FindControl(String name, ArrayList<CControlMe> controls) {
		int controlSize = controls.size();
		for (int i = 0; i < controlSize; i++) {
			CControlMe control = (CControlMe) controls.get(i);
			if (control.GetName().equals(name)) {
				return control;
			}

			ArrayList<CControlMe> subControls = control.GetControls();
			if ((subControls != null) && (subControls.size() > 0)) {
				CControlMe findControl = FindControl(name, subControls);
				if (findControl != null) {
					return findControl;
				}
			}
		}

		return null;
	}

	public CControlMe FindControl(POINT mp) {
		ArrayList<CControlMe> subControls = new ArrayList();
		GetSortedControls(null, subControls);
		CControlMe control = FindControl(mp.Clone(), subControls);
		subControls.clear();
		return control;
	}

	public CControlMe FindControl(POINT mp, CControlMe parent) {
		ArrayList<CControlMe> subControls = new ArrayList();
		GetSortedControls(parent, subControls);
		CControlMe control = FindControl(mp.Clone(), subControls);
		subControls.clear();
		return control;
	}

	public CControlMe FindControl(String name) {
		return FindControl(name, this.m_controls);
	}

	protected CControlMe FindPreviewsControl(CControlMe control) {
		if (control.AllowPreviewsEvent()) {
			return control;
		}

		CControlMe parent = control.GetParent();
		if (parent != null) {
			return FindPreviewsControl(parent);
		}

		return control;
	}

	protected CControlMe FindWindow(CControlMe control) {
		if (control.IsWindow()) {
			return control;
		}

		CControlMe parent = control.GetParent();
		if (parent != null) {
			return FindWindow(parent);
		}

		return control;
	}

	public ArrayList<CControlMe> GetControls() {
		return this.m_controls;
	}

	protected CControlMe GetFocusedControl(ArrayList<CControlMe> controls) {
		int controlSize = controls.size();
		for (int i = 0; i < controlSize; i++) {
			CControlMe control = (CControlMe) controls.get(i);
			if (control.IsFocused()) {
				return control;
			}

			ArrayList<CControlMe> subControls = control.GetControls();
			if ((subControls != null) && (subControls.size() > 0)) {
				CControlMe focusedControl = GetFocusedControl(subControls);
				if (focusedControl != null) {
					return focusedControl;
				}
			}
		}

		return null;
	}

	protected float GetPaintingOpacity(CControlMe control) {
		float opacity = control.GetOpacity();
		CControlMe parent = control.GetParent();
		if (parent != null) {
			opacity *= GetPaintingOpacity(parent);
		} else {
			opacity *= this.m_opacity;
		}
		return opacity;
	}

	protected String GetPaintingResourcePath(CControlMe control) {
		String resourcePath = control.GetResourcePath();
		if ((resourcePath != null) && (resourcePath.length() > 0)) {
			return resourcePath;
		}

		CControlMe parent = control.GetParent();
		if (parent != null) {
			return GetPaintingResourcePath(parent);
		}

		return this.m_resourcePath;
	}

	protected boolean GetSortedControls(CControlMe parent, ArrayList<CControlMe> sortedControls) {
		ArrayList<CControlMe> controls = null;
		if (parent != null) {
			controls = parent.GetControls();
		} else {
			controls = this.m_controls;
		}
		int controlSize = controls.size();
		int index = 0;
		for (int i = 0; i < controlSize; i++) {
			CControlMe control = (CControlMe) controls.get(i);
			if (control.IsVisible()) {
				if (control.IsTopMost()) {
					sortedControls.add(control);
				} else {
					sortedControls.add(index, control);
					index++;
				}
			}
		}
		return sortedControls.size() > 0;
	}

	protected boolean IsPaintEnabled(CControlMe control) {
		if (control.IsEnabled()) {
			CControlMe parent = control.GetParent();
			if (parent != null) {
				return IsPaintEnabled(parent);
			}

			return true;
		}

		return false;
	}

	public void InsertControl(int index, CControlMe control) {
		this.m_controls.add(index, control);
	}

	public void Invalidate() {
		if (this.m_host != null) {
			this.m_host.Invalidate();
		}
	}

	public void Invalidate(CControlMe control) {
		if (this.m_host != null) {
			int clientX = ClientX(control);
			int clientY = ClientY(control);
			this.m_host.Invalidate(
					new RECT(clientX, clientY, clientX + control.GetWidth(), clientY + control.GetHeight()));
		}
	}

	public void Invalidate(RECT rect) {
		if (this.m_host != null) {
			this.m_host.Invalidate(rect);
		}
	}

	public void OnPaint(RECT clipRect) {
		ArrayList<CControlMe> subCotrols = new ArrayList();
		GetSortedControls(null, subCotrols);
		RenderControls(clipRect, subCotrols, this.m_resourcePath, this.m_opacity);
		subCotrols.clear();
	}

	public boolean OnPreviewMouseEvent(int eventID, CControlMe control, POINT mp, MouseButtonsA button, int clicks,
			int delta) {
		CControlMe preControl = FindPreviewsControl(control);
		if (preControl != null) {
			int cLeft = ClientX(preControl);
			int ctop = ClientY(preControl);
			POINT newMp = new POINT(mp.x - cLeft, mp.y - ctop);
			if (preControl.OnPreviewsMouseEvent(eventID, newMp, button, clicks, delta)) {
				return true;
			}
		}
		return false;
	}

	public void OnResize() {
		Update();
	}

	public void OnTimer(int timerID) {
		if (this.m_timers.containsKey(Integer.valueOf(timerID))) {
			((CControlMe) this.m_timers.get(Integer.valueOf(timerID))).OnTimer(timerID);
		}
	}

	public void OnTouchBegin(ArrayList<CTouch> touches) {
		int touchesSize = touches.size();
		this.m_draggingControl = null;
		this.m_mouseDownControl = null;
		POINT mp = GetMousePoint();
		this.m_mouseDownPoint = mp;
		ArrayList<CControlMe> subControls = new ArrayList();
		if (!GetSortedControls(null, subControls)) {
			subControls = this.m_controls;
		}
		CControlMe control = FindControl(mp.Clone(), subControls);
		if (control != null) {
			if (touchesSize == 1) {
				CControlMe window = FindWindow(control);
				if ((window != null) && (window.IsWindow())) {
					window.BringToFront();
				}
			}
			if (IsPaintEnabled(control)) {
				int clientX = ClientX(control);
				int clientY = ClientY(control);
				POINT cmp = new POINT(mp.x - clientX, mp.y - clientY);
				CControlMe focusedControl = GetFocusedControl();
				this.m_mouseDownControl = control;
				this.m_mouseDownControl.OnTouchBegin(touches);
				if (touchesSize == 1) {
					if (focusedControl == GetFocusedControl()) {
						if (control.CanFocus()) {
							SetFocusedControl(control);
						} else {
							SetFocusedControl(null);
						}
					}
					if (OnPreviewMouseEvent(EVENTID.MOUSEDOWN, this.m_mouseDownControl, mp, MouseButtonsA.Left, 1, 0)) {
						return;
					}
					this.m_mouseDownControl.OnMouseDown(cmp.Clone(), MouseButtonsA.Left, 1, 0);
					if (this.m_mouseDownControl != null) {
						RefObject<POINT> refPoint = new RefObject(this.m_dragStartOffset);
						this.m_mouseDownControl.OnDragReady(refPoint);
					}
				}
			}
		}
	}

	public void OnTouchCancel(ArrayList<CTouch> touches) {
		this.m_draggingControl = null;
		this.m_mouseDownControl = null;
		this.m_mouseMoveControl = null;
		Invalidate();
	}

	public void OnTouchEnd(ArrayList<CTouch> touches) {
		int touchesSize = touches.size();
		POINT mp = GetMousePoint();
		if (this.m_mouseDownControl != null) {
			this.m_mouseDownControl.OnTouchEnd(touches);
			POINT cmp = new POINT(mp.x - ClientX(this.m_mouseDownControl), mp.y - ClientY(this.m_mouseDownControl));
			if (touchesSize == 1) {
				CControlMe mouseDownControl = this.m_mouseDownControl;
				if (OnPreviewMouseEvent(EVENTID.MOUSEUP, mouseDownControl, mp, MouseButtonsA.Left, 1, 0)) {
					return;
				}
				if (this.m_mouseDownControl != null) {
					ArrayList<CControlMe> subControls = new ArrayList();
					if (!GetSortedControls(null, subControls)) {
						subControls = this.m_controls;
					}
					CControlMe control = FindControl(mp.Clone(), subControls);
					if ((control != null) && (control == this.m_mouseDownControl)) {
						this.m_mouseDownControl.OnClick(cmp.Clone(), MouseButtonsA.Left, 1, 0);
					} else {
						this.m_mouseMoveControl = null;
					}
					if (this.m_mouseDownControl != null) {
						mouseDownControl = this.m_mouseDownControl;
						this.m_mouseDownControl = null;
						mouseDownControl.OnMouseUp(cmp, MouseButtonsA.Left, 1, 0);
					}
				}
			}
		} else if (this.m_draggingControl != null) {
			this.m_draggingControl.OnTouchEnd(touches);
			if (touchesSize == 1) {
				ArrayList<CControlMe> subControls = new ArrayList();
				if (!GetSortedControls(null, subControls)) {
					subControls = this.m_controls;
				}
				POINT cmp = new POINT(mp.x - ClientX(this.m_mouseDownControl), mp.y - ClientY(this.m_mouseDownControl));
				CControlMe draggingControl = this.m_draggingControl;
				this.m_draggingControl = null;
				if (OnPreviewMouseEvent(EVENTID.MOUSEUP, draggingControl, mp, MouseButtonsA.Left, 1, 0)) {
					return;
				}
				draggingControl.OnMouseUp(cmp.Clone(), MouseButtonsA.Left, 1, 0);
				draggingControl.OnDragEnd();
				CControlMe parent = draggingControl.GetParent();
				if (parent != null) {
					parent.Invalidate();
				} else {
					Invalidate();
				}
			}
		}
	}

	public void OnTouchMove(ArrayList<CTouch> touches) {
		int touchesSize = touches.size();
		POINT mp = GetMousePoint();
		if (this.m_mouseDownControl != null) {
			this.m_mouseDownControl.OnTouchMove(touches);
			if (touchesSize == 1) {
				if (OnPreviewMouseEvent(EVENTID.MOUSEMOVE, this.m_mouseDownControl, mp, MouseButtonsA.Left, 1, 0)) {
					return;
				}
				POINT cmp = new POINT(mp.x - ClientX(this.m_mouseDownControl), mp.y - ClientY(this.m_mouseDownControl));
				this.m_mouseDownControl.OnMouseMove(cmp.Clone(), MouseButtonsA.Left, 1, 0);
				if (this.m_mouseDownControl.AllowDrag()) {
					if ((Math.abs(mp.x - this.m_mouseDownPoint.x) > this.m_dragStartOffset.x)
							|| (Math.abs(mp.y - this.m_mouseDownPoint.y) > this.m_dragStartOffset.y)) {

						if (this.m_mouseDownControl.OnDragBegin()) {
							this.m_dragBeginRect = this.m_mouseDownControl.GetBounds();
							this.m_dragBeginPoint = this.m_mouseDownPoint.Clone();
							this.m_draggingControl = this.m_mouseDownControl;
							this.m_mouseDownControl = null;
							CControlMe parent = this.m_draggingControl.GetParent();
							if (parent != null) {
								parent.Invalidate();
							} else {
								Invalidate();
							}
						}
					}
				}
			}
		} else if (this.m_draggingControl != null) {
			this.m_draggingControl.OnTouchMove(touches);
			if (touchesSize == 1) {
				CControlMe draggingControl = this.m_draggingControl;
				int offsetX = mp.x - this.m_dragBeginPoint.x;
				int offsetY = mp.y - this.m_dragBeginPoint.y;
				RECT newBounds = this.m_dragBeginRect.Clone();
				newBounds.left += offsetX;
				newBounds.top += offsetY;
				newBounds.right += offsetX;
				newBounds.bottom += offsetY;
				draggingControl.SetBounds(newBounds);
				draggingControl.OnDragging();
				CControlMe parent = draggingControl.GetParent();
				if (parent != null) {
					parent.Invalidate();
				} else {
					Invalidate();
				}
			}
		}
	}

	protected void RenderControls(RECT rect, ArrayList<CControlMe> controls, String resourcePath, float opacity) {
		int controlSize = controls.size();
		for (int i = 0; i < controlSize; i++) {
			CControlMe control = (CControlMe) controls.get(i);
			control.OnPrePaint(this.m_paint, control.GetDisplayRect());
			RECT destRect = new RECT();
			int clientX = ClientX(control);
			int clientY = ClientY(control);
			RECT bounds = new RECT(clientX, clientY, clientX + control.GetWidth(), clientY + control.GetHeight());
			if (control.UseRegion()) {
				RECT clipRect = control.GetRegion();
				bounds.left += clipRect.left;
				bounds.top += clipRect.top;
				bounds.right = (bounds.left + clipRect.right - clipRect.left);
				bounds.bottom = (bounds.top + clipRect.bottom - clipRect.top);
			}
			RefObject<RECT> tempRef_destRect = new RefObject(destRect);
			RefObject<RECT> tempRef_rect = new RefObject(rect);
			RefObject<RECT> tempRef_bounds = new RefObject(bounds);
			boolean tempVar = (control.IsVisible())
					&& (this.m_host.GetIntersectRect(tempRef_destRect, tempRef_rect, tempRef_bounds) > 0);
			if (tempVar) {
				RECT clipRect = new RECT(destRect.left - clientX, destRect.top - clientY, destRect.right - clientX,
						destRect.bottom - clientY);
				String newResourcePath = control.GetResourcePath();
				if ((newResourcePath == null) || (newResourcePath.length() == 0)) {
					newResourcePath = resourcePath;
				}
				float newOpacity = control.GetOpacity() * opacity;
				SetPaint(clientX, clientY, clipRect, newResourcePath, newOpacity);
				control.OnPaint(this.m_paint, clipRect);
				ArrayList<CControlMe> subControls = new ArrayList();
				GetSortedControls(control, subControls);
				if ((subControls != null) && (subControls.size() > 0)) {
					RenderControls(destRect, subControls, newResourcePath, newOpacity);
					subControls.clear();
				}
				SetPaint(clientX, clientY, clipRect, newResourcePath, newOpacity);
				control.OnPaintBorder(this.m_paint, clipRect);
			}
		}
	}

	public void RemoveControl(CControlMe control) {
		if (control == this.m_draggingControl) {
			this.m_draggingControl = null;
		}
		if (control == this.m_focusedControl) {
			this.m_focusedControl = null;
		}
		if (control == this.m_mouseDownControl) {
			this.m_mouseDownControl = null;
		}
		if (control == this.m_mouseMoveControl) {
			this.m_mouseMoveControl = null;
		}
		ArrayList<Integer> removeTimers = new ArrayList();
		for (Iterator i$ = this.m_timers.keySet().iterator(); i$.hasNext();) {
			int timerID = ((Integer) i$.next()).intValue();

			if (this.m_timers.get(Integer.valueOf(timerID)) == control) {
				removeTimers.add(Integer.valueOf(timerID));
			}
		}
		for (Iterator i$ = removeTimers.iterator(); i$.hasNext();) {
			int timerID = ((Integer) i$.next()).intValue();

			StopTimer(timerID);
		}
		if (control.GetParent() == null) {
			this.m_controls.remove(control);
			control.OnRemove();
		}
	}

	public void SendToBack(CControlMe control) {
		CControlMe parent = control.GetParent();
		if (parent != null) {
			parent.SendChildToBack(control);

		} else if ((this.m_controls != null) && (this.m_controls.size() > 0)) {
			this.m_controls.remove(control);
			this.m_controls.add(0, control);
		}
	}

	public void SetAlign(ArrayList<CControlMe> controls) {
		int controlSize = controls.size();
		for (int i = 0; i < controlSize; i++) {
			CControlMe control = (CControlMe) controls.get(i);
			if (control.DisplayOffset()) {
				SIZE parentSize = this.m_displaySize;
				CControlMe parent = control.GetParent();
				if (parent != null) {
					parentSize = parent.GetSize();
				}
				SIZE size = control.GetSize();
				PADDING margin = control.GetMargin();
				PADDING padding = new PADDING();
				if (parent != null) {
					padding = parent.GetPadding();
				}
				if (control.GetAlign() == HorizontalAlignA.Center) {
					control.SetLeft((parentSize.cx - size.cx) / 2);
				} else if (control.GetAlign() == HorizontalAlignA.Right) {
					control.SetLeft(parentSize.cx - size.cx - margin.right - padding.right);
				}
				if (control.GetVerticalAlign() == VerticalAlignA.Bottom) {
					control.SetTop(parentSize.cy - size.cy - margin.bottom - padding.bottom);
				} else if (control.GetVerticalAlign() == VerticalAlignA.Middle) {
					control.SetTop((parentSize.cy - size.cy) / 2);
				}
			}
		}
	}

	public void SetAnchor(ArrayList<CControlMe> controls, SIZE oldSize) {
		if ((oldSize.cx != 0) && (oldSize.cy != 0)) {
			int controlSize = controls.size();
			for (int i = 0; i < controlSize; i++) {
				CControlMe control = (CControlMe) controls.get(i);
				SIZE parentSize = this.m_displaySize;
				CControlMe parent = control.GetParent();
				if (parent != null) {
					parentSize = parent.GetSize();
				}
				ANCHOR anchor = control.GetAnchor();
				RECT bounds = control.GetBounds();
				if ((anchor.right) && (!anchor.left)) {
					bounds.left = (bounds.left + parentSize.cx - oldSize.cx);
				}
				if ((anchor.bottom) && (!anchor.top)) {
					bounds.top = (bounds.top + parentSize.cy - oldSize.cy);
				}
				if (anchor.right) {
					bounds.right = (bounds.right + parentSize.cx - oldSize.cx);
				}
				if (anchor.bottom) {
					bounds.bottom = (bounds.bottom + parentSize.cy - oldSize.cy);
				}
				control.SetBounds(bounds);
			}
		}
	}

	public void SetDock(ArrayList<CControlMe> controls) {
		int controlSize = controls.size();
		for (int i = 0; i < controlSize; i++) {
			CControlMe control = (CControlMe) controls.get(i);
			SIZE parentSize = this.m_displaySize.Clone();
			CControlMe parent = control.GetParent();
			DockStyleA dockStyle = control.GetDock();
			if (dockStyle != DockStyleA.None) {
				PADDING padding = new PADDING();
				if (parent != null) {
					padding = parent.GetPadding();
				}
				PADDING margin = control.GetMargin();
				SIZE cSize = control.GetSize();
				RECT spaceRect = new RECT();
				spaceRect.left = (padding.left + margin.left);
				spaceRect.top = (padding.top + margin.top);
				spaceRect.right = (parentSize.cx - padding.right - margin.right);
				spaceRect.bottom = (parentSize.cy - padding.bottom - margin.bottom);
				RECT bounds = new RECT();
				if (dockStyle == DockStyleA.Bottom) {
					bounds.left = spaceRect.left;
					bounds.top = (spaceRect.bottom - cSize.cy);
					bounds.right = spaceRect.right;
					bounds.bottom = spaceRect.bottom;
				} else if (dockStyle == DockStyleA.Fill) {
					bounds = spaceRect;
				} else if (dockStyle == DockStyleA.Left) {
					bounds.left = spaceRect.left;
					bounds.top = spaceRect.top;
					bounds.right = (bounds.left + cSize.cx);
					bounds.bottom = spaceRect.bottom;
				} else if (dockStyle == DockStyleA.Right) {
					bounds.left = (spaceRect.right - cSize.cx);
					bounds.top = spaceRect.top;
					bounds.right = spaceRect.right;
					bounds.bottom = spaceRect.bottom;
				} else if (dockStyle == DockStyleA.Top) {
					bounds.left = spaceRect.left;
					bounds.top = spaceRect.top;
					bounds.right = spaceRect.right;
					bounds.bottom = (bounds.top + cSize.cy);
				}
				control.SetBounds(bounds);
			}
		}
	}

	protected void SetPaint(int offsetX, int offsetY, RECT clipRect, String resourcePath, float opacity) {
		this.m_paint.SetOffset(new POINT(offsetX, offsetY));
		this.m_paint.SetClip(clipRect);
		this.m_paint.SetResourcePath(resourcePath);
		this.m_paint.SetOpacity(opacity);
	}

	public void StartTimer(CControlMe control, int timerID, int interval) {
		this.m_timers.put(Integer.valueOf(timerID), control);
		if (this.m_host != null) {
			this.m_host.StartTimer(timerID, interval);
		}
	}

	public void StopTimer(int timerID) {
		if (this.m_timers.containsKey(Integer.valueOf(timerID))) {
			if (this.m_host != null) {
				this.m_host.StopTimer(timerID);
			}
			this.m_timers.remove(Integer.valueOf(timerID));
		}
	}

	public void Update() {
		if (this.m_host != null) {
			SIZE oldSize = this.m_displaySize.Clone();
			this.m_displaySize = this.m_host.GetSize();
			if ((this.m_displaySize.cx != 0) && (this.m_displaySize.cy != 0)) {
				SetAlign(this.m_controls);
				SetAnchor(this.m_controls, oldSize);
				SetDock(this.m_controls);
				int controlsSize = this.m_controls.size();
				for (int i = 0; i < controlsSize; i++) {
					((CControlMe) this.m_controls.get(i)).Update();
				}
			}
		}
	}
}
