package br.org.pasa.projeto.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

public class Util {
	
	private static final List<String> LISTA_CNPJS_INVALIDOS = Arrays.asList("00000000000000", "11111111111111", "22222222222222", "33333333333333", "44444444444444", "55555555555555", "66666666666666", "77777777777777", "88888888888888", "99999999999999");
	
	private static final Logger logger = Logger.getLogger(Util.class);
	
	private Util(){
	}
	
	/**
	 * Formata um cnpj com a pontuacao
	 * @param cnpjParam
	 * @return String
	 */
    public static String formatarCNPJ(final String cnpjParam) {
        String cnpj = Util.isNumberNotNullNotEmpty(cnpjParam) && (cnpjParam.length() < 14) ? String.format("%014d", Long.valueOf(cnpjParam)) : cnpjParam;
        return (cnpj != null) && (cnpj.length() == 14) ? new StringBuilder(cnpj).insert(2, '.').insert(6, '.').insert(10, '/').insert(15, '-').toString() : null;
    }
    
    /**
     * Verifica se uma determinada string pode ser convertida para numerico 
     * @param number
     * @return boolean
     */
    public static boolean isNumberNotNullNotEmpty(final String number) {
        return (!Util.isStringNullOrEmpty(number)) && StringUtils.isNumeric(number);
    }
    
    /**
     * Formata uma determinada string para apenas numerico 
     * @param str
     * @return String
     */
    public static String formatarStringApenasNumerica(final String str) {
        return Util.isStringNullOrEmpty(str) ? null : Pattern.compile("\\D").matcher(str).replaceAll(StringUtils.EMPTY);
    }
    
    /**
     * Verifica se uma determinada colecao e nula ou vazia
     * @param c
     * @return boolean
     */
    public static boolean isCollectionNullOrEmpty(final Collection<?> c) {
        return (c == null) || c.isEmpty();
    }
    
    /**
     * Verifica se uma string e nula ou vazia
     * @param str
     * @return boolean
     */
    public static boolean isStringNullOrEmpty(final String str) {
        if ((str == null) || str.isEmpty()) {
            return true;
        }

        for (final char c : str.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                return false;
            }
        }

        return true;
    }
    
    /**
     * Verifica se um determinado cnpj e valido
     * @param cnpj
     * @return boolean
     */
    public static boolean isValidCnpj(final String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14) || Util.LISTA_CNPJS_INVALIDOS.contains(cnpj)) {
            return false;
        }

        int sm, i, r, num, peso;

        // "try" - protege o código para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                // converte o i-Ã©simo caractere do CNPJ em um número:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posição de '0' na tabela ASCII)
                num = cnpj.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            final char dig13 = (r == 0) || (r == 1) ? '0' : (char) ((11 - r) + 48);

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = cnpj.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            final char dig14 = (r == 0) || (r == 1) ? '0' : (char) ((11 - r) + 48);

            // Verifica se os dígitos calculados conferem com os dígitos informados.
            return (dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13));
        } catch (final InputMismatchException e) {
        	logger.error(e.getMessage(), e);
        	return false;
        }
    }

}
