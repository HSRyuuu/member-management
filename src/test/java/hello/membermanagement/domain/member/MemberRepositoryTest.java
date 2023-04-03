package hello.membermanagement.domain.member;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = new MemberRepository();

    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }
    @Test
    void save() {
        //given
        Member member = new Member("kim",12,"19980803","01053092890", "hello@gmail.com");

        //when
        Member savedMember = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findById() {
        //given
        Member member = new Member("kim",12,"19980803","01053092890", "hello@gmail.com");
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findById(member.getId());

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void findAll() {
        //given
        Member member1 = new Member("memA",12,"19980803","01053092890", "hello@gmail.com");
        Member member2 = new Member("memB",12,"19980803","01053092890", "hello2@gmail.com");
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> list = memberRepository.findAll();

        //then
        assertThat(list).contains(member1,member2);
    }

    @Test
    void updateMember() {
        //given
        Member member = new Member("memA",12,"19980803","01053092890", "hello@gmail.com");

        Member savedMember = memberRepository.save(member);
        Long memberId = savedMember.getId();

        //when
        Member updateParam = new Member("memB",23,"20201010","01029392030", "hello@gmail.com");
        memberRepository.updateMember(memberId,updateParam);

        //then
        Member findMember = memberRepository.findById(memberId);
        assertThat(findMember.getName()).isEqualTo("memB");
        assertThat(findMember.getAge()).isEqualTo(23);
        assertThat(findMember.getBirthday()).isEqualTo("20201010");
        assertThat(findMember.getPhoneNumber()).isEqualTo("01029392030");
        assertThat(findMember.getEmail()).isEqualTo("hello@gmail.com");

    }

    @Test
    void deleteMember() {
    }

    @Test
    void clearStore() {
    }

    @Test
    void isExist() {
        //given
        Member member1 = new Member("memA",12,"19980803","01053092890", "hello@gmail.com");
        Member member2 = new Member("memB",12,"19980803","01053092890", "hello2@gmail.com");
        memberRepository.save(member1);
        memberRepository.save(member2);
        Long id1 = member1.getId();
        Long id2 = member2.getId();
        //when
        boolean exist = memberRepository.isExist(id1);
        boolean not_exist = memberRepository.isExist(1342L);

        assertThat(exist).isTrue();
        assertThat(not_exist).isFalse();
    }
}