package hello.membermanagement.domain.admin;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Admin {
    private String id;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
}
