package org.example.be_java_hisp_w26_g04.util.crud;

import lombok.Getter;

@Getter
public enum OrderEnum {
    DATE_ASC("date_asc"),
    DATE_DESC("date_desc"),
    NAME_ASC("name_asc"),
    NAME_DESC("name_desc");

    private final String order;

    OrderEnum(String order) {
        this.order = order;
    }

}
