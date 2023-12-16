
package farmaciaapp.models.foundation;

import java.sql.Connection;

/**
 *
 * @author Lenovo ideapad 330S
 */
public interface SQLConnectionBuilder {
    static final int 
            ERROR_NO_ERROR = -1, 
            ERROR_BAD_LINK = 0,
            ERROR_INCORRECT_USER = 1, 
            ERROR_BAD_DATABASE = 2;
    
    Connection getConnection();
    boolean checkDB();
    void Build();
    int getError();
}
