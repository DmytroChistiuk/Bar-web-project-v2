package SpringBeanClass.service;

import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * Custom exception which throws in Service layer.
 * The class extends SQLException and gives more detailed answers why and where exception appeared.
 */
@Component
public class ServiceException extends SQLException {
    public ServiceException() {
    }
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
