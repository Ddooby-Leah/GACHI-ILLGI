package com.ddooby.gachiillgi.domain.repository;

import com.ddooby.gachiillgi.domain.entity.Authority;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
//@Sql("/sql/authority-repository-test-data.sql")
class AuthorityRepositoryTest {

    @Autowired
    AuthorityRepository authorityRepository;

    @Test
    void 권한이름으로_권한_엔티티를_찾을_수_있다() {
        //given
        //when
        Authority findResult = authorityRepository.findByAuthorityName("ROLE_GUEST");

        //then
        Assertions.assertThat(findResult.getCreatedBy()).isEqualTo("ddooby");
    }


}