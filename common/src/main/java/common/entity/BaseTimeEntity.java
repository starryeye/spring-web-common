package common.entity;

import lombok.Getter;

import java.time.ZonedDateTime;

/**
 * TODO
 */
@Getter
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

//    @CreatedDate
//    @Column(updatable = false)
    private ZonedDateTime createdDate;

//    @LastModifiedDate
    private ZonedDateTime lastModifiedDate;
}
