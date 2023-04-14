package me.kreaktech.unility.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.checkerframework.common.aliasing.qual.Unique;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "university")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Name cannot be blank")
    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    
	@NonNull
	@Column(name = "announcements_last_fetch_date", nullable = false)
	private Timestamp date;
}
