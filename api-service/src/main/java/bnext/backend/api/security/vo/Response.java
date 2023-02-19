package bnext.backend.api.security.vo;


import bnext.backend.api.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Response {

    private String token;
    //private Set<UserRole> roles;
    private List<String> roles;
    private User user;
}
