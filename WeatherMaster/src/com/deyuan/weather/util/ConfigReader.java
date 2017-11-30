package com.deyuan.weather.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * ��������ȡ������<br>
 * 
 * @author Phonnie
 * 
 */
public class ConfigReader {

	protected HashMap sections = new HashMap();
	private transient String currentSecion;
	private transient Properties current;

	/**
	 * ���캯��
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public ConfigReader(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		read(reader);
		reader.close();
	}

	/**
	 * ��ȡ�ļ�
	 * 
	 * @param reader
	 * @throws IOException
	 */
	protected void read(BufferedReader reader) throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			parseLine(line);
		}
	}

	/**
	 * ���������ļ���
	 * 
	 * @param line
	 */
	@SuppressWarnings("unchecked")
	protected void parseLine(String line) {
		line = line.trim();
		if (line.matches("\\[.*\\]")) {
			currentSecion = line.replaceFirst("\\[(.*)\\]", "$1");
			current = new Properties();
			sections.put(currentSecion, current);
		} else if (line.matches(".*=.*")) {
			if (current != null) {
				int i = line.indexOf('=');
				String name = line.substring(0, i);
				String value = line.substring(i + 1);
				current.setProperty(name, value);
			}
		}
	}

	/**
	 * ��ȡֵ
	 * 
	 * @param section
	 * @param name
	 * @return
	 */
	public String getValue(String section, String name) {
		Properties p = (Properties) sections.get(section);
		if (p == null) {
			return null;
		}
		String value = p.getProperty(name);
		return value;
	}

	/**
	 * �Ƿ����key
	 * 
	 * @param section
	 * @param name
	 * @return
	 */
	public boolean containsKey(String section, String key) {
		Properties p = (Properties) sections.get(section);
		return p.contains(key);
	}
}
