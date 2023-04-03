package hello.membermanagement.web.validationform;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteValidationForm {
    @NotNull
    private Long id;
}
