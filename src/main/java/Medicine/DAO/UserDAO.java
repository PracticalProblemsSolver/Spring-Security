package Medicine.DAO;

import Medicine.Entities.User;
import java.util.List;

public interface UserDAO {
    public void addUser(User user);

    public List<User> getUserList();

    public boolean userExist(String username);

    public User findByLogin(String login);
}
