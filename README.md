# Hibernate4-Java8-LocalDateTime

Hibernate 4 UserTypes for Java 8 LocalDateTime/LocalDate/LocalTime.

If you are using JPA 2.1 or Hibernate 4.3 or later, You'd better use Attribute Converter.

## Requirements
* Hibernate 4 or later
* Java 8

## Dependency

## User Types for TIMESTAMP, DATETIME, DATE, TIME database columns.

### LocalDateTimeUserType
An user type for Java 8 [LocalDateTime](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html).

```java
@Type(type = "kr.pe.kwonnam.hibernate4localdatetime.LocalDateTimeUserType")
@Column(name = "created_at", nullable = false)
private LocalDateTime createdAt;
```
Beware LocalDateTime/Instant support nanoseconds, but java.util.Date only supports milliseconds.
`LocaDateTimeUserType` changes LocalDateTime` to `java.util.Date` internally, so nanoseconds part will be truncated.

### LocalDateUserType
An user type for Java 8 [LocalDate](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html).

```java
@Type(type = "kr.pe.kwonnam.hibernate4localdatetime.LocalDateUserType")
@Column(name = "created_date", nullable = false)
private LocalDate createdDate;
```

### LocalTimeUserType
An user type for Java 8 [LocalTime](https://docs.oracle.com/javase/8/docs/api/java/time/LocalTime.html).

```java
@Type(type = "kr.pe.kwonnam.hibernate4localdatetime.LocalTimeUserType")
@Column(name = "created_time", nullable = false)
private LocalTime createdTime;
```

## UserTypes for VARCHAR(STRING) columns.
Because this user type need to parse/format string to LocalDateTime, you need to set [DateTimeFormatter](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html) pattern.

### StringLocalDateTimeUserType
An user type for Java 8 [LocalDateTime](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html) with a VARCHAR(String) column.

```java
@Type(
    type = "kr.pe.kwonnam.hibernate4localdatetime.StringLocalDateTimeUserType",
    parameters = {
        @Parameter(name = StringLocalDateTimeUserType.PARAM_PATTERN, value = "yyyy-MM-dd HH:mm:ss")
    }
)
@Column(name = "created_at", nullable = true, length = 23)
private LocalDateTime createdAt;
```

### StringLocalDateUserType
An user type for Java 8 [LocalDate](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html).

```java
@Type(
    type = "kr.pe.kwonnam.hibernate4localdatetime.StringLocalDateUserType",
    parameters = {
        @Parameter(name = StringLocalDateUserType.PARAM_PATTERN, value = "yyyyMMdd")
    }
)
@Column(name = "created_date", nullable = true, length = 8)
private LocalDate createdDate;
```

### StringLocalTimeUserType
An user type for Java 8 [LocalTime](https://docs.oracle.com/javase/8/docs/api/java/time/LocalTime.html).

```java
@Type(
    type = "kr.pe.kwonnam.hibernate4localdatetime.StringLocalTimeUserType",
    parameters = {
        @Parameter(name = StringLocalTimeUserType.PARAM_PATTERN, value = "HH:mm:ss.nnnnnnnnn")
    }
)
@Column(name = "created_time", nullable = true, length = 18)
private LocalTime createdTime;
```
