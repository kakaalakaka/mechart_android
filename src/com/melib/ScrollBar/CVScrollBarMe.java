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

public class CVScrollBarMe extends CScrollBarMe {
	public void CallControlMouseEvent(int eventID, Object sender, POINT mp, MouseButtonsA button, int clicks,
			int delta) {
		super.CallControlMouseEvent(eventID, sender, mp, button, clicks, delta);
		if (sender == GetBackButton()) {
			if (eventID == EVENTID.MOUSEDOWN) {
				OnBackButtonMouseDown(mp, button, clicks, delta);
			} else if (eventID == EVENTID.MOUSEUP) {
				OnBackButtonMouseUp(mp, button, clicks, delta);
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
		return "VScrollBar";
	}

	public void OnDragScroll() {
		boolean flag = false;
		CButtonMe backButton = GetBackButton();
		CButtonMe scrollButton = GetScrollButton();
		int height = backButton.GetHeight();
		int contentSize = GetContentSize();
		if (scrollButton.GetBottom() > height) {
			flag = true;
		}
		super.OnDragScroll();
		if (flag) {
			SetPos(contentSize);
		} else {
			SetPos((int) (contentSize * scrollButton.GetTop() / backButton.GetHeight()));
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
		if (mp.y < scrollButton.GetTop()) {
			PageReduce();
			SetIsReducing(true);
		} else if (mp.y > scrollButton.GetBottom()) {
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
			int abHeight = addButton.IsVisible() ? addButton.GetHeight() : 0;
			addButton.SetSize(new SIZE(width, abHeight));
			addButton.SetLocation(new POINT(0, height - abHeight));
			int rbHeight = reduceButton.IsVisible() ? reduceButton.GetHeight() : 0;
			reduceButton.SetSize(new SIZE(width, rbHeight));
			reduceButton.SetLocation(new POINT(0, 0));
			int backWidth = height - abHeight - rbHeight;
			backButton.SetSize(new SIZE(width, backWidth));
			backButton.SetLocation(new POINT(0, rbHeight));
			int scrollWidth = backWidth * pageSize / contentSize;
			int scrollPos = (int) (backWidth * pos / contentSize);
			if (scrollWidth < 10) {
				scrollWidth = 10;
				if (scrollPos + scrollWidth > backWidth) {
					scrollPos = backWidth - scrollWidth;
				}
			}
			scrollButton.SetSize(new SIZE(width, scrollWidth));
			scrollButton.SetLocation(new POINT(0, scrollPos));
		}
	}
}
