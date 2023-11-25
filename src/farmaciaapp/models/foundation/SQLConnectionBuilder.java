
package farmaciaapp.models.foundation;

import java.sql.Connection;

/**
 *
 * @author Lenovo ideapad 330S
 */
public interface SQLConnectionBuilder {
    Connection getConnection();
    boolean checkDB();
    void Build();
}
