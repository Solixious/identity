package org.pratyush.identity.repository;

import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.jooq.DSLContext;
import org.pratyush.identity.db.tables.records.TblUsersRecord;
import org.pratyush.identity.model.dto.User;
import org.pratyush.identity.model.request.UserRegistrationRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static org.pratyush.identity.db.tables.TblUsers.TBL_USERS;

@Repository
@Slf4j
public class UserRepository {

    private final DSLContext dsl;
    private final Mapper mapper;
    private final RoleRepository roleRepository;

    public UserRepository(DSLContext dsl, Mapper mapper, RoleRepository roleRepository) {
        this.dsl = dsl;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
    }

    public Mono<Integer> register(UserRegistrationRequest request) {
        TblUsersRecord record = dsl.newRecord(TBL_USERS, request);
        return Mono.from(
                dsl.insertInto(TBL_USERS)
                        .set(record))
                .flatMap(i -> roleRepository.addDefaultRoleForUser(request))
                .onErrorReturn(0);
    }

    public Mono<User> findByEmail(String email) {
        return Mono.from(
                dsl.selectFrom(TBL_USERS)
                        .where(TBL_USERS.EMAIL.eq(email)))
                .map(this::toUser)
                .flatMap(roleRepository::attachRoles);
    }

    private User toUser(TblUsersRecord tblUsersRecord) {
        return mapper.map(tblUsersRecord, User.class);
    }
}
