package com.ufo.core.common;

/**
 * Created by FOG on 21.02.2018.
 */
@SuppressWarnings("unused")
public enum SearchStrategy {

    EXACT_MATCH((v, searched) -> v != null && v.equals(searched)),
    IGNORE_CASE_MATCH((v, searched) -> v != null && v.equalsIgnoreCase(searched)),
    CONTAINS_MATCH((v, searched) -> v != null && v.contains(searched)),
    STARTS_WITH_MATCH((v, searched) -> v != null && v.startsWith(searched)),
    ENDS_WITH_MATCH((v, searched) -> v != null && v.endsWith(searched));

    private final Matcher<String, Boolean> searchOperation;

    private SearchStrategy(Matcher<String, Boolean> searchOperation) {
        this.searchOperation = searchOperation;
    }

    public boolean search(String value, String value2) {
        return searchOperation.matches(value, value2);
    }

}
