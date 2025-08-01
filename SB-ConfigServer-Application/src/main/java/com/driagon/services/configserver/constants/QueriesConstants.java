package com.driagon.services.configserver.constants;

public final class QueriesConstants {

    public static final class PropertyQueries {

        /**
         * Query to find properties by scope ID and a set of keys.
         * This query retrieves properties that match the specified scope ID and are within the provided set of keys.
         */
        public static final String FIND_BY_SCOPE_ID = "SELECT p FROM Property p WHERE p.scope.id = :scopeId AND p.key IN :keys";
    }
}