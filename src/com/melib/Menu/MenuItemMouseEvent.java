package com.melib.Menu;

import com.melib.Base.MouseButtonsA;
import com.melib.Base.POINT;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public abstract interface MenuItemMouseEvent {
	public abstract void CallMenuItemMouseEvent(int paramInt1, Object paramObject, CMenuItemMe paramMenuItemA,
			POINT paramPOINT, MouseButtonsA paramMouseButtonsA, int paramInt2, int paramInt3);
}
