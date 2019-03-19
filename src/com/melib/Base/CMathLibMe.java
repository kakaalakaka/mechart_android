package com.melib.Base;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.melib.Indicator.LPDATA;

/**
 * @file
 * @copyright defined in mechart_android/LICENSE
 */
public class CMathLibMe {

	public static void M001(int index, int n, double s, double m, double high, double low, double hhv, double llv,
			int last_state, double last_sar, double last_af, RefObject<Integer> state, RefObject<Double> af,
			RefObject<Double> sar) {

		if (index >= n) {
			if (index == n) {
				af.argvalue = Double.valueOf(s);
				if (llv < low) {
					sar.argvalue = Double.valueOf(llv);
					state.argvalue = Integer.valueOf(1);
				}
				if (hhv > high) {
					sar.argvalue = Double.valueOf(hhv);
					state.argvalue = Integer.valueOf(2);
				}
			} else {
				state.argvalue = Integer.valueOf(last_state);
				af.argvalue = Double.valueOf(last_af);
				RefObject<Double> localRefObject;
				if (((Integer) state.argvalue).intValue() == 1) {
					if (high > hhv) {
						localRefObject = af;
						localRefObject.argvalue = Double.valueOf(((Double) localRefObject.argvalue).doubleValue() + s);
						if (((Double) af.argvalue).doubleValue() > m) {
							af.argvalue = Double.valueOf(m);
						}
					}
					sar.argvalue = Double.valueOf(last_sar + ((Double) af.argvalue).doubleValue() * (hhv - last_sar));
					if (((Double) sar.argvalue).doubleValue() < low) {
						state.argvalue = Integer.valueOf(1);
					} else {
						state.argvalue = Integer.valueOf(3);
					}
				} else if (((Integer) state.argvalue).intValue() == 2) {
					if (low < llv) {
						localRefObject = af;
						localRefObject.argvalue = Double.valueOf(((Double) localRefObject.argvalue).doubleValue() + s);
						if (((Double) af.argvalue).doubleValue() > m)
							af.argvalue = Double.valueOf(m);
					}
					sar.argvalue = Double.valueOf(last_sar + ((Double) af.argvalue).doubleValue() * (llv - last_sar));
					if (((Double) sar.argvalue).doubleValue() > high) {
						state.argvalue = Integer.valueOf(2);
					} else {
						state.argvalue = Integer.valueOf(4);
					}
				} else if (((Integer) state.argvalue).intValue() == 3) {
					sar.argvalue = Double.valueOf(hhv);
					if (((Double) sar.argvalue).doubleValue() > high) {
						state.argvalue = Integer.valueOf(2);
					} else {
						state.argvalue = Integer.valueOf(4);
					}
					af.argvalue = Double.valueOf(s);
				} else if (((Integer) state.argvalue).intValue() == 4) {
					sar.argvalue = Double.valueOf(llv);
					if (((Double) sar.argvalue).doubleValue() < low) {
						state.argvalue = Integer.valueOf(1);
					} else {
						state.argvalue = Integer.valueOf(3);
					}
					af.argvalue = Double.valueOf(s);
				}
			}
		}
	}

	public static double M002(double value, double[] listForAvedev, int listForAvedev_length, double avg) {
		int i = 0;
		if (listForAvedev_length > 0) {
			double sum = Math.abs(value - avg);
			for (i = 0; i < listForAvedev_length; i++) {
				sum += Math.abs(listForAvedev[i] - avg);
			}
			return sum / listForAvedev_length;
		}
		return 0.0D;
	}

	public static double M003(int index, int n, double value, LPDATA last_MA) {
		double sum = 0.0D;
		if (last_MA.mode == 0) {
			sum = last_MA.sum + value;
		} else {
			if (index > n - 1) {
				sum = last_MA.lastvalue * n;
				sum -= last_MA.first_value;
			} else {
				sum = last_MA.lastvalue * index;
				n = index + 1;
			}
			sum += value;
		}
		return sum / n;
	}

