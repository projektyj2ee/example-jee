package com.czapiewski.service;

import java.io.IOException;
import java.util.Properties;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

/**
 * Serwis do odczytu parametrow aplikacji
 * 
 * @author Marcin
 * 
 */
@Stateless
public class CommentsService {

	private Integer schedulerInterval = 0;
	private Integer commentsCount = 0;

	private final Logger logger = Logger.getLogger(CommentsService.class);

	/**
	 * Pobiera interwal czasowy po jakim maja sie automatycznie odswiezyc tabele
	 * z komentarzami z pliku commentsdemo.properties. Info - wartosc ta jest
	 * inicjalizowana tylko podczas pierwszego wywolania
	 * 
	 * @return
	 */
	public Integer getSchedulerInterval() {
		if (schedulerInterval == 0) {
			try {
				String nazwaPliku = "commentsdemo.properties";
				Properties prop = System.getProperties();

				prop.load(getClass().getClassLoader().getResourceAsStream(
						nazwaPliku));

				schedulerInterval = prop.getProperty("scheduler.interval") != null ? new Integer(
						prop.getProperty("scheduler.interval")) : 0;

				schedulerInterval = schedulerInterval * 1000;

			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			} catch (Exception e) { // uzywam do przechwycenie np wyjatku
									// java.lang.NumberFormatException
				logger.error(e.getMessage(), e);
			}
		}// end if

		return schedulerInterval;
	}

	/**
	 * Pobiera wartosc maksymalnej ilosci wiadomosci jaka ma sie wyswietlac na
	 * stronie z pliku commentsdemo.properties. Info - wartosc ta jest
	 * inicjalizowana tylko podczas pierwszego wywolania
	 * 
	 * @return
	 */
	public Integer getMaxCommendsByPage() {
		if (commentsCount == 0) {
			try {
				String nazwaPliku = "commentsdemo.properties";
				Properties prop = System.getProperties();

				prop.load(getClass().getClassLoader().getResourceAsStream(
						nazwaPliku));

				commentsCount = prop.getProperty("max.comments.count", "5") != null ? new Integer(
						prop.getProperty("max.comments.count")) : 0;

			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}// end if
		return commentsCount;
	}

}
