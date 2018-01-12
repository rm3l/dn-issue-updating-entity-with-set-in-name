package mydomain.model;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"setting_key", "setting_value"}))
public class Setting {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Basic
  @Column(name = "setting_key", nullable = false)
  private String key;

  @Basic
  @Column(name = "setting_value")
  private String value;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Setting setting = (Setting) o;
    return Objects.equals(getKey(), setting.getKey()) &&
        Objects.equals(getValue(), setting.getValue());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getKey(), getValue());
  }

  @Override
  public String toString() {
    return "Setting{" +
        "id=" + id +
        ", key='" + key + '\'' +
        ", value='" + value + '\'' +
        '}';
  }
}