	public static double M004(int index, int n, double value, LPDATA last_SUM) {
		double sum = 0.0D;
		if (last_SUM.mode == 0) {
			sum = last_SUM.sum + value;
		} else {
			sum = last_SUM.lastvalue;
			if (index > n - 1) {
				sum -= last_SUM.first_value;
			}
			sum += value;
		}
		return sum;
	}

	public static double M005(int n, int weight, double value, double lastWMA) {
		return (value * weight + (n - weight) * lastWMA) / n;
	}

	public static double M006(int n, double value, double lastEMA) {
		return (value * 2.0D + lastEMA * (n - 1)) / (n + 1);
	}

	public static double M007(double[] list, int length, double avg, double standardDeviation) {
		int i = 0;
		if (length > 0) {
			double sum = 0.0D;
			for (i = 0; i < length; i++) {
				sum += (list[i] - avg) * (list[i] - avg);
			}
			return standardDeviation * Math.sqrt(sum / length);
		}
		return 0.0D;
	}

	public static double GetMin(double[] list, int length) {
		double min = 0.0D;
		int i = 0;
		for (i = 0; i < length; i++) {
			if (i == 0) {
				min = list[i];
			} else if (min > list[i]) {
				min = list[i];
			}
		}

		return min;
	}

	public static double GetMax(double[] list, int length) {
		double max = 0.0D;
		int i = 0;
		for (i = 0; i < length; i++) {
			if (i == 0) {
				max = list[i];

			} else if (max < list[i]) {
				max = list[i];
			}
		}

		return max;
	}

	public static double M010(double[] list, int length) {
		int i = 0;
		double sum = 0.0D;
		if (length > 0) {
			for (i = 0; i < length; i++) {
				sum += list[i];
			}
			return sum / length;
		}
		return 0.0D;
	}

	public static double M011(double[] list, int length) {
		double sum = 0.0D;
		int i = 0;
		for (i = 0; i < length; i++) {
			sum += list[i];
		}
		return sum;
	}

	public static int M012(double min, double max, int yLen, int maxSpan, int minSpan, int defCount,
			RefObject<Double> step, RefObject<Integer> digit) {
		double sub = max - min;
		int nMinCount = (int) Math.ceil(yLen / maxSpan);
		int nMaxCount = (int) Math.floor(yLen / minSpan);
		int nCount = defCount;
		double logStep = sub / nCount;
		boolean start = false;
		double divisor = 0.0D;
		int i = 0;
		int nTemp = 0;
		step.argvalue = Double.valueOf(0.0D);
		digit.argvalue = Integer.valueOf(0);
		nCount = Math.max(nMinCount, nCount);
		nCount = Math.min(nMaxCount, nCount);
		nCount = Math.max(nCount, 1);
		for (i = 15; i >= -6; i--) {
			divisor = Math.pow(10.0D, i);
			Object localObject;
			Integer localInteger1;
			Integer localInteger2;
			if (divisor < 1.0D) {
				localObject = digit;
				localInteger1 = (Integer) ((RefObject) localObject).argvalue;
				localInteger2 = (Integer) (((RefObject) localObject).argvalue = Integer
						.valueOf(((Integer) ((RefObject) localObject).argvalue).intValue() + 1));
			}
			nTemp = (int) Math.floor(logStep / divisor);
			if (start) {
				if (nTemp < 4) {
					if (((Integer) digit.argvalue).intValue() <= 0)
						break;
					localObject = digit;
					localInteger1 = (Integer) ((RefObject) localObject).argvalue;
					localInteger2 = (Integer) (((RefObject) localObject).argvalue = Integer
							.valueOf(((Integer) ((RefObject) localObject).argvalue).intValue() - 1));
					break;
				}

				if ((nTemp >= 4) && (nTemp <= 6)) {
					nTemp = 5;
					localObject = step;
					((RefObject) localObject).argvalue = Double
							.valueOf(((Double) ((RefObject) localObject).argvalue).doubleValue() + nTemp * divisor);
					break;
				}

				localObject = step;
				((RefObject) localObject).argvalue = Double
						.valueOf(((Double) ((RefObject) localObject).argvalue).doubleValue() + 10.0D * divisor);
				if (((Integer) digit.argvalue).intValue() <= 0)
					break;
				localObject = digit;
				localInteger1 = (Integer) ((RefObject) localObject).argvalue;
				localInteger2 = (Integer) (((RefObject) localObject).argvalue = Integer
						.valueOf(((Integer) ((RefObject) localObject).argvalue).intValue() - 1));
				break;
			}
			if (nTemp > 0) {
				step.argvalue = Double.valueOf(nTemp * divisor + ((Double) step.argvalue).doubleValue());
				logStep -= ((Double) step.argvalue).doubleValue();
				start = true;
			}
		}
		return 0;
	}

