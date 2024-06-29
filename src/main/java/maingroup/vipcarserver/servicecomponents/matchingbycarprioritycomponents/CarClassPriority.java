package maingroup.vipcarserver.servicecomponents.matchingbycarprioritycomponents;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum CarClassPriority {
    ELITE(1),
    PREMIUM(2),
    LUX(3),
    COMFORT(4);

    private final int classPriority;
}
