package com.project.manager.notice.api.domain.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 공지사항 repository
 *
 * @author 정재요
 * @date 2024. 04. 26
 */
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    /**
     * 공지사항 조회수 증가
     *
     * @param id 공지사항 ID
     */
    @Modifying
    @Query("update Notice set views = ifnull(views, 0) + 1 where id = :id")
    void increaseViews(Long id);

}
