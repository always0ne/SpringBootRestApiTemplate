package com.restapi.template.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 작성시간, 수정시간 관리용 객체
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Date {
    /**
     * 작성시각
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;
    /**
     * 수정시각
     */
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public Date() {
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    /**
     * 수정시각 업데이트
     */
    protected void update() {
        this.modifiedDate = LocalDateTime.now();
    }
}
