package kr.pe.kwonnam.hibernate4localdatetime.entities;

import kr.pe.kwonnam.hibernate4localdatetime.StringLocalDateTimeUserType;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "string_local_date_time_entities")
public class StringLocalDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Type(
        type = "kr.pe.kwonnam.hibernate4localdatetime.StringLocalDateTimeUserType",
        parameters = {
            @Parameter(name = StringLocalDateTimeUserType.PARAM_PATTERN, value = "yyyyMMddHHmmss")
        }
    )
    @Column(name = "created_at", nullable = true, length = 14)
    private LocalDateTime createdAt;

    @Type(
        type = "kr.pe.kwonnam.hibernate4localdatetime.StringLocalDateTimeUserType",
        parameters = {
            @Parameter(name = StringLocalDateTimeUserType.PARAM_PATTERN, value = "yyyy/MM/dd HH:mm:ss.SSS")
        }
    )
    @Column(name = "updated_at", nullable = true, length = 23)
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