	public static void M013(int index, double close, double p, RefObject<Double> sxp, RefObject<Integer> sxi,
			RefObject<Double> exp, RefObject<Integer> exi, RefObject<Integer> state, RefObject<Integer> cStart,
			RefObject<Integer> cEnd, RefObject<Double> k, RefObject<Double> b) {
		boolean reverse = false;
		boolean ex = false;
		if (index == 0) {
			sxp.argvalue = Double.valueOf(close);
			exp.argvalue = Double.valueOf(close);
		} else if (index == 1) {
			if (close >= ((Double) exp.argvalue).doubleValue()) {
				state.argvalue = Integer.valueOf(0);
			} else {
				state.argvalue = Integer.valueOf(1);
			}
			exp.argvalue = Double.valueOf(close);
			exi.argvalue = Integer.valueOf(1);
		} else {
			if (((Integer) state.argvalue).intValue() == 0) {
				if (100.0D * (((Double) exp.argvalue).doubleValue() - close)
						/ ((Double) exp.argvalue).doubleValue() > p) {
					reverse = true;
				} else if (close >= ((Double) exp.argvalue).doubleValue()) {
					ex = true;
				}

			} else if (100.0D * (close - ((Double) exp.argvalue).doubleValue())
					/ ((Double) exp.argvalue).doubleValue() > p) {
				reverse = true;
			} else if (close <= ((Double) exp.argvalue).doubleValue()) {
				ex = true;
			}

			if (reverse == true) {
				if (((Integer) state.argvalue).intValue() == 1) {
					state.argvalue = Integer.valueOf(0);
				} else {
					state.argvalue = Integer.valueOf(1);
				}
				k.argvalue = Double
						.valueOf((((Double) exp.argvalue).doubleValue() - ((Double) sxp.argvalue).doubleValue())
								/ (((Integer) exi.argvalue).intValue() - ((Integer) sxi.argvalue).intValue()));
				b.argvalue = Double.valueOf(((Double) exp.argvalue).doubleValue()
						- ((Double) k.argvalue).doubleValue() * ((Integer) exi.argvalue).intValue());
				cStart.argvalue = sxi.argvalue;
				cEnd.argvalue = exi.argvalue;
				sxi.argvalue = exi.argvalue;
				sxp.argvalue = exp.argvalue;
				exi.argvalue = Integer.valueOf(index);
				exp.argvalue = Double.valueOf(close);
			} else if (ex == true) {
				exp.argvalue = Double.valueOf(close);
				exi.argvalue = Integer.valueOf(index);
				k.argvalue = Double
						.valueOf((((Double) exp.argvalue).doubleValue() - ((Double) sxp.argvalue).doubleValue())
								/ (((Integer) exi.argvalue).intValue() - ((Integer) sxi.argvalue).intValue()));
				b.argvalue = Double.valueOf(((Double) exp.argvalue).doubleValue()
						- ((Double) k.argvalue).doubleValue() * ((Integer) exi.argvalue).intValue());
				cStart.argvalue = sxi.argvalue;
				cEnd.argvalue = exi.argvalue;
			} else {
				k.argvalue = Double.valueOf((close - ((Double) exp.argvalue).doubleValue())
						/ (index - ((Integer) exi.argvalue).intValue()));
				b.argvalue = Double.valueOf(close - ((Double) k.argvalue).doubleValue() * index);
				cStart.argvalue = exi.argvalue;
				cEnd.argvalue = Integer.valueOf(index);
			}
		}
	}

