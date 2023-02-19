package bnext.backend.api.security.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Request {

    private String username;
    private String password;
    private List<String> roles;

}

