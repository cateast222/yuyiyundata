
package com.yuyiyun.YYdata.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class DateTimeUtil {
	public static final int Date_Contrast_YEAR = 0;
	public static final int Date_Contrast_MONTH = 1;
	public static final int Date_Contrast_DAY = 2;
	public static final int Date_Contrast_HOUR = 3;
	public static final int Date_Contrast_MINUTE = 4;
	public static final int Date_Contrast_SECOND = 5;

	// 日期权重 * 依照日期格式确定
	public static int yyyy_mm_dd = 0;
	public static int mm_dd_yyyy = 10;
	public static int yyyy_mm_dd_CN = 50;
	public static int yy_mm_dd = 100;
	public static int mm_dd_yy = 200;
	public static int yyyymmdd = 5000;
	public static int yymmdd = 10000;
	public static int mm_dd = 100000;
	public static int dd_mm_yyyy = 150000;
	public static int dd_mm = 180000;

	private String dateTimeStr = null;

	public static void main(String args[]) {
		Date dayBegin = getDayEnd(new Date());
		System.out.println(dayBegin);
		
//		Date date1 = stringToDate("2019-10-15 11:55:55", "");
//		Date date2 = stringToDate("2019-10-15 15:55:56", "");
//		System.out.println(date1.getTime() < date2.getTime());
//		boolean b = dateContrast(date1, date2, 6);
//		System.out.println(b);
//		System.out.println(date1.getHours()+"-"+date2.getHours());
//		boolean t =  date1.getHours() >= date2.getHours();
//		System.out.println(t);
//		String dataToString1 = dateToString(date1, "yyyy-MM-dd");
//		System.out.println(dataToString1);
//		Date stringToDate1 = stringToDate(dataToString1, "yyyy-MM-dd");
//		System.out.println(stringToDate1);
//
//		String dataToString2 = dateToString(date2, "yyyy-MM-dd");
//		System.out.println(dataToString2);
//		Date stringToDate2 = stringToDate(dataToString2, "yyyy-MM-dd");
//		System.out.println(stringToDate2);
//		System.out.println(stringToDate1.getTime() < stringToDate2.getTime());
//		// 把时间转换为字符串
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date = new Date(136464513123L);
//		String str = dateFormat.format(date);
//		System.out.println(str);
//
//		// 把字符串转化为时间
//		String str2 = "2018-07-19";
//		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");// 这里要与时间字符串的格式一样即可，否则报错
//		try {
//			Date date1 = dateFormat1.parse(str2);
//			System.out.println(date1);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * @Description 时间大小比较
	 * @Author duhao
	 * @Date 2019年9月16日上午12:58:56
	 * @param date1
	 * @param date2
	 * @param contrastObj
	 * 
	 *                    <pre>
	 * 	Date_Contrast_YEAR	年	"yyyy"
	 *                    </pre>
	 * 
	 *                    <pre>
	 * 	Date_Contrast_MONTH	月	"yyyy-MM"
	 *                    </pre>
	 * 
	 *                    <pre>
	 * 	Date_Contrast_DAY	天	"yyyy-MM-dd"
	 *                    </pre>
	 * 
	 *                    <pre>
	 * 	Date_Contrast_HOUR	时	"yyyy-MM-dd HH"
	 *                    </pre>
	 * 
	 *                    <pre>
	 * 	Date_Contrast_MINUTE	分	"yyyy-MM-dd HH:mm"
	 *                    </pre>
	 * 
	 *                    <pre>
	 * 	Date_Contrast_SECOND	秒	"yyyy-MM-dd HH:mm:ss"
	 *                    </pre>
	 * 
	 * @return <code>'<i>date1 < date2</i>' is 'True',or 'False'</code>
	 */
	public static boolean dateContrast(Date date1, Date date2, int contrastObj) {
		String[] patterns = { "yyyy", "yyyy-MM", "yyyy-MM-dd", "yyyy-MM-dd HH", "yyyy-MM-dd HH:mm",
				"yyyy-MM-dd HH:mm:ss" };
		if (contrastObj > Date_Contrast_SECOND || contrastObj < Date_Contrast_YEAR) {
			contrastObj = Date_Contrast_SECOND;
		}
		Date stringToDate1 = stringToDate(dateToString(date1, patterns[contrastObj]), patterns[contrastObj]);
		Date stringToDate2 = stringToDate(dateToString(date2, patterns[contrastObj]), patterns[contrastObj]);
		return stringToDate1.getTime() < stringToDate2.getTime();
	}

	/**
	 * @Description 字符串转时间（推荐使用，与<code> dataToString(Date date) </code> 相互使用）
	 * @Author duhao
	 * @Date 2019年9月15日下午11:44:24
	 * @param dateStr
	 * @return
	 */
	public static Date stringToDate(String dateStr) {
		return stringToDate(dateStr, null);
	}

	/**
	 * @Description 字符串转时间
	 * @Author duhao
	 * @Date 2019年9月15日下午11:29:32
	 * @param dateStr 字符串时间
	 * @param pattern 与时间字符串的格式一样即可，否则报错，默认为："yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static Date stringToDate(String dateStr, String pattern) {

		Date date = null;
		if ((pattern == null) || pattern.equals("")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat dateFormat = new SimpleDateFormat(pattern);// 这里要与时间字符串的格式一样即可，否则报错
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @Description 时间转字符串（推荐使用，与<code> stringToDate(String dateStr) </code> 相互使用）
	 * @Author duhao
	 * @Date 2019年9月15日下午11:46:19
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, null);
	}

	/**
	 * @Description 时间转字符串
	 * @Author duhao
	 * @Date 2019年9月15日下午11:25:13
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		if ((pattern == null) || pattern.equals("")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		String str = dateFormat.format(date);
		return str;
	}

	/**
	 * 
	 * @describe 获取当前时间
	 * @author duhao
	 * @date 2019年10月13日上午11:51:37
	 * @return String
	 */
	public static String getCurrentTime() {
		Calendar dateTime = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String curTime = null;

		try {
			curTime = sdf.format(dateTime.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return curTime;
	}

	/**
	 * 
	 * @describe 从给定的字符串中解析日期和时间 可解析不同地区、时区日期时间 可自定义解析的正则表达式和格式字符串
	 * @author duhao
	 * @date 2019年10月13日上午11:52:07
	 * @param str        包含日期的字符串
	 * @param local      区域代码，一般中英文时间可不传该参数，其他区域目前仅支持俄罗斯时间
	 * @param timezone   给定的时区，格式类似“+8”——东8区、“-4”——西4区
	 * @param dateRegex  自定义解析的正则表达式，需要和dateFormat同时使用
	 * @param dateFormat 自定义解析的格式字符串，需要和dateRegex同时使用
	 * @return 解析得到的日期时间
	 */
	public String parseDateTime(String str, String local, String timezone, String dateRegex, String dateFormat) {
		if (str == null) {
			return null;
		}
		if (str.contains("p.m")) {
			str = str.replaceAll("p.m", "pm");
		}

		// FIXME 什么作用？
		if (str.contains("2557")) {
			str = str.replaceAll("2557", "2014");
		}
		Matcher matcher = null;
		String dateTime = null;
		Vector<WeightedDate> v = new Vector<WeightedDate>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if ((str.contains("ET") || str.contains("EDT") || str.contains("美东时间"))
				&& (timezone == null || "".equals(timezone.trim()))) {
			timezone = "12";
		}

		if ((str.contains("BST")) && (timezone == null || "".equals(timezone.trim()))) {
			timezone = "7";
		}

		if (local != null && local.equalsIgnoreCase("RU")) {// 处理俄文日期和时间
			this.dateTimeStr = this.replaceRussian(str.replace("\n", ""));
		} else {
			this.dateTimeStr = str.replace("\n", "");
		}

		// 处理自定义时间格式和时间匹配格式的场景
		try {
			if (dateRegex != null && dateFormat != null) {
				// 使用自定义的匹配表达式和格式字符串解析日期和时间
				Pattern pattern = Pattern.compile(dateRegex);
				matcher = pattern.matcher(this.dateTimeStr);
				if (matcher.find()) {
					SimpleDateFormat sdf_pri = new SimpleDateFormat(dateFormat);
					dateTime = sdf.format(sdf_pri.parse(matcher.group()));

					return this.convertTimeZone(dateTime, timezone);
				}

			} else if (dateFormat != null) {
				SimpleDateFormat sdf_pri = new SimpleDateFormat(dateFormat);
				dateTime = sdf.format(sdf_pri.parse(this.dateTimeStr));
				return this.convertTimeZone(dateTime, timezone);
			}
		} catch (Exception e) {
			System.err.println(e);
		}

		// 处理特殊格式日期和时间，格式类似“10分钟前”
		dateTime = this.parserCharacter();
		if (dateTime != null) {
			return this.convertTimeZone(dateTime, timezone);
		}
		// 解析时间
		String time = this.getTime(this.dateTimeStr);

		// 解析日期
		WeightedDate[] wds = new WeightedDate[7];
		try {
			wds[0] = this.getHtmlDateL1();
			wds[1] = this.getHtmlDateChL1();
			wds[2] = this.getHtmlDateEnL1();
		} catch (Exception e) {
		}
		for (int i = 0; i < 3; i++) {
			if (wds[i] != null) {
				v.add(wds[i]);
			}
		}
		if (v.size() > 0) {
			if (time == null || time.isEmpty()) {
				time = "00:00";
			}
			dateTime = this.getBest(v).date + " " + time;
			return this.convertTimeZone(dateTime, timezone);
		}

		try {
			wds[3] = this.getHtmlDateL2();
			wds[4] = this.getHtmlDateChL2();
			wds[5] = this.getHtmlDateL3();
			wds[6] = this.getHtmlDateChL3();
		} catch (Exception e) {
		}
		for (int i = 3; i < 7; i++) {
			if (wds[i] != null) {
				v.add(wds[i]);
			}
		}
		if (v.size() > 0) {
			if (time == null || time.isEmpty()) {
				time = "00:00";
			}
			dateTime = this.getBest(v).date + " " + time;
			return this.convertTimeZone(dateTime, timezone);
		}

		if (time != null) {// 如果只匹配到时间，则默认日期为当前日期
			dateTime = getCurrentTime().split("\\s+")[0] + " " + time;
			if (this.checkDateTime(dateTime) == true) {
				return this.convertTimeZone(dateTime, timezone);
			}
		}

		return null;
	}

	/**
	 * 
	 * @describe TODO 检查时间是否在合理的范围内，即在1980年1月1日后，没有超过当前时间 主要用于验证信息的发布时间是否解析正确
	 * @author duhao
	 * @date 2019年10月13日上午11:53:29
	 * @param dateTime
	 * @return boolean
	 */
	public boolean checkDateTime(String dateTime) {
		boolean isNormal = false;

		Calendar date = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date.setTime(sdf.parse(dateTime));
			if (dateTime.compareTo("1980-01-01") > 0 && Calendar.getInstance().after(date)) {
				isNormal = true;
			}
		} catch (Exception e) {
			System.err.println("check date \"" + dateTime + "\" exception!");
		}

		return isNormal;
	}

	/**
	 * 
	 * @describe 按照设置的时区将时间转换成北京时间
	 * @author duhao
	 * @date 2019年10月13日上午11:54:04
	 * @param dateTime 需要转换的时间
	 * @param timezone 配置的时区
	 * @return 转换后的北京时间
	 */
	public String convertTimeZone(String dateTime, String timezone) {
		if (timezone == null || "".equals(timezone)) {
			timezone = "+8";
		}

		if (dateTime.trim().length() <= 16) {
			dateTime = dateTime.trim() + ":00";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(dateTime.trim()));
			if (timezone.contains("12")) {
				calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(timezone.replace("+", "")));
			} else if (timezone.contains("7")) {
				calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(timezone.replace("+", "")));
			} else {
				calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(timezone.replace("+", "")) - 8);
			}
			dateTime = sdf.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("error while parser datetime of \"" + dateTime + "\"\n" + e.toString());
		}
		return dateTime;
	}

	/**
	 * 
	 * @describe 提取字符串中的时间——时分秒
	 * @author duhao
	 * @date 2019年10月13日上午11:55:20
	 * @param str 包含时间的字符串
	 * @return 解析得到的时间
	 */
	public String getTime(String str) {
		String time = null;
		String timeRegexAll = "([\\s  　 日]*|^)((?:[01]?[0-9]|2[0-4])[\\:\\.时時](?:[0-5]?[0-9])(?:[\\:分][0-5]?[0-9]秒?)?(\\s?[aApP][mM])?)";
		Pattern timePatternALL = Pattern.compile(timeRegexAll);
		Matcher matcher = null;
		try {
			matcher = timePatternALL.matcher(str.replace("下午", "pm"));
			StringBuffer sb = new StringBuffer();
			if (matcher.find()) {
				time = matcher.group(2);
				if (matcher.group(3) != null && matcher.group(3).trim().equalsIgnoreCase("pm")) {
					int newHourOfTime = Integer.parseInt(time.split("\\:", 2)[0]) + 12;
					if (newHourOfTime < 24) {
						time = newHourOfTime + ":" + time.split("\\:", 2)[1];
					}
				}
				matcher.appendReplacement(sb, matcher.group(1));
			}
			if (time != null) {
				time = time.replaceAll("[aApP][mM]$", "").replaceAll("[时分秒時]", ":");
				if (time.endsWith(":")) {
					time = time.substring(0, time.length() - 1);
				}
			}
			matcher.appendTail(sb);
			str = sb.toString();
			this.dateTimeStr = str;
		} catch (Exception e) {
			e.printStackTrace();
			time = null;
		}
		if (time != null && time.contains(".")) {
			return time.replaceAll("\\.", ":");
		}
		return time;
	}

	/**
	 * 
	 * @describe 处理类似“10分钟前”这种格式的时间
	 * @author duhao
	 * @date 2019年10月13日上午11:55:51
	 * @return String
	 */
	private String parserCharacter() {
		String dateTime = null;
		boolean isFind = false;
		Calendar today = Calendar.getInstance();

		Pattern characterPattern = Pattern.compile(
				"(\\d+|半)[\\s　  ]*(秒|seconds?|分钟?|分鐘|minutes?|小时|小時|hours?)(前|\\s+ago)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			matcher = characterPattern.matcher(this.dateTimeStr);
			if (matcher.find()) {
				String pre1 = matcher.group(1);
				String pre2 = matcher.group(2);

				int addHour = 0;
				int addMinute = 0;
				int addSecond = 0;
				if (pre2 != null && pre2.matches("(?i)小時|小时|hours?")) {
					if (pre1.equals("半")) {
						addMinute = -30;
					} else {
						addHour = 0 - Integer.parseInt(pre1);
					}
				} else if (pre2 != null && pre2.matches("(?i)分钟?|分鐘|minutes?")) {
					addMinute = 0 - Integer.parseInt(pre1);
				} else if (pre2 != null && pre2.matches("(?i)秒|seconds?")) {
					addSecond = 0 - Integer.parseInt(pre1);
				}

				today.add(Calendar.HOUR, addHour);
				today.add(Calendar.MINUTE, addMinute);
				today.add(Calendar.SECOND, addSecond);

				dateTime = sdf.format(today.getTime());
				isFind = true;
			}

			if (isFind == false) {
				characterPattern = Pattern.compile(
						"(今天|昨天|前天|today|yesterday)[\\s,　  ]*(([01]?[0-9]|2[0-3]):[0-5][0-9])",
						Pattern.CASE_INSENSITIVE);
				matcher = characterPattern.matcher(this.dateTimeStr);
				if (matcher.find()) {
					String pre = matcher.group(1);
					String time = this.getTime(this.dateTimeStr);

					int addDay = 0;
					if (pre != null && pre.matches("(?i)今天|today")) {
						addDay = 0;
					} else if (pre != null && pre.matches("(?i)昨天|yesterday")) {
						addDay = -1;
					} else if (pre != null && pre.matches("(?i)前天")) {
						addDay = -2;
					} else {
						System.err.println("havn't parser condition \"" + pre + "\"!");
					}
					today.add(Calendar.DAY_OF_MONTH, addDay);
					dateTime = sdf.format(today.getTime()).split("\\s+")[0] + " " + time;
					isFind = true;
				}
			}

			if (isFind == false) {
				characterPattern = Pattern.compile("(\\d+|半)[\\s　  ]*(周|weeks?|天|days?)(前|\\s+ago)",
						Pattern.CASE_INSENSITIVE);
				matcher = characterPattern.matcher(this.dateTimeStr);
				if (matcher.find()) {
					String pre1 = matcher.group(1);
					String pre2 = matcher.group(2);
					String time = this.getTime(this.dateTimeStr);

					int addDay = 0;
					int addHour = 0;
					if (pre2 != null && pre2.matches("(?i)周|weeks?")) {
						addDay = 0 - Integer.parseInt(pre1) * 7;
					} else if (pre1 != null && pre1.matches("(?i)半")) {
						addHour = -12;
					} else if (pre1 != null && pre1.matches("\\d+")) {
						addDay = 0 - Integer.parseInt(pre1);
					}

					today.add(Calendar.DAY_OF_MONTH, addDay);
					today.add(Calendar.HOUR, addHour);

					if (time == null) {
						time = "00:00";
					}
					dateTime = sdf.format(today.getTime()).split("\\s+")[0] + " " + time;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("parser character datetime exception :" + this.dateTimeStr);
		}

		return dateTime;
	}

	/**
	 * 
	 * @describe 私有类，用于计算简单的日期权重，权重越小越标准
	 * @author duhao
	 * @date 2019年10月13日上午11:56:30
	 */
	private class WeightedDate {
		int weight = Integer.MAX_VALUE;

		String date = null;

		void setWeight(int offset, int weight) {
			this.weight = offset + weight;
		}

		void setDate(String yyyy, int mm, int dd) {
			if (mm <= 0 || dd <= 0 || mm > 12 || dd > 31) {
				return;
			}

			if (yyyy.length() == 2 && (yyyy.charAt(0) == '0' || yyyy.charAt(0) == '1' || yyyy.charAt(0) == '2'
					|| yyyy.charAt(0) == '3')) {
				yyyy = "20" + yyyy;
			} else if (yyyy.length() == 2) {
				yyyy = "19" + yyyy;
			}
			this.date = String.format("%s-%02d-%02d", yyyy, mm, dd);
		}

	}

	/**
	 * 
	 * @describe 取权重最小的日期
	 * @author duhao
	 * @date 2019年10月13日上午11:56:53
	 * @param wds
	 * @return
	 */
	private WeightedDate getBest(Vector<WeightedDate> wds) {
		int size = wds.size();
		if (size == 0) {
			return null;
		}
		WeightedDate best = wds.get(0);
		for (WeightedDate wd : wds) {
			if (wd.date != null && wd.weight < best.weight) {
				best = wd;
			} else if (best.date == null) {
				best = wd;
			}
		}
		return best;
	}

	/**
	 * 
	 * @describe 按照完整日期格式解析 支持日期格式包括: <BR>
	 *           <P>
	 *           yyyy-mm-dd yyyy_mm_dd yyyy.mm.dd yyyy/mm/dd <BR>
	 *           yyyy-m-dd yyyy_m_dd yyyy.m.dd yyyy/m/dd <BR>
	 *           yyyy-mm-d yyyy_mm_d yyyy.mm.d yyyy/mm/d <BR>
	 *           yyyy-m-d yyyy_m_d yyyy.m.d yyyy/m/d <BR>
	 *           mm/dd/yyyy mm/d/yyyy m/dd/yyyy m/d/yyyy <BR>
	 *           dd-mm-yyyy dd.mm.yyyy yyyymmdd <BR>
	 *           </P>
	 * @author duhao
	 * @date 2019年10月13日上午11:57:33
	 * @return 解析后最优的日期
	 */
	private WeightedDate getHtmlDateL1() {
		Pattern p = null;
		Matcher m = null;
		Vector<WeightedDate> wds = new Vector<WeightedDate>();

		String yyyy_mm_dd = "(?<!\\d)([12][09][0-9]{2}[-_./][01]?[0-9][-_./][0123]?[0-9])";
		p = Pattern.compile(yyyy_mm_dd);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			if (m.group(1).split("-").length != 3 && m.group(1).split("_").length != 3
					&& m.group(1).split("/").length != 3 && m.group(1).split("\\.").length != 3) {
				continue;
			}
			WeightedDate wd = new WeightedDate();
			String[] subStrings = m.group(1).split("[-_./]");
			wd.setDate(subStrings[0], Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[2]));
			wd.setWeight(m.start(), DateTimeUtil.yyyy_mm_dd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		String mm_dd_yyyy = "(?<!\\d)([01]?[0-9][-_./][0123]?[0-9][-_./][12][09][0-9]{2})";
		p = Pattern.compile(mm_dd_yyyy);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String date = m.group(1);
			if (date.split("-").length != 3 && date.split("\\.").length != 3 && date.split("/").length != 3) {
				continue;
			}
			String[] subStrings = date.split("[-_./]");
			wd.setDate(subStrings[2], Integer.parseInt(subStrings[0]), Integer.parseInt(subStrings[1]));
			wd.setWeight(m.start(), DateTimeUtil.mm_dd_yyyy);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		// 12 23, 2010
		String mm_dd_yyyy2 = "(?<!\\d)([01]?[0-9]\\s+[0123]?[0-9][\\s,]+[12][09][0-9]{2})";
		p = Pattern.compile(mm_dd_yyyy2);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String date = m.group(1).replace(",", " ");
			if (date.split("\\s+").length != 3) {
				continue;
			}
			String[] subStrings = date.split("\\s+");
			wd.setDate(subStrings[2], Integer.parseInt(subStrings[0]), Integer.parseInt(subStrings[1]));
			wd.setWeight(m.start(), DateTimeUtil.mm_dd_yyyy);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		String dd_mm_yyyy = "(?<!\\d)([0123]?[0-9][-_./\\s][01]?[0-9][-_./\\s][12][09][0-9]{2})";
		p = Pattern.compile(dd_mm_yyyy);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String date = m.group(1);
			if (date.split("-").length != 3 && date.split("\\.").length != 3 && date.split("/").length != 3
					&& date.split("\\s").length != 3) {
				continue;
			}
			String[] subStrings = date.split("[-_./\\s]");
			wd.setDate(subStrings[2], Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[0]));
			wd.setWeight(m.start(), DateTimeUtil.yyyy_mm_dd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		String yyyymmdd = "(?<!\\d)([12][09][0-9]{2}[01][0-9][0123][0-9])";
		p = Pattern.compile(yyyymmdd);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			String date = m.group(1);
			WeightedDate wd = new WeightedDate();
			if (Integer.parseInt(date.substring(0, 4)) < 1990) {
				continue;
			}
			wd.setDate(date.substring(0, 4), Integer.parseInt(date.substring(4, 6)),
					Integer.parseInt(date.substring(6, 8)));
			wd.setWeight(m.start(), DateTimeUtil.yyyymmdd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		return this.getBest(wds);
	}

	/**
	 * 
	 * @describe 按照次完整日期格式解析，即使用缩写的时间：省去年的前两位 <BR>
	 *           支持的时间格式: <BR>
	 *           yy-mm-dd yy_mm_dd yy.mm.dd yy/mm/dd <BR>
	 *           yy-m-dd yy_m_dd yy.m.dd yy/m/dd <BR>
	 *           yy-mm-d yy_mm_d yy.mm.d yy/mm/d <BR>
	 *           yy-m-d yy_m_d yy.m.d yy/m/d <BR>
	 *           yymmdd
	 * @author duhao
	 * @date 2019年10月13日下午12:09:32
	 * @return
	 */
	private WeightedDate getHtmlDateL2() {
		Pattern p = null;
		Matcher m = null;
		Vector<WeightedDate> wds = new Vector<WeightedDate>();

		String yy_mm_dd = "(?<!\\d)([0-9]{2}[-_./][01]?[0-9][-_./][0123]?[0-9])[^\\d]*";
		p = Pattern.compile(yy_mm_dd);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			if (m.group(1).split("-").length != 3 && m.group(1).split("_").length != 3
					&& m.group(1).split("/").length != 3 && m.group(1).split("\\.").length != 3) {
				continue;
			}
			WeightedDate wd = new WeightedDate();
			String[] subStrings = m.group(1).split("[-_./]");
			wd.setDate(subStrings[0], Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[2]));
			wd.setWeight(m.start(), DateTimeUtil.yy_mm_dd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		String mm_dd_yyyy = "(?<!\\d)([01]?[0-9][-_./][0123]?[0-9][-_./][0-9]{2})";
		p = Pattern.compile(mm_dd_yyyy);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String date = m.group(1);
			if (date.split("-").length != 3 && date.split("\\.").length != 3 && date.split("/").length != 3) {
				continue;
			}
			String[] subStrings = date.split("[-_./]");
			wd.setDate(subStrings[2], Integer.parseInt(subStrings[0]), Integer.parseInt(subStrings[1]));
			wd.setWeight(m.start(), mm_dd_yy);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		return this.getBest(wds);
	}

	/**
	 * 
	 * @describe 按照简写日期格式解析，即省去年份的时间格式 <BR>
	 *           支持的时间格式: <BR>
	 *           mm-dd mm_dd mm.dd mm/dd <BR>
	 *           m-dd m_dd m.dd m/dd <BR>
	 *           mm-d mm_d mm.d mm/d <BR>
	 *           m-d m_d m.d m/d <BR>
	 * @author duhao
	 * @date 2019年10月13日下午12:15:12
	 * @return
	 */
	private WeightedDate getHtmlDateL3() {
		Pattern p = null;
		Matcher m = null;
		Vector<WeightedDate> wds = new Vector<WeightedDate>();
		String mm_dd = "(?<!\\d)[01]?[0-9][-_/][0123]?[0-9]";
		p = Pattern.compile(mm_dd);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String[] subStrings = m.group().split("[-_/]");
			wd.setDate(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)), Integer.parseInt(subStrings[0]),
					Integer.parseInt(subStrings[1]));
			wd.setWeight(m.start(), DateTimeUtil.mm_dd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			} else {
				wd.setDate(String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1),
						Integer.parseInt(subStrings[0]), Integer.parseInt(subStrings[1]));
				wd.setWeight(m.start(), DateTimeUtil.mm_dd);
				if (this.checkDateTime(wd.date)) {
					wds.add(wd);
				}
			}
		}
		return this.getBest(wds);
	}

	/**
	 * 
	 * @describe 按照完整日期格式解析包含汉字或全角数字的时间 <BR>
	 *           支持时间格式: <BR>
	 *           <P>
	 *           yyyy年mm月dd日 yyyy年m月dd日 yyyy年mm月d日 yyyy年m月d日 <BR>
	 *           [汉语数字]年[汉语数字]月[汉语数字]日 <BR>
	 *           </P>
	 * @author duhao
	 * @date 2019年10月13日下午12:15:45
	 * @return
	 */
	private WeightedDate getHtmlDateChL1() {
		Pattern p = null;
		Matcher m = null;
		Vector<WeightedDate> wds = new Vector<WeightedDate>();

		String yyyy_mm_dd = "[12][09][0-9]{2}(\\s)*年(\\s)*[01]?[0-9](\\s)*月(\\s)*[0123]?[0-9](\\s)*日";
		p = Pattern.compile(yyyy_mm_dd);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			String[] subStrings = m.group().split("[年月日]");
			WeightedDate wd = new WeightedDate();
			wd.setDate(subStrings[0].trim(), Integer.parseInt(subStrings[1].trim()),
					Integer.parseInt(subStrings[2].trim()));
			wd.setWeight(m.start(), DateTimeUtil.yyyy_mm_dd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		yyyy_mm_dd = "[１２][0０９][0０１２３４５６７８９]{2}(\\s)*年(\\s)*[0０１]?[0０１２３４５６７８９](\\s)*月(\\s)*[0０１２３]?[0０１２３４５６７８９](\\s)*日";
		p = Pattern.compile(yyyy_mm_dd);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			String[] subStrings = this.replaceUpperCase(m.group()).split("[年月日]");
			WeightedDate wd = new WeightedDate();
			wd.setDate(subStrings[0], Integer.parseInt(subStrings[1].trim()), Integer.parseInt(subStrings[2].trim()));
			wd.setWeight(m.start(), yy_mm_dd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		yyyy_mm_dd = "([0〇零一二三四五六七八九十两千]{4})(\\s)*年(\\s)*[零0〇一二三四五六七八九十]{1,2}(\\s)*月(\\s)*[0〇零一二三四五六七八九十]{1,3}(\\s)*日";
		p = Pattern.compile(yyyy_mm_dd);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			String[] subStrings = this.replaceChinese(m.group()).split("[年月日]");
			WeightedDate wd = new WeightedDate();
			wd.setDate(subStrings[0], Integer.parseInt(subStrings[1].trim()), Integer.parseInt(subStrings[2].trim()));
			wd.setWeight(m.start(), yyyy_mm_dd_CN);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		return this.getBest(wds);
	}

	/**
	 * 
	 * @describe 按照次日期格式解析包含汉字或全角数字的时间，即省去年份的前两位<BR>
	 *           支持时间格式: <BR>
	 *           yy年mm月dd日 yy年m月dd日 yy年mm月d日 yy年m月d日 <BR>
	 *           [汉语数字]年[汉语数字]月[汉语数字]日 <BR>
	 * @author duhao
	 * @date 2019年10月13日下午12:17:13
	 * @return
	 */
	private WeightedDate getHtmlDateChL2() {
		Pattern p = null;
		Matcher m = null;
		Vector<WeightedDate> wds = new Vector<WeightedDate>();

		String yy_mm_dd = "[0-9]{2}(\\s)*年(\\s)*[01]?[0-9](\\s)*月(\\s)*[0123]?[0-9](\\s)*日";
		p = Pattern.compile(yy_mm_dd);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String[] subStrings = m.group().split("[年月日]");
			wd.setDate(subStrings[0], Integer.parseInt(subStrings[1].trim()), Integer.parseInt(subStrings[2].trim()));
			wd.setWeight(m.start(), DateTimeUtil.yy_mm_dd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		yy_mm_dd = "[0０１２３４５６７８９]{2}(\\s)*年(\\s)*[0０１]?[0０１２３４５６７８９](\\s)*月(\\s)*[0０１２３]?[0０１２３４５６７８９](\\s)*日";
		p = Pattern.compile(yy_mm_dd);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String[] subStrings = this.replaceUpperCase(m.group()).split("[年月日]");
			wd.setDate(subStrings[0], Integer.parseInt(subStrings[1].trim()), Integer.parseInt(subStrings[2].trim()));
			wd.setWeight(m.start(), DateTimeUtil.yy_mm_dd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		yy_mm_dd = "[0〇零一二三四五六七八九十两千]{2}年[0零〇一二三四五六七八九十]{1,2}月[0〇零一二三四五六七八九十]{1,3}日";
		p = Pattern.compile(yy_mm_dd);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String[] subStrings = this.replaceChinese(m.group()).split("[年月日]");
			if (subStrings[0].length() == 4) {
				wd.setDate(subStrings[0], Integer.parseInt(subStrings[1].trim()),
						Integer.parseInt(subStrings[2].trim()));
			} else if (subStrings[0].length() == 2) {
				wd.setDate(subStrings[0], Integer.parseInt(subStrings[1].trim()),
						Integer.parseInt(subStrings[2].trim()));
			}
			wd.setWeight(m.start(), DateTimeUtil.yy_mm_dd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}
		// / 01 八月 2011 06:53:01
		yy_mm_dd = " [.*?,]?([0123]?[0-9][日]?\\s*[一二三四五六七八九十]{1,2}月\\s*[0-9]{2,4}\\s*[年]?)";
		p = Pattern.compile(yy_mm_dd);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			this.dateTimeStr = m.group(1);
			WeightedDate wd = new WeightedDate();
			if (dateTimeStr.contains("日")) {
				int b1 = dateTimeStr.indexOf("日");
				int m1 = dateTimeStr.indexOf("月", b1);
				String day = dateTimeStr.substring(0, b1);
				String mm = replaceChinese(dateTimeStr.substring(b1 + 1, m1));
				String yy = dateTimeStr.substring(m1 + 1).replaceAll("[^\\d]+", "");
				wd.setDate(yy, Integer.parseInt(mm.trim()), Integer.parseInt(day.trim()));
				wd.setWeight(m.start(), dd_mm_yyyy);
				wds.add(wd);
			} else {
				int m1 = dateTimeStr.indexOf("月");
				String dd_mm = dateTimeStr.substring(0, m1);
				String day = dd_mm.replaceAll("[^\\d]+", "");
				String mm = replaceChinese(dd_mm.replaceAll("[\\d]+", ""));
				String yy = dateTimeStr.substring(m1 + 1).replaceAll("[^\\d]+", "");
				wd.setDate(yy, Integer.parseInt(mm.trim()), Integer.parseInt(day.trim()));
				wd.setWeight(m.start(), dd_mm_yyyy);
				wds.add(wd);
			}
		}
		return this.getBest(wds);
	}

	/**
	 * 
	 * @describe 按照简写日期格式解析包含汉字或全角数字的时间，即省去年份<BR>
	 *           支持时间格式: <BR>
	 *           mm月dd日 m月dd日 mm月d日 m月m日 <BR>
	 *           [汉语数字]月[汉语数字]日 <BR>
	 * @author duhao
	 * @date 2019年10月13日下午12:18:17
	 * @return
	 */
	private WeightedDate getHtmlDateChL3() {
		Pattern p = null;
		Matcher m = null;
		Vector<WeightedDate> wds = new Vector<WeightedDate>();

		String mm_dd = "\\s*[01]?[0-9](\\s)*月\\s*[0123]?[0-9](\\s)*日";
		p = Pattern.compile(mm_dd);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String[] subStrings = m.group().split("[月日]");
			wd.setDate(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
					Integer.parseInt(subStrings[0].trim()), Integer.parseInt(subStrings[1].trim()));
			wd.setWeight(m.start(), DateTimeUtil.mm_dd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		mm_dd = "[0０１]?[0０１２３４５６７８９](\\s)*月(\\s)*[0０１２３]?[0０１２３４５６７８９](\\s)*日";
		p = Pattern.compile(mm_dd);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String[] subStrings = this.replaceUpperCase(m.group()).split("[月日]");
			wd.setDate(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
					Integer.parseInt(subStrings[0].trim()), Integer.parseInt(subStrings[1].trim()));

			wd.setWeight(m.start(), DateTimeUtil.mm_dd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		mm_dd = "[零0〇一二三四五六七八九十]{1,2}(\\s)*月(\\s)*[0〇零一二三四五六七八九十]{1,3}(\\s)*日";
		p = Pattern.compile(mm_dd);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String[] subStrings = this.replaceChinese(m.group()).split("[月日]");
			wd.setDate(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
					Integer.parseInt(subStrings[0].trim()), Integer.parseInt(subStrings[1].trim()));

			wd.setWeight(m.start(), DateTimeUtil.mm_dd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		return this.getBest(wds);
	}

	/**
	 * 
	 * @describe 解析包含英文年份的时间
	 * @author duhao
	 * @date 2019年10月13日下午12:19:02
	 * @return
	 */
	private WeightedDate getHtmlDateEnL1() {
		Pattern p = null;
		Matcher m = null;
		Vector<WeightedDate> wds = new Vector<WeightedDate>();

		String mm_dd_yyyy = "(Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Sept|Oct|Nov|Dec|January|February|March|April|May|June|July|August|September|October|November|December)[.]?\\s{0,5}\\d{1,2}(th|st|nd|rd){0,1},{0,1}\\s{0,5}\\d{4}";
		p = Pattern.compile(mm_dd_yyyy, Pattern.CASE_INSENSITIVE);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String date = m.group().replace(",", " ");
			String[] subStrings = date.split("\\s+");
			if (subStrings.length != 3) {
				continue;
			}
			int month = this.getEnMonth(subStrings[0]);
			if (month == -1) {
				continue;
			}
			String dStr = subStrings[1].toLowerCase().replace("st", "").replace("nd", "").replace("rd", "")
					.replace("th", "");
			wd.setDate(subStrings[2], month, Integer.parseInt(dStr));
			wd.setWeight(m.start(), DateTimeUtil.mm_dd_yyyy);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		String dd_mm_yyyy = "\\d{1,2}(th|st|nd|rd)?\\s{0,5}(Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Sept|Oct|Nov|Dec|January|February|March|April|May|June|July|August|September|October|November|December),{0,1},?\\s{0,5}\\d{4}";
		p = Pattern.compile(dd_mm_yyyy, Pattern.CASE_INSENSITIVE);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String date = m.group().replace(",", " ");
			String[] subStrings = date.split("\\s+");
			if (subStrings.length != 3) {
				continue;
			}
			String dStr = subStrings[0].toLowerCase().replace("st", "").replace("nd", "").replace("rd", "")
					.replace("th", "");
			int month = this.getEnMonth(subStrings[1]);
			if (month == -1) {
				continue;
			}
			wd.setDate(subStrings[2], month, Integer.parseInt(dStr));
			wd.setWeight(m.start(), DateTimeUtil.mm_dd_yyyy);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		String dd_mm_yy = "(?<!\\d)\\d{1,2}(th|st|nd|rd)?,?-(Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Oct|Nov|Dec|January|February|March|April|May|June|July|August|September|October|November|December),{0,1}-\\d{2,4}";
		p = Pattern.compile(dd_mm_yy, Pattern.CASE_INSENSITIVE);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String date = m.group().replace(",", " ");
			String[] subStrings = date.split("\\s+|-");
			if (subStrings.length != 3) {
				continue;
			}
			String dStr = subStrings[0].toLowerCase().replace("st", "").replace("nd", "").replace("rd", "")
					.replace("th", "");
			int month = this.getEnMonth(subStrings[1]);
			if (month == -1) {
				continue;
			}
			wd.setDate(subStrings[2], month, Integer.parseInt(dStr));
			wd.setWeight(m.start(), yy_mm_dd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		String dd_mm = "\\d{1,2},?-?\\s+(Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Oct|Nov|Dec|January|February|March|April|May|June|July|August|September|October|November|December),?";
		p = Pattern.compile(dd_mm, Pattern.CASE_INSENSITIVE);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String date = m.group().replace(",", " ");
			String[] subStrings = date.split("\\s+|-");
			int month = this.getEnMonth(subStrings[1]);
			if (month == -1) {
				continue;
			}
			wd.setDate(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)), month,
					Integer.parseInt(subStrings[0]));
			wd.setWeight(m.start(), DateTimeUtil.dd_mm);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			} else {
				wd.setDate(String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1), month,
						Integer.parseInt(subStrings[0]));
				wd.setWeight(m.start(), DateTimeUtil.dd_mm);
				if (this.checkDateTime(wd.date)) {
					wds.add(wd);
				}
			}
		}

		String mm_dd = "(Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Sept|Oct|Nov|Dec|January|February|March|April|May|June|July|August|September|October|November|December).*?\\d{1,2}";
		p = Pattern.compile(mm_dd, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String date = m.group().replace(",", " ");
			String[] subStrings = date.split("\\s+|-|\\.");
			if (date.contains(".") || date.contains("-")) {
				subStrings = date.split("-|\\.");
			}
			int month = this.getEnMonth(subStrings[0]);
			if (month == -1) {
				continue;
			}
			wd.setDate(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)), month,
					Integer.parseInt(subStrings[1].trim()));
			wd.setWeight(m.start(), DateTimeUtil.dd_mm);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			} else {
				wd.setDate(String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1), month,
						Integer.parseInt(subStrings[1].trim()));
				wd.setWeight(m.start(), DateTimeUtil.dd_mm);
				if (this.checkDateTime(wd.date)) {
					wds.add(wd);
				}
			}
		}

		String dd_mm_yy2 = "(?<!\\d)\\d{1,2}(th|st|nd|rd)?\\s{0,5}(Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Oct|Nov|Dec|January|February|March|April|May|June|July|August|September|October|November|December),{0,1},?\\s{0,5}\\d{2}";
		p = Pattern.compile(dd_mm_yy2, Pattern.CASE_INSENSITIVE);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String date = m.group().replace(",", " ");
			String[] subStrings = date.split("\\s+|-");
			if (subStrings.length != 3) {
				continue;
			}
			String dStr = subStrings[0].toLowerCase().replace("st", "").replace("nd", "").replace("rd", "")
					.replace("th", "");
			int month = this.getEnMonth(subStrings[1]);
			if (month == -1) {
				continue;
			}
			wd.setDate(subStrings[2], month, Integer.parseInt(dStr));
			wd.setWeight(m.start(), DateTimeUtil.mm_dd_yyyy);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		String yyyy_mm_dd = "\\d{4}\\s*,?(Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Oct|Nov|Dec|January|February|March|April|May|June|July|August|September|October|November|December)\\s{0,5}\\d{1,2}(th|st|nd|rd){0,1},?";
		p = Pattern.compile(yyyy_mm_dd, Pattern.CASE_INSENSITIVE);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String date = m.group().replaceAll(",", " ");
			String[] subStrings = date.split("\\s+,");
			if (subStrings.length < 3) {
				continue;
			}
			int month = this.getEnMonth(subStrings[1]);
			if (month == -1) {
				continue;
			}
			String dStr = subStrings[2].toLowerCase().replace("st", "").replace("nd", "").replace("rd", "")
					.replace("th", "");
			wd.setDate(subStrings[0], month, Integer.parseInt(dStr));
			wd.setWeight(m.start(), DateTimeUtil.yyyy_mm_dd);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		// 02 Ağustos 2010 Pazartesi 08:51
		dd_mm_yyyy = "\\d{1,2}\\s*(Ocak|,Subat|Subat|Mart|Nisan|Mayžs|Haziran|Temmuz|A-gustos|Ağustos|Eylül|Ekim|Kasžm|Aralžk)\\s*\\d{2,4}\\s*(.*?)";
		p = Pattern.compile(dd_mm_yyyy, Pattern.CASE_INSENSITIVE);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String date = m.group().replaceAll(",", " ");
			String[] subStrings = date.split("\\s+");
			if (subStrings.length < 3) {
				continue;
			}
			int month = this.getAlbMonth(subStrings[1]);
			if (month == -1) {
				continue;
			}
			String dStr = subStrings[2].toLowerCase().replace("st", "").replace("nd", "").replace("rd", "")
					.replace("th", "");
			wd.setDate(dStr, month, Integer.parseInt(subStrings[0]));
			wd.setWeight(m.start(), DateTimeUtil.dd_mm_yyyy);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}

		// 23 мая 2014 года Выбрать дату 18:37 俄语
		dd_mm_yyyy = "\\d{1,2}\\s*(январь|февраль|март|апрель|мая|июнь|июль|август|сентябрь|октябрь|ноябрь|декабрь)\\s*\\d{2,4}\\s*(.*?)";
		p = Pattern.compile(dd_mm_yyyy, Pattern.CASE_INSENSITIVE);
		m = p.matcher(this.dateTimeStr);
		while (m.find()) {
			WeightedDate wd = new WeightedDate();
			String date = m.group().replaceAll(",", " ");
			date = replaceRussian(date);
			String[] subStrings = date.split("\\s+");
			if (subStrings.length < 3) {
				continue;
			}
			int month = -1;
			try {
				month = Integer.parseInt(subStrings[1]);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			if (month == -1) {
				continue;
			}
			String dStr = subStrings[2].toLowerCase().replace("st", "").replace("nd", "").replace("rd", "")
					.replace("th", "");
			wd.setDate(dStr, month, Integer.parseInt(subStrings[0]));
			wd.setWeight(m.start(), DateTimeUtil.dd_mm_yyyy);
			if (this.checkDateTime(wd.date)) {
				wds.add(wd);
			}
		}
		// ///////////
		return this.getBest(wds);
	}

	/**
	 * 
	 * @describe 将字符串中的中文数字替换为阿拉伯数字
	 * @author duhao
	 * @date 2019年10月13日下午12:19:43
	 * @param text
	 * @return
	 */
	private String replaceChinese(String text) {
		if (null == text) {
			return null;
		}
		text = text.replaceAll("月", "").replaceAll("日", "").replaceAll("年", "").replaceAll("週", "");
		String[][] chAry = { { "两千零", "200" }, { "二千零", "200" }, { "二千", "2000" }, { "两千", "2000" }, { "九", "9" },
				{ "八", "8" }, { "七", "7" }, { "六", "6" }, { "五", "5" }, { "四", "4" }, { "三", "3" }, { "二", "2" },
				{ "一", "1" }, { "零", "0" }, { "〇", "0" }, { "博讯北京时间", "" } };
		for (int i = 0; i < chAry.length; i++) {
			if (text.indexOf(chAry[i][0]) >= 0) {
				text = text.replace(chAry[i][0], chAry[i][1]);
			}
		}
		if (text.replaceAll("\\d+十\\d+", "~~~~~").indexOf("~~~~~") > -1) {
			// 处理“二十五”这种形式的数字
			text = text.replace("十", "");
		} else if (text.replaceAll("十\\d+", "~~~~~").indexOf("~~~~~") > -1) {
			// 处理“十五”这种形式的数字
			text = text.replace("十", "1");
		} else if (text.replaceAll("\\d+十", "~~~~~").indexOf("~~~~~") > -1) {
			// 处理“二十”这种形式的数字
			text = text.replace("十", "0");
		} else if (text.indexOf("十") > -1) {
			// 处理“十”这种形式的数字
			text = text.replace("十", "10");
		}
		return text;
	}

	/**
	 * 
	 * @describe 替换全角数字
	 * @author duhao
	 * @date 2019年10月13日下午12:20:07
	 * @param text
	 * @return
	 */
	private String replaceUpperCase(String text) {
		String[][] chAry = { { "０", "0" }, { "１", "1" }, { "２", "2" }, { "３", "3" }, { "４", "4" }, { "５", "5" },
				{ "６", "6" }, { "７", "7" }, { "８", "8" }, { "９", "9" } };
		for (int i = 0; i < chAry.length; i++) {
			if (text.indexOf(chAry[i][0]) >= 0) {
				text = text.replace(chAry[i][0], chAry[i][1]);
			}
		}
		return text;
	}

	/**
	 * 
	 * @describe 将俄语时间替换为阿拉伯数字
	 * @author duhao
	 * @date 2019年10月13日下午12:21:55
	 * @param text
	 * @return
	 */
	private String replaceRussian(String text) {
		String result = text.toLowerCase().replaceAll("понедельник|вторник|среда|четверг|пятница|суббота|воскресенье",
				"");// 去除星期一~星期日
		String[][] number = { { "январь|января", "1" }, { "февраль|фев", "2" }, { "март|марта|мар", "3" },
				{ "апрель|апр", "4" }, { "май|мая", "5" }, { "июнь|июня|июн", "6" }, { "июль|июля|июл", "7" },
				{ "август|авг", "8" }, { "сентябрь|сен", "9" }, { "октябрь|окт", "10" }, { "ноябрь|ноя", "11" },
				{ "декабрь|дек", "12" }, { "вчера|вчерашний", "昨天" }, { "позавчера", "前天" }, { "сегодня", "今天" },
				{ "после обеда", "PM" } };
		for (int i = 0; i < number.length; i++) {
			result = result.replaceAll("(?i)" + number[i][0], number[i][1]);
		}

		return result;
	}

	/**
	 * 
	 * @describe 替换英文时间 Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Oct|Nov|Dec <BR>
	 *           January|February|March|April|May|June|July|August|September|October|
	 *           November|December <BR>
	 * @author duhao
	 * @date 2019年10月13日下午12:22:09
	 * @param monthStr
	 * @return
	 */
	private int getEnMonth(String monthStr) {
		String lMonth = monthStr.toLowerCase();
		String[][] monthAry = { { "january", "1" }, { "february", "2" }, { "march", "3" }, { "april", "4" },
				{ "may", "5" }, { "june", "6" }, { "july", "7" }, { "august", "8" }, { "september", "9" },
				{ "october", "10" }, { "november", "11" }, { "december", "12" } };
		for (int i = 0; i < monthAry.length; i++) {
			if (lMonth.compareTo(monthAry[i][0]) == 0) {
				return Integer.parseInt(monthAry[i][1]);
			}
		}

		// Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Oct|Nov|Dec|
		String[][] simpleMonthAry = { { "jan", "1" }, { "feb", "2" }, { "mar", "3" }, { "apr", "4" }, { "may", "5" },
				{ "jun", "6" }, { "jul", "7" }, { "aug", "8" }, { "sep", "9" }, { "oct", "10" }, { "nov", "11" },
				{ "dec", "12" } };
		for (int i = 0; i < simpleMonthAry.length; i++) {
			if (lMonth.toLowerCase().indexOf(simpleMonthAry[i][0]) >= 0) {
				return Integer.parseInt(simpleMonthAry[i][1]);
			}
		}

		return -1;
	}

	/**
	 * 
	 * @describe 替换阿拉伯文时间<BR>
	 * @author duhao
	 * @date 2019年10月13日下午12:22:39
	 * @param monthStr
	 * @return
	 */
	private int getAlbMonth(String monthStr) {
		String lMonth = monthStr.toLowerCase();
		String[][] monthAry = { { "ocak", "1" }, { ",subat", "2" }, { "subat", "2" }, { "mart", "3" }, { "nisan", "4" },
				{ "mayžs", "5" }, { "haziran", "6" }, { "temmuz", "7" }, { "a-gustos", "8" }, { "ağustos", "8" },
				{ "eylül", "9" }, { "ekim", "10" }, { "kasžm", "11" }, { "aralžk", "12" } };
		for (int i = 0; i < monthAry.length; i++) {
			if (lMonth.compareTo(monthAry[i][0]) == 0) {
				return Integer.parseInt(monthAry[i][1]);
			}
		}
		// Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Oct|Nov|Dec|
		String[][] simpleMonthAry = { { "ocak", "1" }, { ",subat", "2" }, { "subat", "2" }, { "mart", "3" },
				{ "nisan", "4" }, { "mayžs", "5" }, { "haziran", "6" }, { "temmuz", "7" }, { "a-gustos", "8" },
				{ "ağustos", "8" }, { "eylül", "9" }, { "ekim", "10" }, { "kasžm", "11" }, { "aralžk", "12" } };
		for (int i = 0; i < simpleMonthAry.length; i++) {
			if (lMonth.toLowerCase().indexOf(simpleMonthAry[i][0]) >= 0) {
				return Integer.parseInt(simpleMonthAry[i][1]);
			}
		}

		return -1;
	}

	/**
	 * 
	 * @describe 时区 时间转换方法:将传入的时间（可能为其他时区）转化成目标时区对应的时间
	 * @author duhao
	 * @date 2019年10月13日下午12:22:55
	 * @param sourceTime 时间格式必须为：yyyy-MM-dd HH:mm:ss
	 * @param sourceId   入参的时间的时区id 比如：+08:00
	 * @param targetId   要转换成目标时区id 比如：+09:00
	 * @param reFormat   返回格式 默认：yyyy-MM-dd HH:mm:ss
	 * @return string 转化时区后的时间
	 */
	public static String timeConvert(String sourceTime, String sourceId, String targetId, String reFormat) {
		// 校验入参是否合法
		if (null == sourceId || "".equals(sourceId) || null == targetId || "".equals(targetId) || null == sourceTime
				|| "".equals(sourceTime)) {

			return null;
		}

		if (StringUtils.isEmpty(reFormat)) {
			reFormat = "yyyy-MM-dd HH:mm:ss";
		}

		// 校验 时间格式必须为：yyyy-MM-dd HH:mm:ss
		String reg = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$";
		if (!sourceTime.matches(reg)) {
			return null;
		}

		try {
			// 时间格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 根据入参原时区id，获取对应的timezone对象
			TimeZone sourceTimeZone = TimeZone.getTimeZone("GMT" + sourceId);
			// 设置SimpleDateFormat时区为原时区（否则是本地默认时区），目的:用来将字符串sourceTime转化成原时区对应的date对象
			df.setTimeZone(sourceTimeZone);
			// 将字符串sourceTime转化成原时区对应的date对象
			java.util.Date sourceDate = df.parse(sourceTime);

			// 开始转化时区：根据目标时区id设置目标TimeZone
			TimeZone targetTimeZone = TimeZone.getTimeZone("GMT" + targetId);
			// 设置SimpleDateFormat时区为目标时区（否则是本地默认时区），目的:用来将字符串sourceTime转化成目标时区对应的date对象
			df.setTimeZone(targetTimeZone);
			// 得到目标时间字符串
			String targetTime = df.format(sourceDate);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date date = sdf.parse(targetTime);
			sdf = new SimpleDateFormat(reFormat);

			return sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getTimeV2(String string) {

		if (string == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-");
		string = string.trim();
		if (string.matches("^1[0-9]{12}")) {
			return sdf.format(new Date(Long.parseLong(string)));
		} else if (string.matches("^1[0-9]{9}")) {
			return sdf.format(new Date(Long.parseLong(string + "000")));
		} else if (string.matches("^[0-9]{1,2}月[0-9]{1,2}日$")) {
			string = sdf2.format(new Date()) + string;
		} else if (string.matches("^[0-9]{1,2}-[0-9]{1,2}$")) {
			string = sdf3.format(new Date()) + string;
		}
		return parseDateTime(string, null, null, null, null);
	}
	
	/**
	 * 获取当天的开始时间
	 * @author duhao
	 * @return
	 */
	public static java.util.Date getDayBegin(java.util.Date date) {
		Calendar cal = new GregorianCalendar();
		if (ToolsUtil.isNotEmpty(date)) {
			cal.setTime(date);
		}
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取当天的结束时间
	 * @author duhao
	 * @return
	 */
	public static java.util.Date getDayEnd(java.util.Date date) {
		Calendar cal = new GregorianCalendar();
		if (ToolsUtil.isNotEmpty(date)) {
			cal.setTime(date);
		}
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

}
