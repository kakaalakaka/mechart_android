package com.melib.TextBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import com.melib.Base.COLOR;
import com.melib.Base.CPaintMe;
import com.melib.Base.CStrMe;
import com.melib.Base.CControlHostMe;
import com.melib.Base.FONT;
import com.melib.Base.HorizontalAlignA;
import com.melib.Base.INativeBaseMe;
import com.melib.Base.MouseButtonsA;
import com.melib.Base.PADDING;
import com.melib.Base.POINT;
import com.melib.Base.RECT;
import com.melib.Base.RECTF;
import com.melib.Base.RefObject;
import com.melib.Base.SIZE;
import com.melib.Base.SIZEF;
import com.melib.Layout.CDivMe;
import com.melib.ScrollBar.CHScrollBarMe;
import com.melib.ScrollBar.CVScrollBarMe;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */

public class CTextBoxMe extends CDivMe {
	public CTextBoxMe() {
		SIZE size = new SIZE(100, 20);
		SetSize(size);
	}

	protected boolean m_isKeyDown = false;

	protected boolean m_isMouseDown = false;

	protected int m_offsetX = 0;

	protected ArrayList<RECTF> m_ranges = new ArrayList();

	protected Stack<String> m_redoStack = new Stack();

	protected boolean m_showCursor = false;

	protected int m_startMovingIndex = -1;

	protected int m_stopMovingIndex = -1;

	protected boolean m_textChanged = false;

	protected static int TICK = 500;

	private int m_timerID = GetNewTimerID();

	protected Stack<String> m_undoStack = new Stack();

	protected ArrayList<SIZEF> m_wordsSize = new ArrayList();

	public int GetLinesCount() {
		return this.m_lines.size();
	}

	protected int m_lineHeight = 20;

	public int GetLineHeight() {
		return this.m_lineHeight;
	}

	public void SetLineHeight(int value) {
		this.m_lineHeight = value;
	}

	protected ArrayList<WordLine> m_lines = new ArrayList();

	public ArrayList<WordLine> GetLines() {
		return this.m_lines;
	}

	protected boolean m_multiline = false;
	protected char m_passwordChar;

	public boolean Multiline() {
		return this.m_multiline;
	}

	public void SetMultiline(boolean value) {
		if (this.m_multiline != value) {
			this.m_multiline = value;
			this.m_textChanged = true;
		}
		SetShowVScrollBar(this.m_multiline);
	}

	public char GetPasswordChar() {
		return this.m_passwordChar;
	}

	public void SetPasswordChar(char value) {
		this.m_passwordChar = value;
		this.m_textChanged = true;
	}

	protected boolean m_readOnly = false;
	protected boolean m_rightToLeft;

	public boolean IsReadOnly() {
		return this.m_readOnly;
	}

	public void SetReadOnly(boolean value) {
		this.m_readOnly = value;
	}

	public boolean RightToLeft() {
		return this.m_rightToLeft;
	}

	public void SetRightToLeft(boolean value) {
		this.m_rightToLeft = value;
		this.m_textChanged = true;
	}

	public String GetSelectionText() {
		String text = GetText();
		int textLength = text.length();
		if ((textLength > 0) && (this.m_selectionStart != textLength)) {
			String selectedText = text.substring(this.m_selectionStart, this.m_selectionStart + this.m_selectionLength);
			return selectedText;
		}
		return "";
	}

	protected long m_selectionBackColor = COLOR.ARGB(10, 36, 106);

	public long GetSelectionBackColor() {
		return this.m_selectionBackColor;
	}

	public void SetSelectionBackColor(long value) {
		this.m_selectionBackColor = value;
	}

	protected long m_selectionForeColor = COLOR.ARGB(255, 255, 255);
	protected int m_selectionLength;

	public long GetSelectionForeColor() {
		return this.m_selectionForeColor;
	}

	public void SetSelectionForeColor(long value) {
		this.m_selectionForeColor = value;
	}

	public int GetSelectionLength() {
		return this.m_selectionLength;
	}

	public void SetSelectionLength(int value) {
		this.m_selectionLength = value;
	}

	protected int m_selectionStart = -1;
	protected String m_tempText;

	public int GetSelectionStart() {
		return this.m_selectionStart;
	}

	public void SetSelectionStart(int value) {
		this.m_selectionStart = value;
	}

	public String GetTempText() {
		return this.m_tempText;
	}

	public void SetTempText(String tempText) {
		this.m_tempText = tempText;
	}

