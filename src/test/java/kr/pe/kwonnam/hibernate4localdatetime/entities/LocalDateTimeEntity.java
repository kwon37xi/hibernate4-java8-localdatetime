package kr.pe.kwonnam.hibernate4localdatetime.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "local_date_time_entities")
public class LocalDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Type(type = "kr.pe.kwonnam.hibernate4localdatetime.LocalDateTimeUserType")
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Type(type = "kr.pe.kwonnam.hibernate4localdatetime.LocalDateTimeUserType")
    @Column(name = "updatedAt", nullable = true)
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
}

