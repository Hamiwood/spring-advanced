package org.example.expert.aop;

import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
@Table(name="api_use_time")
public class ApiUseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Long time;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String request;

    @Column(nullable = true)
    private String response;

    public ApiUseTime(User user, long time, String url, String request, String response) {
        this.user = user;
        this.time = time;
        this.url = url;
        this.request = request;
        this.response = response;
    }
}
