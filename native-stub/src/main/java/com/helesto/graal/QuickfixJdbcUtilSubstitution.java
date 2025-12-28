package com.helesto.graal;

import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;
import quickfix.ConfigError;
import quickfix.FieldConvertError;
import quickfix.SessionID;
import quickfix.SessionSettings;

import javax.sql.DataSource;

/**
 * Substitutes quickfix.JdbcUtil to eliminate runtime dependencies on the legacy Proxool library.
 * This prevents native image build failures, as the application relies on Quarkus for DataSource management.
 */
@TargetClass(className = "quickfix.JdbcUtil")
final class QuickfixJdbcUtilSubstitution {

    /**
     * Overrides default DataSource creation to bypass internal Proxool logic.
     * Returns null to ensure usage of the Quarkus-managed DataSource.
     */
    @Substitute
    public static DataSource getDataSource(SessionSettings settings, SessionID sessionID)
            throws ConfigError, FieldConvertError {
        return null;
    }
}