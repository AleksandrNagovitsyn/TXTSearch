package util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementSetter {
    public void set(PreparedStatement preparedStatement) throws SQLException;
}
