package mydomain.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"setting_key", "setting_value"}))
public class Setting {

  @Id
  private Long id;

  @Basic
  @Column(name = "setting_key", nullable = false)
  private String settingKey;

  @Basic
  @Column(name = "setting_value")
  private String settingValue;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSettingKey() {
    return settingKey;
  }

  public void setSettingKey(String settingKey) {
    this.settingKey = settingKey;
  }

  public String getSettingValue() {
    return settingValue;
  }

  public void setSettingValue(String settingValue) {
    this.settingValue = settingValue;
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
    return Objects.equals(getSettingKey(), setting.getSettingKey()) &&
        Objects.equals(getSettingValue(), setting.getSettingValue());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getSettingKey(), getSettingValue());
  }

  @Override
  public String toString() {
    return "Setting{" +
        "id=" + id +
        ", settingKey='" + settingKey + '\'' +
        ", settingValue='" + settingValue + '\'' +
        '}';
  }
}
