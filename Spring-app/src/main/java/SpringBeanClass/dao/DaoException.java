package SpringBeanClass.dao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
/**
 * Custom exception which throws in Dao layer.
 * The class extends SQLException and gives more detailed answers why and where exception appeared.
 */
@Repository

public class DaoException extends SQLException {
    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
