package com.melib.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CStrMe;
import com.melib.Base.FONT;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;
import com.melib.Grid.CGridMe;
import com.melib.Grid.CGridCellMe;
import com.melib.Grid.CGridColumnMe;
import com.melib.Grid.CGridRowMe;
import com.melib.ScrollBar.CHScrollBarMe;
import com.melib.TextBox.CTextBoxMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CTreeMe extends CGridMe {
	private CTreeNodeMe m_movingNode;

	public CTreeMe() {
		SetGridLineColor(COLOR.EMPTY);
	}

	public ArrayList<CTreeNodeMe> m_nodes = new ArrayList();

	protected boolean m_checkBoxes = false;

	public boolean CheckBoxes() {
		return this.m_checkBoxes;
	}

	public void SetCheckBoxes(boolean value) {
		this.m_checkBoxes = value;
	}

	protected SIZE m_checkBoxSize = new SIZE(14, 14);
	protected String m_checkedImage;

	public SIZE GetCheckBoxSize() {
		return this.m_checkBoxSize.Clone();
	}

	public void SetCheckBoxSize(SIZE value) {
		this.m_checkBoxSize = value.Clone();
	}

	protected String m_collapsedNodeImage;
	protected String m_expendedNodeImage;

	public String GetCheckedImage() {
		return this.m_checkedImage;
	}

	public void SetCheckedImage(String value) {
		this.m_checkedImage = value;
	}

	public String GetCollapsedNodeImage() {
		return this.m_collapsedNodeImage;
	}

	public void SetCollapsedNodeImage(String value) {
		this.m_collapsedNodeImage = value;
	}

	public String GetExpendedNodeImage() {
		return this.m_expendedNodeImage;
	}

	public void SetExpendedNodeImage(String value) {
		this.m_expendedNodeImage = value;
	}

	protected SIZE m_nodeSize = new SIZE(14, 14);
	protected String m_unCheckedImage;

	public SIZE GetNodeSize() {
		return this.m_nodeSize.Clone();
	}

	public void SetNodeSize(SIZE value) {
		this.m_nodeSize = value.Clone();
	}

	public ArrayList<CTreeNodeMe> GetSelectedNodes() {
		ArrayList<CTreeNodeMe> list = new ArrayList();
		ArrayList<CGridRowMe> selectedRows = GetSelectedRows();
		int count = selectedRows.size();

		for (int i = 0; i < count; i++) {
			ArrayList<CGridCellMe> cells = ((CGridRowMe) selectedRows.get(i)).m_cells;
			int cellCount = cells.size();
			for (int j = 0; j < cellCount; j++) {
				CTreeNodeMe item = (CTreeNodeMe) ((cells.get(i) instanceof CTreeNodeMe) ? (CGridCellMe) cells.get(j)
						: null);
				if (item != null) {
					list.add(item);
					break;
				}
			}
		}
		return list;
	}

	public void SetSelectedNodes(ArrayList<CTreeNodeMe> value) {
		int count = value.size();
		ArrayList<CGridRowMe> list = new ArrayList();
		for (int i = 0; i < count; i++) {
			list.add(((CTreeNodeMe) value.get(i)).GetRow());
		}
		SetSelectedRows(list);
	}

	public String GetUnCheckedImage() {
		return this.m_unCheckedImage;
	}

	public void SetUnCheckedImage(String value) {
		this.m_unCheckedImage = value;
	}

	public void AppendNode(CTreeNodeMe node) {
		node.SetTree(this);
		node.OnAddingNode(-1);
		this.m_nodes.add(node);
	}

	public void ClearNodes() {
		while (this.m_nodes.size() > 0) {
			RemoveNode((CTreeNodeMe) this.m_nodes.get(this.m_nodes.size() - 1));
		}
	}

	public void Collapse() {
		int count = this.m_nodes.size();
		if (this.m_nodes.size() > 0) {
			for (int i = 0; i < count; i++) {
				((CTreeNodeMe) this.m_nodes.get(i)).Collapse();
			}
		}
	}

	public void CollapseAll() {
		int count = this.m_nodes.size();
		if (this.m_nodes.size() > 0) {
			for (int i = 0; i < count; i++) {
				((CTreeNodeMe) this.m_nodes.get(i)).CollapseAll();
			}
		}
	}

	public void Dispose() {
		if (!IsDisposed()) {
			this.m_nodes.clear();
		}
		super.Dispose();
	}

	public void Expend() {
		int count = this.m_nodes.size();
		if (this.m_nodes.size() > 0) {
			for (int i = 0; i < count; i++) {
				((CTreeNodeMe) this.m_nodes.get(i)).Expend();
			}
		}
	}

	public void ExpendAll() {
		int count = this.m_nodes.size();
		if (this.m_nodes.size() > 0) {
			for (int i = 0; i < count; i++) {
				((CTreeNodeMe) this.m_nodes.get(i)).ExpendAll();
			}
		}
	}

	public ArrayList<CTreeNodeMe> GetChildNodes() {
		return this.m_nodes;
	}

	public String GetControlType() {
		return "Tree";
	}

	public int GetNodeIndex(CTreeNodeMe node) {
		int count = this.m_nodes.size();
		for (int i = 0; i < count; i++) {
			if (this.m_nodes.get(i) == node) {
				return i;
			}
		}
		return -1;
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("checkboxes")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(CheckBoxes());
		} else if (name.equals("checkboxsize")) {
			type.argvalue = "size";
			value.argvalue = CStrMe.ConvertSizeToStr(GetCheckBoxSize());
		} else if (name.equals("checkedimage")) {
			type.argvalue = "string";
			value.argvalue = GetCheckedImage();
		} else if (name.equals("collapsednodeimage")) {
			type.argvalue = "string";
			value.argvalue = GetCollapsedNodeImage();
		} else if (name.equals("expendednodeimage")) {
			type.argvalue = "string";
			value.argvalue = GetExpendedNodeImage();
		} else if (name.equals("uncheckedimage")) {
			type.argvalue = "string";
			value.argvalue = GetUnCheckedImage();
		} else if (name.equals("nodesize")) {
			type.argvalue = "size";
			value.argvalue = CStrMe.ConvertSizeToStr(GetNodeSize());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "CheckBoxes", "CheckBoxSize", "CheckedImage",
				"CollapsedNodeImage", "ExpendedNodeImage", "UnCheckedImage", "NodeSize" }));
		return propertyNames;
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

		node.SetTree(this);
		node.OnAddingNode(num);
		this.m_nodes.add(index, node);
	}

	public void OnCellMouseDown(CGridCellMe cell, POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnCellMouseDown(cell, mp.Clone(), button, clicks, delta);
		CTreeNodeMe ea = (CTreeNodeMe) ((cell instanceof CTreeNodeMe) ? cell : null);
		if (ea != null) {
			int pos = 0;
			CHScrollBarMe hScrollBar = GetHScrollBar();
			if ((hScrollBar != null) && (hScrollBar.IsVisible())) {
				pos = hScrollBar.GetPos();
			}
			RECT bounds = ea.GetTargetColumn().GetBounds();
			bounds.left += GetHorizontalOffset() - pos;
			bounds.top += GetVerticalOffset() - pos;
			int left = bounds.left;

			if (this.m_checkBoxes) {
				int cx = this.m_checkBoxSize.cx;
				if ((mp.x >= left) && (mp.x <= left + cx)) {
					ea.SetChecked(!ea.IsChecked());
					return;
				}
				left += cx + 10;
			}

			ArrayList<CTreeNodeMe> childNodes = ea.GetChildNodes();
			if ((childNodes != null) && (childNodes.size() > 0)) {
				int num4 = this.m_nodeSize.cx;
				if ((mp.x >= left) && (mp.x <= left + num4)) {
					if (ea.IsExpended()) {
						ea.Collapse();
					} else {
						ea.Expend();
					}
					Update();
					return;
				}
			}

			if (ea.AllowDragOut()) {
				this.m_movingNode = ea;
			}
		}
	}

	public void OnCellMouseMove(CGridCellMe cell, POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnCellMouseMove(cell, mp.Clone(), button, clicks, delta);
		if (this.m_movingNode != null) {
			Invalidate();
		}
	}

	public void OnCellMouseUp(CGridCellMe cell, POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnCellMouseUp(cell, mp.Clone(), button, clicks, delta);
		if (this.m_movingNode != null) {
			CGridRowMe row = GetRow(mp.Clone());

			if (row != null) {
				CGridCellMe gridCell = row.GetCell(0);
				CTreeNodeMe node = (CTreeNodeMe) ((gridCell instanceof CTreeNodeMe) ? gridCell : null);
				if ((node.AllowDragIn()) && (this.m_movingNode != node)) {
					CTreeNodeMe parent = node.GetParent();
					CTreeNodeMe ea3 = this.m_movingNode.GetParent();
					if (ea3 != null) {
						ea3.RemoveNode(this.m_movingNode);
					} else {
						RemoveNode(this.m_movingNode);
					}

					if (parent != null) {
						if (ea3 == parent) {
							parent.InsertNode(parent.GetNodeIndex(node), this.m_movingNode);
						} else {
							node.AppendNode(this.m_movingNode);
						}

					} else if (ea3 == parent) {
						InsertNode(GetNodeIndex(node), this.m_movingNode);
					} else {
						node.AppendNode(this.m_movingNode);
					}

					node.Expend();
				}
			}
			this.m_movingNode = null;
			Update();
		}
	}

	public void OnPaintForeground(CPaintMe paint, RECT clipRect) {
		super.OnPaintForeground(paint, clipRect);

		if (this.m_movingNode != null) {
			FONT font = GetFont();
			POINT mousePoint = GetMousePoint();
			SIZE size = paint.TextSize(this.m_movingNode.GetText(), font);
			RECT rect = new RECT(mousePoint.x, mousePoint.y, mousePoint.x + size.cx, mousePoint.y + size.cy);
			paint.DrawText(this.m_movingNode.GetText(), GetForeColor(), font, rect);
		}
	}

	public void OnPaintEditTextBox(CGridCellMe cell, CPaintMe paint, RECT rect, RECT clipRect) {
		CTextBoxMe editTextBox = GetEditTextBox();
		if (editTextBox != null) {
			CTreeNodeMe ea = (CTreeNodeMe) ((cell instanceof CTreeNodeMe) ? cell : null);
			if (ea != null) {
				int indent = ea.GetIndent();
				rect.left += indent;
				if (rect.right < rect.left) {
					rect.right = rect.left;
				}
				editTextBox.SetBounds(rect);
				editTextBox.SetDisplayOffset(false);
				editTextBox.BringToFront();
			} else {
				super.OnPaintEditTextBox(cell, paint, rect, clipRect);
			}
		}
	}

	public void RemoveNode(CTreeNodeMe node) {
		node.OnRemovingNode();
		this.m_nodes.remove(node);
	}

	public void SetProperty(String name, String value) {
		if (name.equals("checkboxes")) {
			SetCheckBoxes(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("checkboxsize")) {
			SetCheckBoxSize(CStrMe.ConvertStrToSize(value));
		} else if (name.equals("checkedimage")) {
			SetCheckedImage(value);
		} else if (name.equals("collapsednodeimage")) {
			SetCollapsedNodeImage(value);
		} else if (name.equals("expendednodeimage")) {
			SetExpendedNodeImage(value);
		} else if (name.equals("uncheckedimage")) {
			SetUnCheckedImage(value);
		} else if (name.equals("nodesize")) {
			SetNodeSize(CStrMe.ConvertStrToSize(value));
		} else {
			super.SetProperty(name, value);
		}
	}
}
