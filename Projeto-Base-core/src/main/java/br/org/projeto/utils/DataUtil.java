package br.org.projeto.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * The Class DataUtil.
 * 
 * @author
 */
public class DataUtil {

    private DataUtil() {
    }

    /** A constante PATTERN_DATA. */
    public static final String PATTERN_DATA = "dd/MM/yyyy";

    /** A constante PATTERN_DATA_MMYYYY. */
    public static final String PATTERN_DATA_MMYYYY = "MM/yyyy";

    /** A constante PATTERN_DATA_YYYYMMDD. */
    public static final String PATTERN_DATA_YYYYMMDD = "yyyyMMdd";

    /** A constante PATTERN_DATA_yyyyMMddHHmmss. */
    public static final String PATTERN_DATA_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /** A constante PATTERN_DATA_HORA. */
    public static final String PATTERN_DATA_HORA = "dd/MM/yyyy HH:mm:ss";

    /** A constante PATTERN_DATA_HORA. */
    public static final String PATTERN_DDMMYYYYHHMM = "dd/MM/yyyy HH:mm";

    /** A constante PATTERN_DDMMYYYY_AS_HHMM. */
    public static final String PATTERN_DDMMYYYY_AS_HHMM = "dd/MM/yyyy 'às' HH:mm";

    public static final String PATTERN_DDMMYYYY_AS_HHMMSS = "dd/MM/yyyy 'às' HH:mm:ss";

    /** A constante PATTERN_DDMMYYYY_AS_HHMM. */
    public static final String PATTERN_EEEE_DD_DE_MMMM_DE_YYYY = "EEEE',' dd 'de' MMMM 'de' yyyy";

