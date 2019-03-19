package com.melib.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CStrMe;
import com.melib.Base.FONT;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;
import com.melib.Grid.CGridMe;
import com.melib.Grid.GridCellStyle;
import com.melib.Grid.CGridColumnMe;
import com.melib.Grid.CGridControlCellMe;
import com.melib.Grid.CGridRowMe;
import com.melib.Grid.GridRowStyle;
import com.melib.ScrollBar.CHScrollBarMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CTreeNodeMe extends CGridControlCellMe {
	protected void finalize() throws Throwable {
		this.m_nodes.clear();
	}

	public ArrayList<CTreeNodeMe> m_nodes = new ArrayList();

	protected String m_text;

	protected boolean m_allowDragIn = false;

	public boolean AllowDragIn() {
		return this.m_allowDragIn;
	}

	public void SetAllowDragIn(boolean value) {
		this.m_allowDragIn = value;
	}

	protected boolean m_allowDragOut = false;

	public boolean AllowDragOut() {
		return this.m_allowDragOut;
	}

	public void SetAllowDragOut(boolean value) {
		this.m_allowDragOut = value;
	}

	protected boolean m_checked = false;

	public boolean IsChecked() {
		return this.m_checked;
	}

	public void SetChecked(boolean value) {
		if (this.m_checked != value) {
			this.m_checked = value;
			CheckChildNodes(this.m_nodes, this.m_checked);
		}
	}

	protected boolean m_expended = true;

	public boolean IsExpended() {
		return this.m_expended;
	}

	public void SetExpended(boolean value) {
		this.m_expended = value;
	}

	protected CTreeNodeMe m_parent = null;
	protected CGridColumnMe m_targetColumn;
	protected int m_indent;

	public CTreeNodeMe GetParent() {
		return this.m_parent;
	}

	public void SetParent(CTreeNodeMe value) {
		this.m_parent = value;
	}

	public CGridColumnMe GetTargetColumn() {
		return this.m_targetColumn;
	}

	public void SetTargetColumn(CGridColumnMe value) {
		this.m_targetColumn = value;
	}

	public int GetIndent() {
		return this.m_indent;
	}

	protected CTreeMe m_tree = null;
	protected String m_value;

	public CTreeMe GetTree() {
		return this.m_tree;
	}

	public void SetTree(CTreeMe value) {
		this.m_tree = value;
	}

	public String GetValue() {
		return this.m_value;
	}

	public void SetValue(String value) {
		this.m_value = value;
	}

	public void AppendNode(CTreeNodeMe node) {
		node.SetParent(this);
		node.SetTree(this.m_tree);
		node.OnAddingNode(-1);
		this.m_nodes.add(node);
	}

	protected void CheckChildNodes(ArrayList<CTreeNodeMe> nodes, boolean isChecked) {
		int size = this.m_nodes.size();
		for (int i = 0; i < size; i++) {
			CTreeNodeMe treeNode = (CTreeNodeMe) nodes.get(i);
			treeNode.SetChecked(isChecked);
			ArrayList<CTreeNodeMe> childNodes = treeNode.GetChildNodes();
			if ((childNodes != null) && (childNodes.size() > 0)) {
				CheckChildNodes(childNodes, isChecked);
			}
		}
	}

	public void ClearNodes() {
		while (this.m_nodes.size() > 0) {
			RemoveNode((CTreeNodeMe) this.m_nodes.get(this.m_nodes.size() - 1));
		}
	}

	public void Collapse() {
		if (this.m_nodes.size() > 0) {
			this.m_expended = false;
			CollapseChildNodes(this.m_nodes, false);
		}
	}

	public void CollapseAll() {
		if (this.m_nodes.size() > 0) {
			this.m_expended = false;
			CollapseChildNodes(this.m_nodes, true);
		}
	}

	protected void CollapseChildNodes(ArrayList<CTreeNodeMe> nodes, boolean collapseAll) {
		int count = nodes.size();
		for (int i = 0; i < count; i++) {
			CTreeNodeMe ea = (CTreeNodeMe) nodes.get(i);
			if (collapseAll) {
				ea.SetExpended(false);
			}
			ea.GetRow().SetVisible(false);
			ArrayList<CTreeNodeMe> childNodes = ea.GetChildNodes();
			if ((childNodes != null) && (childNodes.size() > 0)) {
				CollapseChildNodes(childNodes, collapseAll);
			}
		}
	}

	public void Expend() {
		if (this.m_nodes.size() > 0) {
			this.m_expended = true;
			ExpendChildNodes(this.m_nodes, true, false);
		}
	}

	public void ExpendAll() {
		if (this.m_nodes.size() > 0) {
			this.m_expended = true;
			ExpendChildNodes(this.m_nodes, true, true);
		}
	}

	protected void ExpendChildNodes(ArrayList<CTreeNodeMe> nodes, boolean parentExpened, boolean expendAll) {
		int size = nodes.size();
		for (int i = 0; i < size; i++) {
			CTreeNodeMe ea = (CTreeNodeMe) nodes.get(i);
			boolean flag = parentExpened;
			if (expendAll) {
				flag = true;
				ea.GetRow().SetVisible(true);
				ea.SetExpended(true);
			} else {
				if (parentExpened) {
					ea.GetRow().SetVisible(true);
				} else {
					ea.GetRow().SetVisible(false);
				}
				if (!ea.IsExpended()) {
					flag = false;
				}
			}
			ArrayList<CTreeNodeMe> childNodes = ea.GetChildNodes();
			if ((childNodes != null) && (childNodes.size() > 0)) {
				ExpendChildNodes(childNodes, flag, expendAll);
			}
		}
	}

	public ArrayList<CTreeNodeMe> GetChildNodes() {
		return this.m_nodes;
	}

	protected CTreeNodeMe GetLastNode(ArrayList<CTreeNodeMe> nodes) {
		int size = nodes.size();
		if (size > 0) {
			for (int i = size - 1; i >= 0; i--) {
				CTreeNodeMe ea = (CTreeNodeMe) nodes.get(i);
				if (ea.GetRow() != null) {
					ArrayList<CTreeNodeMe> childNodes = ea.GetChildNodes();
					CTreeNodeMe lastNode = GetLastNode(childNodes);
					if (lastNode != null) {
						return lastNode;
					}

					return ea;
				}
			}
		}

		return null;
	}

	public int GetNodeIndex(CTreeNodeMe node) {
		int size = this.m_nodes.size();
		for (int i = 0; i < size; i++) {
			if (this.m_nodes.get(i) == node) {
				return i;
			}
		}
		return -1;
	}

	public String GetPaintText() {
		return GetText();
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("allowdragin")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowDragIn());
		} else if (name.equals("allowdragout")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(AllowDragOut());
		} else if (name.equals("checked")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsChecked());
		} else if (name.equals("expended")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsExpended());
		} else if (name.equals("value")) {
			type.argvalue = "string";
			value.argvalue = GetValue();
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames
				.addAll(Arrays.asList(new String[] { "AllowDragIn", "AllowDragOut", "Checked", "Expended", "Value" }));
		return propertyNames;
	}

	public String GetString() {
		return this.m_text;
	}

	public void InsertNode(int index, CTreeNodeMe node) {
		int num = -1;
		if (index == 0) {
			if (node.GetParent() != null) {
				num = node.GetParent().GetRow().GetIndex() + 1;
			} else {
				num = 0;
			}

		} else if (this.m_nodes.size() > 0) {
			num = ((CTreeNodeMe) this.m_nodes.get(index)).GetRow().GetIndex();
		}

		node.SetTree(this.m_tree);
		node.SetParent(this);
		node.OnAddingNode(num);
		this.m_nodes.add(index, node);
	}

	public boolean IsNodeVisible(CTreeNodeMe node) {
		CTreeNodeMe parent = node.GetParent();
		if (parent != null) {
			if (!parent.IsExpended()) {
				return false;
			}

			return IsNodeVisible(parent);
		}

		return true;
	}

	public void OnAddingNode(int index) {
		CGridRowMe row = GetRow();
		if (GetRow() == null) {
			row = new CGridRowMe();
			CTreeNodeMe parent = this.m_parent;
			if (parent == null) {
				if (index != -1) {
					this.m_tree.InsertRow(index, row);
					ArrayList<CGridRowMe> rows = this.m_tree.GetRows();
					int rowSize = rows.size();
					for (int i = 0; i < rowSize; i++) {
						((CGridRowMe) rows.get(i)).SetIndex(i);
					}
				} else {
					this.m_tree.AddRow(row);
					ArrayList<CGridRowMe> list2 = this.m_tree.GetRows();
					row.SetIndex(list2.size() - 1);
				}
				row.AddCell(0, this);
				this.m_targetColumn = this.m_tree.GetColumn(0);
			} else {
				int rowIndex = parent.GetRow().GetIndex() + 1;
				if (index != -1) {
					rowIndex = index;
				} else {
					CTreeNodeMe lastNode = GetLastNode(parent.GetChildNodes());
					if (lastNode != null) {
						if (lastNode.GetRow() == null) {
							return;
						}
						rowIndex = lastNode.GetRow().GetIndex() + 1;
					}
				}

				this.m_tree.InsertRow(rowIndex, row);
				ArrayList<CGridRowMe> list3 = this.m_tree.GetRows();
				int num4 = list3.size();
				if (rowIndex == num4 - 1) {
					row.SetIndex(rowIndex);
				} else {
					for (int i = 0; i < num4; i++) {
						((CGridRowMe) list3.get(i)).SetIndex(i);
					}
				}
				row.AddCell(0, this);
				this.m_targetColumn = this.m_tree.GetColumn(parent.m_targetColumn.GetIndex() + 1);
			}
			SetColSpan(this.m_tree.GetColumns().size());
			if ((this.m_nodes != null) && (this.m_nodes.size() > 0)) {
				int nodeSize = this.m_nodes.size();
				for (int i = 0; i < nodeSize; i++) {
					((CTreeNodeMe) this.m_nodes.get(i)).OnAddingNode(-1);
				}
			}
			row.SetVisible(IsNodeVisible(this));
		}
	}

	public void OnPaintCheckBox(CPaintMe paint, RECT rect) {
		if (this.m_checked) {
			if ((this.m_tree.GetCheckedImage() != null) && (this.m_tree.GetCheckedImage().length() > 0)) {
				paint.DrawImage(this.m_tree.GetCheckedImage(), rect);
			} else {
				paint.FillRect(COLOR.ARGB(0, 0, 0), rect);
			}

		} else if ((this.m_tree.GetUnCheckedImage() != null) && (this.m_tree.GetUnCheckedImage().length() > 0)) {
			paint.DrawImage(this.m_tree.GetUnCheckedImage(), rect);
		} else {
			paint.DrawRect(COLOR.ARGB(0, 0, 0), 1.0F, 0, rect);
		}
	}

	public void OnPaintNode(CPaintMe paint, RECT rect) {
		if (this.m_expended) {
			if ((this.m_tree.GetExpendedNodeImage() != null) && (this.m_tree.GetExpendedNodeImage().length() > 0)) {
				paint.DrawImage(this.m_tree.GetExpendedNodeImage(), rect);
			}

		} else if ((this.m_tree.GetCollapsedNodeImage() != null)
				&& (this.m_tree.GetCollapsedNodeImage().length() > 0)) {
			paint.DrawImage(this.m_tree.GetCollapsedNodeImage(), rect);
			return;
		}

		int width = rect.right - rect.left;
		int height = rect.bottom - rect.top;
		POINT[] points = new POINT[3];

		if (this.m_expended) {
			points[0] = new POINT(rect.left, rect.top);
			points[1] = new POINT(rect.left + width, rect.top);
			points[2] = new POINT(rect.left + width / 2, rect.top + height);
		} else {
			points[0] = new POINT(rect.left, rect.top);
			points[1] = new POINT(rect.left, rect.top + height);
			points[2] = new POINT(rect.left + width, rect.top + height / 2);
		}
		CGridMe grid = GetGrid();
		paint.FillPolygon(grid.GetForeColor(), points);
	}

	public void OnPaint(CPaintMe paint, RECT rect, RECT clipRect, boolean isAlternate) {
		int clipW = clipRect.right - clipRect.left;
		int clipH = clipRect.bottom - clipRect.top;
		CGridMe grid = GetGrid();
		CGridRowMe row = GetRow();
		if ((clipW > 0) && (clipH > 0) && (grid != null) && (GetColumn() != null) && (row != null)
				&& (GetTargetColumn() != null)) {
			int height = rect.bottom - rect.top;
			int pos = 0;
			CHScrollBarMe hScrollBar = grid.GetHScrollBar();
			if ((hScrollBar != null) && (hScrollBar.IsVisible())) {
				pos = hScrollBar.GetPos();
			}
			FONT font = null;
			long eMPTY = COLOR.EMPTY;
			long dwPenColor = COLOR.EMPTY;
			boolean autoEllipsis = this.m_tree.AutoEllipsis();
			GridCellStyle style = GetStyle();
			if (style != null) {
				if (style.AutoEllipsis()) {
					autoEllipsis = style.AutoEllipsis();
				}
				eMPTY = style.GetBackColor();
				if (style.GetFont() != null) {
					font = style.GetFont();
				}
				dwPenColor = style.GetForeColor();
			}
			GridRowStyle rowStyle = grid.GetRowStyle();
			if (isAlternate) {
				GridRowStyle alternateRowStyle = grid.GetAlternateRowStyle();
				if (alternateRowStyle != null) {
					rowStyle = alternateRowStyle;
				}
			}
			if (rowStyle != null) {
				boolean selected = false;
				ArrayList<CGridRowMe> selectedRows = grid.GetSelectedRows();
				int count = selectedRows.size();
				for (int i = 0; i < count; i++) {
					if (selectedRows.get(i) == row) {
						selected = true;
						break;
					}
				}
				if (eMPTY == COLOR.EMPTY) {
					if (selected) {
						eMPTY = rowStyle.GetSelectedBackColor();
					} else if (GetRow() == GetGrid().GetHoveredRow()) {
						eMPTY = rowStyle.GetHoveredBackColor();
					} else {
						eMPTY = rowStyle.GetBackColor();
					}
				}
				if (font == null) {
					font = rowStyle.GetFont();
				}
				if (dwPenColor == COLOR.EMPTY) {
					if (selected) {
						dwPenColor = rowStyle.GetSelectedForeColor();
					} else if (GetRow() == GetGrid().GetHoveredRow()) {
						dwPenColor = rowStyle.GetHoveredForeColor();
					} else {
						dwPenColor = rowStyle.GetForeColor();
					}
				}
			}

			paint.FillRect(eMPTY, rect);
			RECT bounds = GetTargetColumn().GetBounds();
			bounds.left += GetGrid().GetHorizontalOffset() - pos;
			bounds.top += GetGrid().GetVerticalOffset() - pos;
			int left = bounds.left;
			if (this.m_tree.CheckBoxes()) {
				int cw = this.m_tree.GetCheckBoxSize().cx;
				int ch = this.m_tree.GetCheckBoxSize().cy;
				RECT rect3 = new RECT();
				rect3.left = left;
				rect.top += (height - ch) / 2;
				rect3.right = (rect3.left + cw);
				rect3.bottom = (rect3.top + ch);
				OnPaintCheckBox(paint, rect3);
				left += cw + 10;
			}

			int cx = this.m_tree.GetNodeSize().cx;
			int cy = this.m_tree.GetNodeSize().cy;
			if (this.m_nodes.size() > 0) {
				RECT nodeRect = new RECT();
				nodeRect.left = left;
				rect.top += (height - cy) / 2;
				nodeRect.right = (nodeRect.left + cx);
				nodeRect.bottom = (nodeRect.top + cy);
				OnPaintNode(paint, nodeRect);
			}
			left += cx + 10;
			this.m_indent = left;
			String paintText = GetPaintText();
			if (paintText != null) {
				SIZE tSize = paint.TextSize(paintText, font);
				RECT tRect = new RECT();
				tRect.left = left;
				rect.top += (row.GetHeight() - tSize.cy) / 2;
				tRect.right = (tRect.left + tSize.cx);
				tRect.bottom = (tRect.top + tSize.cy);
				if ((autoEllipsis) && ((tRect.right < clipRect.right) || (tRect.bottom < clipRect.bottom))) {
					if (tRect.right < clipRect.right) {
						tRect.right = clipRect.right;
					}
					if (tRect.bottom < clipRect.bottom) {
						tRect.bottom = clipRect.bottom;
					}
					paint.DrawTextAutoEllipsis(paintText, dwPenColor, font, tRect);
				} else {
					paint.DrawText(paintText, dwPenColor, font, tRect);
				}
			}
		}
		OnPaintControl(paint, rect, clipRect);
	}

	public void OnRemovingNode() {
		this.m_indent = 0;
		CGridRowMe row = GetRow();
		if (row != null) {
			if ((this.m_nodes != null) && (this.m_nodes.size() > 0)) {
				int num = this.m_nodes.size();
				for (int i = 0; i < num; i++) {
					((CTreeNodeMe) this.m_nodes.get(i)).OnRemovingNode();
				}
			}
			this.m_tree.RemoveRow(row);
			row.ClearCells();
			SetRow(null);
			ArrayList<CGridRowMe> rows = this.m_tree.GetRows();
			int count = rows.size();
			for (int i = 0; i < count; i++) {
				((CGridRowMe) rows.get(i)).SetIndex(i);
			}
			this.m_targetColumn = null;
		}
	}

	public void RemoveNode(CTreeNodeMe node) {
		node.OnRemovingNode();
		this.m_nodes.remove(node);
	}

	public void SetProperty(String name, String value) {
		if (name.equals("allowdragin")) {
			SetAllowDragIn(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("allowdragout")) {
			SetAllowDragOut(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("checked")) {
			SetChecked(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("expended")) {
			SetExpended(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("value")) {
			SetValue(value);
		} else {
			super.SetProperty(name, value);
		}
	}

	public void SetString(String value) {
		this.m_text = value;
	}
}
