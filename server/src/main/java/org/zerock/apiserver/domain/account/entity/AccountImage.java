package org.zerock.apiserver.domain.account.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "fileName")
public class AccountImage {

    private String fileName;

    private int ord;
    public AccountImage(String fileName, int ord) {
        this.fileName = fileName;
        this.ord = ord;
    }

}
