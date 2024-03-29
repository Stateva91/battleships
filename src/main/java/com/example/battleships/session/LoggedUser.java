package com.example.battleships.session;

import com.example.battleships.models.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class LoggedUser {
    private Long id;

    private String fullName;

    public void login(User user){
       this.id=user.getId();
       this.fullName= user.getFullName();
    }

    public void logout(){
        this.id=null;
        this.fullName=null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
