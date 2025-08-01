package com.driagon.services.configserver.constants;

public final class ValidationMessages {

    public static final class CreateUser {
        public static final String FIRST_NAME_NOT_BLANK = "First name must not be blank";
        public static final String LAST_NAME_NOT_BLANK = "Last name must not be blank";
        public static final String EMAIL_NOT_BLANK = "Email must not be blank";
        public static final String EMAIL_VALID = "Email must be a valid email address";
        public static final String PASSWORD_NOT_BLANK = "Password must not be blank";
        public static final String ROLE_ID_POSITIVE = "Role ID must be a positive number";
    }

    public static final class CreateScope {
        public static final String NAME_NOT_BLANK = "Scope name must not be blank";
        public static final String NAME_LENGTH = "Scope name must be between 3 and 50 characters";
        public static final String DESCRIPTION_LENGTH = "Description must be between 0 and 255 characters";
    }

    public static final class GetScopeById {
        public static final String ID_POSITIVE = "Scope ID must be a positive number";
        public static final String ID_NOT_BLANK = "Scope ID must not be blank";
        public static final String ID_DIGITS = "Scope ID must be a number";
    }

    public static class ScopeController {
        public static class SetUsersToScope {
            public static final String SCOPE_ID_POSITIVE = "Scope ID must be a positive number";
            public static final String SCOPE_ID_NOT_BLANK = "Scope ID must not be blank";
            public static final String SCOPE_ID_DIGITS = "Scope ID must be a number";
            public static final String EMAILS_NOT_EMPTY = "Emails must not be empty";
        }
    }
}