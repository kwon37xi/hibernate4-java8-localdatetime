package kr.pe.kwonnam.hibernate4localdatetime.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Type(type = "kr.pe.kwonnam.hibernate4localdatetime.LocalDateTimeUserType")
    @Column(name = "createdAt", nullable = false, columnDefinition = "datetime")
    private LocalDateTime createdAt;
}

