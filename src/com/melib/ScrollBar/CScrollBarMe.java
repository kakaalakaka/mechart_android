package com.melib.ScrollBar;

import com.melib.Base.CControlMe;
import com.melib.Base.ControlEvent;
import com.melib.Base.CControlHostMe;
import com.melib.Base.ControlMouseEvent;
import com.melib.Base.EVENTID;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.SIZE;
import com.melib.Button.CButtonMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CScrollBarMe extends CControlMe implements ControlEvent, ControlMouseEvent {
	private int m_tick;
	private int m_addSpeed;

	public CScrollBarMe() {
		SetCanFocus(false);
		SetDisplayOffset(false);
		SIZE size = new SIZE(10, 10);
		SetSize(size);
		SetTopMost(true);
	}

	public int GetAddSpeed() {
		return this.m_addSpeed;
	}

	public void SetAddSpeed(int addSpeed) {
		this.m_addSpeed = addSpeed;
		if (this.m_addSpeed != 0) {
			StartTimer(this.m_timerID, 10);
		} else {
			StopTimer(this.m_timerID);
		}
	}

	private boolean m_isAdding = false;

	public boolean IsAdding() {
		return this.m_isAdding;
	}

	public void SetIsAdding(boolean value) {
		if (this.m_isAdding != value) {
			this.m_isAdding = value;
			this.m_tick = 0;
			if (this.m_isAdding) {
				StartTimer(this.m_timerID, 100);
			} else {
				StopTimer(this.m_timerID);
			}
		}
	}

	private boolean m_isReducing = false;

	public boolean IsReducing() {
		return this.m_isReducing;
	}

	public void SetIsReducing(boolean value) {
		if (this.m_isReducing != value) {
			this.m_isReducing = value;
			this.m_tick = 0;
			if (this.m_isReducing) {
				StartTimer(this.m_timerID, 100);
			} else {
				StopTimer(this.m_timerID);
			}
		}
	}

	private int m_timerID = GetNewTimerID();
	private CButtonMe m_addButton = null;

	public CButtonMe GetAddButton() {
		return this.m_addButton;
	}

	private CButtonMe m_backButton = null;

	public CButtonMe GetBackButton() {
		return this.m_backButton;
	}

	private int m_contentSize = 0;

	public int GetContentSize() {
		return this.m_contentSize;
	}

	public void SetContentSize(int value) {
		this.m_contentSize = value;
	}

	private int m_lineSize = 10;
	private int m_pageSize;
	private int m_pos;

	public int GetLineSize() {
		return this.m_lineSize;
	}

	public void SetLineSize(int value) {
		this.m_lineSize = value;
	}

	public int GetPageSize() {
		return this.m_pageSize;
	}

	public void SetPageSize(int value) {
		this.m_pageSize = value;
	}

	public int GetPos() {
		return this.m_pos;
	}

	public void SetPos(int value) {
		this.m_pos = value;
		if (this.m_pos > this.m_contentSize - this.m_pageSize) {
			this.m_pos = (this.m_contentSize - this.m_pageSize);
		}
		if (this.m_pos < 0) {
			this.m_pos = 0;
		}
	}

	private CButtonMe m_reduceButton = null;

	public CButtonMe GetReduceButton() {
		return this.m_reduceButton;
	}

	private CButtonMe m_scrollButton = null;

	public CButtonMe GetScrollButton() {
		return this.m_scrollButton;
	}

	public void CallControlEvent(int eventID, Object sender) {
		if (sender == this.m_scrollButton) {
			if (eventID == EVENTID.DRAGGING) {
				OnDragScroll();
			}
		}
	}

	public void CallControlMouseEvent(int eventID, Object sender, POINT mp, MouseButtonsA button, int clicks,
			int delta) {
		if (sender == this.m_addButton) {
			if (eventID == EVENTID.MOUSEDOWN) {
				OnAddButtonMouseDown(mp.Clone(), button, clicks, delta);
			} else if (eventID == EVENTID.MOUSEUP) {
				OnAddButtonMouseUp(mp.Clone(), button, clicks, delta);
			}
		} else if (sender == this.m_reduceButton) {
			if (eventID == EVENTID.MOUSEDOWN) {
				OnReduceButtonMouseDown(mp.Clone(), button, clicks, delta);
			} else if (eventID == EVENTID.MOUSEUP) {
				OnReduceButtonMouseUp(mp.Clone(), button, clicks, delta);
			}
		}
	}

	public void Dispose() {
		if (!IsDisposed()) {
			StopTimer(this.m_timerID);
			if (this.m_addButton != null) {
				this.m_addButton.UnRegisterEvent(this, EVENTID.MOUSEDOWN);
				this.m_addButton.UnRegisterEvent(this, EVENTID.MOUSEUP);
			}
			if (this.m_scrollButton != null) {
				this.m_scrollButton.UnRegisterEvent(this, EVENTID.DRAGGING);
			}
			if (this.m_reduceButton != null) {
				this.m_reduceButton.UnRegisterEvent(this, EVENTID.MOUSEDOWN);
				this.m_reduceButton.UnRegisterEvent(this, EVENTID.MOUSEUP);
			}
		}
		super.Dispose();
	}

	public String GetControlType() {
		return "ScrollBar";
	}

	public void LineAdd() {
		this.m_pos += this.m_lineSize;
		if (this.m_pos > this.m_contentSize - this.m_pageSize) {
			this.m_pos = (this.m_contentSize - this.m_pageSize);
		}
		Update();
		OnScrolled();
	}

	public void LineReduce() {
		this.m_pos -= this.m_lineSize;
		if (this.m_pos < 0) {
			this.m_pos = 0;
		}
		Update();
		OnScrolled();
	}

	public void OnAddButtonMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		LineAdd();
		SetIsAdding(true);
	}

	public void OnAddButtonMouseUp(POINT mp, MouseButtonsA button, int clicks, int delta) {
		SetIsAdding(false);
	}

	public void OnAddSpeedScrollEnd() {
	}

	public void OnAddSpeedScrollStart(long startTime, long nowTime, int start, int end) {
		int diff = (int) ((nowTime - startTime) / 1000000L);
		if ((diff > 0) && (diff < 800)) {
			int sub = 10000 * (Math.abs(start - end) / 20) / diff;
			if (start > end) {
				SetAddSpeed(GetAddSpeed() + sub);
			} else if (start < end) {
				SetAddSpeed(GetAddSpeed() - sub);
			}
		}
	}

	public int OnAddSpeedScrolling() {
		int num = this.m_addSpeed / 10;
		if (num == 0) {
			num = this.m_addSpeed > 0 ? 1 : -1;
		}
		return num;
	}

	public void OnDragScroll() {
		if (this.m_scrollButton.GetLeft() < 0) {
			this.m_scrollButton.SetLeft(0);
		}
		if (this.m_scrollButton.GetTop() < 0) {
			this.m_scrollButton.SetTop(0);
		}
		if (this.m_scrollButton.GetRight() > this.m_backButton.GetWidth()) {
			this.m_scrollButton.SetLeft(this.m_backButton.GetWidth() - this.m_scrollButton.GetWidth());
		}
		if (this.m_scrollButton.GetBottom() > this.m_backButton.GetHeight()) {
			this.m_scrollButton.SetTop(this.m_backButton.GetHeight() - this.m_scrollButton.GetHeight());
		}
	}

	public void OnLoad() {
		super.OnLoad();

		CControlHostMe host = GetNative().GetHost();
		if (this.m_addButton == null) {
			CControlMe control = host.CreateInternalControl(this, "addbutton");
			this.m_addButton = ((CButtonMe) ((control instanceof CButtonMe) ? control : null));
			this.m_addButton.RegisterEvent(this, EVENTID.MOUSEDOWN);
			this.m_addButton.RegisterEvent(this, EVENTID.MOUSEUP);
			AddControl(this.m_addButton);
		}
		if (this.m_backButton == null) {
			CControlMe control = host.CreateInternalControl(this, "backbutton");
			this.m_backButton = ((CButtonMe) ((control instanceof CButtonMe) ? control : null));
			AddControl(this.m_backButton);
		}
		if (this.m_reduceButton == null) {
			CControlMe control = host.CreateInternalControl(this, "reducebutton");
			this.m_reduceButton = ((CButtonMe) ((control instanceof CButtonMe) ? control : null));
			this.m_reduceButton.RegisterEvent(this, EVENTID.MOUSEDOWN);
			this.m_reduceButton.RegisterEvent(this, EVENTID.MOUSEUP);
			AddControl(this.m_reduceButton);
		}
		if (this.m_scrollButton == null) {
			CControlMe control = host.CreateInternalControl(this, "scrollbutton");
			this.m_scrollButton = ((CButtonMe) ((control instanceof CButtonMe) ? control : null));
			this.m_scrollButton.SetAllowDrag(true);
			this.m_scrollButton.RegisterEvent(this, EVENTID.DRAGGING);
			this.m_backButton.AddControl(this.m_scrollButton);
		}
	}

	public void OnReduceButtonMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		LineReduce();
		SetIsReducing(true);
	}

	public void OnReduceButtonMouseUp(POINT mp, MouseButtonsA button, int clicks, int delta) {
		SetIsReducing(false);
	}

	public void OnScrolled() {
		CallEvents(EVENTID.SCROLLED);
		CControlMe parentControl = GetParent();
		if (parentControl != null) {
			parentControl.Invalidate();
		}
	}

	public void OnTimer(int timerID) {
		super.OnTimer(timerID);
		if (this.m_timerID == timerID) {
			if (this.m_isAdding) {
				if (this.m_tick > 5) {
					PageAdd();
				} else {
					LineAdd();
				}
			}
			if (this.m_isReducing) {
				if (this.m_tick > 5) {
					PageReduce();
				} else {
					LineReduce();
				}
			}
			if (this.m_addSpeed != 0) {
				int speed = OnAddSpeedScrolling();
				SetPos(GetPos() + speed);
				Update();
				OnScrolled();
				this.m_addSpeed -= speed;
				if (Math.abs(this.m_addSpeed) < 3) {
					this.m_addSpeed = 0;
				}
				if (this.m_addSpeed == 0) {
					OnAddSpeedScrollEnd();
					StopTimer(this.m_timerID);
					if (GetParent() != null) {
						GetParent().Invalidate();
					}
				}
			}
			this.m_tick += 1;
		}
	}

	public void OnVisibleChanged() {
		super.OnVisibleChanged();
		if (!IsVisible()) {
			this.m_pos = 0;
		}
	}

	public void PageAdd() {
		this.m_pos += this.m_pageSize;
		if (this.m_pos > this.m_contentSize - this.m_pageSize) {
			this.m_pos = (this.m_contentSize - this.m_pageSize);
		}
		Update();
		OnScrolled();
	}

	public void PageReduce() {
		this.m_pos -= this.m_pageSize;
		if (this.m_pos < 0) {
			this.m_pos = 0;
		}
		Update();
		OnScrolled();
	}

	public void ReduceButtonMouseDown(Object sender, POINT mp, MouseButtonsA button, int clicks, int delta) {
		OnReduceButtonMouseDown(mp.Clone(), button, clicks, delta);
	}

	public void ReduceButtonMouseUp(Object sender, POINT mp, MouseButtonsA button, int clicks, int delta) {
		OnReduceButtonMouseUp(mp.Clone(), button, clicks, delta);
	}

	public void ScrollToBegin() {
		this.m_pos = 0;
		Update();
		OnScrolled();
	}

	public void ScrollToEnd() {
		this.m_pos = (this.m_contentSize - this.m_pageSize);
		if (this.m_pos < 0) {
			this.m_pos = 0;
		}
		Update();
		OnScrolled();
	}
}
