package com.ddooby.gachiillgi.domain.repository;

import com.ddooby.gachiillgi.domain.entity.Follow;
import com.ddooby.gachiillgi.domain.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql("/sql/follow-repository-test-data.sql")
class FollowRepositoryTest {

    @Autowired
    FollowRepository followRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void 팔로워와_팔로위로_팔로우_관계_엔티티를_찾을_수_있다() {
        //given
        User follower = userRepository.findByEmail("ddooby.doobob@kakaoenterprise.com")
                .orElse(null);

        User followee = userRepository.findByEmail("ddooby.doobob1@kakaoenterprise.com")
                .orElse(null);
        //when
        Follow follow = followRepository.findByFollowerAndFollowee(follower, followee)
                .orElse(null);

        //then
        Assertions.assertThat(follow).isNotNull();
    }

    @Test
    public void 잘못된_팔로워와_팔로위로_팔로우_관계_엔티티를_찾을_수_없다() {
        //given
        User follower = userRepository.findByEmail("ddooby.1313@kakaoente1rp13rise.com")
                .orElse(null);

        User followee = userRepository.findByEmail("ddooby.13131@kakaoent23erprise.com")
                .orElse(null);
        //when
        Follow follow = followRepository.findByFollowerAndFollowee(follower, followee)
                .orElse(null);

        //then
        Assertions.assertThat(follow).isNull();
    }

    @Test
    public void 팔로워와_팔로위로_팔로우_관계가_존재하는지_알_수_있다() {
        //given
        User follower = userRepository.findByEmail("ddooby.doobob@kakaoenterprise.com")
                .orElse(null);

        User followee = userRepository.findByEmail("ddooby.doobob1@kakaoenterprise.com")
                .orElse(null);

        //when
        //then
        Assertions.assertThat(followRepository.existsByFollowerAndFollowee(follower, followee)).isTrue();
    }

    @Test
    public void 잘못된_유저를_입력해도_팔로우_관계가_존재하는지_알_수_있다() {
        //given
        User follower = userRepository.findByEmail("ddooby.doobob@kak234terprise.com")
                .orElse(null);

        User followee = userRepository.findByEmail("ddooby.doo34bob1@kak234rprise.com")
                .orElse(null);

        //when
        //then
        Assertions.assertThat(followRepository.existsByFollowerAndFollowee(follower, followee)).isFalse();
    }


}