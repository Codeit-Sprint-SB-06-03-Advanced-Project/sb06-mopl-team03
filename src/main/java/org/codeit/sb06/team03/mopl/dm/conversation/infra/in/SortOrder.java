package org.codeit.sb06.team03.mopl.dm.conversation.infra.in;

public enum SortOrder {
    ASCENDING, DESCENDING;

    public static SortOrder parse(String name) {
        return SortOrder.valueOf(name.toUpperCase());
    }
}
