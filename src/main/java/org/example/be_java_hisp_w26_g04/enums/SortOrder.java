package org.example.be_java_hisp_w26_g04.enums;

public enum SortOrder {
    DATE_ASC("date_asc"),
    DATE_DESC("date_desc"),
    NAME_ASC("name_asc"),
    NAME_DESC("name_desc");

    private final String order;

    SortOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return this.order;
    }
}
