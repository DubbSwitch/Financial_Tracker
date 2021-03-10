package controllers;

import models.IODataModel;
import models.User;

public class UserContextController {
    public User findUser(IODataModel iodataModel, String userName){
        try {
            for (User u : iodataModel.getUsers())
                if (u.getUserName().equals(userName))
                    return u;
        }catch (StackOverflowError sofe){
            System.out.println("User does not exist");
        }
        return null;
    }
}
