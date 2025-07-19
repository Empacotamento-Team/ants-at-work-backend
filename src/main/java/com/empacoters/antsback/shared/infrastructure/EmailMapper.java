package com.empacoters.antsback.shared.infrastructure;

import com.empacoters.antsback.shared.vo.Email;

public class EmailMapper {
    public static EmailEmbeddable toEmbeddable(Email email) {
        return new EmailEmbeddable(email.address());
    }

    public static Email toDomain(EmailEmbeddable emailEmbeddable) {
        return new Email(emailEmbeddable.getValue());
    }
}
