package maingroup.vipcarserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank; //Blank constraints at the framework level @NotBlank includes checking for null and empty strings//
import jakarta.validation.constraints.NotNull; //for Null constraints at the framework level//
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "vipcar_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = {"citySector", "roles", "rider", "driver", "optionsSetting", "cars", "carPictures", "profilePhotos", "messages", "chatUsers", "blackListedUsers", "blockedByUsers"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String lastName;

    @NotBlank
    @Size(min = 10, max = 10)
    @Column(nullable = false, length = 10)
    private String birthDate;

    @NotBlank
    @Email
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String email;

    @NotBlank
    @Size(min = 7, max = 25)
    @Column(nullable = false)
    private String phone;

    @NotBlank
    @NotBlank
    @Size(min = 8, max = 255)
    @Column(nullable = false, length = 255)
    private String password;

    @NotNull
    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal rating = BigDecimal.valueOf(5.0);

    @NotNull
    @Column(nullable = false, name = "user_sity")
    @ColumnDefault("'Дніпро'")
    private String userSity = "Дніпро";

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "city_sector_id", nullable = false)
    private CitySector citySector;

    @ManyToMany(/*fetch = FetchType.EAGER*/fetch = FetchType.LAZY)
    @JoinTable(
            name = "vipcar_users_roles",
            joinColumns = @JoinColumn(name = "vipcar_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Rider rider;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true /*fetch = FetchType.EAGER*/)
    private Driver driver;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Setting setting;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private MSetting mSetting;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Pin pin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Car> cars = new HashSet<>(); // Add a relation to Driver Car

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CarPicture> carPictures = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProfilePhoto> profilePhotos = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MStatus> mStatuses = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatUser> chatUsers = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BlackList> blackListedUsers = new HashSet<>();

    @OneToMany(mappedBy = "blockedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BlackList> blockedByUsers = new HashSet<>();

    //avoid recursive loop occurring within your hashCode method implementations
    // using Override hashCode() and equals():
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return id != null && id.equals(other.id);
    }
}