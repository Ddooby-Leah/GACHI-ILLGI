package com.ddooby.gachiillgi.domain.repository;

import com.ddooby.gachiillgi.domain.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@DataJpaTest
@Sql("/sql/user-repository-test-data.sql")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void 이메일로_유저와_유저권한을_찾을_수_있다() {
        //given
        //when
        Optional<User> user = userRepository.findOneWithUserAuthorityByEmail("ddooby.doobob@kakaoenterprise.com");

        //then
        Assertions.assertThat(user.isPresent()).isTrue();
        Assertions.assertThat(user.get().getUserAuthoritySet()).isNotEmpty();
    }

    @Test
    void 이메일이_없으면_Optional_empty를_반환한다() {
        //when
        Optional<User> user = userRepository.findOneWithUserAuthorityByEmail("ddooby.doobob@12313.com");

        //then
        Assertions.assertThat(user.isEmpty()).isTrue();
    }

    @Test
    void 유저아이디로_유저와_팔로우유저를_찾을_수_있다() {
        //given
        User findUser = userRepository.findByEmail("ddooby.doobob@kakaoenterprise.com").orElse(null);
        //when
        assert findUser != null;
        User user = userRepository.findOneWithFollowUsersByUserId(findUser.getUserId())
                .orElse(null);

        //then
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getFollowerList()).isNotEmpty();
    }

    @Test
    void 유저아이디가_없으면_Optional_empty를_반환한다() {
        //when
        Optional<User> user = userRepository.findOneWithFollowUsersByUserId(124141511513L);

        //then
        Assertions.assertThat(user.isEmpty()).isTrue();
    }

    @Test
    void 이메일로_유저를_찾을_수_있다() {
        //given
        //when
        Optional<User> user = userRepository.findByEmail("ddooby.doobob@kakaoenterprise.com");

        //then
        Assertions.assertThat(user.isPresent()).isTrue();
        Assertions.assertThat(user.get().getUserAuthoritySet()).isNotEmpty();
    }

    @Test
    void 잘못된_이메일이면_Optional_empty를_반환한다() {
        //when
        Optional<User> user = userRepository.findByEmail("ddooby.doobob@12313.com");

        //then
        Assertions.assertThat(user.isEmpty()).isTrue();
    }


}