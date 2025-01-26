package com.retail.experience.config;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.type.StringType;

import java.sql.Types;

public class SQLDialect extends Dialect {

    private static final String INTEGER_STR = "integer";
    private static final String SUB_STR = "substr";
    private static final String BLOB_STR = "blob";
    private static final String IDENTITY_SELECT_STRING = "IdentitySelectString";
    private static final String CREATE_TEMPORARY_TABLE_STRING = "CreateTemporaryTableString";

    public SQLDialect() {
        registerColumnType(Types.BIT, INTEGER_STR);
        registerColumnType(Types.TINYINT, "tinyint");
        registerColumnType(Types.SMALLINT, "smallint");
        registerColumnType(Types.INTEGER, INTEGER_STR);
        registerColumnType(Types.BIGINT, "bigint");
        registerColumnType(Types.FLOAT, "float");
        registerColumnType(Types.REAL, "real");
        registerColumnType(Types.DOUBLE, "double");
        registerColumnType(Types.NUMERIC, "numeric");
        registerColumnType(Types.DECIMAL, "decimal");
        registerColumnType(Types.CHAR, "char");
        registerColumnType(Types.VARCHAR, "varchar");
        registerColumnType(Types.LONGVARCHAR, "longvarchar");
        registerColumnType(Types.DATE, "date");
        registerColumnType(Types.TIME, "time");
        registerColumnType(Types.TIMESTAMP, "timestamp");
        registerColumnType(Types.BINARY, BLOB_STR);
        registerColumnType(Types.VARBINARY, BLOB_STR);
        registerColumnType(Types.LONGVARBINARY, BLOB_STR);
        registerColumnType(Types.NULL, "null");
        registerColumnType(Types.BLOB, BLOB_STR);
        registerColumnType(Types.CLOB, "clob");
        registerColumnType(Types.BOOLEAN, INTEGER_STR);
        registerFunction("concat", new VarArgsSQLFunction(StringType.INSTANCE, "", "||", ""));
        registerFunction("mod", new SQLFunctionTemplate(StringType.INSTANCE, "?1 % ?2"));
        registerFunction(SUB_STR, new StandardSQLFunction(SUB_STR, StringType.INSTANCE));
        registerFunction("substring", new StandardSQLFunction(SUB_STR, StringType.INSTANCE));
    }

    public boolean supportsIdentityColumns() {
        return true;
    }

    public boolean hasDataTypeInIdentityColumn() {
        return false;
    }

    public String getIdentityColumnString() {
        return INTEGER_STR;
    }

    public String getIdentitySelectString() {
        return IDENTITY_SELECT_STRING;
    }

    public boolean supportsTemporaryTables() {
        return true;
    }

    public String getCreateTemporaryTableString() {
        return CREATE_TEMPORARY_TABLE_STRING;
    }

    public boolean dropTemporaryTableAfterUse() {
        return false;
    }

    @Override
    public boolean supportsCurrentTimestampSelection() {
        return true;
    }

    @Override
    public boolean isCurrentTimestampSelectStringCallable() {
        return false;
    }

    @Override
    public String getCurrentTimestampSelectString() {
        return "select current_timestamp";
    }

    @Override
    public boolean supportsUnionAll() {
        return true;
    }

    @Override
    public boolean hasAlterTable() {
        return false;
    }

    @Override
    public boolean dropConstraints() {
        return false;
    }

    @Override
    public String getAddColumnString() {
        return "add column";
    }

    @Override
    public String getForUpdateString() {
        return "";
    }

    @Override
    public boolean supportsOuterJoinForUpdate() {
        return false;
    }

    @Override
    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    @Override
    public boolean supportsCascadeDelete() {
        return false;
    }

    @Override
    public String getDropForeignKeyString() {
        String error = "No drop foreign key syntax supported by SQLiteDialect";
        throw new UnsupportedOperationException(error);
    }

    @Override
    public String getAddForeignKeyConstraintString(
            String constraintName, String[] foreignKey, String referencedTable,
            String[] primaryKey, boolean referencesPrimaryKey) {
        String error = "No add foreign key syntax supported by SQLiteDialect";
        throw new UnsupportedOperationException(error);
    }

    @Override
    public String getAddPrimaryKeyConstraintString(String constraintName) {
        String error = "No add primary key syntax supported by SQLiteDialect";
        throw new UnsupportedOperationException(error);
    }
}
