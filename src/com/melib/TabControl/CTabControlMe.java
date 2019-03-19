package com.melib.TabControl;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlMe;
import com.melib.Base.EVENTID;
import com.melib.Base.PADDING;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Button.CButtonMe;
import com.melib.Layout.CDivMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CTabControlMe extends CDivMe {
	protected int m_animationState;
	public ArrayList<CTabPageMe> m_tabPages = new ArrayList();

	private int m_timerID = GetNewTimerID();

	protected TabPageLayout m_layout = TabPageLayout.Top;

	public TabPageLayout GetLayout() {
		return this.m_layout;
	}

	public void SetLayout(TabPageLayout value) {
		this.m_layout = value;
	}

	protected int m_selectedIndex = -1;
	protected boolean m_useAnimation;

	public int GetSelectedIndex() {
		return this.m_selectedIndex;
	}

	public void SetSelectedIndex(int value) {
		int count = this.m_tabPages.size();
		if (count > 0) {
			if ((value >= 0) && (value < count)) {
				this.m_selectedIndex = value;
				SetSelectedTabPage((CTabPageMe) this.m_tabPages.get(value));
			}
		}
	}

	public CTabPageMe GetSelectedTabPage() {
		int count = this.m_tabPages.size();
		if (count > 0) {
			if ((this.m_selectedIndex >= 0) && (this.m_selectedIndex < count)) {
				return (CTabPageMe) this.m_tabPages.get(this.m_selectedIndex);
			}
		}
		return null;
	}

	public void SetSelectedTabPage(CTabPageMe value) {
		int index = -1;
		int tabPageSize = this.m_tabPages.size();
		if ((value != null) && (tabPageSize > 0)) {
			CTabPageMe oldSelectedTabPage = GetSelectedTabPage();
			for (int i = 0; i < tabPageSize; i++) {
				CTabPageMe tabPage = (CTabPageMe) this.m_tabPages.get(i);
				if (tabPage == value) {
					index = i;
					tabPage.SetVisible(true);
				} else {
					tabPage.SetVisible(false);
				}
			}
			if (index != -1) {
				this.m_selectedIndex = index;

			} else if (tabPageSize > 0) {
				this.m_selectedIndex = 0;
			}

			CTabPageMe newSelectedTabPage = GetSelectedTabPage();
			if (oldSelectedTabPage != newSelectedTabPage) {
				OnSelectedTabPageChanged();
			}
			newSelectedTabPage.BringToFront();
			newSelectedTabPage.GetHeaderButton().BringToFront();
		} else {
			this.m_selectedIndex = -1;
		}
	}

	public boolean UseAnimation() {
		return this.m_useAnimation;
	}

	public void SetUseAnimation(boolean value) {
		this.m_useAnimation = value;
		if (this.m_useAnimation) {
			StartTimer(this.m_timerID, 20);
		} else {
			StopTimer(this.m_timerID);
		}
	}

	public void AddControl(CControlMe control) {
		CTabPageMe item = (CTabPageMe) ((control instanceof CTabPageMe) ? control : null);
		if (item != null) {
			item.SetTabControl(this);
		}
		super.AddControl(control);
		if (item != null) {
			this.m_tabPages.add(item);
			SetSelectedTabPage(item);
		}
	}

	public void ClearControls() {
		this.m_tabPages.clear();
		this.m_selectedIndex = -1;
		super.ClearControls();
	}

	public void Dispose() {
		if (!IsDisposed()) {
			StopTimer(this.m_timerID);
		}
		super.Dispose();
	}

	protected void DrawMoving() {
		if (this.m_animationState > 0) {
			boolean drawing = false;
			int tabPageSize = this.m_tabPages.size();
			CTabPageMe selectedTabPage = GetSelectedTabPage();
			for (int i = 0; i < tabPageSize; i++) {
				CTabPageMe tabPage = (CTabPageMe) this.m_tabPages.get(i);
				if ((tabPage != selectedTabPage) || (this.m_animationState != 1)) {

					CButtonMe headerButton = tabPage.GetHeaderButton();
					if (headerButton.IsVisible()) {
						int moving = headerButton.GetLeft();
						int pos = tabPage.GetHeaderLocation().x;
						if ((this.m_layout == TabPageLayout.Left) || (this.m_layout == TabPageLayout.Right)) {
							pos = tabPage.GetHeaderLocation().y;
							moving = headerButton.GetTop();
						}
						if (pos != moving) {
							int relative = moving;
							int sub = Math.abs(pos - relative);
							int step = 20;
							if (this.m_useAnimation) {
								if (tabPage == selectedTabPage) {
									if (sub > 200) {
										step = sub / 200 * 100;
									}

								} else {
									step = sub;
								}

							} else {
								step = sub;
							}
							if (relative != pos) {
								if (pos > relative + step) {
									relative += step;
								} else if (pos < relative - step) {
									relative -= step;
								} else {
									relative = pos;
								}
								if ((this.m_layout == TabPageLayout.Left) || (this.m_layout == TabPageLayout.Right)) {
									headerButton.SetTop(relative);
								} else {
									headerButton.SetLeft(relative);
								}
								drawing = true;
							}
						}
					}
				}
			}
			if (!drawing) {
				if (this.m_animationState == 2) {
					this.m_animationState = 0;
				}
			}
			Update();
			Invalidate();
		}
	}

	public String GetControlType() {
		return "TabControl";
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("layout")) {
			type.argvalue = "enum:TabPageLayout";
			if (GetLayout() == TabPageLayout.Left) {
				value.argvalue = "Left";
			} else if (GetLayout() == TabPageLayout.Right) {
				value.argvalue = "Right";
			} else if (GetLayout() == TabPageLayout.Bottom) {
				value.argvalue = "Bottom";
			} else {
				value.argvalue = "Top";
			}
		} else if (name.equals("selectedindex")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetSelectedIndex());
		} else if (name.equals("useanimation")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(UseAnimation());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "Layout", "SelectedIndex", "UseAnimation" }));
		return propertyNames;
	}

	public ArrayList<CTabPageMe> GetTabPages() {
		return this.m_tabPages;
	}

	public void InsertControl(int index, CControlMe control) {
		CTabPageMe item = (CTabPageMe) ((control instanceof CTabPageMe) ? control : null);
		if (item != null) {
			item.SetTabControl(this);
		}
		super.AddControl(control);
		this.m_tabPages.add(index, item);
		SetSelectedTabPage(item);
	}

	public void OnDragTabHeaderBegin(CTabPageMe tabPage) {
		this.m_animationState = 1;
		tabPage.GetHeaderButton().BringToFront();
	}

	public void OnDragTabHeaderEnd(CTabPageMe tabPage) {
		if (this.m_animationState == 1) {
			this.m_animationState = 2;
			DrawMoving();
		}
	}

	public void OnDraggingTabHeader(CTabPageMe tabPage) {
		CButtonMe headerButton = tabPage.GetHeaderButton();
		int moving = headerButton.GetLeft();
		if ((this.m_layout == TabPageLayout.Left) || (this.m_layout == TabPageLayout.Right)) {
			moving = headerButton.GetTop();
		}
		int tabPageSize = this.m_tabPages.size();
		for (int i = 0; i < tabPageSize; i++) {
			CTabPageMe page = (CTabPageMe) this.m_tabPages.get(i);
			if (page != tabPage) {
				CButtonMe tpHeader = page.GetHeaderButton();
				if (tpHeader.IsVisible()) {
					int pos = page.GetHeaderLocation().x;
					int size = tpHeader.GetWidth();
					int sSize = headerButton.GetWidth();
					if ((this.m_layout == TabPageLayout.Left) || (this.m_layout == TabPageLayout.Right)) {
						pos = page.GetHeaderLocation().y;
						size = tpHeader.GetHeight();
						sSize = headerButton.GetHeight();
					}
					boolean instead = false;

					if (moving > pos) {
						if ((moving > pos) && (moving < pos + size / 2)) {
							instead = true;
						}
					}
					if (moving < pos) {
						if ((moving + sSize > pos + size / 2) && (moving + sSize < pos + size)) {
							instead = true;
						}
					}

					if (instead) {
						POINT headerLocation = tabPage.GetHeaderLocation();
						if ((this.m_layout == TabPageLayout.Left) || (this.m_layout == TabPageLayout.Right)) {
							tabPage.SetHeaderLocation(new POINT(tabPage.GetHeaderLocation().x, pos));
							page.SetHeaderLocation(new POINT(page.GetHeaderLocation().x, headerLocation.y));
						} else {
							tabPage.SetHeaderLocation(new POINT(pos, tabPage.GetHeaderLocation().y));
							page.SetHeaderLocation(new POINT(headerLocation.x, page.GetHeaderLocation().y));
						}
						int oIndex = this.m_tabPages.indexOf(tabPage);
						int nIndex = this.m_tabPages.indexOf(page);
						this.m_tabPages.set(oIndex, page);
						this.m_tabPages.set(nIndex, tabPage);
						this.m_selectedIndex = nIndex;
						break;
					}
				}
			}
		}
		DrawMoving();
	}

	public void OnLoad() {
		super.OnLoad();
		if (this.m_useAnimation) {
			StartTimer(this.m_timerID, 20);
		} else {
			StopTimer(this.m_timerID);
		}
	}

	public void OnSelectedTabPageChanged() {
		CallEvents(EVENTID.SELECTEDTABPAGECHANGED);
	}

	public void OnTimer(int timerID) {
		super.OnTimer(timerID);
		if (this.m_timerID == timerID) {
			DrawMoving();
		}
	}

	public void RemoveControl(CControlMe control) {
		CTabPageMe tabPage = (CTabPageMe) ((control instanceof CTabPageMe) ? control : null);
		if (tabPage != null) {
			int count = this.m_tabPages.size();
			if (count > 0) {
				CTabPageMe selectedTabPage = GetSelectedTabPage();
				if (selectedTabPage == tabPage) {
					if (this.m_selectedIndex > 0) {
						if (this.m_selectedIndex < count - 1) {
							selectedTabPage = (CTabPageMe) this.m_tabPages.get(this.m_selectedIndex + 1);
						} else {
							selectedTabPage = (CTabPageMe) this.m_tabPages.get(this.m_selectedIndex - 1);
						}

					} else if (count > 1) {
						selectedTabPage = (CTabPageMe) this.m_tabPages.get(this.m_selectedIndex + 1);
					}
				}

				this.m_tabPages.remove(tabPage);
				super.RemoveControl(tabPage.GetHeaderButton());
				super.RemoveControl(tabPage);
				tabPage.SetTabControl(null);
				SetSelectedTabPage(selectedTabPage);
			}
		} else {
			super.RemoveControl(control);
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("layout")) {
			value = value.toLowerCase();
			if (value.equals("left")) {
				SetLayout(TabPageLayout.Left);
			} else if (value.equals("top")) {
				SetLayout(TabPageLayout.Top);
			} else if (value.equals("right")) {
				SetLayout(TabPageLayout.Right);
			} else if (value.equals("bottom")) {
				SetLayout(TabPageLayout.Bottom);
			}
		} else if (name.equals("selectedindex")) {
			SetSelectedIndex(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("useanimation")) {
			SetUseAnimation(CStrMe.ConvertStrToBool(value));
		} else {
			super.SetProperty(name, value);
		}
	}

	public void Update() {
		if (GetNative() == null) {
			return;
		}
		super.Update();
		PADDING padding = GetPadding();
		int left = padding.left;
		int top = padding.top;
		int width = GetWidth() - padding.left - padding.right;
		int height = GetHeight() - padding.top - padding.bottom;
		int tabPageSize = this.m_tabPages.size();
		for (int i = 0; i < tabPageSize; i++) {
			CTabPageMe tabPage = (CTabPageMe) this.m_tabPages.get(i);
			CButtonMe headerButton = tabPage.GetHeaderButton();
			if (headerButton.IsVisible()) {
				PADDING margin = headerButton.GetMargin();
				int tw = headerButton.GetWidth() + margin.left + margin.right;
				int th = headerButton.GetHeight() + margin.top + margin.bottom;
				RECT bounds = new RECT();
				POINT headerLocation = new POINT();
				switch (this.m_layout) {
				case Bottom:
					bounds.left = padding.left;
					bounds.top = padding.top;
					bounds.right = width;
					bounds.bottom = (height - th);
					headerLocation.x = (left + margin.left);
					headerLocation.y = (height - th + margin.top);
					break;
				case Left:
					bounds.left = tw;
					bounds.top = padding.top;
					bounds.right = width;
					bounds.bottom = height;
					headerLocation.x = (padding.left + margin.left);
					headerLocation.y = (top + margin.top);
					break;
				case Right:
					bounds.left = padding.left;
					bounds.top = padding.top;
					bounds.right = (width - tw);
					bounds.bottom = height;
					headerLocation.x = (width - tw + margin.left);
					headerLocation.y = (top + margin.top);
					break;
				case Top:
					bounds.left = padding.left;
					bounds.top = th;
					bounds.right = width;
					bounds.bottom = height;
					headerLocation.x = (left + margin.left);
					headerLocation.y = (padding.top + margin.top);
				}

				tabPage.SetBounds(bounds);
				tabPage.SetHeaderLocation(headerLocation);
				if (this.m_animationState > 0) {
					if ((this.m_layout == TabPageLayout.Left) || (this.m_layout == TabPageLayout.Right)) {
						headerLocation.y = headerButton.GetTop();
					} else if ((this.m_layout == TabPageLayout.Bottom) || (this.m_layout == TabPageLayout.Top)) {
						headerLocation.x = headerButton.GetLeft();
					}
				}
				headerButton.SetLocation(headerLocation);
				left += tw;
				top += th;
			} else {
				tabPage.SetVisible(false);
			}
		}
	}
}
