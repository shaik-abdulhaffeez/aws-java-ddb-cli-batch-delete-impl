package com.sk.batch.delete.service.api.util;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
public class JsonP {
	private static final String ONETAB = "   ";
	static Map<String, Object> _parseJSON(Iterator<Character> iter) {
		final Map<String, Object> map = new LinkedHashMap<String, Object>() {
			private static final long serialVersionUID = -3192438622581031914L;
			@Override
			public String toString() {
				return toJSONString(this);
			}
		};
		char ch;
		int count = 0;
		KeyValue kv = new KeyValue(count);
		StringBuilder sb = new StringBuilder();
		while (iter.hasNext()) {
			ch = iter.next();
			switch (ch) {
			case '{':
			case '[':
				kv.value = _parseJSON(iter);
				map.put(kv.key, kv.value);
				kv = new KeyValue(++count);
				break;
			case ' ':
			case '\t':
			case '\n':
			case '\r':
				// ignore
				break;
			case '"':
			case '\'':
				sb.append(parseQuotedString(ch, iter));
				break;
			case '\\':
				sb.append(iter.next());
				break;
			case ':':
				kv.key = sb.toString().trim();
				sb = new StringBuilder();
				break;
			case ',':
				if (sb.toString().length() > 0) {
					kv.value = sb.toString();
					sb = new StringBuilder();
					map.put(kv.key, kv.value);
					kv = new KeyValue(++count);
				}
				break;
			case '}':
			case ']':
				if (sb.toString().length() > 0) {
					kv.value = sb.toString();
					map.put(kv.key, kv.value);
				}
				return map;
			default:
				sb.append(ch);
			}
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	private static String _toJSONString(Map<String, Object> map, int level, boolean compliant) {
		final StringBuilder sb = new StringBuilder();
		final StringBuilder tab = new StringBuilder();
		for (int i = 0; i < level; ++i) {
			tab.append(ONETAB);
		}
		if (level > 0) {
			sb.append('\n').append(tab);
		}
		sb.append('{');
		String sep = "";
		for (final Map.Entry<String, Object> me : map.entrySet()) {
			sb.append(sep);
			sb.append('\n');
			sb.append(tab);
			sb.append(ONETAB);
			sb.append(safeValue(me.getKey(), compliant));
			sb.append(": ");
			final Object v = me.getValue();
			if (v instanceof Map) {
				sb.append(_toJSONString((Map<String, Object>) v, ++level, compliant));
			} else {
				if (v == null) {
					sb.append("null");
				} else {
					sb.append(safeValue(v.toString(), compliant));
				}
			}
			sep = ", ";
		}
		sb.append('\n');
		sb.append(tab);
		sb.append("}");
		return sb.toString();
	}
	public static boolean getBoolean(Map<String, Object> map, String... keys) {
		return Boolean.parseBoolean(getString(map, keys));
	}
	public static double getDouble(Map<String, Object> map, String... keys) {
		return Double.parseDouble(getString(map, keys));
	}
	public static int getInteger(Map<String, Object> map, String... keys) {
		return Integer.parseInt(getString(map, keys));
	}
	public static long getLong(Map<String, Object> map, String... keys) {
		return Long.parseLong(getString(map, keys));
	}
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(Map<String, Object> map, String... keys) {
		Map<String, Object> m = map;
		for (int i = 0; i < keys.length - 1; ++i) {
			final Object obj = m.get(keys[i]);
			if (obj instanceof Map) {
				m = (Map<String, Object>) m.get(keys[i]);
			} else {
				return null;
			}
		}
		return (Map<String, Object>) m.get(keys[keys.length - 1]);
	}
	@SuppressWarnings("unchecked")
	public static Object getMapOrString(Map<String, Object> map, String... keys) {
		Map<String, Object> m = map;
		for (int i = 0; i < keys.length - 1; ++i) {
			final Object obj = m.get(keys[i]);
			if (obj instanceof Map) {
				m = (Map<String, Object>) m.get(keys[i]);
			} else {
				return null;
			}
		}
		return m.get(keys[keys.length - 1]);
	}
	@SuppressWarnings("unchecked")
	public static String getString(Map<String, Object> map, String... keys) {
		Map<String, Object> m = map;
		for (int i = 0; i < keys.length - 1; ++i) {
			final Object obj = m.get(keys[i]);
			if (obj instanceof Map) {
				m = (Map<String, Object>) m.get(keys[i]);
			} else {
				return null;
			}
		}
		return (String) m.get(keys[keys.length - 1]);
	}
	public static void main(String[] args) {
		// EXAMPLE JSON
		final String str = "{MessageId: 38179544-469f-422a-9ae4-2f77931aa514,ReceiptHandle: AQEBP1+FzNmFVJ7Oc/RvW74iXpSuB/9ka/g79JH60aNluQYHF6bSBpTiZ4Glu7RHj5dAbY0zscoFxOuri+vj6F44mIctd1iXguHrHGa5HLuvSgOk8O4Elfk4l5LiKe97E779XX5gGgx4jtg1++ljSvxZTNIZ3pWDDPg/AecuJymON29dxu/MR7heRobJ7yAqW4C6BWN5hPzp6TM3lGpTiNBhSO1hsBCl4jWGrDE/6wrp0PY0i/KbHxbRCIuDHB8xf3387PDwdtxjMEHROzE8cEwEusMfaDaUe4y3oJQnLx5gGkA=,MD5OfBody: 6e3530543a7ba3af97a1550cca247479,Body: {\"requested_time\": 1600849058,\"sqs_message_id\": \"3c95a780-10ff-40ca-b28a-bac285bd2753\",   \"revoke_description\": \"revoke application\",   \"process_count\": 0,   \"revoke_audit_id\": \"631b0f0f-1fc0-4a40-aedb-70a08b8bbeaa\",   \"user\": \"testuser\",   \"revoke_table_id\": \"804a26b1-1a85-48f0-b6c0-73e54d46559b\",   \"revoke_api_path\": \"/administration/application/{id}/revoke\",    \"revoke_category\": \"application\",    \"status\": \"in progress\"},Attributes: {},MessageAttributes: {}}";
		final Map<String, Object> jso = JsonP.parseJSON(str);
		System.out.println("**Compact JSON**");
		System.out.println(jso);
		System.out.println();
		System.out.println("**Compliant JSON**");
		System.out.println(JsonP.toJSONString(jso, true));
		System.out.println();
		System.out.println("json.basic.0.description = " + JsonP.getString(jso, "basic", "0", "description"));
	}
	public static Map<String, Object> parseJSON(Iterator<Character> iter) {
		while (iter.hasNext()) {
			final char ch = iter.next();
			if (ch == '{' || ch == '[') {
				break;
			}
		}
		return _parseJSON(iter);
	}
	public static Map<String, Object> parseJSON(Reader r) throws IOException {
		return parseJSON(new ReaderCharacterIterator(r));
	}
	public static Map<String, Object> parseJSON(String str) {
		return parseJSON(new StringCharacterIterator(str));
	}
	static String parseQuotedString(char endChar, Iterator<Character> iter) {
		final StringBuilder sb = new StringBuilder();
		char ch = iter.next();
		while (ch != endChar) {
			if (ch == '\\') {
				ch = iter.next();
			}
			sb.append(ch);
			ch = iter.next();
		}
		return sb.toString();
	}
	private static String safeValue(String value, boolean compliant) {
		final char[] ch = value.toCharArray();
		boolean quote = false;
		if (compliant) {
			try {
				if (!value.equals(null)) {
					Double.parseDouble(value);
				}
			} catch (final NumberFormatException e) {
				quote = true;
			}
		}
		for (int i = 0; i < ch.length; ++i) {
			switch (ch[i]) {
			case ':':
			case '\'':
			case ' ':
			case '\n':
			case '\t':
			case '\r':
			case ',':
			case '\"':
			case '{':
			case '[':
			case '}':
			case ']':
				quote = true;
				break;
			}
		}
		if (!quote) {
			return value;
		}
		final StringBuilder sb = new StringBuilder();
		sb.append('\"');
		for (int i = 0; i < ch.length; ++i) {
			if (ch[i] == '"') {
				sb.append('\'');
			}
			sb.append(ch[i]);
		}
		sb.append('\"');
		return sb.toString();
	}
	public static String toJSONString(Map<String, Object> map) {
		return toJSONString(map, false);
	}
	public static String toJSONString(Map<String, Object> map, boolean compliant) {
		return _toJSONString(map, 0, compliant);
	}
}

class KeyValue {
	public String key;
	public Object value;
	public KeyValue(int count) {
		key = Integer.toString(count);
	}
	public KeyValue(String key, Object value) {
		this.key = key;
		this.value = value;
	}
}

class ReaderCharacterIterator implements Iterator<Character> {
	private int ch;
	private final Reader r;
	public ReaderCharacterIterator(Reader r) throws IOException {
		this.r = r;
		this.ch = r.read();
	}
	@Override
	public boolean hasNext() {
		return ch > -1;
	}
	@Override
	public Character next() {
		try {
			final char c = (char) ch;
			ch = r.read();
			return c;
		} catch (final IOException ex) {
			Logger.getLogger(ReaderCharacterIterator.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
}

class StringCharacterIterator implements Iterator<Character> {
	private final char[] ch;
	int pos = -1;
	public StringCharacterIterator(String str) {
		this.ch = str.toCharArray();
	}
	@Override
	public boolean hasNext() {
		return pos < ch.length - 1;
	}
	@Override
	public Character next() {
		++pos;
		return ch[pos];
	}
}