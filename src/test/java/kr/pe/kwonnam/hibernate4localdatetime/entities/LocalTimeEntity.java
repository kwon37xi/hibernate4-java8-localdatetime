package kr.pe.kwonnam.hibernate4localdatetime.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "local_time_entities")
public class LocalTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Type(type = "kr.pe.kwonnam.hibernate4localdatetime.LocalTimeUserType")
    @Column(name = "created_time", nullable = false)
    private LocalTime createdTime;

    @Type(type = "kr.pe.kwonnam.hibernate4localdatetime.LocalTimeUserType")
    @Column(name = "updated_time", nullable = true)
    private LocalTime updatedTime;

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

    public LocalTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
