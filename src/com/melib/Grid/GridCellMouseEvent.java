package com.melib.Grid;

import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public abstract interface GridCellMouseEvent {
	public abstract void CallGridCellMouseEvent(int paramInt1, Object paramObject, CGridCellMe paramGridCell,
			POINT paramPOINT, MouseButtonsA paramMouseButtonsA, int paramInt2, int paramInt3);
}
