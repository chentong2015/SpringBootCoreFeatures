package com.retail.experience.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SQLDialectTest {

    private final SQLDialect sqlDialect = new SQLDialect();

    @Test
    void supports_identity_columns() {
        Assertions.assertTrue(sqlDialect.supportsIdentityColumns());
    }

    @Test
    void has_data_type_in_identity_column() {
        Assertions.assertFalse(sqlDialect.hasDataTypeInIdentityColumn());
    }

    @Test
    void get_identity_column_string() {
        Assertions.assertEquals("integer", sqlDialect.getIdentityColumnString());
    }

    @Test
    void get_identity_select_string() {
        Assertions.assertEquals("IdentitySelectString", sqlDialect.getIdentitySelectString());
    }

    @Test
    void supports_temporary_tables() {
        Assertions.assertTrue(sqlDialect.supportsTemporaryTables());
    }

    @Test
    void get_create_temporary_table_string() {
        Assertions.assertEquals("CreateTemporaryTableString", sqlDialect.getCreateTemporaryTableString());
    }

    @Test
    void drop_temporary_table_after_use() {
        Assertions.assertFalse(sqlDialect.dropTemporaryTableAfterUse());
    }

    @Test
    void supports_current_timestamp_Selection() {
        Assertions.assertTrue(sqlDialect.supportsCurrentTimestampSelection());
    }

    @Test
    void is_current_timestamp_select_string_callable() {
        Assertions.assertFalse(sqlDialect.isCurrentTimestampSelectStringCallable());
    }

    @Test
    void get_current_timestamp_select_string() {
        Assertions.assertEquals("select current_timestamp", sqlDialect.getCurrentTimestampSelectString());
    }

    @Test
    void supports_union_all() {
        Assertions.assertTrue(sqlDialect.supportsUnionAll());
    }

    @Test
    void has_alter_table() {
        Assertions.assertFalse(sqlDialect.hasAlterTable());
    }

    @Test
    void drop_constraints() {
        Assertions.assertFalse(sqlDialect.dropConstraints());
    }

    @Test
    void get_add_column_string() {
        Assertions.assertEquals("add column", sqlDialect.getAddColumnString());
    }

    @Test
    void get_for_update_string() {
        Assertions.assertEquals("", sqlDialect.getForUpdateString());
    }

    @Test
    void supports_outer_join_for_update() {
        Assertions.assertFalse(sqlDialect.supportsOuterJoinForUpdate());
    }

    @Test
    void supports_if_exists_before_table_name() {
        Assertions.assertTrue(sqlDialect.supportsIfExistsBeforeTableName());
    }

    @Test
    void supports_cascade_delete() {
        Assertions.assertFalse(sqlDialect.supportsCascadeDelete());
    }

    @Test
    void get_drop_foreign_key_string() {
        Assertions.assertThrows(UnsupportedOperationException.class, sqlDialect::getDropForeignKeyString);
    }

    @Test
    void get_add_foreign_key_constraint_string() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> sqlDialect.getAddForeignKeyConstraintString(null, null, null, null, true));
    }

    @Test
    void get_add_primary_key_constraint_string() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> sqlDialect.getAddPrimaryKeyConstraintString("constraintName"));

    }
}