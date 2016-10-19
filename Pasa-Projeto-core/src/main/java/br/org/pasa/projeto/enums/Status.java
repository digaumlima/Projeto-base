package br.org.pasa.projeto.enums;

public enum Status {

    INATIVO("Inativo."), // 0
    ATIVO("Ativo."); // 1

    private String descricao;

    private Status(final String descricaoParam) {
        this.descricao = descricaoParam;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public static Status getStatusByOrdinal(final int ordinal) {
        final Status[] status = Status.values();
        return (ordinal >= 0) && (ordinal < status.length) ? status[ordinal] : null;
    }	
}
