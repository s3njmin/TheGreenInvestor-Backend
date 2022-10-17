package G2T6.G2T6.G2T6.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Article {
    @GeneratedValue(strategy = GenerationType.AUTO) @Id
    private Long id;
    @Column(length=100000)
    private String body;
    private String article;

    public Article(String body, String article) {
        this.body = body;
        this.article = article;
    }
}