	protected long m_tempTextForeColor = COLOR.DISABLEDCONTROLTEXT;

	public long GetTempTextForeColor() {
		return this.m_tempTextForeColor;
	}

	public void SetTempTextForeColor(long tempTextForeColor) {
		this.m_tempTextForeColor = tempTextForeColor;
	}

	protected HorizontalAlignA m_textAlign = HorizontalAlignA.Left;

	public HorizontalAlignA GetTextAlign() {
		return this.m_textAlign;
	}

	public void SetTextAlign(HorizontalAlignA value) {
		this.m_textAlign = value;
	}

	protected boolean m_wordWrap = false;

	public boolean WordWrap() {
		return this.m_wordWrap;
	}

	public void SetWordWrap(boolean value) {
		if (this.m_wordWrap != value) {
			this.m_wordWrap = value;
			this.m_textChanged = true;
		}
		SetShowHScrollBar(!this.m_wordWrap);
	}

	public boolean CanRedo() {
		if (this.m_redoStack.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean CanUndo() {
		if (this.m_undoStack.size() > 0) {
			return true;
		}
		return false;
	}

	public void ClearRedoUndo() {
		this.m_undoStack.clear();
		this.m_redoStack.clear();
	}

	protected void DeleteWord() {
		String text = GetText();
		if (text == null) {
			text = "";
		}
		int textLength = text.length();
		if (textLength > 0) {
			int oldLines = this.m_lines.size();
			String left = "";
			String right = "";
			int rightIndex = -1;
			if (this.m_selectionLength > 0) {
				left = this.m_selectionStart > 0 ? text.substring(0, this.m_selectionStart) : "";
				rightIndex = this.m_selectionStart + this.m_selectionLength;
			} else {
				left = this.m_selectionStart > 0 ? text.substring(0, this.m_selectionStart - 1) : "";
				rightIndex = this.m_selectionStart + this.m_selectionLength;
				if (this.m_selectionStart > 0) {
					this.m_selectionStart -= 1;
				}
			}
			if (rightIndex < textLength) {
				right = text.substring(rightIndex);
			}
			String newText = left + right;
			this.m_text = text;
			textLength = newText.length();
			if (textLength == 0) {
				this.m_selectionStart = 0;

			} else if (this.m_selectionStart > textLength) {
				this.m_selectionStart = textLength;
			}

			this.m_selectionLength = 0;
		}
	}

	public void Dispose() {
		if (!IsDisposed()) {
			StopTimer(this.m_timerID);
			this.m_lines.clear();
			this.m_redoStack.clear();
			this.m_undoStack.clear();
			this.m_wordsSize.clear();
		}
		super.Dispose();
	}

	public int GetContentHeight() {
		int hmax = super.GetContentHeight();
		int cheight = 0;
		int rangeSize = this.m_ranges.size();
		for (int i = 0; i < rangeSize; i++) {
			if (cheight < ((RECTF) this.m_ranges.get(i)).bottom) {
				cheight = (int) ((RECTF) this.m_ranges.get(i)).bottom;
			}
		}
		return hmax > cheight ? hmax : cheight;
	}

	public int GetContentWidth() {
		int wmax = super.GetContentWidth();
		int cwidth = 0;
		int rangeSize = this.m_ranges.size();
		for (int i = 0; i < rangeSize; i++) {
			if (cwidth < ((RECTF) this.m_ranges.get(i)).right) {
				cwidth = (int) ((RECTF) this.m_ranges.get(i)).right;
			}
		}
		return wmax > cwidth ? wmax : cwidth;
	}

	public String GetControlType() {
		return "TextBox";
	}

	public void GetProperty(String name, RefObject<String> value, RefObject<String> type) {
		if (name.equals("lineheight")) {
			type.argvalue = "int";
			value.argvalue = CStrMe.ConvertIntToStr(GetLineHeight());
		} else if (name.equals("multiline")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(Multiline());
		} else if (name.equals("passwordchar")) {
			type.argvalue = "text";
			value.argvalue = new Character(GetPasswordChar()).toString();
		} else if (name.equals("readonly")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(IsReadOnly());
		} else if (name.equals("righttoleft")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(RightToLeft());
		} else if (name.equals("selectionbackcolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetSelectionBackColor());
		} else if (name.equals("selectionforecolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetSelectionForeColor());
		} else if (name.equals("temptext")) {
			type.argvalue = "text";
			value.argvalue = GetTempText();
		} else if (name.equals("temptextforecolor")) {
			type.argvalue = "color";
			value.argvalue = CStrMe.ConvertColorToStr(GetTempTextForeColor());
		} else if (name.equals("textalign")) {
			type.argvalue = "enum:HorizontalAlignA";
			value.argvalue = CStrMe.ConvertHorizontalAlignToStr(GetTextAlign());
		} else if (name.equals("wordwrap")) {
			type.argvalue = "bool";
			value.argvalue = CStrMe.ConvertBoolToStr(WordWrap());
		} else {
			super.GetProperty(name, value, type);
		}
	}

	public ArrayList<String> GetPropertyNames() {
		ArrayList<String> propertyNames = super.GetPropertyNames();
		propertyNames.addAll(Arrays.asList(new String[] { "LineHeight", "Multiline", "PasswordChar", "ReadOnly",
				"RightToLeft", "SelectionBackColor", "SelectionForeColor", "TempText", "TempTextForeColor", "TextAlign",
				"WordWrap" }));
		return propertyNames;
	}

	protected boolean IsLineVisible(int index, double visiblePercent) {
		int rangeSize = this.m_ranges.size();
		if (rangeSize > 0) {
			if ((index >= 0) && (index < rangeSize)) {
				int top = 0;
				int scrollV = 0;
				int sch = 0;
				CHScrollBarMe hScrollBar = GetHScrollBar();
				CVScrollBarMe vScrollBar = GetVScrollBar();
				if ((hScrollBar != null) && (hScrollBar.IsVisible())) {
					sch = hScrollBar.GetHeight();
				}
				if ((vScrollBar != null) && (vScrollBar.IsVisible())) {
					scrollV = -vScrollBar.GetPos();
				}
				top = scrollV;
				int cell = 1;
				int floor = GetHeight() - cell - sch - 1;
				RECTF indexRect = (RECTF) this.m_ranges.get(index);
				int indexTop = (int) indexRect.top + scrollV;
				int indexBottom = (int) indexRect.bottom + scrollV;
				if (indexTop < cell) {
					indexTop = cell;
				} else if (indexTop > floor) {
					indexTop = floor;
				}
				if (indexBottom < cell) {
					indexBottom = cell;
				} else if (indexBottom > floor) {
					indexBottom = floor;
				}
				if (indexBottom - indexTop > this.m_lineHeight * visiblePercent) {
					return true;
				}
			}
		}
		return false;
	}

	public void InsertWord(String str) {
		String text = GetText();
		if (text == null) {
			text = "";
		}
		if ((text.length() == 0) || (this.m_selectionStart == text.length())) {
			text = text + str;
			this.m_text = text;
			this.m_selectionStart = text.length();
		} else {
			int sIndex = this.m_selectionStart > text.length() ? text.length() - 1 : this.m_selectionStart;
			String left = sIndex > 0 ? text.substring(0, sIndex) : "";
			String right = "";
			int rightIndex = this.m_selectionStart + (this.m_selectionLength == 0 ? 0 : this.m_selectionLength);
			if (rightIndex < text.length()) {
				right = text.substring(rightIndex);
			}
			text = left + str + right;
			this.m_text = text;
			this.m_selectionStart += str.length();
			if (this.m_selectionStart > text.length()) {
				this.m_selectionStart = text.length();
			}
			this.m_selectionLength = 0;
		}
	}

	public void OnCopy() {
		String selectionText = GetSelectionText();
		if ((selectionText != null) && (selectionText.length() > 0)) {
			CControlHostMe host = GetNative().GetHost();
			host.Copy(selectionText);
			super.OnCopy();
		}
	}

	public void OnCut() {
		if (!this.m_readOnly) {
			OnCopy();
			int oldLines = this.m_lines.size();
			String redotext = GetText();
			DeleteWord();
			OnTextChanged();
			if (this.m_textChanged) {
				this.m_undoStack.push(redotext);
			}
			Invalidate();
			int newLines = this.m_lines.size();
			if (newLines != oldLines) {
				CVScrollBarMe vScrollBar = GetVScrollBar();
				if (vScrollBar != null) {
					vScrollBar.SetPos(vScrollBar.GetPos() + this.m_lineHeight * (newLines - oldLines));
					Invalidate();
				}
			}
			super.OnCut();
		}
	}

	public void OnGotFocus() {
		super.OnGotFocus();
		this.m_showCursor = true;
		Invalidate();
		StartTimer(this.m_timerID, TICK);
	}

	public void OnLostFocus() {
		super.OnLostFocus();
		StopTimer(this.m_timerID);
		this.m_isKeyDown = false;
		this.m_showCursor = false;
		this.m_selectionLength = 0;
		Invalidate();
	}

	public void OnMouseDown(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseDown(mp.Clone(), button, clicks, delta);
		if (button == MouseButtonsA.Left) {
			if (clicks == 1) {
				int sIndex = -1;
				int linesCount = this.m_lines.size();
				this.m_selectionLength = 0;
				this.m_startMovingIndex = -1;
				this.m_stopMovingIndex = -1;
				if (linesCount > 0) {
					CHScrollBarMe hScrollBar = GetHScrollBar();
					CVScrollBarMe vScrollBar = GetVScrollBar();
					int scrollH = (hScrollBar != null) && (hScrollBar.IsVisible()) ? hScrollBar.GetPos() : 0;
					int scrollV = (vScrollBar != null) && (vScrollBar.IsVisible()) ? vScrollBar.GetPos() : 0;
					scrollH += this.m_offsetX;
					int x = mp.x + scrollH;
					int y = mp.y + scrollV;
					RECTF lastRange = new RECTF();
					int rangeSize = this.m_ranges.size();
					if (rangeSize > 0) {
						lastRange = (RECTF) this.m_ranges.get(rangeSize - 1);
					}
					for (int i = 0; i < linesCount; i++) {
						WordLine line = (WordLine) this.m_lines.get(i);
						for (int j = line.m_start; j <= line.m_end; j++) {
							RECTF rect = (RECTF) this.m_ranges.get(j);
							if (i == linesCount - 1) {
								rect.bottom += 3.0F;
							}
							if ((y >= rect.top) && (y <= rect.bottom)) {
								float sub = (rect.right - rect.left) / 2.0F;
								if (((x >= rect.left - sub) && (x <= rect.right - sub))
										|| ((j == line.m_start) && (x <= rect.left + sub))
										|| ((j == line.m_end) && (x >= rect.right - sub))) {
									if ((j == line.m_end) && (x >= rect.right - sub)) {
										sIndex = j + 1;
										break;
									}

									sIndex = j;

									break;
								}
							}
						}
						if (sIndex != -1) {
							break;
						}
					}

					if (sIndex == -1) {
						if (((y >= lastRange.top) && (y <= lastRange.bottom) && (x > lastRange.right))
								|| (y >= lastRange.bottom)) {
							sIndex = rangeSize;
						}
					}
				}
				if (sIndex != -1) {
					this.m_selectionStart = sIndex;
				} else {
					this.m_selectionStart = 0;
				}
				this.m_startMovingIndex = this.m_selectionStart;
				this.m_stopMovingIndex = this.m_selectionStart;
			} else if (clicks == 2) {
				if (!this.m_multiline) {
					SelectAll();
				}
			}
		}
		this.m_isMouseDown = true;
		this.m_showCursor = true;
		StartTimer(this.m_timerID, TICK);
		Invalidate();
	}

	public void OnMouseMove(POINT mp, MouseButtonsA button, int clicks, int delta) {
		super.OnMouseMove(mp.Clone(), button, clicks, delta);
		if (this.m_isMouseDown) {
			int linesCount = this.m_lines.size();
			if (linesCount > 0) {
				int eIndex = -1;
				CHScrollBarMe hScrollBar = GetHScrollBar();
				CVScrollBarMe vScrollBar = GetVScrollBar();
				int scrollH = (hScrollBar != null) && (hScrollBar.IsVisible()) ? hScrollBar.GetPos() : 0;
				int scrollV = (vScrollBar != null) && (vScrollBar.IsVisible()) ? vScrollBar.GetPos() : 0;
				scrollH += this.m_offsetX;
				POINT point = mp;
				if (point.x < 0) {
					point.x = 0;
				}
				if (point.y < 0) {
					point.y = 0;
				}
				int x = point.x + scrollH;
				int y = point.y + scrollV;
				RECTF lastRange = new RECTF();
				int rangeSize = this.m_ranges.size();
				if (rangeSize > 0) {
					lastRange = (RECTF) this.m_ranges.get(rangeSize - 1);
				}
				for (int i = 0; i < linesCount; i++) {
					WordLine line = (WordLine) this.m_lines.get(i);
					for (int j = line.m_start; j <= line.m_end; j++) {
						RECTF rect = (RECTF) this.m_ranges.get(j);
						if (i == linesCount - 1) {
							rect.bottom += 3.0F;
						}
						if (eIndex == -1) {
							if ((y >= rect.top) && (y <= rect.bottom)) {
								float sub = (rect.right - rect.left) / 2.0F;
								if (((x >= rect.left - sub) && (x <= rect.right - sub))
										|| ((j == line.m_start) && (x <= rect.left + sub))
										|| ((j == line.m_end) && (x >= rect.right - sub))) {
									if ((j == line.m_end) && (x >= rect.right - sub)) {
										eIndex = j + 1;
									} else {
										eIndex = j;
									}
								}
							}
						}
					}
					if (eIndex != -1) {
						break;
					}
				}

				if (eIndex != -1) {
					this.m_stopMovingIndex = eIndex;
				}
				if (this.m_startMovingIndex == this.m_stopMovingIndex) {
					this.m_selectionStart = this.m_startMovingIndex;
					this.m_selectionLength = 0;
				} else {
					this.m_selectionStart = (this.m_startMovingIndex < this.m_stopMovingIndex ? this.m_startMovingIndex
							: this.m_stopMovingIndex);
					this.m_selectionLength = Math.abs(this.m_startMovingIndex - this.m_stopMovingIndex);
				}
				Invalidate();
			}
		}
	}

	public void OnMouseUp(POINT mp, MouseButtonsA button, int clicks, int delta) {
		this.m_isMouseDown = false;
		super.OnMouseUp(mp.Clone(), button, clicks, delta);
	}

	public void OnPaintForeground(CPaintMe paint, RECT clipRect) {
		int width = GetWidth();
		int height = GetHeight();
		if ((width > 0) && (height > 0)) {
			int lineHeight = this.m_multiline ? this.m_lineHeight : height;
			PADDING padding = GetPadding();
			CControlHostMe host = GetNative().GetHost();
			RECT rect = new RECT(0, 0, width, height);
			FONT font = GetFont();
			SIZEF tSize = paint.TextSizeF(" ", font);
			CHScrollBarMe hScrollBar = GetHScrollBar();
			CVScrollBarMe vScrollBar = GetVScrollBar();
			int vWidth = (vScrollBar != null) && (vScrollBar.IsVisible())
					? vScrollBar.GetWidth() - padding.left - padding.right
					: 0;
			int scrollH = (hScrollBar != null) && (hScrollBar.IsVisible()) ? hScrollBar.GetPos() : 0;
			int scrollV = (vScrollBar != null) && (vScrollBar.IsVisible()) ? vScrollBar.GetPos() : 0;
			float strX = padding.left + 1;
			String text = GetText();
			if (text == null) {
				text = "";
			}
			int length = text.length();
			SIZEF bSize = paint.TextSizeF("0", font);
			if (this.m_textChanged) {
				int line = 0;
				int count = 0;
				this.m_textChanged = (!this.m_textChanged);
				this.m_lines.clear();
				this.m_ranges.clear();
				this.m_wordsSize.clear();
				for (int i = 0; i < length; i++) {
					if (i == 0) {
						count = 0;
						line++;
						strX = padding.left + 1;
						this.m_lines.add(new WordLine(i, i));
					}
					char ch = text.charAt(i);
					String dch = new Character(ch).toString();
					if (ch == '\t') {
						int addCount = 4 - count % 4;
						bSize.cx *= addCount;
						tSize.cy = bSize.cy;
						count += addCount;
					} else {
						if (this.m_passwordChar != 0) {
							dch = new Character(this.m_passwordChar).toString();
						}
						tSize = paint.TextSizeF(dch, font);
						if (ch == '\n') {
							tSize.cx = 0.0F;
						}
						count++;
					}

					if (this.m_multiline) {
						boolean isNextLine = false;
						if (ch == '\r') {
							isNextLine = true;
							tSize.cx = 0.0F;

						} else if (this.m_wordWrap) {
							if (strX + tSize.cx > width - vWidth) {
								isNextLine = true;
							}
						}

						if (isNextLine) {
							count = 0;
							line++;
							strX = padding.left + 1;
							this.m_lines.add(new WordLine(i, i));
						} else {
							this.m_lines.set(line - 1,
									new WordLine(((WordLine) this.m_lines.get(line - 1)).m_start, i));
						}
					} else {
						this.m_lines.set(line - 1, new WordLine(((WordLine) this.m_lines.get(line - 1)).m_start, i));
					}
					if (ch > 1000) {
						tSize.cx += 1.0F;
					}
					RECTF rangeRect = new RECTF(strX, padding.top + (line - 1) * lineHeight, strX + tSize.cx,
							padding.top + line * lineHeight);
					this.m_ranges.add(rangeRect);
					this.m_wordsSize.add(tSize);
					strX = rangeRect.right;
				}

				if (this.m_rightToLeft) {
					int lcount = this.m_lines.size();
					for (int i = 0; i < lcount; i++) {
						WordLine ln = (WordLine) this.m_lines.get(i);
						float lw = width - vWidth - (((RECTF) this.m_ranges.get(ln.m_end)).right
								- ((RECTF) this.m_ranges.get(ln.m_start)).left) - 2.0F;
						if (lw > 0.0F) {
							for (int j = ln.m_start; j <= ln.m_end; j++) {
								RECTF rangeRect = (RECTF) this.m_ranges.get(j);
								rangeRect.left += lw;
								rangeRect.right += lw;
								this.m_ranges.set(j, rangeRect);
							}
						}
					}
				}
				Update();
			}
			scrollH += this.m_offsetX;
			RECT tempRect = new RECT();
			int rangesSize = this.m_ranges.size();
			int offsetX = this.m_offsetX;
			if (!this.m_multiline) {
				RECTF firstRange = new RECTF();
				RECTF lastRange = new RECTF();
				if (rangesSize > 0) {
					firstRange = (RECTF) this.m_ranges.get(0);
					lastRange = (RECTF) this.m_ranges.get(rangesSize - 1);
				}
				scrollH -= offsetX;

				if (this.m_textAlign == HorizontalAlignA.Center) {
					offsetX = -(int) (width - padding.right - (lastRange.right - firstRange.left)) / 2;

				} else if (this.m_textAlign == HorizontalAlignA.Right) {
					offsetX = -(int) (width - padding.right - (lastRange.right - firstRange.left) - 3.0F);

				} else if (lastRange.right > width - padding.right) {
					int alwaysVisibleIndex = this.m_selectionStart + this.m_selectionLength;
					if (this.m_startMovingIndex != -1) {
						alwaysVisibleIndex = this.m_startMovingIndex;
					}
					if (this.m_stopMovingIndex != -1) {
						alwaysVisibleIndex = this.m_stopMovingIndex;
					}
					if (alwaysVisibleIndex > rangesSize - 1) {
						alwaysVisibleIndex = rangesSize - 1;
					}
					if (alwaysVisibleIndex != -1) {
						RECTF alwaysVisibleRange = (RECTF) this.m_ranges.get(alwaysVisibleIndex);
						int cw = width - padding.left - padding.right;
						if (alwaysVisibleRange.left - offsetX > cw - 10) {
							offsetX = (int) alwaysVisibleRange.right - cw + 3;
							if (offsetX < 0) {
								offsetX = 0;
							}
						} else if (alwaysVisibleRange.left - offsetX < 10.0F) {
							offsetX -= (int) bSize.cx * 4;
							if (offsetX < 0) {
								offsetX = 0;
							}
						}
						if (offsetX > lastRange.right - cw) {
							offsetX = (int) lastRange.right - cw + 3;
						}

					}

				} else if (this.m_textAlign == HorizontalAlignA.Right) {
					offsetX = -(int) (width - padding.right - (lastRange.right - firstRange.left) - 3.0F);
				} else {
					offsetX = 0;
				}

				this.m_offsetX = offsetX;
				scrollH += this.m_offsetX;
			}
			int lineCount = this.m_lines.size();

			ArrayList<RECTF> selectedRanges = new ArrayList();
			ArrayList<RECT> selectedWordsRanges = new ArrayList();
			ArrayList<Character> selectedWords = new ArrayList();
			for (int i = 0; i < lineCount; i++) {
				WordLine line = (WordLine) this.m_lines.get(i);
				for (int j = line.m_start; j <= line.m_end; j++) {
					char ch = text.charAt(j);
					if (ch != '\t') {
						if (this.m_passwordChar > 0) {
							ch = this.m_passwordChar;
						}
					}
					RECTF rangeRect = ((RECTF) this.m_ranges.get(j)).Clone();
					rangeRect.left -= scrollH;
					rangeRect.top -= scrollV;
					rangeRect.right -= scrollH;
					rangeRect.bottom -= scrollV;
					RECT rRect = new RECT(rangeRect.left,
							rangeRect.top + (lineHeight - ((SIZEF) this.m_wordsSize.get(j)).cy) / 2.0F - 1.0F,
							rangeRect.right,
							rangeRect.top + (lineHeight + ((SIZEF) this.m_wordsSize.get(j)).cy) / 2.0F - 1.0F);
					if (rRect.right == rRect.left) {
						rRect.right = (rRect.left + 1);
					}
					RefObject<RECT> tempRef_tempRect = new RefObject(tempRect);
					RefObject<RECT> tempRef_rRect = new RefObject(rRect);
					RefObject<RECT> tempRef_rect = new RefObject(rect);
					boolean tempVar = host.GetIntersectRect(tempRef_tempRect, tempRef_rRect, tempRef_rect) > 0;
					if (tempVar) {
						if (this.m_selectionLength > 0) {
							if ((j >= this.m_selectionStart) && (j < this.m_selectionStart + this.m_selectionLength)) {
								selectedWordsRanges.add(rRect);
								selectedRanges.add(rangeRect);
								selectedWords.add(Character.valueOf(ch));
								continue;
							}
						}
						paint.DrawText(new Character(ch).toString(), GetPaintingForeColor(), font, rRect);
					}
				}
			}

			int selectedRangesSize = selectedRanges.size();
			if (selectedRangesSize > 0) {
				int sIndex = 0;
				int right = 0;
				for (int i = 0; i < selectedRangesSize; i++) {
					RECTF rRect = (RECTF) selectedRanges.get(i);
					RECTF sRect = (RECTF) selectedRanges.get(sIndex);
					boolean newLine = rRect.top != sRect.top;
					if ((newLine) || (i == selectedRangesSize - 1)) {
						int eIndex = i == selectedRangesSize - 1 ? i : i - 1;
						RECTF eRect = (RECTF) selectedRanges.get(eIndex);
						RECT unionRect = new RECT(sRect.left, sRect.top, eRect.right + 1.0F, sRect.bottom + 1.0F);
						if (newLine) {
							unionRect.right = right;
						}
						paint.FillRect(this.m_selectionBackColor, unionRect);
						for (int j = sIndex; j <= eIndex; j++) {
							paint.DrawText(((Character) selectedWords.get(j)).toString(), this.m_selectionForeColor,
									font, (RECT) selectedWordsRanges.get(j));
						}
						sIndex = i;
					}
					right = (int) rRect.right;
				}
				selectedRanges.clear();
				selectedWords.clear();
				selectedWordsRanges.clear();
			}
			if ((IsFocused()) && (!this.m_readOnly) && (this.m_selectionLength == 0)
					&& ((this.m_isKeyDown) || (this.m_showCursor))) {
				int index = this.m_selectionStart;
				if (index < 0) {
					index = 0;
				}
				if (index > length) {
					index = length;
				}

				int cursorX = offsetX;
				int cursorY = 0;
				if (length > 0) {
					if (index == 0) {
						if (rangesSize > 0) {
							cursorX = (int) ((RECTF) this.m_ranges.get(0)).left;
							cursorY = (int) ((RECTF) this.m_ranges.get(0)).top;
						}
					} else {
						cursorX = (int) Math.ceil(((RECTF) this.m_ranges.get(index - 1)).right) + 2;
						cursorY = (int) Math.ceil(((RECTF) this.m_ranges.get(index - 1)).top) + 1;
					}
					cursorY += lineHeight / 2 - (int) tSize.cy / 2;
				} else {
					cursorY = lineHeight / 2 - (int) tSize.cy / 2;
				}

				if ((this.m_isKeyDown) || (this.m_showCursor)) {
					RECT cRect = new RECT(cursorX - scrollH - 1, cursorY - scrollV, cursorX - scrollH + 1,
							cursorY + tSize.cy - scrollV);
					paint.FillRect(GetForeColor(), cRect);
				}

			} else if ((!IsFocused()) && (text.length() == 0)) {
				if ((this.m_tempText != null) && (this.m_tempText.length() > 0)) {
					SIZE pSize = paint.TextSize(this.m_tempText, font);
					RECT pRect = new RECT();
					pRect.left = padding.left;
					pRect.top = ((lineHeight - pSize.cy) / 2);
					pRect.right = (pRect.left + pSize.cx);
					pRect.bottom = (pRect.top + pSize.cy);
					paint.DrawText(this.m_tempText, this.m_tempTextForeColor, font, pRect);
				}
			}
		}
	}

	public void OnPaste() {
		if (!this.m_readOnly) {
			CControlHostMe host = GetNative().GetHost();
			String insert = host.Paste();
			if ((insert != null) && (insert.length() > 0)) {
				int oldLines = this.m_lines.size();
				String redotext = GetText();
				InsertWord(insert);
				OnTextChanged();
				if (this.m_textChanged) {
					if (redotext != null) {
						this.m_undoStack.push(redotext);
					}
				}
				Invalidate();
				int newLines = this.m_lines.size();
				if (newLines != oldLines) {
					CVScrollBarMe vScrollBar = GetVScrollBar();
					if (vScrollBar != null) {
						vScrollBar.SetPos(vScrollBar.GetPos() + this.m_lineHeight * (newLines - oldLines));
						Invalidate();
					}
				}
				Update();
				super.OnPaste();
			}
		}
	}

	public void OnTabStop() {
		super.OnTabStop();
		if (!this.m_multiline) {
			if (GetText() != null) {
				int textSize = GetText().length();
				if (textSize > 0) {
					this.m_selectionStart = 0;
					this.m_selectionLength = textSize;
					OnTimer(this.m_timerID);
				}
			}
		}
	}

	public void OnSizeChanged() {
		super.OnSizeChanged();
		if (this.m_wordWrap) {
			this.m_textChanged = true;
			Invalidate();
		}
	}

	public void OnTextChanged() {
		this.m_textChanged = true;
		super.OnTextChanged();
	}

	public void OnTimer(int timerID) {
		super.OnTimer(timerID);
		if (this.m_timerID == timerID) {
			if ((IsVisible()) && (IsFocused()) && (!this.m_textChanged)) {
				this.m_showCursor = (!this.m_showCursor);
				Invalidate();
			}
		}
	}

	public void Redo() {
		if (CanRedo()) {
			if (GetText() != null) {
				this.m_undoStack.push(GetText());
			}
			SetText((String) this.m_redoStack.pop());
		}
	}

	public void SelectAll() {
		this.m_selectionStart = 0;
		if (GetText() != null) {
			this.m_selectionLength = GetText().length();
		}
	}

	protected void SetMovingIndex(int sIndex, int eIndex) {
		if (GetText() != null) {
			int textSize = GetText().length();
			if (textSize > 0) {
				if (sIndex < 0) {
					sIndex = 0;
				}
				if (sIndex > textSize) {
					sIndex = textSize;
				}
				if (eIndex < 0) {
					eIndex = 0;
				}
				if (eIndex > textSize) {
					eIndex = textSize;
				}
				this.m_startMovingIndex = sIndex;
				this.m_stopMovingIndex = eIndex;
				this.m_selectionStart = Math.min(this.m_startMovingIndex, this.m_stopMovingIndex);
				this.m_selectionLength = Math.abs(this.m_startMovingIndex - this.m_stopMovingIndex);
			}
		}
	}

	public void SetProperty(String name, String value) {
		if (name.equals("lineheight")) {
			SetLineHeight(CStrMe.ConvertStrToInt(value));
		} else if (name.equals("multiline")) {
			SetMultiline(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("passwordchar")) {
			SetPasswordChar(value.toCharArray()[0]);
		} else if (name.equals("readonly")) {
			SetReadOnly(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("righttoleft")) {
			SetRightToLeft(CStrMe.ConvertStrToBool(value));
		} else if (name.equals("selectionbackcolor")) {
			SetSelectionBackColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("selectionforecolor")) {
			SetSelectionForeColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("temptext")) {
			SetTempText(value);
		} else if (name.equals("temptextforecolor")) {
			SetTempTextForeColor(CStrMe.ConvertStrToColor(value));
		} else if (name.equals("textalign")) {
			SetTextAlign(CStrMe.ConvertStrToHorizontalAlign(value));
		} else if (name.equals("wordwrap")) {
			SetWordWrap(CStrMe.ConvertStrToBool(value));
		} else {
			super.SetProperty(name, value);
		}
	}

	public void Undo() {
		if (CanUndo()) {
			if (GetText() != null) {
				this.m_redoStack.push(GetText());
			}
			SetText((String) this.m_undoStack.pop());
		}
	}

	public void Update() {
		INativeBaseMe inative = GetNative();
		if (inative != null) {
			CVScrollBarMe vScrollBar = GetVScrollBar();
			if (vScrollBar != null) {
				vScrollBar.SetLineSize(this.m_lineHeight);
			}
		}
		super.Update();
	}
}