	public static void M014(double[] list, int length, RefObject<Float> k, RefObject<Float> b) {
		int i = 0;
		double sumX = 0.0D;
		double sumY = 0.0D;
		double sumUp = 0.0D;
		double sumDown = 0.0D;
		double xAvg = 0.0D;
		double yAvg = 0.0D;
		k.argvalue = Float.valueOf(0.0F);
		b.argvalue = Float.valueOf(0.0F);
		if (length > 1) {
			for (i = 0; i < length; i++) {
				sumX += i + 1;
				sumY += list[i];
			}
			xAvg = sumX / length;
			yAvg = sumY / length;
			for (i = 0; i < length; i++) {
				sumUp += (i + 1 - xAvg) * (list[i] - yAvg);
				sumDown += (i + 1 - xAvg) * (i + 1 - xAvg);
			}
			k.argvalue = Float.valueOf((float) (sumUp / sumDown));
			b.argvalue = Float.valueOf((float) (yAvg - ((Float) k.argvalue).floatValue() * xAvg));
		}
	}

	public static double M015(double close, double lastSma, int n, int m) {
		return (close * m + lastSma * (n - m)) / n;
	}

	public static void M105(int x1, int y1, int x2, int y2, RefObject<Integer> x, RefObject<Integer> y,
			RefObject<Integer> w, RefObject<Integer> h) {
		x.argvalue = Integer.valueOf(x1 < x2 ? x1 : x2);
		y.argvalue = Integer.valueOf(y1 < y2 ? y1 : y2);
		w.argvalue = Integer.valueOf(Math.abs(x1 - x2));
		h.argvalue = Integer.valueOf(Math.abs(y1 - y2));
		if (((Integer) w.argvalue).intValue() <= 0) {
			w.argvalue = Integer.valueOf(4);
		}
		if (((Integer) h.argvalue).intValue() <= 0) {
			h.argvalue = Integer.valueOf(4);
		}
	}

	public static double M106(float x1, float y1, float x2, float y2, float oX, float oY) {

		if (x1 - oX != x2 - oX) {
			return (y2 - oY - (y1 - oY)) / (x2 - oX - (x1 - oX));
		}
		return 0.0D;
	}

	public static void M107(float x1, float y1, float x2, float y2, float oX, float oY, RefObject<Float> k,
			RefObject<Float> b) {
		k.argvalue = Float.valueOf(0.0F);
		b.argvalue = Float.valueOf(0.0F);
		if (x1 - oX != x2 - oX) {
			k.argvalue = Float.valueOf((y2 - oY - (y1 - oY)) / (x2 - oX - (x1 - oX)));
			b.argvalue = Float.valueOf(y1 - oY - ((Float) k.argvalue).floatValue() * (x1 - oX));
		}
	}

	public static void M108(float width, float height, RefObject<Float> a, RefObject<Float> b) {
		a.argvalue = Float.valueOf(width / 2.0F);
		b.argvalue = Float.valueOf(height / 2.0F);
	}

	public static boolean M109(float x, float y, float oX, float oY, float a, float b) {
		x -= oX;
		y -= oY;
		if ((a == 0.0F) && (b == 0.0F) && (x == 0.0F) && (y == 0.0F)) {
			return true;
		}
		if (a == 0.0F) {
			if ((x == 0.0F) && (y >= -b) && (y <= b)) {
				return false;
			}
		}
		if (b == 0.0F) {
			if ((y == 0.0F) && (x >= -a) && (x <= a)) {
				return true;
			}
		}
		if ((x * x / (a * a) + y * y / (b * b) >= 0.8D) && (x * x / (a * a) + y * y / (b * b) <= 1.2D)) {
			return true;
		}
		return false;
	}

