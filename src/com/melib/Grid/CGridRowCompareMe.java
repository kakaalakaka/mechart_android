package com.melib.Grid;

import java.util.Comparator;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridRowCompareMe implements Comparator<CGridRowMe> {
	public int m_columnIndex;
	public int m_type;

	public int compare(CGridRowMe x, CGridRowMe y) {
		CGridCellMe cellLeft = x.GetCell(this.m_columnIndex);
		CGridCellMe cellRight = y.GetCell(this.m_columnIndex);
		if (this.m_type == 0) {
			return cellLeft.CompareTo(cellRight);
		}

		return cellRight.CompareTo(cellLeft);
	}
}
