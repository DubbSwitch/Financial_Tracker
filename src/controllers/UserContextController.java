package controllers;

import models.IODataModel;
import models.User;

public class UserContextController {
    public User findUser(IODataModel iodataModel, String userName){
        for(User u : iodataModel.getUsers())
            if(u.getUserName().equals(userName))
                return u;
        return null;
    }
}
