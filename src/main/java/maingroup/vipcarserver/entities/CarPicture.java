package maingroup.vipcarserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "car_pictures")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String frontSide;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String backSide;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String leftSide;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String rightSide;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String frontInterior;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String backInterior;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(nullable = false)
    private Car car;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "timestamp without time zone")
    private LocalDateTime updatedAt;
}
//Completed//