    /** A constante PATTERN_YYYY_MM_DD. */
    public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    
    /** A constante PATTERN_YYYY_MM_DD_HH_MM_SS. */
    public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * To date.
     * 
     * @param data
     *            the data
     * @return the date
     */
    public static Date toDate(final String data) {
        Date retorno;
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_DATA, Locale.getDefault());
            retorno = simpleDateFormat.parse(data);

        } catch (ParseException e) {
            retorno = null;
        }
        return retorno;
    }

    /**
     * To date.
     * 
     * @param data
     *            data
     * @param patternData
     *            pattern data
     * @return date
     */
    public static Date toDate(final String data, final String patternData) {
        Date retorno;
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patternData, Locale.getDefault());
            retorno = simpleDateFormat.parse(data);

        } catch (ParseException e) {
            retorno = null;
        }
        return retorno;
    }

    /**
     * Obtém ano corrente.
     * 
     * @return ano corrente
     */
    public static Integer getAnoCorrente() {
        return getInstance().get(Calendar.YEAR);
    }

    /**
     * Obtém a instância única de DataUtil.
     * 
     * @return única instância de DataUtil
     */
    public static Calendar getInstance() {
        return Calendar.getInstance();
    }

    /**
     * Formata data.
     * 
     * @param data
     *            data
     * @param patternData
     *            pattern data
     * @return string
     */
    public static final String formataData(final Date data, final String patternData) {
        final SimpleDateFormat formatter = new SimpleDateFormat(patternData);
        return formatter.format(data);
    }

    /**
     * Formata data.
     * 
     * @param data
     *            data
     * @return string
     */
    public static final String formataData(final Date data) {
        final SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_DATA);
        return formatter.format(data);
    }

    /**
     * Obter data atual.
     * 
     * @return date
     */
    public static Date obterDataAtual() {
        return getInstance().getTime();
    }

    /**
     * Adicionar anos à data.
     * 
     * @param data
     *            data
     * @param anos
     *            anos
     * @return date
     */
    public static Date adicionarAnoData(Date data, int anos) {
        Calendar calendar = getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.YEAR, anos);
        return calendar.getTime();
    }

    /**
     * Adicionar meses à data.
     * 
     * @param data
     *            data
     * @param meses
     *            meses
     * @return date
     */
    public static Date adicionarMesData(Date data, Integer meses) {
        Calendar calendar = getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.MONTH, meses);
        return calendar.getTime();
    }

    /**
     * Adicionar minutos data.
     * 
     * @param data
     *            data
     * @param minutos
     *            minutos
     * @return date
     */
    public static Date adicionarMinutosData(Date data, Integer minutos) {
        Calendar calendar = getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.MINUTE, minutos);
        return calendar.getTime();
    }

    /**
     * Subtrair minutos data.
     * 
     * @param data
     *            data
     * @param minutos
     *            minutos
     * @return date
     */
    public static Date subtrairMinutosData(Date data, Integer minutos) {
        Calendar calendar = getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.MINUTE, -minutos);
        return calendar.getTime();
    }

    /**
     * Verifica se a primeira data é posterior a segunda.
     * 
     * @param data1
     *            data1
     * @param data2
     *            data2
     * @return true, se bem sucedido
     */
    public static boolean ePosterior(Date data1, Date data2) {
        return data1.after(data2);
    }

    /**
     * Compara o ano da data com o ano.
     * 
     * @param data
     *            data
     * @param ano
     *            ano
     * @return int - 0, se os anos forem iguais; 1, maior; e -1, menor.
     */
    public static int comparaAno(Date data, int ano) {
        Calendar calendar = getInstance();
        calendar.setTime(data);
        if (calendar.get(Calendar.YEAR) < ano) {
            return -1;
        } else if (calendar.get(Calendar.YEAR) > ano) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Diferenca entre datas.
     * 
     * @param dataInicio
     *            data inicio
     * @param dataFim
     *            data fim
     * @return int
     * @throws ParseException
     *             parse exception
     */
    public static int diferencaEntreDatas(Date dataInicio, Date dataFim) throws ParseException {
        GregorianCalendar inicio = new GregorianCalendar();
        GregorianCalendar fim = new GregorianCalendar();
        inicio.setTime(dataInicio);
        fim.setTime(dataFim);
        long dt1 = inicio.getTimeInMillis();
        long dt2 = fim.getTimeInMillis();
        return (int) (((dt2 - dt1) / 86400000) + 1);
    }

    /**
     * Obter dia.
     * 
     * @param data
     *            data
     * @return int
     */
    public static int obterDia(final Date data) {
        final GregorianCalendar gcData = new GregorianCalendar();
        gcData.setTime(data);
        return gcData.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Obter ano.
     * 
     * @param data
     *            data
     * @return int
     */
    public static int obterAno(final Date data) {
        final GregorianCalendar gcData = new GregorianCalendar();
        gcData.setTime(data);
        return gcData.get(Calendar.YEAR);
    }

    /**
     * Obter ano.
     * 
     * @param data
     *            data
     * @return int
     */
    public static int obterMes(final Date data) {
        final GregorianCalendar gcData = new GregorianCalendar();
        gcData.setTime(data);
        return gcData.get(Calendar.MONTH) + 1;
    }

    /**
     * Clone with2359.
     * 
     * @param date
     *            date
     * @return date
     */
    public static Date cloneWith2359(final Date date) {
        Date retorno = null;

        if (date != null) {
            retorno = (Date) date.clone();
            final Calendar cal = Calendar.getInstance();
            cal.setTime(retorno);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            retorno.setTime(cal.getTime().getTime());
        }
        return retorno;
    }

    /**
     * Verifica se é data valida.
     * 
     * @param data
     *            data
     * @param formato
     *            formato
     * @return true, se é data valida
     */
    public static boolean isDataValida(String data, String formato) {

        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato, Locale.getDefault());
            simpleDateFormat.parse(data);

        } catch (ParseException e) {
            return false;
        }
        return true;

    }

    /**
     * Verifica se é data valida.
     * 
     * @param data
     *            data
     * @return true, se é data valida
     */
    public static boolean isDataValida(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATA);
        sdf.setLenient(false);
        try {
            sdf.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
