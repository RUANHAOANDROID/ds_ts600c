package com.reader.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PBOCTLV {

	// eg. tlv="9F36020B889F270180" return {9F27=80, 9F36=0B88}
	public static Map<String, String> decodingTLV(List<String[]> list) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			String[] tlv = (String[]) list.get(i);
			map.put(tlv[0], tlv[2]);
		}
		return map;
	}

	// eg. tlv="9F36020B889F270180" return {[9F36,02,0B88], [9F27,01,80]}
	public static List<String[]> decodingTLV(String str) {
		if (str == null || str.length() % 2 != 0) {
			throw new RuntimeException("Invalid tlv, null or odd length");
		}
		List<String[]> ls = new ArrayList<String[]>();
		for (int i = 0; i < str.length();) {
			try {
				String tag = str.substring(i, i = i + 2);
				// extra byte for TAG field
				if ((Integer.parseInt(tag, 16) & 0x1F) == 0x1F) {
					tag += str.substring(i, i = i + 2);
				}
				String len = str.substring(i, i = i + 2);
				int length = Integer.parseInt(len, 16);
				// more than 1 byte for length
				if (length > 128) {
					int bytesLength = length - 128;
					len = str.substring(i, i = i + (bytesLength * 2));
					length = Integer.parseInt(len, 16);
				}
				length *= 2;
				String value = str.substring(i, i = i + length);
				System.out.println("tag:" + tag + " len:" + len + " value:"
						+ value);
				ls.add(new String[] { tag, len, value });
			} catch (NumberFormatException e) {
				throw new RuntimeException("Error parsing number", e);
			} catch (IndexOutOfBoundsException e) {
				throw new RuntimeException("Error processing field", e);
			}
		}
		return ls;
	}

	public static String encodingTLV(Map<Integer, byte[]> tlvMap) {
		String str = "";
		Iterator<Entry<Integer, byte[]>> iter = tlvMap.entrySet().iterator();
		int tag = 0;
		String length = "";
		Entry<Integer, byte[]> entry;
		while (iter.hasNext()) {
			entry = (Entry<Integer, byte[]>) iter.next();
			tag = (Integer) entry.getKey();
			byte[] value = (byte[]) entry.getValue();
			// length =
			// String.valueOf(Integer.parseInt(String.valueOf(value.length() /
			// 2), 16));
			if (value.length < 0x80)
				length = String.format("%02x", value.length);
			else if (value.length >= 0x80 && value.length < 0xFF)
				length = String.format("81%02x", value.length);
			else if (value.length >= 0xff && value.length < 0xFFFF)
				length = String.format("82%02x%02x", value.length / 256,
						value.length % 256);
			String valueStr = StringUtil.bcd2Str(value);
			String tagStr;
			if (((tag / 256) & 0x0F) == 0x0F)
				tagStr = String.format("%02X%02X", tag / 256, tag % 256);
			else
				tagStr = String.format("%02X", tag % 256);

			str += tagStr + length + valueStr;
		}
		return str;
	}

	public static String encodingTLV(List<String[]> tlvList) {
		String str = "";
		for (int i = 0; i < tlvList.size(); i++) {
			String[] tlv = (String[]) tlvList.get(i);
			str += tlv[0] + tlv[1] + tlv[2];
		}
		return str;
	}

	public static byte[] getValue(Map<Integer, byte[]> tlvMap, int getTag) {
		Iterator<Entry<Integer, byte[]>> iter = tlvMap.entrySet().iterator();
		int tag = 0;
		Entry<Integer, byte[]> entry;
		while (iter.hasNext()) {
			entry = (Entry<Integer, byte[]>) iter.next();
			tag = (Integer) entry.getKey();
			byte[] value = (byte[]) entry.getValue();
			// length =
			// String.valueOf(Integer.parseInt(String.valueOf(value.length() /
			// 2), 16));
			if (getTag == tag) {
				// str=StringUtil.bcd2Str(value);
				return value;
			}
		}
		return null;
	}

	public static byte[] getValue(String tlvStr, int getTag) {
		List<String[]> listTLV = PBOCTLV.decodingTLV(tlvStr);
		if (listTLV == null)
			return null;
		Map<String, String> mapTLV = PBOCTLV.decodingTLV(listTLV);
		Iterator<Entry<String, String>> iter = mapTLV.entrySet().iterator();
		Object tag = 0;
		Entry<String, String> entry;
		while (iter.hasNext()) {
			entry = (Entry<String, String>) iter.next();
			tag = entry.getKey();
			Object value = entry.getValue();
			if (value == null)
				return null;
			// length =
			// String.valueOf(Integer.parseInt(String.valueOf(value.length() /
			// 2), 16));

			int tagV = Integer.parseInt((String) tag, 16);// ByteUtil.bcdToInt(((String)tag).getBytes());

			// SodoLog.v("native pbocTLV","tagv="+tagV);
			// SodoLog.v("native pbocTLV",""+value);

			if (getTag == tagV) {
				// str=StringUtil.bcd2Str(value);
				byte[] tmpByte = StringUtil.hexStringToBytes((String) value);
				return tmpByte;// ByteUtil.ASCII_To_BCD(tmpByte,
								// tmpByte.length);//
			}
		}
		return null;
	}
	public static String getValueString(String tlvStr, int getTag) {
		List<String[]> listTLV = PBOCTLV.decodingTLV(tlvStr);
		if (listTLV == null)
			return null;
		Map<String, String> mapTLV = PBOCTLV.decodingTLV(listTLV);
		Iterator<Entry<String, String>> iter = mapTLV.entrySet().iterator();
		Object tag = 0;
		Entry<String, String> entry;
		while (iter.hasNext()) {
			entry = (Entry<String, String>) iter.next();
			tag = entry.getKey();
			Object value = entry.getValue();
			if (value == null)
				return null;
			// length =
			// String.valueOf(Integer.parseInt(String.valueOf(value.length() /
			// 2), 16));

			int tagV = Integer.parseInt((String) tag, 16);// ByteUtil.bcdToInt(((String)tag).getBytes());

			// SodoLog.v("native pbocTLV","tagv="+tagV);
			// SodoLog.v("native pbocTLV",""+value);

			if (getTag == tagV) {
				// str=StringUtil.bcd2Str(value);
//				byte[] tmpByte = StringUtil.hexStringToBytes((String) value);
//				return tmpByte;// ByteUtil.ASCII_To_BCD(tmpByte,
				return (String) value;
				// tmpByte.length);//
			}
		}
		return null;
	}
	
	public static boolean checkSW(byte[] respData, int respLen,
			byte[] stateCodeArray) {
		if ((respData.length < 2) || (respLen < 2)
				|| (respData.length < respLen) || (stateCodeArray.length != 2)) {
			return false;
		}

		if ((respData[respLen - 2] == stateCodeArray[0])
				&& (respData[respLen - 1] == stateCodeArray[1])) {
			return true;
		} else {
			return false;
		}
	}

}