	public static void M110(float x1, float y1, float x2, float y2, float x3, float y3, RefObject<Float> oX,
			RefObject<Float> oY, RefObject<Float> r) {
		oX.argvalue = Float.valueOf(((y3 - y1) * (y2 * y2 - y1 * y1 + x2 * x2 - x1 * x1)
				+ (y2 - y1) * (y1 * y1 - y3 * y3 + x1 * x1 - x3 * x3))
				/ (2.0F * (x2 - x1) * (y3 - y1) - 2.0F * (x3 - x1) * (y2 - y1)));

		oY.argvalue = Float.valueOf(((x3 - x1) * (x2 * x2 - x1 * x1 + y2 * y2 - y1 * y1)
				+ (x2 - x1) * (x1 * x1 - x3 * x3 + y1 * y1 - y3 * y3))
				/ (2.0F * (y2 - y1) * (x3 - x1) - 2.0F * (y3 - y1) * (x2 - x1)));

		r.argvalue = Float.valueOf(
				(float) Math.sqrt((x1 - ((Float) oX.argvalue).floatValue()) * (x1 - ((Float) oX.argvalue).floatValue())
						+ (y1 - ((Float) oY.argvalue).floatValue()) * (y1 - ((Float) oY.argvalue).floatValue())));
	}

	public static int M112(int index) {
		if (index < 1) {
			return 0;
		}

		int i = 0;
		int result = 0;
		int[] vList = new int[index];
		for (i = 0; i <= index - 1; i++) {
			if ((i == 0) || (i == 1)) {
				vList[i] = 1;
			} else {
				vList[i] = (vList[(i - 1)] + vList[(i - 2)]);
			}
		}
		result = vList[(index - 1)];
		return result;
	}

	public static void M124(float x1, float y1, float x2, float y2, float x3, float y3, RefObject<Float> x4,
			RefObject<Float> y4) {
		x4.argvalue = Float.valueOf(x1 + x3 - x2);
		y4.argvalue = Float.valueOf(y1 + y3 - y2);
	}

	public static int IsLeapYear(int year) {
		return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0) ? 1 : 0;
	}

	public static int[][] m_ytab = { { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
			{ 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };

	public static double M129(int tm_year, int tm_mon, int tm_mday, int tm_hour, int tm_min, int tm_sec, int tm_msec) {
		double tn = 0.0D;
		tm_mon -= 2;
		if (tm_mon <= 0) {
			tm_mon += 12;
			tm_year--;
		}
		double day = tm_year / 4 - tm_year / 100 + tm_year / 400 + 367 * tm_mon / 12 + tm_mday;
		double year = tm_year * 365 - 719499;
		tn = (day + year) * 24.0D * 60.0D * 60.0D + tm_hour * 3600 + tm_min * 60 + tm_sec;
		return tn + tm_msec / 1000;
	}

	public static void M130(double num, RefObject<Integer> tm_year, RefObject<Integer> tm_mon,
			RefObject<Integer> tm_mday, RefObject<Integer> tm_hour, RefObject<Integer> tm_min,
			RefObject<Integer> tm_sec, RefObject<Integer> tm_msec) {
		long date = (long) num;
		int seconds = (int) (date % 86400L);
		int days = (int) (date / 86400L);
		int startYear = 1970;
		tm_sec.argvalue = Integer.valueOf(seconds % 60);
		tm_min.argvalue = Integer.valueOf(seconds % 3600 / 60);
		tm_hour.argvalue = Integer.valueOf(seconds / 3600);
		int index = 0;
		while (days >= (index = IsLeapYear(startYear) == 1 ? 366 : 365)) {
			days -= index;
			startYear++;
		}
		tm_year.argvalue = Integer.valueOf(startYear);
		int monthIndex = 0;
		while (days >= m_ytab[IsLeapYear(startYear)][monthIndex]) {
			days -= m_ytab[IsLeapYear(startYear)][monthIndex];
			monthIndex++;
		}
		tm_mon.argvalue = Integer.valueOf(monthIndex + 1);
		tm_mday.argvalue = Integer.valueOf(days + 1);
		tm_msec.argvalue = Integer.valueOf((int) (num * 1000.0D - Math.floor(num) * 1000.0D));
	}
}
