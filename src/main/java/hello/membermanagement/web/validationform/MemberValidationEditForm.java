package hello.membermanagement.web.validationform;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class MemberValidationEditForm {

    private Long id;
    @NotBlank
    @Pattern(regexp = "^[가-힣]*$|^[a-zA-Z]*$")
    private String name;
    @NotNull
    @Range(min = 1, max = 120)
    private Integer age;

    @Pattern(regexp = "\\d{4}\\d{2}\\d{2}")
    private String birthday;

    @NotNull
    @Pattern(regexp = "(010)-\\d{3,4}-\\d{4}")
    private String phoneNumber;
    @Email
    private String email;
}
