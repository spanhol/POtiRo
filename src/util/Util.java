/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando Spanhol
 */
public class Util {

	//formatadores de dada
	public static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat dth = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

	/**
	 * Workaround de conversão de String no formato brasileiro "1,0" para
	 * double.
	 *
	 * Double.parseDouble utiliza o formato ingles "1.0".
	 *
	 * @param str - A String com virgula onde sera realizado o cast para double.
	 * @return um double.
	 */
	public static Double parseDouble(String str) {
		if (str.isEmpty()) {
			return 0d;
		} else {
			return Double.parseDouble(str.replace(".", "").replace(",", "."));
		}
	}

	/**
	 * fornece valor padrao 0 para Strings vazias
	 *
	 * @param str - String de um inteiro
	 * @return A representação da String como inteiro ou 0 se a String for
	 * vazia.
	 */
	public static Integer parseInt(String str) {
		if (str.isEmpty()) {
			return 0;
		} else {
			return Integer.parseInt(str);
		}
	}

	/**
	 * Parser de Date para horas no formato "dd/MM/yyyy hh:mm:ss".
	 *
	 * @param datahora - String no formato "dd/MM/yyyy hh:mm:ss"
	 * @return Date.
	 */
	public static Date ParseDataHora(String datahora) {
		try {
			datahora = datahora.trim();
			return dth.parse(datahora);
		} catch (ParseException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	/**
	 * Parser de Date para horas no formato "dd/MM/yyyy".
	 *
	 * @param data - String no formato "dd/MM/yyyy".
	 * @return Date.
	 */
	public static Date ParseData(String data) {
		try {
			data = data.trim();
			return dt.parse(data);
		} catch (ParseException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	/**
	 * Parser de Date para horas no formato "yyyy-MM-dd" utilizado
	 * principalmente pelo Banco de dados.
	 *
	 * @param data - String no formato "yyyy-MM-dd".
	 * @return Date.
	 */
	public static Date ParseDataYMD(String data) {
		try {
			data = data.trim();
			return ymd.parse(data);
		} catch (ParseException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
}
