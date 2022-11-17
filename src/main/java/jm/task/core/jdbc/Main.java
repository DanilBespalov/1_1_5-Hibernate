package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Name1", "LastName1", (byte) 20);
        System.out.println("User c именем ");
        userService.saveUser("Name2", "LastName2", (byte) 25);
        System.out.println("User c именем ");
        userService.saveUser("Name3", "LastName3", (byte) 31);
        System.out.println("User c именем ");
        userService.saveUser("Name4", "LastName4", (byte) 38);
        System.out.println("User c именем ");

        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
