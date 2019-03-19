package com.melib.Chart;

import java.lang.reflect.Array;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CListMe<T> {
	public Object[] m_ary;
	public int m_size;
	public int m_capacity;
	public int m_step;

	public CListMe() {
		this.m_size = 0;
		this.m_ary = null;
		this.m_capacity = 4;
		this.m_step = 4;
	}

	public CListMe(int capacity) {
		this.m_size = 0;
		this.m_ary = null;
		this.m_capacity = capacity;
		this.m_step = 4;
	}

	protected void finalize() throws Throwable {
		super.finalize();
		Dispose();
	}

	public void addranges(T[] ary, int size) {
		this.m_ary = ary;
		this.m_size = size;
		this.m_capacity = this.m_size;
		this.m_step = 4;
	}

	public int capacity() {
		return this.m_capacity;
	}

	public void clear() {
		this.m_step = 4;
		this.m_size = 0;
		this.m_ary = null;
	}

	public void Dispose() {
		clear();
	}

	public T get(int index) {
		return (T) this.m_ary[index];
	}

	public void insert(int index, T value) {
		this.m_size += 1;
		if (this.m_ary == null) {
			this.m_ary = new Object[this.m_capacity];
		} else if (this.m_size > this.m_capacity) {
			this.m_capacity += this.m_step;
			Object[] newAry = new Object[this.m_capacity];
			for (int i = 0; i < this.m_size - 1; i++) {
				if (i < index) {
					newAry[i] = this.m_ary[i];
				} else if (i >= index) {
					newAry[(i + 1)] = this.m_ary[i];
				}
			}
			this.m_ary = null;
			this.m_ary = newAry;
		} else {
			Object last = Integer.valueOf(0);
			for (int i = index; i < this.m_size; i++) {
				if (i == index) {
					last = this.m_ary[i];
				} else if (i > index) {
					Object temp = this.m_ary[i];
					this.m_ary[i] = last;
					last = temp;
				}
			}
		}

		this.m_ary[index] = value;
	}

	public void push_back(T value) {
		this.m_size += 1;
		if (this.m_ary == null) {
			this.m_ary = new Object[this.m_capacity];
		} else if (this.m_size > this.m_capacity) {
			this.m_capacity += this.m_step;
			Object[] newAry = new Object[this.m_capacity];
			for (int i = 0; i < this.m_size - 1; i++) {
				newAry[i] = this.m_ary[i];
			}
			this.m_ary = null;
			this.m_ary = newAry;
		}

		this.m_ary[(this.m_size - 1)] = value;
	}

	public void remove_at(int index) {
		this.m_size -= 1;
		for (int i = index; i < this.m_size; i++) {
			this.m_ary[i] = this.m_ary[(i + 1)];
		}
		if (this.m_capacity - this.m_size > this.m_step) {
			this.m_capacity -= this.m_step;
			if (this.m_capacity > 0) {
				Object[] newAry = new Object[this.m_capacity];
				for (int i = 0; i < this.m_size; i++) {
					newAry[i] = this.m_ary[i];
				}
				this.m_ary = newAry;
			} else {
				this.m_ary = null;
			}
		}
	}

	public void set(int index, T value) {
		this.m_ary[index] = value;
	}

	public void set_capacity(int capacity) {
		this.m_capacity = capacity;
		if (this.m_ary != null) {
			Object[] newAry = new Object[this.m_capacity];
			for (int i = 0; i < this.m_size - 1; i++) {
				newAry[i] = this.m_ary[i];
			}
			this.m_ary = null;
			this.m_ary = newAry;
		}
	}

	public void set_step(int step) {
		this.m_step = step;
	}

	public int size() {
		return this.m_size;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("[");
		for (int i = 0; i < this.m_size; i++) {
			buf.append(this.m_ary[i]);
			if (i != this.m_size - 1) {
				buf.append(",");
			}
		}
		buf.append("]");
		return buf.toString();
	}

	public T[] toArray(T[] t, int lenth, Class<? extends T[]> type) {
		T[] copy = ((Object) type == (Object) Object[].class) ? (T[]) new Object[lenth]
				: (T[]) Array.newInstance(type.getComponentType(), lenth);
		System.arraycopy(t, 0, copy, 0, Math.min(t.length, lenth));
		return copy;
	}
}
