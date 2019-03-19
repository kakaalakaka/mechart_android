package com.melib.Grid;

import java.util.Collections;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CGridSortMe {
	public void SortColumn(CGridMe grid, CGridColumnMe column, GridColumnSortMode sortMode) {
		CGridRowCompareMe compare = new CGridRowCompareMe();
		compare.m_columnIndex = column.GetIndex();
		if (sortMode == GridColumnSortMode.Asc) {
			compare.m_type = 0;
		} else if (sortMode == GridColumnSortMode.Desc) {
			compare.m_type = 1;
		}
		Collections.sort(grid.m_rows, compare);
	}
}
