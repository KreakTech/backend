package me.kreaktech.unility.entity;

import java.sql.Timestamp;
import javax.persistence.*;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "activity", uniqueConstraints = @UniqueConstraint(columnNames = {"university_id", "activity_content_id"}))
public class Activity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false)
	@JoinColumn(name = "university_id", referencedColumnName = "id")
    private University university;

    @NonNull
    @Column(name = "activity_date", nullable = false)
    private Timestamp date;

    @OneToOne(optional = false)
	@JoinColumn(name = "activity_content_id", referencedColumnName = "id")
    private ActivityContent activityContent;

}
