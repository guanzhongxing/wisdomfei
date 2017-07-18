package com.admintwo.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @Title: UtilTools
 * @Description: 该类为通用的工具类
 * @author 徐江飞
 * @data 2017年3月18日 上午10:13:48
 * @version V1.0
 */
public class ToolsUtils {

	/**
	 * 将字符串进行md5加密
	 */
	public static String MD5(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 将图片进行压缩处理
	 */
	public static void handlePicture(String startPic, String endPic, int width, int height) throws IOException {
		// 创建图片文件(此处为上传图片)和处理后的图片文件
		File fromPic = new File(startPic);
		File toPic = new File(endPic);
		// 按指定大小把图片进行缩和放（会遵循原图高宽比例）
		// 此处把图片压成168*168的缩略图
		Thumbnails.of(fromPic).size(width, height).toFile(toPic);
		// 图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
		Thumbnails.of(fromPic).scale(1f).outputQuality(0.25f).toFile(toPic);
	}

	/**
	 * 该方法将时间戳改成几天前、几小时前、几分钟前
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeFormatText(Date date) {
		long minute = 60 * 1000;// 1分钟
		long hour = 60 * minute;// 1小时
		long day = 24 * hour;// 1天
		long month = 31 * day;// 月
		long year = 12 * month;// 年
		if (date == null) {
			return null;
		}
		long diff = new Date().getTime() - date.getTime();
		long r = 0;
		if (diff > year) {
			/*
			 * r = (diff / year); return r + "年前";
			 */
			return new SimpleDateFormat("yyyy-MM-dd").format(date);
		}
		if (diff > month) {
			r = (diff / month);
			return r + "月前";
		}
		if (diff > day) {
			r = (diff / day);
			return r + "天前";
		}
		if (diff > hour) {
			r = (diff / hour);
			return r + "小时前";
		}
		if (diff > minute) {
			r = (diff / minute);
			return r + "分钟前";
		}
		return "刚刚";
	}

	/**
	 * 该方法用于分词
	 * 
	 * @param text
	 * @return
	 */
	public static String IkAnalyzer(String str) {

		String word = "";// 对输入进行分词

		try {
			StringReader reader = new StringReader(str);
			IKSegmenter ik = new IKSegmenter(reader, true);// 当为true时，分词器进行最大词长切分
			Lexeme lexeme = null;
			while ((lexeme = ik.next()) != null) {
				word += lexeme.getLexemeText() + ",";
			}
			// 分词后
			System.out.println("====ToolsUtils.IkAnalyzer()分词后：" + word);

		} catch (IOException e1) {
			System.out.println("====ToolsUtils.IkAnalyzer()方法分词异常");
		}
		return word;
	}

	public static HttpServletResponse download(String path, HttpServletResponse response) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。
			// String ext = filename.substring(filename.lastIndexOf(".") +
			// 1).toUpperCase();

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return response;
	}

	/**
	 * 该方法用于unicode转中文
	 * 
	 * @param unicode
	 * @return
	 */
	private static String unicodeToCn(String unicode) {
		/** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格 */
		String[] strs = unicode.split("\\\\u");
		String returnStr = "";
		// 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
		for (int i = 1; i < strs.length; i++) {
			returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
		}
		return returnStr;
	}

	/**
	 * 该方法用于中文转unicode
	 * 
	 * @param cn
	 * @return
	 */
	private static String cnToUnicode(String cn) {
		char[] chars = cn.toCharArray();
		String returnStr = "";
		for (int i = 0; i < chars.length; i++) {
			returnStr += "\\u" + Integer.toString(chars[i], 16);
		}
		return returnStr;
	}

	public static void main(String[] args) {
		System.out.println(cnToUnicode("徐江飞"));
		System.out.println(unicodeToCn("\u5f90\u6c5f\u98de"));
	}

}
