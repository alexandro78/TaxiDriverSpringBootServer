package maingroup.vipcarserver.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cars")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"driver", "user", "carPicture", "carDocPicture", "carClass"})
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 15)
    @Column(nullable = false, length = 15)
    private String licensePlate;

    @NotBlank
    @Size(max = 40)
    @Column(nullable = false, length = 40)
    private String carBrand;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String carModel;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String color;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String production;

    @NotNull
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean param1 = false;

    @NotNull
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean approved = false;

    @NotNull
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean active = false;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @OneToOne(mappedBy = "car")
    private CarPicture carPicture;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private CarDocPicture carDocPicture;

    @OneToOne(mappedBy = "car")
    private CarClass carClass;

//    @Override
//    public String toString() {
//        return "Car{id=" + id +
//                ", licensePlate='" + licensePlate + '\'' +
//                ", carBrand='" + carBrand + '\'' +
//                ", carModel='" + carModel + '\'' +
//                ", color='" + color + '\'' +
//                ", production='" + production + '\'' +
//                ", coupe=" + coupe +
//                ", approved=" + approved +
//                ", active=" + active +
//                ", timestamp=" + timestamp + '}';
//    }
}
