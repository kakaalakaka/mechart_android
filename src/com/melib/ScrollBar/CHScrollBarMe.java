package com.melib.ScrollBar;

import com.melib.Base.EVENTID;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.SIZE;
import com.melib.Button.CButtonMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CHScrollBarMe extends CScrollBarMe {
	public void CallControlMouseEvent(int eventID, Object sender, POINT mp, MouseButtonsA button, int clicks,
			int delta) {
		super.CallControlMouseEvent(eventID, sender, mp.Clone(), button, clicks, delta);
		if (sender == GetBackButton()) {
			if (eventID == EVENTID.MOUSEDOWN) {
				OnBackButtonMouseDown(mp.Clone(), button, clicks, delta);
			} else if (eventID == EVENTID.MOUSEUP) {
				OnBackButtonMouseUp(mp.Clone(), button, clicks, delta);
			}
		}
	}

	public void Dispose() {
		if (!IsDisposed()) {
			CButtonMe button = GetBackButton();
			if (button != null) {
				button.UnRegisterEvent(this, EVENTID.MOUSEDOWN);
				button.UnRegisterEvent(this, EVENTID.MOUSEUP);
			}
		}
		super.Dispose();
	}

	public String GetControlType() {
		return "HScrollBar";
	}

	public void OnDragScroll() {
		boolean flag = false;
		CButtonMe backButton = GetBackButton();
		CButtonMe scrollButton = GetScrollButton();
		int width = backButton.GetWidth();
		int contentSize = GetContentSize();
		if (scrollButton.GetRight() > width) {
			flag = true;
		}
		super.OnDragScroll();
		if (flag) {
			SetPos(contentSize);
		} else {
			SetPos((int) (contentSize * scrollButton.GetLeft() / width));
		}
		OnScrolled();
	}

	public void OnLoad() {
		boolean flag = false;
		CButtonMe backButton = GetBackButton();
		if (backButton != null) {
			flag = true;
		}
		super.OnLoad();
		if (!flag) {
			backButton = GetBackButton();
			backButton.RegisterEvent(this, EVENTID.MOUSEDOWN);
			backButton.RegisterEvent(this, EVENTID.MOUSEUP);
		}
	}

	public void OnBackButtonMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		CButtonMe scrollButton = GetScrollButton();
		if (mp.x < scrollButton.GetLeft()) {
			PageReduce();
			SetIsReducing(true);
		} else if (mp.x > scrollButton.GetRight()) {
			PageAdd();
			SetIsAdding(true);
		}
	}

	public void OnBackButtonMouseUp(POINT mp, MouseButtonsA button, int clicks, int delta) {
		SetIsAdding(false);
		SetIsReducing(false);
	}

	public void Update() {
		if (GetNative() == null) {
			return;
		}
		CButtonMe addButton = GetAddButton();
		CButtonMe backButton = GetBackButton();
		CButtonMe reduceButton = GetReduceButton();
		CButtonMe scrollButton = GetScrollButton();
		int width = GetWidth();
		int height = GetHeight();
		int contentSize = GetContentSize();

		if ((contentSize > 0) && (addButton != null) && (backButton != null) && (reduceButton != null)
				&& (scrollButton != null)) {
			int pos = GetPos();
			int pageSize = GetPageSize();
			if (pos > contentSize - pageSize) {
				pos = contentSize - pageSize;
			}
			if (pos < 0) {
				pos = 0;
			}
			int visible = addButton.IsVisible() ? addButton.GetWidth() : 0;
			addButton.SetSize(new SIZE(visible, height));
			addButton.SetLocation(new POINT(width - visible, 0));
			int rbWidth = reduceButton.IsVisible() ? reduceButton.GetWidth() : 0;
			reduceButton.SetSize(new SIZE(rbWidth, height));
			reduceButton.SetLocation(new POINT(0, 0));
			int backWidth = width - visible - rbWidth;
			backButton.SetSize(new SIZE(backWidth, height));
			backButton.SetLocation(new POINT(rbWidth, 0));
			int scrollWidth = backWidth * pageSize / contentSize;
			int scrollPos = backWidth * pos / contentSize;
			if (scrollWidth < 10) {
				scrollWidth = 10;
				if (scrollPos + scrollWidth > backWidth) {
					scrollPos = backWidth - scrollWidth;
				}
			}
			scrollButton.SetSize(new SIZE(scrollWidth, height));
			scrollButton.SetLocation(new POINT(scrollPos, 0));
		}
	}
}
